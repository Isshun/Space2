package com.mojang.metagun.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.RelationModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.model.TravelModel;
import com.mojang.metagun.path.Vertex;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.service.PathResolver;
import com.mojang.metagun.ui.RectangleView;
import com.mojang.metagun.ui.View.OnClickListener;

public class SpaceActionScreen extends Screen {

	private static final int 	POS_Y = Constants.GAME_HEIGHT - 65;
	private static final int 	LIST_START_Y = 19;
	private static final int 	LINE_INTERVAL = 12;
	
	protected SystemModel 		mSelectedSystem;
	protected SystemModel 		mActionSystem;

	public SpaceActionScreen (Screen parent, SystemModel system) {
		mParent = parent;
		mSelectedSystem = system;
	}

	@Override
	protected void onCreate () {
		System.out.println("SpaceAction: onCreate");

		// Button move
		RectangleView btMove = new RectangleView(Constants.GAME_WIDTH - 120, POS_Y + 24, 50, 50, new Color(1, 1, 1, 0.45f));
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

		System.out.println("SpaceAction: onCreate 2");

		// Button cancel
		RectangleView btCancel = new RectangleView(Constants.GAME_WIDTH - 60, POS_Y + 24, 50, 50, new Color(1, 0.6f, 0.6f, 0.45f));
		btCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				back();
			}
		});
		addView(btCancel);
	}

	@Override
	public void onDraw (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		System.out.println("SpaceAction: onDraw");
		
		drawRectangle(0, POS_Y, Constants.GAME_WIDTH, 65, new Color(0.2f, 0.2f, 0.2f, 0.85f));

		System.out.println("SpaceAction: onDraw 1");

		drawRectangle(0, POS_Y, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
		if (mSelectedSystem != null) {
			drawString("Selected:", 4, POS_Y + 5);
			drawString(mSelectedSystem.getName(), Constants.GAME_WIDTH / 2 - mSelectedSystem.getName().length() * 6 - 5, POS_Y + 5);
			int i = 0;
			for (FleetModel fleet: mSelectedSystem.getFleets()) {
				if (fleet.getOwner().equals(GameService.getInstance().getPlayer())) {
					drawString(fleet.getName(), 4, POS_Y + 20 + i++ * 12);
				}
			}
		}

		System.out.println("SpaceAction: onDraw 2");

		drawRectangle(Constants.GAME_WIDTH / 2, POS_Y, 1, 200, new Color(0, 0, 0, 0.45f));
		System.out.println("SpaceAction: onDraw 3");
		drawRectangle(Constants.GAME_WIDTH / 2 + 1, POS_Y, Constants.GAME_WIDTH / 2, 14, new Color(1, 1, 1, 0.45f));
		System.out.println("SpaceAction: onDraw 4");
		drawString(mActionSystem == null ? "Target" : "Target:", Constants.GAME_WIDTH / 2 + 5, POS_Y + 5);
		System.out.println("SpaceAction: onDraw 5");
		if (mActionSystem != null) {
			drawString(mActionSystem.getName(), Constants.GAME_WIDTH - mActionSystem.getName().length() * 6 - 5, POS_Y + 5);
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
				drawString(str, Constants.GAME_WIDTH / 2 + 5, POS_Y + 20 + j++ * 12);
			}
			
			System.out.println("SpaceAction: onDraw 6");

			drawRectangle(Constants.GAME_WIDTH - 60, POS_Y + 24, 50, 50, new Color(1, 1, 1, 0.45f));
			drawString("Move", Constants.GAME_WIDTH - 100, POS_Y + 30);
			drawString("Cancel", Constants.GAME_WIDTH - 50, POS_Y + 30);
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
	public void onMove (int offsetX, int offsetY) {
		mParent.onMove(offsetX, offsetY);
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
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
