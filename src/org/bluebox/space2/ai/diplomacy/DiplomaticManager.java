package org.bluebox.space2.ai.diplomacy;

import java.util.ArrayList;
import java.util.List;

import org.bluebox.space2.ai.AIManager;
import org.bluebox.space2.ai.AIPlayerModel;
import org.bluebox.space2.ai.GSAI;
import org.bluebox.space2.ai.Goal;

public class DiplomaticManager extends AIManager {

	private List<PlayerReport>	mReports;
	
	public DiplomaticManager (GSAI gsai, AIPlayerModel player) {
		super(gsai, player);
		
		mReports = new ArrayList<PlayerReport>();
	}

	@Override
	public Goal need (Object need) {
		return null;
		
	}

	@Override
	public void onUpdate () {
		// TODO Auto-generated method stub
		
	}

	public List<PlayerReport> getPlayerReports () {
		return mReports;
	}

}
