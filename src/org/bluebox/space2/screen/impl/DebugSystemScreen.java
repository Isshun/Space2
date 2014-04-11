package org.bluebox.space2.screen.impl;

import java.util.List;

import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.screen.ScreenBase;
import org.bluebox.space2.screen.ScreenLayerBase;
import org.bluebox.space2.service.GameService;
import org.bluebox.space2.ui.TextView;
import org.bluebox.space2.ui.View.OnClickListener;


public class DebugSystemScreen extends ScreenBase {

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
	public void onDraw (ScreenLayerBase mainLayer, ScreenLayerBase UILayer) {
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
