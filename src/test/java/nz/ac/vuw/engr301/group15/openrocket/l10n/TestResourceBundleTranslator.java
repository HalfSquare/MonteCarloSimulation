package nz.ac.vuw.engr301.group15.openrocket.l10n;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.MissingResourceException;

import net.sf.openrocket.l10n.ResourceBundleTranslator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestResourceBundleTranslator {
	
	private Locale originalDefaultLocale;
	
	@Before
	public void setup() {
		// Default locale affects resource bundles, so set something non-English
		this.originalDefaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.GERMAN);
	}
	
	@After
	public void teardown() {
		Locale.setDefault(originalDefaultLocale);
	}
	
	
	@Test
	public void testSuccessfulDefault() {
		ResourceBundleTranslator trans = new ResourceBundleTranslator("l10n.messages");
		assertEquals("messages_de.properties", trans.get("debug.currentFile"));
	}
	
	@Test
	public void testSuccessfulNonDefault() {
		ResourceBundleTranslator trans = new ResourceBundleTranslator("l10n.messages", Locale.FRENCH);
		assertEquals("messages_fr.properties", trans.get("debug.currentFile"));
	}
	
	@Test
	public void testFailure() {
		ResourceBundleTranslator trans = new ResourceBundleTranslator("l10n.messages", Locale.US);
		try {
			fail("Returned: " + trans.get("missing"));
		} catch (MissingResourceException e) {
			// Expected
		}
	}

}
