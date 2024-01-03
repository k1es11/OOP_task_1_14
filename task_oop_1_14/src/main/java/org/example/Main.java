package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Line line = new Line(p1, p2);

        List<Point> vertices = new ArrayList<>();
        vertices.add(new Point(0, 0));
        vertices.add(new Point(3, 0));
        vertices.add(new Point(3, 4));
        vertices.add(new Point(0, 4));
        Polygon polygon = new Polygon(vertices);

        ObjectGroup group = new ObjectGroup();
        group.addObject(line);
        group.addObject(polygon);

        System.out.println("Площадь линии: " + line.getArea());
        System.out.println("Площадь многоугольника: " + polygon.getArea());
        System.out.println("Площадь группы объектов: " + group.getArea());
    }
}