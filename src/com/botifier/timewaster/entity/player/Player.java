package com.botifier.timewaster.entity.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.entity.*;
import com.botifier.timewaster.main.MainGame;
//import com.botifier.timewaster.entity.ShotgunPattern;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;
import com.botifier.timewaster.util.Team;
import com.botifier.timewaster.util.bulletpatterns.*;
import com.botifier.timewaster.util.movements.LocalPlayerControl;

public class Player extends Entity {
	Sound s;
	public boolean build = false;
	public int dex = 50;
	float SPS = 0;
	float cooldown = 0;
	public long invulPeriod = 0;
	BulletPattern p;
	
	public Player(String name, float x, float y) throws SlickException {
		super(name, MainGame.getImage("debugman"), new LocalPlayerControl(x, y, 50f));
		s = MainGame.getSound("bladeswing");
		getController().setCollision(true);
		team = Team.ALLY;
		overrideMove = true;
		def = 20;
		vit = 75;
		spawncap = 3;
		//p = new BeeHivePattern();
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();
		if (health <= 0) {
			active = false;
		}
		if (spawns.size() > 0 && spawns.size() > spawncap) {
			spawns.get(0).destroy = true;
			spawns.remove(0);
			MainGame.mm.e.remove(spawns.get(0));
		}
		for (int w = spawns.size()-1; w > -1; w--) {
			Entity e = spawns.get(w);
			if (!MainGame.getEntities().contains(e) && e.destroy != true)
				MainGame.getEntities().add(e);
			if (e.team != this.team)
				e.team = this.team;
		}
		if (this.active) {
			SPS = 1.5f + 6.5f*(dex/75f);
			if (p != null)
				SPS *= p.getFireSpeed();
			getController().control(i);
			getController().move(delta);
			
			hitbox.setCenterX(getLocation().getX());
			hitbox.setY(getLocation().getY()-hitbox.getHeight());
			collisionbox.setCenterX(getLocation().getX());
			collisionbox.setY(getLocation().getY()-collisionbox.getHeight()+2);
			influence.setCenterX(getLocation().getX());
			influence.setCenterY(getLocation().getY());
			/*if(i.isKeyPressed(Input.KEY_B)) {
				build = !build;
			}*/
			if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && build == false) {
				if (cooldown <= 0) {
					Vector2f mouse = new Vector2f(i.getMouseX(), i.getMouseY());
					float angle = Math2.calcAngle(getController().getLoc(), mouse);
					if (p == null) {
						b.add(new Bullet("Bob", getController().getLoc().x, getController().getLoc().y, 100, angle, 25, 45, 90,this));
					} else {
						p.fire(this, mouse.x, mouse.y, angle);
					}
					if ((angle < Math.PI && angle > Math.PI/2) || (angle > -Math.PI && angle < -Math.PI/2)) {
						dir = false;
					} else {
						dir = true;
					}
					s.play(1, 0.5f);
					cooldown = 60/SPS;
				}
			}
			cooldown--;
			if (health < maxhealth) {
				health += 0.1f+0.005f*vit;
			} else if (health <= 0) {
				active = false;
				invincible = true;
				return;
			}
			
			if (invulPeriod > 0) {
				if (invincible == false)
					invincible = true;
				invulPeriod--;
			} else {
				if (invincible == true) {
					invincible = false;
				}
			}
		}
		//JOptionPane.
	}

	@Override
	public LocalPlayerControl getController() {
		return (LocalPlayerControl)super.getController();
	}
}
