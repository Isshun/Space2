package com.mojang.metagun.service;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.DeviceModel;
import com.mojang.metagun.model.DeviceModel.Device;
import com.mojang.metagun.model.FleetModel;
import com.mojang.metagun.model.NameGenerator;
import com.mojang.metagun.model.PlanetClassModel;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.ShipClassModel;
import com.mojang.metagun.model.ShipModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.model.TravelModel;
import com.mojang.metagun.path.Vertex;

public class GameService {
	
	private static final int TRAVEL_MAX_DISTANCE = 150;

	private static final String sSystemNames[] = {"Archer", "Cerberus", "Proxima", "Vega", "Regula", "Tigen", "Idron", "Wolf", "Remus", "Deridia", "Bajor", "Drayan", "Enara", "Ledos", "Rakosa", "Arrakis", "Ocampa", "Telsium Prime", "Banea", "Hanon", "Arcadia", "Caitan", "Halia", "Trillius", "Ardana", "Benzar", "Risa", "Selay", "Antos", "Aaamazzara", "Acamar", "Akaali", "Andoria", "Antica", "Antos", "Ardana", "Argelius", "Arkaria", "Assigner", "Aurelia", "Bajor", "Ba'ku", "Banea", "Barzan", "Benzar", "Betazed", "Bolarus", "Breen", "Brekka", "Brunali", "Bynaus", "Capella", "Cardassia", "Chalna", "Cravic", "Delta", "Deneb", "Denobula", "Dosi", "Draylax", "Drema", "Ekos", "El-Aurian", "Elaysian", "Eminiar", "Excalbia", "Fabrina", "Ferenginar", "Garenor", "Gosis", "Hekaras", "Halkan", "Ilidaria", "Janus", "Kantare", "Karemma", "Kelemane", "Kelis", "Kelva", "Koinonian", "Kolarus", "Kurill", "Kraylor", "Kyrian-Vaskan", "Kzin", "Lissepia", "Loque'eque", "Luria", "Lyssarrian", "Makull", "Mari", "Melkotian", "Mintaka", "Miri", "Mokra", "Nausicaa", "Nechani", "Norcadia", "Ocampa", "Organia", "Orion", "Ornara", "Peliar Zel", "Pendari", "Pralor", "Qomar", "Rakhar", "Rakosa", "Ram Izad", "Remus", "Romulus", "Selay", "Sikaris", "Tagra", "Tak Tak", "Talax", "Tallonian", "Talos", "Tandar", "Tarella", "Tarquin", "Tellar", "Telsius", "Teplan", "T'Lan", "Torothan", "Triannon", "Trill", "Tyrellia", "Tzenketh", "Ullian", "Valakis", "Vaadwaur", "Vendikar", "Vhnori", "Vulcan", "Xindus", "Yadera", "Yridian", "Zahl", "Zakdorn", "Zalkon", "Zeon"};
	
