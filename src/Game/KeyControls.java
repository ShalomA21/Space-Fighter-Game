package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyControls extends KeyAdapter implements Controls {
    Action action;
    public KeyControls(){
        action = new Action();
    }

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.thrust = 1;
                break;
            case KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_S:
                action.turn = -1;
                break;
            case KeyEvent.VK_D:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.thrust = 0;
                break;
            case KeyEvent.VK_A:
                action.turn = 0;
                break;
            case KeyEvent.VK_S:
                action.turn = 0;
                break;
            case KeyEvent.VK_D:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}