package org.bluebox.space2.game;

import java.util.List;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.game.model.BuildingClassModel;
import org.bluebox.space2.game.model.DeviceModel;
import org.bluebox.space2.game.model.FleetModel;
import org.bluebox.space2.game.model.IBuildingCondition;
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
import org.bluebox.space2.game.model.TechnologyModel;
import org.bluebox.space2.game.model.IBuildingEffect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameDataFactory {

	private static final BuildingClassModel sBuildingClasses[] = {

		new BuildingClassModel("Fermes",								"Fermes", 			"^+20%", 				42, Type.HYDROPONICS, 		Art.buildings[0], "L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.",
			new IBuildingCondition() {
				@Override
				public int isAvailable (PlayerModel player, PlanetModel planet, List<String> requires) {
					switch (planet.getClassification().id) {
					case PlanetClassModel.CLASS_L_JUNGLE: return 1;
					case PlanetClassModel.CLASS_L: return 1;
					case PlanetClassModel.CLASS_P: return 1;
					case PlanetClassModel.CLASS_K_LOW_OXYGEN: return 1;
					case PlanetClassModel.CLASS_M: return 1;
					}
	
					requires.add(Strings.REQUIRES_FARMING_LANDS);
	
					switch (planet.getClassification().id) {
					case PlanetClassModel.CLASS_K: return 0;
					}
	
					return -1;
				}
			},
			new IBuildingEffect() {
				@Override
				public void effect (PlayerModel player, PlanetModel planet) {
					planet.addFoodModifier(0.2);
				}
			}
		),

		new BuildingClassModel("Fermes Hydroponiques",				"Hydro.", 			"^+20%", 				42, Type.HYDROPONICS, 		Art.buildings[0], "L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.",
			new IBuildingCondition() {
				@Override
				public int isAvailable (PlayerModel player, PlanetModel planet, List<String> requires) {
					if (player.hasTech(TechnologyModel.Type.HYDROPONICS)) {
						return 1;
					}
					requires.add(Strings.REQUIRES_HYDROPONICS_TECH);
					return 0;
				}
			},
			new IBuildingEffect() {
				@Override
				public void effect (PlayerModel player, PlanetModel planet) {
					planet.addFoodModifier(0.2);
				}
			}
		),
		
		new BuildingClassModel("Centre économique", 				"Eco.", 				"$+20% @+5% pop+5", 	42, Type.ECONOMIC_CENTER, 	Art.buildings[1], "", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
				planet.addMoneyModifier(0.2);
				planet.addCultureModifier(0.05);
			}
		}),
		
		new BuildingClassModel("Dock spatial", 					"Sp. Dock", 		"*+20%",					42, Type.DOCK, 					Art.buildings[2], "Dock for spaceships located in low orbit, they provide facilities to build and repair spacecraft.", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
				planet.addProdModifier(0.2);
			}
		}),
		
		new BuildingClassModel("Habitats coloniaux", 			"C-Hab.", 			"pop+50", 				42, Type.COLONIAL_HABITAT, 		Art.buildings[3], "", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
			}
		}),
		
		new BuildingClassModel("Résaux de transport",			"Trans.", 			"*+20% $+20% @+20% &+20%", 						42, Type.TRANSPORTATION, 		Art.buildings[4], "", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
				planet.addProdModifier(0.2);
				planet.addScienceModifier(0.2);
				planet.addCultureModifier(0.2);
				planet.addMoneyModifier(0.2);
			}
		}),
		
		new BuildingClassModel("Centrales marémotrice",			"Centrales maré.","*+20% ~+5%",	 					42, Type.TIDAL_POWER, 		Art.buildings[5], "",
			new IBuildingCondition() {
				@Override
				public int isAvailable (PlayerModel player, PlanetModel planet, List<String> requires) {
					switch (planet.getClassification().id) {
					case PlanetClassModel.CLASS_K_OCEAN: return 1;
					case PlanetClassModel.CLASS_M: return 1;
					}
					requires.add(Strings.REQUIRES_OCEAN);
					return -1;
				}
			},
			new IBuildingEffect() {
				@Override
				public void effect (PlayerModel player, PlanetModel planet) {
					planet.addProdModifier(0.2);
					planet.addHapinessModifier(0.05);
				}
			}
		),
		
		new BuildingClassModel("Eoliennes océanique", 			"Eol. Océanique","*+20% ~+5%", 						42, Type.OFFSHORE_TURBINE, 		Art.buildings[6], "",
			new IBuildingCondition() {
				@Override
				public int isAvailable (PlayerModel player, PlanetModel planet, List<String> requires) {
					switch (planet.getClassification().id) {
					case PlanetClassModel.CLASS_K_OCEAN: return 1;
					case PlanetClassModel.CLASS_M: return 1;
					}
					requires.add(Strings.REQUIRES_OCEAN);
					return -1;
				}
			},
			new IBuildingEffect() {
				@Override
				public void effect (PlayerModel player, PlanetModel planet) {
					planet.addProdModifier(0.2);
					planet.addHapinessModifier(0.05);
				}
			}
		),

		new BuildingClassModel("Eoliennes", 						"Eol.", 				"*+20%", 						42, Type.WIND_TURBINE, 		Art.buildings[7], "", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
				planet.addProdModifier(0.2);
			}
		}),
		
		new BuildingClassModel("Centre de recherche", 			"Rech.", 			"&+20% *+10%", 				42, Type.RECHERCHE_CENTER, 		Art.buildings[8], "", null,
			new IBuildingEffect() {
			@Override
			public void effect (PlayerModel player, PlanetModel planet) {
				planet.addProdModifier(0.1);
				planet.addScienceModifier(0.2);
			}
		}),

		new BuildingClassModel("Connexion au réseau commun",	"Reseaux commun",	"~+50%", 						42, Type.COMMUN_NETWORK, 		Art.buildings[9], "",
			new IBuildingCondition() {
				@Override
				public int isAvailable (PlayerModel player, PlanetModel planet, List<String> requires) {
					int ret = 0;
					
					List<SystemModel> neighbors = planet.getSystem().getNeighbors();
					for (SystemModel neighbor: neighbors) {
						if (player.equals(neighbor.getOwner())) {
							ret = 1;
						}
					}
					if (ret == 0) {
						requires.add(Strings.REQUIRES_TRAVEL_LINE_TO_EMPIRE);
					}
	
					if (player.hasTech(TechnologyModel.Type.COMMUN_NETWORK) == false) {
						requires.add(Strings.REQUIRES_COMMUN_NETWORK_TECH);
						ret = 0;
					}
	
					return 0;
				}
			},
			new IBuildingEffect() {
				@Override
				public void effect (PlayerModel player, PlanetModel planet) {
					planet.addProdModifier(0.2);
					planet.addHapinessModifier(0.5);
				}
			}
		),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null),
		new BuildingClassModel("", "", "", 42, Type.HYDROPONICS, Art.buildings[0], "", null, null)
	};

	private static final int sSystemMap[][][] = {
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

	public static GameData create () {
		GameData data = new GameData();

		initGame(data);
		
		return data;
	}

	public static void initGame (GameData data) {
		initBuildingClasses(data);
		initShipClasses(data);
		initPlayers(data);
		initSystems(data);
		initTravelLines(data);
		initHomeWorlds(data);
		initFleets(data);
	}

	public static void initBuildingClasses (GameData data) {
		for (BuildingClassModel buildingClass: sBuildingClasses) {
			data.buildingClasses.add(buildingClass);
		}
		
//		{
//			BuildingClassModel b = new BuildingClassModel(Type.HYDROPONICS);
//			b.setName("Hydroponic farms");
//			b.setShortName("Hydroponics.");
//			b.setEffect("^+20%");
//			b.setDesc("L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.");
//			b.setIcon(Art.buildings[0]);
//			data.buildingClasses.add(b);
//		}
//
//		{
//			BuildingClassModel b = new BuildingClassModel(Type.ECONOMIC_CENTER);
//			b.setName("Economic center");
//			b.setShortName("Centre eco.");
//			b.setEffect("$+20% @+5% pop+5");
//			b.setDesc("L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.");
//			b.setIcon(Art.buildings[1]);
//			data.buildingClasses.add(b);
//		}
//
//		{
//			BuildingClassModel b = new BuildingClassModel(Type.DOCK);
//			b.setName("Space dock");
//			b.setShortName("space dock");
//			b.setEffect("*+20%");
//			b.setDesc("Dock for spaceships located in low orbit, they provide facilities to build and repair spacecraft.");
//			b.setIcon(Art.buildings[2]);
//			data.buildingClasses.add(b);
//		}
//
//		for (int i = 0; i < 30; i++)
//		{
//			BuildingClassModel b = new BuildingClassModel(tempMethode(i));
//			b.setName("random building " + i);
//			b.setShortName("rnd "  + i);
//			b.setEffect("*+20%");
//			b.setDesc("rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd rnd");
//			b.setIcon(Art.buildings[3 + i]);
//			data.buildingClasses.add(b);
//		}
	}

	private static Type tempMethode (int i) {
		switch (i) {
		case 1: return Type.RANDOM_1;
		case 2: return Type.RANDOM_2;
		case 3: return Type.RANDOM_3;
		case 4: return Type.RANDOM_4;
		case 5: return Type.RANDOM_5;
		case 6: return Type.RANDOM_6;
		case 7: return Type.RANDOM_7;
		case 8: return Type.RANDOM_8;
		case 9: return Type.RANDOM_9;
		case 10: return Type.RANDOM_10;
		case 11: return Type.RANDOM_11;
		case 12: return Type.RANDOM_12;
		case 13: return Type.RANDOM_13;
		case 14: return Type.RANDOM_14;
		case 15: return Type.RANDOM_15;
		case 16: return Type.RANDOM_16;
		case 17: return Type.RANDOM_17;
		case 18: return Type.RANDOM_18;
		case 19: return Type.RANDOM_19;
		case 20: return Type.RANDOM_20;
		case 21: return Type.RANDOM_21;
		case 22: return Type.RANDOM_22;
		case 23: return Type.RANDOM_23;
		case 24: return Type.RANDOM_24;
		case 25: return Type.RANDOM_25;
		case 26: return Type.RANDOM_26;
		case 27: return Type.RANDOM_27;
		case 28: return Type.RANDOM_28;
		case 29: return Type.RANDOM_29;
		}
		return Type.RANDOM_30;
	}

	public static void initFleets (GameData data) {

		// Create fleets
		ShipClassModel sc = data.shipClasses.get(0);
		for (PlayerModel player: data.players) {
			{
				FleetModel fleet = new FleetModel(player, NameGenerator.generate(player.getNameGeneratorLanguage(), player.getFleets().size()));
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
				
				data.fleets.add(fleet);
			}
			{
				FleetModel fleet = new FleetModel(player);
				fleet.setLocation(player.getHome());
				//fleet.setName(player.equals(mPlayer) ? "Beta" : player.getName());
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				player.addFleet(fleet);
				
				data.fleets.add(fleet);
			}
			{
				FleetModel fleet = new FleetModel(player);
				fleet.setLocation(player.getHome());
				//fleet.setName(player.equals(mPlayer) ? "Omega" : player.getName());
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				fleet.addShip(new ShipModel(sc));
				player.addFleet(fleet);
				
				data.fleets.add(fleet);
			}
		}
	}

	public static void initHomeWorlds (GameData data) {
		int offset = data.systems.size() / data.players.size();
		int i = 0;
		for (PlayerModel player: data.players) {
			// Add random planets if system is within 3 planet
			int nbPlanetToAdd = Math.max(0, 3 - data.systems.get(i).getPlanets().size());
			for (int j = 0; j < nbPlanetToAdd; j++) {
				PlanetModel p = PlanetModel.create(data.systems.get(i).getPlanets().size());
				data.systems.get(i).addPlanet(p);
				data.planets.add(p);
			}
			player.colonize(data.systems.get(i).getRicherPlanet());
			i += offset;
		}
	}

	public static void initTravelLines (GameData data) {
		// Create travel lines
		for (SystemModel s: data.systems) {
			int r = (int)(Game.sRandom.nextInt(2));
			SystemModel s1 = null;
			SystemModel s2 = null;
			SystemModel s3 = null;
			int distance1 = Integer.MAX_VALUE;
			int distance2 = Integer.MAX_VALUE;
			int distance3 = Integer.MAX_VALUE;
			for (SystemModel sp: data.systems) {
				if (s != sp && s.getDistance(sp) < distance1) {
					distance1 = s.getDistance(sp);
					s1 = sp;
				}
			}
			for (SystemModel sp: data.systems) {
				if (s != sp && s.getDistance(sp) < distance2 && s.getDistance(sp) > distance1) {
					distance2 = s.getDistance(sp);
					s2 = sp;
				}
			}
			if (r == 1) {
				for (SystemModel sp: data.systems) {
					if (s != sp && s.getDistance(sp) < distance3 && s.getDistance(sp) > distance1 && s.getDistance(sp) > distance2) {
						distance3 = s.getDistance(sp);
						s3 = sp;
					}
				}
			}
			//System.out.println(s.getName() + ", link to: " + s1.getName() + ", " + s2.getName());
			data.travelLines.add(new TravelModel(s, s1));
			s.addNeighbor(s1);
			s1.addNeighbor(s);
			if (s2 != null) {
				data.travelLines.add(new TravelModel(s, s2));
				s.addNeighbor(s2);
				s2.addNeighbor(s);
			}
			if (s3 != null) {
				data.travelLines.add(new TravelModel(s, s3));
				s.addNeighbor(s3);
				s3.addNeighbor(s);
			}
		}
	}

	public static void initSystems (GameData data) {
		data.systemMapIndex = 4;//(int)(Math.random() * sMap.length);

		for (int[] s : sSystemMap[data.systemMapIndex]) {
			SystemModel system = SystemModel.create(s[0], s[1]);
			data.planets.addAll(system.getPlanets());
			data.systems.add(system);
		}
	}

	public static void initPlayers (GameData data) {
		data.player = new PlayerModel("me", new Color(0.9f, 0.9f, 0, 0.65f), Color.YELLOW, false);
		data.players.add(new PlayerModel("player-1", new Color(0.55f, 0, 0, 0.65f), new Color(0.55f, 0, 0, 1), true));
		data.players.add(new PlayerModel("player-2", new Color(80f/255, 120f/255, 182f/255, 0.65f), new Color(80f/255, 120f/255, 182f/255, 1), true));
		data.players.add(new PlayerModel("player-3", new Color(160f/255, 190f/255, 24f/255, 0.65f), Color.PINK, true));
		data.players.add(data.player);
		data.player.setNameGeneratorLanguage(NameGenerator.GREC);
	}

	public static void initShipClasses (GameData data) {
		{
			ShipClassModel sc = new ShipClassModel("Fighter", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.setBuildValue(100);
			data.shipClasses.add(sc);
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
			data.shipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Cruiser", 250);
			sc.setBuildValue(800);
			data.shipClasses.add(sc);
		}
		{
			ShipClassModel sc =new ShipClassModel("Colonizer", 40);
			sc.addDevice(DeviceModel.get(Device.COLONIZER));
			sc.setBuildValue(42);
			data.shipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Explorer", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.setBuildValue(60);
			data.shipClasses.add(sc);
		}
	}

}
