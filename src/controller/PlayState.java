package controller;
import java.awt.*;
import java.awt.event.*;
import model.*;
import utils.*;
import java.util.Iterator;

/**
 * Represents the gameplay state where the player navigates a level, collects jewels, and avoids obstacles.
 * Handles level loading, player input, physics updates, collision detection, and win/loss conditions.
 * @author Gurangad Batth
 */
public class PlayState extends GameState{
    // instances
    private LevelManager levelManager;
    private InputHandler input;
    private GameStateManager gsm;
    private int currentLevel;
    private int totalScore;
    // We will change this incase we add more levels
    private final int MAX_LEVELS = 6;

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
        // Initialize the level and load the map text file
        this.gsm = gsm;
        this.totalScore = score;
        levelManager = new LevelManager();
        levelManager.loadLevel("assets/levels/level" + currentLevel + ".txt");

        // Restore players score from level before
        Player p = levelManager.getPlayer();
        if(p!=null){
            for(int i = 0; i < totalScore; i++){
                p.addJewel();
            }
        }
    }

    /**
     * Updates the game state each frame: processes input, checks win conditions, updates physics,
     * handles collisions, and manages state transitions.
     */
    @Override
    public void update(){
        Player player = levelManager.getPlayer();
        // process the keyboard controls.
        if(player != null){
            if(input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A)){
                player.moveLeft();
            }
            if(input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D)){
                player.moveRight();
            }
            if(input.isKeyDown(KeyEvent.VK_SPACE) || input.isKeyDown(KeyEvent.VK_W)){
                player.jump();
            }
        }

        // win condition
        boolean jewelsLeft = false;
        for(Entity e : levelManager.getEntities()){
            if(e instanceof Collectible){
                jewelsLeft = true;
                break; // we found atleast one, no need to keep checking
            }
        }

        if(!jewelsLeft){
            if(currentLevel < MAX_LEVELS){
                // load the next level, passing the current score along
                gsm.setState(new PlayState(gsm, input, currentLevel + 1, player.getJewelsCollected()));
            }
            else{
                // we beat the final level, save to file and show game over screen
                ScoreManager.saveScore(player.getJewelsCollected());
                gsm.setState(new GameOverState(gsm, input, "Heist Complete!", player.getJewelsCollected()));
            }
            return;
        }

        // update all entities
        for(Entity e : levelManager.getEntities()){
            e.update();
        }

        // handle player collisions and pickups
        if(player != null){
            player.setGrounded(false);

            Iterator<Entity> iterator = levelManager.getEntities().iterator();
            while(iterator.hasNext()){
                Entity e = iterator.next();

                if(e == player){
                    continue;
                }

                if(PhysicsUtils.checkCollision(player, e)){
                    if(e instanceof Obstacle){
                        Obstacle obstacle = (Obstacle) e;

                        if(PhysicsUtils.isLandingOn(player, obstacle) && obstacle.getDamage() == 0 && player.getYVelocity() >= 0){
                            player.setY(obstacle.getY() - player.getHeight());
                            player.resetYVelocity();
                            player.setGrounded(true);
                        }
                        else if(obstacle.getDamage() > 0){
                            gsm.setState(new GameOverState(gsm, input, "Mission Failed", player.getJewelsCollected()));
                            return;
                        }
                    }
                    else if(e instanceof Collectible){
                        Collectible collectible = (Collectible) e;
                        player.addJewel();
                        totalScore += collectible.getScoreValue() / 100;
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Renders every entity for the current level and the on-screen score.
     * @param g2d the graphics object used for drawing gameplay
     */
    @Override
    public void render(Graphics2D g2d){
        // draw background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        for(Entity e : levelManager.getEntities()){
            if(e instanceof Player){
                g2d.setColor(Color.CYAN);
                g2d.fillRect((int)e.getX(), (int)e.getY(), e.getWidth(), e.getHeight());
            }
            else if(e instanceof Obstacle){
                Obstacle obstacle = (Obstacle) e;
                if(obstacle.getDamage() > 0){
                    g2d.setColor(Color.RED);
                }
                else{
                    g2d.setColor(Color.GRAY);
                }
                g2d.fillRect((int)e.getX(), (int)e.getY(), e.getWidth(), e.getHeight());
            }
            else if(e instanceof Collectible){
                g2d.setColor(Color.YELLOW);
                g2d.fillOval((int)e.getX(), (int)e.getY(), e.getWidth(), e.getHeight());
            }
        }

        Player player = levelManager.getPlayer();
        if(player != null){
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
            g2d.drawString("Jewels: " + player.getJewelsCollected(), 20, 30);
            g2d.drawString("Level: " + currentLevel, 20, 55);
        }
    }
}
