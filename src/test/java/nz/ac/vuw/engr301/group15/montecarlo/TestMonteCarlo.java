package nz.ac.vuw.engr301.group15.montecarlo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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

}
