package com.moosedrive.boots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.moosedrive.boots.utils.NameUtils;
import com.moosedrive.boots.world.Populace;
import com.moosedrive.boots.world.shops.BootShop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BootsGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Table table;

    private Label customersLabel;
    private Label customersCountLabel;

    private Label bootLabel;
    private Label bootCountLabel;
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
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);

            customersLabel = new Label("Customers:", skin);
            customersCountLabel = new Label("0", skin);

            bootLabel = new Label("Boots:", skin);
            bootCountLabel = new Label("0", skin);

            table = new Table();
            table.setFillParent(true);
            stage.addActor(table);
            table.right().bottom();
            //TODO remove debugging line
            table.setDebug(true);

            table.add(customersLabel);
            table.add(customersCountLabel).width(100);
            table.row();
            table.add(bootLabel);
            table.add(bootCountLabel).width(100);

        } catch (IOException ex) {
            Logger.getLogger(BootsGame.class.getName()).log(Level.SEVERE, null, ex);
            this.dispose();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bootCountLabel.setText(String.valueOf(bootShop.count()));
        customersCountLabel.setText(String.valueOf(populace.count()));
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //batch.begin();
        //batch.draw(img, 0, 0);
        //batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        stage.dispose();
    }
}
