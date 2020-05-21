package com.botifier.timewaster.entity;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.bulletpatterns.ExplodePattern;
import com.botifier.timewaster.util.bulletpatterns.SpherePattern;
import com.botifier.timewaster.util.movements.EnemyController;

public class IceSphereClone extends Enemy {
	SpherePattern sp;
	ExplodePattern ep;
	long cooldown = 0;
	long dashcooldown;

	public IceSphereClone(float x, float y) throws SlickException {
		super("Ice Sphere", MainGame.getImage("FakeIce"), new EnemyController(x, y, 100f, 0.1f, 150), null, null,2f);
		sp = new SpherePattern();
		ep = new ExplodePattern();
		def = 20;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (dashcooldown <= 0) {
			Entity cls = null;
			for (int i = MainGame.getEntities().size()-1; i > -1; i--) {
				Entity en = MainGame.getEntities().get(i);
				if (en instanceof Bullet || en.isInvincible() || en == this || en.team == team || en.invulnerable == true || en.active == false || en.visible == false || getLocation().distance(en.getLocation()) > influence.radius)
					continue;
				if (cls == null)
					cls = en;
				if (getLocation().distance(en.getLocation()) < getLocation().distance(cls.getLocation())) {
					cls = en;
				}
			}
			if (cls != null) {
				getController().dash(cls.hitbox.getCenterX(), cls.hitbox.getCenterY());
				dashcooldown = 200;
			} else {
				getController().wander(false,0.5f);
			}
		} else {
			getController().wander(false,0.5f);
		}
		if (cooldown <= 0) {
			try {
				sp.fire(this, getController().getLoc().getX(), getController().getLoc().getY(), 0);
				cooldown = (long)(60/3.5f*sp.fireSpeed);
				return;
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		dashcooldown--;
		cooldown--;
	}
	
	@Override
	public void onDeath() {
		if (active) {
			try {
				ep.fire(MainGame.mm.dead, getController().getLoc().getX(), getController().getLoc().getY(), 0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			active = false;
		}
		destroy = true;
	}

}
