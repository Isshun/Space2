package org.bluebox.space2.ai;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.ai.expension.goal.ShipFilter;
import org.bluebox.space2.ai.planet.PlanetaryGovernor;
import org.bluebox.space2.ai.ship.ShipCaptain;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.ShipTemplateModel;
import org.bluebox.space2.game.service.GameService;

import com.badlogic.gdx.graphics.Color;

public class AIPlayerModel extends PlayerModel {
	private ArrayList<ShipCaptain> 			mCaptains;
	private ArrayList<PlanetaryGovernor> 	mGovernor;
	private GSAI 									mGSAI;

	public AIPlayerModel (String name, Color uiColor, Color color, boolean isAI) {
		super(name, uiColor, color, isAI);
		
		mCaptains = new ArrayList<ShipCaptain>();
		mGovernor = new ArrayList<PlanetaryGovernor>();
		mGSAI = new GSAI(this);
	}

	public List<ShipCaptain> getCaptains () {
		return mCaptains;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		mGSAI.onUpdate();
	}

	public GSAI getGSAI () {
		return mGSAI;
	}

	public ShipTemplateModel getShipTemplate (ShipFilter filter) {
		for (ShipTemplateModel template: GameService.getInstance().getData().shipTemplates) {
			if (filter.hasColonizer && template.hasDevice(Device.COLONIZER)) {
				return template;
			}
		}
		return null;
	}

	public ShipCaptain createCaptain (ShipModel ship) {
		ShipCaptain captain = new ShipCaptain(this, ship);
		mCaptains.add(captain);
		return captain;
	}

	public List<PlanetModel> getKnowedPlanet () {
		// TODO
		return GameService.getInstance().getData().planets;
	}
}
