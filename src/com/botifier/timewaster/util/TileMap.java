package com.botifier.timewaster.util;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import com.botifier.timewaster.main.MainGame;

public class TileMap implements TileBasedMap {
	int width = 0;
	int height = 0;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean blocked(PathFindingContext arg0, int tx, int ty) {
		if (MainGame.s[ty].charAt(tx) == '#') {
			return true;
		}
		return false;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public int getHeightInTiles() {
		return height;
	}

	@Override
	public int getWidthInTiles() {
		return width;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {

	}

}
