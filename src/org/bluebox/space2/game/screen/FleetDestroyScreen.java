package org.bluebox.space2.game.screen;

import java.util.Set;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.DockModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class FleetDestroyScreen extends BaseScreen {

	private static final int GRID_WIDTH = 46;
	private static final int GRID_HEIGHT = 50;
	private static final int GRID_PADDING = 0;
	private static final int GRID_COLUMNS = 4;
	private static final int GRID_CONTENT_WIDTH = GRID_WIDTH - GRID_PADDING - GRID_PADDING;
	private static final int GRID_CONTENT_HEIGHT = GRID_HEIGHT - GRID_PADDING - GRID_PADDING;
	private static final int START_Y = 36;
	private static final int START_X = 5;
	
	Set<FleetModel> 	mShipCollection;
	private int 				mSelected;
	private double 			mTotInd;
	private double 			mAttInd;
	private double 			mDefInd;
	private ButtonView		mBtSendToDock;
	private ButtonView 		mBtDestroy;
	private ButtonView 		mBtCancel;
	protected DockModel 		mDock;

	public FleetDestroyScreen (Set<FleetModel> mSelectedFleets) {
		mShipCollection = mSelectedFleets;
		mDock = GameService.getInstance().getPlayer().getHome().getDock();
	}

	@Override
	protected void onCreate () {
		
		// Send to dock
		mBtSendToDock = new ButtonView(100, 200, 42, 20, Color.RED);
		mBtSendToDock.setText("send to dock");
		mBtSendToDock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				for (FleetModel fleet: mShipCollection) {
					fleet.destroy(mDock);
				}
				mShipCollection.clear();
				back();
			}
		});
		addView(mBtSendToDock);

		// Destroy ships
		mBtDestroy = new ButtonView(164, 200, 42, 20, Color.RED);
		mBtDestroy.setText("destroy");
		mBtDestroy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				for (FleetModel fleet: mShipCollection) {
					fleet.destroy();
				}
				mShipCollection.clear();
				back();
			}
		});
		addView(mBtDestroy);

		// Back button
		mBtCancel = new ButtonView(228, 200, 42, 20, Color.RED);
		mBtCancel.setText("cancel");
		mBtCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				back();
			}
		});
		addView(mBtCancel);
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.setStringSize(StringConfig.SIZE_BIG);
		mainLayer.drawString("Dismantle", 4, 4);

		mainLayer.setStringMaxWidth(180);
		mainLayer.setStringMultiline(true);
		mainLayer.drawString("    Fleet is not empty,     send ships to nearest dock ?", 110, 110);
	}


	@Override
	public void onTouch (int x, int y) {
	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
	}

	@Override
	public void onLongTouch (int x, int y) {
	}

}
