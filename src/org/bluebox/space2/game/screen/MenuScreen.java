package org.bluebox.space2.game.screen;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.ButtonView;
import org.bluebox.space2.engine.ui.View.OnClickListener;
import org.bluebox.space2.game.GameDataFactory;
import org.bluebox.space2.game.GameDataLoader;
import org.bluebox.space2.game.service.GameService;

public class MenuScreen extends BaseScreen {

	@Override
	protected void onCreate () {
		mGame.stopRunning();

		ButtonView btCreate = new ButtonView("create", 20, 20);
		btCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				GameService.getInstance().setData(GameDataFactory.create());
				mGame.startRunning();
				addScreen(new SpaceScreen());
			}
		});
		addView(btCreate);

		ButtonView btLoad = new ButtonView("load", 20, 60);
		btLoad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick () {
				GameService.getInstance().setData(GameDataLoader.load());
				mGame.startRunning();
				addScreen(new SpaceScreen());
			}
		});
		addView(btLoad);
}

	@Override
	protected void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMove (int startX, int startY, int offsetX, int offsetY) {
		// TODO Auto-generated method stub

	}

}
