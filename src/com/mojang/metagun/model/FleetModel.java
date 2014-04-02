package com.mojang.metagun.model;

import java.util.List;

public class FleetModel {
	private List<ShipModel>	mShips;
	private PlayerModel mOwner;

	public FleetModel (PlayerModel owner) {
		mOwner = owner;
	}
}
