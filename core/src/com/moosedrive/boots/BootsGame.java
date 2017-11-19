package com.moosedrive.boots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.moosedrive.boots.utils.NameUtils;
import com.moosedrive.boots.world.Populace;
import com.moosedrive.boots.world.shops.BootShop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BootsGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    BootShop bootShop;
    Populace populace;

    @Override
    public void create() {
        try {
            NameUtils.initializeNames();
            batch = new SpriteBatch();
            img = new Texture("badlogic.jpg");
            bootShop = BootShop.getInstance();
            populace = Populace.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(BootsGame.class.getName()).log(Level.SEVERE, null, ex);
            this.dispose();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
