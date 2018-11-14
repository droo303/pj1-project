package game;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Controller {
    private Timeline timer;
    private View view;
    private Model model;
    private int cnt = 5;

    public Controller(View view, Model model) {
        timer = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                model.movingModel(cnt);
                synchronized (model) {
                    ArrayList<ModelObject> toDelete = new ArrayList<>();
                    for (ModelObject object : model.getObjects()) {
                        object.process();
                        if (object.isOutOfSpace()) {
                            toDelete.add(object);
                        }
                    }
                    model.getObjects().removeAll(toDelete);
                }
                view.update();
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;
    }

    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.update();
        timer.play();
    }
}
