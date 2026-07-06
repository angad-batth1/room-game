package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import utils.Constants;

/**
 * This is the game over state that displays the result screen and lets the player return to the main menu.
 * @author Gurangad Batth
 */
public class GameOverState extends GameState {

    private InputHandler input;
    private String message;
    private int score;
    private GameStateManager gsm;

    /**
     * This is the constructor for the game over state.
     * @param gsm the game state manager used for screen transitions
     * @param input the input handler used to detect Enter
     * @param message the message shown on the game over screen
     * @param score the final jewel score shown to the player
     */
    public GameOverState(GameStateManager gsm, InputHandler input, String message, int score){
        super(gsm);
        this.input = input;
        this.message = message;
        this.score = score;
        this.gsm = gsm;
    }

    /**
     * This method checks if the player wants to return to the main menu.
     */
    @Override
    public void update(){
        if(input.isKeyDown(KeyEvent.VK_ENTER)){
            input.resetKeys();
            gsm.setState(new MainMenuState(gsm, input));
        }
    }

    /**
     * This method renders the game over screen, result message, and final score.
     * @param g2d the graphics object used to draw the screen
     */
    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        // draw Message
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 48));
        g2d.drawString(message, 220, 200);
        // draw score
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 32));
        g2d.drawString("Jewels Stolen: " + score, 250, 300);
        // draw Prompt
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.drawString("Press [ENTER] to return to Headquarters", 160, 450);
    }
}
