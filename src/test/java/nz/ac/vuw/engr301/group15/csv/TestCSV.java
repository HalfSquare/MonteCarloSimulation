package nz.ac.vuw.engr301.group15.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import nz.ac.vuw.engr301.group15.gui.Gui;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.junit.jupiter.api.Test;


/**
 * These tests are designed to test the CSV output of the API to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Michael & Jacqui
 *
 */


public class TestCSV {

	/**
	 * This tests the number of CSV Lines are correct
	 */
	@Test
	public void TestCSVLines() throws IOException {
		File file = new File("points.csv");
		assertTrue(file.exists());

		long lines = 0;
		BufferedReader reader = new BufferedReader(new FileReader("points.csv"));
		while (reader.readLine() != null) lines++;

		assertEquals(11,lines);
	}

	/**
	 * Test 1: CSV imported correctly
	 */
	@Test
	public void TestCSVConsoleImport() {
		Gui gui = new Gui(false, new File("src/main/resources/testMCData.csv"));
		String numSims = gui.settingsMissionControl.getNumSimulations();
		String rodAngle = gui.settingsMissionControl.getLaunchRodAngle();
		String rodLength = gui.settingsMissionControl.getLaunchRodLength();
		String rodDir = gui.settingsMissionControl.getLaunchRodDir();
		String launchAlt = gui.settingsMissionControl.getLaunchAlt();
		String launchLat = gui.settingsMissionControl.getLaunchLat();
		String launchLong = gui.settingsMissionControl.getLaunchLong();
		String maxAngle = gui.settingsMissionControl.getMaxAngle();
		String windSpeed = gui.settingsMissionControl.getWindSpeed();
		String windDir = gui.settingsMissionControl.getWindDir();
		String windTurbulence = gui.settingsMissionControl.getWindTurbulence();
		String launchTemp = gui.settingsMissionControl.getLaunchTemp();
		String airPressure = gui.settingsMissionControl.getLaunchAirPressure();
		assertEquals(numSims, "10");
		assertEquals(rodAngle, "0");
		assertEquals(rodLength, "0.2");
		assertEquals(rodDir, "0");
		assertEquals(launchAlt, "0");
		assertEquals(launchLat, "30");
		assertEquals(launchLong, "-50");
		assertEquals(maxAngle, "0.018");
		assertEquals(windSpeed, "3");
		assertEquals(windDir, "0");
		assertEquals(windTurbulence, "0.1");
		assertEquals(launchTemp, "285");
		assertEquals(airPressure, "1010");
	}

	/**
	 * Test 2: CSV exported correctly
	 */
	@Test
	public void TestCSVConsoleExport() throws Exception {
		Gui gui = new Gui(false, new File("src/main/resources/testMCData.csv"));
		MonteCarloSimulation sim = new MonteCarloSimulation();
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
		sim.runSimulations(rocketFile, gui.settingsMissionControl);
	}

	/**
	 * Test 3: Application runs with GUI
	 */
	@Test
	public void TestApplicationRunsGUI() {
		Gui gui = new Gui(true, new File("src/main/resources/testMCData.csv"));
		MonteCarloSimulation sim = new MonteCarloSimulation();
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");

		assertNotNull(gui.getGraphWindow());
		assertNotNull(gui.getSettingsWindow());
		assertNotNull(gui.getSimulationWindow());
	}

	/**
	 * Test 4: Application runs without GUI
	 */

	@Test
	public void TestApplicationRunsNoGUI() {
		Gui gui = new Gui(false, new File("src/main/resources/testMCData.csv"));
		MonteCarloSimulation sim = new MonteCarloSimulation();
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");

		assertNull((gui.getGraphWindow()));
		assertNull(gui.getSettingsWindow());
		assertNull(gui.getSimulationWindow());
	}

}
