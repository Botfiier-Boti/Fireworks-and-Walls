package com.botifier.timewaster.util.movements;

import org.newdawn.slick.Input;

import com.botifier.timewaster.main.MainGame;
import com.botifier.timewaster.util.EntityController;

public class LocalPlayerControl extends EntityController {
	boolean UP, DOWN, LEFT, RIGHT;
	public LocalPlayerControl(float x, float y, float speed) {
		super(x, y, speed);
	}
	
	public void control(Input i) {
		float nx = src.getX(), ny = src.getY();
		PPS = 0.6f + 1.5f*(speed/75f);
		if (i.isKeyDown(Input.KEY_W)) {
			UP = true;
		} else  {
			UP = false;
		}
		if (i.isKeyDown(Input.KEY_S)) {
			DOWN = true;
		} else {
			DOWN = false;
		}
		if (i.isKeyDown(Input.KEY_A)) {
			LEFT = true;
		} else {
			LEFT = false;
		}
		if (i.isKeyDown(Input.KEY_D)) {
			RIGHT = true;
		}  else {
			RIGHT = false;
		}
		if (obeysCollision == true)
			testMove(nx,ny);
		if (UP)
			ny -= PPS;
		if (DOWN)
			ny += PPS;
		if (LEFT)
			nx -= PPS;
		if (RIGHT)
			nx += PPS;
		if (nx > src.getX() || nx < src.getX() || ny > src.getY() || ny < src.getY()) {
			dst = null;
			setDestination(nx, ny); 
		}
	}
	
	public void testMove(float nx, float  ny) {
		if (MainGame.mm.m.blocked(null, (int)(nx)/16, (int)(ny-PPS)/16) == true)
		{
			UP = false;
		}
		if (MainGame.mm.m.blocked(null, (int)(nx)/16, (int)(ny+PPS)/16) == true)
		{
			DOWN = false;
		}
		if (MainGame.mm.m.blocked(null, (int)(nx-PPS)/16, (int)(ny)/16) == true)
		{
			LEFT = false;
		}
		if (MainGame.mm.m.blocked(null, (int)(nx+PPS)/16, (int)(ny)/16) == true)
		{
			RIGHT = false;
		}
	}
	
}
