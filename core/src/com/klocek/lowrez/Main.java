package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;

import java.util.ArrayList;

/**
 * Created by Konrad on 2016-04-10.
 */
public class Main extends ScreenAdapter {

    private SpriteBatch batch;
    private LowRez game;
    private OrthographicCamera camera;
    private Controls controls;
    private Player player;
    private VisitorEntrance entrance;
    private ArrayList<Beer> beers;
    private ArrayList<Visitor> visitors;
    private Bar bar;
    private BeerTable beerTable;
    private boolean playerLost = false;
    private BitmapFont font;
    private Viewport viewport;
    private BeerLife beerLife;
    private Sound collisionSound, lostSound;
    private boolean isPlayed = false;
    private int lostBeers = 0;
    private Texture lostTexture;
    private Pause pause;

    private static final int WIDTH = 512, HEIGHT = 512 + 128, BEERS_LOST = 3;

    public Main(LowRez game) {
        super();
        batch = new SpriteBatch();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        lostTexture = new Texture(Gdx.files.internal("lost.png"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        font.setColor(Color.RED);
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        pause = new Pause(this);
        beerLife = new BeerLife(this);
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));
        lostSound = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
        entrance = new VisitorEntrance(this);
        player = new Player(this);
        controls = new Controls(this);
        bar = new Bar(batch, HEIGHT / 5);
        beers = new ArrayList<>();
        beerTable = new BeerTable(batch, WIDTH / 8 * 7, HEIGHT / 5);
        visitors = new ArrayList<>();
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.apply();
        if(pause.isPaused()) {
            batch.begin();
            pause.update(delta);
            batch.end();
        } else {
            if (!playerLost) {
                batch.begin();
                super.render(delta);
                controls.update(delta);
                bar.update(delta);
                pause.update(delta);
                beerTable.update(delta);
                entrance.update(delta);
                player.update(delta);
                for (Beer b : beers) {
                    b.update(delta);
                }

                for (Visitor v : visitors) {
                    v.update(delta);
                }
                beerLife.update(delta);
                batch.end();
                //Colisions
                for (Beer b : beers) {
                    for (Visitor v : visitors) {
                        if (v.getBarRectangle().overlaps(b.getBeerSprite().getBoundingRectangle())) {
                            if (v.isChild()) {
                                playerLost = true;
                            } else {
                                player.addPoint();
                                b.markEnded();
                                v.markEnded();
                                collisionSound.play();
                            }
                        }
                    }
                }

                //TODO: CHECK!
                for (int i = 0; i < beers.size(); i++) {
                    if (!beers.get(i).isWithCollision()) {
                        beerLife.lostBeer();
                    }
                    if (beers.get(i).isEnded()) {
                        beers.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < visitors.size(); i++) {
                    if (visitors.get(i).isLost()) {
                        playerLost = true;
                        break;
                    }

                    if (visitors.get(i).isEnded()) {
                        visitors.remove(i);
                        i--;
                    }
                }

                if (beerLife.getLifes() == 0) {
                    playerLost = true;
                }
            } else {
                if (!isPlayed) {
                    lostSound.play();
                    isPlayed = true;
                }
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(lostTexture, 0, HEIGHT / 5);
                font.draw(batch, player.getScore() + "", 344, 376);
                batch.end();
                if (Gdx.input.justTouched()) {
                    game.backToMenu();
                }
            }
        }

    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void goLeft() {
        player.goLeft();
    }

    public void pickBeer() {
        player.pickBeer();
    }

    public boolean isBeer() {
        return player.hasBeer();
    }

    public void goRight() {
        player.goRight();
    }

    public void addBeer(Beer b) {
        beers.add(b);
    }

    public BeerTable getBeerTable() {
        return beerTable;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void addVisitor(Visitor v) {
        visitors.add(v);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public int getScore() {
        return player.getScore();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        player.dispose();
        controls.dispose();
        bar.dispose();
        //Beer.getBeerTexture().dispose();
        lostTexture.dispose();
        batch.dispose();
    }

}
