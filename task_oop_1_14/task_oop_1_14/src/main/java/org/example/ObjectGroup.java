package org.example;

import java.util.ArrayList;
import java.util.List;

public class ObjectGroup extends GeometricObject {
    private final List<GeometricObject> objects;

    public ObjectGroup() {
        this.objects = new ArrayList<>();
    }

    public void addObject(GeometricObject object) {
        objects.add(object);
    }

    @Override
    public double getArea() {
        double areaSum = 0;
        for (GeometricObject obj : objects) {
            areaSum += obj.getArea();
        }
        return areaSum;
    }

    @Override
    public GeometricObject subtract(GeometricObject obj) {
        ObjectGroup resultGroup = new ObjectGroup();
        for (GeometricObject object : objects) {
            resultGroup.addObject(object.subtract(obj));
        }
        return resultGroup;
    }

    @Override
    public GeometricObject merge(GeometricObject obj) {
        if (obj instanceof ObjectGroup otherGroup) {
            ObjectGroup mergedGroup = new ObjectGroup();
            for (GeometricObject object : this.objects) {
                mergedGroup.addObject(object);
            }
            for (GeometricObject object : otherGroup.objects) {
                mergedGroup.addObject(object);
            }
            return mergedGroup;
        } else {
            return this;
        }
    }

    @Override
    public boolean intersects(GeometricObject obj) {
        for (GeometricObject object : objects) {
            if (object.intersects(obj)) {
                return true;
            }
        }
        return false;
    }
}
