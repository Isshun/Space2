package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.ImageView;
import org.bluebox.space2.engine.ui.RectangleView;
import org.bluebox.space2.engine.ui.TextView;
import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.IShipCollectionModel;
import org.bluebox.space2.game.model.RelationModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.service.GameService;
import org.bluebox.space2.path.PathResolver;
import org.bluebox.space2.path.Vertex;

import com.badlogic.gdx.graphics.Color;

public class SpaceActionScreen extends BaseScreen {

	private static final int 	POS_Y = Constants.GAME_HEIGHT - 65;
	private static final int 	LIST_START_Y = 19;
	private static final int 	LINE_INTERVAL = 12;
	private static final int 	ITEM_HEIGHT = 46;
	private static final int 	ITEM_WIDTH = 60;
	private static final int 	ITEM_MARGIN = 6;
	private static final int 	LINE_SPACING = 4;
	
	protected Set<FleetModel> 	mSelectedFleets;
	protected SystemModel 		mSelectedSystem;
	protected SystemModel 		mActionSystem;
	private boolean 				mIsOpen;
	private ImageView 			mImgTarget;
	private ButtonView 			btMove;
	private RectangleView 		btColonize;
	protected boolean 			mIsOnMove;
	private Color 					mColor;
	private TextView 				mLbFleets;
	private ButtonView btDestroy;
	private ButtonView btMerge;
	private ButtonView btConfirm;
	private ButtonView btCancel;
	private TextView lbTooltip;
	private ButtonView btNewFleet;
	private ButtonView btInfo;

	public SpaceActionScreen (BaseScreen parent, SystemModel system) {
		mParent = parent;
		mSelectedSystem = system;
		mSelectedFleets = new HashSet<FleetModel>();
	}

