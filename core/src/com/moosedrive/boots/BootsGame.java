package com.moosedrive.boots;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.moosedrive.boots.utils.NameUtils;
import com.moosedrive.boots.world.Populace;
import com.moosedrive.boots.world.shops.BootShop;

public class BootsGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Table table;

    private ScrollPane worldTextScroller;
    private Label worldText;
    private Label shopText;

    BootShop bootShop;
    Populace populace;

    @Override
    public void create() {
        try {
            NameUtils.initializeNames();
            batch = new SpriteBatch();
            bootShop = BootShop.getInstance();
            populace = Populace.getInstance();
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);

            worldText = new Label("world-info", skin, "status");
            worldText.setWrap(true);
            worldTextScroller = new ScrollPane(worldText, skin);
            worldTextScroller.setFadeScrollBars(false);
            shopText = new Label("shop-info", skin, "status");
            worldText.setAlignment(Align.topLeft);
            shopText.setAlignment(Align.topLeft);

            table = new Table();
            table.setFillParent(true);
            stage.addActor(table);
            table.right().bottom();
            //TODO remove debugging line
            table.setDebug(true);

            table.add(worldTextScroller).expand().fill();
            table.add();
            table.row();
            table.add();
            table.add(shopText).prefWidth(200).prefHeight(200).minHeight(200).minWidth(200);

        } catch (IOException ex) {
            Logger.getLogger(BootsGame.class.getName()).log(Level.SEVERE, null, ex);
            this.dispose();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //batch.begin();
        //batch.draw(img, 0, 0);
        //batch.end();
        processKeyPresses();
        updateText();

    }

    private void updateText() {
        worldText.setText(populace.worldStatusText());
        shopText.setText(bootShop.stockText());
    }

    private boolean prevKeyDownC = false;
    private boolean prevKeyDownB = false;
    private void processKeyPresses() {
        boolean keyDownC = Gdx.input.isKeyPressed(Input.Keys.C);
        boolean keyDownB = Gdx.input.isKeyPressed(Input.Keys.B);
        if (keyDownC && !prevKeyDownC){
            populace.addCustomerToWorld();
            prevKeyDownC = true;
        } else if (!keyDownC){
            prevKeyDownC = false;
        }
        if (keyDownB && !prevKeyDownB){
            bootShop.addBoot();
            prevKeyDownB = true;
        } else if (!keyDownB){
            prevKeyDownB = false;
        }
    }


    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
