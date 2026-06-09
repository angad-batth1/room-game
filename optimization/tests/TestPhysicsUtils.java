package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Entity;
import utils.PhysicsUtils;

public class TestPhysicsUtils {

    @Test
    public void testCollisionOverlap(){
        Entity a = new DummyTester(0, 0, 32, 32);
        Entity b = new DummyTester(16, 16, 32, 32);
        assertEquals(true, PhysicsUtils.checkCollision(a, b));
    }

    @Test
    public void testNoCollision(){
        Entity a = new DummyTester(0, 0, 32, 32);
        Entity b = new DummyTester(100, 100, 32, 32);
        assertEquals(false, PhysicsUtils.checkCollision(a, b));
    }

    @Test
    public void testLandingOnPlatform(){
        Entity player = new DummyTester(10, 20, 32, 32); // bottom is y=52
        Entity platform = new DummyTester(0, 50, 100, 32); // top is y = 50
        assertEquals(true, PhysicsUtils.isLandingOn(player, platform));
    }
    
}
