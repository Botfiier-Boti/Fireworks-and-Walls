package com.botifier.timewaster.util.bulletpatterns;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;

public class ExplodePattern extends BulletPattern {

	public ExplodePattern() {
		fireSpeed = 1f;
		bulletSpeed = 20f;
		duration = 500;
		shots = 48;
		spread=20f;
		mindamage = 300;
		atkScaling = false;
		override = MainGame.getImage("FakeIce").copy();
		override.setImageColor(0.55f, 0.55f, 0.55f);
	}

	@Override
	public void fire(Entity owner, float x, float y, float angle) throws SlickException {
		for (int i = 0; i < shots; i++) {
			owner.b.add(createBullet(owner, owner.getLocation().getX(), owner.getLocation().getY(), 0, i));	
		}
	}

}
