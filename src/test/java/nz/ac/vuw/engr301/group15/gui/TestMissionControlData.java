package nz.ac.vuw.engr301.group15.gui;

import java.io.File;
import java.util.Scanner;

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
			new Gui(false, new File("src/test/java/nz/ac/vuw/engr301/group15/gui/bad.csv"));
		} catch (Exception e){
			fail();
		}
	}

	// Test wrong file path is dealt with
	@Test
	public void Test3(){
		try {
			new Gui(false, new File("thisisaninvalidpath"));
		} catch (Exception e){
			fail();
		}
	}

	// Test bad file format is dealt with
	@Test
	public void Test4(){
		try {
			new Gui(false, new File("src/test/java/nz/ac/vuw/engr301/group15/gui/bad.csv"));
		} catch (Exception e){
			fail();
		}
	}

	// Test write settings method is correct
	@Test
	public void Test5(){
		try {
			Gui gui = new Gui(false, new File("src/test/java/nz/ac/vuw/engr301/group15/gui/good.csv"));
			gui.settingsMissionControl = loadSettings();
			String path = System.getProperty("user.dir") + "/src/test/java/nz/ac/vuw/engr301/group15/gui/out.csv";
			gui.writeMissionControlSettings(new File(path));

			Scanner sc = new Scanner(new File(path));
			String[][] data = new String[2][13];
			String[][] check = new String[2][13];
			String lineOne = "launchRodAngle,launchRodLength,launchRodDir,launchAlt,launchLat,launchLong,maxAngle,windSpeed,windDir,windTurbulence,launchTemp,launchAirPressure,numSimulations";
			String lineTwo = "0.0,0.2,0.0,159.0,-41.1283,175.0202,0.017453292519943295,6.0,0.0,0.1,284.15,1010.0,1000";
			check[0] = lineOne.split(",");
			check[1] = lineTwo.split(",");

			// Read data into 2D array, splitting at the commas (0 is data names, 1 is data values)
			for (int i = 0; i < 2; i++){
				if (sc.hasNext()){
					data[i] = sc.nextLine().split(",");
				}
			}
			sc.close();
			System.out.println(data[0][1]);
			for (int i = 0; i < 13; i++){
				for (int y = 0; y < 2; y++){
					assertEquals(data[y][i], check[y][i]);
				}
			}

		} catch (Exception e){
			fail();
		}
	}

}
