package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;

public class Model {

    public ArrayList<ModelObject> objects = new ArrayList<>();
    //private ModelObject man, coin;
    private Man man;
    private Coin coin;
    private Tree tree;

    public Point2D dir;
    public Point2D mousePoint;


    public Point2D getDir() {
        return dir;
    }


    public ArrayList<ModelObject> getObjects() {
        return this.objects;
    }

    void addObject(ModelObject object){
        objects.add(object);
    }

    public Model() {

    }

    public synchronized void setCursor(double x, double y) {
        mousePoint = new Point2D(x, y);
        if (man != null) {
            man.setDirectionToPoint(mousePoint);
            this.dir = new Point2D(mousePoint.subtract(man.getPosition()).normalize().getX(), mousePoint.subtract(man.getPosition()).normalize().getY());
        }
    }

    public synchronized void initGame() {
        Random rand = new Random();
        objects.clear();
        Point2D center = new Point2D(View.WIDTH / 2, View.HEIGHT / 2);
        man = new Man(center, new Point2D(100, 0));
        objects.add(man);
        for (int i = 0; i < 5; i++) {

            tree = new Tree(new Point2D(rand.nextInt(View.WIDTH + 1 - 150)+75, rand.nextInt(View.HEIGHT + 1 - 150)+75), new Point2D(0, 0));

            objects.add(tree);
        }

        for (int i = 0; i < 5; i++) {
            boolean bad_point = false;
            Point2D point = new Point2D(rand.nextInt(View.WIDTH + 1 - 100)+50, rand.nextInt(View.HEIGHT + 1 - 100)+50);
            for (ModelObject object : objects) {
                if (point.distance(object.getPosition()) < 25) {
                    bad_point = true;
                }
            }
            if (bad_point) {
                i--;
            } else {
                coin = new Coin(point, new Point2D(0, 0));
                objects.add(coin);
            }
        }


    }
}
