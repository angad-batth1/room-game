package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import utils.Constants;

public class GameOverState extends GameState {
    private InputHandler input;
    private String message;
    private int score;
    private GameStateManager gsm;

    public GameOverState(GameStateManager gsm, InputHandler input, String message, int score){
        super(gsm);
        this.input = input;
        this.message = message;
        this.score = score;
        this.gsm = gsm;
    }

    @Override
    public void update(){
        // Rreturn to the main menu when enter is pressed
        if(input.isKeyDown(KeyEvent.VK_ENTER)){
            input.resetKeys(); // wipe enter key before transition
            gsm.setState(new MainMenuState(gsm, input));
        }
    }

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