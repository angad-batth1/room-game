package model;

public class Obstacle extends Entity{
    // Damage instance
    private int damage;
    public Obstacle(double x, double y, int width, int height, int damage){
        super(x, y, width, height);
        this.damage = damage;
    }

    @Override
    public void update(){
        // Static walls and traps dont move, no update logic needed
        // If I decide to implement guards later, their patrol math will go here.
    }

    public int getDamage(){
        return damage;
    }

}