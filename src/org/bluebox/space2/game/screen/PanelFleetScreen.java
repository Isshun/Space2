package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.TextView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game.Anim;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.IShipCollectionModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class PanelFleetScreen extends BaseScreen {

	private static final int GRID_WIDTH = 46;
	private static final int GRID_HEIGHT = 50;
	private static final int GRID_PADDING = 0;
	private static final int GRID_COLUMNS = 4;
	private static final int GRID_CONTENT_WIDTH = GRID_WIDTH - GRID_PADDING - GRID_PADDING;
	private static final int GRID_CONTENT_HEIGHT = GRID_HEIGHT - GRID_PADDING - GRID_PADDING;
	private static final int START_Y = 36;
	private static final int START_X = 5;
	
	IShipCollectionModel 	mShipCollection;
	private int 				mSelected;
	private double 			mTotInd;
	private double 			mAttInd;
	private double 			mDefInd;
	private ButtonView mBtNewFleet;

	public PanelFleetScreen (IShipCollectionModel collection) {
		mShipCollection = collection;
		
		for (ShipModel ship: mShipCollection.getShips()) {
			mTotInd += ship.getIndice();
			mAttInd += ship.getAttackIndice();
			mDefInd += ship.getDefenseIndice();
		}
	}

	@Override
	protected void onCreate () {
		mGame.setBg(Art.bg_1);

		List<SystemModel> systems = GameService.getInstance().getSystems();
		int i = 0;
		for (final SystemModel system: systems) {
			TextView text = new TextView(system.getName(), 4, 200 + i++ * 12);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick () {
					mShipCollection.setCourse(system);
				}
			});
			addView(text);
		}
		
		mBtNewFleet = new ButtonView(300, 4, 42, 20, Color.RED);
		mBtNewFleet.setText("new fleet");
		mBtNewFleet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelCreateFleet(mShipCollection));
			}
		});
		addView(mBtNewFleet);
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(String.format("%s (%d/%d/%d)", mShipCollection.getName(), (int)mTotInd, (int)mAttInd, (int)mDefInd), 6, 6);

		// Draw location
		mainLayer.drawString(mShipCollection.getLocationName(), 6, 24);

		mainLayer.draw(mShipCollection.isDock() ? Art.spacedock : Art.ship_big, Constants.GAME_WIDTH - 132, 12);

		drawShipInfo(mainLayer, Constants.GAME_WIDTH - 180, Constants.GAME_HEIGHT - 150);
		
		//drawRectangle(6, 32, 183, 600, Color.rgba8888(0.85f, 0.85f, 1, 0.45f)); 
		
		List<ShipModel> ships = mShipCollection.getShips();
		int i = 0;
		for (ShipModel ship : ships) {
			
			if (i == mSelected) {
				mainLayer.drawRectangle(
					START_X + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
					START_Y + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT,
					GRID_CONTENT_WIDTH,
					GRID_CONTENT_HEIGHT,
					Color.rgba8888(0.85f, 1, 0.85f, 0.65f));
			}
			
			mainLayer.draw(Art.ship_32,
				START_X + 7 + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
				START_Y + 6 + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT);
			
			mainLayer.drawRectangle(
				START_X + 7 + GRID_PADDING + (i % GRID_COLUMNS) * GRID_WIDTH,
				START_Y + 40 + GRID_PADDING + (int)(i / GRID_COLUMNS) * GRID_HEIGHT,
				(int)((GRID_CONTENT_WIDTH - 14) * ship.getHullRatio()),
				2,
				Color.rgba8888(0, 1, 0, 1));
			
			i++;
		}
	}

	private void drawShipInfo (BaseScreenLayer mainLayer, int posX, int posY) {
		mSelected = Math.min(mSelected, mShipCollection.getShips().size() - 1);
		if (mSelected == -1) {
			return;
		}
		
		ShipModel ship = mShipCollection.getShips().get(mSelected);

		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(String.valueOf((int)ship.getIndice()), posX + 6, posY + 16);
//		draw(Art.ic_info_32, 215, 120);
		mainLayer.drawString("class:      " + ship.getClassName(),posX + 42, posY);
		mainLayer.drawString("mass:           " + ship.getMass(), posX + 42, posY + 12 * 1);
		mainLayer.drawString("crew:         " + ship.getCrew() + "/" + ship.getTotalCrew(), posX + 42, posY + 12 * 2);
		mainLayer.drawString("special:       " + ship.getSpecialDeviceName(), posX + 42, posY + 12 * 3);
		
		mainLayer.drawRectangle(posX, posY + 48, 157, 1, Color.rgba8888(1, 1, 1, 0.65f));

		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(String.valueOf((int)ship.getAttackIndice()), posX + 6, posY + 64);
//		draw(Art.ic_attack_32, 215, 162);
		mainLayer.drawString("pow. phaser:    " + (int)ship.getPhaserPower(), posX + 42, posY + 18 + 12 * 3);
		mainLayer.drawString("pow. torpedo:   " + (int)ship.getTorpedoPower(), posX + 42, posY + 18 + 12 * 4);
		mainLayer.drawString("speed:           " + (int)ship.getVelocity(), posX + 42, posY + 18 + 12 * 5);

		mainLayer.drawRectangle(posX, posY + 90, 157, 1, Color.rgba8888(1, 1, 1, 0.65f));
		
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(String.valueOf((int)ship.getDefenseIndice()), posX + 6, posY + 106);
//		draw(Art.shield, 215, 204);
		mainLayer.drawString("hull:       " + ship.getHull() + "/" + ship.getHullBase(), posX + 42, posY + 24 + 12 * 6);
		mainLayer.drawString("armory:          " + (int)ship.getArmory(), posX + 42, posY + 24 + 12 * 7);
		mainLayer.drawString("shield:          " + (int)ship.getShieldPower(), posX + 42, posY + 24 + 12 * 8);
		
	}

	@Override
	public void onTouch (int x, int y) {
		if (x > START_X && y > START_Y) {
			int i = (x - START_X) / GRID_WIDTH + (y - START_Y) / GRID_HEIGHT * GRID_COLUMNS;
			if (i < mShipCollection.getNbShip()) {
				mSelected = i;
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

	@Override
	public void onNext () {
		List<FleetModel> fleets = new ArrayList<FleetModel>(mPlayer.getFleets());
		int index = fleets.indexOf(mShipCollection);
		if (index < fleets.size() - 1) {
			FleetModel fleet = fleets.get(Math.min(index + 1, fleets.size() - 1));
			mGame.replaceScreen(this, new PanelFleetScreen(fleet), Anim.FLIP_RIGHT);
		}
	}

	@Override
	public void onPrev () {
		List<FleetModel> fleets = new ArrayList<FleetModel>(mPlayer.getFleets());
		int index = fleets.indexOf(mShipCollection);
		if (index > 0) {
			FleetModel fleet = fleets.get(Math.max(index - 1, 0));
			mGame.replaceScreen(this, new PanelFleetScreen(fleet), Anim.FLIP_LEFT);
		}
	}

}
