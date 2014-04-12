package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.game.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerModel {
	
	private String 				mName;
	private int 					mFlag;
	private Color					mColor;
	private List<SystemModel> 	mSystems;
	private Set<PlanetModel> 	mPlanets;
	private List<FleetModel> 	mFleets;
	private PlanetModel 			mHome;
	private Color 					mSpaceColor;
	private Color 					mDarkColor;
	private Color 					mUIColor;
	private boolean 				mIsAI;
	public AIOrders				aiOrders;
	
	public PlayerModel(String name, Color uiColor, Color color, boolean isAI) {
		mName = name;
		mFleets = new ArrayList<FleetModel>();
		mFlag = (int)(Game.sRandom.nextInt(9));
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new HashSet<PlanetModel>();
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

	public List<FleetModel> getFleets () {
		return mFleets;
	}

	public void addFleet (FleetModel fleet) {
		fleet.setOwner(this);
		mFleets.add(fleet);
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
			return RelationModel.RELATION_ME;
		}
		return RelationModel.RELATION_WAR;
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

}
