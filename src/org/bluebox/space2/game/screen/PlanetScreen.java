
package org.bluebox.space2.game.screen;

import java.util.List;

import org.bluebox.space2.Utils;
import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.ImageView;
import org.bluebox.space2.engine.ui.RectangleView;
import org.bluebox.space2.engine.ui.View;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game.Anim;
import org.bluebox.space2.game.model.DockModel;
import org.bluebox.space2.game.model.StructureModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.SystemModel;

import com.badlogic.gdx.graphics.Color;

public class PlanetScreen extends BaseScreen {
	private static final int PLANET_REVOLUTION = 100;
	private static final int MOON_REVOLUTION = 100;
	private static final int PLANET_SPACING = 64;
	private static final int PLANET_SIZE = 42;

	private int time = 0;
	PlanetModel mPlanet;
	private double tick;
	private SystemModel mSystem;
	private Color mColor;
	private RectangleView mBtShip;
	private RectangleView mBtStructure;
	private ButtonView mBtDock;
	private ButtonView mBtBuildings;

	public PlanetScreen (SystemModel system, PlanetModel planet) {
		mPlanet = planet;
		mSystem = system;
		//mColor = planet.getOwner() != null ? planet.getOwner().getUIColor() : new Color(1, 0.5f, 0.5f, 0.5f);
		mColor = new Color(1, 1, 1, 0.45f);
		mRefreshOnUpdate = true;
	}

	@Override
	protected void onCreate () {
		super.onCreate();
		
		ButtonView btDebug = new ButtonView("Debug", Constants.GAME_HEIGHT - 20, Constants.GAME_WIDTH - 70);
		btDebug.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PlanetDebugScreen(mPlanet));
			}
		});
		addView(btDebug);
		
		// Button ship
		mBtShip = new ButtonView(6, Constants.GAME_HEIGHT - 20, 60, 20, mColor);
		mBtShip.setText("ship");
		mBtShip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PlanetBuildShipScreen(PlanetScreen.this, mPlanet));
			}
		});
		addView(mBtShip);
		
		// Button structure
		mBtStructure = new ButtonView(126, Constants.GAME_HEIGHT - 20, 60, 20, mColor);
		mBtStructure.setText("structure");
		mBtStructure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				BaseScreen s = new PlanetBuildStructureScreen(PlanetScreen.this, mPlanet);
				s.setTransition(Anim.FLIP_BOTTOM);
				addScreen(s);
			}
		});
		addView(mBtStructure);
		
		// Button building
		mBtBuildings = new ButtonView(200, Constants.GAME_HEIGHT - 20, 60, 20, mColor);
		mBtBuildings.setText("installations");
		mBtBuildings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				BaseScreen s = new PlanetStructureScreen(PlanetScreen.this, mPlanet);
				s.setTransition(Anim.ZOOM);
				addScreen(s);
			}
		});
		addView(mBtBuildings);
		
		// Dock
		mBtDock = new ButtonView(280, Constants.GAME_HEIGHT - 20, 60, 20, mColor);
		//mBtDock = new ButtonView(Constants.GAME_WIDTH - 200, 100, 42, 60, Color.RED);
		mBtDock.setText("dock");
		mBtDock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				FleetInfoScreen s = new FleetInfoScreen(mPlanet.getDock());
				s.setTransition(Anim.FLIP_BOTTOM);
				addScreen(s);
			}
		});
		addView(mBtDock);
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		int planetX = Constants.GAME_WIDTH - 128 - 40;
		int planetY = 40;
		
		int nbShip = mPlanet.getFleets().size();
		if (nbShip > 0) {
			double interval = (4.05 + 0.85) / nbShip;
			boolean isPlanetDrew = false;
			for (double i = -3.14; i < 3.14; i += interval) {
				// Planet
				if (i > 0 && !isPlanetDrew) {
					isPlanetDrew = true;
					mainLayer.draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], planetX, planetY);
				}
				// Ships in orbit
				if (i < -2.23 || i > -0.85) {
					int shipX = planetX + 128 / 2 + (int)(Math.cos(i) * 90) - 8;
					int shipY = planetY + 128 / 2 + (int)(Math.sin(i) * 40) - 8;
					mainLayer.draw(Art.ship, shipX, shipY);
				}
			}
		} else {
			mainLayer.draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], planetX, planetY);
		}
		
		if (mPlanet.getDock() != null) {
			int shipX = planetX - 128 / 2;
			int shipY = planetY;
			mainLayer.draw(Art.dock, shipX, shipY);
			mBtDock.setVisibility(View.VISIBLE);
		} else {
			mBtDock.setVisibility(View.GONE);
		}
		

		
