package game;
import javafx.geometry.Point2D;

public abstract class ModelObject {


    private Point2D position;
    private double direction;
    private final Point2D imageOffset;
    private Point2D dir;

    public Point2D getDir() {
        return dir;
    }

    public void setDir(Point2D dir) {
        this.dir = dir;
    }



    public ModelObject(Point2D position, Point2D imageOffset) {
        this.position = position;
        this.imageOffset = imageOffset;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setPositionX( double position) {
        this.position = new Point2D(position,this.getY());
    }

    public void setPositionY( double position) {
        this.position = new Point2D(this.getX(),position);
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getDirection() {
        return direction;
    }


    public void setDirectionToPoint(Point2D direction) {
        Point2D imageRotation = position.add(imageOffset);
        double angle = position.angle(direction, imageRotation);
        if (direction.getY() < position.getY()) {
            angle = -angle;
        }
        this.direction = angle;
        this.dir= imageRotation.add(direction).normalize();
    }

    public boolean isOutOfSpaceX() {
        if (this.position.getX() < 0 || this.position.getX() > View.WIDTH) {
            return true;
        }
        return false;
    }

    public boolean isOutOfSpaceY(){
        if (this.position.getY() < 0 || this.position.getY() > View.HEIGHT) {
            return true;
        }
        return false;
    }

     public void move(double offsetX, double offsetY) {
        position = new Point2D(getX() + offsetX, getY() + offsetY);
    }

    public abstract void process();

}
