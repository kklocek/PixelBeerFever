package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Konrad on 2016-04-17.
 */
public class Help extends GameScreen {

    private Texture helpTexture1;
    private Texture helpTexture2;
    private Texture actual;

    public Help(LowRez game) {
        super(game);
        helpTexture1 = new Texture(Gdx.files.internal("help1.png"));
        helpTexture2 = new Texture(Gdx.files.internal("help2.png"));
        actual = helpTexture1;
        getControls().setRightArrowIdle(false);
        getControls().setLeftArrowIdle(true);
    }

    @Override
    public void goLeft() {
        if(actual == helpTexture2) {
            actual = helpTexture1;
            getControls().setLeftArrowIdle(true);
            getControls().setRightArrowIdle(false);
        }
    }

    @Override
    public void goRight() {
        if(actual == helpTexture1) {
            actual = helpTexture2;
            getControls().setLeftArrowIdle(false);
        } else {
            getGame().backToMenu();
        }
    }

    @Override
    public void render(float delta) {
        getBatch().setProjectionMatrix(getCamera().projection);
        getBatch().setTransformMatrix(getCamera().view);
        Gdx.gl.glClearColor(255, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getCamera().update();
        getViewport().apply();
        getBatch().begin();
        getBatch().draw(actual, 0, 128);
        getControls().update(delta);
        getBatch().end();
    }

    @Override
    public void dispose() {
        helpTexture1.dispose();
        helpTexture2.dispose();
    }

}
