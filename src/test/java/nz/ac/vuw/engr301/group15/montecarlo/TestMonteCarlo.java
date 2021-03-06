package nz.ac.vuw.engr301.group15.montecarlo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * These tests are designed to test the monte carlo class to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Georgia
 *
 */
public class TestMonteCarlo {

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
		String args[] = new String[0];
		MonteCarloSimulation mcs = new MonteCarloSimulation();
		mcs.main(args);
	}

}
