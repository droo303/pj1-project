package game;

import javafx.geometry.Point2D;

public class Man extends ModelObject {
    public Man(Point2D position, Point2D imageOffset) {
        super(position, imageOffset);
    }

    @Override
    public void process() {

        Point2D dir = getDir();
        if (dir != null) {
            move(dir.multiply(5).getX(), dir.multiply(5).getY());
        }
    }
}

