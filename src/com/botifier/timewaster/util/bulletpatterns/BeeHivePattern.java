package com.botifier.timewaster.util.bulletpatterns;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.entity.Beehive;
import com.botifier.timewaster.util.Entity;

public class BeeHivePattern extends BulletPattern {
	
	public BeeHivePattern() {
		fireSpeed = 0.2f;
	}
	
	@Override
	public void fire(Entity owner, float x, float y, float angle) throws SlickException {
		owner.b.add(new Beehive(owner.getLocation().getX(), owner.getLocation().getY(), new Vector2f(x,y), owner));
	}

}
