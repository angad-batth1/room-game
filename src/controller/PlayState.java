package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import model.Collectible;
import model.Entity;
import model.LevelManager;
import model.Obstacle;
import model.Player;
import utils.AssetLoader;
import utils.Constants;
import utils.PhysicsUtils;
import utils.ScoreManager;

/**
 * Represents the gameplay state where the player navigates a level, collects jewels, and avoids obstacles.
 * Handles level loading, player input, physics updates, collision detection, and win/loss conditions.
 * @author Gurangad Batth
 */
public class PlayState extends GameState{
    // instances
    private static final int MAX_LEVELS = 6;
    private static final BufferedImage GAME_BACKGROUND = AssetLoader.loadImage(Constants.GAME_BACKGROUND_PATH);
    private static final BufferedImage PLAYER_SPRITE = AssetLoader.loadImage(Constants.PLAYER_SPRITE_PATH);
    private static final BufferedImage WALL_SPRITE = AssetLoader.loadImage(Constants.WALL_SPRITE_PATH);
    private static final BufferedImage HAZARD_SPRITE = AssetLoader.loadImage(Constants.HAZARD_SPRITE_PATH);
    private static final BufferedImage JEWEL_SPRITE = AssetLoader.loadImage(Constants.JEWEL_SPRITE_PATH);

    private final LevelManager levelManager;
    private final InputHandler input;
    private final int currentLevel;
    private final int startingScore;
    private boolean paused;
    private boolean facingRight = true;

    /**
     * Constructor for the play state, loads the specified level and restores the player's score.
     * @param gsm the game state manager for state transitions
     * @param input the input handler for player controls
     * @param level the level number to load
     * @param score the cumulative jewels collected from previous levels
     */
    public PlayState(GameStateManager gsm, InputHandler input, int level, int score){
        super(gsm);
        this.input = input;
        this.currentLevel = level;
        this.startingScore = score;
        // Initialize the level and load the map text file
        levelManager = new LevelManager();
        levelManager.loadLevel("assets/levels/level" + currentLevel + ".txt");

        // Restore players score from level before
        Player player = levelManager.getPlayer();
        if(player != null){
            player.setJewelsCollected(score);
        }
    }

