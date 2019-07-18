package com.botifier.timewaster.entity;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.util.Entity;

public class ShotgunPattern extends BulletPattern {
	int shots = 8;
	public ShotgunPattern() {
		fireSpeed = 300f;
		bulletSpeed = 1f;
	}
	
	@Override
	public void fire(Entity owner, float x, float y, float angle) throws SlickException {
		for (int i = 0; i < shots; i++) {
			owner.b.add(new Bullet("Bob", x, y, bulletSpeed, angle-0.2f*shots+(i*0.1f*shots), 1000));	
		}
	}

}
