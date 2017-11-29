/**
 * 
 */
package com.moosedrive.boots.world.shops;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Chad
 *
 */
class BootShopTest {
	private BootShop shop;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		shop = BootShop.getInstance();
		assertNotNull(shop, "Unable to setup test. shop is null.");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		shop = null;
	}

	/**
	 * Test method for {@link com.moosedrive.boots.world.shops.BootShop#getInstance()}.
	 */
	@Test
	void testGetInstance() {
		BootShop inst = BootShop.getInstance();
		assertNotNull(inst, "GetInstance returned a null.");
	}
	
	@Test
	void testAddBoot() {
		int size = shop.count();
		shop.addBoot();
		assertTrue(shop.count() == size + 1, "Shop did not grow by one.");
	}
	
	@Test
	void testAdd1000Boots() {
		int size = shop.count();
		for (int i = 0; i < 1000; i++)
			shop.addBoot();
		assertTrue(shop.count() == size + 1000, "Shop did not grow by 1000.");
	}

}
