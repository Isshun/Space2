package org.bluebox.space2.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

public class GameDataLoader {

	public static GameData load (GameData mData) {
		mData = new GameData();
		mData.planets = new ArrayList<PlanetModel>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("1.sav"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replace("\t", "");

				// Start systems
				if ("BEGIN PLAYER".equals(line)) {
					PlayerModel player = PlayerModel.load(mData, br);
					mData.players.add(player);
					if (player.isAI() == false) {
						mData.player = player;
					}
				}

				// Start systems
				if ("BEGIN SYSTEM".equals(line)) {
					SystemModel system = SystemModel.load(mData, br, null);
					mData.systems.add(system);
					mData.planets.addAll(system.getPlanets());
				}

				// Travel lines
				if ("BEGIN TRAVEL".equals(line)) {
					TravelModel travel = TravelModel.load(mData, br);
					mData.travelLines.add(travel);
				}

			}
				
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addPlanetsToPlayers(mData);
		
		return mData;
	}

	private static void addPlanetsToPlayers (GameData mData) {
		for (PlanetModel planet: mData.planets) {
			if (planet.getOwner() != null) {
				
			}
		}
	}

}
