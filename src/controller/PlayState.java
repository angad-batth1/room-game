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
     * Constructor for the play state, loads the specified level and restores the player's score
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
     * handles collisions, and manages state transitions
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
                // we beat the final level, save to file and show the win screen
                ScoreManager.saveScore(player.getJewelsCollected());
                gsm.setState(new GameOverState(gsm, input, "Heists Successfull", player.getJewelsCollected()));
            }
            return; // exit update method
        }


        // update the physics for all the entities using a enhanced for loop
        for(Entity e : levelManager.getEntities()){
            e.update();
        }
        // reset the grounded state wach frame. we will prove they are on the ground below
        player.setGrounded(false);

        // I found this Java Iterator to be the best  way to safely delete items.
        Iterator<Entity> it = levelManager.getEntities().iterator();
        while(it.hasNext()){
            Entity e = it.next();
            // skip checking the player against themselves
            if(e instanceof Player) continue;
            // obstacles (walls and lasters)
            if(e instanceof Obstacle){
                Obstacle obs = (Obstacle) e;
                // did we hit a laser
                if(obs.getDamage() > 0 && PhysicsUtils.checkCollision(player, obs)){
                    gsm.setState(new GameOverState(gsm, input, "KILLED BY LASER", player.getJewelsCollected())); // instantly reload the state
                    return;
                }
                // did we land on a wall
                if (PhysicsUtils.isLandingOn(player, obs)){
                    player.setY(obs.getY() - player.getHeight()); // Snap to the top of the block
                    player.resetYVelocity(); // Stop gravity
                    player.setGrounded(true); // Allow jumping again
                    player.updateHitbox(); // Sync the collision box instantly
                }else if(PhysicsUtils.checkCollision(player, obs)){
                    // head on ceiling
                    if(player.getYVelocity() < 0 && player.getY() > obs.getY()) {
                        player.setY(obs.getY() + obs.getHeight());
                        player.resetYVelocity();
                    } 
                    // hit left side of wall
                    else if(player.getX() < obs.getX()){
                        player.setX(obs.getX() - player.getWidth());
                    } 
                    // hit right side of wall
                    else if(player.getX() > obs.getX()){
                        player.setX(obs.getX() + obs.getWidth());
                    }
                }
                player.updateHitbox(); // sync hitbox after collision adjustment
            } 
            // collectibles and jewels
            else if (e instanceof model.Collectible) {
                if (PhysicsUtils.checkCollision(player, e)) {
                    player.addJewel();
                    it.remove(); // Safely delete the jewel from the map!
                }
            }
        }
    }

    /**
     * Renders the current level including the background, all entities (player, obstacles, jewels),
     * and the heads-up display showing level and score
     * @param g2d the Graphics2D object used for drawing
     */
    @Override
    public void render(Graphics2D g2d){
        // set the background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, utils.Constants.SCREEN_WIDTH, utils.Constants.SCREEN_HEIGHT);
        // draw all the entities in the current level using a enhanced for loop.
        for(Entity e : levelManager.getEntities()){
            // temporary placeholders untill we get sprites.
            if(e instanceof Player){
                g2d.setColor(Color.CYAN);
            }else if(e instanceof Obstacle){
                Obstacle obs = (Obstacle) e;
                if(obs.getDamage() > 0){
                    g2d.setColor(Color.RED); // lasers
                }else {
                    g2d.setColor(Color.GRAY); // walls - gray
                }
            }else if(e instanceof Collectible){
                g2d.setColor(Color.YELLOW); // jewels
            }
            g2d.fillRect((int) e.getX(), (int) e.getY(), e.getWidth(), e.getHeight());
        }

        // Draw a Heads up display so the player knows their progress
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level: " + currentLevel, 10, 20);
        g2d.drawString("Score: " + levelManager.getPlayer().getJewelsCollected(), 10, 40);
    }
}
