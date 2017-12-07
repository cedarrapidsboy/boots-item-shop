package com.moosedrive.boots.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void titleCaseTest() {
	    assertEquals("String", StringUtils.toTitleCase("string"));
	    assertEquals("Another String", StringUtils.toTitleCase("another string"));
	    assertEquals("YET ANOTHER STRING", StringUtils.toTitleCase("YET ANOTHER STRING"));
	}

}
