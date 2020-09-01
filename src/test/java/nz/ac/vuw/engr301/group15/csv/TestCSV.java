package nz.ac.vuw.engr301.group15.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.InputStream;
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


}
