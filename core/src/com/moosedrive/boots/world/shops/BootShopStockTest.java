package com.moosedrive.boots.world.shops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.moosedrive.boots.items.armor.ArmorFactory;
import com.moosedrive.boots.items.armor.Boot;

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
		buildStock();
	}

	@Test
	void testRemoveBoot() {
		assertNotNull(stock, "stock not initialized");
		Boot boot = ArmorFactory.getRandomBoot();
		stock.addBoot(boot);
		assertEquals(stock.getStock().size(), 1);
		assertEquals(stock.getStockByCondition().size(), 1);
		assertEquals(stock.getStockByCost().size(), 1);
		stock.removeBoot(boot);
		assertEquals(stock.getStock().size(), 0);
		assertEquals(stock.getStockByCondition().size(), 0);
		assertEquals(stock.getStockByCost().size(), 0);
	}

	@Test
	void testRemoveBoots() {
		buildStock();
		stock.getStock().forEach(b -> stock.removeBoot(b));
		assertEquals(0, stock.getStock().size(), "Unsorted stock not the right size.");
		assertEquals(0, stock.getStockByCondition().size(), "Condition sorted stock not the right size.");
		assertEquals(0, stock.getStockByCost().size(), "Cost sorted stock not the right size.");
	}

	@Test
	void testConditionSortOrder() {
		buildStock();
		List<Boot> condStock = stock.getStockByCondition();
		assertTrue(condStock.get(0).getCondition() >= condStock.get(condStock.size() - 1).getCondition());

	}
	
	@Test
	void testCostSortOrder() {
		buildStock();
		List<Boot> costStock = stock.getStockByCost();
		assertTrue(costStock.get(0).getBasePrice() >= costStock.get(costStock.size() - 1).getBasePrice());

	}
	
	void buildStock() {
		assertNotNull(stock, "stock not initialized");
		for (int i = 1; i <= 100; i++) {
			stock.addBoot(ArmorFactory.getRandomBoot());
			assertEquals(i, stock.getStock().size(), "Unsorted stock not the right size.");
			assertEquals(i, stock.getStockByCondition().size(), "Condition sorted stock not the right size.");
			assertEquals(i, stock.getStockByCost().size(), "Cost sorted stock not the right size.");
		}

	}

}
