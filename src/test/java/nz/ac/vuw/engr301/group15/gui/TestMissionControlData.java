package nz.ac.vuw.engr301.group15.gui;

import java.io.File;
import java.io.InputStream;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;
import nz.ac.vuw.engr301.group15.gui.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

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

	// Test reading in mission control settings
	@Test
	public void Test2(){
		try {
			Gui gui = new Gui();
			String path = System.getProperty("user.dir") + "/src/test/java/nz/ac/vuw/engr301/group15/gui/good.csv";
			gui.loadMissionControlData(new File(path));
		} catch (Exception e){
			fail();
		}
	}

	// Test wrong file path is dealt with
	@Test
	public void Test3(){
		try {
			Gui gui = new Gui();
			String path = "thisisaninvalidpath";
			gui.loadMissionControlData(new File(path));
		} catch (Exception e){
			fail();
		}
	}

	// Test bad file format is dealt with
	@Test
	public void Test4(){
		try {
			Gui gui = new Gui();
			String path = System.getProperty("user.dir") + "/src/test/java/nz/ac/vuw/engr301/group15/gui/bad.csv";
			gui.loadMissionControlData(new File(path));
		} catch (Exception e){
			fail();
		}
	}

}
