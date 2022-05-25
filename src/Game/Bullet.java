package Game;

import frame.Vector2D;

import java.awt.*;

public class Bullet extends GameObject {
    private double lifetime;
    public static final int RADIUS = 3;
    public static final int BULLET_LIFE = 50;

    boolean firedByShip;
    public Sprite sprite;

    public Bullet(Vector2D pos, Vector2D vel, boolean byShip) {
        super(pos, vel, RADIUS);
        this.lifetime = BULLET_LIFE;
        firedByShip = byShip;
        double width = 30;

        Image im = Sprite.BULLET;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double direction = 3;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        sprite = new Sprite(im, pos, dir, width, height);
        radius = sprite.getRadius();

    }

    @Override
    public void update() {
        super.update();
        lifetime -= 1;
        if (lifetime <= 0) dead = true;
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() == SpaceShip.class || other.getClass() == Bullet.class;
    }

    @Override
    public void hit() {
        dead = true;

    }
}