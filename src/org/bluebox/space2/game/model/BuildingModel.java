package org.bluebox.space2.game.model;

public class BuildingModel {
	protected PlanetModel	mPlanet;
	private BuildingClassModel mBuildClass;

	public BuildingModel(BuildingClassModel buildClass, PlanetModel planet) {
		mBuildClass = buildClass;
		mPlanet = planet;
	}
	
	public String getName() {
		return mBuildClass.getName();
	}

	public BuildingClassModel.Type getType () {
		return mBuildClass.type;
	}
}
