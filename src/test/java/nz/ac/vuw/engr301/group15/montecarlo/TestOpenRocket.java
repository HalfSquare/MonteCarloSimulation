package nz.ac.vuw.engr301.group15.montecarlo;

import java.io.InputStream;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


/**
 * These tests are designed to test OpenRocket to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * IMPORTANT NOTE: the pipeline will crash if you run more than 100 simulations.
 *
 * @author Georgia
 *
 */

public class TestOpenRocket {

	MissionControlSettings loadSettings() {
		MissionControlSettings settings = new MissionControlSettings();
		settings.setLaunchRodAngle("0.0");
		settings.setLaunchRodLength("0.2");
		settings.setLaunchRodDir("0.0");
		settings.setLaunchAlt("159.0");
		settings.setLaunchLat("-41.1283");
		settings.setLaunchLong("175.0202");
		settings.setMaxAngle("0.017453292519943295");
		settings.setWindSpeed("6.0");
		settings.setWindDir("0.0");
		settings.setWindTurbulence("0.1");
		settings.setLaunchTemp("284.15");
		settings.setLaunchAirPressure("1010.0");
		settings.setNumSimulations("1000");
		return settings;
	}

	// Initial test to set up pipeline - should ALWAYS pass
	@Test
	public void Test1(){
		int x = 1;
		int y = 1;
		assertEquals(x, y);
	}

	// Initial test to set up pipeline - should ALWAYS pass
	@Test
	public void Test2(){
		assertTrue(true);
	}

	// Initial test to check that simulation can run 1 instance
	@Test
	public void Test3() {
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
			MissionControlSettings settings = loadSettings();
			settings.setNumSimulations("1");
			sim.runSimulations(rocketFile, settings);
		}
		catch (Exception ex){
			ex.printStackTrace();
			fail();
		}
	}

	// Initial test to check that simulation will not crash on negative value
	@Test
	public void Test4(){
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
			MissionControlSettings settings = loadSettings();
			settings.setNumSimulations("-6");
			sim.runSimulations(rocketFile, settings);
		}
		catch (Exception ex){
			fail();
		}
	}

	// Initial test to check that simulation can run 10 instances
	@Test
	public void Test5() {
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try{
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
			MissionControlSettings settings = loadSettings();
			settings.setNumSimulations("10");
			sim.runSimulations(rocketFile, settings);
		}
		catch (Exception ex){
			ex.printStackTrace();
			fail();
		}
	}
}
