package com.botifier.timewaster.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.main.MainGame;

public class Camera {
	
	public Entity centerE;
	
	public Vector2f center;
	
	public Rectangle r;
	
	public float cx, cy;
	
	public Camera(float width, float height) {
		r = new Rectangle(0,0,width+4,height+4);
		center = new Vector2f();
	}
	
	public void update(GameContainer gc) {
		if (centerE != null) {
			r.setCenterX(centerE.getLocation().x-2);
			r.setCenterY(centerE.getLocation().y-2);
		}
	}
	
	public void draw(GameContainer gc, Graphics g) {
		Input in = gc.getInput();
		g.setDrawMode(Graphics.MODE_NORMAL);
		in.setScale(1/MainGame.camRatio, 1/MainGame.camRatio);
		g.scale(MainGame.camRatio, MainGame.camRatio);
		if (centerE == null) {
			g.translate(-center.getX(), -center.getY());
		} else {
			cx = getWidth()/2 - centerE.getLocation().getX();
			cy = getHeight()/2 - centerE.getLocation().getY();
			in.setOffset(-cx, -cy);
			g.translate(cx, cy);
		}
		g.setWorldClip(r);
	}
	
	public void setCenterEntity(Entity e) {
		this.centerE = e;
	}

	public float getWidth() {
		return r.getWidth();
	}
	
	public float getHeight() {
		return r.getHeight();
	}
	
}
