package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.IShipCollectionModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class FleetCreateScreen extends BaseScreen {

	private static final int 			ITEM_MARGIN = 5;
	private static final int			ITEM_WIDTH = Constants.GAME_WIDTH / 2 - ITEM_MARGIN * 2;
	private static final int			ITEM_HEIGHT = 32;
	private static final int 			LINE_SPACING = 5;
	private static final int 			START_Y = 27;
	
	private Color 							mColor;
	protected IShipCollectionModel 	mShipCollection;
	protected FleetModel 				mNewFleet;
	private ButtonView 					mBtCancel;
	private ButtonView 					mBtDone;

	public FleetCreateScreen (IShipCollectionModel shipCollection) {
		mShipCollection = shipCollection;
		mNewFleet = new FleetModel(GameService.getInstance().getPlayer());
		mNewFleet.setLocation(shipCollection.getLocation());
	}

	@Override
	protected void onCreate () {
		mColor = new Color(0.85f, 1, 0.85f, 0.65f);
		
		mBtCancel = new ButtonView(ITEM_MARGIN, Constants.GAME_HEIGHT - 30, ITEM_WIDTH, 28, Color.RED);
		mBtCancel.setText("Cancel");
		mBtCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				cancelFleet();
				back();
			}
		});
		addView(mBtCancel);
		
		mBtDone = new ButtonView(ITEM_MARGIN + Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 30, ITEM_WIDTH, 28, Color.GREEN);
		mBtDone.setText("Build");
		mBtDone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				back();
			}
		});
		addView(mBtDone);
	}

	protected void cancelFleet () {
		List<ShipModel> cpy = new ArrayList<ShipModel>(mNewFleet.getShips());
		for (ShipModel ship: cpy) {
			mShipCollection.addShip(ship);
		}
		mNewFleet.destroy();
	}

	@Override
	public void onBack () {
		cancelFleet();
	}

	@Override
	protected void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.draw(Art.bg_1, 0, 0);

		mainLayer.drawString("Old fleet", ITEM_MARGIN, ITEM_MARGIN);
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(mShipCollection.getName(), ITEM_MARGIN, ITEM_MARGIN + 10);
		mainLayer.drawString("New fleet", ITEM_MARGIN + Constants.GAME_WIDTH / 2, ITEM_MARGIN);
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString(mNewFleet.getName(), ITEM_MARGIN + Constants.GAME_WIDTH / 2, ITEM_MARGIN + 10);

		mainLayer.drawRectangle(Constants.GAME_WIDTH / 2, 0, 2, Constants.GAME_HEIGHT, mColor);

		// Draw old fleet
		int i = 0;
		for (ShipModel ship: mShipCollection.getShips()) {
			drawItem(mainLayer, ship, 0, START_Y + i * ITEM_HEIGHT + LINE_SPACING);
			i++;
		}

		// Draw new fleet
		i = 0;
		for (ShipModel ship: mNewFleet.getShips()) {
			drawItem(mainLayer, ship, Constants.GAME_WIDTH / 2 + 2, START_Y + i * ITEM_HEIGHT + LINE_SPACING);
			i++;
		}
	}

	private void drawItem (BaseScreenLayer mainLayer, ShipModel ship, int x, int y) {
		mainLayer.drawRectangle(x + ITEM_MARGIN, y, ITEM_WIDTH, ITEM_HEIGHT - LINE_SPACING, mColor);
		mainLayer.draw(Art.ship_32, x + ITEM_MARGIN, y);
		mainLayer.drawString(ship.getClassName(), x + ITEM_MARGIN + 40, y + 5);
		mainLayer.setStringColor(mColor);
		String stats = String.format("%d/%d/%d", (int)ship.getAttackIndice(), (int)ship.getDefenseIndice(), (int)ship.getIndice());
		mainLayer.drawString(stats, x + ITEM_MARGIN + 40, y + 16);
	}

	@Override
	public void onTouch (int x, int y) {
		// Click on old fleet
		if (x < Constants.GAME_WIDTH / 2) {
			int index = (y - START_Y) / ITEM_HEIGHT;
			if (index < mShipCollection.getNbShip()) {
				ShipModel ship = mShipCollection.getShips().get(index);
				mShipCollection.getShips().remove(index);
				mNewFleet.addShip(ship);
			}
		}
		
		// Click on new fleet
		else {
			int index = (y - START_Y) / ITEM_HEIGHT;
			if (index < mNewFleet.getNbShip()) {
				ShipModel ship = mNewFleet.getShips().get(index);
				mNewFleet.getShips().remove(index);
				mShipCollection.addShip(ship);
			}
		}
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
