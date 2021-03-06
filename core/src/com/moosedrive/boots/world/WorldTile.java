package com.moosedrive.boots.world;

import com.moosedrive.boots.world.types.Cube;

public class WorldTile {

	private Cube cube;


	public Cube getCube() {
		return this.cube;
	}
	
	public WorldTile(int x, int y, int z) {
		super();
		this.cube = new Cube(x, y, z);
	}
	
	public WorldTile(int q, int r) {
		super();
		this.cube = new Cube(q, -q-r, r);
	}


}
