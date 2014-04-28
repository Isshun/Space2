package org.bluebox.space2.game.service;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.BuildingModel;
import org.bluebox.space2.game.model.DeviceModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.NameGenerator;
import org.bluebox.space2.game.model.PlanetClassModel;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.ShipClassModel;
import org.bluebox.space2.game.model.ShipModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.model.BuildingClassModel.Type;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.path.Vertex;

import com.badlogic.gdx.graphics.Color;

public class GameService {
	
	private static final int TRAVEL_MAX_DISTANCE = 150;
	
	private static final int SYSTEMS_GEN_MIN_DISTANCE = 42;
	private static final double SYSTEM_GEN_RANGE_DISTANCE = 300;
	
	private static GameService sSelf;
	private GameData 				mData;
	
	public List<SystemModel> getSystems () {
		return mData.systems;
	}

	public static GameService getInstance () {
		if (sSelf == null) {
			sSelf = new GameService();
		}
		return sSelf;
	}

	public void initDebug (int mapChange) {
//		for (SystemModel system: mSystems) {
//			system.setOwner(mPlayers.get((int)(Math.random() * 3)));
//		}
//		
		
		GameService.getInstance().getPlayer().getHome().addStructure(BuildingClassModel.Type.DOCK);
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
		GameService.getInstance().getPlayer().getHome().getDock().addShip(new ShipModel(GameService.getInstance().getShipClasses().get(0)));
	}

	private void addPlanet (SystemModel system, PlanetModel p) {
		mData.planets.add(p);
		system.addPlanet(p);
	}

	public SystemModel getSystemAtPos (int x, int y) {
		for (SystemModel s: mData.systems) {
			if (x >= s.getX() - Constants.TOUCH_MARGIN && x <= s.getX() + Constants.SYSTEM_SIZE + Constants.TOUCH_MARGIN && y >= s.getY() - Constants.TOUCH_MARGIN && y <= s.getY() + Constants.SYSTEM_SIZE + Constants.TOUCH_MARGIN ) {
				return s;
			}
		}
		return null;
	}

	public TravelModel getTravelAtPos (int x, int y) {
		for (TravelModel t: mData.travelLines) {
			if (x >= t.getX() && x <= t.getX() + Constants.SYSTEM_SIZE && y >= t.getY() && y <= t.getY() + Constants.SYSTEM_SIZE) {
				return t;
			}
		}
		return null;
	}

	public List<PlanetModel> getPlanets () {
		return mData.planets;
	}

	public PlayerModel getPlayer () {
		return mData.player;
	}

	public List<PlayerModel> getPlayers () {
		return mData.players;
	}

	public List<TravelModel> getTraveLines () {
		return mData.travelLines;
	}

	public void dump () {
//		System.out.println("Integer pos[][][] = {");
		System.out.print("{");
		int i = 0;
		for (SystemModel s: mData.systems) {
			System.out.print("{"+s.getX()+", "+s.getY()+"}");
			if (i++ < mData.systems.size()-1) {
				System.out.print(",");
			}
		}
		System.out.println("},");
	}

	public List<ShipClassModel> getShipClasses () {
		return mData.shipClasses;
	}

	public List<TravelModel> getTravelPath (List<Vertex> path) {
		List<TravelModel> travels = new ArrayList<TravelModel>();
		
		SystemModel s1 = null;
		for (Vertex v: path) {
			if (s1 == null) {
				s1 = v.getSystem();
			} else {
				travels.add(getTravel(s1, v.getSystem()));
				s1 = v.getSystem();
			}
		}
		
		return travels;
	}

	private TravelModel getTravel (SystemModel s1, SystemModel s2) {
		for (TravelModel t: mData.travelLines) {
			if (t.getFrom().equals(s1) && t.getTo().equals(s2)) {
				return t;
			}
			if (t.getTo().equals(s1) && t.getFrom().equals(s2)) {
				return t;
			}
		}
		return null;
	}

	public List<FleetModel> getFleets () {
		return mData.fleets;
	}

	public void setData (GameData data) {
		mData = data;
	}

	public GameData getData () {
		return mData;
	}

	public List<BuildingClassModel> getBuildingClasses () {
		return mData.buildingClasses;
	}

	public BuildingClassModel getBuildingClass (Type type) {
		for (BuildingClassModel buildingClass: mData.buildingClasses) {
			if (buildingClass.type == type) {
				return buildingClass;
			}
		}
		return null;
	}

	public BuildingModel createBuilding (Type type, PlanetModel planet) {
		for (BuildingClassModel buildingClass: mData.buildingClasses) {
			if (buildingClass.type == type) {
				return new BuildingModel(buildingClass, planet);
			}
		}
		return null;
	}

}
