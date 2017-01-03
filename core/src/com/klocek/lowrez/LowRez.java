package com.klocek.lowrez;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class LowRez extends Game {

    private Screen game, menu, about, help;

    @Override
    public void create() {
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
        about.dispose();
        help.dispose();
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