	@Override
	protected void onCreate () {
		System.out.println("SpaceAction: onCreate");

		setOffsetY(-POS_Y, -POS_Y);
		
		mColor = new Color(0.85f, 1, 0.85f, 0.65f);

//		mImgTarget = new ImageView(Art.systems[0], Constants.GAME_WIDTH - 64, 2);
//		addView(mImgTarget);

//		RectangleView target = new RectangleView(Constants.GAME_WIDTH / 2 + 1, 0, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
//		target.setPadding(4);
//		target.setText("Target");
//		addView(target);		
		
		RectangleView moveTo = new RectangleView(4, -Constants.GAME_HEIGHT + 4, Constants.GAME_WIDTH - 8, 14, new Color(1, 1, 1, 0.45f));
		moveTo.setPadding(4);
		moveTo.setText("Select destination");
		addView(moveTo);
		
		mLbFleets = new TextView("lbFleets", 0, 0);
		mLbFleets.setPadding(5);
		addView(mLbFleets);

		// Button move
		btMove = new ButtonView(Constants.GAME_WIDTH - 62, 2, 60, 14, null);
		btMove.setText("move");
		btMove.setIcon(Art.ic_move);
		btMove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				System.out.println("SpaceAction: MOVE !!");
				mIsOnMove = true;
				notifyChange();
			}
		});
		addView(btMove);

		// Button colonize
		btColonize = new RectangleView(Constants.GAME_WIDTH - 118, 2, 60, 14, Color.GREEN);
		btColonize.setText("Colonize");
		btColonize.setPadding(4);
		btColonize.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				mSelectedSystem.colonize(mPlayer);
				mParent.notifyChange();
				back();
			}
		});
		addView(btColonize);

		// Destroy fleet
		btDestroy = new ButtonView(Constants.GAME_WIDTH - 125, 2, 60, 14, null);
		btDestroy.setText("Dismantle");
		btDestroy.setPadding(4);
		btDestroy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new FleetDestroyScreen(mSelectedFleets));
			}
		});
		addView(btDestroy);
		
		// Display fleet info
		btInfo = new ButtonView(Constants.GAME_WIDTH - 251, 2, 60, 14, null);
		btInfo.setText("Info");
		btInfo.setPadding(4);
		btInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new FleetInfoScreen(mSelectedFleets.iterator().next(), new ArrayList<IShipCollectionModel>(mSelectedFleets)));
			}
		});
		addView(btInfo);

		// Create new fleet
		btNewFleet = new ButtonView(Constants.GAME_WIDTH - 188, 2, 60, 14, null);
		btNewFleet.setText("Subdivide");
		btNewFleet.setPadding(4);
		btNewFleet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				if (mSelectedFleets.size() > 0) {
					addScreen(new FleetCreateScreen(mSelectedFleets.iterator().next()));
				}
			}
		});
		addView(btNewFleet);

		// Merge fleet
		btMerge = new ButtonView(Constants.GAME_WIDTH - 140, 30, 60, 14, Color.BLUE);
		btMerge.setText("Merge");
		btMerge.setPadding(4);
		btMerge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				// Move all ships to first fleet
				FleetModel toMerge = null;
				for (FleetModel f: mSelectedFleets) {
					if (toMerge == null) {
						toMerge = f;
					} else {
						toMerge.addShips(f.getShips());
					}
				}
				// Remove fleet from player
				for (FleetModel f: mSelectedFleets) {
					if (toMerge != f) {
						f.destroy();
					}
				}
				mSelectedFleets.clear();
				mSelectedFleets.add(toMerge);
				notifyChange();
			}
		});
		addView(btMerge);
		
		// Button confirm
		btConfirm = new ButtonView(Constants.GAME_WIDTH - 70, 20, 60, 14, Color.PINK);
		btConfirm.setText("Confirm");
		btConfirm.setPadding(4);
		btConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				action();
				back();
			}
		});
		addView(btConfirm);

		// Button cancel
		btCancel = new ButtonView(Constants.GAME_WIDTH - 70, 40, 60, 14, Color.RED);
		btCancel.setText("Cancel");
		btCancel.setPadding(4);
		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
			}
		});
		addView(btCancel);

		// Tooltip
		lbTooltip = new TextView("Select system\n   on map", Constants.GAME_WIDTH - 110, 30);
		lbTooltip.setPadding(4);
		addView(lbTooltip);

		setSelected(mSelectedSystem);
		
		drawRightPanel();
	}
	
	protected void action () {
		for (FleetModel fleet: mSelectedFleets) {
			if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
				fleet.setCourse(mActionSystem);
			}
		}
		
		mSelectedFleets.clear();
		back();
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.drawRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.85f));
		mainLayer.drawRectangle(0, 0, Constants.GAME_WIDTH, 17, new Color(1, 1, 1, 0.45f));
		if (mSelectedSystem != null) {
//			mainLayer.drawString(mSelectedSystem.getName(), Constants.GAME_WIDTH / 2 - mSelectedSystem.getName().length() * 6 - 5, 5);
			int i = 0;
			for (FleetModel fleet: mSelectedSystem.getFleets()) {
				if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
					drawItem(mainLayer, fleet, i++ * (ITEM_WIDTH + ITEM_MARGIN), 20);
					//mainLayer.drawString(fleet.getName(), 4, 20 + i++ * 12);
				}
			}
		}

		if (mIsOnMove || mActionSystem != null) {
			drawSelectDestination(mainLayer);
		} else if (mActionSystem != null) {
			drawTargetPanel();
		} else {
			drawRightPanel();
		}
		
