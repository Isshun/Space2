package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.service.FightService;


public class BuildingClassModel {
	public enum Type {
		DOCK, ECONOMIC_CENTER, HYDROPONICS
	}
	
	private String 	mName;
	private String 	mEffect;
	private String 	mDesc;
	private String 	mShortName;
	public Type 		type;

	public BuildingClassModel (Type type) {
		this.type = type;
	}

	public String getName () {
		return mName;
	}

	public String getShortName () {
		return mShortName;
	}

	public String getDesc () {
		return mDesc;
	}

	public String getEffect () {
		return mEffect;
	}

	public void setName (String name) {
		mName = name;
	}

	public void setShortName (String shortName) {
		mShortName = shortName;
	}

	public void setEffect (String effect) {
		mEffect = effect;
	}

	public void setDesc (String desc) {
		mDesc = desc;
	}

	public static BuildingClassModel create (Type type) {
		return new BuildingClassModel(type);
	}

}
