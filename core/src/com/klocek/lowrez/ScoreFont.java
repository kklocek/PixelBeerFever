package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-12-12.
 */
public class ScoreFont implements Disposable {

    private static final int DIGITS = 10;
    private static final int DIGIT_WIDTH = 4 * GameConstants.SCALE_FACTOR;
    private static final int X = 440;
    private static final int Y = 272; //TODO: Refactor

    private Texture[] numbers;

    public ScoreFont() {
        numbers = new Texture[DIGITS];
        for (int i = 0; i < DIGITS; i++) {
            numbers[i] = new Texture(Gdx.files.internal("numbers/" + i + ".png"));
        }
    }

    public void draw(int score, SpriteBatch batch) {
        int scoreDigit;
        int loops = 0;
        do {
            scoreDigit = score % 10;
            score = score / 10;
            batch.draw(numbers[scoreDigit], X - loops * (DIGIT_WIDTH + GameConstants.SCALE_FACTOR), Y);
            loops++;
        } while (score != 0);
    }

    @Override
    public void dispose() {
        for (Texture n : numbers) {
            n.dispose();
        }
    }
}
