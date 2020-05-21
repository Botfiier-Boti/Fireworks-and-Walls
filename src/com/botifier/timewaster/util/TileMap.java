package com.botifier.timewaster.util;


import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import com.botifier.timewaster.main.MainGame;

public class TileMap implements TileBasedMap {
	int width = 0;
	int height = 0;
	
	public TileMap(int width, int height) {
		this.width = width/16;
		this.height = height/16;
	}

	@Override
	public boolean blocked(PathFindingContext arg0, int tx, int ty) {
		if (tx > -1 && ty >-1 && ty < MainGame.mm.tiles.length && tx < MainGame.mm.tiles[ty].length ) {
			char c = MainGame.mm.tiles[ty][tx];
			if ( c == ' ' || c == 0 ) {
				return true;
			}
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
