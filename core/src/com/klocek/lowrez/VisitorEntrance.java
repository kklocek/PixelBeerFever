package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Created by Konrad on 2016-04-11.
 */
public class VisitorEntrance {

    private static final int ENTRANCES = 4;
    private static final int VISITORS = 4;
    private final int SCALE_FACTOR;
    private float timer = 0;
    private float timeToNext;
    private Random rnd;
    private Texture [] playerTextures;
    private Main gameManager;
    private float ratio = 2;
    private int level = 1;
    private int previousEntrance = -1;

    public VisitorEntrance(Main gameManager) {
        rnd = new Random();
        timeToNext = rnd.nextFloat() * 2 + 1;
        playerTextures = new Texture[VISITORS];
        this.gameManager = gameManager;

        SCALE_FACTOR = GameConstants.SCALE_FACTOR;
        for(int i = 0; i < VISITORS; i++)
            playerTextures[i] = new Texture(Gdx.files.internal("visitor" + (i + 1) + ".png"));
    }

    public void update(float delta) {
        timer += delta;
        if(timer >= timeToNext) {
            int pos = rnd.nextInt(ENTRANCES);
            int tex = rnd.nextInt(VISITORS);

            gameManager.addVisitor(new Visitor(gameManager.getBatch(), playerTextures[tex], pos * 16 * SCALE_FACTOR + 8 * SCALE_FACTOR, gameManager.getHeight() / 8 * 7, gameManager.getHeight() / 8 * 3));

            timer = 0;
            if(previousEntrance == pos)
                timeToNext = rnd.nextFloat() * ratio + 1;
            else
                timeToNext = rnd.nextFloat() * ratio + 1.4f;

            previousEntrance = pos;
            if(gameManager.getScore() / 4 > level) {
                level++;
                ratio -= 0.2f;
                if(ratio <= 0)
                    ratio = 0.18f;
            }
        }
    }
}
