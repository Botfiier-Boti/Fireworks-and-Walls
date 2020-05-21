package com.botifier.timewaster.entity;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.EnemyController;

public abstract class LobbedProjectile extends Entity {
	Vector2f start;
	Vector2f posMod;
	float tdist = 0;
	float cThrow = 0;
	protected float mult = 1f;
	
	public LobbedProjectile(String name, Image i, EnemyController controller, Vector2f dst, Entity o) {
		super(name, i, controller);
		this.o = o;
		controller.setCollision(false);
		controller.dst = dst;
		maxhealth = 1;
		healthbarVisible = false;
		invulnerable = true;
		start = controller.getLoc().copy();
		posMod = new Vector2f(0,0);
		tdist = start.distance(dst);	
	}

	public abstract void onLand() throws SlickException;
	
	@Override
	public void onDeath() throws SlickException {
		super.onDeath();
		onLand();
	}
	
	@Override
	public void update(int delta) throws SlickException {
		getController().move(delta);
		if (getController().getDst() != null) {
			float dist = getLocation().distance(getController().getDst());	
			if (dist > tdist/2) {
				posMod.y -= getController().getPPS()*mult;
				mult -= 6*getController().getPPS()*(float) ((dist-(tdist/2))/tdist);
			} else if (dist < tdist/2) {
				posMod.y += getController().getPPS()*mult;
				mult += 6*getController().getPPS()*(float) ((dist-(tdist/2))/tdist);
			}
			if (mult < 0) {
				mult = -mult;
			}
			if (posMod.y > 0)
				posMod.y = 0;
		}
		if (getController().isMoving() == false)
			onDeath();
	}
	
	@Override
	public void draw(Graphics g) {
		if (image != null && getController().getDst() != null) {
			Image y = image;
			Vector2f iLoc = getLocation().copy();
			iLoc.add(posMod);
			y.setCenterOfRotation(y.getWidth()/2, y.getHeight()/2);
			y.setRotation(rotation);
			g.drawImage(y, iLoc.getX()-y.getWidth()/2, iLoc.getY()-y.getHeight());
		}
	}
	
	
}
