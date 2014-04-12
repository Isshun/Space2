package org.bluebox.space2.game.screen;

import java.util.List;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.TextView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.SystemModel;


public class DebugSystemScreen extends BaseScreen {

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
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
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
