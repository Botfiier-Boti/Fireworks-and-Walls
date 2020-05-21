package com.botifier.timewaster.entity;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;
import com.botifier.timewaster.util.movements.EnemyController;

public class Bee extends Enemy {
	Sound s;
	long cooldown = 0;

	public Bee(float x, float y) {
		super("Bee", MainGame.getImage("Bee"), new EnemyController(x,y, 50, 1f, 0), null, null);
		s = MainGame.getSound("yalikejazz");
		maxhealth = 50;
		fireSpeed = 1f;
		linger = false;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (cooldown <= 0) {
			Entity cls = null;
			for (int i = MainGame.getEntities().size()-1; i > -1; i--) {
				Entity en = MainGame.getEntities().get(i);
				if (en instanceof Bullet || en instanceof Bee || en.isInvincible() || en == this || en.team == team || en.invulnerable == true || en.active == false || en.visible == false || getLocation().distance(en.getLocation()) > influence.radius)
					continue;
				if (cls == null)
					cls = en;
				if (getLocation().distance(en.getLocation()) < getLocation().distance(cls.getLocation())) {
					cls = en;
				}
			}
			if (cls != null) {
				try {
					float angle = Math2.calcAngle(getController().src,cls.getLocation());
					b.add(new Bullet("Bob", getController().getLoc().x, getController().getLoc().y, 100, angle, 25, 10, 10,this));
					cooldown = (long)(100-60/3.5f*fireSpeed*Math.random());
					attacking = true;
					return;
				} catch (SlickException e) {
					e.printStackTrace();
				}
			} else if (cls == null && attacking == true){
				attacking = false;
			}
		}

		health -= 0.1f;
		getController().wander(false, 0.5f);
		cooldown--;
	}
	
	@Override
	public void onHit(Bullet b) {
		super.onHit(b);
	}


}
