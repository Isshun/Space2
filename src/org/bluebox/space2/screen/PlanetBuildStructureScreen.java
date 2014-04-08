package org.bluebox.space2.screen;

import java.util.List;

import org.bluebox.space2.Constants;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.ShipClassModel;
import org.bluebox.space2.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class PlanetBuildStructureScreen extends Screen {

	public PlanetBuildStructureScreen (Screen parent, PlanetModel mPlanet) {
		mParent = parent;
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		int posY = Constants.GAME_HEIGHT - 100;

		drawRectangle(0, posY, Constants.GAME_WIDTH, 100, new Color(0.5f, 0.5f, 1, 0.5f));
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
