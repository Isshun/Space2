
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.FleetModel;
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
	private Color[][]				mMap;

	public SpaceScreen () {
		mParalax = Art.bg;
		mCurrentTravelCache = new SpriteCache(100, true);
		mCurrentTravelCacheId = -1;
		mMap = new Color[400][400];
	}
	
	@Override
	protected void onCreate () {

		for (int x = 0; x < 400; x++) {
			for (int y = 0; y < 400; y++) {
				mMap[x][y] = null;
			}
		}

		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
			for (SystemModel system: systems) {
				if (system.getOwner() != null) {
					int x2 = system.getX() / 4;
					int y2 = system.getY() / 4;
					
					System.out.println("POS: " + x2 + " x " + y2);
					
					if (x2+x >= 0 && y2+y >= 0 && x2+x < 400 && y2+y < 400 && mMap[x2+x][y2+y] == null) {
						mMap[x2+x][y2+y] = system.getOwner().getColor();
					}
					if (x2-x >= 0 && y2+y >= 0 && x2-x < 400 && y2+y < 400 && mMap[x2-x][y2+y] == null) {
						mMap[x2-x][y2+y] = system.getOwner().getColor();
					}
					if (x2+x >= 0 && y2-y >= 0 && x2+x < 400 && y2-y < 400 && mMap[x2+x][y2-y] == null) {
						mMap[x2+x][y2-y] = system.getOwner().getColor();
					}
					if (x2-x >= 0 && y2-y >= 0 && x2-x < 400 && y2-y < 400 && mMap[x2-x][y2-y] == null) {
						mMap[x2-x][y2-y] = system.getOwner().getColor();
					}
//							if (x2-x >= 0 && y2-y >= 0 && x2-x < 100 && y2-y < 100 && mMap[x2-x][y2-y] == null) {
//								mMap[x2-x][y2-y] = system.getOwner().getColor();
//							}
//							if (y2-y >= 0 && x2+x < 100 && y2-y < 100 && mMap[x2+x][y2-y] == null) {
//								mMap[x2+x][y2-y] = system.getOwner().getColor();
//							}
//							if (x2-x >= 0 && x2-x < 100 && y2+y < 100 && mMap[x2-x][y2+y] == null) {
//								mMap[x2-x][y2+y] = system.getOwner().getColor();
//							}
							
							
//							if (x2+i < 100 && y2+i < 100 && mMap[x2+i][y2+i] == null) {
//								mMap[x2+i][y2+i] = system.getOwner().getColor();
//							}
//							if (x2-i >= 0 && y2-i >=0 && x2-i < 100 && y2-i < 100 && mMap[x2-i][y2-i] == null) {
//								mMap[x2-i][y2-i] = system.getOwner().getColor();
//							}
//							if (x2-i >= 0 && x2 < 100 && y2+i < 100 && mMap[x2-i][y2+i] == null) {
//								mMap[x2-i][y2+i] = system.getOwner().getColor();
//							}
//							if (y2 - i >= 0 && x2 < 100 && y2-i < 100 && mMap[x2+i][y2-i] == null) {
//								mMap[x2+i][y2-i] = system.getOwner().getColor();
//							}
						}
					}
				}
			}
		
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
		List<SystemModel> systems = GameService.getInstance().getSystems();
		
		// Draw fleets
		for (SystemModel system: systems) {
			if (system.getFleets().size() > 0) {
				drawRectangle(spriteBatch, mRealPosX + system.getX() + 16, mRealPosY + system.getY() - 4, 16, 16, system.getFleets().get(0).getOwner().getColor());
				draw(spriteBatch, Art.ship, mRealPosX + system.getX() + 16, mRealPosY + system.getY() - 4);
				if (system.getFleets().size() > 1) {
					drawString(spriteBatch, "+" + (system.getFleets().size() - 1), mRealPosX + system.getX() + 28, mRealPosY + system.getY() + 12);
				}
			}
		}
	}

	@Override
	public void onDraw (int gameTime, int screenTime) {

		for (int x = 0; x < 400; x++) {
			for (int y = 0; y < 400; y++) {
				if (mMap[x][y] != null) {
					drawRectangle(x * 4 + Constants.SYSTEM_SIZE / 2, y * 4 + Constants.SYSTEM_SIZE / 2, 4, 4, mMap[x][y]);
				}
			}
		}

		// Draw travel lines
		List<TravelModel> travels = GameService.getInstance().getTraveLines();
		for (TravelModel travel : travels) {
			int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
			drawRectangle(
				mDeprecatedPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
				mDeprecatedPosY + Constants.SYSTEM_SIZE / 2 + travel.getY(),
				length,
				1,
				new Color(0.8f, 0.8f, 1, 0.55f),
				travel.getAngle());
		}
		
		// Draw selected travel path
		if (mTravelPath != null) {
			for (TravelModel travel : mTravelPath) {
				int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
				drawRectangle(
					mDeprecatedPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
					mDeprecatedPosY - 1 + Constants.SYSTEM_SIZE / 2 + travel.getY(),
					length,
					2,
					new Color(0.75f, 0.9f, 0, 0.95f),
					travel.getAngle());
			}
			for (SystemModel system: mSystemPath) {
				if (system != mSelected) {
					draw(Art.system_selected[system.getType()], mDeprecatedPosX + system.getX(), mDeprecatedPosY + system.getY());
				}
			}
			
			draw(Art.system_selected[mSelected.getType()], mDeprecatedPosX + mSelected.getX(), mDeprecatedPosY + mSelected.getY());
		}
		
		// Draw selected system
		else if (mSelected != null) {
			draw(Art.system_selected[mSelected.getType()], mDeprecatedPosX + mSelected.getX(), mDeprecatedPosY + mSelected.getY());
		}

		// Draw system
		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system : systems) {
			drawSystem(system);
		}

