package com.botifier.timewaster.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.EntityController;

public class PopupText extends Entity {
	String text;
	Color c;
	
	public PopupText(String text, float x, float y, Color c) throws SlickException {
		super("Text", null, new EntityController(x, y, 10));
		getController().setDestination(x, y-20);
		getController().setCollision(false);
		this.text = text;
		this.c = c;
		solid = false;
		healthbarVisible = false;
		invincible = true;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (getController().isMoving() == false)
			destroy = true;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawString(text, getController().src.x, getController().src.y);
		g.setColor(Color.white);
	}
}
