package org.bluebox.space2.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StructureModel extends BuildingBaseModel {
	protected PlanetModel		mPlanet;
	private BuildingClassModel mBuildClass;

	public StructureModel(BuildingClassModel buildClass, PlanetModel planet) {
		super(buildClass.getBuildValue());
		
		mBuildClass = buildClass;
		mPlanet = planet;
	}
	
	public String getName() {
		return mBuildClass.getName();
	}

	public BuildingClassModel.Type getType () {
		return mBuildClass.type;
	}

	public TextureRegion getIcon() {
		return mBuildClass.getIcon();
	}
	
	public String getShortName () {
		return mBuildClass.getShortName();
	}

	public void addEffect (PlayerModel player, PlanetModel planet) {
		mBuildClass.addEffect(player, planet);
	}
}
