package com.klocek.lowrez;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Konrad on 2016-04-16.
 */
public class Menu extends ScreenAdapter {

    private LowRez game;
    private Texture menuTexture;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Vector3 input;

    public Menu(LowRez game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConstants.WIDTH, GameConstants.HEIGHT);
        camera.update();
        viewport = new StretchViewport(GameConstants.WIDTH, GameConstants.HEIGHT, camera);
        menuTexture = new Texture(Gdx.files.internal("menu.png"));
        input = new Vector3();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        Gdx.gl.glClearColor(255,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.apply();
        batch.begin();
        batch.draw(menuTexture, 0, 128); // :(
        batch.end();

        if(Gdx.input.justTouched()) {
            handleInput();
        }
    }

    private void handleInput() {
        //TODO
        input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        input = camera.unproject(input);
        if(input.x < 216 && input.y < 128 + 128 && input.y >= 128)
            game.playGame();
        else if(input.x > 294 && input.y < 128 + 128 && input.y >= 128)
            game.showHelp();
        else if(input.x > 294 && input.y >= 400 + 128)
            game.showAbout();
    }

    @Override
    public void dispose() {
        menuTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
