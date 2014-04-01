
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.Gdx;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Input;
import com.mojang.metagun.Space2;
import com.mojang.metagun.Sound;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;

public class PlanetScreen extends Screen {
	private static final int PLANET_REVOLUTION = 100;
	private static final int MOON_REVOLUTION = 100;
	private static final int PLANET_SPACING = 64;
	private static final int PLANET_SIZE = 42;

	private int time = 0;
	private PlanetModel mPlanet;
	private double tick;
	private SystemModel mSystem;

	public PlanetScreen (SystemModel system, PlanetModel planet) {
		mPlanet = planet;
		mSystem = system;
	}

	@Override
	public void render () {
		spriteBatch.begin();
		draw(Art.bg, 0, 0);
		
		int posX = Space2.GAME_WIDTH - 128 - 20;
		int posY = 20;

		if (Math.sin(tick) >= 0) {
			draw(Art.planet_128, posX, posY);
		}

		int moonPosX = posX + 128 / 2 + (int)(Math.cos(tick) * 80) - 16;
		int moonPosY = posY + 128 / 2 + (int)(Math.sin(tick) * 32) - 16;
		draw(Art.planet_32, moonPosX, moonPosY);
		
		if (Math.sin(tick) < 0) {
			draw(Art.planet_128, posX, posY);
		}
		
		drawRectangle(4, 4, Space2.GAME_WIDTH - 8, 18, Color.rgba8888(1, 1, 1, 0.5f));
		drawBigString(mPlanet.getName(), 8, 8);
		
		drawCharacteristics(4, 26);
		drawInfos(4, 100);
		
		spriteBatch.end();
	}

	private void drawCharacteristics (int posX, int posY) {
		drawRectangle(posX, posY, 134, 46, Color.rgba8888(1, 1, 1, 0.5f));
		drawRectangle(posX, posY, 134, 12, Color.rgba8888(1, 1, 1, 0.5f));
		drawString("Characteristics", posX + 4, posY + 4);
		drawString("Size:          " + mPlanet.getSizeName(), posX + 4, posY + 4 + 12);
		drawString("Class:   " + mPlanet.getClassification(), posX + 4, posY + 4 + 22);
		drawString("Population:         " + mPlanet.getPeople(), posX + 4, posY + 4 + 32);
	}

	private void drawInfos (int posX, int posY) {
		drawRectangle(posX, posY, 134, 72, Color.rgba8888(1, 1, 1, 0.5f));
		drawRectangle(posX, posY, 134, 12, Color.rgba8888(1, 1, 1, 0.5f));
		drawString("Production: ", posX + 4, posY + 4);
		
		posY += 10;
		draw(Art.res_food, posX + 4, posY + 8);
		drawString(String.format("Food:        %d (%d)", (int)mPlanet.getFood(), (int)mPlanet.getBaseFood()), posX + 20, posY + 10);

		posY += 10;
		draw(Art.res_construction, posX + 4, posY + 8);
		drawString(String.format("Build:       %d (%d)", (int)mPlanet.getBuild(), (int)mPlanet.getBaseBuild()), posX + 20, posY + 10);

		posY += 10;
		draw(Art.res_money, posX + 4, posY + 8);
		drawString(String.format("Money:       %d (%d)", (int)mPlanet.getMoney(), (int)mPlanet.getBaseMoney()), posX + 20, posY + 10);

		posY += 10;
		draw(Art.res_science, posX + 4, posY + 8);
		drawString(String.format("Science:     %d (%d)", (int)mPlanet.getScience(), (int)mPlanet.getBaseScience()), posX + 20, posY + 10);

		posY += 10;
		draw(Art.res_culture, posX + 4, posY + 8);
		drawString(String.format("Culture:     %d (%d)", (int)mPlanet.getCulture(), (int)mPlanet.getBaseCulture()), posX + 20, posY + 10);
	}

	@Override
	public void tick (Input input) {
		time++;
		tick += 0.001;
		
			if (Gdx.input.isKeyPressed(Keys.BACK)) {
				setScreen(new SystemScreen(mSystem));
			}
		
			if (time > Constants.TOUCH_RECOVERY && (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT] || Gdx.input.isTouched())) {
				
				
				if (Gdx.input.getDeltaX() > 42) {
					setScreen(new SystemScreen(mSystem));
				}
				
				input.releaseAllKeys();
		}
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub
		
	}

}
