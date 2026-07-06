package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import model.Entity;
import utils.PhysicsUtils;

/**
 * This is a testing class used to test the physics of the game -- logic in physics utils
 * @author Gurangad Batth
 */
public class TestPhysicsUtils {

    /**
     * This test checks that two overlapping hitboxes register a collision.
     */
    @Test
    public void testCollisionOverlap(){
        Entity a = new DummyTester(0, 0, 32, 32);
        Entity b = new DummyTester(16, 16, 32, 32);
        assertTrue(PhysicsUtils.checkCollision(a, b));
    }

    /**
     * This test checks that two separated hitboxes do not register a collision.
     */
    @Test
    public void testNoCollision(){
        Entity a = new DummyTester(0, 0, 32, 32);
        Entity b = new DummyTester(100, 100, 32, 32);
        assertFalse(PhysicsUtils.checkCollision(a, b));
    }

    /**
     * This test checks that landing logic detects an entity touching the top of a platform.
     */
    @Test
    public void testLandingOnPlatform(){
        Entity player = new DummyTester(10, 20, 32, 32); // bottom is y=52
        Entity platform = new DummyTester(0, 50, 100, 32); // top is y = 50
        assertTrue(PhysicsUtils.isLandingOn(player, platform));
    }
    
}
