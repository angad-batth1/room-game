package model;

public class Collectible extends Entity{
    private int scoreValue;
    public Collectible(double x, double y, int width, int height, int scoreValue){
        super(x, y, width, height);
        this.scoreValue = scoreValue;
    }

    @Override
    public void update(){
        // Static collectibles dont move, no update logic needed
    }

    public int getScoreValue(){
        return scoreValue;
    
    }
}