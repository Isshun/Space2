package org.bluebox.space2.game.screen;

import java.util.List;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.ScrollerView;
import org.bluebox.space2.engine.ui.TextView;
import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game.Anim;
import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class PlanetBuildStructureScreen extends BaseScreen {
	
	private static final int POPUP_PADDING = 6; 
	private static final int POPUP_TOP = 26; 
	private static final int POPUP_WIDTH = Constants.GAME_WIDTH - POPUP_PADDING * 2; 
	private static final int POPUP_HEIGHT = Constants.GAME_HEIGHT - POPUP_TOP - POPUP_PADDING * 2;
	private static final int GRID_SIZE = 36;
	private static final int ITEM_BY_PAGE = 5;
	private static final int GRID_NB_COLUMNS = 1;
	private static final int SEP = POPUP_WIDTH / 5 * 3;
	
	private ButtonView 						mBtBuild;
	protected int 								mSelected;
	protected PlanetModel 					mPlanet;
	protected List<BuildingClassModel> 	mBuildings;
	private ButtonView mBtClose;
	private ScrollerView mScroller;
	private int mPageIndex;
	private TextView mLbPage;

	public PlanetBuildStructureScreen (BaseScreen parent, PlanetModel planet) {
		mParent = parent;
		parent.notifyChange();
		mPlanet = planet;
		mOutTransition = Anim.GO_DOWN;
		mBuildings = GameService.getInstance().getBuildingClasses();
	}

	@Override
	protected void onCreate () {
		int btWidth = POPUP_WIDTH / 2 - 12;
		
		mLbPage = new TextView("page: 1 / " + (int)(mBuildings.size() / ITEM_BY_PAGE + 1), POPUP_PADDING, POPUP_TOP + POPUP_PADDING + POPUP_HEIGHT - 12);
		addView(mLbPage);
		
		mBtBuild = new ButtonView(POPUP_PADDING + SEP + 10, POPUP_TOP + POPUP_PADDING + POPUP_HEIGHT - 30, 60, 22, new Color(0.2f, 1, 0.2f, 1));
		mBtBuild.setText("build");
		mBtBuild.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				if (mSelected < mBuildings.size()) {
					BuildingClassModel toBuild =  mBuildings.get(mSelected);
					mPlanet.buildStructure(toBuild.getType());
					mBuildings.remove(toBuild);
					notifyChange();
//					back();
				}
			}
		});
		addView(mBtBuild);
		
		mBtClose = new ButtonView(POPUP_PADDING + SEP + 80, POPUP_TOP + POPUP_PADDING + POPUP_HEIGHT - 30, 60, 22, new Color(1, 1, 1, 0.55f));
		mBtClose.setText("close");
		mBtClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				back();
			}
		});
		addView(mBtClose);
		
		mScroller = new ScrollerView(SEP + 5, POPUP_TOP + POPUP_PADDING, POPUP_WIDTH - SEP, POPUP_HEIGHT, new Color(1, 0.5f, 0.5f, 0.55f));
		mScroller.setText("L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.");
		addView(mScroller);
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		int posY = Constants.GAME_HEIGHT - 100;
		
		System.out.println("popup width: " + POPUP_WIDTH + ", sep: " + SEP + ", sub: " + (POPUP_WIDTH - SEP));

		// Background
		mainLayer.drawRectangle(POPUP_PADDING + SEP, POPUP_PADDING + POPUP_TOP, POPUP_WIDTH, POPUP_HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.85f));
				
		int i = 0;
		for (BuildingClassModel b: mBuildings) {
			if (i >= mPageIndex * ITEM_BY_PAGE && i < (mPageIndex + 1) * ITEM_BY_PAGE) {
				int j = i - mPageIndex * ITEM_BY_PAGE;
				drawIcon(mainLayer, b, POPUP_PADDING + (j % GRID_NB_COLUMNS) * GRID_SIZE, POPUP_PADDING + POPUP_TOP + (j / GRID_NB_COLUMNS) * GRID_SIZE, mSelected == j);
			}
			i++;
		}
		
		if (mSelected + mPageIndex * ITEM_BY_PAGE < mBuildings.size()) {
			drawInfo(mainLayer, mBuildings.get(mSelected + mPageIndex * ITEM_BY_PAGE), POPUP_PADDING + SEP, POPUP_PADDING + POPUP_TOP, POPUP_WIDTH - SEP, POPUP_HEIGHT - 38);
		}
	}

	private void drawIcon (BaseScreenLayer mainLayer, BuildingClassModel building, int posX, int posY, boolean isSelected) {
		mainLayer.drawRectangle(posX, posY, SEP - 4, 32, isSelected ? new Color(0.75f, 1, 0.75f, 0.65f) : new Color(1, 1, 1, 0.45f));
		mainLayer.draw(building.getIcon(), posX, posY);
		List<String> requires = building.getRequires(mPlayer, mPlanet);
//		if (building.isAvailable(mPlayer, mPlanet) == false) {
		if (requires != null) {
			mainLayer.setStringColor(Color.RED);
		}
		mainLayer.drawString(building.getShortName(), posX + 34, posY + 4);
	}

	private void drawInfo (BaseScreenLayer mainLayer, BuildingClassModel building, int startX, int startY, int width, int height) {
//		// Background
//		drawRectangle(startX, startY, width, height, new Color(0.5f, 1, 0.5f, 0.5f));

		// Separation
		mainLayer.drawRectangle(startX, startY, 2, height, new Color(1, 1, 1, 0.45f));

		// Building title
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.setStringMultiline(true);
		mainLayer.setStringMaxWidth(width - 22);
		int lines = mainLayer.drawString(building.getName(), startX + 10, startY + 10);

		// Building effect
		mainLayer.setStringColorNumbers(true);
		mainLayer.drawString(building.getEffect(), startX + 10, startY + 7 + (lines * 20));
		
		// Requires
		int i = 0;
		List<String> requires = building.getRequires(mPlayer, mPlanet);
		if (requires != null) {
			mainLayer.drawString("Requires:", startX + 10, startY + 20 + (lines * 20));
			for (String req: requires) {
				mainLayer.setStringColor(Color.RED);
				mainLayer.drawString(req, startX + 10, startY + 30 + (lines * 20) + i * 10);
				i++;
			}
		}

		
//		// Building description
//		setStringMultiline(true);
//		setStringMaxWidth(width - 22);
//		drawString(building.getDesc(), startX + 10, startY + 20 + (lines * 20));
		
		mScroller.setPosition(startX + 10, startY + 20 + (lines * 20));
		mScroller.setWidth(width - 22);
		mScroller.setHeight(height - 20 - (lines * 20));
		mScroller.setVisibility(View.GONE);
		
//		// Button build
//		drawRectangle(startX + 11, startY + height - 32, width - 22, 22, new Color(0.8f, 1, 0.8f, 0.5f));
//		drawString("build", startX + 11, startY + height - 32);
//
//		// Button cancel
//		drawRectangle(startX + 11, startY + height - 62, width - 22, 22, new Color(1, 0.8f, 0.8f, 0.5f));
//		drawString("close", startX + 11, startY + height - 62);
	}

	@Override
	public void onTouch (int x, int y) {
		int startX = POPUP_PADDING;
		int startY = POPUP_PADDING - 12;
		
		if (x > startX && x < startX + GRID_SIZE * 6 && y > startY) {
//			int index = (y - startY) / GRID_SIZE * GRID_NB_COLUMNS + (x - startX) / GRID_SIZE;
			int index = (y - startY) / GRID_SIZE - 1;
			mSelected = index;
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

	public void onUp (int x, int y) {
		if (mPageIndex < (int)(mBuildings.size() / ITEM_BY_PAGE)) {
			mPageIndex++;
			mLbPage.setText("page: " + (mPageIndex + 1) + " / " + (int)(mBuildings.size() / ITEM_BY_PAGE + 1));
			notifyChange();
		}
	}

	public void onDown (int x, int y) {
		if (mPageIndex > 0) {
			mPageIndex--;
			mLbPage.setText("page: " + (mPageIndex + 1) + " / " + (int)(mBuildings.size() / ITEM_BY_PAGE + 1));
			notifyChange();
		}
	}

}
