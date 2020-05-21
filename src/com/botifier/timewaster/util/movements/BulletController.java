package com.botifier.timewaster.util.movements;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.entity.Bullet;
import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;

public class BulletController extends EntityController {
	boolean pierceEnemies = false;
	boolean pierceObstacles = false;
	boolean pierceDefense = false;
	boolean boomerangs = false;
	boolean inf = false;
	
	float damage = 0.0f;
	long distTr = 0;
	long dist = 100;
	long bpoint = 100;
	float angle;
	private Entity origin;
	private ArrayList<Entity> enemiesHit = new ArrayList<Entity>();
	private Vector2f originPoint;
	
	public BulletController(float x, float y, float speed, long life, float angle, Entity origin) {
		super(x, y, speed);
		this.dist = life;
		this.angle = angle;
		moving = true;
		this.setOrigin(origin);
		this.originPoint = origin.getLocation().copy();
	}
	
	public BulletController(float x, float y, float speed, long life, float angle, Entity origin, boolean ignoreObstacles, boolean pierceEnemies, boolean boomerang) {
		super(x, y, speed);
		this.dist = life;
		this.angle = angle;
		moving = true;
		this.setOrigin(origin);
		this.originPoint = origin.getLocation().copy();
		this.pierceObstacles = ignoreObstacles;
		this.pierceEnemies = pierceEnemies;
		this.boomerangs = boomerang;
		bpoint = dist/2;
	}
	
	@Override
	public void move(int delta) {
		PPS = speed/60f;
		if (moving) {
			float x = (float)Math.cos(angle)*(PPS);
			float y = (float)Math.sin(angle)*(PPS);
			Vector2f nLoc = new Vector2f(x, y);
			for (int i = MainGame.getEntities().size()-1; i > -1; i--) {
				Entity en = MainGame.getEntities().get(i);
				if (getOrigin() == null  || en instanceof Bullet || en.isInvincible() || en == getOrigin() || en.team == getOrigin().team || en.invulnerable == true || en.active == false || en.hitbox.intersects(getOwner().influence) == false || enemiesHit.contains(en))
					continue;
				if (en.hitbox.intersects(getOwner().hitbox)) {
					en.onHit((Bullet)getOwner());
					if (pierceEnemies == false) {
						distTr = 0;
						dst = null;
						moving = false;
						return;
					} else {
						enemiesHit.add(en);
					}
					continue;
				}
			}
			if (pierceObstacles == false) {
				Vector2f cLoc = src.copy();
				cLoc.add(nLoc);
				cLoc = src.copy();
				cLoc.add(nLoc);
				if (((int)cLoc.x/16>=0 && cLoc.x/16<MainGame.mm.m.getWidthInTiles()) && (((int)cLoc.y/16>=0) && (int)cLoc.y/16<MainGame.mm.m.getHeightInTiles())) {
					if (MainGame.mm.m.blocked(null, (int)cLoc.x/16, (int)cLoc.y/16)) {
						distTr = 0;
						dst = null;
						moving = false;
						return;
					}
				}
			}
			src.add(nLoc);
			distTr += 1;
			if (boomerangs && distTr == bpoint) {
				if (pierceEnemies == true)
					enemiesHit.clear();
				this.angle = Math2.calcAngle(src, originPoint);
				boomerangs = false;
			}
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

	public Entity getOrigin() {
		return origin;
	}

	public void setOrigin(Entity origin) {
		this.origin = origin;
	}
}