//		if (Math.sin(tick) >= 0) {
//			draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], posX, posY);
//		}

//		int moonPosX = posX + 128 / 2 + (int)(Math.cos(tick) * 80) - 16;
//		int moonPosY = posY + 128 / 2 + (int)(Math.sin(tick) * 32) - 16;
//		draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_32], moonPosX, moonPosY);
//		
//		if (Math.sin(tick) < 0) {
//			draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], posX, posY);
//		}
		
		mainLayer.drawRectangle(6, 6, Constants.GAME_WIDTH - 12, 20, mColor);

//		if (mPlanet.getOwner() != null) {
//			drawBigString(mPlanet.getName(), 12, 12, mPlanet.getOwner().getColor());
//		} else {
//		}
		
		if (isTop()) {
//			mainLayer.drawString("Population:         " + mPlanet.getPeople(), posX + 4, posY + 4 + 32);
			//drawCharacteristics(mainLayer, 6, 32);
			mainLayer.setStringSize(StringConfig.SIZE_BIG);
			mainLayer.drawString(mPlanet.getName(), 12, 12);
			mainLayer.drawString("" + mPlanet.getSizeName() + " / " + mPlanet.getClassName(), 20 + 12 * mPlanet.getName().length(), 17);
			
			mBtBack.setVisibility(View.VISIBLE);

			drawCurrentBuilding(mainLayer, 6, 32);
			drawCurrentDock(mainLayer, 6, 85);
			drawInfos(mainLayer, 200, 84);
			mBtShip.setVisibility(mPlanet.getDock() != null ? View.VISIBLE : View.GONE);
			mBtStructure.setVisibility(View.VISIBLE);
		} else {
			//mainLayer.drawString("Build on " + mPlanet.getName(), 12, 12);
			mBtBack.setVisibility(View.GONE);
			mBtShip.setVisibility(View.GONE);
			mBtStructure.setVisibility(View.GONE);
		}
		
