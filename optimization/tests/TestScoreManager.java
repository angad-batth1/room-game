package tests;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ScoreManager;

/**
 * This is a tester class for the high score save and load flow.
 * It verifies score persistence and legacy line parsing.
 * @author Gurangad Batth
 */
public class TestScoreManager {
    private static final String SCORE_PATH_PROPERTY = "roomgame.highscore.path";

    private Path tempDirectory;
    private Path tempScoreFile;

    /**
     * This setup method creates a temporary score file path for each test.
     * @throws IOException if the temporary directory cannot be created
     */
    @Before
    public void setUp() throws IOException{
        tempDirectory = Files.createTempDirectory("room-game-scores");
        tempScoreFile = tempDirectory.resolve("highscores.txt");
        System.setProperty(SCORE_PATH_PROPERTY, tempScoreFile.toString());
    }

    /**
     * This cleanup method removes the temporary score file after each test.
     * @throws IOException if the temporary files cannot be deleted
     */
    @After
    public void tearDown() throws IOException{
        System.clearProperty(SCORE_PATH_PROPERTY);
        Files.deleteIfExists(tempScoreFile);
        Files.deleteIfExists(tempDirectory);
    }

    /**
     * This test checks that saved scores load back in descending order.
     */
    @Test
    public void testSaveAndLoadHighScores(){
        ScoreManager.saveScore(3);
        ScoreManager.saveScore(7);
        ScoreManager.saveScore(5);

        List<Integer> highScores = ScoreManager.loadHighScores();
        assertEquals(Arrays.asList(7, 5, 3), highScores);
    }

    /**
     * This test checks that the loader still understands the older text based score format.
     * @throws IOException if the temporary file cannot be written
     */
    @Test
    public void testLegacyScoreFormatParsing() throws IOException{
        Files.write(
            tempScoreFile,
            Arrays.asList("Heist Score: 12", "invalid line", "Heist Score: 4"),
            StandardCharsets.UTF_8
        );

        List<Integer> highScores = ScoreManager.loadHighScores();
        assertEquals(Arrays.asList(12, 4), highScores);
    }
}
