package controller;
import java.awt.*;
import java.awt.event.*;
import model.*;
import utils.PhysicsUtils;
import java.util.Iterator;

public class PlayState extends GameState{
    // instances
    private LevelManager levelManager;
    private InputHandler input;
    private GameStateManager gsm;

    // constructor
    public PlayState(GameStateManager gsm, InputHandler input){
        super(gsm);
        this.input = input;
        // Initialize the level and load the map text file
        levelManager = new LevelManager();
        levelManager.loadLevel("assets/levels/level1.txt");
    }

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
            if(e instanceof model.Obstacle){
                model.Obstacle obs = (model.Obstacle) e;
                // did we hit a laser
                if(obs.getDamage() > 0 && PhysicsUtils.checkCollision(player, obs)){
                    System.out.println("YOU HAVE DIED LOL! Restarting level...");
                    gsm.setState(new PlayState(gsm, input)); // instantly reload the state
                    return;
                }
                // did we land on a wall
                if (PhysicsUtils.isLandingOn(player, obs)){
                    player.setY(obs.getY() - player.getHeight()); // Snap to the top of the block
                    player.resetYVelocity(); // Stop gravity
                    player.setGrounded(true); // Allow jumping again
                    player.updateHitbox(); // Sync the collision box instantly
                }
            } 
            // collectibles and jewels
            else if (e instanceof model.Collectible) {
                if (PhysicsUtils.checkCollision(player, e)) {
                    player.addJewel();
                    it.remove(); // Safely delete the jewel from the map!
                    System.out.println("Jewels Collected: " + player.getJewelsCollected());
                }
            }
        }
    }

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
            }else if (e instanceof Obstacle){
                g2d.setColor(Color.RED); // walls and lasers
            }else if (e instanceof Collectible){
                g2d.setColor(Color.YELLOW); // jewels
            }
            g2d.fillRect((int) e.getX(), (int) e.getY(), e.getWidth(), e.getHeight());
        }
    }
}
