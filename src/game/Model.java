package game;

import java.util.ArrayList;
import javafx.geometry.Point2D;

public class Model {

        private ArrayList<ModelObject> objects = new ArrayList<>();
        private ModelObject man;
        private Point2D dir  = new Point2D(0,0);

        public ArrayList<ModelObject> getObjects() {
            return this.objects;
        }

        public Model() {

        }

        public synchronized void setCursor(double x, double y) {
            Point2D mousePoint = new Point2D(x, y);
            if (man != null) {
                man.setDirectionToPoint(mousePoint);
                this.dir = new Point2D(mousePoint.subtract(man.getPosition()).normalize().getX(),mousePoint.subtract(man.getPosition()).normalize().getY());

            }
        }

        public synchronized void initGame() {
            objects.clear();
            Point2D center = new Point2D(View.WIDTH / 2, View.HEIGHT / 2);
            man = new Man(center, new Point2D(100, 0));
            objects.add(man);
        }

        public synchronized void movingModel(int n){
            man.move(dir.multiply(n).getX(),dir.multiply(n).getY());
        }
}
