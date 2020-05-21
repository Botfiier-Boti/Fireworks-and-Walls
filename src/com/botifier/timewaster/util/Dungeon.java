package com.botifier.timewaster.util;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.botifier.timewaster.main.MainGame;

public class Dungeon {
	public ArrayList<Room> hm = new ArrayList<Room>();
	public char[][] tiles;
	Random r = new Random();
	Polygon d;
	int num_rooms = 7;
	int min_size = 5;
	int max_size = 10;
	public Room spawnRoom;
	
	public Dungeon(int num_rooms, int min_size, int max_size) {
		this.num_rooms = num_rooms;
		this.min_size = min_size;
		this.max_size = max_size;
		tiles = new char[MainGame.mm.m.getWidthInTiles()][MainGame.mm.m.getHeightInTiles()];
		generate();
	}
	
	public Dungeon() {
		tiles = new char[MainGame.mm.m.getWidthInTiles()][MainGame.mm.m.getHeightInTiles()];
		generate();
	}
	
	public void generate() {
		hm.clear();
		Rectangle l = null;
		for (int i = 0; i < num_rooms; i++) {
			int size = min_size;
			if (max_size > min_size)
				size += r.nextInt(max_size-min_size);
			int x = r.nextInt(MainGame.mm.m.getWidthInTiles()-size);
			int y = r.nextInt(MainGame.mm.m.getHeightInTiles()-size);
			Room ro = new Room(new Vector2f(x,y),size);
			if (l == null) {
				l = ro.r;
				continue;
			}
			if (hm.size() > 0) {
				if (r.nextInt(2) == 1) {
					hCorri((int)l.getCenterX(),(int)ro.r.getCenterX(), (int)ro.r.getCenterY());
					vCorri((int)l.getCenterX(),(int)ro.r.getCenterY(), (int)ro.r.getCenterX());
				} else {
					vCorri((int)l.getCenterX(),(int)ro.r.getCenterY(), (int)ro.r.getCenterX());
					hCorri((int)l.getCenterX(),(int)ro.r.getCenterX(), (int)ro.r.getCenterY());
				}
				l = ro.r;
			}
			hm.add(ro);
			if (spawnRoom == null) {
				spawnRoom = ro;
			}
		}
		for (Room r : hm) {
			for (int y = 0; y < r.size; y++) {
				for (int x = 0; x < r.size; x++) {
					tiles[(int) (r.position.y+y)][(int) (r.position.x+x)] = r.tiles[y][x];
				}
			}
		}
	}
	
	private void hCorri(int x1, int x2, int y) {
		for (int x = x1/16; x < x2/16+1; x++) {
			tiles[y/16][x] = '#';
		}
	}
	
	private void vCorri(int y1, int y2, int x) {
		for (int y = y1/16; y < y2/16+1; y++) {
			tiles[y][x/16] = '#';
		}
	}
	
	public void draw(Graphics g) {
		if (MainGame.debug)
		for (Room r : hm) {
			g.draw(r.r);
		}
	}
	
}
