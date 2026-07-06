package utils;

/**
 * This is the constants class that stores shared screen and player values used across the game.
 * @author Gurangad Batth
 */
public class Constants{
    // Player physicals and dimentions
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;
    public static final double GRAVITY = 0.5;
    public static final double MAX_FALL_SPEED = 12.0;
    public static final double JUMP_STRENGTH = -10.0;
    public static final double RUN_SPEED = 5.0;

    // Screen dimentions ( we will use in Renderer and GameWindow )
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
}
