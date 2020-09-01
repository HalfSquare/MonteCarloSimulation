package nz.ac.vuw.engr301.group15;

import nz.ac.vuw.engr301.group15.gui.TestMissionControlData;
import nz.ac.vuw.engr301.group15.montecarlo.TestMonteCarlo;
import nz.ac.vuw.engr301.group15.openrocket.TestOpenRocket;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
		// GUI tests
		TestMissionControlData.class,

		// Monte Carlo tests
		TestMonteCarlo.class,

		//OpenRocket tests
		TestOpenRocket.class,

		//Arch tests
		//nz.ac.vuw.engr301.group15.montecarlo.openrocket.arch.TestSystemInfo.class

		//Unit tests
		//nz.ac.vuw.engr301.group15.openrocket.unit.TestValue.class

})



public class TestSuite {


}
