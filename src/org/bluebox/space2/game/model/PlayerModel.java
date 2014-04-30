package org.bluebox.space2.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerModel {
	private static int 				sCount;

	private String 					mName;
	private int 						mId;
	private int 						mFlag;
	private Color						mColor;
	private List<SystemModel>	 	mSystems;
	private Set<PlanetModel>	 	mPlanets;
	private Set<FleetModel> 		mFleets;
	private PlanetModel 				mHome;
	private Color 						mSpaceColor;
	private Color 						mDarkColor;
	private Color 						mUIColor;
	private boolean 					mIsAI;
	public AIOrders					aiOrders;
	private int 						mNameGeneratorLanguage;
	private Set<TechnologyModel>	mTechs;
	
	public PlayerModel(String name, Color uiColor, Color color, boolean isAI) {
		mId = sCount++;
		mName = name;
		mFleets = new HashSet<FleetModel>();
		mFlag = (int)(Game.sRandom.nextInt(9));
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new HashSet<PlanetModel>();
		mTechs = new HashSet<TechnologyModel>();
		mIsAI = isAI;
		if (isAI) {
			aiOrders = new AIOrders();
		}
		//mColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
		mColor = color;
		mUIColor = uiColor;
		mSpaceColor = new Color(Math.min(color.r + 0.1f, 1), Math.min(color.g + 0.1f, 1), Math.min(color.b + 0.1f, 1), 0.65f);
		mDarkColor = new Color(Math.max(color.r - 0.3f, 0), Math.max(color.g - 0.3f, 0), Math.max(color.b - 0.3f, 0), 1);
	}

	public void addSystem (SystemModel system) {
		if (mHome == null) {
			mHome = system.getCapital();
		}
		
		mSystems.add(system);
		system.setOwner(this);
	}

	public Set<PlanetModel> getPlanets () {
		return mPlanets;
	}
	
	public List<SystemModel> getSystems () {
		return mSystems;
	}

	public Color getColor() {
		return mColor;
	}

	public String getName () {
		return mName;
	}

	public TextureRegion getFlag () {
		return Art.flags[mFlag];
	}

	public String getRelation () {
		return "war";
	}

	public Set<FleetModel> getFleets () {
		return mFleets;
	}

	public void addFleet (FleetModel fleet) {
		fleet.setOwner(this);
		mFleets.add(fleet);
	}
	
	public void removeFleet (FleetModel fleet) {
		mFleets.remove(fleet);
	}

	public PlanetModel getHome () {
		return mHome;
	}

	public void colonize (PlanetModel planet) {
		if (planet.isFree() && (planet.getSystem().isFree() || planet.getSystem().getOwner() == this)) {
			System.out.println("Colonize: " + planet.getName());
			
			planet.setOwner(this);
			planet.setPeople(1);
			mPlanets.add(planet);
			
			// Set capital
			if (planet.getSystem().getOwner() == null) {
				planet.getSystem().setOwner(this);
				planet.getSystem().setCapital(planet);
			}

			
			if (!mSystems.contains(planet.getSystem())) {
				addSystem(planet.getSystem());
			}
		}
	}

	public int getRelation (PlayerModel otherPlayer) {
		if (otherPlayer.equals(this)) {
			return PlayerRelationModel.RELATION_ME;
		}
		return PlayerRelationModel.RELATION_WAR;
	}

	public void update () {
		List<FleetModel> fleets = new ArrayList<FleetModel>(mFleets);
		for (FleetModel fleet: fleets) {
			fleet.move();
		}
	}

	public Color getSpaceColor () {
		return mSpaceColor;
	}

	public void addPlanet (PlanetModel planetModel) {
		mPlanets.add(planetModel);
	}

	public Color getDarkColor () {
		return mDarkColor;
	}
	
	public Color getUIColor () {
		return mUIColor;
	}

	public boolean isAI () {
		return mIsAI;
	}

	public int getNameGeneratorLanguage () {
		return mNameGeneratorLanguage;
	}

	public void setNameGeneratorLanguage (int language) {
		mNameGeneratorLanguage = language;
	}

	public boolean hasTech (TechnologyModel.Type tech) {
		return mTechs.contains(tech);
	}

	public int getId () {
		return mId;
	}

	public void setId (int id) { mId = id; }
	public void setHome (PlanetModel home) { mHome = home; }
	public void setColor (Color color) { mColor = color; }
	public void setSpaceColor (Color color) { mSpaceColor = color; }
	public void setName (String name) {mName = name; }
	public void setAI (int i) { mIsAI = i != 1; }
}
