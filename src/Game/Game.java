package Game;

import frame.JEasyFrame;

import frame.JEasyFrameFull;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static Game.Unit.FPS;
import static Game.Unit.setFullScreenDimensions;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static final int INITIAL_LIVES = 3;
    public static final int INITIAL_SAFETY_DURATION = 5000;
    boolean shipIsSafe;
    public List<GameObject> objects;
    SpaceShip ship;
    KeyControls ctrl;
    int score, lives, level, remainingAsteroids;
    GameScreen view;
    boolean ended;
    long gameStartTime;
    long startTime;
    boolean resetting;

    public Game(boolean fullScreen) {
        if (fullScreen) setFullScreenDimensions();
        view = new GameScreen(this);
        objects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {

            objects.add(new Asteroid());

        }
        ctrl = new KeyControls();
        ship = new SpaceShip(ctrl);
        objects.add(ship);
        score = 0;
        remainingAsteroids = N_INITIAL_ASTEROIDS;
        lives = INITIAL_LIVES;
        ended = false;
        shipIsSafe = true;
        resetting = false;
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "SPACE FIGHTER");
        frame.setResizable(false);
        frame.addKeyListener(ctrl);

    }

    public static void main(String[] args) {
        Game game = new Game(false);
        game.gameLoop();
    }

    public void gameLoop() {
        long DTMS = Math.round(1000 / FPS);
        gameStartTime = startTime = System.currentTimeMillis();
        while (!ended) {
            long time0 = System.currentTimeMillis();
            update();
            view.repaint();
            long timeToSleep = time0 + DTMS - System.currentTimeMillis();
            if (timeToSleep < 0)
                System.out.println();
            else
                try {
                    Thread.sleep(timeToSleep);
                } catch (Exception e) {
                }


        }

        System.out.println("Your score was " + score);
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public boolean reset(boolean newlife) {
        objects.clear();
        if (newlife) level++;
        else
            lives--;
        if (lives == 0)
            return false;
        for (int i = 0; i < N_INITIAL_ASTEROIDS + (level - 1) * 5; i++) {
            objects.add(new Asteroid());

        }
        ship.reset();
        objects.add(ship);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        shipIsSafe = true;
        startTime = System.currentTimeMillis();
        return true;

    }


    public void update() {


        if (shipIsSafe) {
            shipIsSafe = System.currentTimeMillis() < startTime + INITIAL_SAFETY_DURATION;
        } else
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }


        ended = true;
        List<GameObject> alive = new ArrayList<>();
        for (GameObject o : objects) {
            if (!o.dead) {
                o.update();
                alive.add(o);
                if (o == ship) ended = false;
            }
            else if (o==ship){
                resetting = true;
                break;
            }
            else updateScore(o);
        }
        if (ship.bullet != null) {
            alive.add(ship.bullet);
            ship.bullet = null;
        }


        synchronized (Game.class) {
            if (remainingAsteroids==0)
                reset(true);
            else if (resetting) {
                ended = !reset(false);
                resetting = false;
            }
            else {
                objects.clear();
                for (GameObject o : alive) objects.add(o);

            }
        }
    }

    public void updateScore(GameObject o) {
        if (o.getClass() == Asteroid.class) {
            score += 10;
            remainingAsteroids -= 1;
        }
    }

}