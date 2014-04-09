package org.bluebox.space2.model;

public class BuildingModel {
	private BuildingClassModel mBuildClass;

	public BuildingModel(BuildingClassModel buildClass) {
		mBuildClass = buildClass;
	}
	
	public String getName() {
		return mBuildClass.getName();
	}
}
