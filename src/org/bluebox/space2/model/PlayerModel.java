package org.bluebox.space2.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.Art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerModel {
	
	private String 				mName;
	private TextureRegion 		mFlag;
	private Color					mColor;
	private List<SystemModel> 	mSystems;
	private List<PlanetModel> 	mPlanets;
	private List<FleetModel> 	mFleets;
	private PlanetModel mHome;
	private Color mSpaceColor;
	
	public PlayerModel(String name, Color color) {
		mName = name;
		mFleets = new ArrayList<FleetModel>();
		mFlag = Art.flags[(int)(Math.random() * 9)];
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
		//mColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
		mColor = color;
		mSpaceColor = new Color(Math.min(color.r + 0.1f, 1), Math.min(color.g + 0.1f, 1), Math.min(color.b + 0.1f, 1), 0.65f);
	}

	public void addSystem (SystemModel system) {
		if (mHome == null) {
			mHome = system.getCapital();
		}
		
		mSystems.add(system);
		system.setOwner(this);
		for (PlanetModel planet: system.getPlanets()) {
			mPlanets.add(planet);
		}
	}

	public List<PlanetModel> getPlanets () {
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
		return mFlag;
	}

	public String getRelation () {
		return "war";
	}

	public List<FleetModel> getfleets () {
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
	
}
