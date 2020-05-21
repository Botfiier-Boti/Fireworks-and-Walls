package com.botifier.timewaster.util.movements;

import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.util.Entity;

public class EnemyController extends EntityController {
	long paitence = 1000;
	Random r = new Random();
	float wanderspeed = 10f;
	float runspeed = 35f;
	long wanderCooldown = 0;
	long cooldown = 0;
	public Circle wanderArea = null;
	
	public EnemyController(float x, float y, float speed, float wanderMod, long wanderCooldown) {
		super(x, y, speed);
		this.obeysCollision = true;
		this.runspeed = speed;
		this.wanderspeed = speed * wanderMod;
		this.wanderCooldown = wanderCooldown;
	}

	public void wander(boolean force, float rangeMult) {
		Entity e = owner;
		float rad = 0;
		int nx = 0, ny = 0;
		double theta =  (Math.random()*2*Math.PI);
		if (wanderArea != null) {
			rad =(float) ((wanderArea.getRadius()*rangeMult)*Math.sqrt(Math.random()));
			nx = (int) (wanderArea.getCenterX() + rad * Math.cos(theta));
			ny = (int) (wanderArea.getCenterY() + rad * Math.sin(theta));
		} else {
			rad = (float) ((e.influence.getRadius()*rangeMult)*Math.sqrt(Math.random()));
			nx = (int) (e.influence.getCenterX() + rad * Math.cos(theta));
			ny = (int) (e.influence.getCenterY() + rad * Math.sin(theta));
		}
		
		if (force) {
			if (speed != wanderspeed) {
				speed = wanderspeed;
			}
			dst = null;
			setDestination(nx, ny); 
			return;
		} else if (moving == false && cooldown <= 0) {
			if (nx > src.getX() || nx < src.getX() || ny > src.getY() || ny < src.getY()) {
				Vector2f v = new Vector2f(nx, ny);

				if (speed != wanderspeed) {
					speed = wanderspeed;
				}
				if (testMapCollision(v) == true || testEntityCollision(v) == true) {
					return;
				}
				dst = null;
				setDestination(nx, ny); 
				cooldown = wanderCooldown;
			}
		}else if (cooldown > 0)
			cooldown--;
		
	}
	
	public void dash(float nx, float ny) {
		if (speed != runspeed) {
			speed = runspeed;
		}
		Vector2f v = new Vector2f(nx, ny);
		if (testMapCollision(v) == true || testEntityCollision(v) == true) {
			return;
		}
		dst = null;
		setDestination(nx, ny); 
	}
	
}
