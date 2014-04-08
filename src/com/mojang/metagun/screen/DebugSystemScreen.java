package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.TextView;
import com.mojang.metagun.ui.View.OnClickListener;

public class DebugSystemScreen extends Screen {

	private SystemModel mSystem;

	public DebugSystemScreen (SystemModel s) {
		mSystem = s;
	}

	@Override
	protected void onCreate () {
		int i = 0;
		int j = 0;
		for (final PlanetModel p: mSystem.getPlanets()) {

			// Draw planet
			{
				TextView text = new TextView(p.getName() + ": " + p.getIndice(), 4, 12 + i++ * 10);
				text.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick () {
					}
				});
				addView(text);
			}
			
			// Draw fleet
			List<FleetModel> fleets = p.getOrbit();
			for (FleetModel f: fleets) {
				TextView text = new TextView(f.getName() + ": " + f.getNbShip() + " / " + f.getOwner().getName(), 120, 12 + j++ * 10);
				text.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick () {
					}
				});
				addView(text);
			}
		}

	}

	@Override
	public void onDraw (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
