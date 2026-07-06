package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.List;
import utils.AssetLoader;
import utils.Constants;
import utils.ScoreManager;

/**
 * This is the main menu state of the game.
 * It draws the title screen and transitions to gameplay when Enter is pressed.
 * @author Gurangad Batth
 */
public class MainMenuState extends GameState {
    private static final BufferedImage MENU_BACKGROUND = AssetLoader.loadImage(Constants.MENU_BACKGROUND_PATH);

    private final InputHandler input;
    private final List<Integer> highScores;

    /**
     * this is the contructor for the main menu state
     * @param gsm the game state manager for transitioning to play state
     * @param input the input handler to detect Enter key press
     */
    public MainMenuState(GameStateManager gsm, InputHandler input){
        super(gsm);
        this.input = input;
        highScores = ScoreManager.loadHighScores();
    }

    /**
     * Updates the menu state by checking if Enter is pressed to start the game.
     */
    @Override
    public void update(){
        // go to level 1 when enter is pressed
        if(input.consumeKeyPress(KeyEvent.VK_ENTER)){
            gsm.setState(new PlayState(gsm,input, 1, 0));
        }
    }

    /**
     * Renders the main menu screen with title, instructions, and controls.
     * @param g2d the Graphics2D object used for drawing
     */
    @Override
    public void render(Graphics2D g2d){
        drawMenuBackground(g2d);
        drawShadowedCenteredString(
            g2d,
            Constants.WINDOW_TITLE,
            160,
            new Font("Monospaced", Font.BOLD, 54),
            new Color(188, 83, 67),
            new Color(130, 125, 118, 120)
        );
        drawCenteredString(g2d, "Press [ENTER] to Start", 275, new Font("Monospaced", Font.PLAIN, 24), new Color(70, 66, 62));
        drawCenteredString(
            g2d,
            "Controls: ARROWS / WASD to Move   SPACE / W to Jump",
            322,
            new Font("Monospaced", Font.PLAIN, 16),
            new Color(82, 79, 74)
        );
        drawCenteredString(
            g2d,
            "Press [ESC] during a heist to pause, restart, or retreat.",
            350,
            new Font("Monospaced", Font.PLAIN, 16),
            new Color(108, 103, 96)
        );
        drawCenteredString(
            g2d,
            "Collect every jewel. Avoid hazards. Clear all 6 rooms.",
            378,
            new Font("Monospaced", Font.PLAIN, 16),
            new Color(121, 118, 112)
        );

        drawPanel(g2d, 210, 408, 380, 130, new Color(249, 245, 239, 215), new Color(203, 195, 186, 190));
        drawCenteredString(g2d, "Top Heists", 437, new Font("Monospaced", Font.BOLD, 20), new Color(167, 132, 67));

        if(highScores.isEmpty()){
            drawCenteredString(
                g2d,
                "No completed heists saved yet",
                478,
                new Font("Monospaced", Font.PLAIN, 16),
                new Color(78, 73, 69)
            );
            return;
        }

        int displayCount = Math.min(3, highScores.size());
        for(int i = 0; i < displayCount; i++){
            drawCenteredString(
                g2d,
                (i + 1) + ". " + highScores.get(i) + " jewels",
                478 + (i * 26),
                new Font("Monospaced", Font.PLAIN, 16),
                new Color(78, 73, 69)
            );
        }
    }

    /**
     * This helper method draws a centered string at a given vertical position.
     * @param g2d the graphics object used for drawing
     * @param text the text being displayed
     * @param y the y coordinate for the baseline of the text
     * @param font the font used to draw the text
     * @param color the color used to draw the text
     */
    private void drawCenteredString(Graphics2D g2d, String text, int y, Font font, Color color){
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = (Constants.SCREEN_WIDTH - metrics.stringWidth(text)) / 2;
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    /**
     * This helper method draws a shadowed centered string for larger title text.
     * @param g2d the graphics object used for drawing
     * @param text the text being displayed
     * @param y the y coordinate for the text baseline
     * @param font the font used to draw the text
     * @param color the main text color
     * @param shadowColor the shadow color behind the text
     */
    private void drawShadowedCenteredString(Graphics2D g2d, String text, int y, Font font, Color color, Color shadowColor){
        drawCenteredString(g2d, text, y + 4, font, shadowColor);
        drawCenteredString(g2d, text, y, font, color);
    }

    /**
     * This helper method draws the main menu background with gradients and ambient lights.
     * @param g2d the graphics object used for drawing
     */
    private void drawMenuBackground(Graphics2D g2d){
        if(MENU_BACKGROUND != null){
            g2d.drawImage(MENU_BACKGROUND, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
            g2d.setColor(new Color(255, 255, 255, 62));
        }
        else{
            g2d.setPaint(new GradientPaint(0, 0, new Color(240, 240, 236), 0, Constants.SCREEN_HEIGHT, new Color(214, 214, 208)));
        }
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    /**
     * This helper method draws a rounded panel with an outline.
     * @param g2d the graphics object used for drawing
     * @param x the x coordinate of the panel
     * @param y the y coordinate of the panel
     * @param width the panel width
     * @param height the panel height
     * @param fillColor the panel fill color
     * @param borderColor the panel border color
     */
    private void drawPanel(Graphics2D g2d, int x, int y, int width, int height, Color fillColor, Color borderColor){
        g2d.setColor(fillColor);
        g2d.fillRoundRect(x, y, width, height, 24, 24);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(x, y, width, height, 24, 24);
    }
}
