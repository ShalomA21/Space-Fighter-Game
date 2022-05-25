package Game;

import frame.Vector2D;

import java.awt.*;
import java.util.Random;

import static Game.Unit.WORLD_HEIGHT;
import static Game.Unit.WORLD_WIDTH;


public class Asteroid extends GameObject {
    public static final double VMIN = 200, VMAX = 300;
    public Sprite sprite;
    public double rotationPerFrame;


    public Asteroid(double x, double y, double vx, double vy, Sprite sprite) {
        super(new Vector2D(x, y),
                new Vector2D(vx, vy), sprite.getRadius());
    }

    public Asteroid() {
        super(new Vector2D(WORLD_WIDTH*Math.random(), WORLD_HEIGHT+Math.random()), new Vector2D(0,0), 0);
        double speed = VMIN+(VMAX-VMIN)*Math.random();
        double angle = Math.random() * 2 * Math.PI;
        vel.set(new Vector2D(speed*Math.cos(angle), speed*Math.sin(angle)));
        rotationPerFrame = Math.random()*0.1;
        double width = Math.min(Math.max(20+new Random().nextGaussian()*30, 30), 50);


        Image im = Sprite.ASTEROID;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        sprite = new Sprite(im, pos, dir, width, height);
        radius = sprite.getRadius();
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public void update() {
        super.update();
        dir.rotate(rotationPerFrame);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() != Asteroid.class;
    }
}