package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Polygon extends GeometricObject {
    private final List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    public List<Point> getVertices() {
        return vertices;
    }

    @Override
    public double getArea() {
        double area = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % n);
            area += (current.getX() * next.getY()) - (current.getY() * next.getX());
        }
        return Math.abs(area) / 2;
    }

    public static Point findCentroid(List<Point> polygon) {
        double cx = 0, cy = 0;
        int n = polygon.size();
        for (Point p : polygon) {
            cx += p.getX();
            cy += p.getY();
        }
        return new Point(cx / n, cy / n);
    }

    public static void sortClockwise(List<Point> polygon) {
        polygon.sort(new ClockwiseComparator(findCentroid(polygon)));
    }

    static class ClockwiseComparator implements Comparator<Point> {
        private final Point centroid;

        public ClockwiseComparator(Point centroid) {
            this.centroid = centroid;
        }

        @Override
        public int compare(Point p1, Point p2) {
            if (p1.getX() - centroid.getX() >= 0 && p2.getX() - centroid.getX() < 0)
                return -1;
            if (p1.getX() - centroid.getX() < 0 && p2.getX() - centroid.getX() >= 0)
                return 1;
            if (p1.getX() - centroid.getX() == 0 && p2.getX() - centroid.getX() == 0) {
                if (p1.getY() - centroid.getY() >= 0 || p2.getY() - centroid.getY() >= 0)
                    return Double.compare(p1.getY(), p2.getY());
                return Double.compare(p2.getY(), p1.getY());
            }

            double det = (p1.getX() - centroid.getX()) * (p2.getY() - centroid.getY()) - (p2.getX() - centroid.getX()) * (p1.getY() - centroid.getY());
            if (det < 0)
                return -1;
            if (det > 0)
                return 1;

            double d1 = (p1.getX() - centroid.getX()) * (p1.getX() - centroid.getX()) + (p1.getY() - centroid.getY()) * (p1.getY() - centroid.getY());
            double d2 = (p2.getX() - centroid.getX()) * (p2.getX() - centroid.getX()) + (p2.getY() - centroid.getY()) * (p2.getY() - centroid.getY());
            return Double.compare(d1, d2);
        }
    }
    
    public static List<Point> removeInteriorVertices(List<Point> polygon, List<Point> intersections) {
        List<Point> cleanedPolygon = new ArrayList<>(polygon);
        for (Point p : polygon) {
            if (pointContains(intersections, p)) {
                cleanedPolygon.remove(p);
            }
        }
        return cleanedPolygon;
    }

    private static boolean pointContains(List<Point> points, Point p){
        for (Point s : points){
            if (p.equals(s)){
                return true;
            }
        }
        return false;
    }

    public static boolean isInsidePolygon(List<Point> polygon, Point p) {
        int n = polygon.size();
        boolean inside = false;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            if ((polygon.get(i).getY() > p.getY()) != (polygon.get(j).getY() > p.getY()) &&
                    (p.getX() < (polygon.get(j).getX() - polygon.get(i).getX()) * (p.getY() - polygon.get(i).getY()) / (polygon.get(j).getY() - polygon.get(i).getY()) + polygon.get(i).getX())) {
                inside = !inside;
            }
        }
        return !inside;
    }

    @Override
    public GeometricObject subtract(GeometricObject obj) {
        return this;
    }

    public static List<Point> intersectPolygons(List<Point> polygon1, List<Point> polygon2) {
        List<Point> intersections = new ArrayList<>();

        for (int i = 0; i < polygon1.size(); i++) {
            int j = (i + 1) % polygon1.size();
            Point p1 = polygon1.get(i);
            Point p2 = polygon1.get(j);

            for (int k = 0; k < polygon2.size(); k++) {
                int l = (k + 1) % polygon2.size();
                Point p3 = polygon2.get(k);
                Point p4 = polygon2.get(l);

                if (doIntersect(p1, p2, p3, p4)) {
                    Point intersection = calculateIntersection(p1, p2, p3, p4);
                    if (!containsPoint(intersections, intersection)) {
                        intersections.add(intersection);
                    }
                }
            }
        }

        return intersections;
    }

    public static Point calculateIntersection(Point p1, Point q1, Point p2, Point q2) {
        double A1 = q1.getY() - p1.getY();
        double B1 = p1.getX() - q1.getX();
        double C1 = A1 * p1.getX() + B1 * p1.getY();

        double A2 = q2.getY() - p2.getY();
        double B2 = p2.getX() - q2.getX();
        double C2 = A2 * p2.getX() + B2 * p2.getY();

        double det = A1 * B2 - A2 * B1;
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;
        if (x == 0) x = 0;
        if (y == 0) y = 0;
        return new Point(x, y);
    }

    public static boolean containsPoint(List<Point> points, Point p) {
        for (Point point : points) {
            if (point.getX() == p.getX() && point.getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    public static int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) {
            return 0;
        }

        return (val > 0) ? 1 : 2;
    }

    @Override
    public GeometricObject merge(GeometricObject obj) {
        if (obj instanceof Polygon other) {
            List<Point> intersections = intersectPolygons(this.vertices, other.vertices);
            if (intersections.size() < 1 || other.equals(this)) return this;
            List<Point> union = new ArrayList<>(removeInteriorVertices(this.vertices, other.vertices));
            for (Point p : other.vertices) {
                if (isInsidePolygon(this.vertices, p) && !pointContains(union, p) && !pointContains(this.vertices, p)) {
                    union.add(new Point(p.getX(), p.getY()));
                }
            }

            for (Point intersection : intersections) {
                if (isInsidePolygon(this.vertices, intersection) && !pointContains(union, intersection) && !pointContains(this.vertices, intersection)) {
                    union.add(new Point(intersection.getX(), intersection.getY()));
                }
            }

            sortClockwise(union);
            return new Polygon(union);
        } else {
            return this;
        }
    }



    @Override
    public boolean intersects(GeometricObject obj) {
        if (obj instanceof Polygon other) {
            return doPolygonsIntersect(this.vertices, other.getVertices());
        } else {
            return false;
        }
    }

    public static boolean onSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }

    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        return o1 != o2 && o3 != o4;
    }

    private static boolean isInside(List<Point> polygon, int n, Point p) {
        if (n < 3) {
            return false;
        }

        Point extreme = new Point(Double.MAX_VALUE, p.getY());
        int count = 0, i = 0;

        do {
            int next = (i + 1) % n;
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme)) {
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0) {
                    return onSegment(polygon.get(i), p, polygon.get(next));
                }
                count++;
            }
            i = next;
        } while (i != 0);

        return count % 2 == 1;
    }

    private boolean doPolygonsIntersect(List<Point> polygon1, List<Point> polygon2) {
        int n = polygon1.size();
        int m = polygon2.size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (doIntersect(polygon1.get(i), polygon1.get((i + 1) % n), polygon2.get(j), polygon2.get((j + 1) % m))) {
                    return true;
                }
            }
        }

        for (Point value : polygon1) {
            if (isInside(polygon2, m, value)) {
                return true;
            }
        }

        for (Point point : polygon2) {
            if (isInside(polygon1, n, point)) {
                return true;
            }
        }

        return false;
    }
}
