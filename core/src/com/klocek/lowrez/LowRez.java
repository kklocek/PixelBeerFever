package com.klocek.lowrez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LowRez extends Game {

    private Screen game, menu, about, help;

	@Override
	public void create () {
        menu = new Menu(this);
        setScreen(menu);
        game = new Main(this);
        about = new About(this);
        help = new Help(this);
        screen.show();
	}

    @Override
    public void dispose() {
        super.dispose();
        game.dispose();
        menu.dispose();
    }

    public void backToMenu() {
        game.dispose();
        create();
    }

    public void playGame() {
        setScreen(game);
        screen.show();
    }

    public void showAbout() {
        setScreen(about);
        screen.show();
    }

    public void showHelp() {
        setScreen(help);
        screen.show();
    }
}
