
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.model.TravelModel;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.ui.ImageView;
import com.mojang.metagun.ui.TextView;
import com.mojang.metagun.ui.View;
import com.mojang.metagun.ui.View.OnClickListener;

public class SpaceScreen extends Screen {
	private static final int MAP_POS_X = Constants.GAME_WIDTH - 64 - 6;
	private static final int MAP_POS_Y = 6;

	private int mPosX;
	private int mPosY;

	@Override
	protected void onCreate () {

		// Button debug
		ImageView btDebug = new ImageView(Art.bt_debug, 82, 6);
		btDebug.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new DebugScreen());
			}
		});
		addView(btDebug);
		addView(new TextView("DEBUG", 84, 40));

		// Button armada
		ImageView btArmada = new ImageView(Art.bt_debug, 120, 6);
		btArmada.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelArmadaScreen());
			}
		});
		addView(btArmada);
		addView(new TextView("ARMADA", 122, 40));
	}

	@Override
	public void onRender (SpriteBatch spriteBatch) {
		int posX = mPosX / 8;
		int posY = mPosY / 8;

		draw(Art.bg, posX - 320, posY - 240);

//		draw(Art.bg, posX - 320, posY - 240);
//		draw(Art.bg, posX, posY - 240);
//		draw(Art.bg, posX + 320, posY - 240);
//
//		draw(Art.bg, posX - 320, posY);
//		draw(Art.bg, posX, posY);
//		draw(Art.bg, posX + 320, posY);
//
//		draw(Art.bg, posX - 320, posY + 240);
//		draw(Art.bg, posX, posY + 240);
//		draw(Art.bg, posX + 320, posY + 240);

		// Draw travel lines
		List<TravelModel> travels = GameService.getInstance().getTraveLines();
		for (TravelModel travel : travels) {
			int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
			drawRectangle(
				mPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
				mPosY + Constants.SYSTEM_SIZE / 2 + travel.getY(),
				length,
				2,
				new Color(1, 1, 1, 0.65f),
				travel.getAngle());
//			Pixmap pixmap = new Pixmap(length, 2, Format.RGBA8888);
//			pixmap.setColor(Color.rgba8888(1, 1, 1, 0.65f));
//			pixmap.fillRectangle(0, 0, length, 2);
//			Texture pixmaptex = new Texture(pixmap);
//			Sprite line = new Sprite(pixmaptex);
//			line.setRotation(travel.getAngle());
//			line.setPosition(mPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(), mPosY + Constants.SYSTEM_SIZE / 2 + travel.getY());
//			line.draw(mSpriteBatch);
//			pixmap.dispose();
		}

		// Draw systems
		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system : systems) {
			drawSystem(system);
		}

		// Draws travel ships
		for (TravelModel travel : travels) {
			if (travel.getNbFleet() > 0) {
				draw(Art.ship, mPosX + 4 + travel.getX(), mPosY + 4 + travel.getY(), travel.getAngle());
			}
		}
		
		drawInterface(systems, 6, 6);
	}

	private void drawSystem (SystemModel system) {
		draw(Art.system, mPosX + system.getX(), mPosY + system.getY());
		String name = system.getName();
		PlayerModel owner = system.getOwner();
		if (owner != null) {
			drawString(name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6, owner.getColor());
		} else {
			drawString(name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6, Color.WHITE);
		}
	}

	private void drawInterface (List<SystemModel> systems, int posX, int posY) {
		draw(Art.bt_planets, 6, 6);
		drawString("GOV.", posX + 2, posY + 34);

		draw(Art.bt_relations, 44, 6);
		drawString("RELS.", posX + 40, posY + 34);

		draw(Art.map, Constants.GAME_WIDTH - 64 - 6, 6);
		drawRectangle(Constants.GAME_WIDTH - 64 - 6 - mPosX / 20, 6 - mPosY / 20, 18, 12, Color.rgba8888(0.5f, 0.5f, 0.8f, 0.8f));
		drawString("Cycle:  42", Constants.GAME_WIDTH - 64 - 4, posY + 49);

		// Mini-map
		for (SystemModel system : systems) {
			Color color = system.getOwner() != null ? system.getOwner().getColor() : Color.WHITE;
			drawRectangle(MAP_POS_X + system.getX() / 20, MAP_POS_Y + system.getY() / 20, 1, 1, color);
		}
	}

	@Override
	public void onTouch (int x, int y) {

//		if (x < 32 && y < 32) {
//			GameService.getInstance().dump();
//			GameService.getInstance().getSystems().clear();
//		} else {
//			GameService.getInstance().addSystem(mPosX + x, mPosY + y);
//		}
		
		// bt government
		if (x >= 6 && x <= 6 + 32 && y >= 2 && y <= 44) {
			addScreen(new PanelGovernmentScreen());
		}
		
		// bt relations
		if (x >= 44 && x <= 44 + 32 && y >= 2 && y <= 44) {
			addScreen(new PanelRealtionScreen());
		}
		
		SystemModel system = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
		if (system != null) {
			addScreen(new SystemScreen(system));
		}
		
		TravelModel travel = GameService.getInstance().getTravelAtPos(x - mPosX, y - mPosY);
		if (travel != null) {
			addScreen(new TravelScreen(travel));
		}

	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mPosX += offsetX;
		mPosY += offsetY;
	}
}
