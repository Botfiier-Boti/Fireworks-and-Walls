package com.botifier.timewaster.util.movements;

import org.newdawn.slick.Input;

import com.botifier.timewaster.main.MainGame;

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
		if (obeysCollision == true) {
			testMove(nx,ny);
		}
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
		MainGame m = MainGame.mm;
		/*Old code
		if (m.m.blocked(null, (int)(nx)/16, (int)(ny-PPS)/16) == true)
		{
			UP = false;
		}
		if (m.m.blocked(null, (int)(nx)/16, (int)(ny+PPS)/16) == true)
		{
			DOWN = false;
		}
		if (m.m.blocked(null, (int)(nx-PPS)/16, (int)(ny)/16) == true)
		{
			LEFT = false;
		}
		if (m.m.blocked(null, (int)(nx+PPS)/16, (int)(ny)/16) == true)
		{
			RIGHT = false;
		}*/
		
		//UP
		if ((((int)(getOwner().collisionbox.getMinX()-PPS)/16 >= 0) && ((int)(getOwner().collisionbox.getMaxX()+PPS)/16 < MainGame.mm.m.getWidthInTiles())) 
			&& (((int)(getOwner().collisionbox.getMinY()-PPS)/16 >= 0) && ((int)(getOwner().collisionbox.getMaxY()+PPS)/16 < MainGame.mm.m.getHeightInTiles()))) {
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMaxX())/16, (int)(getOwner().collisionbox.getMinY()-PPS)/16) == true)
			{
				UP = false;
			}
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMinX())/16, (int)(getOwner().collisionbox.getMinY()-PPS)/16) == true)
			{
				UP = false;
			}
			
			//DOWN
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMaxX())/16, (int)(getOwner().collisionbox.getMaxY()+PPS)/16) == true)
			{
				DOWN = false;
			}
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMinX())/16, (int)(getOwner().collisionbox.getMaxY()+PPS)/16) == true)
			{
				DOWN = false;
			}
			
			//LEFT
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMinX()-PPS)/16, (int)(getOwner().collisionbox.getMaxY())/16) == true)
			{
				LEFT = false;
			}
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMinX()-PPS)/16, (int)(getOwner().collisionbox.getMinY())/16) == true)
			{
				LEFT = false;
			}
			
			//RIGHT
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMaxX()+PPS)/16, (int)(getOwner().collisionbox.getMaxY())/16) == true)
			{
				RIGHT = false;
			}
			if (m.m.blocked(null, (int)(getOwner().collisionbox.getMaxX()+PPS)/16, (int)(getOwner().collisionbox.getMinY())/16) == true)
			{
				RIGHT = false;
			}
		}
		
	}
	
}
