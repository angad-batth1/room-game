package controller;

import java.awt.Graphics2D;
/*
Let us implement a game state using stack, when the game boots up,
we push() the main menu onto the stack. If they click play, we push()
to level 1 state. If they hit escape, we push() a pause menu, if they
resume, we pop() the pause menu off, and then the game continues.

To do this, we must first implement gamestate, then we can implement gamestatemanager.
*/

public abstract class GameState {
    private GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    // every screen must have a way to update its math and draw its graphics
    public abstract void update();
    public abstract void render(Graphics2D g2d);
    
}
