package com.moosedrive.boots.world.shops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
	void beforeAll() throws Exception {
		stock = new BootShopStock();
		buildStock();
	}

	@AfterEach
	void afterAll() throws Exception {
		stock = null;
	}

	@Test
	void testAddBootIncreasesSize() {
		assertNotNull(stock, "stock not initialized");
		int size = stock.size();
		stock.addBoot(ArmorFactory.getRandomBoot());
		assertEquals(stock.getStock().size(), size + 1);
		assertEquals(stock.getStockByCondition().size(), size + 1);
		assertEquals(stock.getStockByCost().size(), size + 1);
	}


	@Test
	void testRemoveBootDecreasesSize() {
		assertNotNull(stock, "stock not initialized");
		Boot boot = ArmorFactory.getRandomBoot();
		int size = stock.size();
		stock.addBoot(boot);
		assertEquals(stock.getStock().size(), size + 1);
		assertEquals(stock.getStockByCondition().size(), size + 1);
		assertEquals(stock.getStockByCost().size(), size + 1);
		stock.removeBoot(boot);
		assertEquals(stock.getStock().size(), size);
		assertEquals(stock.getStockByCondition().size(), size);
		assertEquals(stock.getStockByCost().size(), size);
	}

	@Test
	void testRemoveAllBoots() {
		stock.getStock().forEach(b -> stock.removeBoot(b));
		assertEquals(0, stock.getStock().size(), "Unsorted stock not the right size.");
		assertEquals(0, stock.getStockByCondition().size(), "Condition sorted stock not the right size.");
		assertEquals(0, stock.getStockByCost().size(), "Cost sorted stock not the right size.");
	}

	@Test
	void testConditionSortOrder() {
		List<Boot> condStock = stock.getStockByCondition();
		assertTrue(condStock.get(0).getCondition() >= condStock.get(condStock.size() - 1).getCondition());

	}
	
	@Test
	void testCostSortOrder() {
		List<Boot> costStock = stock.getStockByCost();
		assertTrue(costStock.get(0).getBasePrice() >= costStock.get(costStock.size() - 1).getBasePrice());

	}
	@Test
	void testSize() {
		BootShopStock sizeTest = new BootShopStock();
		assertEquals(0, sizeTest.size());
		sizeTest.addBoot(ArmorFactory.getRandomBoot());
		assertEquals(1, sizeTest.size());
		sizeTest = null;
	}
	
	@Test
	void testAddSameBoot() {
		Boot boot = ArmorFactory.getRandomBoot();
		assertTrue(stock.addBoot(boot), "Unable to add Unique boot.");
		assertFalse(stock.addBoot(boot), "Able to add duplicate boot.");
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
