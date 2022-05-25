package Game;

import frame.Vector2D;

import javax.sound.sampled.Clip;
import java.awt.*;

import static Game.Unit.*;

public abstract class GameObject {
    public Vector2D pos;
    public Vector2D vel;
    public Vector2D dir;
    public double radius;
    public boolean dead;
    public Clip deathSound = null;


    public GameObject(Vector2D pos, Vector2D vel, double radius) {
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
        this.dead = false;
        this.dir = new Vector2D(1,0);
    }

    public void update() {
        pos.addScaled(vel, DT);
        pos.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public boolean overlap(GameObject other) {
        return this.pos.dist(other.pos) < this.radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (!this.dead   && !other.dead && this.canHit(other) && this.overlap(other)){
            this.hit();
            other.hit();
        }
    }

    public abstract boolean canHit(GameObject other);

    public abstract void draw(Graphics2D g);

    public void hit()
    { dead = true;
    }

}