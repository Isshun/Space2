package org.bluebox.space2.screen;

import java.util.List;

import org.bluebox.space2.Constants;
import org.bluebox.space2.Utils;
import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.PlanetModel;
import org.bluebox.space2.model.ShipClassModel;
import org.bluebox.space2.model.ShipModel;
import org.bluebox.space2.service.GameService;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlanetBuildShipScreen extends Screen {

	private static final int 	POS_Y = Constants.GAME_HEIGHT - 100;
	private static final int 	LIST_START_Y = 19;
	private static final int 	LINE_INTERVAL = 12;
	
	private PlanetModel 			mPlanet;

	public PlanetBuildShipScreen (Screen parent, PlanetModel planet) {
		mParent = parent;
		mPlanet = planet;
//		mSpriteBatch = parent.getSpriteBatch();
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw (int gameTime, int screenTime) {
		drawRectangle(0, POS_Y, Constants.GAME_WIDTH, 100, new Color(0.2f, 0.2f, 0.2f, 0.85f));

		drawRectangle(0, POS_Y, Constants.GAME_WIDTH / 3, 14, new Color(1, 1, 1, 0.45f));
		drawString("Design", 4, POS_Y + 5);
		drawRectangle(Constants.GAME_WIDTH / 3, POS_Y, 1, 200, new Color(0, 0, 0, 0.45f));

		drawRectangle(Constants.GAME_WIDTH / 3 + 1, POS_Y, Constants.GAME_WIDTH / 3 - 1, 14, new Color(1, 1, 1, 0.45f));
		drawString("Spacedock", Constants.GAME_WIDTH / 3 + 5, POS_Y + 5);
		drawRectangle(Constants.GAME_WIDTH / 3 * 2, POS_Y, 1, 200, new Color(0, 0, 0, 0.45f));

		drawRectangle(Constants.GAME_WIDTH / 3 * 2 + 1, POS_Y, Constants.GAME_WIDTH / 3, 14, new Color(1, 1, 1, 0.45f));
		drawString("In orbit", Constants.GAME_WIDTH / 3 * 2 + 5, POS_Y + 5);
		
		// Draw ship desing
		List<ShipClassModel> classes = GameService.getInstance().getShipClasses();
		int i = 0;
		for (ShipClassModel sc: classes) {
			drawString(sc.getName(), 4, POS_Y + LIST_START_Y + i * LINE_INTERVAL);
			drawString(Utils.getFormatedTime(mPlanet.getBuildETA(sc.getBuildValue())), Constants.GAME_WIDTH / 3 - 34, POS_Y + LIST_START_Y + i * LINE_INTERVAL);
			i++;
		}

		// Draw ship on construction
		List<ShipModel> builds = mPlanet.getBuilds();
		int j = 0;
		int time = 0;
		for (ShipModel sc: builds) {
			time += sc.getBuildETA();
			drawString(sc.getClassName(), Constants.GAME_WIDTH / 3 + 5, POS_Y + LIST_START_Y + j * LINE_INTERVAL);
			drawString(Utils.getFormatedTime(time), Constants.GAME_WIDTH / 3 * 2 - 34, POS_Y + LIST_START_Y + j * LINE_INTERVAL);
			j++;
		}

		// Draw ship in orbit
		List<FleetModel> orbit = mPlanet.getOrbit();
		int k = 0;
		if (mPlanet.getDock() != null) {
			drawString(mPlanet.getDock().getName(), Constants.GAME_WIDTH / 3 * 2 + 5, POS_Y + LIST_START_Y);
			drawRectangle(Constants.GAME_WIDTH - 28, POS_Y + LIST_START_Y + 1, 22, 3, Color.GREEN);
			k = 1;
		}
		for (FleetModel sc: orbit) {
			drawString(sc.getName(), Constants.GAME_WIDTH / 3 * 2 + 5, POS_Y + LIST_START_Y + k * LINE_INTERVAL);
			drawRectangle(Constants.GAME_WIDTH - 28, POS_Y + LIST_START_Y + k * LINE_INTERVAL + 1, 22, 3, Color.GREEN);
			k++;
		}
	}

	@Override
	public void onTouch (int x, int y) {
		if (y > POS_Y + LIST_START_Y) {
			List<ShipClassModel> classes = GameService.getInstance().getShipClasses();
			int index = (y - POS_Y - LIST_START_Y + 3) / LINE_INTERVAL;
			if (x < Constants.GAME_WIDTH / 3 && index < classes.size()) {
				mPlanet.addBuild(new ShipModel(classes.get(index)));
			}
			if (x > Constants.GAME_WIDTH / 3 && x < Constants.GAME_WIDTH / 3 * 2) {
				System.out.println("remove build");
			}
		} else if (y < POS_Y) {
			back();
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onNext () {
		mParent.onNext();
		List<PlanetModel> planets = mPlanet.getSystem().getPlanets();
		int index = planets.indexOf(mPlanet);
		mPlanet = planets.get(Math.min(index + 1, planets.size() - 1));
	}

	@Override
	public void onPrev () {
		mParent.onPrev();
		List<PlanetModel> planets = mPlanet.getSystem().getPlanets();
		int index = planets.indexOf(mPlanet);
		mPlanet = planets.get(Math.max(index - 1, 0));
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}


}
