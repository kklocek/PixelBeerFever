package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Konrad on 2016-04-16.
 */
public class Menu extends GameScreen {

    private Texture menuTexture;
    private Vector3 input;

    public Menu(LowRez game) {
        super(game);
        menuTexture = new Texture(Gdx.files.internal("menu.png"));
        getControls().setIdle(true);
        input = new Vector3();
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
        getBatch().draw(menuTexture, 0, 128); // :(
        getControls().update(delta);
        getBatch().end();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }

        if (Gdx.input.justTouched()) {
            handleInput();
        }
    }

    private void handleInput() {
        input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        input = getCamera().unproject(input);
        if (input.x < 216 && input.y < 128 + 128 && input.y >= 128)
            getGame().playGame();
        else if (input.x > 294 && input.y < 128 + 128 && input.y >= 128)
            getGame().showHelp();
        else if (input.x > 294 && input.y >= 400 + 128)
            getGame().showAbout();
    }

    @Override
    public void dispose() {
        menuTexture.dispose();
    }

}
