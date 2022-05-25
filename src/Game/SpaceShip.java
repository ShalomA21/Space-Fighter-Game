package Game;

import frame.Vector2D;

import java.awt.*;
import java.util.Random;

import static Game.Unit.*;

public class SpaceShip extends GameObject {
    public static final int RADIUS = 2;

    public static final double STEER_RATE = 3 * Math.PI;

    public static final double MAG_ACC = 200;

    public static final double DRAG = 0.03;

    public static final int MUZZLE_VELOCITY = 100;


    public Vector2D dir;

    public boolean thrusting;

    public Bullet bullet;
    public Sprite sprite;


    public int XP[] = { -6, 0, 6, 0 }, YP[] = { 8, 4, 8, -8 };
    public int XPTHRUST[] = { -5, 0, 5, 0 }, YPTHRUST[] = { 7, 3, 7, -7 };

    private long timeLastShot;
    public static final long SHOT_DELAY=200;

    private Controls ctrl;

    public SpaceShip(Controls ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, -1), RADIUS);
        this.ctrl = ctrl;
        dir = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
        timeLastShot = System.currentTimeMillis();
        double width = 50;


        Image im = Sprite.SHIP;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        sprite = new Sprite(im, pos, dir, width, height);
        radius = sprite.getRadius();

    }

    private void mkBullet(){
        bullet = new Bullet(new Vector2D(pos), new Vector2D(vel), true);
        bullet.pos.addScaled(dir, (radius+bullet.radius)*1.1);
        bullet.vel.addScaled(dir, MUZZLE_VELOCITY);

    }

    public void reset(){
        pos.set(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2));
        vel.set(new Vector2D(0,0));
        dir.set(new Vector2D(0, -1));
        dead = false;

    }


    @Override
    public void update() {
        super.update();
        Action action = ctrl.action();

        dir.rotate(action.turn * STEER_RATE * DT);
        vel = new Vector2D(dir).mult(vel.mag());
        vel.addScaled(dir, MAG_ACC * DT * action.thrust);
        vel.addScaled(vel, -DRAG);
        thrusting = action.thrust > 0;
        if (action.shoot) {
            long time = System.currentTimeMillis();
            if (time-timeLastShot>SHOT_DELAY) {
                mkBullet();
                action.shoot = false;
                timeLastShot = time;
            }
        }
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public boolean canHit(GameObject other) {
        return true;
    }

    @Override
    public void hit() {
        this.dead = true;
    }
}