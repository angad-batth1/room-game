package model;

import java.awt.Rectangle;

/**
 * This is the abstract parent entity class for anything that exists physically in the game world.
 * It stores position, dimensions, hitbox data, and a unique id.
 * @author Gurangad Batth
 */
public abstract class Entity {

    // all our entities need a unique ID or numerical value
    private static int nextId = 1;

    private int id;
    private double x;
    private double y;
    private int width;
    private int height;
    private Rectangle hitbox;;

    /**
     * This is the constructor for an entity.
     * @param x the starting x coordinate of the entity
     * @param y the starting y coordinate of the entity
     * @param width the width of the entity
     * @param height the height of the entity
     */
    public Entity(double x, double y, int width, int height) {
        this.id = nextId++;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle((int)x, (int)y, width, height);
    }

    /**
     * This method updates the entity every frame.
     */
    public abstract void update(); 

    /**
     * This method refreshes the hitbox so it matches the entity's current position.
     */
    public void updateHitbox(){
        this.hitbox.setBounds((int)x, (int)y, width, height);
    }

    /**
     * This method gets the current x coordinate.
     * @return the x coordinate of the entity
     */
    public double getX(){
        return x;
    }

    /**
     * This method sets the x coordinate of the entity.
     * @param x the new x coordinate
     */
    public void setX(double x){
        this.x = x;
        updateHitbox();
    }

    /**
     * This method gets the current y coordinate.
     * @return the y coordinate of the entity
     */
    public double getY(){
        return y;
    }

    /**
     * This method sets the y coordinate of the entity.
     * @param y the new y coordinate
     */
    public void setY(double y){
        this.y = y;
        updateHitbox();
    }

    /**
     * This method gets the width of the entity.
     * @return the width of the entity
     */
    public int getWidth(){
        return width;
    }

    /**
     * This method gets the height of the entity.
     * @return the height of the entity
     */
    public int getHeight(){
        return height;
    }

    /**
     * This method gets the hitbox rectangle of the entity.
     * @return the hitbox rectangle
     */
    public Rectangle getHitbox(){
        return hitbox;
    }

    /**
     * This method sets the id of the entity.
     * @param id the new unique id value
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * This method gets the unique id of the entity.
     * @return the entity id
     */
    public int getId(){
        return id;
    }
}
