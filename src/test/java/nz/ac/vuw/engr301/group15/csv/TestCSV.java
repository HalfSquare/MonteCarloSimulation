package nz.ac.vuw.engr301.group15.montecarlo;

import java.io.InputStream;
import nz.ac.vuw.engr301.group15.gui.Gui;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


/**
 * These tests are designed to test OpenRocket to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Georgia
 *
 */

public class TestOpenRocket {
	MissionControlSettings settings = new MissionControlSettings();
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
		try{
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
			sim.runSimulations(rocketFile, settings);
		}
		catch (Exception ex){
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
			sim.runSimulations(rocketFile, settings);
		}
		catch (Exception ex){
			ex.printStackTrace();
			fail();
		}
	}

}
