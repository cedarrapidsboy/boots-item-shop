package com.moosedrive.boots;

import java.io.IOException;
import java.util.List;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.armor.Boot;
import com.moosedrive.boots.utils.NameUtils;
import com.moosedrive.boots.world.Populace;
import com.moosedrive.boots.world.World;
import com.moosedrive.boots.world.shops.BootShop;

public class BootsGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Table table;

	private ScrollPane worldTextScroller;
	private Table worldText;
	private Label shopText;

	BootShop bootShop;
	Populace populace;

	@Override
	public void create() {
		try {
			lastFrameCount = TimeUtils.millis();
			NameUtils.initializeNames();
			batch = new SpriteBatch();
			bootShop = BootShop.getInstance();
			populace = Populace.getInstance(World.getOverworld());
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);

			worldText = new Table(skin);
			worldTextScroller = new ScrollPane(worldText, skin);
			worldText.align(Align.topLeft);
			worldTextScroller.setFadeScrollBars(false);
			shopText = new Label("shop-info", skin, "status");
			shopText.setAlignment(Align.topLeft);
			shopText.setFontScale(1.0F);

			table = new Table();
			table.setFillParent(true);
			stage.addActor(table);
			table.right().bottom();
			// TODO remove debugging line
			table.setDebug(true);

			table.add(new MapWidget(World.getOverworld())).expand().fill().align(Align.topLeft);
			table.add();
			table.row();
			table.add(worldTextScroller).maxHeight(200).expandX().fill().align(Align.topLeft);
			table.add(shopText).prefWidth(200).prefHeight(200).minHeight(200).minWidth(200);

			// TESTING
			// populace.populateWorld();

		} catch (IOException ex) {
			Logger.getLogger(BootsGame.class.getName()).log(Level.SEVERE, null, ex);
			this.dispose();
		}
	}

	private long frames = 0;
	private long lastFrameCount = 0;

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		updateTitle();
		// batch.begin();
		// batch.draw(img, 0, 0);
		// batch.end();
		processKeyPresses();
		populace.worldTick();
		updateText();

	}

	/**
	 * Update the game title (including FPS)
	 */
	private void updateTitle() {
		frames++;
		long currentTime = TimeUtils.millis();
		if ((currentTime - lastFrameCount) > 3000) {
			String title = "Boots Simulator" + " (FPS: " + frames / ((currentTime - lastFrameCount) / 1000) + " Mon: "
					+ populace.getMonsterCount() + " Cus: " + populace.getCustomerCount() + ")";
			Gdx.graphics.setTitle(title);
			lastFrameCount = currentTime;
			frames = 0;
		}
	}

	private void updateText() {

		worldText.clear();
		worldText.add(new Label("Name", skin)).getActor().setFontScale(0.75F);
		worldText.add(new Label("Health", skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
		worldText.add(new Label("DMG", skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
		worldText.add(new Label("AC", skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
		worldText.add(new Label("Gold", skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
		worldText.add(new Label("Inventory", skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
		worldText.add(new Label("Action", skin)).left().getActor().setFontScale(0.5F);
		worldText.row();
		List<String[]> worldRecords = populace.worldStatus();

		worldRecords.stream().forEach(s -> {

			for (int i = 0; i < s.length; i++) {
				if (i == s.length - 1) {
					worldText.add(new Label(s[i], skin)).expandX().left().getActor().setFontScale(0.5F);
				} else if (i == 0) {
					worldText.add(new Label(s[i], skin)).right().getActor().setFontScale(0.5F);
				} else {
					worldText.add(new Label(s[i], skin)).padLeft(10.0F).padRight(10.0F).getActor().setFontScale(0.5F);
				}

			}
			worldText.row();
		});
		shopText.setText(bootShop.stockText());
	}

	private boolean prevKeyDownC = false;
	private boolean prevKeyDownS = false;
	private boolean prevKeyDownB = false;

	private void processKeyPresses() {
		boolean keyDownC = Gdx.input.isKeyPressed(Input.Keys.C);
		boolean keyDownS = Gdx.input.isKeyPressed(Input.Keys.S);
		boolean keyDownB = Gdx.input.isKeyPressed(Input.Keys.B);
		if (keyDownC && !prevKeyDownC) {
			populace.addCustomer();
			prevKeyDownC = true;
		} else if (!keyDownC) {
			prevKeyDownC = false;
		}
		if (keyDownS && !prevKeyDownS) {
			for (int i = 0; i < 25; i++) {
				populace.addSpider();
			}
			prevKeyDownS = true;
		} else if (!keyDownS) {
			prevKeyDownS = false;
		}
		if (keyDownB && !prevKeyDownB) {
			Boot boot = ArmorFactory.getRandomBoot();
			int cost = BootShop.getBootCost(boot);
			if (bootShop.removeShopMoney(cost)) {
				bootShop.addBoot(boot);
			}
			prevKeyDownB = true;
		} else if (!keyDownB) {
			prevKeyDownB = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		// super.resize(width, height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}
}
