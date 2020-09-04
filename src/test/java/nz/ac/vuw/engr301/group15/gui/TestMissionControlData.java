package nz.ac.vuw.engr301.group15.gui;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

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

	// Test write settings method is correct
	@Test
	public void Test5(){
		try {
			Gui gui = new Gui();
			String path = System.getProperty("user.dir") + "/src/test/java/nz/ac/vuw/engr301/group15/gui/out.csv";
			gui.settingsMissionControl = loadSettings();
			gui.writeMissionControlSettings(new File(path));

			Scanner sc = new Scanner(path);
			String[][] data = new String[2][13];
			// Read data into 2D array, splitting at the commas (0 is data names, 1 is data values)
			for (int i = 0; i < 2; i++){
				if (sc.hasNext()){
					data[i] = sc.nextLine().split(",");
				}
			}
			sc.close();
			System.out.println(data[0]);


		} catch (Exception e){
			fail();
		}
	}

}
