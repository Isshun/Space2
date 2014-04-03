package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mojang.metagun.Art;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.service.GameService;

public class PanelRealtionScreen extends Screen {

	@Override
	public void onRender (SpriteBatch spriteBatch) {
		draw(Art.bg, 0, 0);

		List<PlayerModel> players = GameService.getInstance().getPlayers();
		int i = 0;
		for (PlayerModel player : players) {
			draw(player.getFlag(), 4, 4 + i * 20);
			drawRectangle(20, 4 + i * 20, 8, 8, player.getColor());
			drawString(player.getName(), 30, 6 + i * 20);
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

}
