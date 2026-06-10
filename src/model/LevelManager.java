package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Constants;

public class LevelManager{

    private ArrayList<Entity> currentLevelEntities; // array list to hold all the active game objects
    private Player player; // We will keep a direct reference to player to make GameLoop code easier

    public LevelManager(){ 
        currentLevelEntities = new ArrayList<>();
    }

    public void loadLevel(String filepath){
        // Clear old entities if we are loading a new level
        currentLevelEntities.clear();
        player = null;
        // Now we can read the file using the try-catch block
        try{

            File levelFile = new File(filepath);
            Scanner scanner = new Scanner(levelFile);
            
            int row = 0;
            // Read the file line by line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                // Loop through each character in the current line
                for(int i = 0; i < line.length(); i++){
                    char currentChar = line.charAt(i);
                    // convert them to grid coordinate, size relative to player
                    double xPos = i*Constants.PLAYER_WIDTH;
                    double yPos = row*Constants.PLAYER_HEIGHT;
                    // Spawn objects based on the text character
                    if(currentChar == 'X'){
                        currentLevelEntities.add(new Obstacle(xPos, yPos, 32, 32, 0));
                    }else if(currentChar == 'L'){
                        currentLevelEntities.add(new Obstacle(xPos, yPos, 32, 32, 10));

                    }
                }
            }
        }
    }
}