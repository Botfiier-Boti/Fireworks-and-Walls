package com.botifier.timewaster.entity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.Math2;
import com.botifier.timewaster.util.bulletpatterns.BeeHivePattern;
import com.botifier.timewaster.util.bulletpatterns.ShotgunPattern;
import com.botifier.timewaster.util.movements.EnemyController;

public class BigGoblin extends Enemy {
	ShotgunPattern sp;
	BeeHivePattern bp;
	long cooldown = 0;
	long beecooldown = 1000;
	public BigGoblin(float x, float y) throws SlickException {
		super("Big Goblin", MainGame.getImage("BigGobboIdle"), new EnemyController(x, y, 35, 0.25f, 70), new SpriteSheet( MainGame.getImage("BigGobboWalk"), 16,16),new SpriteSheet( MainGame.getImage("BigGobboAttack"), 16,16),0.5f);
		solid = false;
		maxhealth = 2500;
		health = maxhealth;
		obstacle = false;
		def = 40;
		size = 2f;
		spawncap = 1;
		sp = new ShotgunPattern();
		bp = new BeeHivePattern();
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		Entity cls = null;
		for (int i = MainGame.getEntities().size()-1; i > -1; i--) {
			Entity en = MainGame.getEntities().get(i);
			if (en instanceof Bullet || en.isInvincible() || en == this || en.team == team || en.invulnerable == true || en.active == false || en.visible == false || getLocation().distance(en.getLocation()) > influence.radius)
				continue;
			if (cls == null)
				cls = en;
			if (getLocation().distance(en.getLocation()) < getLocation().distance(cls.getLocation())) {
				cls = en;
			}
		}
		if (cooldown <= 0) {
			if (cls != null) {
				getController().dash(cls.getLocation().getX(), cls.getLocation().getY());
				try {
					float angle = Math2.calcAngle(getController().src,cls.getLocation());
					sp.fire(this, getController().getLoc().getX(), getController().getLoc().getY(), angle);
					cooldown = (long)(60/3.5f*fireSpeed);
					attacking = true;
					return;
				} catch (SlickException e) {
					e.printStackTrace();
				}
			} else if (cls == null && attacking == true){
				attacking = false;
			}
		}
		if (beecooldown <= 0) {
			if (cls != null) {
				if (cls.getController().getDir() != null)
					bp.fire(this, cls.getLocation().getX()+cls.getController().getDir().x*20, cls.getLocation().getY()+cls.getController().getDir().y*20, 0);
				else 
					bp.fire(this, cls.getLocation().getX(), cls.getLocation().getY(), 0);
				
				beecooldown = 10000;
			}
		}
		getController().wander(false, 0.5f);
		beecooldown -= delta;
		cooldown--;
	}
	
}
