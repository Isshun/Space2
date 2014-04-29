package org.bluebox.space2.game.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.game.Game;
import org.bluebox.space2.game.GameData;
import org.bluebox.space2.game.model.DeviceModel.Device;
import org.bluebox.space2.game.service.FightService;

import com.badlogic.gdx.Gdx;

public class SystemModel implements ILocation {
	private static final String sSystemNames[] = {"Archer", "Cerberus", "Proxima", "Vega", "Regula", "Tigen", "Idron", "Wolf", "Remus", "Deridia", "Bajor", "Drayan", "Enara", "Ledos", "Rakosa", "Arrakis", "Ocampa", "Telsium Prime", "Banea", "Hanon", "Arcadia", "Caitan", "Halia", "Trillius", "Ardana", "Benzar", "Risa", "Selay", "Antos", "Aaamazzara", "Acamar", "Akaali", "Andoria", "Antica", "Antos", "Ardana", "Argelius", "Arkaria", "Assigner", "Aurelia", "Bajor", "Ba'ku", "Banea", "Barzan", "Benzar", "Betazed", "Bolarus", "Breen", "Brekka", "Brunali", "Bynaus", "Capella", "Cardassia", "Chalna", "Cravic", "Delta", "Deneb", "Denobula", "Dosi", "Draylax", "Drema", "Ekos", "El-Aurian", "Elaysian", "Eminiar", "Excalbia", "Fabrina", "Ferenginar", "Garenor", "Gosis", "Hekaras", "Halkan", "Ilidaria", "Janus", "Kantare", "Karemma", "Kelemane", "Kelis", "Kelva", "Koinonian", "Kolarus", "Kurill", "Kraylor", "Kyrian-Vaskan", "Kzin", "Lissepia", "Loque'eque", "Luria", "Lyssarrian", "Makull", "Mari", "Melkotian", "Mintaka", "Miri", "Mokra", "Nausicaa", "Nechani", "Norcadia", "Ocampa", "Organia", "Orion", "Ornara", "Peliar Zel", "Pendari", "Pralor", "Qomar", "Rakhar", "Rakosa", "Ram Izad", "Remus", "Romulus", "Selay", "Sikaris", "Tagra", "Tak Tak", "Talax", "Tallonian", "Talos", "Tandar", "Tarella", "Tarquin", "Tellar", "Telsius", "Teplan", "T'Lan", "Torothan", "Triannon", "Trill", "Tyrellia", "Tzenketh", "Ullian", "Valakis", "Vaadwaur", "Vendikar", "Vhnori", "Vulcan", "Xindus", "Yadera", "Yridian", "Zahl", "Zakdorn", "Zalkon", "Zeon"};
	private static final String CLASS_NAME = "SystemModel";

	private static int 			sCount;
	private String 				mName;
	private int 					mPosX;
	private int 					mPosY;
	private List<PlanetModel> 	mPlanets;
	private PlayerModel 			mOwner;
	private int 					mType;
	private PlanetModel 			mCapital;
	private List<FleetModel> 	mFleets;
	private List<SystemModel> 	mNeighbors;
	private int 					mId;

	public SystemModel (String name, int x, int y) {
		mId = sCount++;
		mPlanets = new ArrayList<PlanetModel>();
		mFleets = new ArrayList<FleetModel>();
		mName = name;
		mPosX = x;
		mPosY = y;
		mType = Math.min((int)(Game.sRandom.nextInt(7)), 3);
		mNeighbors = new ArrayList<SystemModel>();
	}

	public int getX () {
		return mPosX;
	}

	public int getY () {
		return mPosY;
	}

	public String getName () {
		return mName;
	}

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}

	public void addPlanet (PlanetModel planet) {
		planet.setSystem(this, mPlanets.size());
		mPlanets.add(planet);
		mCapital = getRicherPlanet();
	}

