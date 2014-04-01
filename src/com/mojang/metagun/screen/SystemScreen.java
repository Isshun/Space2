
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
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

	private int time = 0;
	private SystemModel mSystem;
	private double tick;
	private boolean mTouch;
	private int mTouchX;
	private int mTouchY;

	public SystemScreen (SystemModel system) {
		mSystem = system;
	}

	@Override
	public void render () {
		spriteBatch.begin();
		draw(Art.bg, 0, 0);
		draw(Art.sun, -64, Space2.GAME_HEIGHT / 2 - 64);
		drawString(mSystem.getName(), 8, Space2.GAME_HEIGHT / 2 + 64 + 8);

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
			int posY = Space2.GAME_HEIGHT / 2 - 21 + y;
			drawPlanet(planet, posX, posY);
			
			pos++;
		}
		
		spriteBatch.end();
	}

	private void drawPlanet (PlanetModel planet, int posX, int posY) {
		draw(Art.planet, posX, posY);
		drawString(planet.getShortName(), posX, posY + 42 + 6);
		drawString("Size: " + planet.getShortSizeName(), posX, posY + 42 + 16);
		drawString("Class:  " + planet.getShortClassification(), posX, posY + 42 + 26);
	}

	@Override
	public void tick (Input input) {
		time++;
		tick += 0.01;
		
			if (time > Constants.TOUCH_RECOVERY && (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT] || Gdx.input.isTouched())) {
				
				if (Gdx.input.getDeltaX() > 42) {
					setScreen(new SpaceScreen());
				}
				
				mTouch = true;
				mTouchX = Gdx.input.getX();
				mTouchY = Gdx.input.getY();
				
				//if (x - PLANET_OFFSET_LEFT % PLANET_SPACING)
				
//				PlanetModel planet = mSystem.getPlanetA();
//				if (planet != null) {
//					System.out.println("Open system: " + system.getName());
//					setScreen(new SystemScreen(system));
//				}

				
				input.releaseAllKeys();
		}
			
			if (Gdx.input.isTouched() == false && mTouch) {
				mTouch = false;
				
				int x = mTouchX * Space2.GAME_WIDTH / Gdx.graphics.getWidth();
				int y = mTouchY * Space2.GAME_HEIGHT / Gdx.graphics.getHeight();
				
				if (Math.abs(y - Space2.GAME_HEIGHT / 2) <= PLANET_SIZE / 2) {
					if ((x - PLANET_REVOLUTION) % PLANET_SPACING <= PLANET_SIZE) {
						int pos = (x - PLANET_REVOLUTION) / PLANET_SPACING;
						if (mSystem.getPlanets().size() > pos) {
							setScreen(new PlanetScreen(mSystem, mSystem.getPlanets().get(pos)));
						}
					}
				}
			}
	}

}
