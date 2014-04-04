package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.ShipClassModel;
import com.mojang.metagun.model.ShipModel;
import com.mojang.metagun.service.GameService;

public class PlanetBuildShipScreen extends Screen {

	private static final int 	POS_Y = Constants.GAME_HEIGHT - 100;
	private static final int 	LINE_INTERVAL = 12;
	
	private PlanetModel 			mPlanet;

	public PlanetBuildShipScreen (Screen parent, PlanetModel planet) {
		mParent = parent;
		mPlanet = planet;
//		mSpriteBatch = parent.getSpriteBatch();
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		drawRectangle(0, POS_Y, Constants.GAME_WIDTH, 100, new Color(0.2f, 0.2f, 0.2f, 0.85f));

		drawString("Design", 4, POS_Y + 5);
		drawRectangle(Constants.GAME_WIDTH / 3, POS_Y, 1, 200, new Color(0, 0, 0, 0.45f));
		drawString("Spacedock", Constants.GAME_WIDTH / 3 + 5, POS_Y + 5);
		drawRectangle(Constants.GAME_WIDTH / 3 * 2, POS_Y, 1, 200, new Color(0, 0, 0, 0.45f));
		drawString("In orbit", Constants.GAME_WIDTH / 3 * 2 + 5, POS_Y + 5);
		
		List<ShipClassModel> classes = GameService.getInstance().getShipClasses();
		int i = 0;
		for (ShipClassModel sc: classes) {
			drawString(sc.getName(), 4, POS_Y + 18 + i * LINE_INTERVAL);
			i++;
		}

		List<ShipModel> builds = mPlanet.getBuilds();
		int j = 0;
		for (ShipModel sc: builds) {
			drawString(sc.getClassName(), Constants.GAME_WIDTH / 3 + 5, POS_Y + 18 + j * LINE_INTERVAL);
			drawString(String.valueOf(sc.getBuildRemain()), Constants.GAME_WIDTH / 3 + 120, POS_Y + 18 + j * LINE_INTERVAL);
			j++;
		}

	}

	@Override
	public void onTouch (int x, int y) {
		if (y > POS_Y + 18) {
			List<ShipClassModel> classes = GameService.getInstance().getShipClasses();
			int index = (y - POS_Y - 18) / LINE_INTERVAL;
			if (x < Constants.GAME_WIDTH / 2 && index < classes.size()) {
				mPlanet.addBuild(new ShipModel(classes.get(index)));
			}
			if (x > Constants.GAME_WIDTH / 2) {
				System.out.println("remove build");
			}
		} else if (y < POS_Y) {
			back();
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

}
