
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Matrix4;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.model.TravelModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.ImageView;
import com.mojang.metagun.ui.TextView;
import com.mojang.metagun.ui.View;
import com.mojang.metagun.ui.View.OnClickListener;

public class SpaceScreen extends Screen {
	private static final int MAP_POS_X = Constants.GAME_WIDTH - 64 - 6;
	private static final int MAP_POS_Y = 6;

	private SystemModel 			mSelected;
	private SystemModel 			mActionSystem;
	private SpaceActionScreen 	mActionScreen;
	private ImageView mBtPlanets;
	private ImageView mBtRelations;
	private ImageView mBtDebug;
	private ImageView mBtArmada;
	private List<TravelModel> mTravelPath;
	private List<SystemModel> mSystemPath;

	private SpriteCache 			mCurrentTravelCache;
	private int 					mCurrentTravelCacheId;

	public SpaceScreen () {
		mCurrentTravelCache = new SpriteCache(100, true);
		mCurrentTravelCacheId = -1;
	}
	
	@Override
	protected void onCreate () {

		// Button planets
		mBtPlanets = new ImageView(Art.bt_planets, 6, 6);
		mBtPlanets.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelGovernmentScreen());
			}
		});
		addView(mBtPlanets);
		addView(new TextView("GOV.", 8, 40));

		// Button relations
		mBtRelations = new ImageView(Art.bt_relations, 44, 6);
		mBtRelations.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelRealtionScreen());
			}
		});
		addView(mBtRelations);
		addView(new TextView("RELS.", 46, 40));

		// Button debug
		mBtDebug = new ImageView(Art.bt_debug, 82, 6);
		mBtDebug.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new DebugScreen());
			}
		});
		addView(mBtDebug);
		addView(new TextView("DEBUG", 84, 40));

		// Button armada
		mBtArmada = new ImageView(Art.bt_debug, 120, 6);
		mBtArmada.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelArmadaScreen());
			}
		});
		addView(mBtArmada);
		addView(new TextView("ARMADA", 122, 40));
	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		int posX = mPosX / 8;
		int posY = mPosY / 8;

		draw(Art.bg, posX - 320, posY - 240);

//		draw(Art.bg, posX - 320, posY - 240);
//		draw(Art.bg, posX, posY - 240);
//		draw(Art.bg, posX + 320, posY - 240);
//
//		draw(Art.bg, posX - 320, posY);
//		draw(Art.bg, posX, posY);
//		draw(Art.bg, posX + 320, posY);
//
//		draw(Art.bg, posX - 320, posY + 240);
//		draw(Art.bg, posX, posY + 240);
//		draw(Art.bg, posX + 320, posY + 240);

		// Draw systems
		if (mSystemSprite == null) {

			mSystemSprite = new SpriteCache(1000, true);
			mSystemSprite.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			mSystemSprite.beginCache();
			
			// Draw travel lines
			List<TravelModel> travels = GameService.getInstance().getTraveLines();
			for (TravelModel travel : travels) {
				int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
				drawRectangle(mSystemSprite,
					mPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
					mPosY + Constants.SYSTEM_SIZE / 2 + travel.getY(),
					length,
					1,
					new Color(0.8f, 0.8f, 1, 0.55f),
					travel.getAngle());
			}
			
			// Draw system
			List<SystemModel> systems = GameService.getInstance().getSystems();
			for (SystemModel system : systems) {
				drawSystem(system);
			}
			
			mCacheId = mSystemSprite.endCache();
		}
		
		// Draw selection
		if (mCurrentTravelCacheId == -1 && (mTravelPath != null || mSelected != null)) {
			drawSelected();
		}
		
		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system: systems) {
			if (system.getFleets().size() > 0) {
				if (system.equals(mSelected)) {
					//drawRectangle(mSystemSprite, mPosX + system.getX() + 16, mPosY + system.getY() - 4, 16, 16, Color.RED);
				}
				draw(Art.ship, mPosX + system.getX() + 16, mPosY + system.getY() - 4);
			}
		}
		
		
		draw(mCurrentTravelCache, mCurrentTravelCacheId);

//		// Draws travel ships
//		for (TravelModel travel : travels) {
//			if (travel.getNbFleet() > 0) {
//				draw(Art.ship, mPosX + 4 + travel.getX(), mPosY + 4 + travel.getY(), travel.getAngle());
//			}
//		}
		
