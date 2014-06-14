//package org.bluebox.space2.game.ai;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.bluebox.space2.game.Game;
//import org.bluebox.space2.game.model.FleetModel;
//import org.bluebox.space2.game.model.PlanetModel;
//import org.bluebox.space2.game.model.PlayerModel;
//import org.bluebox.space2.game.model.ShipClassModel;
//import org.bluebox.space2.game.model.ShipModel;
//import org.bluebox.space2.game.model.SystemModel;
//import org.bluebox.space2.game.model.TravelModel;
//import org.bluebox.space2.game.model.DeviceModel.Device;
//import org.bluebox.space2.game.model.FleetModel.Action;
//import org.bluebox.space2.game.service.GameService;
//
//public class AI {
//
//	private static AI sSelf;
//
//	public static AI getInstance () {
//		if (sSelf == null) {
//			sSelf = new AI();
//		}
//		return sSelf;
//	}
//
//	public void play (PlayerModel player) {
//		AIColonize.play(player);
//		AIBuildShip.play(player);
//		AIBuildStructure.play(player);
//	}
//
//}
