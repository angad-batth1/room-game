package tests;
import model.Entity;

/**
 * This is a dummy class used to do JUnit testing for Entities and collision, specifically, collision detection, grounding etc
 * @author Gurangad Batth
 */
public class DummyTester extends Entity{
    public DummyTester(double x, double y, int width, int height) {
        super(x, y, width, height); // Just call this only
    }

    @Override 
    public void update() {
        // Keep this blank to test, we just wanna test hitboxes
    }
}
