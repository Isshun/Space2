package org.bluebox.space2.game.screen;

import org.bluebox.space2.Utils;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.StructureModel;
import org.bluebox.space2.game.model.PlanetModel;

import com.badlogic.gdx.graphics.Color;

public class PlanetStructureScreen extends BaseScreen {

	private PlanetModel mPlanet;
	private Color mColor;
	private int mTotalETA;
	private ButtonView mBtCancel;

	public PlanetStructureScreen (PlanetScreen parent, PlanetModel planet) {
		mParent = parent;
		parent.notifyChange();
		mPlanet = planet;
		mRefreshOnUpdate = true;
		mColor = new Color(1, 1, 1, 0.45f);
	}

	@Override
	protected void onCreate () {
		super.onCreate();
	}

	@Override
	protected void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.drawRectangle(6, 32, Constants.GAME_WIDTH - 12, Constants.GAME_HEIGHT - 40, mColor);

		int i = 0;
		mTotalETA = 0;
		for (StructureModel building: mPlanet.getStructuresToBuild()) {
			drawIcon(mainLayer, building, 8 + 34 * i, 34 + 34 * 0, false);
			i++;
		}
		for (StructureModel building: mPlanet.getStructures()) {
			drawIcon(mainLayer, building, 8 + 34 * i, 34 + 34 * 0, true);
			i++;
		}
	}

	private void drawIcon (BaseScreenLayer mainLayer, StructureModel building, int x, int y, boolean isBuild) {
		if (isBuild) {
			String shortName = building.getShortName();
			if (shortName != null && shortName.length() > 3) {
				shortName = shortName.substring(0, 3) + ".";
			}
			mainLayer.draw(building.getIcon(), x, y);
			mainLayer.drawRectangle(x, y + 16, 32, 16, new Color(0, 0, 0, 0.5f));
			mainLayer.drawString(shortName, x + 2, y + 18);
		} else {
			String shortName = building.getShortName();
			if (shortName != null && shortName.length() > 3) {
				shortName = shortName.substring(0, 3) + ".";
			}
			mTotalETA += mPlanet.getBuildETA(building.getBuildRemain());
			String eta = Utils.getFormatedTime(mTotalETA);
			mainLayer.draw(building.getIcon(), x, y);
			mainLayer.drawRectangle(x, y + 16, 32, 16, new Color(0, 0, 0, 0.5f));
			mainLayer.drawString("Build", x + 2, y + 18);
			mainLayer.drawString(eta, x + 2, y + 25);
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
