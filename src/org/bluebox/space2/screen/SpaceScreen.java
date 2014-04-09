
package org.bluebox.space2.screen;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.bluebox.space2.Art;
import org.bluebox.space2.Constants;
import org.bluebox.space2.model.FleetModel;
import org.bluebox.space2.model.PlayerModel;
import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.model.TravelModel;
import org.bluebox.space2.service.GameService;
import org.bluebox.space2.ui.ImageView;
import org.bluebox.space2.ui.TextView;
import org.bluebox.space2.ui.View;
import org.bluebox.space2.ui.View.OnClickListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class SpaceScreen extends Screen {
	private static final int MAP_POS_X = Constants.GAME_WIDTH - 64 - 6;
	private static final int MAP_POS_Y = 6;

	private SystemModel 			mSelected;
	private SystemModel 			mActionSystem;
	private SpaceActionScreen 	mActionScreen;
	private ImageView mBtPlanets;
	private ImageView mBtRelations;
	private ImageView mBtDebug;
	private ImageView mBtArmada;
	private List<TravelModel> mTravelPath;
	private List<SystemModel> mSystemPath;

	private SpriteCache 			mCurrentTravelCache;
	private int 					mCurrentTravelCacheId;
	private Color[][]				mMap;

	public SpaceScreen () {
		mParalax = Art.bg;
		mCurrentTravelCache = new SpriteCache(100, true);
		mCurrentTravelCacheId = -1;
		mMap = new Color[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		mRefreshOnUpdate = true;
	}
	
	@Override
	protected void onCreate () {

		for (int x = 0; x < Constants.MAP_WIDTH; x++) {
			for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
				mMap[x][y] = null;
			}
		}

		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (int x = 0; x < 80; x++) {
			for (SystemModel system: systems) {
				if (system.getOwner() != null) {
					int x2 = system.getX() + Constants.SYSTEM_SIZE / 2;
					int y2 = system.getY() + Constants.SYSTEM_SIZE / 2;
					
					for (int i = 0; i < 1000; i++) {
						setPoint((int)Math.round(x2+Math.cos(i) * x), (int)Math.round(y2+Math.sin(i) * x), system.getOwner().getSpaceColor());
					}
				}
			}
		}
		
		// Button planets
		mBtPlanets = new ImageView(Art.bt_planets, 6, 6);
		mBtPlanets.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelGovernmentScreen());
			}
		});
		addView(mBtPlanets);
		addView(new TextView("GOV.", 8, 40));

		// Button relations
		mBtRelations = new ImageView(Art.bt_relations, 44, 6);
		mBtRelations.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelRealtionScreen());
			}
		});
		addView(mBtRelations);
		addView(new TextView("RELS.", 46, 40));

		// Button debug
		mBtDebug = new ImageView(Art.bt_debug, 82, 6);
		mBtDebug.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new DebugScreen());
			}
		});
		addView(mBtDebug);
		addView(new TextView("DEBUG", 84, 40));

		// Button armada
		mBtArmada = new ImageView(Art.bt_debug, 120, 6);
		mBtArmada.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				addScreen(new PanelArmadaScreen());
			}
		});
		addView(mBtArmada);
		addView(new TextView("ARMADA", 122, 40));
	}
	
	private void setPoint (int x, int y, Color color) {
		if (x >= 0 && y >= 0 && x < Constants.MAP_WIDTH && y < Constants.MAP_HEIGHT && mMap[x][y] == null) {
			mMap[x][y] = color;
		}
	}

	@Override
	public void onRender (SpriteBatch spriteBatch, int gameTime, int screenTime) {
		drawRectangle(spriteBatch, 0, 0, 1000, 1000, Color.RED);

//		try {
//
//		//create our FBOs
//		FrameBuffer blurTargetA = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
//		FrameBuffer blurTargetB = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
//		
//		//our basic pass-through vertex shader
//		byte[] encoded = Files.readAllBytes(Paths.get("res/lesson5.vert"));
//		final String VERT = Charset.forName("US-ASCII").decode(ByteBuffer.wrap(encoded)).toString();
//
//		//our fragment shader, which does the blur in one direction at a time
//		byte[] encoded2 = Files.readAllBytes(Paths.get("res/lesson5.frag"));
//		final String FRAG = Charset.forName("US-ASCII").decode(ByteBuffer.wrap(encoded2)).toString();
//
//		//create our shader program
//		ShaderProgram blurShader = new ShaderProgram(VERT, FRAG);
//
//		//Good idea to log any warnings if they exist
//		System.out.println("shader:" + blurShader.isCompiled());
//		
//		if (blurShader.getLog().length() != 0)
//		    System.out.println(blurShader.getLog());
//
//		blurShader.begin();
//		blurShader.setUniformf("dir", 0f, 0f); //direction of blur; nil for now
//		blurShader.setUniformf("resolution", FBO_SIZE); //size of FBO texture
//		blurShader.setUniformf("radius", 3f); //radius of blur
////		blurShader.setUniformMatrix("u_worldView", projection);
//		blurShader.setUniformi("u_texture", 0);
//		//blurShader.setUniformMatrix("u_worldView", matrix);
//		//blurShader.setUniformi("u_texture", 0);
//		//mesh.render(shader, GL10.GL_TRIANGLES);
//		blurShader.end();
//		
//		spriteBatch.draw(region, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
//		blurTargetA.end();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		drawRectangle(spriteBatch, Constants.GAME_WIDTH - 64 - 6, 6, 63, 50, new Color(0, 0.06f, 0.14f, 1));
		drawRectangle(spriteBatch, Constants.GAME_WIDTH - 64 - 6 - mRealPosX / 20, 6 - mRealPosY / 20, 18, 12, new Color(0.5f, 0.5f, 0.8f, 0.8f));
		
		List<SystemModel> systems = GameService.getInstance().getSystems();
		
		// Draw fleets
		for (SystemModel system: systems) {
			if (system.getFleets().size() > 0) {
				drawRectangle(spriteBatch, mRealPosX + system.getX() + 16, mRealPosY + system.getY() - 4, 16, 16, system.getFleets().get(0).getOwner().getColor());
				draw(spriteBatch, Art.ship, mRealPosX + system.getX() + 16, mRealPosY + system.getY() - 4);
				if (system.getFleets().size() > 1) {
					drawString(spriteBatch, "+" + (system.getFleets().size() - 1), mRealPosX + system.getX() + 28, mRealPosY + system.getY() + 12);
				}
			}
		}
	}

	@Override
	public void onDraw (int gameTime, int screenTime) {

//		if (mPixmap != null) {
//			mPixmap.dispose();
//		}
		
		mPixmap = new Pixmap(Constants.MAP_WIDTH, Constants.MAP_HEIGHT, Format.RGBA8888);
//		mPixmap.setColor(Color.CYAN);
//		mPixmap.fillRectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		
		for (int x = 0; x < Constants.MAP_WIDTH; x++) {
			for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
				if (mMap[x][y] != null) {
//					mPixmap.setColor(mMap[x][y]);
					mPixmap.drawPixel(x, Constants.MAP_HEIGHT - y, Color.rgba8888(mMap[x][y]));
					//drawRectangle(x + Constants.SYSTEM_SIZE / 2, y + Constants.SYSTEM_SIZE / 2, 1, 1, mMap[x][y]);
				}
			}
		}

		draw(mPixmap, 0, 0);
		
		mPixmap.dispose();


		// Draw travel lines
		List<TravelModel> travels = GameService.getInstance().getTraveLines();
		for (TravelModel travel : travels) {
			int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
			drawRectangle(
				mDeprecatedPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
				mDeprecatedPosY + Constants.SYSTEM_SIZE / 2 + travel.getY(),
				length,
				1,
				new Color(0.8f, 0.8f, 1, 0.55f),
				travel.getAngle());
		}
		
		// Draw selected travel path
		if (mTravelPath != null) {
			for (TravelModel travel : mTravelPath) {
				int length = (int)Math.sqrt(Math.pow(Math.abs(travel.getFrom().getX() - travel.getTo().getX()), 2) + Math.pow(Math.abs(travel.getFrom().getY() - travel.getTo().getY()), 2));
				drawRectangle(
					mDeprecatedPosX - length / 2 + Constants.SYSTEM_SIZE / 2 + travel.getX(),
					mDeprecatedPosY - 1 + Constants.SYSTEM_SIZE / 2 + travel.getY(),
					length,
					2,
					new Color(0.75f, 0.9f, 0, 0.95f),
					travel.getAngle());
			}
			for (SystemModel system: mSystemPath) {
				if (system != mSelected) {
					draw(Art.system_selected[system.getType()], mDeprecatedPosX + system.getX(), mDeprecatedPosY + system.getY());
				}
			}
			
			draw(Art.system_selected[mSelected.getType()], mDeprecatedPosX + mSelected.getX(), mDeprecatedPosY + mSelected.getY());
		}
		
		// Draw selected system
		else if (mSelected != null) {
			draw(Art.system_selected[mSelected.getType()], mDeprecatedPosX + mSelected.getX(), mDeprecatedPosY + mSelected.getY());
		}

		// Draw system
		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system : systems) {
			drawSystem(system);
		}
		
		drawInterface(Constants.GAME_WIDTH - 100, 10);
	}

	private void drawSystem (SystemModel system) {
		draw(Art.system[system.getType()], mDeprecatedPosX + system.getX(), mDeprecatedPosY + system.getY());

		String name = system.getName();
		PlayerModel owner = system.getOwner();
		if (owner != null) {
			setStringColor(owner.getColor());
		}
		drawString(name, mDeprecatedPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mDeprecatedPosY + system.getY() + Constants.SYSTEM_SIZE + 6);
	}

	private void drawInterface (int posX, int posY) {
		List<SystemModel> systems = GameService.getInstance().getSystems();

//		draw(Art.bt_planets, 6, 6);
//		drawString("GOV.", posX + 2, posY + 34);
//
//		draw(Art.bt_relations, 44, 6);
//		drawString("RELS.", posX + 40, posY + 34);
//
		mCacheUI.beginCache();

		draw(mCacheUI, Art.map, Constants.GAME_WIDTH - 64 - 6, 6);
		//drawString(mCacheUI, "Cycle:  " + mCycle, Constants.GAME_WIDTH - 64 - 4, posY + 49);

		// Mini-map
		for (SystemModel system : systems) {
			Color color = system.getOwner() != null ? system.getOwner().getColor() : Color.WHITE;
			drawRectangle(mCacheUI, MAP_POS_X + system.getX() / 20, MAP_POS_Y + system.getY() / 20, 1, 1, color);
		}
		
		mCacheUIId = mCacheUI.endCache();

	}

	@Override
	public void onTouch (int x, int y) {

//		if (x < 32 && y < 32) {
//			GameService.getInstance().dump();
//			GameService.getInstance().getSystems().clear();
//		} else {
//			GameService.getInstance().addSystem(mPosX + x, mPosY + y);
//		}
		
		// Selected mode
		if (mSelected != null) {
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
			if (system != null && mSelected != system) {
				mActionSystem = system;
				mActionScreen.setActionSystem(system);
			}
		}
		
		// Normal mode
		else {

			// bt government
			if (x >= 6 && x <= 6 + 32 && y >= 2 && y <= 44) {
				addScreen(new PanelGovernmentScreen());
				return;
			}
			
			// bt relations
			if (x >= 44 && x <= 44 + 32 && y >= 2 && y <= 44) {
				addScreen(new PanelRealtionScreen());
				return;
			}
			
			SystemModel system = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
			if (system != null) {
				addScreen(new SystemScreen(system));
				return;
			}
			
			TravelModel travel = GameService.getInstance().getTravelAtPos(x - mRealPosX, y - mRealPosY);
			if (travel != null) {
				addScreen(new TravelScreen(travel));
				return;
			}
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mRealPosX += offsetX;
		mRealPosY += offsetY;
		mParalaxNotified = true;
	}

	@Override
	public void onMoveEnd (int offsetX, int offsetY) {
//		notifyChange();
	}

	public void gotoPos (int x, int y) {
		mRealPosX = - x + Constants.GAME_WIDTH / 2;
		mRealPosY = - y + Constants.GAME_HEIGHT / 2;
	}

	@Override
	public void onLongTouch (int x, int y) {
		SystemModel selected = GameService.getInstance().getSystemAtPos(x - mRealPosX, y - mRealPosY);
		if (selected != null) {
			setSelected(selected);
			mActionScreen = new SpaceActionScreen(this, mSelected);
			addScreen(mActionScreen);
			
			mBtArmada.setVisibility(View.GONE);
			mBtDebug.setVisibility(View.GONE);
			mBtPlanets.setVisibility(View.GONE);
			mBtRelations.setVisibility(View.GONE);
			
			notifyChange();
		}
	}

	public void setTravelPath (List<TravelModel> travelPath, List<SystemModel> systemPath) {
		mTravelPath = travelPath;
		mSystemPath = systemPath;
		notifyChange();
	}

	public void setSelected (SystemModel selected) {
		mSelected = selected;
		notifyChange();
	}

}
