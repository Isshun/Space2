
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Input;
import com.mojang.metagun.Space2;
import com.mojang.metagun.Sound;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;

public class SystemScreen extends Screen {
	private static final int PLANET_REVOLUTION = 100;
	private static final int PLANET_SPACING = 64;
	private static final int PLANET_SIZE = 42;

	private SystemModel mSystem;
	private double tick;
	private boolean mTouch;
	private int mTouchX;
	private int mTouchY;

	public SystemScreen (SystemModel system) {
		System.out.println("Open system: " + system.getName());
		mSystem = system;
	}

	@Override
	public void render () {
		mSpriteBatch.begin();
		draw(Art.bg, 0, 0);
		draw(Art.sun, -64, Constants.GAME_HEIGHT / 2 - 64);
		
		drawRectangle(6, 6, Constants.GAME_WIDTH - 10, 20, Color.rgba8888(1, 1, 1, 0.5f));
		drawBigString(mSystem.getName(), 12, 12);

		if (mSystem.getOwner() != null) {
			draw(mSystem.getOwner().getFlag(), 4, 32);
			drawString(String.format("%s (%s)", mSystem.getOwner().getName(), mSystem.getOwner().getRelation()), 30, 32);
		} else {
			drawString("Free", 12, 32);
		}

		List<PlanetModel> planets = mSystem.getPlanets();
		int pos = 0;
		for (PlanetModel planet: planets) {

			double init = planet.getInitialTick();
			int x = (int)(Math.cos(init + tick) * 100);
			int y = (int)(Math.sin(init + tick) * 100);
			if (tick > 1 || y > 0) {
				x = PLANET_REVOLUTION;
				y = 0;
			} else {
				Gdx.graphics.requestRendering();
			}
			int posX = x + PLANET_SPACING * pos;
			int posY = Constants.GAME_HEIGHT / 2 - 21 + y;
			drawPlanet(planet, posX, posY);
			
			pos++;
		}
		
		mSpriteBatch.end();
	}

	private void drawPlanet (PlanetModel planet, int posX, int posY) {
		draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_42], posX, posY);
		drawString(planet.getShortName(), posX, posY + 42 + 6);
		drawString("Size: " + planet.getShortSizeName(), posX, posY + 42 + 16);
		drawString("Class:  " + planet.getShortClassification(), posX, posY + 42 + 26);
	}

	@Override
	public void onTouch (int x, int y) {
		if (Math.abs(y - Constants.GAME_HEIGHT / 2) <= PLANET_SIZE / 2) {
			if ((x - PLANET_REVOLUTION) % PLANET_SPACING <= PLANET_SIZE) {
				int pos = (x - PLANET_REVOLUTION) / PLANET_SPACING;
				if (mSystem.getPlanets().size() > pos) {
					addScreen(new PlanetScreen(mSystem, mSystem.getPlanets().get(pos)));
				}
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
	}

}
