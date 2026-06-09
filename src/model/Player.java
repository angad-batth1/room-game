package model;
import utils.Constants;

public class Player extends Entity{
    // Instane variables for the player class
    private double xVelocity;
    private double yVelocity;
    private boolean isStealthActive; // Track if stealth mode is active
    private int jewelsCollected; // Number of jewels collected by player
    private boolean isGrounded; // Is player on the ground (jumping)

    /**
     * Constructor for the player class, initializes the player at a given position
     * @param x the x coordinate to initialize the player at
     * @param y the y coordinate to initialize the player at
     */
    public Player(double x, double y){
        // Call superlass constructor to initialize position and hitbox
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.isStealthActive = false;
        this.jewelsCollected = 0;
        this.isGrounded = false;
    }

    /**
     * This fulfills the abstract requirement from entity
     * This runs every single frame of the game loop.
     */
    @Override
    public void update(){
        // apply gravity to y velocity
        yVelocity += Constants.GRAVITY;
        if(yVelocity > Constants.MAX_FALL_SPEED){
            yVelocity = Constants.MAX_FALL_SPEED;
        }
        // apply velocity to position
        setX(getX() + xVelocity);
        setY(getY() + yVelocity);

        updateHitbox(); // Update the hitbox position after moving.

        xVelocity = 0; // Reset horizontal velocity after each update

    }

    // TODO: add controller methods and getters/setter.
    // moveLeft, moveRight, jump, toggleStealth, addJewel, isStealthActive, getJewelsCollected, setGrounded.
}