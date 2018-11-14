package game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class View {

        public final static int HEIGHT = 600;
        public final static int WIDTH = 800;

        private final GraphicsContext context;
        private final Image background, man;

        private final Model model;

        View(GraphicsContext context, Model model) {
            this.context = context;
            background = new Image("file:src/game/image/forest.png");
            man = new Image("file:src/game/image/man40_37.png");
            this.model = model;
            update();
        }

        private void drawImage(Image image, Point2D point) {
            context.drawImage(image, point.getX() - image.getWidth() / 2, point.getY() - image.getHeight() / 2);
        }

        private void rotate(double angle, Point2D center) {
            Rotate r = new Rotate(angle, center.getX(), center.getY());
            context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        }

        public void update() {
            context.drawImage(background, 0, 0, WIDTH, HEIGHT);
            context.setStroke(Color.BLUE);
            context.setLineWidth(2);
            context.strokeRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
            synchronized (model) {
                for (ModelObject object : model.getObjects()) {
                    context.save();
                    rotate(object.getDirection(), object.getPosition());
                    //object.move();
                        drawImage(man, object.getPosition());
                        context.restore();
                }
            }
        }
}
