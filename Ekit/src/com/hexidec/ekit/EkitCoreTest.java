package com.hexidec.ekit;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.*;
//import org.junit.Before;
//import org.junit.Test;

import javax.swing.JTextPane;
import javax.swing.JToolBar;



public class EkitCoreTest extends EkitCore {
	Vector<String> vcTools;
	@Before
	public void setUp() throws Exception {
		vcTools = new Vector<String>();
		vcTools.add("tool1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitializeMultiToolbars() {
		EkitCore testEkitCore = new EkitCore();
		String mockedString = mock(String.class);
		testEkitCore.initializeMultiToolbars(mockedString);
	}
	
	@Test
	public void testGetToolBar() {
		EkitCore testEkitCore = new EkitCore();
		JToolBar testToolBar = null;
		assertNull(testEkitCore.getToolBar(false));
		assertEquals(vcTools.size(),1);
		testEkitCore.customizeToolBar(0, vcTools, true);
	}
	
	@Test
	public void testJTPMain(){
		EkitCore testEkitCore = new EkitCore();
		JTextPane jtpMain = testEkitCore.getJTPMain();
		assertNotNull(jtpMain);
		assert(jtpMain.getDocument().toString().contains("htmlDoc"));
	}

	

}
