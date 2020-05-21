package com.botifier.timewaster.main;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import com.botifier.timewaster.entity.*;
import com.botifier.timewaster.entity.player.Player;
import com.botifier.timewaster.util.Camera;
import com.botifier.timewaster.util.Entity;
import com.botifier.timewaster.util.TileMap;
import com.botifier.timewaster.util.movements.EntityController;

public class MainGame extends BasicGame{
	private static final String versionId = "0.0.3";
	public static MainGame mm;
	private static boolean loaded = false;
	public Entity dead;
	public ArrayList<Entity> e = new ArrayList<Entity>();
	public ArrayList<PopupText> pt = new ArrayList<PopupText>();
	public ArrayList<Entity> b = new ArrayList<Entity>();
	public HashMap<String, Image> i = new HashMap<String, Image>();
	public HashMap<String, Sound> so = new HashMap<String, Sound>();
	public TileMap m;
	public static boolean debug = false;
	AStarPathFinder aspf;
	Random r = new Random();
	Entity mtrack;
	Entity targeted = null;
	//Dungeon dg;
	public Player p;
	public Camera c;
	public static float camRatio = 2f;
	long ticks = 0;
	public int delta = 0;
	public char[][] tiles;
	Font f;
	TrueTypeFont ttf;
	TrueTypeFont ttfB;
	long cooldown = 100;
	long maxcooldown = 100000;
	public long natspawns = 0;
	
	public static ArrayList<String> s = new ArrayList<String>();
	
