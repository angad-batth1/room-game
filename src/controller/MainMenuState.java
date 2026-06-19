package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import utils.Constants;

public class MainMenuState extends GameState {
    private InputHandler input;
    private GameStateManager gsm;

    public MainMenuState(GameStateManager gsm, InputHandler input){
        super(gsm);
        this.input = input;
        this.gsm = gsm;
    }

    @Override
    public void update(){
        // go to game when enter is pressed
        if(input.isKeyDown(KeyEvent.VK_ENTER)){
            input.resetKeys();
            gsm.setState(new PlayState(gsm,input, 1, 0));
        }
    }

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