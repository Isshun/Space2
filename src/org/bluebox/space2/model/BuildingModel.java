package org.bluebox.space2.model;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.service.FightService;


public class BuildingModel {
	private String 				mName;
	private String 				mEffect;
	private String 				mDesc;
	private String mShortName;

	public BuildingModel () {
		mName = "Test building model";
		mShortName = "Centre eco.";
		mEffect = "$+20% ^-20% &-20% *+20% @+20%";
		mDesc = "L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.";
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

}