	private static final int sMap[][][] = {
		{{276, 196},{407, 202},{311, 391},{244, 299},{320, 286},{476, 281},{487, 402},{355, 482},{438, 612},{601, 606},{554, 518},{684, 407},{597, 315},{639, 124},{489, 131},{596, 210},{273, 79},{109, 228},{134, 416},{279, 486},{158, 657},{397, 681},{798, 676},{620, 783},{297, 622},{892, 542},{944, 370},{757, 297},{740, 407},{842, 422},{743, 204},{916, 244}},
		{{284, 201},{168, 346},{513, 392},{607, 262},{295, 389},{445, 291},{700, 442},{532, 544},{378, 505},{381, 468},{156, 525},{371, 581},{775, 602},{512, 221},{854, 200},{726, 311},{631, 380},{852, 452}},
		{{356, 170},{836, 175},{791, 383},{508, 402},{663, 188},{649, 478},{308, 494},{204, 584},{397, 615},{477, 646},{494, 517},{347, 343},{557, 282},{672, 336},{809, 540},{606, 541},{505, 150},{280, 300},{929, 277},{648, 40},{952, 430},{200, 447}},
		{{420, 246},{493, 329},{400, 368},{329, 355},{285, 292},{365, 175},{634, 183},{666, 380},{468, 392},{404, 494},{324, 475},{232, 390},{183, 297},{220, 191},{440, 114},{619, 338},{571, 471},{856, 545},{691, 266},{781, 454},{647, 548},{527, 601},{554, 180}},
		{{265, 160},{681, 12},{612, 95},{510, 85},{589, 210},{483, 207},{391, 166},{328, 304},{465, 326},{528, 415},{653, 352},{761, 363},{620, 478},{413, 385},{279, 460},{208, 496},{146, 342},{462, 491},{533, 532},{341, 565},{693, 632},{684, 544},{512, 618},{862, 432},{750, 492},{810, 231},{700, 281},{736, 75},{838, 42},{920, 280},{705, 145},{877, 190}},
		{{642, 395},{735, 389},{758, 509},{607, 566},{473, 464},{561, 342},{588, 470},{762, 287},{925, 462},{894, 642},{731, 676},{476, 706},{707, 285},{451, 306},{511, 573},{268, 584},{372, 451},{402, 596},{832, 397},{496, 189},{893, 274},{348, 335},{183, 440}},
		{{580, 380},{620, 600},{715, 463},{430, 470},{621, 658},{653, 463},{464, 536},{309, 591},{387, 544},{399, 628},{337, 499},{396, 413},{703, 394},{807, 388},{678, 542},{675, 673},{649, 760},{499, 746},{577, 730},{424, 798},{465, 402},{487, 453},{342, 420}},
		{{699, 271},{531, 414},{673, 509},{616, 389},{460, 223},{393, 459},{527, 640},{890, 665},{712, 641},{864, 282},{582, 180},{907, 503},{209, 369},{382, 609}},
		{{258, 122},{187, 205},{247, 349},{329, 293},{499, 223},{706, 150},{827, 138},{942, 131},{991, 209},{1030, 275},{1051, 372},{1032, 461},{940, 570},{844, 643},{823, 589},{812, 476},{1104, 565},{890, 436},{955, 300},{988, 426},{697, 229},{572, 228},{528, 324},{424, 305},{315, 239},{191, 298},{663, 301},{786, 221},{728, 391}},
		{{331, 181},{296, 355},{571, 414},{632, 228},{957, 188},{981, 454},{804, 534},{463, 625},{772, 329},{479, 303},{737, 682},{465, 150},{790, 108},{1084, 325},{1009, 665},{383, 483},{180, 536},{201, 291}},
		{{406, 434},{406, 134},{366, 274},{597, 284},{442, 174},{462, 332},{333, 491},{696, 529},{591, 419},{842, 234},{584, 151},{816, 336},{959, 501},{685, 638},{320, 540},{221, 578},{359, 626},{265, 668},{834, 159},{985, 231},{985, 292},{1108, 241},{1045, 119},{712, 338}},
		{{565, 203},{434, 302},{714, 414},{685, 514},{574, 555},{623, 329},{728, 285},{430, 520},{932, 571},{562, 422},{147, 382},{753, 637},{930, 486},{207, 427},{246, 519},{132, 570},{349, 649}}
	};
	
	private static final int SYSTEMS_GEN_MIN_DISTANCE = 42;
	private static final double SYSTEM_GEN_RANGE_DISTANCE = 300;
	
	private static GameService sSelf;
	
	private List<SystemModel> mSystems;
	private List<PlanetModel> mPlanets;

	private List<ShipClassModel> mShipClasses;

	private ArrayList<PlayerModel> mPlayers;

	private PlayerModel mPlayer;

	private ArrayList<TravelModel> mTravelLines;

	public int mMapIndex;

	private List<FleetModel> mFleets;

	private GameService() {
		mSystems = new ArrayList<SystemModel>();
		mPlanets = new ArrayList<PlanetModel>();
		mPlayers = new ArrayList<PlayerModel>();
		mTravelLines = new ArrayList<TravelModel>();
		mShipClasses = new ArrayList<ShipClassModel>();
		mFleets = new ArrayList<FleetModel>();
	}
	
	public List<SystemModel> getSystems () {
		return mSystems;
	}

