package tests;
import model.Entity;

/**
 * This is a dummy class used to do JUnit testing for Entities and collision, specifically, collision detection, grounding etc
 * @author Gurangad Batth
 */
public class DummyTester extends Entity{
    /**
     * This is the constructor for the dummy tester entity.
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @param width the width of the dummy entity
     * @param height the height of the dummy entity
     */
    public DummyTester(double x, double y, int width, int height) {
        super(x, y, width, height); // Just call this only
    }

    /**
     * This update method stays empty because the dummy tester only exists for collision testing.
     */
    @Override 
    public void update() {
        // Keep this blank to test, we just wanna test hitboxes
    }
}
