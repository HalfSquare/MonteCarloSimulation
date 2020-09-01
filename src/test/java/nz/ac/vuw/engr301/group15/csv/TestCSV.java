package nz.ac.vuw.engr301.group15.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
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
	 * This tests the CSV Output file has been created
	 */
	@Test
	public void TestFileCreated(){
		File file = new File("points.csv");
		assertTrue(file.exists());
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

	/**
	 * Test 4: Application runs without GUI
	 */


}
