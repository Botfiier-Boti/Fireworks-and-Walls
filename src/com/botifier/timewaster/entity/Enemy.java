package com.botifier.timewaster.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;
import com.botifier.timewaster.util.Team;
import com.botifier.timewaster.util.movements.EnemyController;

public class Enemy extends Entity {
	Animation aWalk;
	Animation aAttack;
	SpriteSheet walk;
	SpriteSheet attack;
	float fireSpeed = 0.95f;
	boolean attacking = false;	
	
	public Enemy(String name, Image i, EnemyController controller, SpriteSheet walk, SpriteSheet attack) {
		super(name, i, controller);
		this.walk = walk;
		this.attack = attack;
		if (walk != null) {
			aWalk = new Animation(walk, (int)(300-(Math.max(0.6f + 1.5f*(getController().speed/75f), 1))));

			aWalk.start();
		}
		if (attack != null) {
			aAttack = new Animation(attack,(int)(200-(Math.max(60/3.5f*fireSpeed, 1))));
			aAttack.start();
		}
		team = Team.ENEMY;
	}
	
	public Enemy(String name, Image i, EnemyController controller, SpriteSheet walk, SpriteSheet attack, float imod) {
		super(name, i, controller, imod);
		this.walk = walk;
		this.attack = attack;
		if (walk != null) {
			aWalk = new Animation(walk, (int)(300-(Math.max(0.6f + 1.5f*(getController().speed/75f), 1))));

			aWalk.start();
		}
		if (attack != null) {
			aAttack = new Animation(attack,(int)(200-(Math.max(60/3.5f*fireSpeed, 1))));
			aAttack.start();
		}
		team = Team.ENEMY;
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (aAttack != null && attacking) {
			aAttack.update(delta);
		}
		if (aWalk != null && getController().isMoving()) {
			aWalk.update(delta);
		} 
		if (getController().dst != null && getController().isMoving()) {
			float angle = (float) Math.toDegrees(Math2.calcAngle(getController().src,getController().dst));	
			if ((angle <= 180 && angle >= 90) || (angle >= -180 && angle < -90)) {
				dir = true;
			} else {
				dir = false;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (visible == true && active == true) {
			
			if (image != null) {
				if (attacking && aAttack != null) {
					Image i = aAttack.getCurrentFrame().getScaledCopy(size);
					image.setCenterOfRotation(i.getWidth()/2, i.getHeight()/2);
					image.setRotation(rotation);
					if (dir == false) {
						g.drawImage(i, getController().src.getX()-i.getWidth()/2, getController().src.getY()-i.getHeight());
					} else {
						g.drawImage(i.getFlippedCopy(true, false), getController().src.getX()-i.getWidth()/2, getController().src.getY()+-i.getHeight());
					}
				} else if (getController().isMoving() && aWalk != null) {
					Image i = aWalk.getCurrentFrame().getScaledCopy(size);
					image.setCenterOfRotation(i.getWidth()/2, i.getHeight()/2);
					image.setRotation(rotation);
					if (dir == false) {
						g.drawImage(i, getController().src.getX()-i.getWidth()/2, getController().src.getY()-i.getHeight());
					} else {
						g.drawImage(i.getFlippedCopy(true, false), getController().src.getX()-i.getWidth()/2, getController().src.getY()+-i.getHeight());
					}
				} else {
					image.setCenterOfRotation(image.getWidth()/2, image.getHeight()/2);
					image.setRotation(rotation);
					if (dir == false) {
						g.drawImage(image, getController().src.getX()-image.getWidth()/2, getController().src.getY()-image.getHeight());
					} else {
						g.drawImage(image.getFlippedCopy(true, false), getController().src.getX()-image.getWidth()/2, getController().src.getY()+-image.getHeight());
					}
				}
			}
			else
				g.drawString(getName().substring(0, 1), getController().src.getX(), getController().src.getY());
			
			if (MainGame.debug) {
				g.draw(influence);
				g.draw(hitbox);
				if (getController().dst != null)
					g.drawLine(getController().src.getX(), getController().src.getY(), getController().dst.getX(), getController().dst.getY());
			}
			if (healthbarVisible) {
				g.setColor(Color.red);
				g.fillRect(hitbox.getMinX()-1, hitbox.getMaxY()+1, hitbox.getWidth()+2, 3);
				g.setColor(Color.green);
				g.fillRect(hitbox.getMinX()-1, hitbox.getMaxY()+1, (hitbox.getWidth()+2)*(Math.max(health, 0)/maxhealth), 3);
				g.setColor(Color.black);
				g.drawRect(hitbox.getMinX()-1, hitbox.getMaxY()+1, hitbox.getWidth()+2, 3);
				g.setColor(Color.white);
			}
		}
	}

	@Override
	public EnemyController getController() {
		return (EnemyController) super.getController();
	}
}
