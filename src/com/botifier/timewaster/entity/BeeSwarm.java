package com.botifier.timewaster.entity;

import org.newdawn.slick.SlickException;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.movements.EnemyController;

public class BeeSwarm extends Enemy{
	
	public BeeSwarm(float x, float y, int size) {
		super("Bee Swarm", MainGame.getImage("bee"), new EnemyController(x,y,50,0.5f,50), null, null);
		visible = false;
		iModifier = 4f;
		maxhealth = 1;
		spawncap = size;
		for (int i = 0; i < size; i++) {
			Bee b = new Bee(x,y);
			b.team = this.team;
			b.o = this;
			spawns.add(b);
		}
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (spawns.size() > 0) {
			Entity cls = null;
			for (int i = MainGame.getEntities().size()-1; i > -1; i--) {
				Entity en = MainGame.getEntities().get(i);
				if (en instanceof Bullet || en instanceof Bee || en.isInvincible() || en == this || en.team == team || en.invulnerable == true || en.active == false || en.visible == false || getLocation().distance(en.getLocation()) > influence.radius)
					continue;
				if (cls == null)
					cls = en;
				if (getLocation().distance(en.getLocation()) < getLocation().distance(cls.getLocation())) {
					cls = en;
				}
			}
			if (cls != null) {
				getController().dash(cls.getLocation().getX(), cls.getLocation().getY());
			}
			getController().wander(false, 1.5f);
			for (int i = spawns.size()-1; i > -1; i--) {
				Bee b = (Bee) spawns.get(i);
				if (b.destroy == true) {
					spawns.remove(b);
					continue;
				}
				if (b.team != this.team)
					b.team = this.team;
				if (!MainGame.getEntities().contains(b) && b.destroy != true)
					MainGame.getEntities().add(b);
				if (b.getController().wanderArea != this.influence) {
					b.getController().wanderArea = this.influence;
				}
			}
		} else {
			destroy = true;
		}
	}

	@Override
	public EnemyController getController() {
		return (EnemyController) super.getController();
	}


}
