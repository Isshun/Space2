package org.bluebox.space2.game.screen;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.engine.ui.TextView;

public class ErrorScreen extends BaseScreen {

	public static final int RESOLUTION_NOT_SUPPORTED = 1;

	public ErrorScreen (int error) {
		TextView text = new TextView("error", 6, 6);
		addView(text);
	}

	@Override
	protected void onCreate () {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw (BaseScreenLayer mainLayer, BaseScreenLayer UILayer) {
		// TODO Auto-generated method stub

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
	public void onLongTouch (int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