//	public void colonize (PlayerModel player, PlanetModel planet) {
//		if (mCapital == null) {
//			mCapital = planet;
//		}
//		planet.setOwner(player);
//		mOwner = player;
//	}

	public PlayerModel getOwner() {
		return mOwner;
	}
	
	public void setOwner (PlayerModel owner) {
		if (mOwner != owner) {
			mOwner = owner;
			owner.addSystem(this);
		}
	}

	public int getDistance (SystemModel s) {
		return (int)Math.sqrt(Math.pow(Math.abs(mPosX - s.getX()), 2) + Math.pow(Math.abs(mPosY - s.getY()), 2));
	}

	public int getType () {
		return mType;
	}

	public PlanetModel getCapital () {
		return mCapital;
	}

	public PlanetModel getRicherPlanet () {
		PlanetModel richerPlanet = null;
		int richer = -1;
		for (PlanetModel planet: mPlanets) {
			if (planet.getIndice() > richer) {
				richerPlanet = planet;
			}
		}
		return richerPlanet;
	}

	public boolean isFree () {
		return mOwner == null;
	}

	@Override
	public void removeFleet (FleetModel fleet) {
		mFleets.remove(fleet);
	}

	@Override
	public void addFleet (FleetModel fleet) {
		mFleets.add(fleet);
		fleet.setLocation(this);
	}
	
	public int attack(FleetModel fleet) {
		boolean attackerWin = false;
		for (PlanetModel planet: mPlanets) {
			if (planet.attack(fleet) == FightService.DEFENDER_WIN) {
				System.out.println("system winner: defender");
				return FightService.DEFENDER_WIN;
			}
		}
		System.out.println("system winner: attacker");
		return FightService.ATTACKER_WIN;
	}

	public void moveTo (FleetModel fleet) {
		if (hasHostileForce(fleet.getOwner())) {
			int winner = attack(fleet);
			if (winner == FightService.ATTACKER_WIN) {
				fleet.setLocation(this);
			}
		} else {
			fleet.setLocation(this);
		}
	}

	private boolean hasHostileForce (PlayerModel player) {
		for (PlanetModel planet: mPlanets) {
			for (FleetModel fleet: planet.getOrbit()) {
				if (fleet.getOwner().getRelation(player) == RelationModel.RELATION_WAR) {
					return true;
				}
			}
		}
		return false;
	}

	public void setCapital (PlanetModel capital) {
		mCapital = capital;
	}

	public List<FleetModel> getFleets () {
		return mFleets;
	}

	public int getId () {
		return mId;
	}

	public void colonize (PlayerModel player) {
		System.out.println("Colonize: " + mName);
		if (getCapital() == null) {
			throw new GameException("cannot colonize system with no capital");
		}
		
		ShipModel colonizer = getColonizer(player);
		if (colonizer == null) {
			throw new GameException("cannot colonize system without colonizer");
		}
		
		colonizer.getFleet().removeShip(colonizer);
		
		getCapital().setOwner(player);
		if (mOwner != player) {
			mOwner = player;
			player.addSystem(this);
		}
	}

	private ShipModel getColonizer (PlayerModel player) {
		for (FleetModel fleet: mFleets) {
			if (fleet.getOwner().equals(player)) {
				for (ShipModel ship: fleet.getShips()) {
					if (ship.hasDevice(Device.COLONIZER)) {
						return ship;
					}
				}
			}
		}
		return null;
	}

	public static SystemModel create (int posX, int posY) {
		int nameIndex = Math.min(sCount, sSystemNames.length - 1);
		String name = sSystemNames[nameIndex];

		SystemModel system = new SystemModel(name, posX, posY);
		
		if (name.equals("Arrakis")) {
			PlanetModel planet = new PlanetModel(PlanetClassModel.CLASS_L_DESERT, 3);
//			planet.setClassification();
//			planet.setSize(3);
			system.addPlanet(planet);
		} else {
			int length = (int)(Math.random() * 5 + 1);
			for (int i = 0; i < length; i++) {
				system.addPlanet(PlanetModel.create(i));
			}
		}
		
		system.setCapital(system.getRicherPlanet());
		
		return system;
	}

	public int getIndice () {
		int indice = 0;
		for (PlanetModel p: mPlanets) {
			indice += p.getIndice();
		}
		return indice;
	}

	public void addNeighbor (SystemModel neighbor) {
		mNeighbors.add(neighbor);
	}

	public List<SystemModel> getNeighbors () {
		return mNeighbors;
	}

	public void save (BufferedWriter bw) {

		try {
			bw.write("BEGIN SYSTEM\n");

			bw.write("\tID=" + mId + "\n");
			bw.write("\tNAME=" + mName + "\n");
			bw.write("\tPOS_X=" + mPosX + "\n");
			bw.write("\tPOS_Y=" + mPosY + "\n");
			
			if (mCapital != null) {
			bw.write("\tCAPITAL=" + mCapital.getId() + "\n");
			}

			if (mOwner != null) {
				bw.write("\tOWNER=" + mOwner.getId() + "\n");
			}
	
			bw.write("\tBEGIN PLANETS\n");
			for (PlanetModel planet: mPlanets) {
				planet.save(bw);
			}
			bw.write("\tEND PLANETS\n");
			
			bw.write("END SYSTEM\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static SystemModel load (GameData mData, BufferedReader br, PlayerModel owner) {
		SystemModel system = new SystemModel(null, 0, 0);
		int capitalId = -1;
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				if ("END SYSTEM".equals(line)) {
					if (capitalId != -1) {
						system.mCapital = mData.getPlanetFromId(capitalId);
					}
					return system;
				}
				
				if (line.indexOf("ID") == 0) { system.mId = Integer.valueOf(line.substring(3)); }
				if (line.indexOf("CAPITAL") == 0) { capitalId = Integer.valueOf(line.substring(8)); }
				if (line.indexOf("NAME") == 0) { system.mName = line.substring(5); }
				if (line.indexOf("OWNER") == 0) { system.mOwner = owner; }
				if (line.indexOf("POS_X") == 0) { system.mPosX = Integer.valueOf(line.substring(6)); }
				if (line.indexOf("POS_Y") == 0) { system.mPosY = Integer.valueOf(line.substring(6)); }
				
				if ("BEGIN PLANET".equals(line)) {
					PlanetModel planet = PlanetModel.load(mData, br, owner);
					planet.setSystem(system, system.mPlanets.size());
					system.mPlanets.add(planet);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;
	}
	
}
