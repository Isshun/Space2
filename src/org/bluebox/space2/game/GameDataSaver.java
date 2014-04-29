package org.bluebox.space2.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;

public class GameDataSaver {

	public static void save (GameData mData) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("1.sav", false))) {
			
			// Players
			bw.write("BEGIN PLAYERS\n");
			for (PlayerModel player: mData.players) {
				bw.write("\tBEGIN PLAYER\n");
				bw.write("\t\tID=" + player.getId() + "\n");
				bw.write("\t\tNAME=" + player.getName() + "\n");
				bw.write("\t\tHOME=" + player.getHome().getId() + "\n");
				bw.write("\t\tCOLOR1=" + player.getColor().toString() + "\n");
				bw.write("\t\tCOLOR2=" + player.getSpaceColor().toString() + "\n");
				if (player.isAI()) {
					bw.write("\t\tAI=" + 1 + "\n");
				}
				
				// Systems
				bw.write("BEGIN SYSTEMS\n");
				for (SystemModel system: player.getSystems()) {
					system.save(bw);
				}
				bw.write("END SYSTEMS\n");

				bw.write("\t\tHOME=" + player.getHome().getId() + "\n");
				
				bw.write("\tEND PLAYER\n\n");
			}
			bw.write("END PLAYERS\n\n");

			// Free systems
			bw.write("BEGIN SYSTEMS\n");
			for (SystemModel system: mData.systems) {
				if (system.isFree()) {
					system.save(bw);
				}
			}
			bw.write("END SYSTEMS\n");
			
			// Travel lines
			bw.write("BEGIN TRAVELS\n");
			for (TravelModel travel: mData.travelLines) {
				bw.write("BEGIN TRAVEL\n");
				bw.write("\tFROM=" + travel.getFrom().getId() + "\n");
				bw.write("\tTO=" + travel.getTo().getId() + "\n");
				bw.write("END TRAVEL\n");
			}
			bw.write("END TRAVELS\n");

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
