package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Created by Konrad on 2016-04-10.
 */
public class Game extends GameScreen {

    private Player player;
    private VisitorEntrance entrance;
    private ArrayList<Beer> beers;
    private ArrayList<Visitor> visitors;
    private Bar bar;
    private BeerTable beerTable;
    private boolean playerLost = false;

    private BeerLife beerLife;
    private Sound collisionSound, lostSound;
    private boolean isPlayed = false;
    private Texture lostTexture;
    private Pause pause;
    private ScoreFont scoreFont;

    public Game(LowRez game) {
        super(game);
        lostTexture = new Texture(Gdx.files.internal("lost.png"));
        pause = new Pause(this);
        beerLife = new BeerLife(this);
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));
        lostSound = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
        entrance = new VisitorEntrance(this);
        player = new Player(this);
        bar = new Bar(getBatch(), getHeight() / 5);
        beers = new ArrayList<>();
        beerTable = new BeerTable(getBatch(), getWidth() / 8 * 7, getHeight() / 5);
        visitors = new ArrayList<>();
        scoreFont = new ScoreFont();
    }

    @Override
    public void render(float delta) {
        getBatch().setProjectionMatrix(getCamera().projection);
        getBatch().setTransformMatrix(getCamera().view);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getCamera().update();
        getViewport().apply();
        if (pause.isPaused()) {
           renderPause(delta);
        } else {
            if (!playerLost) {
                renderGame(delta);
            } else {
                renderEndGame(delta);
            }
        }
    }

    public void goLeft() {
        player.goLeft();

        if (getPlayer().isFirstBeerLane())
            getControls().setLeftArrowIdle(true);
        else {
            getControls().setLeftArrowIdle(false);
            getControls().setRightArrowIdle(false);
        }
    }

    public void pickBeer() {
        if (!playerLost) {
            player.pickBeer();
            getControls().setBeerIdle(true);
        } else
            getGame().backToMenu();
    }

    public boolean isBeer() {
        return player.hasBeer();
    }

    public void goRight() {
        player.goRight();

        if (getPlayer().isLastBeerLane())
            getControls().setRightArrowIdle(true);
        else {
            getControls().setRightArrowIdle(false);
            getControls().setLeftArrowIdle(false);
        }
    }

    public void addBeer(Beer b) {
        beers.add(b);
    }

    public BeerTable getBeerTable() {
        return beerTable;
    }

    public void addVisitor(Visitor v) {
        visitors.add(v);
    }

    public int getScore() {
        return player.getScore();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void dispose() {
        player.dispose();
        entrance.dispose();
        beerTable.dispose();
        beerLife.dispose();
        collisionSound.dispose();
        lostSound.dispose();
        pause.dispose();
        scoreFont.dispose();
        getControls().dispose();
        bar.dispose();
        lostTexture.dispose();
        getBatch().dispose();
    }

    public void reset() {
        isPlayed = false;
        playerLost = false;
        player.reset();
        beerLife.reset();
        beerTable.reset();
        beers.clear();
        visitors.clear();
        getControls().reset();
    }

    private void renderGame(float delta) {
        getBatch().begin();
        super.render(delta);
        getControls().update(delta);
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
        getBatch().end();
        //Colisions
        for (Beer b : beers) {
            for (Visitor v : visitors) {
                if (v.getBarRectangle().overlaps(b.getBeerSprite().getBoundingRectangle())) {
                    player.addPoint();
                    b.markEnded();
                    v.markEnded();
                    collisionSound.play();
                }
            }
        }

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
    }

    private void renderEndGame(float delta) {
        if (!isPlayed) {
            lostSound.play();
            isPlayed = true;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
        getBatch().draw(lostTexture, 0, getHeight() / 5);
        scoreFont.draw(player.getScore(), getBatch());
        getControls().update(delta);
        getControls().setBeerIdle(false);
        getControls().setLeftArrowIdle(true);
        getControls().setRightArrowIdle(true);
        getBatch().end();
    }

    private void renderPause(float delta) {
        getBatch().begin();
        pause.update(delta);
        getBatch().end();
    }

}
