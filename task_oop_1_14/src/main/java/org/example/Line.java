package org.example;

import java.util.Objects;

public class Line extends GeometricObject {
    private final Point start;
    private final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    private double length(){
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return getStart().equals(line.getStart()) && getEnd().equals(line.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public GeometricObject merge(GeometricObject obj) {
        if (obj instanceof Line other) {
            if (this.intersects(other)){
                if (this.end.equals(other.start)) {
                    return new Line(this.start, other.end);
                } else if (this.start.equals(other.end)) {
                    return new Line(other.start, this.end);
                }
                if (this.collinearity(other) && other.length() > this.length()) return other;
            }
            return this;
        } else {
            return obj;
        }
    }


    @Override
    public boolean intersects(GeometricObject obj) {
        if (obj instanceof Line other) {
            return doIntersect(this.start, this.end, other.start, other.end);
        } else {
            return false;
        }
    }

    private boolean collinearity(Line other){
        Point p1 = this.start, p2 = this.end;
        Point p3 = other.start, p4 = other.end;
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);

        return d1 == 0 && d2 == 0 && d3 == 0 && d4 == 0;
    }

    private static boolean doIntersect(Point p1, Point p2, Point p3, Point p4) {
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);

        if (d1 == 0 && d2 == 0 && d3 == 0 && d4 == 0) return true;

        return d1 != d2 && d3 != d4;
    }

    private static double direction(Point p1, Point p2, Point p3) {
        double x1 = p1.getX(), y1 = p1.getY();
        double x2 = p2.getX(), y2 = p2.getY();
        double x3 = p3.getX(), y3 = p3.getY();
        double val = (x3 - x1) * (y2 - y1) - (x2 - x1) * (y3 - y1);

        if (val == 0) return 0;

        return (val > 0) ? 1 : 2;
    }

    @Override
    public GeometricObject subtract(GeometricObject obj) {
        if (obj instanceof Line other) {
            if (this.intersects(other) && this.collinearity(other)) {
                if (other.start.equals(this.start) && other.end.equals(this.end)) {
                    return null;
                } else if (other.start.equals(this.start)) {
                    return new Line(other.end, this.end);
                } else if (other.end.equals(this.end)) {
                    return new Line(this.start, other.start);
                } else {
                    return new Line(this.start, other.start);
                }
            } else {
                return this;
            }
        } else {
            return this;
        }
    }
}
