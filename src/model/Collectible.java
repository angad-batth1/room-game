package model;

/**
 * This is the collectible class that represents jewels the player can pick up for score.
 * @author Gurangad Batth
 */
public class Collectible extends Entity{
    private int scoreValue;

    /**
     * This is the constructor for a collectible.
     * @param x the x position of the collectible
     * @param y the y position of the collectible
     * @param width the width of the collectible
     * @param height the height of the collectible
     * @param scoreValue the amount of score granted when collected
     */
    public Collectible(double x, double y, int width, int height, int scoreValue){
        super(x, y, width, height);
        this.scoreValue = scoreValue;
    }

    /**
     * This method updates the collectible.
     * Right now collectibles stay still, so no logic is needed.
     */
    @Override
    public void update(){
    }

    /**
     * This method gets the score value of the collectible.
     * @return the score value awarded by this collectible
     */
    public int getScoreValue(){
        return scoreValue;
    }
}