//		// Draws travel ships
//		for (TravelModel travel : travels) {
//			if (travel.getNbFleet() > 0) {
//				draw(Art.ship, mPosX + 4 + travel.getX(), mPosY + 4 + travel.getY(), travel.getAngle());
//			}
//		}
		
//		List<SystemModel> systems = GameService.getInstance().getSystems();
//		drawInterface(systems, 6, 6);
	}

	private void drawSystem (SystemModel system) {
		if (system.equals(mActionSystem)) {
			drawRectangle(mDeprecatedPosX + system.getX(), mDeprecatedPosY + system.getY(), 22, 22, Color.RED);
		}
		
		draw(Art.system[system.getType()], mDeprecatedPosX + system.getX(), mDeprecatedPosY + system.getY());

		String name = system.getName();
		PlayerModel owner = system.getOwner();
		if (owner != null) {
			drawString(name, mDeprecatedPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mDeprecatedPosY + system.getY() + Constants.SYSTEM_SIZE + 6, owner.getColor());
		} else {
			drawString(name, mDeprecatedPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mDeprecatedPosY + system.getY() + Constants.SYSTEM_SIZE + 6, Color.WHITE);
		}
	}

	private void drawInterface (List<SystemModel> systems, int posX, int posY) {
		draw(Art.bt_planets, 6, 6);
		drawString("GOV.", posX + 2, posY + 34);

		draw(Art.bt_relations, 44, 6);
		drawString("RELS.", posX + 40, posY + 34);

		draw(Art.map, Constants.GAME_WIDTH - 64 - 6, 6);
		drawRectangle(Constants.GAME_WIDTH - 64 - 6 - mDeprecatedPosX / 20, 6 - mDeprecatedPosY / 20, 18, 12, Color.rgba8888(0.5f, 0.5f, 0.8f, 0.8f));
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
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
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
			
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
			if (system != null) {
				addScreen(new SystemScreen(system));
				return;
			}
			
			TravelModel travel = GameService.getInstance().getTravelAtPos(x - mRealPosX, y - mRealPosY);
			if (travel != null) {
				addScreen(new TravelScreen(travel));
				return;
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mRealPosX += offsetX;
		mRealPosY += offsetY;
		mParalaxNotified = true;
	}

	public void gotoPos (int x, int y) {
		mRealPosX = - x + Constants.GAME_WIDTH / 2;
		mRealPosY = - y + Constants.GAME_HEIGHT / 2;
	}

	@Override
	public void onLongTouch (int x, int y) {
		SystemModel selected = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
		if (selected != null) {
			setSelected(selected);
			mActionScreen = new SpaceActionScreen(this, mSelected);
			addScreen(mActionScreen);
			
			mBtArmada.setVisibility(View.GONE);
			mBtDebug.setVisibility(View.GONE);
			mBtPlanets.setVisibility(View.GONE);
			mBtRelations.setVisibility(View.GONE);
			
			notifyChange();
		}
	}

	public void setTravelPath (List<TravelModel> travelPath, List<SystemModel> systemPath) {
		mTravelPath = travelPath;
		mSystemPath = systemPath;
		notifyChange();
	}

	public void setSelected (SystemModel selected) {
		mSelected = selected;
		notifyChange();
	}

}
