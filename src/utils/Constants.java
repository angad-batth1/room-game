package utils;

/**
 * This is the constants class that stores shared screen and player values used across the game.
 * @author Gurangad Batth
 */
public class Constants{
    public static final String WINDOW_TITLE = "The Heist";
    public static final int TARGET_FPS = 60;
    public static final int TILE_SIZE = 32;
    public static final int HIGH_SCORE_LIMIT = 5;
    public static final double WINDOW_SCALE = 1.5;

    // Player physicals and dimentions
    public static final int PLAYER_WIDTH = TILE_SIZE;
    public static final int PLAYER_HEIGHT = TILE_SIZE;
    public static final double GRAVITY = 0.5;
    public static final double MAX_FALL_SPEED = 12.0;
    public static final double JUMP_STRENGTH = -10.0;
    public static final double RUN_SPEED = 5.0;

    // Screen dimentions ( we will use in Renderer and GameWindow )
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int WINDOW_WIDTH = (int) (SCREEN_WIDTH * WINDOW_SCALE);
    public static final int WINDOW_HEIGHT = (int) (SCREEN_HEIGHT * WINDOW_SCALE);

    // Image asset paths
    public static final String MENU_BACKGROUND_PATH = "assets/images/menu-background.png";
    public static final String RESULT_BACKGROUND_PATH = "assets/images/result-background.png";
    public static final String GAME_BACKGROUND_PATH = "assets/images/game-background.png";
    public static final String PLAYER_SPRITE_PATH = "assets/images/player.png";
    public static final String WALL_SPRITE_PATH = "assets/images/wall.png";
    public static final String HAZARD_SPRITE_PATH = "assets/images/hazard.png";
    public static final String JEWEL_SPRITE_PATH = "assets/images/jewel.png";
}
