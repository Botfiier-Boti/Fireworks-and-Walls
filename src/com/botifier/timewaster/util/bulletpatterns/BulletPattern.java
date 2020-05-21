package com.botifier.timewaster.util.bulletpatterns;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.botifier.timewaster.entity.Bullet;
import com.botifier.timewaster.util.Entity;

public abstract class BulletPattern {
	public float fireSpeed = 1.0f;
	float bulletSpeed = 300; 
	long duration;
	int shots = 1;
	float spread = 0;
	int mindamage = 10, maxdamage = 10;
	boolean enemyPierce = false;
	boolean obstaclePierce = false;
	boolean armorPierce = false;
	boolean atkScaling = true;
	boolean boomerang = false;
	public Image override = null;
	
	
	public abstract void fire(Entity owner, float x, float y, float angle) throws SlickException;
	
	public Bullet createBullet(Entity owner, float x, float y, float angle, int i) throws SlickException {
		if (owner.active == false)
			return null;
		double na = Math.toDegrees(angle);
		if (shots % 2 == 0) {
			if (i % 2 != 0) {
				na = Math.toDegrees(angle)-(spread+(i*spread/2));
			} else if (i%2 == 0) {
				na = Math.toDegrees(angle)+(spread+(i*spread/2));
			}
		} else {
			if (i % 2 != 0) {
				na = Math.toDegrees(angle)-spread-((i*spread));
			} else if (i%2 == 0) {
				na = Math.toDegrees(angle)+((i*spread));
			}
		}
		Bullet b = Bullet.createBullet("Bob", x, y, bulletSpeed, (float)Math.toRadians(na), duration, mindamage,maxdamage,owner, obstaclePierce, enemyPierce, boomerang);
		b.ignoresArmor = armorPierce;
		b.atkScaling = atkScaling;
		b.boomerang = boomerang;
		if (override != null)
			b.setImage(override);
		return b;
	}
	
	public float getFireSpeed() {
		return fireSpeed;
	}
	
	public float getBulletSpeed() {
		return bulletSpeed;
	}
	
}