	public MainGame() {
		super("Time Waster v"+versionId);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//e.add(new Bob());
		f  = new Font("Verdana", Font.BOLD, 14);
		ttf  = new TrueTypeFont(f, true);
		ttfB = new TrueTypeFont(f.deriveFont(Font.BOLD,24), true);
		gc.getGraphics().setFont(ttf);
		loadImages();
		loadSounds();
		c = new Camera(gc.getWidth()/camRatio,gc.getHeight()/camRatio);
		p = new Player("George", 100, 100);
		dead = new Entity("The Dead",null,new EntityController(0,0,0));
		dead.visible = false;
		mtrack = new Entity("Mouse Tracker",null,new EntityController(0,0,0));
		mtrack.solid = false;
		mtrack.visible = false;
		mtrack.invulnerable = true;
		c.setCenterEntity(p);
		//e.add(new LobTest(100,100, new Vector2f(100,150), dead));
		for (int i = 0; i < 4; i++) {
			BigGoblin bg = new BigGoblin(245, 355);
			e.add(bg);
		}
		for (int i = 0; i < 2; i++) {
			e.add(new TestChest(50+(r.nextInt(300)),50+(r.nextInt(300))));
		}
		e.add(p);
		m = new TileMap(400, 400);
		tiles = new char[m.getWidthInTiles()][m.getHeightInTiles()];
		s.clear();
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				if (x != 0 && y != 0 && x != 1 && y != 1 && x < tiles[y].length-1 && x < tiles[y].length-2 && y < tiles.length-1 && y < tiles.length-2) {
					/*if ( x > 5 && x < tiles[y].length-5 && y > 5 && y < tiles.length-5 && r.nextInt(50) == 2) {
						tiles[y][x] = 'E';
					} else */
					tiles[y][x] = '#';
				}
			}
		}
		//dg = new Dungeon(20, 5, 5);
		//tiles = dg.tiles;
		//p.getLocation().set(dg.spawnRoom.getSpawn());
		debug = false;
		loaded = true;
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (g.isAntiAlias()) {
			g.setAntiAlias(false);
		}
		if (p.health <= 0) {
			g.setBackground(Color.black);
			String died = "YOU DIED";
			String con = "Press Space to Respawn.";
			g.setFont(ttfB);
			g.drawString(died,(gc.getWidth()/2)-ttf.getWidth(died)+6, (gc.getHeight()/2)-ttf.getHeight(died));
			g.setFont(ttf);
			g.drawString(con,(gc.getWidth()/2)-ttf.getWidth(con)/2, (gc.getHeight()/2)+ttf.getHeight(con));
			return;
		}
		c.draw(gc, g);
		dead.draw(g);
		for (int y = 0; y < m.getHeightInTiles(); y++) {
			for (int x = 0; x < m.getWidthInTiles(); x++) {
				char tile = tiles[y][x];
				switch (tile) {
					case '#':
						g.setColor(Color.lightGray);
						break;
					default:
						g.setColor(Color.darkGray);
						break;
				}
				g.fillRect(x*16, y*16, 16, 16);
			}
		}
		/*for (int i = 0; i < s.size(); i++) {
			for (int y = 0; y < s.get(0).length(); y ++) {
				if (s.get(i).length() > 0)
				if (s.get(i).charAt(y) == '#') {
					g.setColor(Color.darkGray);
					g.fillRect(y*16, i*16, 16, 16);
				} else if (s.get(i).charAt(y) == ' ') {
					g.setColor(Color.lightGray);
					g.fillRect(y*16, i*16, 16, 16);
				}
			}
		}*/
		Collections.sort(e);
		g.setColor(Color.black);
		if (b.size() > 0) {
			for (int e = 0; e < b.size(); e++) {
				Entity en = b.get(e);
				if (en != null && en.hasshadow)
					g.fillOval(en.getLocation().getX()-en.collisionbox.getWidth()/2, en.getLocation().getY()-en.collisionbox.getHeight(), en.collisionbox.getWidth(), en.collisionbox.getHeight()+1);
			}
		}
		for (int i = e.size()-1; i > -1; i--) {
			Entity en = e.get(i);
			if (en.active && en.visible && en.hasshadow)
				g.fillOval(en.getLocation().getX()-en.collisionbox.getWidth()/2, en.getLocation().getY()-en.collisionbox.getHeight(), en.collisionbox.getWidth(), en.collisionbox.getHeight()+1);
		}
		g.setColor(Color.white);
		for (int i = e.size()-1; i > -1; i--) {
			Entity en = e.get(i);
			en.draw(g);
		}
		if (b.size() > 0) {
			for (int e = 0; e < b.size(); e++) {
				Entity bu = b.get(e);
				if (bu != null)
					bu.draw(g);
			}
		}
		if (pt.size() > 0) {
			for (int e = 0; e < pt.size(); e++) {
				PopupText pu = pt.get(e);
				if (pu != null)
					pu.draw(g);
			}
		}
		if (p.build) {
			int x = gc.getInput().getMouseX()/16;
			int y = gc.getInput().getMouseY()/16;
			if (x < 0)
				x = 0;
			if (x > m.getWidthInTiles()-1)
				x = m.getWidthInTiles()-1;
			if (y < 0)
				y = 0;
			if (y > m.getHeightInTiles()-1)
				y = m.getHeightInTiles()-1;
			g.drawRect(x*16, y*16, 16, 16);	
		}
		g.setColor(Color.white);
		if (targeted != null && targeted.image != null) {
			g.draw(targeted.hitbox);
		}
		//dg.draw(g);
		g.resetTransform();
		g.drawString("Bullets: "+b.size(), 10, 24);
		g.drawString("Entities: "+e.size(), 10, 38);
		mtrack.getLocation().set(gc.getInput().getAbsoluteMouseX(), gc.getInput().getAbsoluteMouseY());

		g.drawString("Mouse Pos: "+mtrack.getLocation().x+","+mtrack.getLocation().y, 10, 52);
		if (targeted != null) {
			g.drawString("Targeted: "+targeted.getName(), 10, 66);
			g.drawString("Targeted Pos: "+(int)targeted.getLocation().x+","+(int)targeted.getLocation().y , 10, 80);
			g.drawString("Targeted Health: "+targeted.health+"/"+targeted.maxhealth , 10, 94);
			g.drawString("Targeted Atk: "+targeted.atk , 10, 108);
			g.drawString("Targeted Def: "+targeted.def , 10, 122);
			g.drawString("Influence: "+(int)targeted.influence.radius*2, 10, 136);
		}
	}

	public void handleEntities(GameContainer gc, int delta) throws SlickException {
		dead.update(delta);
		if (targeted != null && targeted.destroy) {
			targeted = null;
		}
		for (int ie = e.size()-1; ie >= 0; ie--) {
			Entity en = e.get(ie);

			if (en.destroy == true) {
				e.remove(en);
				if (en instanceof TestChest) {
					e.add(new TestChest(50+(r.nextInt(300)),50+(r.nextInt(300))));
				}
				continue;
			}
			
			if (en instanceof Bob) {
				Bob b = (Bob)en;
				if (b.p == null) {
					aspf = new AStarPathFinder(m, 1000, false);
					b.mod = true;
					b.p = aspf.findPath(null, (int)(b.getController().src.x/16), (int)(b.getController().src.y/16), (int)(Math.random()*30), (int)(Math.random()*30));
				}
			}
			if (en instanceof Player) {
				((Player)en).update(gc, delta);
			} else {
				en.update(delta);
			}
			if (c.centerE != null && c.centerE == mtrack) {
				if (en.hitbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						targeted = en;
					}
				}
			}
		}
		for (int ie = b.size()-1; ie >= 0; ie--) {
			Entity bu = b.get(ie);
			if (bu == null)
				continue;
			if (bu.destroy == true) {
				b.remove(bu);
				continue;
			}
			bu.update(delta);
		}
		for (int ie = pt.size()-1; ie >= 0; ie--) {
			PopupText pu = pt.get(ie);
			if (pu.destroy == true) {
				pt.remove(pu);
				continue;
			}
			pu.update(delta);
		}
		if (targeted != null) {
			if (gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				targeted = null;
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();
		if (p.health <= 0) {
			if (i.isKeyPressed(Input.KEY_SPACE)) {
				p.invulPeriod = 100;
				p.health = p.maxhealth;
				p.getController().getLoc().set(100, 100);
				p.active = true;
			}
			return;
		}
		this.delta = delta;
		if (p.health > 0) {
			c.update(gc);
			handleEntities(gc, delta);
			if (p.build) {
				if (i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					int x = Math.max(0, i.getMouseX()/16+1);
					int y = Math.max(0, i.getMouseY()/16);
					
					if (y < s.size() && y >= 0 && x < s.get(y).length() && x >= 1) {
						s.set(y,s.get(y).substring(0, x-1) + "#" + s.get(y).substring(x));
					}
				} else if (i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
					int x = i.getMouseX()/16+1;
					int y = i.getMouseY()/16;
					if (y < s.size() && y >= 0 && x < s.get(y).length() && x >= 1) {
						s.set(y,s.get(y).substring(0, x-1) + " " + s.get(y).substring(x));
					}
				}
			}
		}
		/*if (cooldown <= 0) {
			for (int y = 0; y < m.getHeightInTiles(); y++) {
				for (int x = 0; x < m.getWidthInTiles(); x++) {
					if (tiles[y][x] == 'E' && natspawns < 60) {
						BigGoblin bg = new BigGoblin(x*16,y*16);
						e.add(bg);
						natspawns++;
					}
				}
			}
			cooldown = maxcooldown;
		}
		cooldown-=delta;*/
		i.clearKeyPressedRecord();
	}
	
	public void loadImages() throws SlickException {
		i.put("biggobboidle", new Image("BigGobboIdle.png"));
		i.put("biggobbowalk", new Image("BigGobboWalk.png"));
		i.put("biggobboattack", new Image("BigGobboAttack.png"));
		i.put("fakeice", new Image("FakeIce.png"));
		i.put("defaultshot", new Image("Shots.png"));
		i.put("debugman", new Image("Testy.png"));
		i.put("purplebag", new Image("purplebag.png"));
		i.put("whitebag", new Image("whitebag.png"));
		i.put("testchest", new Image("testchest.png"));
		i.put("boomerang", new Image("boomerang.png"));
		i.put("bee", new Image("bee.png"));
		i.put("beehive", new Image("beehive.png"));
		Iterator<Entry<String, Image>> it = i.entrySet().iterator();
		while (it.hasNext()) {
			Image i = it.next().getValue();
			i.setFilter(Image.FILTER_NEAREST);
		}
	}
	
	public void loadSounds() throws SlickException {
		so.put("bladeswing", new Sound("bladeSwing.wav"));
		so.put("lootappears", new Sound("loot_appears.wav"));
		so.put("yalikejazz", new Sound("ya-like-jazz.wav"));
	}
	
	public static Image getImage(String name) {
		return mm.i.get(name.toLowerCase());
	}
	
	public static Sound getSound(String name) {
		return mm.so.get(name.toLowerCase());
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static ArrayList<Entity> getEntities() {
		return mm.e;
	}
	
	public static void spawnTempText(String s, float x, float y, Color c) throws SlickException {
		mm.pt.add(new PopupText(s, x, y, c));
	}
	
	public static void main(String[] args) throws SlickException {
		mm = new MainGame();
		AppGameContainer gc = new AppGameContainer(mm, 480, 480, false);
		gc.setTargetFrameRate(59);
		gc.setShowFPS(true);
		gc.setAlwaysRender(true);
		GameContainer.enableSharedContext();
		gc.start();
	}

}
