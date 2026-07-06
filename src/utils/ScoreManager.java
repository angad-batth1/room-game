package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the score manager class that saves completed run scores to the highscores file.
 * @author Gurangad Batth
 */
public class ScoreManager {
    private static final String OVERRIDE_PATH_PROPERTY = "roomgame.highscore.path";
    private static final Path LEGACY_HIGHSCORE_FILE = Path.of("assets", "data", "highscores.txt");
    private static final Path FALLBACK_HIGHSCORE_FILE = Path.of(
        System.getProperty("user.home"),
        ".the-heist",
        "highscores.txt"
    );
    private static final Pattern SCORE_PATTERN = Pattern.compile("-?\\d+");

    /**
     * Appends a new score to the highscores.txt file.
     * @param score The total jewels collected by the player.
     */
    public static void saveScore(int score){
        Path filePath = resolveHighScoreFile();
        try{
            Files.createDirectories(filePath.getParent());
            Files.writeString(
                filePath,
                formatScore(score) + System.lineSeparator(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            System.out.println("Score successfully saved to " + filePath.toAbsolutePath());
        }catch(IOException e){
            System.out.println("failed to save high score");
            e.printStackTrace();
        }
    }

    /**
     * This method loads every saved score and sorts them from highest to lowest.
     * @return the saved score list in descending order
     */
    public static List<Integer> loadHighScores(){
        Path filePath = resolveHighScoreFile();
        if(!Files.exists(filePath)){
            return Collections.emptyList();
        }

        List<Integer> highScores = new ArrayList<>();
        try{
            for(String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)){
                Integer parsedScore = parseScore(line);
                if(parsedScore != null){
                    highScores.add(parsedScore);
                }
            }
        }catch(IOException e){
            System.out.println("failed to read high scores");
            e.printStackTrace();
            return Collections.emptyList();
        }

        highScores.sort(Collections.reverseOrder());
        if(highScores.size() > Constants.HIGH_SCORE_LIMIT){
            return new ArrayList<>(highScores.subList(0, Constants.HIGH_SCORE_LIMIT));
        }
        return highScores;
    }

    /**
     * This method gets the file path currently being used for saved scores.
     * @return the resolved high score file path
     */
    public static Path getHighScoreFilePath(){
        return resolveHighScoreFile();
    }

    /**
     * This helper method formats a single score line before it is saved to disk.
     * @param score the score being saved
     * @return the formatted score line
     */
    private static String formatScore(int score){
        return "Heist Score: " + score;
    }

    /**
     * This helper method parses a score value out of a saved line.
     * @param line the raw line read from the score file
     * @return the parsed score, or null if the line does not contain a score
     */
    private static Integer parseScore(String line){
        Matcher matcher = SCORE_PATTERN.matcher(line);
        Integer parsedScore = null;
        while(matcher.find()){
            parsedScore = Integer.parseInt(matcher.group());
        }
        return parsedScore;
    }

    /**
     * This helper method resolves the best high score file path for local development or shipped builds.
     * @return the file path that should be used for saved scores
     */
    private static Path resolveHighScoreFile(){
        String overridePath = System.getProperty(OVERRIDE_PATH_PROPERTY);
        if(overridePath != null && !overridePath.isBlank()){
            return Path.of(overridePath);
        }
        if(Files.exists(LEGACY_HIGHSCORE_FILE.getParent())){
            return LEGACY_HIGHSCORE_FILE;
        }
        return FALLBACK_HIGHSCORE_FILE;
    }
}
