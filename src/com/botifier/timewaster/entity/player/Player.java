package com.botifier.timewaster.entity.player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.entity.Bullet;
import com.botifier.timewaster.entity.BulletPattern;
import com.botifier.timewaster.entity.ShotgunPattern;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;
import com.botifier.timewaster.util.movements.LocalPlayerControl;

public class Player extends Entity {
	public boolean build = false;
	public int dex = 75;
	float SPS = 0;
	float cooldown = 0;
	BulletPattern p;
	
	public Player(String name, float x, float y) throws SlickException {
		super(name, new Image("Testy.png"), new LocalPlayerControl(x, y, 125f));
		p = new ShotgunPattern();
	}
	
	public void update(Input i, int delta) throws SlickException {
		SPS = 1.5f + 6.5f*(dex/75f);
		if (p != null)
			SPS *= p.getFireSpeed();
		getController().move(delta);
		((LocalPlayerControl)getController()).control(i);
		for (int e = 0; e < b.size(); e++) {
			Bullet bu = b.get(e);
			bu.update(delta);
			if (bu.getController().isMoving() == false) {
				b.remove(bu);
				e--;
			}
		}

		if(i.isKeyPressed(Input.KEY_B)) {
			build = !build;
		}
		if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && build == false) {
			if (cooldown <= 0) {
				Vector2f mouse = new Vector2f(i.getMouseX(), i.getMouseY());
				float angle = Math2.calcAngle(getController().getLoc(), mouse);
				if (p == null) {
					b.add(new Bullet("Bob", getController().getLoc().x, getController().getLoc().y, 300, angle, 30));
				} else {
					p.fire(this, getController().getLoc().x, getController().getLoc().y, angle);
				}
				cooldown = 60/SPS;
			}
		}
		cooldown--;
		//JOptionPane.
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		for (int e = 0; e < b.size(); e++) {
			Bullet bu = b.get(e);
			bu.draw(g);
		}
	}

}
