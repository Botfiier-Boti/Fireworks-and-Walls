package com.botifier.timewaster.util.movements;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;

public class EntityController {
	public Vector2f src;
	public Vector2f dst;
	public float speed;
	protected float PPS;
	protected boolean moving = false;
	protected boolean obeysCollision = true;
	protected Entity owner;
	protected Rectangle testBox = new Rectangle(0,0,0,0);
	protected float angle = 0;
	protected Vector2f dir;
	
	public EntityController(float x, float y, float speed) {
		src = new Vector2f(x, y);
		this.speed = Math2.round(speed, 2);
	}
	
	public void move(int delta) {
		boolean eC = false, mC = false;
		PPS = 0.6f + 1.5f*(speed/75f);
		if (dst == null)
			return;
		float angle = Math2.calcAngle(src,dst);
		this.angle = angle;

		// make the object "snap" to the destination if it's close enough (to avoid vibrating)
		if (Math.round(src.getX()) == Math.round(dst.getX())) {
			src.x = dst.getX();
		}
		if (Math.round(src.getY()) == Math.round(dst.getY())) {
			src.y = dst.getY();
		}
		Vector2f spd = new Vector2f(0, 0);
		// move the object
		if (src.getX() != dst.getX())
			spd.x = Math2.round((float) (Math.cos(angle)*(PPS)), 1);
		if (src.getY() != dst.getY())
			spd.y = Math2.round((float) (Math.sin(angle)*(PPS)), 1);
		if (obeysCollision) {
			eC = testEntityCollision(dst);
			mC = testMapCollision(dst);
			if (eC || mC) {
				dst = null;
				moving = false;
				return;
			}
		}
		this.dir = spd;
		src.add(spd);
		if (src.distance(dst) < PPS) {
			if (obeysCollision) {
				eC = testEntityCollision(dst);
				mC = testMapCollision(dst);
				if (eC || mC) {
					dst = null;
					moving = false;
					return;
				} else {
					src = dst;
					dst = null;
					moving = false;
				}
			} else {
				src = dst;
				dst = null;
				moving = false;
			}
		} else {
			moving = true;
		}
		
	}
	
	public boolean testEntityCollision(Vector2f dst) {
		if (dst == null)
			return false;
		ArrayList<Entity> test = MainGame.getEntities();
		float angle = Math2.calcAngle(src,dst);
		Vector2f spd = new Vector2f(0, 0);

		Vector2f fake = src.copy();
		if (fake.getX() != dst.getX())
			spd.x = Math2.round((float) (Math.cos(angle)*(PPS)), 1);
		if (fake.getY() != dst.getY())
			spd.y = Math2.round((float) (Math.sin(angle)*(PPS)), 1);
		fake.add(spd);
		if (owner.image != null)
			testBox.setBounds(fake.getX()-owner.image.getWidth()/2,fake.getY()-owner.collisionbox.getHeight(),owner.collisionbox.getWidth(),owner.collisionbox.getHeight());
		else 
			testBox.setBounds(fake.getX()-owner.collisionbox.getWidth()/2,fake.getY()-owner.collisionbox.getHeight(),owner.collisionbox.getWidth(),owner.collisionbox.getHeight());
		for (int i = test.size()-1; i >= 0; i--) {
			Entity en = test.get(i);
			if (en.solid != true || en.obstacle != true || en == getOwner())
				continue;
			if (owner.influence.intersects(en.collisionbox)) {
				if (testBox.intersects(en.collisionbox)) {
					dst = null;
					moving = false;
					return true;
				}
			} else
				continue;
		}
		return false;
	}
	
	public boolean testAllEntityCollision(Vector2f dst) {
		if (dst == null)
			return false;
		ArrayList<Entity> test = MainGame.getEntities();
		float angle = Math2.calcAngle(src,dst);
		Vector2f spd = new Vector2f(0, 0);

		Vector2f fake = src.copy();
		if (fake.getX() != dst.getX())
			spd.x = Math2.round((float) (Math.cos(angle)*(PPS)), 1);
		if (fake.getY() != dst.getY())
			spd.y = Math2.round((float) (Math.sin(angle)*(PPS)), 1);
		fake.add(spd);
		if (owner.image != null)
			testBox.setBounds(fake.getX()-owner.image.getWidth()/2,fake.getY()-owner.collisionbox.getHeight(),owner.collisionbox.getWidth(),owner.collisionbox.getHeight());
		else 
			testBox.setBounds(fake.getX()-owner.collisionbox.getWidth()/2,fake.getY()-owner.collisionbox.getHeight(),owner.collisionbox.getWidth(),owner.collisionbox.getHeight());
		for (int i = test.size()-1; i >= 0; i--) {
			Entity en = test.get(i);
			if (en == getOwner())
				continue;
			if (owner.influence.intersects(en.collisionbox)) {
				if (testBox.intersects(en.collisionbox)) {
					return true;
				}
			} else
				continue;
		}
		return false;
	}
	
	public boolean testMapCollision(Vector2f dst) {
		float angle = Math2.calcAngle(src,dst);
		Vector2f spd = new Vector2f(0, 0);

		Vector2f fake = src.copy();
		if (fake.getX() != dst.getX())
			spd.x = Math2.round((float) (Math.cos(angle)*(PPS)), 1);
		if (fake.getY() != dst.getY())
			spd.y = Math2.round((float) (Math.sin(angle)*(PPS)), 1);
		fake.add(spd);
		testBox.setBounds(fake.getX()-owner.collisionbox.getWidth()/2,fake.getY()-owner.collisionbox.getHeight(),owner.collisionbox.getWidth(),owner.collisionbox.getHeight());
		for (int i = 0; i < testBox.getPointCount(); i++) {
			float[] point = testBox.getPoint(i);
			if (((int)point[0]/16>=0 && point[0]/16<MainGame.mm.m.getWidthInTiles()) && (((int)point[1]/16>=0) && (int)point[1]/16<MainGame.mm.m.getHeightInTiles())) {
				if (MainGame.mm.m.blocked(null, (int)point[0]/16, (int)point[1]/16)) {
					moving = false;
					this.dst = null;
					return true;
				}
			} else {
				moving = false;
				this.dst = null;
				return true;
			}
			continue;
		}
		return false;
	}
	
	public Vector2f getLoc() {
		return src;
	}
	
	public Vector2f getDst() {
		return dst;
	}
	
	public Vector2f getDir() {
		return dir.copy();
	}
	
	public float getPPS() {
		return PPS;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setDestination(float x, float y) {
		if (x == src.getX() && y == src.getY())
			return;
		dst = new Vector2f(Math2.round(x, 2), Math2.round(y, 2));
		moving = true;
	}
	
	public void setCollision(boolean c) {
		this.obeysCollision = c;
	}
	
	public boolean obeysCollision() {
		return this.obeysCollision;
	}
	
	public boolean isMoving() {
		return moving;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}
