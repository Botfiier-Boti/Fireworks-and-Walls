package com.botifier.timewaster.entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.BulletController;

public class Bullet extends Entity {

	public Bullet(String name, float x, float y, float speed, float angle, long lifeTime) throws SlickException {
		super(name, new Image("Shots.png"), new BulletController(x, y, speed, lifeTime, angle));
		rotation = (float) Math.toDegrees(angle);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}

}
