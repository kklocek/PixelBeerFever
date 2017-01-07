package com.klocek.lowrez;

import com.badlogic.gdx.Screen;

public class LowRez extends com.badlogic.gdx.Game {

    private Screen menu, about, help;
    private Game game;

    @Override
    public void create() {
        menu = new Menu(this);
        game = new Game(this);
        about = new About(this);
        help = new Help(this);
        showInit();
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
        showInit();
    }

    public void playGame() {
        game.reset();
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

    private void showInit() {
        setScreen(menu);
        screen.show();
    }
}
