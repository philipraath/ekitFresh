package com.hexidec.ekit;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EkitUtility {
	JMenuBar jMenuBar;
	/** Convenience method for obtaining a custom menu bar
	  */
	public JMenuBar getCustomMenuBar(Vector<String> vcMenus, Hashtable<String, JMenu> htMenus)
	{
		jMenuBar = new JMenuBar();
		for(int i = 0; i < vcMenus.size(); i++)
		{
			String menuToAdd = vcMenus.elementAt(i).toLowerCase();
			if(htMenus.containsKey(menuToAdd))
			{
				jMenuBar.add((JMenu)(htMenus.get(menuToAdd)));
			}
		}
		return jMenuBar;
	}
	
	/** Convenience method for creating the multiple toolbar set from a sequence string
	 * @return 
	  */
	public ArrayList<Vector<String>> buildMultiToolBarElements(String toolbarSeq)
	{
		ArrayList<Vector<String>> vcToolPicks = new ArrayList<Vector<String>>(3);
		vcToolPicks.add(0, new Vector<String>());
		vcToolPicks.add(1, new Vector<String>());
		vcToolPicks.add(2, new Vector<String>());

		int whichBar = 0;
		StringTokenizer stToolbars = new StringTokenizer(toolbarSeq.toUpperCase(), "|");
		while(stToolbars.hasMoreTokens())
		{
			String sKey = stToolbars.nextToken();
			if(sKey.equals("*"))
			{
				whichBar++;
				if(whichBar > 2)
				{
					whichBar = 2;
				}
			}
			else
			{
				vcToolPicks.get(whichBar).add(sKey);
			}
		}
		return vcToolPicks;
	}

}
