package utils;

import java.io.*;

/**
 * This is the score manager class that saves completed run scores to the highscores file.
 * @author Gurangad Batth
 */
public class ScoreManager {
    // this is the path to the data file.
    private static final String HIGHSCORE_FILE = "assets/data/highscores.txt";

    /**
     * Appends a new score to the highscores.txt file.
     * @param score The total jewels collected by the player.
     */
    public static void saveScore(int score){
        try{
            // true tells it to append, and not overwrite.
            FileWriter fileWriter = new FileWriter(HIGHSCORE_FILE, true); // source is highschool file
            PrintWriter printWriter = new PrintWriter(fileWriter); // print writer for commands
            // write score to file
            printWriter.println("Heist Score: " + score);
            // save and close
            printWriter.close();
            // Testing and confirmation
            System.out.println("Score successfully saved to " + HIGHSCORE_FILE);
        }catch(IOException e){
            System.out.println("failed");
            e.printStackTrace();
        }
    }
}
