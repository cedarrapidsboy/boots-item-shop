package com.moosedrive.boots.world.shops;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.moosedrive.boots.items.armor.ArmorFactory;

class BootShopStockTest {
	private BootShopStock stock;

	@BeforeEach
	void setUp() throws Exception {
		stock = new BootShopStock();
	}

	@AfterEach
	void tearDown() throws Exception {
		stock = null;
	}

	@Test
	void testAddBoot() {
		assertNotNull(stock, "stock not initialized");
		stock.addBoot(ArmorFactory.getRandomBoot());
		assertEquals(stock.getStock().size(), 1);
		assertEquals(stock.getStockByCondition().size(), 1);
		assertEquals(stock.getStockByCost().size(), 1);
	}
	@Test
	void testAddBoots() {
		assertNotNull(stock, "stock not initialized");
		stock.addBoot(ArmorFactory.getRandomBoot());
		stock.addBoot(ArmorFactory.getRandomBoot());
		stock.addBoot(ArmorFactory.getRandomBoot());
		assertEquals(3, stock.getStock().size(), "Unsorted stock not the right size.");
		assertEquals(3, stock.getStockByCondition().size(), "Condition sorted stock not the right size.");
		assertEquals(3, stock.getStockByCost().size(), "Cost sorted stock not the right size.");
	}

}
