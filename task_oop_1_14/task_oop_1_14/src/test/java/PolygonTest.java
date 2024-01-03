import org.example.Point;
import org.example.Polygon;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class PolygonTest {
    Polygon p1 = new Polygon(Arrays.asList(new Point(0, 0), new Point(3, 0), new Point(3, 5), new Point(0, 5)));
    Polygon p2 = new Polygon(Arrays.asList(new Point(2, 0), new Point(6, 0), new Point(6, 3), new Point(2, 3)));
    Polygon p3 = new Polygon(Arrays.asList(new Point(4, 5), new Point(6, 5), new Point(6, 8), new Point(4, 8)));

    @Test
    public void testArea(){
        assertEquals(p1.getArea(), 15, 0);
        assertEquals(p2.getArea(), 12, 0);
        assertEquals(p3.getArea(), 6, 0);
    }

    @Test
    public void testIntersection(){

    }

    @Test
    public void testMerge(){

    }

    @Test
    public void testSubtract(){

    }
}
