package view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import controller.GameLoop;
import controller.GameStateManager;
import controller.InputHandler;
import controller.MainMenuState;
import utils.Constants;

/**
 * This is the main game window class that builds the JFrame and starts the game systems.
 * @author Gurangad Batth
 */
public class GameWindow{
    // Our only instance is the JFrame
    private final JFrame frame;

    /**
     * This is the constructor for the game window.
     * It sets up the frame, renderer, input, first state, and game loop.
     */
    public GameWindow(){
        // Initialize the JFrame, set size, close operation.
        frame = new JFrame(Constants.WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Last year, this was not present and allowed people to stretch the window
        frame.setLocationRelativeTo(null); // Centers the window on the computer monitor

        // Instead of doing frame.add with a method, lets use renderer class to add our drawing canvas to this window
        Renderer renderer = new Renderer();
        renderer.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        InputHandler input = new InputHandler();
        GameStateManager gsm = new GameStateManager();
        renderer.setGSM(gsm);

        // add the keyboard listeners to the canvas
        renderer.addKeyListener(input);

        // load the first screen into the stack
        gsm.pushState(new MainMenuState(gsm, input));


        // add canvas to window and show
        frame.setContentPane(renderer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        SwingUtilities.invokeLater(renderer::requestFocusInWindow);

        // start the game
        GameLoop gameLoop = new GameLoop(gsm, renderer);
        gameLoop.start();
    }
}
