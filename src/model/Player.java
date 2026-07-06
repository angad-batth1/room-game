package model;
import utils.Constants;

/**
 * This is the player class that handles movement, jumping, gravity, and jewel tracking.
 * @author Gurangad Batth
 */
public class Player extends Entity{

    private double xVelocity;
    private double yVelocity;
    private boolean isStealthActive; // Track if stealth mode is active
    private int jewelsCollected; // Number of jewels collected by player
    private boolean isGrounded; // Is player on the ground (jumping)

    /**
     * This is the constructor for the player.
     * @param x the starting x coordinate of the player
     * @param y the starting y coordinate of the player
     */
    public Player(double x, double y){
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        xVelocity = 0;
        yVelocity = 0;
        isStealthActive = false;
        jewelsCollected = 0;
        isGrounded = false;
    }

    /**
     * This method updates the player position and applies gravity.
     */
    @Override
    public void update(){
        // gravity physics
        yVelocity += Constants.GRAVITY;
        if(yVelocity > Constants.MAX_FALL_SPEED){
            yVelocity = Constants.MAX_FALL_SPEED;
        }

        setX(getX() + xVelocity);
        setY(getY() + yVelocity);

        // Add physics to this game for enhanced math logic and smoothness
        xVelocity = 0; // Reset horizontal velocity after each update

    }

    // Controller methods

    /**
     *  Method to move the player left, sets velocity
     */
    public void moveLeft(){
        xVelocity = -(Constants.RUN_SPEED);
    }

    /**
     * Method to move the player right, sets velocity
     */
    public void moveRight(){
        xVelocity = Constants.RUN_SPEED;
    }

    /**
     * Method to make the player jump, sets velocity and grounded state
     */
    public void jump(){
        if(isGrounded){
            yVelocity = Constants.JUMP_STRENGTH;
            isGrounded = false;
        }
    }

    // Gameplay methods

    /**
     * Method to toggle stealth mode on and off
     */
    public void toggleStealth(){
        isStealthActive = !isStealthActive;
    }

    /**
     * Method to add jewel to players ollection
     */
    public void addJewel(){
        jewelsCollected++;
    }

    // Getters and setters

    /**
     * Method to check if stealth mode is active
     * @return true if stealth mode is active, false otherwise
     */
    public boolean isStealthActive(){
        return isStealthActive;
    }
    
    /**
     * Method to get the number of jewels collected by the player.
     * @return the number of jewels collected
     */
    public int getJewelsCollected(){
        return jewelsCollected;
    }

    /**
     * This method restores the player's jewel count.
     * @param jewelsCollected the number of jewels the player should currently have
     */
    public void setJewelsCollected(int jewelsCollected){
        this.jewelsCollected = Math.max(0, jewelsCollected);
    }

    /**
     * Method to set if the player is grounded (on the ground)
     * @param grounded set the boolean to gounded.
     */
    public void setGrounded(boolean grounded){
        this.isGrounded = grounded;
    }

    /**
     * This method checks whether the player is currently standing on solid ground.
     * @return true if the player is grounded, false otherwise
     */
    public boolean isGrounded(){
        return isGrounded;
    }

    /**
     * This method resets the player's vertical velocity back to zero.
     */
    public void resetYVelocity(){
        this.yVelocity = 0;
    }

    /**
     * This method gets the current vertical velocity of the player.
     * @return the player's vertical velocity
     */
    public double getYVelocity(){
        return this.yVelocity;
    }
}
