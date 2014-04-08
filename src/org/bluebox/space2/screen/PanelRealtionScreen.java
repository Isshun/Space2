package org.bluebox.space2.screen;

import java.util.List;

import org.bluebox.space2.Art;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.service.GameService;


public class PanelRealtionScreen extends Screen {

	@Override
	public void onDraw (int gameTime, int screenTime) {
		draw(Art.bg, 0, 0);

		List<PlayerModel> players = GameService.getInstance().getPlayers();
		int i = 0;
		for (PlayerModel player : players) {
			draw(player.getFlag(), 4, 4 + i * 20);
			drawRectangle(20, 4 + i * 20, 8, 8, player.getColor());
			drawString(player.getName(), 30, 6 + i * 20);
			drawString(player.getHome().getName(), 120, 6 + i * 20);
			i++;
		}
	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