	public static GameService getInstance () {
		if (sSelf == null) {
			sSelf = new GameService();
		}
		return sSelf;
	}

	public void initDebug (int mapChange) {
		mSystems.clear();
		mPlanets.clear();
		mPlayers.clear();
		mShipClasses.clear();
		mTravelLines.clear();
		
		{
			ShipClassModel sc = new ShipClassModel("Fighter", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.setBuildValue(100);
			mShipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Defender", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.setBuildValue(200);
			mShipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Cruiser", 250);
			sc.setBuildValue(800);
			mShipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Explorer", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.setBuildValue(60);
			mShipClasses.add(sc);
		}
		
		mPlayer = new PlayerModel("me", Color.YELLOW);
		mPlayers.add(new PlayerModel("player-1", Color.RED));
		mPlayers.add(new PlayerModel("player-2", Color.BLUE));
		mPlayers.add(new PlayerModel("player-3", Color.PINK));
		mPlayers.add(mPlayer);
		
		// Create systems
		if (mapChange != 0) {
			mMapIndex += mapChange;
		} else {
			mMapIndex = 4;//(int)(Math.random() * sMap.length);
		}
		int map[][] = sMap[mMapIndex];
		for (int[] s : map) {
			addSystem(s[0], s[1]);
		}
		
		// Create travel lines
		for (SystemModel s: mSystems) {
			int r = (int)(Math.random() * 2);
			SystemModel s1 = null;
			SystemModel s2 = null;
			SystemModel s3 = null;
			int distance1 = Integer.MAX_VALUE;
			int distance2 = Integer.MAX_VALUE;
			int distance3 = Integer.MAX_VALUE;
			for (SystemModel sp: mSystems) {
				if (s != sp && s.getDistance(sp) < distance1) {
					distance1 = s.getDistance(sp);
					s1 = sp;
				}
			}
			for (SystemModel sp: mSystems) {
				if (s != sp && s.getDistance(sp) < distance2 && s.getDistance(sp) > distance1) {
					distance2 = s.getDistance(sp);
					s2 = sp;
				}
			}
			if (r == 1) {
				for (SystemModel sp: mSystems) {
					if (s != sp && s.getDistance(sp) < distance3 && s.getDistance(sp) > distance1 && s.getDistance(sp) > distance2) {
						distance3 = s.getDistance(sp);
						s3 = sp;
					}
				}
			}
			//System.out.println(s.getName() + ", link to: " + s1.getName() + ", " + s2.getName());
			mTravelLines.add(new TravelModel(s, s1));
			if (s2 != null) {
				mTravelLines.add(new TravelModel(s, s2));
			}
			if (s3 != null) {
				mTravelLines.add(new TravelModel(s, s3));
			}
		}
		
//		PathFinder finder = new AStarPathFinder(, 500, true, nodes);

		// Create home worlds
		int offset = mSystems.size() / mPlayers.size();
		int i = 0;
		for (PlayerModel player: mPlayers) {
			player.colonize(mSystems.get(i).getRicherPlanet());
			i += offset;
		}
		
		// Create fleets
		ShipClassModel sc = mShipClasses.get(0);
		for (PlayerModel player: mPlayers) {
			{
				FleetModel fleet = new FleetModel(NameGenerator.generate(NameGenerator.KLINGON, player.getfleets().size()));
				fleet.setLocation(player.getHome());
				//fleet.setName(player.equals(mPlayer) ? "Alpha" : player.getName());
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				player.addFleet(fleet);
				
				mFleets.add(fleet);
			}
			{
				FleetModel fleet = new FleetModel();
				fleet.setLocation(player.getHome());
				//fleet.setName(player.equals(mPlayer) ? "Beta" : player.getName());
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				player.addFleet(fleet);
				
				mFleets.add(fleet);
			}
			{
				FleetModel fleet = new FleetModel();
				fleet.setLocation(player.getHome());
				//fleet.setName(player.equals(mPlayer) ? "Omega" : player.getName());
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				player.addFleet(fleet);
				
				mFleets.add(fleet);
			}
		}
		
	}
//
//	private void addRandomSystem (int level, SystemModel parent) {
//		int startX = level == 0 ? 400 : parent.getX();
//		int startY = level == 0 ? 200 : parent.getY();
//		
//		for (int i = 0; i < 2; i++) {
//			int posX = startX + 60 + (int)(Math.random() * 400) - 200;
//			int posY = startY + 60 + (int)(Math.random() * 400) - 200;
//
//			// Check if system too close
//			boolean tooClose = false;
//			for (SystemModel s: mSystems) {
//				int length = (int)Math.sqrt(Math.pow(Math.abs(posX - s.getX()), 2) + Math.pow(Math.abs(posY - s.getY()), 2));
//				if (length < 100) {
//					tooClose = true;
//				}
//			}
//			
//			if (true) {
//				int nameIndex = Math.min(mSystems.size(), sSystemNames.length - 1);
//				String name = sSystemNames[nameIndex];
//				
//				if (name.equals("Arrakis")) {
//					
//				} else {
//					SystemModel system = new SystemModel(name, posX, posY);
//					addPlanet(system, new PlanetModel());
//					addPlanet(system, new PlanetModel());
//					addPlanet(system, new PlanetModel());
//					mSystems.add(system);
//					mPlayers.get(0).addSystem(system);
//				}
//				
//				if (parent != null) {
//					mTravelLines.add(new TravelModel(system, parent));
//				}
//				
//				if (level < 3) {
//					addRandomSystem(level + 1, system);
//				}
//			}
//		}
//	}

	public void addSystem(int posX, int posY) {
		int nameIndex = Math.min(mSystems.size(), sSystemNames.length - 1);
		String name = sSystemNames[nameIndex];
		
		if (name.equals("Arrakis")) {
			SystemModel system = new SystemModel(name, posX, posY);
			PlanetModel planet = new PlanetModel();
			planet.setClassification(PlanetClassModel.CLASS_L_DESERT);
			planet.setSize(3);
			addPlanet(system, planet);
			mSystems.add(system);
		} else {
			SystemModel system = new SystemModel(name, posX, posY);
			int length = (int)(Math.random() * 5 + 1);
			for (int i = 0; i < length; i++) {
				addPlanet(system, new PlanetModel());
			}
			mSystems.add(system);
		}
	}
	
	private void addPlanet (SystemModel system, PlanetModel p) {
		mPlanets.add(p);
		system.addPlanet(p);
	}

	public SystemModel getSystemAtPos (int x, int y) {
		for (SystemModel s: mSystems) {
			if (x >= s.getX() - Constants.TOUCH_MARGIN && x <= s.getX() + Constants.SYSTEM_SIZE + Constants.TOUCH_MARGIN && y >= s.getY() - Constants.TOUCH_MARGIN && y <= s.getY() + Constants.SYSTEM_SIZE + Constants.TOUCH_MARGIN ) {
				return s;
			}
		}
		return null;
	}

	public TravelModel getTravelAtPos (int x, int y) {
		for (TravelModel t: mTravelLines) {
			if (x >= t.getX() && x <= t.getX() + Constants.SYSTEM_SIZE && y >= t.getY() && y <= t.getY() + Constants.SYSTEM_SIZE) {
				return t;
			}
		}
		return null;
	}

	public List<PlanetModel> getPlanets () {
		return mPlanets;
	}

	public PlayerModel getPlayer () {
		return mPlayer;
	}

	public List<PlayerModel> getPlayers () {
		return mPlayers;
	}

	public List<TravelModel> getTraveLines () {
		return mTravelLines;
	}

	public void dump () {
//		System.out.println("Integer pos[][][] = {");
		System.out.print("{");
		int i = 0;
		for (SystemModel s: mSystems) {
			System.out.print("{"+s.getX()+", "+s.getY()+"}");
			if (i++ < mSystems.size()-1) {
				System.out.print(",");
			}
		}
		System.out.println("},");
	}

	public List<ShipClassModel> getShipClasses () {
		return mShipClasses;
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
		for (TravelModel t: mTravelLines) {
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
		return mFleets;
	}

}
