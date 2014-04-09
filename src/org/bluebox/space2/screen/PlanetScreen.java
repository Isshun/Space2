
package org.bluebox.space2.screen;

import java.util.List;

import org.bluebox.space2.Art;
import org.bluebox.space2.Constants;
import org.bluebox.space2.StringConfig;
import org.bluebox.space2.Game.Anim;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.ui.ImageView;
import org.bluebox.space2.ui.RectangleView;
import org.bluebox.space2.ui.View;
import org.bluebox.space2.ui.View.OnClickListener;

import com.badlogic.gdx.graphics.Color;

public class PlanetScreen extends Screen {
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

	public PlanetScreen (SystemModel system, PlanetModel planet) {
		mPlanet = planet;
		mSystem = system;
		//mColor = planet.getOwner() != null ? planet.getOwner().getUIColor() : new Color(1, 0.5f, 0.5f, 0.5f);
		mColor = new Color(1, 1, 1, 0.45f);
		
		mBtShip = new ButtonView(6, Constants.GAME_HEIGHT - 20, 100, 20, mColor);
		mBtShip.setText("ship");
		mBtShip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PlanetBuildShipScreen(PlanetScreen.this, mPlanet));
			}
		});
		addView(mBtShip);

		mBtStructure = new ButtonView(126, Constants.GAME_HEIGHT - 20, 100, 20, mColor);
		mBtStructure.setText("structure");
		mBtStructure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				Screen s = new PlanetBuildStructureScreen(PlanetScreen.this, mPlanet);
				s.setTransition(Anim.FLIP_BOTTOM);
				addScreen(s);
			}
		});
		addView(mBtStructure);
	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		int planetX = Constants.GAME_WIDTH - 128 - 40;
		int planetY = 40;
		
		int nbShip = mPlanet.getOrbit().size();
		if (nbShip > 0) {
			double interval = (4.05 + 0.85) / nbShip;
			boolean isPlanetDrew = false;
			for (double i = -3.14; i < 3.14; i += interval) {
				// Planet
				if (i > 0 && !isPlanetDrew) {
					isPlanetDrew = true;
					draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], planetX, planetY);
				}
				// Ships in orbit
				if (i < -2.23 || i > -0.85) {
					int shipX = planetX + 128 / 2 + (int)(Math.cos(i) * 90) - 8;
					int shipY = planetY + 128 / 2 + (int)(Math.sin(i) * 40) - 8;
					draw(Art.ship, shipX, shipY);
				}
			}
		} else {
			draw(Art.planets[mPlanet.getClassification().id][Art.PLANET_RES_128], planetX, planetY);
		}
		
		if (mPlanet.getDock() != null) {
			int shipX = planetX - 128 / 2;
			int shipY = planetY;
			draw(Art.dock, shipX, shipY);
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
		
		drawRectangle(6, 6, Constants.GAME_WIDTH - 12, 20, mColor);

//		if (mPlanet.getOwner() != null) {
//			drawBigString(mPlanet.getName(), 12, 12, mPlanet.getOwner().getColor());
//		} else {
//		}
		
		if (isTop()) {
			setStringSize(StringConfig.SIZE_BIG);
			drawString(mPlanet.getName(), 12, 12);
			drawCharacteristics(6, 32);
			drawInfos(6, 84);
			mBtShip.setVisibility(mPlanet.getDock() != null ? View.VISIBLE : View.GONE);
			mBtStructure.setVisibility(View.VISIBLE);
		} else {
			setStringSize(StringConfig.SIZE_BIG);
			drawString("Build on " + mPlanet.getName(), 12, 12);
			mBtShip.setVisibility(View.GONE);
			mBtStructure.setVisibility(View.GONE);
		}
		
//		drawString("ship", 6, Constants.GAME_HEIGHT - 20);
//		drawString("structure", 126, Constants.GAME_HEIGHT - 20);
	}

	private void drawCharacteristics (int posX, int posY) {
		drawRectangle(posX, posY, 134, 46, mColor);
		drawRectangle(posX, posY, 134, 12, mColor);
		drawString("Info", posX + 4, posY + 4);
		drawString("Size:          " + mPlanet.getSizeName(), posX + 4, posY + 4 + 12);
		drawString("Class:   " + mPlanet.getClassName(), posX + 4, posY + 4 + 22);
		drawString("Population:         " + mPlanet.getPeople(), posX + 4, posY + 4 + 32);
	}

	private void drawInfos (int posX, int posY) {
		drawRectangle(posX, posY, 134, 72, mColor);
		drawRectangle(posX, posY, 134, 12, mColor);
		drawString("Production ", posX + 4, posY + 4);
		
		posY += 14;
		draw(Art.res_food, posX + 4, posY);
		drawString(String.format("Food:        %d (%d)", (int)mPlanet.getFood(), (int)mPlanet.getBaseFood()), posX + 20, posY + 4);

		posY += 12;
		draw(Art.ic_construction_12, posX + 4, posY);
		drawString(String.format("Build:       %d (%d)", (int)mPlanet.getBuild(), (int)mPlanet.getBaseBuild()), posX + 20, posY + 4);

		posY += 12;
		draw(Art.ic_money_12, posX + 4, posY);
		drawString(String.format("Money:       %d (%d)", (int)mPlanet.getMoney(), (int)mPlanet.getBaseMoney()), posX + 20, posY + 4);

		posY += 12;
		draw(Art.ic_science, posX + 4, posY);
		drawString(String.format("Science:     %d (%d)", (int)mPlanet.getScience(), (int)mPlanet.getBaseScience()), posX + 20, posY + 4);

		posY += 12;
		draw(Art.res_culture, posX + 4, posY);
		drawString(String.format("Culture:     %d (%d)", (int)mPlanet.getCulture(), (int)mPlanet.getBaseCulture()), posX + 20, posY + 4);
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
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreate () {
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
