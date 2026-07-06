package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Constants;

/**
 * This is the level manager class that loads text based levels and spawns the entities inside them.
 * @author Gurangad Batth
 */
public class LevelManager{

    private ArrayList<Entity> currentLevelEntities; // array list to hold all the active game objects
    private Player player; // We will keep a direct reference to player to make GameLoop code easier

    /**
     * This is the constructor for the level manager.
     * It initializes the entity list for the current level.
     */
    public LevelManager(){ 
        currentLevelEntities = new ArrayList<>();
    }

    /**
     * This method loads a level file and converts each map character into an entity.
     * @param filepath the path to the text file representing the level map
     */
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
                    }else if(currentChar == 'J'){
                        currentLevelEntities.add(new Collectible(xPos, yPos, 32, 32, 100));
                    }else if(currentChar == 'P'){
                        player = new Player(xPos, yPos);
                        currentLevelEntities.add(player);
                    }
                }
                row++;
            }
            scanner.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    /**
     * This method gets every active entity in the current level.
     * @return the list of current level entities
     */
    public ArrayList<Entity> getEntities(){
        return currentLevelEntities;
    }

    /**
     * This method gets the player entity for the current level.
     * @return the player of the current level, or null if none exists
     */
    public Player getPlayer(){
        return player;
    }

    /*
    
    Since each character represents a 32x32 pixel block on the screen
    X is a solid wall, P is where the theif drops in, J is a jewel, L is a laser.
    Empty spaces are for air. This way, our textfiles can simply be a map.
    Without writing any code, we can load any number of levels.
    
    */
}
