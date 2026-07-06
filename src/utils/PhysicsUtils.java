package utils;

import model.Entity;

/**
 * This is the physics utility class that stores shared collision and landing helper logic.
 * @author Gurangad Batth
 */
public class PhysicsUtils {

    // We will make a static method, so it can be used without an instance
    // This method will check the collision of two entities, a, and b

    /**
     * Checks if the hitboxes of two entities intersect, indicating a collision.
     * @param a Entity a being checked
     * @param b Entity b being checked
     * @return true if intersecting, false otherwise
     */
    public static boolean checkCollision(Entity a, Entity b){
        // quick safety
        if(a == null || b == null){
            return false;
        }
        // Use the rectangle class's built in intersection method to check if the hitboxes of a and b intersect
        return a.getHitbox().intersects(b.getHitbox());
    }

    /**
     * Determine if an Entity is landing on top of another Entity based on their positions and hitboxes.
     * This is important for platformer jump logic so player doesnt stick to the bottom of the platforms.
     * @param fallingEntity The entity that is falling and potentially landing on the platform
     * @param platform The entity that is being checked as a potential landing platform
     * @return True if the falling Entity is landing on top of the platform, false if not
     */
    public static boolean isLandingOn(Entity fallingEntity, Entity platform){
        if(!checkCollision(fallingEntity, platform)){ // If they are not colliding at all, then we can return false immediately
            return false; 
        }
        double entityBottom = fallingEntity.getY() + fallingEntity.getHeight(); // Calculate the bottom edge of the falling entity
        double platformTop = platform.getY(); // The top edge of the platform is just its y coordinate.
        return entityBottom >= platformTop && fallingEntity.getY() < platformTop; // check if the bottom of the entity is below the top of the platform, but the top of the entity is above the top of the platform, indicating a landing on top scenario.
    }
}
