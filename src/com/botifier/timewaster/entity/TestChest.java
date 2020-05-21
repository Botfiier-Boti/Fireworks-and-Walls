package com.botifier.timewaster.entity;

import java.util.Random;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.EntityController;

public class TestChest extends Entity {
	Random r = new Random();

	public TestChest(float x, float y) throws SlickException {
		super("Test Chest", MainGame.getImage("TestChest"), new EntityController(x, y, 0), 0f);
		solid = true;
		maxhealth = 2500;
		health = maxhealth;
		size = 1f;
	}
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (health <= 0) {
			
		}
	}
	
	@Override
	public void onDeath() throws SlickException {
		super.onDeath();
		int i = r.nextInt(300);
		if (i == 26) {
			try {
				MainGame.mm.e.add(new FakeBagEntity(getController().src.x,getController().src.y, 1));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (i > 26 || i < 100) {
			try {
				MainGame.mm.e.add(new FakeBagEntity(getController().src.x,getController().src.y, 0));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
