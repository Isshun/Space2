package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.FleetModel;

import com.badlogic.gdx.graphics.Color;

public class PanelArmadaScreen extends BaseScreen {

	private static final int START_Y = 36;
	private static final int LINE_HEIGHT = 15;
	private static final int SPACING_X = 50;
	private static final int START_X = 120;

	private List<FleetModel> 	mFleets;
	
	@Override
	protected void onCreate () {
		super.onCreate();

		mFleets = new ArrayList<FleetModel>(mPlayer.getFleets());
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.draw(Art.bg_1, 0, 0);
		
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString("ARMADA", 6, 6);

		mainLayer.drawString("Name", 		6, 24);
		
		mainLayer.drawString("Loc.", 	START_X, 24);
		mainLayer.drawString("Ships", START_X + 60 + SPACING_X * 1, 24);
		mainLayer.drawString("Pow.", 	START_X + 60 + SPACING_X * 2, 24);
		mainLayer.drawString("Def.",	START_X + 60 + SPACING_X * 3, 24);
		
		int x = 6;
		int i = 0;
		for (FleetModel fleet : mFleets) {
			mainLayer.drawRectangle(4, START_Y + i * (LINE_HEIGHT + 1), Constants.GAME_WIDTH - 8, LINE_HEIGHT, i % 2 == 0 ? Color.rgba8888(0.85f, 1, 0.85f, 0.55f) : Color.rgba8888(0.85f, 1, 0.85f, 0.4f));
			mainLayer.setStringMaxWidth(16 * 6);
			mainLayer.drawString(fleet.getName(), 8, START_Y + 5 + i * (LINE_HEIGHT + 1));
			mainLayer.setStringMaxWidth(16 * 6);
			mainLayer.drawString(fleet.getLocationName(), START_X + 2, START_Y + 5 + i * (LINE_HEIGHT + 1));
			mainLayer.drawString(String.valueOf((int)fleet.getNbShip()), START_X + 62 + SPACING_X * 1, START_Y + 5 + i * (LINE_HEIGHT + 1));
			mainLayer.drawString(String.valueOf((int)fleet.getAttackIndice()), START_X + 62 + SPACING_X * 2, START_Y + 5 + i * (LINE_HEIGHT + 1));
			mainLayer.drawString(String.valueOf((int)fleet.getDefenseIndice()), START_X + 62 + SPACING_X * 3, START_Y + 5 + i * (LINE_HEIGHT + 1));
			i++;
		}
	}

	@Override
	public void onTouch (int x, int y) {
		if (y > START_Y) {
			int pos = (y - START_Y) / (LINE_HEIGHT + 1);
			if (mFleets.size() > pos) {
				addScreen(new FleetInfoScreen(mFleets.get(pos)));
			}
		}
	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
