package org.example;

public abstract class GeometricObject {
    public abstract double getArea();
    public abstract GeometricObject merge(GeometricObject obj);
    public abstract GeometricObject subtract(GeometricObject obj);
    public abstract boolean intersects(GeometricObject obj);
}
