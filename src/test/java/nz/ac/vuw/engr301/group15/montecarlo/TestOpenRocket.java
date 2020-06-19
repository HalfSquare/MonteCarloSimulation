package nz.ac.vuw.engr301.group15.montecarlo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * These tests are designed to test OpenRocket to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Georgia
 *
 */

public class TestOpenRocket {
	// Initial test to set up pipeline - should ALWAYS pass
	@Test
	public void Test1(){
		int x = 1;
		int y = 1;
		assertEquals(x, y);
		System.out.println("1");
	}

	// Initial test to set up pipeline - should ALWAYS pass
	@Test
	public void Test2(){
		assertTrue(true);
		System.out.println("2");
	}

	// Initial test to check that simulation can run 1 instance
	@Test
	public void Test3() {
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try{
			sim.runSimulations(1);
		}
		catch (Exception ex){
			fail();
		}
		System.out.println("3");
	}

	// Initial test to check that simulation will not crash on negative value
	@Test
	public void Test4(){
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try {
			sim.runSimulations(-6);
		}
		catch (Exception ex){
			fail();
		}
		System.out.println("4");
	}

	// Initial test to check that simulation can run 1000 instances
	@Test
	public void Test5() {
		MonteCarloSimulation sim = new MonteCarloSimulation();
		try{
			sim.runSimulations(1000);
		}
		catch (Exception ex){
			fail();
		}
		System.out.println("5");
	}

}
