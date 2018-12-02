package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Controller {
    private Timeline timer;
    private View view;
    private Model model;
    public boolean createTreesHere;
    public boolean createTreesElsewhere;
    Thread t;

    private int cnt = 5;


    public Controller(View view, Model model) throws IOException {
        Thread t = new Thread(new Client(this));
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean makeNewCoin = false;
                Random rand = new Random();
                synchronized (model) {

                    ArrayList<ModelObject> toDelete = new ArrayList<>();
                    ArrayList<ModelObject> toAdd = new ArrayList<>();
                    for (ModelObject object : model.getObjects()) {
                        object.setDir(model.getDir());
                        /*if (object instanceof Man && object.getPosition().distance(model.mousePoint.getX(), model.mousePoint.getY()) < 10) {
                            gameOver();
                            stop();
                            view.over = true;

                        }*/
                        if (object instanceof Man) {
                            for (ModelObject obj : model.getObjects()) {
                                if (obj instanceof Tree) {
                                    if (samePlace(obj, object)) {
                                        gameOver();
                                        stop();
                                        view.over = true;
                                    }
                                }

                                if (obj instanceof Coin) {
                                    if (samePlace(obj, object)) {
                                        toDelete.add(obj);
                                        createTreesElsewhere = true;
                                    }
                                }
                                if (createTreesHere) {
                                    for (int i = 0; i < 2; i++) {
                                        boolean bad = false;
                                        Tree newTree;
                                        do {
                                            bad = false;
                                            newTree = new Tree(new Point2D(rand.nextInt(View.WIDTH + 1 - 100) + 50, rand.nextInt(View.HEIGHT + 1 - 100) + 50), new Point2D(0, 0));
                                            for (ModelObject ob : model.getObjects()) {
                                                if (ob.getPosition().distance(newTree.getPosition()) < 20) {
                                                    bad = true;
                                                    break;
                                                }
                                            }
                                        } while (bad);

                                        toAdd.add(newTree);
                                    }
                                    boolean badCoin = false;
                                    Coin newCoin;
                                    do {
                                        badCoin = false;
                                        newCoin = new Coin(new Point2D(rand.nextInt(View.WIDTH + 1 - 100) + 50, rand.nextInt(View.HEIGHT + 1 - 100) + 50), new Point2D(0, 0));
                                        for (ModelObject ob : model.getObjects()) {
                                            if (ob.getPosition().distance(newCoin.getPosition()) < 20) {
                                                badCoin = true;
                                                break;
                                            }
                                        }
                                    } while (badCoin);
                                    toAdd.add(newCoin);

                                    view.update();
                                    createTreesHere = false;
                                }

                            }
                        }
                        object.process();
                        if (object.isOutOfSpaceX()){
                            if (object.getPosition().getX() < 0){
                                object.setPositionX(View.WIDTH);
                            } else if (object.getPosition().getX() > View.WIDTH){
                                object.setPositionX(0);
                            }

                        } else if (object.isOutOfSpaceY()){
                           if (object.getPosition().getY() < 0){
                               object.setPositionY(View.HEIGHT);
                           } else if (object.getPosition().getY() > View.HEIGHT){
                               object.setPositionY(0);
                           }
                        }
                    }
                    model.getObjects().removeAll(toDelete);
                    model.objects.addAll(toAdd);
                }
                view.update();
            }
        };
        timer = new Timeline(new KeyFrame(Duration.millis(50), eventHandler));
        //client.setEventHandler(eventHandler);

        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;
        //this.createTreesHere = false;
        //this.createTreesElsewhere = false;
        t.start();
    }

    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    boolean samePlace(ModelObject x, ModelObject y) {
        return (x.getPosition().distance(y.getPosition()) < 25);
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.over = false;
        timer.play();
        view.update();

    }

    void gameOver() {
        timer.stop();
        view.over();
    }

}
