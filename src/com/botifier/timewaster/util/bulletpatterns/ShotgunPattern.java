package com.botifier.timewaster.util.bulletpatterns;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;

public class ShotgunPattern extends BulletPattern {
	public ShotgunPattern() {
		fireSpeed = 1f;
		bulletSpeed = 150f;
		duration = 25;
		shots = 5;
		spread=10f;
		mindamage = 25;
		maxdamage = 45;
		enemyPierce = true;
		obstaclePierce = false;
		boomerang = true;
		override = MainGame.getImage("Boomerang");
	}
	
	@Override
	public void fire(Entity owner, float x, float y, float angle) throws SlickException {
		if (shots == 1){
			owner.b.add(createBullet(owner, owner.getLocation().getX(), owner.getLocation().getY(), angle, 0));	
		} else if (shots % 2 != 0) {
			generateBullets(owner, owner.getLocation().getX(), owner.getLocation().getY(), angle, false);
		} else {
			generateBullets(owner, owner.getLocation().getX(), owner.getLocation().getY(), angle, true);
		}
	}
	
	private void generateBullets(Entity owner, float x, float y, float angle, boolean e) throws SlickException {
		for (int i = 0; i < shots; i++) {
			owner.b.add(createBullet(owner, x, y, angle, i));	
		}
	}

}
