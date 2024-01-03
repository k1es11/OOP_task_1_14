package org.example;

import java.util.Objects;

public class Point extends GeometricObject {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.getX(), getX()) == 0 && Double.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public GeometricObject subtract(GeometricObject obj) {
        if (obj instanceof Point && obj.intersects(this)) {
            return null;
        } else {
            return this;
        }
    }

    @Override
    public GeometricObject merge(GeometricObject obj) {
        if (obj instanceof Point other) {
            return new Line(this, other);
        } else {
            return obj;
        }
    }

    @Override
    public boolean intersects(GeometricObject obj) {
        if (obj instanceof Point other) {
            return this.x == other.getX() && this.y == other.getY();
        } else {
            return false;
        }
    }
}