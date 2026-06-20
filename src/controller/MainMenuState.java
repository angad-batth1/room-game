package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import utils.Constants;

/**
 * The main menu screen of the game. Displays the title and instructions,
 * and transitions to the PlayState when Enter is pressed.
 */
public class MainMenuState extends GameState {
    private InputHandler input;
    private GameStateManager gsm;

    /**
     * this is the contructor for the main menu state
     * @param gsm the game state manager for transitioning to play state
     * @param input the input handler to detect Enter key press
     */
    public MainMenuState(GameStateManager gsm, InputHandler input){
        super(gsm);
        this.input = input;
        this.gsm = gsm;
    }

    /**
     * Updates the menu state by checking if Enter is pressed to start the game
     */
    @Override
    public void update(){
        // go to level 1 when enter is pressed
        if(input.isKeyDown(KeyEvent.VK_ENTER)){
            input.resetKeys();
            gsm.setState(new PlayState(gsm,input, 1, 0));
        }
    }

    /**
     * Renders the main menu screen with title, instructions, and controls
     * @param g2d the Graphics2D object used for drawing
     */
    @Override
    public void render(Graphics2D g2d){
        // draw background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        // draw Title
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 48));
        g2d.drawString("The Heist", 80, 200);
        // draw the instructions
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 24));
        g2d.drawString("Press [ENTER] to Start", 240, 350);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g2d.drawString("Controls: ARROWS / WASD to Move & Jump", 200, 450);
    }
}