    /**
     * Updates the game state each frame: processes input, checks win conditions, updates physics,
     * handles collisions, and manages state transitions.
     */
    @Override
    public void update(){
        Player player = levelManager.getPlayer();
        if(player == null){
            gsm.setState(new GameOverState(gsm, input, "Level Load Error", 0));
            return;
        }

        if(input.consumeKeyPress(KeyEvent.VK_ESCAPE)){
            paused = !paused;
            return;
        }

        if(paused){
            if(input.consumeKeyPress(KeyEvent.VK_R)){
                input.resetKeys();
                gsm.setState(new PlayState(gsm, input, currentLevel, startingScore));
                return;
            }
            if(input.consumeKeyPress(KeyEvent.VK_M)){
                input.resetKeys();
                gsm.setState(new MainMenuState(gsm, input));
            }
            return;
        }

        // process the keyboard controls.
        boolean movingLeft = input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A);
        boolean movingRight = input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D);

        if(movingLeft && !movingRight){
            player.moveLeft();
            facingRight = false;
        }
        if(movingRight && !movingLeft){
            player.moveRight();
            facingRight = true;
        }
        if(input.isKeyDown(KeyEvent.VK_SPACE) || input.isKeyDown(KeyEvent.VK_W)){
            player.jump();
        }

        // update all entities
        for(Entity e : levelManager.getEntities()){
            if(e != player){
                e.update();
            }
        }
        player.update();

        // handle player collisions and pickups
        player.setGrounded(false);

        Iterator<Entity> iterator = levelManager.getEntities().iterator();
        while(iterator.hasNext()){
            Entity e = iterator.next();
            if(e == player){
                continue;
            }

            if(e instanceof Obstacle){
                Obstacle obstacle = (Obstacle) e;
                if(handleObstacleCollision(player, obstacle)){
                    return;
                }
            }
            else if(e instanceof Collectible && PhysicsUtils.checkCollision(player, e)){
                player.addJewel();
                iterator.remove();
            }
        }

        if(countRemainingJewels() == 0){
            if(currentLevel < MAX_LEVELS){
                input.resetKeys();
                gsm.setState(new PlayState(gsm, input, currentLevel + 1, player.getJewelsCollected()));
            }
            else{
                ScoreManager.saveScore(player.getJewelsCollected());
                input.resetKeys();
                gsm.setState(new GameOverState(gsm, input, "Heist Complete!", player.getJewelsCollected()));
            }
        }
    }

    /**
     * Renders every entity for the current level and the on-screen score.
     * @param g2d the graphics object used for drawing gameplay
     */
    @Override
    public void render(Graphics2D g2d){
        drawGameplayBackground(g2d);

        for(Entity e : levelManager.getEntities()){
            if(e instanceof Player){
                drawPlayer(g2d, (Player) e);
            }
            else if(e instanceof Obstacle){
                drawObstacle(g2d, (Obstacle) e);
            }
            else if(e instanceof Collectible){
                drawCollectible(g2d, (Collectible) e);
            }
        }

        drawHud(g2d, levelManager.getPlayer());
        drawHintText(g2d);

        if(paused){
            drawPauseOverlay(g2d);
        }
    }

    /**
     * This helper method handles wall and hazard collisions for the player.
     * @param player the active player entity
     * @param obstacle the obstacle being checked
     * @return true if the collision immediately ends the run, false otherwise
     */
    private boolean handleObstacleCollision(Player player, Obstacle obstacle){
        if(!PhysicsUtils.checkCollision(player, obstacle)){
            return false;
        }

        if(obstacle.getDamage() > 0){
            input.resetKeys();
            gsm.setState(new GameOverState(gsm, input, "Mission Failed", player.getJewelsCollected()));
            return true;
        }

        if(PhysicsUtils.isLandingOn(player, obstacle) && player.getYVelocity() >= 0){
            player.setY(obstacle.getY() - player.getHeight());
            player.resetYVelocity();
            player.setGrounded(true);
        }
        else if(player.getYVelocity() < 0 && player.getY() >= obstacle.getY()){
            player.setY(obstacle.getY() + obstacle.getHeight());
            player.resetYVelocity();
        }
        else if(player.getX() < obstacle.getX()){
            player.setX(obstacle.getX() - player.getWidth());
        }
        else{
            player.setX(obstacle.getX() + obstacle.getWidth());
        }

        player.updateHitbox();
        return false;
    }

    /**
     * This helper method counts how many jewels remain in the current level.
     * @return the number of collectibles still left in the room
     */
    private int countRemainingJewels(){
        int jewelsLeft = 0;
        for(Entity entity : levelManager.getEntities()){
            if(entity instanceof Collectible){
                jewelsLeft++;
            }
        }
        return jewelsLeft;
    }

    /**
     * This helper method draws the gameplay background with a gradient and subtle grid.
     * @param g2d the graphics object used for drawing
     */
    private void drawGameplayBackground(Graphics2D g2d){
        if(GAME_BACKGROUND != null){
            g2d.drawImage(GAME_BACKGROUND, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
            g2d.setColor(new Color(255, 255, 255, 46));
            g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            return;
        }

        g2d.setPaint(new GradientPaint(0, 0, new Color(236, 236, 232), 0, Constants.SCREEN_HEIGHT, new Color(212, 212, 207)));
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    /**
     * This helper method draws the player with a brighter heist style.
     * @param g2d the graphics object used for drawing
     * @param player the player being rendered
     */
    private void drawPlayer(Graphics2D g2d, Player player){
        long now = System.currentTimeMillis();
        int x = (int) player.getX();
        int y = (int) player.getY();
        int width = player.getWidth();
        int height = player.getHeight();
        boolean movingHorizontally = isPlayerTryingToMove();
        boolean airborne = !player.isGrounded();

        double bobOffset = 0;
        double stretchX = 1.0;
        double stretchY = 1.0;
        double rotation = 0;

        if(airborne){
            bobOffset = -2.0;
            stretchX = 0.94;
            stretchY = 1.08;
            rotation = facingRight ? -0.08 : 0.08;
        }
        else if(movingHorizontally){
            double runOscillation = Math.sin(now / 95.0);
            bobOffset = Math.abs(runOscillation) * -2.2;
            stretchX = 1.02 + (Math.abs(runOscillation) * 0.10);
            stretchY = 1.02 - (Math.abs(runOscillation) * 0.08);
            rotation = runOscillation * 0.05;
        }
        else{
            double idleOscillation = Math.sin(now / 240.0);
            bobOffset = idleOscillation * -1.2;
            stretchX = 1.0 + (idleOscillation * 0.02);
            stretchY = 1.0 - (idleOscillation * 0.02);
        }

        int drawWidth = (int) Math.round(width * 1.10);
        int drawHeight = (int) Math.round(height * 1.10);
        if(PLAYER_SPRITE != null){
            double aspectRatio = (double) PLAYER_SPRITE.getWidth() / PLAYER_SPRITE.getHeight();
            double baseSize = Math.max(width, height) * 1.18;
            if(aspectRatio >= 1.0){
                drawWidth = (int) Math.round(baseSize);
                drawHeight = (int) Math.round(baseSize / aspectRatio);
            }
            else{
                drawHeight = (int) Math.round(baseSize);
                drawWidth = (int) Math.round(baseSize * aspectRatio);
            }
        }

        int centerX = x + (width / 2);
        int floorY = y + height;
        int shadowWidth = airborne ? 14 : 18;
        int shadowHeight = airborne ? 4 : 6;
        g2d.setColor(new Color(60, 60, 60, 68));
        g2d.fillOval(centerX - (shadowWidth / 2), floorY - 4, shadowWidth, shadowHeight);

        Graphics2D playerGraphics = (Graphics2D) g2d.create();
        playerGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        playerGraphics.translate(centerX, floorY - (drawHeight / 2.0) + bobOffset);
        playerGraphics.rotate(rotation);
        playerGraphics.scale(facingRight ? stretchX : -stretchX, stretchY);

        if(PLAYER_SPRITE != null){
            playerGraphics.drawImage(PLAYER_SPRITE, -drawWidth / 2, -drawHeight / 2, drawWidth, drawHeight, null);
        }
        else{
            playerGraphics.setColor(new Color(212, 93, 76));
            playerGraphics.fillRoundRect(-drawWidth / 2, -drawHeight / 2, drawWidth, drawHeight, 14, 14);
        }
        playerGraphics.dispose();
    }

    /**
     * This helper method draws a wall block or hovering hazard.
     * @param g2d the graphics object used for drawing
     * @param obstacle the obstacle being rendered
     */
    private void drawObstacle(Graphics2D g2d, Obstacle obstacle){
        long now = System.currentTimeMillis();
        int x = (int) obstacle.getX();
        int y = (int) obstacle.getY();
        int width = obstacle.getWidth();
        int height = obstacle.getHeight();

        if(obstacle.getDamage() > 0){
            int hoverOffset = (int) Math.round(Math.sin((now + (x * 18L)) / 180.0) * 2.0);
            g2d.setColor(new Color(65, 65, 65, 60));
            g2d.fillOval(x + 6, y + height - 5, width - 12, 5);

            if(HAZARD_SPRITE != null){
                g2d.drawImage(HAZARD_SPRITE, x, y + hoverOffset, width, height, null);
                return;
            }
            g2d.setPaint(new GradientPaint(x, y, new Color(118, 45, 45), x, y + height, new Color(72, 19, 19)));
            g2d.fillOval(x, y + hoverOffset, width, height);
            return;
        }

        if(WALL_SPRITE != null){
            g2d.drawImage(WALL_SPRITE, x, y, width, height, null);
            return;
        }

        g2d.setPaint(new GradientPaint(x, y, new Color(228, 228, 224), x, y + height, new Color(198, 198, 192)));
        g2d.fillRoundRect(x, y, width, height, 12, 12);
        g2d.setColor(new Color(178, 178, 170));
        g2d.drawRoundRect(x, y, width, height, 12, 12);
    }

    /**
     * This helper method draws a jewel with glow and a diamond shape.
     * @param g2d the graphics object used for drawing
     * @param collectible the collectible being rendered
     */
    private void drawCollectible(Graphics2D g2d, Collectible collectible){
        long now = System.currentTimeMillis();
        int x = (int) collectible.getX();
        int y = (int) collectible.getY();
        int width = collectible.getWidth();
        int height = collectible.getHeight();
        int hoverOffset = (int) Math.round(Math.sin((now + (x * 14L)) / 200.0) * 2.0);

        if(JEWEL_SPRITE != null){
            g2d.setColor(new Color(80, 72, 48, 45));
            g2d.fillOval(x + 7, y + height - 4, width - 14, 4);
            g2d.drawImage(JEWEL_SPRITE, x, y + hoverOffset, width, height, null);
            return;
        }

        int pulseSize = (int) ((Math.sin(now / 180.0) + 1.0) * 2.0);

        g2d.setColor(new Color(255, 231, 140, 70));
        g2d.fillOval(x - 6 - pulseSize, y - 6 - pulseSize + hoverOffset, width + 12 + (pulseSize * 2), height + 12 + (pulseSize * 2));

        Polygon jewel = new Polygon();
        jewel.addPoint(x + width / 2, y + hoverOffset);
        jewel.addPoint(x + width, y + (height / 2) + hoverOffset);
        jewel.addPoint(x + width / 2, y + height + hoverOffset);
        jewel.addPoint(x, y + (height / 2) + hoverOffset);

        g2d.setColor(new Color(239, 206, 94));
        g2d.fillPolygon(jewel);
        g2d.setColor(new Color(255, 255, 255, 170));
        g2d.drawPolygon(jewel);
    }

    /**
     * This helper method draws the top left HUD panel.
     * @param g2d the graphics object used for drawing
     * @param player the current player entity
     */
    private void drawHud(Graphics2D g2d, Player player){
        if(player == null){
            return;
        }

        g2d.setColor(new Color(248, 243, 236, 210));
        g2d.fillRoundRect(12, 12, 220, 86, 18, 18);
        g2d.setColor(new Color(196, 189, 180, 180));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(12, 12, 220, 86, 18, 18);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2d.setColor(new Color(72, 66, 60));
        g2d.drawString("Jewels: " + player.getJewelsCollected(), 24, 38);
        g2d.drawString("Level: " + currentLevel, 24, 62);
        g2d.drawString("Remaining: " + countRemainingJewels(), 24, 86);
    }

    /**
     * This helper method draws the bottom hint text for gameplay.
     * @param g2d the graphics object used for drawing
     */
    private void drawHintText(Graphics2D g2d){
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g2d.setColor(new Color(88, 84, 78));
        g2d.drawString("Collect every jewel and avoid hazards. Press [ESC] to pause.", 20, Constants.SCREEN_HEIGHT - 12);
    }

    /**
     * This helper method draws the pause overlay and control list.
     * @param g2d the graphics object used for drawing
     */
    private void drawPauseOverlay(Graphics2D g2d){
        g2d.setColor(new Color(60, 58, 54, 140));
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        int panelX = 190;
        int panelY = 150;
        int panelWidth = 420;
        int panelHeight = 250;
        g2d.setColor(new Color(249, 245, 239, 230));
        g2d.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 28, 28);
        g2d.setColor(new Color(201, 191, 181, 180));
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 28, 28);

        drawCenteredString(g2d, "Heist Paused", 205, new Font("Monospaced", Font.BOLD, 34), new Color(188, 83, 67));
        drawCenteredString(g2d, "Press [ESC] to Resume", 265, new Font("Monospaced", Font.PLAIN, 20), new Color(74, 69, 63));
        drawCenteredString(g2d, "Press [R] to Restart This Room", 305, new Font("Monospaced", Font.PLAIN, 20), new Color(74, 69, 63));
        drawCenteredString(g2d, "Press [M] for Main Menu", 345, new Font("Monospaced", Font.PLAIN, 20), new Color(74, 69, 63));
    }

    /**
     * This helper method checks if the player is currently pressing one horizontal direction.
     * @return true if the player is trying to move left or right, false otherwise
     */
    private boolean isPlayerTryingToMove(){
        boolean movingLeft = input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A);
        boolean movingRight = input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D);
        return movingLeft ^ movingRight;
    }

    /**
     * This helper method draws centered text inside gameplay overlays.
     * @param g2d the graphics object used for drawing
     * @param text the text being displayed
     * @param y the y coordinate for the baseline of the text
     * @param font the font used to draw the text
     * @param color the color used to draw the text
     */
    private void drawCenteredString(Graphics2D g2d, String text, int y, Font font, Color color){
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = (Constants.SCREEN_WIDTH - metrics.stringWidth(text)) / 2;
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }
}
