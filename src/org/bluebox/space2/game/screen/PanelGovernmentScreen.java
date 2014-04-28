package org.bluebox.space2.game.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class PanelGovernmentScreen extends BaseScreen {

	private static final int LINE_HEIGHT = 15;
	private static final int START_Y = 19;
	private static final int START_X = 118;
	private static final int SPACING_X = 30;
	int mSortMode;
	
	public PanelGovernmentScreen() {
		mSortMode = -1;
	}
	
	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		mainLayer.draw(Art.bg, 0, 0);

		mainLayer.draw(Art.ic_people, START_X, 3);
		mainLayer.draw(Art.ic_satisfaction, START_X + SPACING_X * 1, 3);
		mainLayer.draw(Art.res_food, START_X + SPACING_X * 2, 3);
		mainLayer.draw(Art.ic_construction_12, 118 + SPACING_X * 3, 3);
		mainLayer.draw(Art.ic_money_12, START_X + SPACING_X * 4, 3);
		mainLayer.draw(Art.ic_science, START_X + SPACING_X * 5, 3);
		mainLayer.draw(Art.res_culture, START_X + SPACING_X * 6, 3);
		
		List<PlanetModel> planets = new ArrayList<PlanetModel>(GameService.getInstance().getPlayer().getPlanets());
		Collections.sort(planets, new Comparator<PlanetModel>() {
			@Override
			public int compare (PlanetModel p1, PlanetModel p2) {
				switch (mSortMode) {
				case -1: return p1.getName().compareTo(p2.getName());
				case 0: return p1.getPeople() > p2.getPeople() ? -1 : 1;
				case 1: return p1.getSatisfation() > p2.getSatisfation() ? -1 : 1;
				case 2: return p1.getFood() > p2.getFood() ? -1 : 1;
				case 3: return p1.getProd() > p2.getProd() ? -1 : 1;
				case 4: return p1.getMoney() > p2.getMoney() ? -1 : 1;
				case 5: return p1.getScience() > p2.getScience() ? -1 : 1;
				case 6: return p1.getCulture() > p2.getCulture() ? -1 : 1;
				}
				return 0;
			}
		});
		int i = 0;
		for (PlanetModel planet : planets) {
			if (planet.getPeople() > 0) {
				mainLayer.drawRectangle(4, START_Y + i * (LINE_HEIGHT + 1), Constants.GAME_WIDTH - 8, LINE_HEIGHT, i % 2 == 0 ? Color.rgba8888(0.85f, 0.85f, 1, 0.45f) : Color.rgba8888(0.85f, 0.85f, 1, 0.4f));
				mainLayer.draw(Art.planets[planet.getClassification().id][Art.PLANET_RES_12], 5, START_Y + 2 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(planet.getName(), 20, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf(planet.getPeople()), START_X + 2, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getSatisfation()), START_X + 2 + SPACING_X * 1, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getFood()), START_X + 2 + SPACING_X * 2, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getProd()), START_X + 2 + SPACING_X * 3, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getMoney()), START_X + 2 + SPACING_X * 4, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getScience()), START_X + 2 + SPACING_X * 5, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.drawString(String.valueOf((int)planet.getCulture()), START_X + 2 + SPACING_X * 6, START_Y + 5 + i * (LINE_HEIGHT + 1));
				mainLayer.draw(Art.bt_space_map, START_X + 2 + SPACING_X * 7, START_Y + 2 + i * (LINE_HEIGHT + 1));
				i++;
			}
		}
	}

	@Override
	public void onTouch (int x, int y) {
		Set<PlanetModel> planets = mPlayer.getPlanets();
		List<PlanetModel> colonized = new ArrayList<PlanetModel>();
		for (PlanetModel planet : planets) {
			if (planet.getPeople() > 0) {
				colonized.add(planet);
			}
		}
		if (y > START_Y) {
			int pos = (y - 22) / (LINE_HEIGHT + 1);
			if (colonized.size() > pos) {
				PlanetModel planet = colonized.get(pos);

				if (x > START_X + 2 + SPACING_X * 7 && x < START_X + 2 + SPACING_X * 7 + 32) {
					BaseScreen s = back();
					if (s instanceof SpaceScreen) {
						((SpaceScreen)s).gotoPos(planet.getX(), planet.getY());
					}
				} else {
					addScreen(new PlanetScreen(planet.getSystem(), planet));
				}
			}
		} else {
			if (x > START_X) {
				int pos = (x - START_X) / SPACING_X;
				mSortMode = pos;
			} else {
				mSortMode = -1;
			}
		}
	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
