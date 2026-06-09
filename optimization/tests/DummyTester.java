package tests;
import model.Entity;
public class DummyTester extends Entity{
    public DummyTester(double x, double y, int width, int height) {
        super(x, y, width, height);
    }
    @Override
    public void update() {
        // Keep this blank to test, we just wanna test hitboxes
    }
}
