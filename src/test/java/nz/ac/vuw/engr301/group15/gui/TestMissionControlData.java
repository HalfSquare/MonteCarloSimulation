package nz.ac.vuw.engr301.group15.gui;

import java.io.InputStream;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * These tests are designed to test the mission control data to make sure everything is working as expected,
 * and that no bugs have been introduced to the code.
 *
 * @author Georgia
 *
 */
public class TestMissionControlData {

	// Initial test to set up pipeline - should ALWAYS pass
	@Test
	public void Test1(){
		int x = 1;
		int y = 1;
		assertEquals(x, y);
	}

}
