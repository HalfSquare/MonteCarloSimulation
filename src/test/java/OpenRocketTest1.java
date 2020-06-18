import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * These tests are designed to test OpenRocket to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Georgia
 *
 */

public class OpenRocketTest1 {
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

	// Initial test to set up pipeline - should ALWAYS fail
	/*@Test
	public void Test3() {
		assertEquals(1, 5);
	}
	*/
}
