package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import utils.Constants;

/**
 * displays the game over screen showing the final result win/loss and the player's scor.
 * handles the user input to return to the main menu when Enter is pressed.
 * @author Gurangad Batth
 */
public class GameOverState extends GameState {
    // instance variables
    private InputHandler input;
    private String message;
    private int score;
    private GameStateManager gsm;

    /**
     * constructor for the game over state
     * @param gsm the game state manager for transitioning between states
     * @param input the input handler to detect player input
     * @param message the win/loss message to display
     * @param score the final score to display
     */
    public GameOverState(GameStateManager gsm, InputHandler input, String message, int score){
        super(gsm);
        this.input = input;
        this.message = message;
        this.score = score;
        this.gsm = gsm;
    }

    /**
     * updates the game state by checking for Enter key press to return to main menu
     */
    @Override
    public void update(){
        // Return to the main menu when enter is pressed
        if(input.isKeyDown(KeyEvent.VK_ENTER)){
            input.resetKeys(); // wipe enter key before transition
            gsm.setState(new MainMenuState(gsm, input));
        }
    }

    /**
     * renders the game over screen with the final message, score, and the instructions
     * @param g2d the Graphics2D object used for drawing
     */
    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        // draw the dynamic Win/Loss message (Red if busted, Green if successful)
        if(message.contains("BUSTED")) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.GREEN);
        }
        g2d.setFont(new Font("Monospaced", Font.BOLD, 48));
        g2d.drawString(message, 150, 200);
        // draw Score
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 32));
        g2d.drawString("Jewels Stolen: " + score, 250, 300);
        // draw Prompt
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.drawString("Press [ENTER] to return to Headquarters", 160, 450);
    }
}