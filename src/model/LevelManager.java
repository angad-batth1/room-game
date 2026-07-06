package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
        try(BufferedReader reader = createLevelReader(filepath)){
            int row = 0;
            // Read the file line by line
            String line;
            while((line = reader.readLine()) != null){
                // Loop through each character in the current line
                for(int i = 0; i < line.length(); i++){
                    char currentChar = line.charAt(i);
                    // convert them to grid coordinate, size relative to player
                    double xPos = i * Constants.TILE_SIZE;
                    double yPos = row * Constants.TILE_SIZE;
                    // Spawn objects based on the text character
                    if(currentChar == 'X'){
                        currentLevelEntities.add(new Obstacle(xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE, 0));
                    }else if(currentChar == 'L'){
                        currentLevelEntities.add(new Obstacle(xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE, 10));
                    }else if(currentChar == 'J'){
                        currentLevelEntities.add(new Collectible(xPos, yPos, Constants.TILE_SIZE, Constants.TILE_SIZE, 100));
                    }else if(currentChar == 'P'){
                        player = new Player(xPos, yPos);
                        currentLevelEntities.add(player);
                    }
                }
                row++;
            }
        }catch(IOException e){
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

    /**
     * This helper method opens a level file from the filesystem first and then from bundled resources.
     * @param filepath the requested level file path
     * @return a buffered reader for the level file
     * @throws IOException if the level file cannot be found or opened
     */
    private BufferedReader createLevelReader(String filepath) throws IOException{
        Path path = Path.of(filepath);
        if(Files.exists(path)){
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        }

        InputStream inputStream = LevelManager.class.getClassLoader().getResourceAsStream(filepath);
        if(inputStream == null && filepath.startsWith("assets/")){
            inputStream = LevelManager.class.getClassLoader().getResourceAsStream(filepath.substring("assets/".length()));
        }

        if(inputStream == null){
            throw new FileNotFoundException("Could not find level file: " + filepath);
        }

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /*
    
    Since each character represents a 32x32 pixel block on the screen
    X is a solid wall, P is where the theif drops in, J is a jewel, L is a floating hazard.
    Empty spaces are for air. This way, our textfiles can simply be a map.
    Without writing any code, we can load any number of levels.
    
    */
}
