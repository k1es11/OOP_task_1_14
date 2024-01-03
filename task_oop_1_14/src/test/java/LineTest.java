import org.example.Line;
import org.example.Point;
import org.junit.Test;
import static org.junit.Assert.*;

public class LineTest {
    Line l = new Line(new Point(0, 0), new Point(0, 0));
    Line l1 = new Line(new Point(0, 1), new Point(2, 1));
    Line l2 = new Line(new Point(1, 0), new Point(1, 2));
    Line l3 = new Line(new Point(0, 2), new Point(2, 0));
    Line l4 = new Line(new Point(3, 0), new Point(4, 2));
    Line l5 = new Line(new Point(10, 10), new Point(15, 10));
    Line l6 = new Line(new Point(15, 10), new Point(20, 10));
    Line l7 = new Line(new Point(12, 10), new Point(13, 10));


    @Test
    public void testArea(){
        assertEquals(l.getArea(), 0, 0);
        assertEquals(l1.getArea(), 0, 0);
        assertEquals(l2.getArea(), 0, 0);
        assertEquals(l3.getArea(), 0, 0);
        assertEquals(l4.getArea(), 0, 0);
    }

    @Test
    public void testIntersection(){
        assertFalse(l.intersects(l1));
        assertTrue(l1.intersects(l2));
        assertTrue(l2.intersects(l3));
        assertTrue(l3.intersects(l1));
        assertFalse(l1.intersects(l4));
        assertFalse(l4.intersects(l));
        assertTrue(l5.intersects(l6));
    }

    @Test
    public void testMerge(){
        assertEquals(l1.merge(l2), l1);
        assertEquals(l1.merge(l3), l1);
        assertEquals(l5.merge(l6), new Line(new Point(10, 10), new Point(20, 10)));
        assertEquals(l5.merge(l7), l5);
        assertEquals(l7.merge(l5), l5);
    }

    @Test
    public void testSubtract(){
        assertEquals(l1.subtract(l2), l1);
        assertEquals(l5.subtract(l7), new Line(new Point(10, 10), new Point(12, 10)));
    }
}
