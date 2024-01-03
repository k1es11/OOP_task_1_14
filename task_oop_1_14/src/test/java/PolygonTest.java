import org.example.Point;
import org.example.Polygon;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class PolygonTest {
    Polygon p1 = new Polygon(Arrays.asList(new Point(0, 0), new Point(3, 0), new Point(3, 5), new Point(0, 5)));
    Polygon p2 = new Polygon(Arrays.asList(new Point(2, 0), new Point(6, 0), new Point(6, 3), new Point(2, 3)));
    Polygon p3 = new Polygon(Arrays.asList(new Point(4, 5), new Point(6, 5), new Point(6, 8), new Point(4, 8)));
    Polygon p4 = new Polygon(Arrays.asList(new Point(0, 0), new Point(2, 0), new Point(2, 3), new Point(0, 3)));
    Polygon p5 = new Polygon(Arrays.asList(new Point(2, 0), new Point(4, 0), new Point(4, 2), new Point(2, 2)));
    Polygon p6 = (Polygon) p4.merge(p5);
    Polygon p7 = new Polygon(Arrays.asList(new Point(2,0), new Point(4, 0), new Point(1, 3)));
    Polygon p8 = (Polygon) p4.merge(p7);
    Polygon p9 = (Polygon) p6.merge(p6);
    Polygon p10 = new Polygon(Arrays.asList(new Point(2,0), new Point(4, 0), new Point(0, 4)));
    Polygon p11 = (Polygon) p4.merge(p10);
    Polygon p12 = (Polygon) p7.merge(p10);

    @Test
    public void testArea(){
        assertEquals(p1.getArea(), 15, 0);
        assertEquals(p2.getArea(), 12, 0);
        assertEquals(p3.getArea(), 6, 0);
        assertEquals(p4.getArea(), 6, 0);
        assertEquals(p5.getArea(), 4, 0);
        assertEquals(p6.getArea(), 10, 0);
        assertEquals(p7.getArea(), 3, 0);
        assertEquals(p8.getArea(), 8, 0);
        assertEquals(p9.getArea(), p6.getArea(), 0);
        assertEquals(p10.getArea(), 4, 0);
        assertEquals(p11.getArea(), 8.25, 0);
        assertEquals(p12.getArea(), 4, 0);
    }

    @Test
    public void testIntersection(){
        assertTrue(p4.intersects(p5));
        assertTrue(p4.intersects(p4));
        assertTrue(p7.intersects(p10));
    }

    @Test
    public void testMerge(){
        assertEquals(p10, p12);
    }

    @Test
    public void testSubtract(){
        assertEquals(p5.subtract(new Polygon(Arrays.asList(new Point(2, 0), new Point(4, 0), new Point(2, 2)))), p5);
    }
}
