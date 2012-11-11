package com.hexidec.ekit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EkitUtilityTest extends EkitUtility{
	Vector<String> htmenus;
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitializeMultiToolbars(){
		String toolbarSeq = EkitCore.TOOLBAR_DEFAULT_MULTI;
		ArrayList<Vector<String>> vcToolPicks = 
				new ArrayList<Vector<String>>(3);
		vcToolPicks = this.buildMultiToolBarElements(toolbarSeq);
		assert(
				"[NW, NS, OP, SV, PR, SP, CT, CP, PS, SP, UN, RE, SP, FN, SP, UC, UM, SP, SR]".equalsIgnoreCase(vcToolPicks.toString()));
		assert(
				"[BL, IT, UD, SP, SK, SU, SB, SP, AL, AC, AR, AJ, SP, UL, OL, SP, LK]".equalsIgnoreCase(vcToolPicks.toString()));
		assert(
				"[ST, SP, FO]".equalsIgnoreCase(vcToolPicks.toString()));
	}
	
	private void display(String input){
		System.out.println(input);
	}
	

}
