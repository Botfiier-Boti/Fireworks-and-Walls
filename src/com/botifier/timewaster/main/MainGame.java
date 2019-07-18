package com.botifier.timewaster.main;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import com.botifier.timewaster.entity.Bob;
import com.botifier.timewaster.entity.player.Player;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.TileMap;

public class MainGame extends BasicGame{
	private static final String versionId = "0.0.1";
	public static MainGame mm;
	public ArrayList<Entity> e = new ArrayList<Entity>();
	public TileMap m;
	AStarPathFinder aspf;
	Player p;
	
	public static String[] s = {"##############################"
							   ,"##############################"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##                          ##"
	              			   ,"##############################"
	              			   ,"##############################"};
	
	public MainGame() {
		super("Time Waster v"+versionId);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.darkGray);
		for (int i = 0; i < 30; i++) {
			for (int y = 0; y < 30; y ++) {
				if (s[i].charAt(y) == '#') {
					g.fillRect(y*16, i*16, 16, 16);
				}
			}
		}
		g.setColor(Color.white);
		for (Entity e : e)
			e.draw(g);
		p.draw(g);

		g.drawString("Bullets: "+p.b.size(), 0, 14);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//e.add(new Bob());
		p = new Player("George", 100, 100);
		m = new TileMap(480/16, 480/16);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();

		for (Entity en : e) {
			if (en instanceof Bob) {
				Bob b = (Bob)en;
				if (b.p == null) {
					aspf = new AStarPathFinder(m, 1000, false);
					b.mod = true;
					b.p = aspf.findPath(null, (int)(b.getController().src.x/16), (int)(b.getController().src.y/16), (int)(Math.random()*30), (int)(Math.random()*30));
				}
			}
			en.update(delta);
		}
		if (p.build) {
			if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				int x = i.getMouseX()/16+1;
				int y = i.getMouseY()/16;
				if (x < 30 && y < 30 && x > 0 && y > 0) {
					s[y] = s[y].substring(0, x-1) + "#" + s[y].substring(x);
				}
			}
			if (i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				int x = i.getMouseX()/16+1;
				int y = i.getMouseY()/16;
				if (x < 30 && y < 30 && x > 0 && y > 0) {
					s[y] = s[y].substring(0, x-1) + " " + s[y].substring(x);
				}
			}
		}
		p.update(i, delta);
	}
	
	public static void main(String[] args) throws SlickException {
		mm = new MainGame();
		AppGameContainer gc = new AppGameContainer(mm, 480, 480, false);
		gc.setTargetFrameRate(59);
		gc.setAlwaysRender(true);
		gc.start();
	}

}