//		List<SystemModel> systems = GameService.getInstance().getSystems();
//		drawInterface(systems, 6, 6);
	}

	private void drawSelected () {
		mCurrentTravelCache.beginCache();
		
		// Draw travel paths
		if (mTravelPath != null) {
			for (TravelModel travel : mTravelPath) {
				int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
				drawRectangle(mCurrentTravelCache,
					0 - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
					0 - 1 + Constants.SYSTEM_SIZE / 2 + travel.getY(),
					length,
					3,
					new Color(0.75f, 0.9f, 0, 0.95f),
					travel.getAngle());
			}
			for (SystemModel system: mSystemPath) {
				mCurrentTravelCache.add(Art.system_selected, 0 + system.getX(), 0 + system.getY());
			}
		}
		
		// Draw selected
		else if (mSelected != null) {
			mCurrentTravelCache.add(Art.system_selected, 0 + mSelected.getX(), 0 + mSelected.getY());
		}
		
		mCurrentTravelCacheId = mCurrentTravelCache.endCache();
	}

	private void drawSystem (SystemModel system) {
		if (system.equals(mActionSystem)) {
			drawRectangle(mSystemSprite, mPosX + system.getX(), mPosY + system.getY(), 22, 22, Color.RED);
		}
		
		mSystemSprite.add(Art.system[system.getType()], mPosX + system.getX(), mPosY + system.getY());

		String name = system.getName();
		PlayerModel owner = system.getOwner();
		if (owner != null) {
			drawString(mSystemSprite, name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6, owner.getColor());
		} else {
			drawString(mSystemSprite, name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6, Color.WHITE);
		}
	}

	private void drawInterface (List<SystemModel> systems, int posX, int posY) {
		draw(Art.bt_planets, 6, 6);
		drawString("GOV.", posX + 2, posY + 34);

		draw(Art.bt_relations, 44, 6);
		drawString("RELS.", posX + 40, posY + 34);

		draw(Art.map, Constants.GAME_WIDTH - 64 - 6, 6);
		drawRectangle(Constants.GAME_WIDTH - 64 - 6 - mPosX / 20, 6 - mPosY / 20, 18, 12, Color.rgba8888(0.5f, 0.5f, 0.8f, 0.8f));
		drawString("Cycle:  " + mCycle, Constants.GAME_WIDTH - 64 - 4, posY + 49);

//		// Mini-map
//		for (SystemModel system : systems) {
//			Color color = system.getOwner() != null ? system.getOwner().getColor() : Color.WHITE;
//			drawRectangle(MAP_POS_X + system.getX() / 20, MAP_POS_Y + system.getY() / 20, 1, 1, color);
//		}
	}

	@Override
	public void onTouch (int x, int y) {

//		if (x < 32 && y < 32) {
//			GameService.getInstance().dump();
//			GameService.getInstance().getSystems().clear();
//		} else {
//			GameService.getInstance().addSystem(mPosX + x, mPosY + y);
//		}
		
		// Selected mode
		if (mSelected != null) {
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
			if (system != null && mSelected != system) {
				mActionSystem = system;
				mActionScreen.setActionSystem(system);
			}
		}
		
		// Normal mode
		else {

			// bt government
			if (x >= 6 && x <= 6 + 32 && y >= 2 && y <= 44) {
				addScreen(new PanelGovernmentScreen());
				return;
			}
			
			// bt relations
			if (x >= 44 && x <= 44 + 32 && y >= 2 && y <= 44) {
				addScreen(new PanelRealtionScreen());
				return;
			}
			
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
			if (system != null) {
				addScreen(new SystemScreen(system));
				return;
			}
			
			TravelModel travel = GameService.getInstance().getTravelAtPos(x - mPosX, y - mPosY);
			if (travel != null) {
				addScreen(new TravelScreen(travel));
				return;
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mPosX += offsetX;
		mPosY += offsetY;
	}

	public void gotoPos (int x, int y) {
		mPosX = - x + Constants.GAME_WIDTH / 2;
		mPosY = - y + Constants.GAME_HEIGHT / 2;
	}

	@Override
	public void onLongTouch (int x, int y) {
		mSelected = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
		mActionScreen = new SpaceActionScreen(this, mSelected);
		addScreen(mActionScreen);
		
		mBtArmada.setVisibility(View.GONE);
		mBtDebug.setVisibility(View.GONE);
		mBtPlanets.setVisibility(View.GONE);
		mBtRelations.setVisibility(View.GONE);
	}

	public void setTravelPath (List<TravelModel> travelPath, List<SystemModel> systemPath) {
		mTravelPath = travelPath;
		mSystemPath = systemPath;
		mCurrentTravelCache.clear();
		mCurrentTravelCacheId = -1;
	}

	public void setSelected (SystemModel selected) {
		mSelected = selected;
		mCurrentTravelCache.clear();
		mCurrentTravelCacheId = -1;
	}
}