//		mainLayer.drawRectangle(Constants.GAME_WIDTH / 2 + 1, 0, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
//		mainLayer.drawString(mActionSystem == null ? "Target" : "Target:", Constants.GAME_WIDTH / 2 + 5, 5);
//		if (mActionSystem != null) {
//			mainLayer.drawString(mActionSystem.getName(), Constants.GAME_WIDTH - mActionSystem.getName().length() * 6 - 5, 5);
//			int j = 0;
//			for (FleetModel fleet: mActionSystem.getFleets()) {
//				int relation = fleet.getOwner().getRelation(GameService.getInstance().getPlayer());
//				String str = fleet.getName() + " (" + fleet.getOwner().getName();
//				switch (relation) {
//				case RelationModel.RELATION_ALLY: str += "/ally)"; break;
//				case RelationModel.RELATION_NEUTRAL: str += "/neutral)"; break;
//				case RelationModel.RELATION_PEACE: str += "/peace)"; break;
//				case RelationModel.RELATION_WAR: str += "/war)"; break;
//				case RelationModel.RELATION_ME: str += ")"; break;
//				}
//				mainLayer.drawString(str, Constants.GAME_WIDTH / 2 + 5, 20 + j++ * 12);
//			}
//
//			mainLayer.drawRectangle(Constants.GAME_WIDTH - 60, 24, 50, 50, new Color(1, 1, 1, 0.45f));
//			mainLayer.drawString("Move", Constants.GAME_WIDTH - 100, 30);
//			mainLayer.drawString("Cancel", Constants.GAME_WIDTH - 50, 30);
//		}
	}

	private void drawTargetPanel () {
		btMove.setVisibility(View.GONE);
		btDestroy.setVisibility(View.GONE);
		btColonize.setVisibility(View.GONE);
		btMerge.setVisibility(View.GONE);
		lbTooltip.setVisibility(View.GONE);
		btConfirm.setVisibility(View.VISIBLE);
		btCancel.setVisibility(View.VISIBLE);
	}

	private void drawRightPanel () {
		btColonize.setVisibility(hasColonizerInSelection() ? View.VISIBLE : View.GONE);
		btMerge.setVisibility(mSelectedFleets.size() > 1 ? View.VISIBLE : View.GONE);
		btMove.setVisibility(mSelectedFleets.size() > 0 ? View.VISIBLE : View.GONE);
		btNewFleet.setVisibility(mSelectedFleets.size() == 1 ? View.VISIBLE : View.GONE);
		btDestroy.setVisibility(mSelectedFleets.size() > 0 ? View.VISIBLE : View.GONE);
		btInfo.setVisibility(mSelectedFleets.size() == 1 ? View.VISIBLE : View.GONE);
		lbTooltip.setVisibility(View.GONE);
		btConfirm.setVisibility(View.GONE);
		btCancel.setVisibility(View.GONE);
	}

	private boolean hasColonizerInSelection () {
		for (FleetModel f: mSelectedFleets) {
			if (f.hasDevice(Device.COLONIZER)) {
				return true;
			}
		}
		return false;
	}

	private void drawSelectDestination(BaseScreenLayer mainLayer) {
		btMove.setVisibility(View.GONE);
		btDestroy.setVisibility(View.GONE);
		btColonize.setVisibility(View.GONE);
		btMerge.setVisibility(View.GONE);

		if (mActionSystem != null) {
			btConfirm.setVisibility(View.VISIBLE);
			btCancel.setVisibility(View.VISIBLE);
			lbTooltip.setVisibility(View.GONE);
		} else {
			lbTooltip.setVisibility(View.VISIBLE);
			btConfirm.setVisibility(View.GONE);
			btCancel.setVisibility(View.GONE);
		}
		
		mainLayer.drawRectangle(Constants.GAME_WIDTH - 134, 0, 2, Constants.GAME_HEIGHT, new Color(1f, 1f, 1f, 0.45f));
//		mainLayer.drawRectangle(0, 0, Constants.GAME_WIDTH, 14, new Color(1, 1, 1, 0.45f));
		if (mActionSystem != null) {
			mainLayer.drawString("Move to:    " + mActionSystem.getName(), Constants.GAME_WIDTH - 129, 4);
		} else {
			mainLayer.drawString("Move to:", Constants.GAME_WIDTH - 129, 4);
		}
	}

	private void drawItem (BaseScreenLayer mainLayer, FleetModel fleet, int x, int y) {
		// Fleet is selected
		if (mSelectedFleets.contains(fleet)) {
			mainLayer.drawRectangle(x + ITEM_MARGIN, y, ITEM_WIDTH, ITEM_HEIGHT - LINE_SPACING, mColor);
		}
		else {
			mainLayer.drawRectangle(x + ITEM_MARGIN, y, ITEM_WIDTH, 2, mColor);
			mainLayer.drawRectangle(x + ITEM_MARGIN, y + 2, 2, ITEM_HEIGHT - LINE_SPACING - 4, mColor);
			mainLayer.drawRectangle(x + ITEM_MARGIN + ITEM_WIDTH - 2, y + 2, 2, ITEM_HEIGHT - LINE_SPACING - 4, mColor);
			mainLayer.drawRectangle(x + ITEM_MARGIN, y + ITEM_HEIGHT - 2 - LINE_SPACING, ITEM_WIDTH, 2, mColor);
		}

		int i = 0;
		for (ShipModel ship: fleet.getShips()) {
			//mainLayer.draw(Art.ship, x + ITEM_MARGIN + (i * 14), y + 18);
			int offX = (i % 6) * 9;
			int offY = (int)(i / 6) * 5;
			mainLayer.drawRectangle(x + ITEM_MARGIN + offX + 4, y + 20 + offY, 7, 3, mColor);
			mainLayer.drawRectangle(x + ITEM_MARGIN + offX + 4, y + 20 + offY, 7, 3, mColor);
			mainLayer.drawRectangle(x + ITEM_MARGIN + offX + 4, y + 20 + offY, (int)(ship.getHullRatio() * 7), 3, Color.GREEN);
			i++;
		}
		mainLayer.drawString(fleet.getName(), x + ITEM_MARGIN + 4, y + 4);
		mainLayer.setStringColor(mColor);
		String stats = String.format("%d/%d/%d", (int)fleet.getAttackIndice(), (int)fleet.getDefenseIndice(), (int)fleet.getIndice());
		mainLayer.drawString(stats, x + ITEM_MARGIN + 4, y + 12);
	}

	@Override
	public void onTouch (int x, int y) {
		if (mIsOnMove) {
			mParent.onTouch(x, y + POS_Y);
		}
		
		if (!mIsOpen) {
			y -= POS_Y;
		}

		System.out.println("SpaceAction: onTouch: " + x + " x " + y);
		
		// Click on window
		if (y > 0) {
			int index = x / (ITEM_WIDTH + ITEM_MARGIN);
			
			if (index < mSelectedSystem.getFleets().size()) {
				FleetModel fleet = mSelectedSystem.getFleets().get(index);
				if (mSelectedFleets.contains(fleet)) {
					mSelectedFleets.remove(fleet);
				} else {
					mSelectedFleets.add(fleet);
				}
			}
			
			if (x > Constants.GAME_WIDTH - 60 && mActionSystem != null) {
//				List<FleetModel> cpyFleet = new ArrayList<FleetModel>(mSelectedSystem.getFleets());
//				for (FleetModel fleet: cpyFleet) {
//					if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
//						fleet.setCourse(mActionSystem);
//					}
//				}
//				back();
			}

//			List<ShipClassModel> classes = GameService.getInstance().getShipClasses();
//			int index = (y - POS_Y - LIST_START_Y) / LINE_INTERVAL;
//			if (x < Constants.GAME_WIDTH / 3 && index < classes.size()) {
//				mPlanet.addBuild(new ShipModel(classes.get(index)));
//			}
//			if (x > Constants.GAME_WIDTH / 3 && x < Constants.GAME_WIDTH / 3 * 2) {
//				System.out.println("remove build");
//			}
		}
		
		// Click on parent
		else {
			mParent.onTouch(x, y + POS_Y);
//			back();
		}
	}

	@Override
	public void onBack () {
		((SpaceScreen)mParent).setTravelPath(null, null);
		((SpaceScreen)mParent).setSelected(null);
	}
	
	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		if (mIsOpen == false && startY < POS_Y) {
			mParent.onMove(0, 0, offsetX, offsetY);
		}
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUp (int x, int y) {
		if (y > POS_Y) {
			setOffsetY(-POS_Y, 0);
			mIsOpen = true;
		}
	}

	@Override
	public void onReturn () {
		reSelectFleetFromSystem();

		if (mSelectedFleets.size() == 0) {
			back();
		}
	}

	@Override
	public void onDown (int x, int y) {
		// big window
		if (mIsOpen) {
			mIsOpen = false;
			setOffsetY(0, -POS_Y);
		}

		// small window
		else {
			if (y > POS_Y) {
				back();
			}
		}
	}

	public void setActionSystem (SystemModel system) {
		mActionSystem = system;
		
		System.out.println("SpaceAction: setAction");

		if (mSelectedSystem != null && system != null) {
			List<Vertex> path = PathResolver.getInstance().getPath(mSelectedSystem, system);
			System.out.println("Path from " + mSelectedSystem.getName() + " to " + system.getName());
			System.out.println("Path length: " + path.size());
			List<TravelModel> travelPath = GameService.getInstance().getTravelPath(path);
			List<SystemModel> systemPath = new ArrayList<SystemModel>();
			for (Vertex v: path) {
				systemPath.add(v.getSystem());
			}
			((SpaceScreen)mParent).setTravelPath(travelPath, systemPath);
			for (TravelModel t: travelPath) {
				System.out.println("travel from: " + t.getName());
			}
		}
		
//		setOffsetY(-Constants.GAME_HEIGHT, -POS_Y);
		mIsOnMove = false;
		notifyChange();
		
		mLbFleets.setText("Selected 2");
	}

	public void setSelected (SystemModel system) {
		if (mIsOnMove || mActionSystem != null) {
			setActionSystem(system);
			return;
		}
		
		System.out.println("SpaceAction: setSelected");
		
		mLbFleets.setText("Selected !!!");
		
		mSelectedSystem = system;
		((SpaceScreen)mParent).setSelected(system);
		
		reSelectFleetFromSystem();
	}

	private void reSelectFleetFromSystem () {
		mSelectedFleets.clear();
		for (FleetModel f: mSelectedSystem.getFleets()) {
			if (f.getOwner().equals(mPlayer)) {
				mSelectedFleets.add(f);
			}
		}
	}

}
