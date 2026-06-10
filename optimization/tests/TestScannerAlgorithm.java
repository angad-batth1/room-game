package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import utils.ScannerAlgorithm;

public class TestScannerAlgorithm {
    
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
