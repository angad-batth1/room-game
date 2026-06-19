package model;

import java.awt.Rectangle;

/**
 * @author Gurangad Batth
 */
public abstract class Entity {
    // Static counter for all entities
    // Exists at class level, not object level, to share data
    private static int nextId = 1;
    // Intance variables for all entities in the game. 
    private int id;
    private double x;
    private double y;
    private int width;
    private int height;
    private Rectangle hitbox;;

    /**
     * Constructor for the Entity class, all physical objets in the game
     * @param id used to identify the type of entity, for example, 0, 1, 2, 3, 4 for the different types
     * @param x the x-coordinate of the entity
     * @param y the y-coordinate of the entity
     * @param width the width of the entity
     * @param height the height of the entity
     */
    public Entity(double x, double y, int width, int height) {
        this.id = nextId++; // Assign the current nextid to the specific object, then inrement it for next one.
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // Initialize the collision box exactly where the entity is
        this.hitbox = new Rectangle((int) x, (int) y, width, height); 
    }

    /**
     * This is the abstrat update method.
     * Each Subclass of Entity will implement its own uniqur logic for this.
     */
    public abstract void update(); 

    /**
     * This method syncs the collision hitbox with the entity's current x/y coordinate
     * We will call this inside the update methof of moving sublasses like Player.
     */
    public void updateHitbox(){
        this.hitbox.setLocation((int) x, (int) y);
    }

    /**
     * Getter for the x coordinate of the entity
     * @return the x coordinate
     */
    public double getX(){
        return x;
    }

    /**
     * Setter for the x coordinate of the entity
     * @param x the new x coordinate to set
     */
    public void setX(double x){
        this.x = x;
    }

    /**
     * Getter for the y coordinate of the entity
     * @return the y coordinate
     */
    public double getY(){
        return y;
    }

    /**
     * Setter for the y coordinate of the entity
     * @param y the new y coordinate to set
     */
    public void setY(double y){
        this.y = y;
    }

    /**
     * Getter for the width of the entity
     * @return the width of the entity
     */
    public int getWidth(){
        return width;
    }

    /**
     * Getter for the height of the entity
     * @return the height of the entity
     */
    public int getHeight(){
        return height;
    }

    /**
     * Getter for the hitbox of the entity, used for collision detection
     * @return the hitbox of the entity
     */
    public Rectangle getHitbox(){
        return hitbox;
    }

    /**
     * Setter for the id of the entity, used to identify the type of entity
     * @param id the new id to set
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Getter for the id of the entity, used to identify the type of entity
     * @return the id of the entity
     */
    public int getId(){
        return id;
    }
}