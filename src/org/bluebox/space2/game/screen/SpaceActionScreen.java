package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.RectangleView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.RelationModel;
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
	
	protected SystemModel 		mSelectedSystem;
	protected SystemModel 		mActionSystem;
	private boolean mIsOpen;

	public SpaceActionScreen (BaseScreen parent, SystemModel system) {
		mParent = parent;
		mSelectedSystem = system;
	}

	@Override
	protected void onCreate () {
		System.out.println("SpaceAction: onCreate");

		setOffsetY(-POS_Y, -POS_Y);
		
		// Button move
		RectangleView btMove = new RectangleView(Constants.GAME_WIDTH - 120, 24, 50, 50, new Color(1, 1, 1, 0.45f));
		btMove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				List<FleetModel> cpyFleet = new ArrayList<FleetModel>(mSelectedSystem.getFleets());
				for (FleetModel fleet: cpyFleet) {
					if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
						fleet.setCourse(mActionSystem);
					}
				}
				back();
			}
		});
		addView(btMove);

		
		// Button cancel
		RectangleView btCancel = new RectangleView(Constants.GAME_WIDTH - 60, 24, 50, 50, new Color(1, 0.6f, 0.6f, 0.45f));
		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				back();
			}
		});
		addView(btCancel);

	
		// Button colonize
		RectangleView btColonize = new RectangleView(Constants.GAME_WIDTH - 180, 24, 50, 50, new Color(0.6f, 1f, 0.6f, 0.45f));
		btColonize.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				mSelectedSystem.colonize(mPlayer);
				mParent.notifyChange();
				back();
			}
		});
		addView(btColonize);
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.drawRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new Color(0.2f, 0.2f, 0.2f, 0.85f));

		mainLayer.drawRectangle(0, 0, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
		if (mSelectedSystem != null) {
			mainLayer.drawString("Selected:", 4, 5);
			mainLayer.drawString(mSelectedSystem.getName(), Constants.GAME_WIDTH / 2 - mSelectedSystem.getName().length() * 6 - 5, 5);
			int i = 0;
			for (FleetModel fleet: mSelectedSystem.getFleets()) {
				if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
					mainLayer.drawString(fleet.getName(), 4, 20 + i++ * 12);
				}
			}
		}

		// Separator
		mainLayer.drawRectangle(Constants.GAME_WIDTH / 2, 0, 1, Constants.GAME_HEIGHT, new Color(0, 0, 0, 0.45f));
		
		mainLayer.drawRectangle(Constants.GAME_WIDTH / 2 + 1, 0, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
		mainLayer.drawString(mActionSystem == null ? "Target" : "Target:", Constants.GAME_WIDTH / 2 + 5, 5);
		if (mActionSystem != null) {
			mainLayer.drawString(mActionSystem.getName(), Constants.GAME_WIDTH - mActionSystem.getName().length() * 6 - 5, 5);
			int j = 0;
			for (FleetModel fleet: mActionSystem.getFleets()) {
				int relation = fleet.getOwner().getRelation(GameService.getInstance().getPlayer());
				String str = fleet.getName() + " (" + fleet.getOwner().getName();
				switch (relation) {
				case RelationModel.RELATION_ALLY: str += "/ally)"; break;
				case RelationModel.RELATION_NEUTRAL: str += "/neutral)"; break;
				case RelationModel.RELATION_PEACE: str += "/peace)"; break;
				case RelationModel.RELATION_WAR: str += "/war)"; break;
				case RelationModel.RELATION_ME: str += ")"; break;
				}
				mainLayer.drawString(str, Constants.GAME_WIDTH / 2 + 5, 20 + j++ * 12);
			}

			mainLayer.drawRectangle(Constants.GAME_WIDTH - 60, 24, 50, 50, new Color(1, 1, 1, 0.45f));
			mainLayer.drawString("Move", Constants.GAME_WIDTH - 100, 30);
			mainLayer.drawString("Cancel", Constants.GAME_WIDTH - 50, 30);
		}
	}

	@Override
	public void onTouch (int x, int y) {
		System.out.println("SpaceAction: onTouch");

		if (y > POS_Y + LIST_START_Y) {
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
		} else if (y < POS_Y) {
			mParent.onTouch(x, y);
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
	public void onDown (int x, int y) {
		setOffsetY(0, -POS_Y);
		mIsOpen = false;
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

		notifyChange();
	}

}
