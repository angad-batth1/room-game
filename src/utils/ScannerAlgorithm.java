package utils;

/*
1 = Solid Wall
2 = Fake Wall (Hidden)
3 = Revealed Wall (Transparent)
*/

public class ScannerAlgorithm{
    
    public static void revealHiddenRoom(int[][] grid, int row, int col){
        // Base Case: Stop if the scan goes out of the map boundaries
        if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length){
            return;
        }
        // Base Case 2: Stop if the tile is not a fake wall (solid wall or empty air)
        if(grid[row][col] != 2){
            return;
        }
        grid[row][col] = 3; // If isnt a solid wall, and its in bounds, its fake

        // Scan north, south, west, east, branch out in all directions
        revealHiddenRoom(grid, row - 1, col); // north
        revealHiddenRoom(grid, row + 1, col); // south
        revealHiddenRoom(grid, row, col -1); // west
        revealHiddenRoom(grid, row, col +1); // east
    }
}