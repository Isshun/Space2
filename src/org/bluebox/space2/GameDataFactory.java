package org.bluebox.space2;

import org.bluebox.space2.model.BuildingClassModel;
import org.bluebox.space2.model.BuildingClassModel.Type;
import org.bluebox.space2.model.DeviceModel;
import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.NameGenerator;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.model.ShipClassModel;
import org.bluebox.space2.model.ShipModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.model.TravelModel;
import org.bluebox.space2.model.DeviceModel.Device;

import com.badlogic.gdx.graphics.Color;

public class GameDataFactory {

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

		initBuildingClasses(data);
		initShipClasses(data);
		initPlayers(data);
		initSystems(data);
		initTravelLines(data);
		initHomeWorlds(data);
		initFleets(data);
		
		return data;
	}

	private static void initBuildingClasses (GameData data) {
		{
			BuildingClassModel b = new BuildingClassModel(Type.HYDROPONICS);
			b.setName("Hydroponic farms");
			b.setShortName("Hydroponics.");
			b.setEffect("^+20%");
			b.setDesc("L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.");
			data.buildingClasses.add(b);
		}

		{
			BuildingClassModel b = new BuildingClassModel(Type.ECONOMIC_CENTER);
			b.setName("Economic center");
			b.setShortName("Centre eco.");
			b.setEffect("$+20% @+5% pop+5");
			b.setDesc("L'hydroponie est la culture de plantes réalisée sur un substrat neutre et inerte (de type sable, pouzzolane, billes d'argile, laine de roche etc.). Ce substrat est régulièrement irrigué d'un courant de solution qui apporte des sels minéraux et des nutriments essentiels à la plante.");
			data.buildingClasses.add(b);
		}

		{
			BuildingClassModel b = new BuildingClassModel(Type.DOCK);
			b.setName("Space dock");
			b.setShortName("space dock");
			b.setEffect("*+20%");
			b.setDesc("Dock for spaceships located in low orbit, they provide facilities to build and repair spacecraft.");
			data.buildingClasses.add(b);
		}
	}

	private static void initFleets (GameData data) {

		// Create fleets
		ShipClassModel sc = data.shipClasses.get(0);
		for (PlayerModel player: data.players) {
			{
				FleetModel fleet = new FleetModel(NameGenerator.generate(NameGenerator.KLINGON, player.getFleets().size()));
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
				FleetModel fleet = new FleetModel();
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
				FleetModel fleet = new FleetModel();
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

	private static void initHomeWorlds (GameData data) {
		int offset = data.systems.size() / data.players.size();
		int i = 0;
		for (PlayerModel player: data.players) {
			player.colonize(data.systems.get(i).getRicherPlanet());
			i += offset;
		}
	}

	private static void initTravelLines (GameData data) {
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
			if (s2 != null) {
				data.travelLines.add(new TravelModel(s, s2));
			}
			if (s3 != null) {
				data.travelLines.add(new TravelModel(s, s3));
			}
		}
	}

	private static void initSystems (GameData data) {
		data.systemMapIndex = 4;//(int)(Math.random() * sMap.length);

		for (int[] s : sSystemMap[data.systemMapIndex]) {
			SystemModel system = SystemModel.create(s[0], s[1]);
			data.planets.addAll(system.getPlanets());
			data.systems.add(system);
		}
	}

	private static void initPlayers (GameData data) {
		data.player = new PlayerModel("me", new Color(0.9f, 0.9f, 0, 0.65f), Color.YELLOW);
		data.players.add(new PlayerModel("player-1", new Color(0.55f, 0, 0, 0.65f), new Color(0.55f, 0, 0, 1)));
		data.players.add(new PlayerModel("player-2", new Color(80f/255, 120f/255, 182f/255, 0.65f), new Color(80f/255, 120f/255, 182f/255, 1)));
		data.players.add(new PlayerModel("player-3", new Color(160f/255, 190f/255, 24f/255, 0.65f), Color.PINK));
		data.players.add(data.player);
	}

	private static void initShipClasses (GameData data) {
		{
			ShipClassModel sc = new ShipClassModel("Fighter", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.addDevice(DeviceModel.get(Device.COLONIZER));
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
			sc.addDevice(DeviceModel.get(Device.COLONIZER));
			sc.setBuildValue(200);
			data.shipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Cruiser", 250);
			sc.addDevice(DeviceModel.get(Device.COLONIZER));
			sc.setBuildValue(800);
			data.shipClasses.add(sc);
		}
		{
			ShipClassModel sc = new ShipClassModel("Explorer", 50);
			sc.addDevice(DeviceModel.get(Device.PHASER_1));
			sc.addDevice(DeviceModel.get(Device.HULL_1));
			sc.addDevice(DeviceModel.get(Device.SHIELD_1));
			sc.addDevice(DeviceModel.get(Device.COLONIZER));
			sc.setBuildValue(60);
			data.shipClasses.add(sc);
		}
	}

}
