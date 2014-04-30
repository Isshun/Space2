package org.bluebox.space2.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StructureModel implements IBuilding {
	protected PlanetModel		mPlanet;
	private BuildingClassModel mBuildClass;
	private int 					mTotalBuild;
	private double					mBuild;

	public StructureModel(BuildingClassModel buildClass, PlanetModel planet) {
		mBuildClass = buildClass;
		mPlanet = planet;
		mTotalBuild = buildClass.getBuildValue();
	}
	
	public String getName() {
		return mBuildClass.getName();
	}

	public BuildingClassModel.Type getType () {
		return mBuildClass.type;
	}
	
	public boolean build (double value) {
		mBuild = (int)Math.min(mBuild + value, mTotalBuild);
		return mBuild == mTotalBuild;
	}

	public TextureRegion getIcon() {
		return mBuildClass.getIcon();
	}
	
	public int 				getBuildRemain () { return (int)(mTotalBuild - mBuild); }

	public String getShortName () {
		return mBuildClass.getShortName();
	}

	public void addEffect (PlayerModel player, PlanetModel planet) {
		mBuildClass.addEffect(player, planet);
	}
}
