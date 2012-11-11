package com.hexidec.ekit;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.*;
//import org.junit.Before;
//import org.junit.Test;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.JToolBar;



public class EkitCoreTest extends EkitCore {
	Vector<String> vcTools;
	Vector<String> htmenus;
	private JMenuBar jMenuBar;
	private JMenu jMenuFile;
	private JMenu jMenuEdit;
	private JMenu jMenuView;
	private JMenu jMenuFont;
	private JMenu jMenuFormat;
	private JMenu jMenuInsert;
	private JMenu jMenuTable;
	private JMenu jMenuForms;
	private JMenu jMenuSearch;
	private JMenu jMenuTools;
	private JMenu jMenuHelp;
	private JMenu jMenuDebug;
	@Before
	public void setUp() throws Exception {
		vcTools = new Vector<String>();
		vcTools.add("tool1");
		
		htmenus = new Vector<String>();
		htmenus.add("file");
		htmenus.add("edit");
		htmenus.add("view");
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testInitializeMultiToolbars() {
//		EkitCore testEkitCore = new EkitCore();
//		String mockedString = mock(String.class);
//		testEkitCore.initializeMultiToolbars(mockedString);
//	}
//	
//	@Test
//	public void testGetToolBar() {
//		EkitCore testEkitCore = new EkitCore();
//		JToolBar testToolBar = null;
//		assertNull(testEkitCore.getToolBar(false));
//		assertEquals(vcTools.size(),1);
//		testEkitCore.customizeToolBar(0, vcTools, true);
//	}
//	
//	@Test
//	public void testJTPMain(){
//		EkitCore testEkitCore = new EkitCore();
//		JTextPane jtpMain = testEkitCore.getTextPane();
//		assertNotNull(jtpMain);
//		assert(jtpMain.getDocument().toString().contains("htmlDoc"));
//	}
	
	@Test
	public void testGetCustomMenuBar(){
//		EkitApplet main = new EkitApplet();
//		EkitCore core = new EkitCore();
//		main.start();
		JMenuBar menuBar = this.getMenuBar();
		assertNotNull(menuBar);
		menuBar = this.getCustomMenuBar(htmenus);
		assert(menuBar.getComponent(0).toString().toLowerCase().contains("text=file"));
		assert(menuBar.getComponent(1).toString().toLowerCase().contains("text=edit"));
		assert(menuBar.getComponent(2).toString().toLowerCase().contains("text=view"));
		assert(menuBar.getComponentCount()==3);
	}
	
	private void display(int input){
		System.out.println(input);
	}
	
	private void display(String input){
		System.out.println(input);
	}

	

}
