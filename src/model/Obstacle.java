package model;

/**
 * This is the obstacle class that represents walls, platforms, and damaging hazards.
 * @author Gurangad Batth
 */
public class Obstacle extends Entity{
    // Damage instance
    private int damage;

    /**
     * This is the constructor for an obstacle.
     * @param x the x position of the obstacle
     * @param y the y position of the obstacle
     * @param width the width of the obstacle
     * @param height the height of the obstacle
     * @param damage the damage value of the obstacle
     */
    public Obstacle(double x, double y, int width, int height, int damage){
        super(x, y, width, height);
        this.damage = damage;
    }

    /**
     * This method updates the obstacle.
     * Obstacles are static right now so they do not need movement logic.
     */
    @Override
    public void update(){
        // Static walls and traps dont move, no update logic needed
        // If I decide to implement guards later, their patrol math will go here.
    }

    /**
     * This method gets the damage value of the obstacle.
     * @return the damage value of this obstacle
     */
    public int getDamage(){
        return damage;
    }

}
