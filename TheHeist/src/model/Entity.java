import java.awt.Rectangle;

public abstract class Entity {
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

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}