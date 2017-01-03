package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Konrad on 2016-04-17.
 */
public class Help extends GameScreen {

    private Texture helpTexture;

    public Help(LowRez game) {
        super(game);
        helpTexture = new Texture(Gdx.files.internal("help.png"));
    }

    @Override
    public void goLeft() {

    }

    @Override
    public void goRight() {

    }

    @Override
    public void pickBeer() {

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
        getBatch().draw(helpTexture, 0, 128);
        getControls().update(delta);
        getBatch().end();

        if (Gdx.input.justTouched()) {
            getGame().backToMenu();
        }
    }

    @Override
    public void dispose() {
        helpTexture.dispose();
    }

}
