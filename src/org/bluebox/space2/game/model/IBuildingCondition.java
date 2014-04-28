package org.bluebox.space2.game.model;

import java.util.List;

public interface IBuildingCondition {
	int isAvailable(PlayerModel player, PlanetModel planet, List<String> requires);
}