//		drawString("ship", 6, Constants.GAME_HEIGHT - 20);
//		drawString("structure", 126, Constants.GAME_HEIGHT - 20);
	}

	private void drawCharacteristics (BaseScreenLayer mainLayer, int posX, int posY) {
		mainLayer.drawRectangle(posX, posY, 134, 46, mColor);
		mainLayer.drawRectangle(posX, posY, 134, 12, mColor);
		mainLayer.drawString("Info", posX + 4, posY + 4);
		mainLayer.drawString("Size:          " + mPlanet.getSizeName(), posX + 4, posY + 4 + 12);
		mainLayer.drawString("Class:   " + mPlanet.getClassName(), posX + 4, posY + 4 + 22);
		mainLayer.drawString("Population:         " + mPlanet.getPopulation(), posX + 4, posY + 4 + 32);
	}

	private void drawCurrentBuilding(BaseScreenLayer mainLayer, int posX, int posY) {
		mainLayer.drawRectangle(posX, posY, 138, 48, mColor);
		mainLayer.drawRectangle(posX, posY, 138, 12, mColor);
		mainLayer.drawString("Building on planet", posX + 4, posY + 4);
		int size = mPlanet.getStructuresToBuild().size();
		int totalETA = 0;
		for (int i = 0; i < Math.min(size, 4); i++) {
			if (i < 3) {
				StructureModel structure = mPlanet.getStructuresToBuild().get(i);
				totalETA += mPlanet.getBuildETA(mPlanet.getStructuresToBuild().get(i).getBuildRemain());
				String eta = Utils.getFormatedTime(totalETA);
				String shortName = structure.getShortName();
				if (shortName != null && shortName.length() > 3) {
					shortName = shortName.substring(0, 3) + ".";
				}
				mainLayer.draw(structure.getIcon(), posX + 2 + 34 * i, posY + 14);
				mainLayer.drawRectangle(posX + 2 + 34 * i, posY + 30, 32, 16, new Color(0, 0, 0, 0.5f));
				mainLayer.drawString(shortName, posX + 4 + 34 * i, posY + 32);
				mainLayer.drawString(eta, posX + 4 + 34 * i, posY + 39);
			} else {
				mainLayer.drawRectangle(posX + 2 + 34 * i, posY + 14, 32, 32, new Color(0.5f, 0.5f, 0.2f, 1));
				mainLayer.drawString("+" + (size - 3), posX + 2 + 34 * i, posY + 16);
			}
		}
	}

	private void drawCurrentDock(BaseScreenLayer mainLayer, int posX, int posY) {
		DockModel dock = mPlanet.getDock();
		if (dock != null) {
			mainLayer.drawRectangle(posX, posY, 134, 46, mColor);
			mainLayer.drawRectangle(posX, posY, 134, 12, mColor);
			mainLayer.drawString("Building at dock", posX + 4, posY + 4);
			int size = dock.getShipToBuild().size();
			int totalETA = 0;
			for (int i = 0; i < Math.min(size, 4); i++) {
				mainLayer.drawRectangle(posX + 4 + 31 * i, posY + 16, 26, 26, new Color(0.5f, 0.5f, 0.2f, 1));
				if (i < 3) {
					totalETA += mPlanet.getBuildETA(dock.getShipToBuild().get(i).getBuildRemain());
					String eta = Utils.getFormatedTime(totalETA);
					String className = dock.getShipToBuild().get(i).getClassName().substring(0, 3) + ".";
					mainLayer.drawString(className, posX + 4 + 31 * i, posY + 16);
					mainLayer.drawString(eta, posX + 4 + 31 * i, posY + 24);
				} else {
					mainLayer.drawString("+" + (size - 3), posX + 4 + 31 * i, posY + 16);
				}
			}
		}
	}

	private void drawInfos (BaseScreenLayer mainLayer, int posX, int posY) {
		mainLayer.drawRectangle(posX, posY, 134, 72, mColor);
		mainLayer.drawRectangle(posX, posY, 134, 12, mColor);
		//mainLayer.drawString("Production ", posX + 4, posY + 4);

		mainLayer.drawString(String.format("^%d *%d $%d @%d &%d",
			(int)mPlanet.getFood(),
			(int)mPlanet.getProd(),
			(int)mPlanet.getMoney(),
			(int)mPlanet.getCulture(),
			(int)mPlanet.getScience()
			), posX + 20, posY + 4);

		
//		posY += 14;
//		mainLayer.draw(Art.res_food, posX + 4, posY);
//		mainLayer.drawString(String.format("Food:        %d (%d)", (int)mPlanet.getFood(), (int)mPlanet.getBaseFood()), posX + 20, posY + 4);
//
//		posY += 12;
		mainLayer.draw(Art.ic_construction_12, posX + 4, posY);
//		mainLayer.drawString(String.format("Build:       %d (%d)", (int)mPlanet.getProd(), (int)mPlanet.getBaseBuild()), posX + 20, posY + 4);
//
//		posY += 12;
//		mainLayer.draw(Art.ic_money_12, posX + 4, posY);
//		mainLayer.drawString(String.format("Money:       %d (%d)", (int)mPlanet.getMoney(), (int)mPlanet.getBaseMoney()), posX + 20, posY + 4);
//
//		posY += 12;
//		mainLayer.draw(Art.ic_science, posX + 4, posY);
//		mainLayer.drawString(String.format("Science:     %d (%d)", (int)mPlanet.getScience(), (int)mPlanet.getBaseScience()), posX + 20, posY + 4);
//
//		posY += 12;
//		mainLayer.draw(Art.res_culture, posX + 4, posY);
//		mainLayer.drawString(String.format("Culture:     %d (%d)", (int)mPlanet.getCulture(), (int)mPlanet.getBaseCulture()), posX + 20, posY + 4);
	}

	@Override
	public void onNext () {
		List<PlanetModel> planets = mSystem.getPlanets();
		int index = planets.indexOf(mPlanet);
		if (index < planets.size() - 1) {
			PlanetModel planet = planets.get(Math.min(index + 1, planets.size() - 1));
			mGame.replaceScreen(this, new PlanetScreen(mSystem, planet), Anim.FLIP_RIGHT);
		}
	}

	@Override
	public void onPrev () {
		List<PlanetModel> planets = mSystem.getPlanets();
		int index = planets.indexOf(mPlanet);
		if (index > 0) {
			PlanetModel planet = planets.get(Math.max(index - 1, 0));
			mGame.replaceScreen(this, new PlanetScreen(mSystem, planet), Anim.FLIP_LEFT);
		}
	}

	@Override
	public void onTouch (int x, int y) {
	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
