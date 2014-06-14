package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.TextView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.service.GameService;


public class DebugScreen extends BaseScreen {

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
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.drawString("map index: " + GameService.getInstance().getData().systemMapIndex, 4, 4);
		

		PlayerModel player = GameService.getInstance().getPlayer();
		List<ShipModel> builds = new ArrayList<ShipModel>();
		for (PlanetModel planet: player.getPlanets()) {
			if (planet.getDock() != null) {
				builds.addAll(planet.getDock().getShipToBuild());
			}
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
