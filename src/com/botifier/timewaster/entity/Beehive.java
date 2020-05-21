package com.botifier.timewaster.entity;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.EnemyController;

public class Beehive extends LobbedProjectile{
	float rotate = 3;
	public Beehive(float x, float y, Vector2f dst, Entity o) {
		super("Beehive", MainGame.getImage("beehive"), new EnemyController(x,y,50f,0,0), dst, o);
		if (dst.x < x)
			rotate = -rotate;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		rotation+=rotate;
	}

	@Override
	public void onLand() throws SlickException {
		float x = getLocation().getX();
		float y =  getLocation().getY();
		if (x < 0)
			return;
		if (x > MainGame.mm.m.getWidthInTiles()*16)
			return;
		if (y < 0)
			return;
		if (y > MainGame.mm.m.getHeightInTiles()*16)
			return;
		if (!MainGame.mm.m.blocked(null, (int)x/16, (int)y/16)) {
			BeeSwarm isc = new BeeSwarm(x, y, 20);
			o.spawns.add(isc);	
		}
	}

}
