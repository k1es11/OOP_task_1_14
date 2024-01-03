import org.example.GeometricObject;
import org.example.Line;
import org.example.Point;
import static org.junit.Assert.*;
import org.junit.Test;

public class PointTest {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    Point p3 = new Point(1, 1);

    @Test
    public void testArea(){
        assertEquals(p1.getArea(), 0, 0);
    }

    @Test
    public void testIntersection(){
        assertTrue(p1.intersects(p2));
        assertFalse(p1.intersects(p3));
        assertFalse(p2.intersects(p3));
    }

    @Test
    public void testMerge() {
        GeometricObject mergedPoint = p1.merge(p3);

        Line line = new Line(p1, p3);
        assertEquals(mergedPoint, line);
    }

    @Test
    public void testSubtract() {
        assertNull(p1.subtract(p2));
        Point point = (Point) p1.subtract(p3);
        assertEquals(point, p1);
    }
}
