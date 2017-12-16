package com.moosedrive.boots.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.MathUtils;
import com.moosedrive.boots.world.types.Cube;

public class World {

	public static final int OVERWORLD_RADIUS = 25;
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
		for (int q = -OVERWORLD_RADIUS; q <= OVERWORLD_RADIUS; q++) {
			int r1 = Math.max(-OVERWORLD_RADIUS, -q - OVERWORLD_RADIUS);
			int r2 = Math.min(OVERWORLD_RADIUS, -q + OVERWORLD_RADIUS);
			for (int r = r1; r <= r2; r++) {
				tile = new WorldTile(this, q, r);
				map.put(hash(tile.getCube()), tile);
			}
		}
	}

	public List<WorldTile> getTiles() {
		return new ArrayList<WorldTile>(map.values());
	}

	public WorldTile getRandomTile() {
		return getTiles().get(MathUtils.random(0, getTiles().size() - 1));
	}

	/**
	 * @return A World map
	 */
	public static World getOverworld() {
		if (overworld == null) {
			overworld = new World();
		}
		return overworld;
	}

	/**
	 * Simply returns the string coordinate - "q,r". Assumed to be unique per world.
	 * 
	 * @param q
	 * @param r
	 * @return "Unique" hash for a given coordinate
	 */
	private String hash(Cube cube) {
		return cube.getQ() + "," + cube.getR();
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

	/**
	 * Returns all tiles that exist around the given tile. Array is indexed from 0-5
	 * - see DIRECTION_X constants.
	 * 
	 * @param tile
	 * @return An array - null values indicate no tile in that direction
	 */
	public WorldTile[] getNeighborTiles(WorldTile tile) {
		WorldTile[] tiles = new WorldTile[5];
		for (int i = DIRECTION_E; i <= DIRECTION_SE; i++) {
			tiles[i] = getNeighborTile(tile, i);
		}
		return tiles;
	}

	public List<WorldTile> getReachableTiles(WorldTile tile, int steps) {
		return getTiles().stream().filter(t -> (distance(tile.getCube(), t.getCube()) <= steps))
				.collect(Collectors.toList());
	}

	public WorldTile getNeighborTile(WorldTile cube, int dir) {
		Cube neighbor = Cube.add(cube.getCube(), getCubeDirection(dir));
		return getTile(neighbor);
	}

	public static int getDistance(WorldTile tileA, WorldTile tileB) {
		Cube a = tileA.getCube();
		Cube b = tileB.getCube();
		return distance(a, b);
	}

	/**
	 * Returns a value t-steps between a and b
	 * 
	 * @param a
	 * @param b
	 * @param t
	 * @return a value t-steps between a and b
	 */
	private static float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}

	/**
	 * Returns a Cube t-steps between a and b
	 * 
	 * @param a
	 * @param b
	 * @param t
	 * @return a Cube t-steps between a and b
	 */
	public static Cube cubeLerp(Cube a, Cube b, float t) {
		return new Cube(lerp(a.getX(), b.getX(), t), lerp(a.getY(), b.getY(), t), lerp(a.getZ(), b.getZ(), t));
	}

	/**
	 * Linear processing of the path between two tiles is a float operation. This
	 * method gets the world tile that contains the point described by Cube.
	 * 
	 * @param cube
	 *            A point in the world
	 * @return A tile that contains the point, null if no tile contains the point
	 */
	public WorldTile cubeRound(Cube cube) {
		int rx = Math.round(cube.getX());
		int ry = Math.round(cube.getY());
		int rz = Math.round(cube.getZ());

		float x_diff = Math.abs(rx - cube.getX());
		float y_diff = Math.abs(ry - cube.getY());
		float z_diff = Math.abs(rz - cube.getZ());

		if (x_diff > y_diff && x_diff > z_diff) {
			rx = -ry - rz;
		} else if (y_diff > z_diff) {
			ry = -rx - rz;
		} else {
			rz = -rx - ry;
		}
		return getTile(new Cube(rx, ry, rz));
	}

	/**
	 * Returns all the tiles that represent the most direct line between tiles a and
	 * b
	 * 
	 * @param a
	 *            Starting tile
	 * @param b
	 *            End tile
	 * @return Ordered set of tile from a to b (inclusive)
	 */
	public WorldTile[] cubeLineDraw(WorldTile a, WorldTile b) {
		float n = getDistance(a, b);
		// We "nudge" the end points a bit to avoid landing exactly on the edge of two
		// hexes
		Cube aNudged = new Cube(a.getCube().getX() + 1.0e-6F, a.getCube().getY() + 1.0e-6F,
				a.getCube().getZ() - 2.0e-6F);
		Cube bNudged = new Cube(b.getCube().getX() + 1.0e-6F, b.getCube().getY() + 1.0e-6F,
				b.getCube().getZ() - 2.0e-6F);
		ArrayList<WorldTile> results = new ArrayList<WorldTile>();
		for (int i = 0; i <= n; i++) {
			results.add(cubeRound(cubeLerp(aNudged, bNudged, 1.0F / n * i)));
		}
		return results.toArray(new WorldTile[results.size()]);

	}

	private static int length(Cube hex) {
		return Math.round((Math.abs(hex.getQ()) + Math.abs(hex.getR()) + Math.abs(hex.getS())) / 2);
	}

	public static int distance(Cube a, Cube b) {
		return length(subtract(a, b));
	}

	private static Cube add(Cube a, Cube b) {
		return new Cube(a.getQ() + b.getQ(), a.getR() + b.getR(), a.getS() + b.getS());
	}

	private static Cube subtract(Cube a, Cube b) {
		return new Cube(a.getQ() - b.getQ(), a.getR() - b.getR(), a.getS() - b.getS());
	}

	private static Cube multiply(Cube a, int k) {
		return new Cube(a.getQ() * k, a.getR() * k, a.getS() * k);
	}

}
