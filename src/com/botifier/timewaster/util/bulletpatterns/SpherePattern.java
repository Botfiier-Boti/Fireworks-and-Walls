package com.botifier.timewaster.util.bulletpatterns;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.util.Entity;

public class SpherePattern extends BulletPattern {
	float angle = 0f;
	
	public SpherePattern() {
		fireSpeed = 0.75f;
		bulletSpeed = 25f;
		duration = 35;
		shots = 16;
		spread = 50f;
		mindamage = 45; 
		atkScaling = false;
		armorPierce = true;
	}

	@Override
	public void fire(Entity owner, float x, float y, float angle) throws SlickException {
		for (int i = 0; i < shots; i++) {
			owner.b.add(createBullet(owner, owner.getLocation().getX(), owner.getLocation().getY(), this.angle, i));	
		}
		this.angle += 2f;
	}

}
