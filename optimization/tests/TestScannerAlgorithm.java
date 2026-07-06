package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import utils.ScannerAlgorithm;

/**
 * This is a tester class for the scanner algorithm
 * Unfortunately, the algo did not make it to the final product but it showcases recursion and JUnit testing
 * @author Gurangad Batth
 */
public class TestScannerAlgorithm {
    
    /**
     * This test checks that the recursive reveal fills every connected fake wall tile.
     */
    @Test
    public void testFloorFillReveal(){

        // First lets make a mini-map, then we'll add 2's
        int[][] map = {
            {1, 1, 1, 1, 1},
            {1, 2, 2, 1, 1},
            {1, 2, 1, 1, 1},
            {1, 1, 1, 1, 1},
        };

        // trigger the scanner on the first fake wall at coordinate (row 1, col 1)
        ScannerAlgorithm.revealHiddenRoom(map, 1, 1);
        // The fake walls should now be '3' (revealed)
        assertEquals(3, map[1][1]);
        assertEquals(3, map[1][2]);
        assertEquals(3, map[2][1]);
        // Solid walls remail untouched
        assertEquals(1, map[2][2]);
    }
}
