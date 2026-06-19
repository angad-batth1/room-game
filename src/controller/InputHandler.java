package controller;

import java.awt.event.*;
import javax.swing.SwingUtilities;

/**
 * @author Mr. Lamont
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    
    private boolean[] keys = new boolean[256];
    private boolean[] mouseButtons = new boolean[4]; 
    private int mouseX, mouseY;

    // Keyboard questions we can ask
    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) return keys[keyCode];
        return false;
    }

    // Questions we can ask about the mouse
    public int getMouseX() { 
        return mouseX; 
    }
    
    public int getMouseY() { 
        return mouseY; 
    }

    public boolean isLeftMouseDown() { 
        return mouseButtons[1]; 
    }
    
    public boolean isMiddleMouseDown() { 
        return mouseButtons[2]; 
    }
    public boolean isRightMouseDown() { 
        return mouseButtons[3]; 
    }

    // Get the mouse button status
    @Override
    public void mousePressed(MouseEvent e) {
        int b = e.getButton();
        if (b > 0 && b < mouseButtons.length) {
            mouseButtons[b] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int b = e.getButton();
        if (b > 0 && b < mouseButtons.length) {
            mouseButtons[b] = false;
        }
    }

    // Get the mouse position
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }


    // Keyboard button status
    public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() < 256){
            keys[e.getKeyCode()] = true;     
        } 
    }
    public void keyReleased(KeyEvent e) { 
        if (e.getKeyCode() < 256){
            keys[e.getKeyCode()] = false; 
        }
        
    }

    public void resetKeys(){
        for(int i = 0; i < keys.length; i++){
            keys[i] = false;
        }
    }

    // Unused mandatory methods
    public void keyTyped(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}