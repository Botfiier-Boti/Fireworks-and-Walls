package com.botifier.timewaster.util.movements;

import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.EntityController;

public class BulletController extends EntityController {
	boolean pierceEnemies = false;
	boolean pierceObstacles = false;
	boolean pierceDefense = false;
	float damage = 0.0f;
	long distTr = 0;
	long dist = 100;
	float angle;
	
	public BulletController(float x, float y, float speed, long life, float angle) {
		super(x, y, speed);
		this.dist = life;
		this.angle = angle;
		moving = true;
	}
	
	@Override
	public void move(int delta) {
		PPS = 0.6f + 1.5f*(speed/75f);
		if (moving) {
			float x = (float)Math.cos(angle)*(PPS);
			float y = (float)Math.sin(angle)*(PPS);
			Vector2f nLoc = new Vector2f(x, y);
			if (pierceEnemies == false) {
				Vector2f cLoc = src.copy();
				cLoc.add(nLoc);
				for (Entity en : MainGame.mm.e) {
					if (en.hitbox.contains(cLoc.x, cLoc.y)) {
						distTr = 0;
						dst = null;
						moving = false;
						return;
					}
				}
			}
			if (pierceObstacles == false) {
				Vector2f cLoc = src.copy();
				cLoc.add(nLoc);
				if (MainGame.mm.m.blocked(null, (int)cLoc.x/16, (int)cLoc.y/16)) {
					distTr = 0;
					dst = null;
					moving = false;
					return;
				}
			}
			src.add(nLoc);
			distTr += 1;
			if (distTr >= dist) {
				distTr = 0;
				dst = null;
				moving = false;
			 }
		} else {
			return;
		}
	}
	
	public float getAngle() {
		return angle;
	}
}
