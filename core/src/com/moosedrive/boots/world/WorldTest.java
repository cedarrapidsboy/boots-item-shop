package com.moosedrive.boots.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	void testTileDistance() {
		int MAX_RADIUS = World.OVERWORLD_RADIUS-1;
		assertNotNull(world);
		WorldTile a = world.getTile(new Cube(0,5,-5));
		assertNotNull(a);
		WorldTile b = world.getTile(new Cube(0,0,0));
		assertNotNull(b);
		int distance = World.getDistance(a, b);
		assertEquals(5, distance);
		a = world.getTile(new Cube(3,2,-5));
		assertNotNull(a);
		b = world.getTile(new Cube(-2,-3,5));
		assertNotNull(b);
		distance = World.getDistance(a, b);
		assertEquals(10, distance);
		a = world.getTile(new Cube(0,MAX_RADIUS,-MAX_RADIUS));
		assertNotNull(a);
		b = world.getTile(new Cube(0,-MAX_RADIUS,MAX_RADIUS));
		assertNotNull(b);
		distance = World.getDistance(a, b);
		assertEquals(MAX_RADIUS*2, distance);

	}

}
