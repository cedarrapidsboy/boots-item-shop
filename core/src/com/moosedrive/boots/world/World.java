package com.moosedrive.boots.world;

import java.util.HashMap;

import com.moosedrive.boots.world.types.Cube;

public class World {

	public static final int OVERWORLD_RADIUS = 100;
	private static final Cube[] CUBE_DIRECTIONS = { new Cube(+1, -1, 0), new Cube(+1, 0, -1), new Cube(0, +1, -1),
			new Cube(-1, +1, 0), new Cube(-1, 0, +1), new Cube(0, -1, +1) };
	public static final int DIRECTION_E = 0;
	public static final int DIRECTION_NE = 1;
	public static final int DIRECTION_NW = 2;
	public static final int DIRECTION_W = 3;
	public static final int DIRECTION_SW = 4;
	public static final int DIRECTION_SE = 5;

	private final HashMap<String, WorldTile> map;
	private static World overworld;

	private World() {
		map = new HashMap<String, WorldTile>();
		WorldTile tile = null;
		for (int q = -OVERWORLD_RADIUS; q < OVERWORLD_RADIUS; q++) {
			for (int r = -OVERWORLD_RADIUS; r < OVERWORLD_RADIUS; r++) {
				tile = new WorldTile(q, r);
				map.put(hash(tile.getCube()), tile);
			}
		}
	}

	/**
	 * @return A World map
	 */
	public static World getOverworld() {
		return (overworld != null ? overworld : new World());
	}

	/**
	 * Simply returns the string coordinate - "q,r". Assumed to be unique per world.
	 * 
	 * @param q
	 * @param r
	 * @return "Unique" hash for a given coordinate
	 */
	private String hash(Cube cube) {
		return cube.getX() + "," + cube.getY() + "," + cube.getZ();
	}

	/**
	 * Returns the tile at the given coordinates, or null if no tile exists
	 * 
	 * @param q
	 * @param r
	 * @return A tile, or null
	 */
	public WorldTile getTile(Cube cube) {
		return map.get(hash(cube));
	}

	private Cube getCubeDirection(int dir) {
		return CUBE_DIRECTIONS[dir];
	}

	public WorldTile getNeighborTile(WorldTile cube, int dir) {
		Cube neighbor = Cube.add(cube.getCube(), getCubeDirection(dir));
		return getTile(neighbor);
	}

	public static int getDistance(WorldTile tileA, WorldTile tileB) {
		Cube a = tileA.getCube();
		Cube b = tileB.getCube();
		return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ())) / 2;
	}

}
