package nz.ac.vuw.engr301.group15.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import nz.ac.vuw.engr301.group15.gui.Gui;
import org.junit.Test;


/**
 * These tests are designed to test the Stats CSV output of the API to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Jacqui
 *
 */
public class TestStatsCSV {

	/**
	 * This tests the file is created
	 */
	@Test
	public void TestStatsCSVCreated() throws IOException {
		File file = new File("simulationStats.csv");
		assertTrue(file.exists());
	}

}
