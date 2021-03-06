
package org.bluebox.space2.game.screen;

import java.util.List;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.screen.BaseScreenLayer.StringConfig;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.SystemModel;

import com.badlogic.gdx.graphics.Color;

public class SystemScreen extends BaseScreen {
	private static final int PLANET_REVOLUTION = 96;
	private static final int PLANET_SPACING = 64;
	private static final int PLANET_SIZE = 42;

	private SystemModel	mSystem;
	private double 		tick;
	private boolean 		mTouch;
	private int 			mTouchX;
	private int 			mTouchY;
	private int 			mPlanetRevolution;
	private ButtonView 	mBtCancel;

	public SystemScreen (SystemModel system) {
		System.out.println("Open system: " + system.getName());
		mSystem = system;
		switch (mSystem.getType()) {
		case 0:
		case 2:
			mPlanetRevolution = PLANET_REVOLUTION;
			break;
		case 1:
			mPlanetRevolution = PLANET_REVOLUTION - 40;
			break;
		default:
			mPlanetRevolution = PLANET_REVOLUTION;
			break;
		}
	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.draw(Art.bg, 0, 0);
		mainLayer.draw(Art.sun[mSystem.getType()], -156, Constants.GAME_HEIGHT / 2 - 128);
		
		if (mSystem.getOwner() != null) {
			Color color = new Color(mSystem.getOwner().getColor());
			mainLayer.drawRectangle(6, 6, Constants.GAME_WIDTH - 12, 20, Color.rgba8888(color.r, color.g, color.b, 0.65f));
			mainLayer.draw(mSystem.getOwner().getFlag(), 8, 8);
			
			mainLayer.setStringSize(StringConfig.SIZE_BIG);
			mainLayer.drawString(mSystem.getName(), 36, 12);
			
			mainLayer.drawString(String.format("%s (%s)", mSystem.getOwner().getName(), mSystem.getOwner().getRelation()), 30, 32);
		} else {
			mainLayer.drawRectangle(6, 6, Constants.GAME_WIDTH - 10, 20, Color.rgba8888(1, 1, 1, 0.5f));
			mainLayer.drawString("Free", 12, 32);

			mainLayer.setStringSize(StringConfig.SIZE_BIG);
			mainLayer.drawString(mSystem.getName(), 12, 12);
		}
		mainLayer.drawString("indice: " + mSystem.getIndice(), Constants.GAME_WIDTH - 68, 32);

		List<PlanetModel> planets = mSystem.getPlanets();
		int pos = 0;
		for (PlanetModel planet: planets) {

//			double init = planet.getInitialTick();
//			int x = (int)(Math.cos(init + tick) * 100);
//			int y = (int)(Math.sin(init + tick) * 100);
//			if (tick > 1 || y > 0) {
//				x = mPlanetRevolution;
//				y = 0;
//			} else {
//				Game.requestRendering();
//			}
			int posX = mPlanetRevolution + PLANET_SPACING * pos;
			int posY = Constants.GAME_HEIGHT / 2 - 21 + 0;
			drawPlanet(mainLayer, planet, posX, posY);
			
			pos++;
		}
	}

	private void drawPlanet (BaseScreenLayer mainLayer, PlanetModel planet, int posX, int posY) {
		int offsetY = 0;
		switch (planet.getSize()) {
		case 0:
			mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_16], posX + 26 - 8, posY + 22 - 8);
			offsetY = 8;
			break;
		case 1:
			mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_24], posX + 26 - 12, posY + 22 - 12);
			offsetY = 12;
			break;
		case 2:
		default:
			mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_32], posX + 26 - 16, posY + 22 - 16);
			offsetY = 16;
			break;
		case 3:
			mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_42], posX + 26 - 21, posY + 22 - 21);
			offsetY = 21;
			break;
		case 4:
			mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_56], posX + 26 - 28, posY + 22 - 28);
			offsetY = 28;
			break;
		}
		String name = planet.getShortName();
		mainLayer.drawString(name, posX + 26 - name.length() * 3, posY + 32 + Math.max(offsetY, 21));
		
		String code = planet.getShortClassification() + planet.getShortSizeName();
		mainLayer.drawString(code, posX + 26 - code.length() * 3, posY + 44 + Math.max(offsetY, 21));

		if (planet.getOwner() != null) {
//			drawRectangle(posX + 26 - "colonized".length() * 3 - 1, posY - 32 - 3, "colonized".length() * 6 + 3, 11, planet.getOwner().getDarkColor());
//			drawString("colonized", posX + 27 - "colonized".length() * 3, posY - 32);
			mainLayer.setStringColor(planet.getOwner().getColor());
			mainLayer.drawString(planet.getOwner().getName(), posX + 27 - planet.getOwner().getName().length() * 3, posY - 22);
		}
		
	}

	@Override
	public void onTouch (int x, int y) {
		if (Math.abs(y - Constants.GAME_HEIGHT / 2) <= PLANET_SIZE / 2) {
			if ((x - mPlanetRevolution) % PLANET_SPACING <= PLANET_SIZE) {
				int pos = (x - mPlanetRevolution) / PLANET_SPACING;
				if (pos >= 0 && mSystem.getPlanets().size() > pos) {
					addScreen(new PlanetScreen(mSystem, mSystem.getPlanets().get(pos)));
				}
			}
		}
	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
	}

	@Override
	protected void onCreate () {
		super.onCreate();

		System.out.println("System pos:" + mSystem.getX() + ", " + mSystem.getY());
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
