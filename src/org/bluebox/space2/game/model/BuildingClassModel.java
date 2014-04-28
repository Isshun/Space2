package org.bluebox.space2.game.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.service.FightService;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BuildingClassModel {
	public enum Type {
		DOCK,
		ECONOMIC_CENTER,
		HYDROPONICS,
		RANDOM_1,
		RANDOM_2,
		RANDOM_3,
		RANDOM_4,
		RANDOM_5,
		RANDOM_6,
		RANDOM_7,
		RANDOM_8,
		RANDOM_9,
		RANDOM_10,
		RANDOM_11,
		RANDOM_12,
		RANDOM_13,
		RANDOM_14,
		RANDOM_15,
		RANDOM_16,
		RANDOM_17,
		RANDOM_18,
		RANDOM_19,
		RANDOM_20,
		RANDOM_21,
		RANDOM_22,
		RANDOM_23,
		RANDOM_24,
		RANDOM_25,
		RANDOM_26,
		RANDOM_27,
		RANDOM_28,
		RANDOM_29,
		RANDOM_30,
		RANDOM_31,
		RANDOM_32,
		RANDOM_33,
		RANDOM_34,
		RANDOM_35,
		RANDOM_36,
		RANDOM_37,
		RANDOM_38,
		RANDOM_39, COLONIAL_HABITAT, TRANSPORTATION, TIDAL_POWER, OFFSHORE_TURBINE, WIND_TURBINE, RECHERCHE_CENTER, COMMUN_NETWORK,
	}
	
	private String 				mName;
	private String 				mEffect;
	private String 				mDesc;
	private String 				mShortName;
	private int						mBuildValue; 
	public Type 					type;
	public TextureRegion 		mIcon;
	private IBuildingCondition mBuildingCondition;
	private IBuildingEffect 	mBuildingEffect;

	public BuildingClassModel (String name, String shortName, String effect, int builValue, Type type, TextureRegion icon, String desc, IBuildingCondition buildingCondition, IBuildingEffect buildingEffect) {
		mBuildingEffect = buildingEffect;
		mBuildingCondition = buildingCondition;
		mName = name;
		mEffect = effect;
		mDesc = desc;
		mShortName = shortName;
		mIcon = icon;
		this.type = type;
		mBuildValue = builValue;
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

	public int getBuildValue () {
		return mBuildValue;
	}

	public void setIcon (TextureRegion icon) {
		mIcon = icon;
	}

	public TextureRegion getIcon () {
		return mIcon;
	}

	public Type getType () {
		return type;
	}

	public boolean isAvailable (PlayerModel player, PlanetModel planet) {
		if (mBuildingCondition != null) {
			return mBuildingCondition.isAvailable(player, planet, null) == 1;
		}
		return true;
	}

	public List<String> getRequires (PlayerModel player, PlanetModel planet) {
		List<String> requires = new ArrayList<String>();
		if (mBuildingCondition == null || mBuildingCondition.isAvailable(player, planet, requires) == 1) {
			return null;
		}
		return requires;
	}

	public void addEffect (PlayerModel player, PlanetModel planet) {
		if (mBuildingEffect != null) {
			mBuildingEffect.effect(player, planet);
		}
	}

}
