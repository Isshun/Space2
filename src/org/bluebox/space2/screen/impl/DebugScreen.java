package org.bluebox.space2.screen.impl;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.model.ShipModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.screen.ScreenBase;
import org.bluebox.space2.screen.ScreenLayerBase;
import org.bluebox.space2.service.GameService;
import org.bluebox.space2.ui.TextView;
import org.bluebox.space2.ui.View.OnClickListener;


public class DebugScreen extends ScreenBase {

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
	public void onDraw (ScreenLayerBase mainLayer, ScreenLayerBase UILayer) {
		mainLayer.drawString("map index: " + GameService.getInstance().getData().systemMapIndex, 4, 4);
		

		PlayerModel player = GameService.getInstance().getPlayer();
		List<ShipModel> builds = new ArrayList<ShipModel>();
		for (PlanetModel planet: player.getPlanets()) {
			builds.addAll(planet.getBuilds());
		}
		int i = 0;
		for (ShipModel build: builds) {
			mainLayer.drawString("build: " + build.getClassName() + " (" + build.getPlanet().getName() + " / " + build.getBuildRemain() + ")", 200, 4 + 10 * i++);
		}

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
