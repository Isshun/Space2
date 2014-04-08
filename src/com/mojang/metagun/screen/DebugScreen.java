package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.ShipModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.TextView;
import com.mojang.metagun.ui.View.OnClickListener;

public class DebugScreen extends Screen {

	@Override
	protected void onCreate () {
		int i = 0;
		for (final SystemModel s: GameService.getInstance().getSystems()) {
			String str = s.getOwner() == null ? s.getName() : s.getName() + ": " + s.getOwner().getName();
			TextView text = new TextView(str, 4, 12 + i++ * 10);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick () {
					addScreen(new DebugSystemScreen(s));
				}
			});
			addView(text);
		}
	}

	@Override
	public void onTouch (int x, int y) {
//		if (x < Constants.GAME_WIDTH / 2) {
//			GameService.getInstance().initDebug(-1);
//		} else {
//			GameService.getInstance().initDebug(1);
//		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		drawString("map index: " + GameService.getInstance().mMapIndex, 4, 4);
		

		PlayerModel player = GameService.getInstance().getPlayer();
		List<ShipModel> builds = new ArrayList<ShipModel>();
		for (PlanetModel planet: player.getPlanets()) {
			builds.addAll(planet.getBuilds());
		}
		int i = 0;
		for (ShipModel build: builds) {
			drawString("build: " + build.getClassName() + " (" + build.getPlanet().getName() + " / " + build.getBuildRemain() + ")", 200, 4 + 10 * i++);
		}

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
