package org.bluebox.space2.screen;

import java.util.List;

import org.bluebox.space2.Constants;
import org.bluebox.space2.StringConfig;
import org.bluebox.space2.Game.Anim;
import org.bluebox.space2.model.BuildingClassModel;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.ShipClassModel;
import org.bluebox.space2.service.GameService;
import org.bluebox.space2.ui.View.OnClickListener;

import com.badlogic.gdx.graphics.Color;

public class PlanetBuildStructureScreen extends Screen {
	
	private static final int POPUP_PADDING = 42; 
	private static final int POPUP_WIDTH = Constants.GAME_WIDTH - POPUP_PADDING * 2; 
	private static final int POPUP_HEIGHT = Constants.GAME_HEIGHT - POPUP_PADDING * 2;
	private static final int GRID_SIZE = 80;
	private static final int GRID_NB_COLUMNS = 4;
	private static final int SEP = POPUP_WIDTH / 6 * 4;
	
	private ButtonView mBtBuild;
	protected int mSelected;
	protected PlanetModel mPlanet;
	protected List<BuildingClassModel> mBuildings;

	public PlanetBuildStructureScreen (Screen parent, PlanetModel planet) {
		mParent = parent;
		parent.notifyChange();
		mPlanet = planet;
		mOutTransition = Anim.GO_DOWN;
		mBuildings = GameService.getInstance().getBuildingClasses();
	}

	@Override
	protected void onCreate () {
		mBtBuild = new ButtonView(POPUP_PADDING + SEP + 11, POPUP_PADDING + POPUP_HEIGHT - 32, POPUP_WIDTH - SEP - 22, 22, new Color(0.2f, 1, 0.2f, 1));
		mBtBuild.setText("structure");
		mBtBuild.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				if (mSelected < mBuildings.size()) {
					mPlanet.buildStructure(mBuildings.get(mSelected));
					back();
				}
			}
		});
		addView(mBtBuild);
	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		int posY = Constants.GAME_HEIGHT - 100;
		
		System.out.println("popup width: " + POPUP_WIDTH + ", sep: " + SEP + ", sub: " + (POPUP_WIDTH - SEP));

		// Background
		drawRectangle(POPUP_PADDING, POPUP_PADDING, POPUP_WIDTH, POPUP_HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.85f));
				
		int i = 0;
		for (BuildingClassModel b: mBuildings) {
			drawIcon(b, POPUP_PADDING + 10 + (i % GRID_NB_COLUMNS) * GRID_SIZE, POPUP_PADDING + 10 + (i / GRID_NB_COLUMNS) * GRID_SIZE, mSelected == i);
			i++;
		}
		
		if (mSelected < mBuildings.size()) {
			drawInfo(mBuildings.get(mSelected), POPUP_PADDING + SEP, POPUP_PADDING, POPUP_WIDTH - SEP, POPUP_HEIGHT);
		}
	}

	private void drawIcon (BuildingClassModel building, int posX, int posY, boolean isSelected) {
		drawRectangle(posX, posY, GRID_SIZE - 10, GRID_SIZE - 10, isSelected ? new Color(0.75f, 1, 0.75f, 0.65f) : new Color(1, 1, 1, 0.45f));
		drawString(building.getShortName(), posX, posY);
	}

	private void drawInfo (BuildingClassModel building, int startX, int startY, int width, int height) {
//		// Background
//		drawRectangle(startX, startY, width, height, new Color(0.5f, 1, 0.5f, 0.5f));

		// Separation
		drawRectangle(startX, startY, 2, Constants.GAME_HEIGHT - POPUP_PADDING - POPUP_PADDING, new Color(1, 1, 1, 0.45f));

		// Building title
		setStringSize(StringConfig.SIZE_BIG);
		setStringMultiline(true);
		setStringMaxWidth(width - 22);
		int lines = drawString(building.getName(), startX + 10, startY + 10);

		// Building effect
		setStringColorNumbers(true);
		drawString(building.getEffect(), startX + 10, startY + 7 + (lines * 20));
		
		// Building description
		setStringMultiline(true);
		setStringMaxWidth(width - 22);
		drawString(building.getDesc(), startX + 10, startY + 20 + (lines * 20));
		
		// Button build
		drawRectangle(startX + 11, startY + height - 32, width - 22, 22, new Color(0.8f, 1, 0.8f, 0.5f));
		drawString("build", startX + 11, startY + height - 32);

		// Button cancel
		drawRectangle(startX + 11, startY + height - 62, width - 22, 22, new Color(1, 0.8f, 0.8f, 0.5f));
		drawString("close", startX + 11, startY + height - 62);
	}

	@Override
	public void onTouch (int x, int y) {
		int startX = POPUP_PADDING + 10;
		int startY = POPUP_PADDING + 10;
		
		if (x > startX && x < startX + GRID_SIZE * GRID_NB_COLUMNS && y > startY) {
			int index = (y - startY) / GRID_SIZE * GRID_NB_COLUMNS + (x - startX) / GRID_SIZE;
			mSelected = index;
		}
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
