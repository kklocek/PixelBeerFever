package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

/**
 * Created by Konrad on 2016-04-11.
 */
public class VisitorEntrance implements Disposable {

    private static final int ENTRANCES = 4;
    private static final int VISITORS = 4;
    private final int SCALE_FACTOR;
    private float timer;
    private float timeToNext;
    private Random rnd;
    private Texture[] playerTextures;
    private Game gameManager;
    private float ratio;
    private int level;
    private int previousEntrance;

    public VisitorEntrance(Game gameManager) {
        rnd = new Random();
        playerTextures = new Texture[VISITORS];
        this.gameManager = gameManager;

        SCALE_FACTOR = GameConstants.SCALE_FACTOR;
        for (int i = 0; i < VISITORS; i++) {
            playerTextures[i] = new Texture(Gdx.files.internal("visitor" + (i + 1) + ".png"));
        }
        reset();
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= timeToNext) {
            int pos = rnd.nextInt(ENTRANCES);
            int tex = rnd.nextInt(VISITORS);

            gameManager.addVisitor(new Visitor(gameManager.getBatch(), playerTextures[tex], pos * 16 * SCALE_FACTOR + 8 * SCALE_FACTOR, gameManager.getHeight() / 8 * 7, gameManager.getHeight() / 8 * 3));

            timer = 0;
            if (previousEntrance == pos)
                timeToNext = rnd.nextFloat() * ratio + 1;
            else
                timeToNext = rnd.nextFloat() * ratio + 1.4f;

            previousEntrance = pos;
            if (gameManager.getScore() / 4 > level) {
                level++;
                ratio -= 0.2f;
                if (ratio <= 0)
                    ratio = 0.18f;
            }
        }
    }

    public void reset() {
        level = 1;
        previousEntrance = -1;
        ratio = 2;
        timer = 0;
        timeToNext = rnd.nextFloat() * 2 + 1;
    }

    @Override
    public void dispose() {
        for (Texture t : playerTextures) {
            t.dispose();
        }
    }
}
