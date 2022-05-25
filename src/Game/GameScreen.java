package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static Game.Unit.*;

public class GameScreen extends JComponent {
    public static final Color BG_COLOR = Color.BLACK, TEXT_COLOR = Color.WHITE, TEXT_BG_COLOR = Color.black;
    private Game game;
    Image im = GALAXY;
    AffineTransform bgTransf;

    public GameScreen(Game game) {
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchX = imWidth > FRAME_WIDTH ? 1 : FRAME_WIDTH / imWidth;
        double stretchY = imHeight > FRAME_HEIGHT ? 1 : FRAME_HEIGHT / imHeight;
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchX, stretchY);

    }

    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im, bgTransf, null);
        synchronized (Game.class) {
            for (GameObject ob : game.objects)
                ob.draw(g);
        }
        g.setColor(TEXT_BG_COLOR);
        g.fillRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);
        g.setColor(TEXT_COLOR);
        g.drawRect(0, getHeight() - SCORE_PANEL_HEIGHT, getWidth(), SCORE_PANEL_HEIGHT);
        g.setFont(new Font("dialog", Font.BOLD, (2 * SCORE_PANEL_HEIGHT / 3)));
        g.drawString("Score " + Integer.toString(game.getScore()), 10, getHeight() - SCORE_PANEL_HEIGHT / 3);
        g.drawString("Lives Left: " + Integer.toString(game.getLives()), 200, getHeight() - SCORE_PANEL_HEIGHT / 3);

    }

    public Dimension getPreferredSize() {
        return Unit.FRAME_SIZE;
    }
}