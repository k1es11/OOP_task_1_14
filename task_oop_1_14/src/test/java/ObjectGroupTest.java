import org.example.ObjectGroup;
import org.example.Point;
import org.example.Polygon;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ObjectGroupTest {
    Polygon p1 = new Polygon(Arrays.asList(new Point(0, 0), new Point(2, 0), new Point(2, 3), new Point(0, 3)));
    Polygon p2 = new Polygon(Arrays.asList(new Point(2, 0), new Point(4, 0), new Point(4, 2), new Point(2, 2)));
    Polygon p3 = new Polygon(Arrays.asList(new Point(2,0), new Point(4, 0), new Point(1, 3)));
    ObjectGroup group1 = new ObjectGroup(Arrays.asList(p1, p2));
    ObjectGroup group2 = new ObjectGroup(Arrays.asList(p1, p3));
    ObjectGroup group3 = new ObjectGroup(Arrays.asList(p1, p3, p2));

    @Test
    public void testArea(){
        assertEquals(group1.getArea(), 10, 0);
        assertEquals(group2.getArea(), 9, 0);
        assertEquals(group3.getArea(), 13, 0);
    }

    @Test
    public void testIntersection(){
        assertTrue(group1.intersects(p3));
    }
}
