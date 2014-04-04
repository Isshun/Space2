package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.ShipModel;
import com.mojang.metagun.service.GameService;

public class DebugScreen extends Screen {

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouch (int x, int y) {
		if (x < Constants.GAME_WIDTH / 2) {
			GameService.getInstance().initDebug(-1);
		} else {
			GameService.getInstance().initDebug(1);
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		drawString("map index: " + GameService.getInstance().mMapIndex, 4, 4);
		

		PlayerModel player = GameService.getInstance().getPlayer();
		List<ShipModel> builds = new ArrayList<ShipModel>();
		for (PlanetModel planet: player.getPlanets()) {
			builds.addAll(planet.getBuilds());
		}
		int i = 0;
		for (ShipModel build: builds) {
			drawString("build: " + build.getPlanet().getName() + "(" + build.getBuildRemain() + ")", 200, 4 + 10 * i++);
		}

	}

}
