package com.botifier.timewaster.entity;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.util.Entity;

public abstract class BulletPattern {
	float fireSpeed = 1.0f;
	float bulletSpeed = 300; 
	
	public abstract void fire(Entity owner, float x, float y, float angle) throws SlickException;
	
	public float getFireSpeed() {
		return fireSpeed;
	}
	
	public float getBulletSpeed() {
		return bulletSpeed;
	}
	
}
