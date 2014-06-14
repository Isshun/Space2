package org.bluebox.space2.game.model;

public class BuildingBaseModel {
	private int 	mTotalBuild;
	private double mBuild;

	public BuildingBaseModel (int buildValue) {
		mTotalBuild = buildValue;
	}

	public int 		getBuildRemain () {
		return (int)(mTotalBuild - mBuild);
	}

	/**
	 * Build object and return true if complete
	 * 
	 * @return: true if complete
	 */
	public boolean build (double value) {
		mBuild = (int)Math.min(mBuild + value, mTotalBuild);
		return mBuild == mTotalBuild;
	}

	public boolean isComplete () {
		return mBuild >= mTotalBuild;
	}

}
