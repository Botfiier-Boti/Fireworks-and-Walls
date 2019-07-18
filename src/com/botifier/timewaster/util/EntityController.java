package com.botifier.timewaster.util;

import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.util.Math2;

public class EntityController {
	public Vector2f src;
	public Vector2f dst;
	public float speed;
	protected float PPS;
	protected boolean moving = false;
	protected boolean obeysCollision = true;
	
	public EntityController(float x, float y, float speed) {
		src = new Vector2f(x, y);
		this.speed = Math2.round(speed, 2);
	}
	
	public void move(int delta) {

		PPS = 0.6f + 1.5f*(speed/75f);
		if (dst == null)
			return;
		float angle = Math2.calcAngle(src,dst);

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
		src.add(spd);
		if (src.distance(dst) < PPS) {
			src = dst;
			dst = null;
		}
		
	}
	
	public Vector2f getLoc() {
		return src;
	}
	
	public Vector2f getDst() {
		return dst;
	}
	
	public float getPPS() {
		return PPS;
	}
	
	public void setDestination(float x, float y) {
		if (x == src.getX() && y == src.getY())
			return;
		dst = new Vector2f(Math2.round(x, 2), Math2.round(y, 2));
		moving = true;
	}
	
	public boolean isMoving() {
		return moving;
	}
}
