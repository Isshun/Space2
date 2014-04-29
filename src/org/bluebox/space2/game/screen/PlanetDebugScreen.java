package org.bluebox.space2.game.screen;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.model.BuildingModel;
import org.bluebox.space2.game.model.PlanetModel;

public class PlanetDebugScreen extends BaseScreen {

	private PlanetModel mPlanet;

	public PlanetDebugScreen (PlanetModel planet) {
		mPlanet = planet;
		mRefreshOnUpdate = true;
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		int line = 0;
		
		mainLayer.drawString("pop: " + mPlanet.getPopulation(), 4, 4 + line++ * 10);

		mainLayer.drawString(String.format("base values: *%d @%d ^%d $%d &%d",
			(int)mPlanet.getBaseProd(),
			(int)mPlanet.getBaseCulture(),
			(int)mPlanet.getBaseFood(),
			(int)mPlanet.getBaseMoney(),
			(int)mPlanet.getBaseScience()), 4, 4 + line++ * 10);

		mainLayer.drawString(String.format("current values: *%d @%d ^%d $%d &%d",
			(int)mPlanet.getProd(),
			(int)mPlanet.getCulture(),
			(int)mPlanet.getFood(),
			(int)mPlanet.getMoney(),
			(int)mPlanet.getScience()), 4, 4 + line++ * 10);

		mainLayer.drawString(String.format("current modifiers: *%d%% @%d%% ^%d%% $%d%% &%d%%",
			(int)(mPlanet.getProdModifier() * 100),
			(int)(mPlanet.getCultureModifier() * 100),
			(int)(mPlanet.getFoodModifier() * 100),
			(int)(mPlanet.getMoneyModifier() * 100),
			(int)(mPlanet.getScienceModifier() * 100)), 4, 4 + line++ * 10);

		int oldLine = ++line;
		mainLayer.drawString("installations: ", 4, 4 + line++ * 10);
		for (BuildingModel building: mPlanet.getStructures()) {
			mainLayer.drawString(building.getName(), 4, 4 + line++ * 10);
		}

		line = oldLine;
		mainLayer.drawString("building: ", 200, 4 + line++ * 10);
		for (BuildingModel building: mPlanet.getStructuresToBuild()) {
			mainLayer.drawString(building.getName(), 200, 4 + line++ * 10);
		}

	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

}
