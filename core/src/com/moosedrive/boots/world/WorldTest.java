package com.moosedrive.boots.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.moosedrive.boots.world.types.Cube;

class WorldTest {

	private static World world;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		world = World.getOverworld();
		assertNotNull(world);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testTileLocation() {
		assertNotNull(world.getTile(new Cube(0, 0, 0)));
		assertNull(world.getTile(new Cube(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE)));
	}

	@Test
	void testReachable() {
		WorldTile tile = world.getTile(new Cube(0, 0, 0));
		assertNotNull(tile);
		List<WorldTile> tiles = world.getReachableTiles(tile, 1);
		assertEquals(7, tiles.size());
		tiles = world.getReachableTiles(tile, 0);
		assertEquals(1, tiles.size());
		tiles = world.getReachableTiles(tile, 2);
		assertEquals(19, tiles.size());
		//a tile on the very edge should not have any reachable tiles on one side
		tile = world.getTile(new Cube(-World.OVERWORLD_RADIUS,0));
		assertNotNull(tile);
		tiles = world.getReachableTiles(tile, 1);
		assertEquals(4, tiles.size());
	}

	@Test
	void testTileDistance() {
		int MAX_RADIUS = World.OVERWORLD_RADIUS - 1;
		assertNotNull(world);
		WorldTile a = world.getTile(new Cube(0, 5, -5));
		assertNotNull(a);
		WorldTile b = world.getTile(new Cube(0, 0, 0));
		assertNotNull(b);
		int distance = World.getDistance(a, b);
		assertEquals(5, distance);
		a = world.getTile(new Cube(3, 2, -5));
		assertNotNull(a);
		b = world.getTile(new Cube(-2, -3, 5));
		assertNotNull(b);
		distance = World.getDistance(a, b);
		assertEquals(10, distance);
		a = world.getTile(new Cube(0, MAX_RADIUS, -MAX_RADIUS));
		assertNotNull(a);
		b = world.getTile(new Cube(0, -MAX_RADIUS, MAX_RADIUS));
		assertNotNull(b);
		distance = World.getDistance(a, b);
		assertEquals(MAX_RADIUS * 2, distance);

	}

	@Test
	void testStraightLineDraw() {
		WorldTile a = world.getTile(new Cube(-3, 3, 0));
		assertNotNull(a);
		WorldTile b = world.getTile(new Cube(0, 0, 0));
		assertNotNull(b);
		WorldTile[] line = world.cubeLineDraw(a, b);
		assertEquals(4, line.length);
		assertEquals(a, line[0]);
		assertEquals(world.getTile(new Cube(-2, 2, 0)), line[1]);
		assertEquals(world.getTile(new Cube(-1, 1, 0)), line[2]);
		assertEquals(b, line[3]);
	}

	@Test
	void testDiagLineDraw() {
		WorldTile a = world.getTile(new Cube(0, 0, 0));
		assertNotNull(a);
		WorldTile b = world.getTile(new Cube(-1, -2, 3));
		assertNotNull(b);
		WorldTile[] line = world.cubeLineDraw(a, b);
		assertEquals(4, line.length);
		assertEquals(a, line[0]);
		assertEquals(world.getTile(new Cube(0, -1, 1)), line[1]);
		assertEquals(world.getTile(new Cube(-1, -1, 2)), line[2]);
		assertEquals(b, line[3]);
	}

}
