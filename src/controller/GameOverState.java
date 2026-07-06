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
 * This is the game over state that displays the result screen and lets the player return to the main menu.
 * @author Gurangad Batth
 */
public class GameOverState extends GameState {
    private static final BufferedImage RESULT_BACKGROUND = AssetLoader.loadImage(Constants.RESULT_BACKGROUND_PATH);

    private final InputHandler input;
    private final String message;
    private final int score;
    private final List<Integer> highScores;

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
        highScores = ScoreManager.loadHighScores();
    }

    /**
     * This method checks if the player wants to return to the main menu.
     */
    @Override
    public void update(){
        if(input.consumeKeyPress(KeyEvent.VK_ENTER)){
            gsm.setState(new MainMenuState(gsm, input));
        }
    }

    /**
     * This method renders the game over screen, result message, and final score.
     * @param g2d the graphics object used to draw the screen
     */
    @Override
    public void render(Graphics2D g2d){
        drawResultBackground(g2d);
        Color headlineColor = message.contains("Complete") ? new Color(103, 153, 77) : new Color(188, 83, 67);
        drawShadowedCenteredString(
            g2d,
            message,
            175,
            new Font("Monospaced", Font.BOLD, 42),
            headlineColor,
            new Color(120, 114, 105, 110)
        );
        drawCenteredString(g2d, "Jewels Stolen: " + score, 248, new Font("Monospaced", Font.BOLD, 28), new Color(170, 132, 67));

        drawPanel(g2d, 215, 290, 370, 150, new Color(249, 245, 239, 215), new Color(202, 193, 184, 190));
        if(highScores.isEmpty()){
            drawCenteredString(g2d, "No saved heists yet", 365, new Font("Monospaced", Font.PLAIN, 18), new Color(76, 72, 68));
        }
        else{
            drawCenteredString(
                g2d,
                "Best Heist: " + highScores.get(0) + " jewels",
                340,
                new Font("Monospaced", Font.PLAIN, 18),
                new Color(76, 72, 68)
            );

            int displayCount = Math.min(3, highScores.size());
            for(int i = 0; i < displayCount; i++){
                drawCenteredString(
                    g2d,
                    (i + 1) + ". " + highScores.get(i) + " jewels",
                    374 + (i * 24),
                    new Font("Monospaced", Font.PLAIN, 18),
                    new Color(96, 91, 86)
                );
            }
        }

        drawCenteredString(
            g2d,
            "Press [ENTER] to return to Headquarters",
            505,
            new Font("Monospaced", Font.PLAIN, 18),
            new Color(73, 68, 64)
        );
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
     * This helper method draws a shadowed centered string for the result headline.
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
     * This helper method draws the result background with a success or failure tint.
     * @param g2d the graphics object used for drawing
     */
    private void drawResultBackground(Graphics2D g2d){
        Color topColor = new Color(241, 241, 236);
        Color bottomColor = new Color(214, 214, 209);

        if(RESULT_BACKGROUND != null){
            g2d.drawImage(RESULT_BACKGROUND, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
            topColor = new Color(255, 255, 255, 58);
            bottomColor = new Color(234, 232, 226, 74);
        }
        g2d.setPaint(new GradientPaint(0, 0, topColor, 0, Constants.SCREEN_HEIGHT, bottomColor));
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    /**
     * This helper method draws a rounded result panel with an outline.
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
