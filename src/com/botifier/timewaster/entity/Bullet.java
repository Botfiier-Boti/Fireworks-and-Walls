package com.botifier.timewaster.entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.BulletController;

public class Bullet extends Entity {
	static Image placehold = null;
	public int[] basedamage;
	public boolean ignoresArmor = false;
	public boolean atkScaling = true;
	public boolean boomerang = false;
	
	public Bullet(String name, float x, float y, float speed, float angle, long lifeTime, int minDmg, int maxDmg, Entity origin) throws SlickException {
		super(name, MainGame.getImage("DefaultShot"), new BulletController(x, y, speed, lifeTime, angle, origin));
		rotation = (float) Math.toDegrees(angle);
		basedamage = new int[] {minDmg, maxDmg};
		wOverride = 3;
		hOverride = 3;
		healthbarVisible = false;
		hasshadow = false;
	}
	
	public Bullet(String name, float x, float y, float speed, float angle, long lifeTime, int minDmg, int maxDmg, Entity origin, boolean pierceObstacles, boolean pierceEnemies, boolean boomerang) throws SlickException {
		super(name, MainGame.getImage("DefaultShot"), new BulletController(x, y, speed, lifeTime, angle, origin, pierceObstacles, pierceEnemies, boomerang));
		rotation = (float) Math.toDegrees(angle);
		basedamage = new int[] {minDmg, maxDmg};
		wOverride = 3;
		hOverride = 3;
		healthbarVisible = false;
		hasshadow = false;
	}
	
	public Bullet(Image i, String name, float x, float y, float speed, float angle, long lifeTime, int minDmg, int maxDmg, Entity origin, boolean pierceObstacles, boolean pierceEnemies, boolean boomerang) throws SlickException {
		super(name, i, new BulletController(x, y, speed, lifeTime, angle, origin, pierceObstacles, pierceEnemies, boomerang));
		rotation = (float) Math.toDegrees(angle);
		basedamage = new int[] {minDmg, maxDmg};
		wOverride = 3;
		hOverride = 3;
		healthbarVisible = false;
		hasshadow = false;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		rotation = (float) Math.toDegrees(getController().getAngle());
		if (getController().isMoving() == false) {
			onDestroy();
			destroy = true;
			return;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	@Override
	public BulletController getController() {
		return ((BulletController)super.getController());
	}
	
	public static Bullet createBullet(String name, float x, float y, float speed, float angle, long lifeTime, int minDmg, int maxDmg, Entity origin, boolean pierceObstacles, boolean pierceEnemies, boolean boomerang) throws SlickException {
		Bullet b = new Bullet(MainGame.getImage("DefaultShot"), name, x, y, speed, angle, lifeTime, minDmg, maxDmg, origin, pierceObstacles, pierceEnemies, boomerang);
		return b;
	}
	
	public static Bullet createBullet(String name, float x, float y, float speed, float angle, long lifeTime, int minDmg, int maxDmg, Entity origin) throws SlickException {
		return createBullet(name, angle, angle, angle, angle, lifeTime, maxDmg, maxDmg, origin, false, false, false);
	}
	
	public void onDestroy() {
		
	}
	
	public void onHit() {
		
	}
	
	public Entity getOrigin() {
		return getController().getOrigin();
	}
	
	public boolean scalesWithAtk() {
		return atkScaling;
	}
	
	public boolean ignoresDefense() {
		return ignoresArmor;
	}

}
