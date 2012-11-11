/*
GNU Lesser General Public License

EkitCore - Base Java Swing HTML Editor & Viewer Class (Core)
Copyright (C) 2000 Howard Kistler

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package com.hexidec.ekit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;

import com.hexidec.ekit.action.*;
import com.hexidec.ekit.component.*;
import com.hexidec.util.Base64Codec;
import com.hexidec.util.Translatrix;
import com.hexidec.ekit.thirdparty.print.DocumentRenderer;

/** EkitCore
  * Main application class for editing and saving HTML in a Java text component
  *
  * @author Howard Kistler
  * @version 1.1
  *
  * REQUIREMENTS
  * Java 2 (JDK 1.5 or higher)
  * Swing Library
  */

public class EkitCore extends JPanel implements ActionListener, KeyListener, FocusListener, DocumentListener
{
	/* Constants */
	// Menu Keys
	public static final String KEY_MENU_FILE   = "file";
	public static final String KEY_MENU_EDIT   = "edit";
	public static final String KEY_MENU_VIEW   = "view";
	public static final String KEY_MENU_FONT   = "font";
	public static final String KEY_MENU_FORMAT = "format";
	public static final String KEY_MENU_INSERT = "insert";
	public static final String KEY_MENU_TABLE  = "table";
	public static final String KEY_MENU_FORMS  = "forms";
	public static final String KEY_MENU_SEARCH = "search";
	public static final String KEY_MENU_TOOLS  = "tools";
	public static final String KEY_MENU_HELP   = "help";
	public static final String KEY_MENU_DEBUG  = "debug";

	// Tool Keys
	public static final String KEY_TOOL_SEP       = "SP";
	public static final String KEY_TOOL_NEW       = "NW";
	public static final String KEY_TOOL_NEWSTYLED = "NS";
	public static final String KEY_TOOL_OPEN      = "OP";
	public static final String KEY_TOOL_SAVE      = "SV";
	public static final String KEY_TOOL_PRINT     = "PR";
	public static final String KEY_TOOL_CUT       = "CT";
	public static final String KEY_TOOL_COPY      = "CP";
	public static final String KEY_TOOL_PASTE     = "PS";
	public static final String KEY_TOOL_PASTEX    = "PX";
	public static final String KEY_TOOL_UNDO      = "UN";
	public static final String KEY_TOOL_REDO      = "RE";
	public static final String KEY_TOOL_BOLD      = "BL";
	public static final String KEY_TOOL_ITALIC    = "IT";
	public static final String KEY_TOOL_UNDERLINE = "UD";
	public static final String KEY_TOOL_STRIKE    = "SK";
	public static final String KEY_TOOL_SUPER     = "SU";
	public static final String KEY_TOOL_SUB       = "SB";
	public static final String KEY_TOOL_ULIST     = "UL";
	public static final String KEY_TOOL_OLIST     = "OL";
	public static final String KEY_TOOL_ALIGNL    = "AL";
	public static final String KEY_TOOL_ALIGNC    = "AC";
	public static final String KEY_TOOL_ALIGNR    = "AR";
	public static final String KEY_TOOL_ALIGNJ    = "AJ";
	public static final String KEY_TOOL_UNICODE   = "UC";
	public static final String KEY_TOOL_UNIMATH   = "UM";
	public static final String KEY_TOOL_FIND      = "FN";
	public static final String KEY_TOOL_ANCHOR    = "LK";
	public static final String KEY_TOOL_SOURCE    = "SR";
	public static final String KEY_TOOL_STYLES    = "ST";
	public static final String KEY_TOOL_FONTS     = "FO";
	public static final String KEY_TOOL_INSTABLE  = "TI";
	public static final String KEY_TOOL_EDITTABLE = "TE";
	public static final String KEY_TOOL_EDITCELL  = "CE";
	public static final String KEY_TOOL_INSERTROW = "RI";
	public static final String KEY_TOOL_INSERTCOL = "CI";
	public static final String KEY_TOOL_DELETEROW = "RD";
	public static final String KEY_TOOL_DELETECOL = "CD";

	public static final String TOOLBAR_DEFAULT_MULTI  = "NW|NS|OP|SV|PR|SP|CT|CP|PS|SP|UN|RE|SP|FN|SP|UC|UM|SP|SR|*|BL|IT|UD|SP|SK|SU|SB|SP|AL|AC|AR|AJ|SP|UL|OL|SP|LK|*|ST|SP|FO";
	public static final String TOOLBAR_DEFAULT_SINGLE = "NW|NS|OP|SV|PR|SP|CT|CP|PS|SP|UN|RE|SP|BL|IT|UD|SP|FN|SP|UC|SP|LK|SP|SR|SP|ST";

	public static final int TOOLBAR_SINGLE = 0;
	public static final int TOOLBAR_MAIN   = 1;
	public static final int TOOLBAR_FORMAT = 2;
	public static final int TOOLBAR_STYLES = 3;

	public static final String CMD_DOC_NEW = "newdoc";
	public static final String CMD_DOC_NEW_STYLED = "newdocstyled";
	public static final String CMD_DOC_OPEN_HTML = "openhtml";
	public static final String CMD_DOC_OPEN_CSS = "opencss";
	public static final String CMD_DOC_OPEN_BASE64 = "openb64";
	public static final String CMD_DOC_SAVE = "save";
	public static final String CMD_DOC_SAVE_AS = "saveas";
	public static final String CMD_DOC_SAVE_BODY = "savebody";
	public static final String CMD_DOC_SAVE_RTF = "savertf";
	public static final String CMD_DOC_SAVE_BASE64 = "saveb64";
	public static final String CMD_DOC_PRINT = "print";
	public static final String CMD_DOC_SERIALIZE_OUT = "serialize";
	public static final String CMD_DOC_SERIALIZE_IN = "readfromser";
	public static final String CMD_EXIT = "exit";
	public static final String CMD_SEARCH_FIND = "find";
	public static final String CMD_SEARCH_FIND_AGAIN = "findagain";
	public static final String CMD_SEARCH_REPLACE = "replace";
	public static final String CMD_CLIP_CUT = "textcut";
	public static final String CMD_CLIP_COPY = "textcopy";
	public static final String CMD_CLIP_PASTE = "textpaste";
	public static final String CMD_CLIP_PASTE_PLAIN = "textpasteplain";
	public static final String CMD_TOGGLE_TOOLBAR_SINGLE = "toggletoolbarsingle";
	public static final String CMD_TOGGLE_TOOLBAR_MAIN   = "toggletoolbarmain";
	public static final String CMD_TOGGLE_TOOLBAR_FORMAT = "toggletoolbarformat";
	public static final String CMD_TOGGLE_TOOLBAR_STYLES = "toggletoolbarstyles";
	public static final String CMD_TOGGLE_SOURCE_VIEW    = "togglesourceview";
	public static final String CMD_TABLE_INSERT = "inserttable";
	public static final String CMD_TABLE_EDIT = "edittable";
	public static final String CMD_TABLE_CELL_EDIT = "editcell";
	public static final String CMD_TABLE_ROW_INSERT = "inserttablerow";
	public static final String CMD_TABLE_ROW_DELETE = "deletetablerow";
	public static final String CMD_TABLE_COLUMN_INSERT = "inserttablecolumn";
	public static final String CMD_TABLE_COLUMN_DELETE = "deletetablecolumn";
	public static final String CMD_INSERT_BREAK = "insertbreak";
	public static final String CMD_INSERT_NBSP = "insertnbsp";
	public static final String CMD_INSERT_HR = "inserthr";
	public static final String CMD_INSERT_IMAGE_LOCAL = "insertlocalimage";
	public static final String CMD_INSERT_IMAGE_URL = "inserturlimage";
	public static final String CMD_INSERT_UNICODE_CHAR = "insertunicodecharacter";
	public static final String CMD_INSERT_UNICODE_MATH = "insertunicodemathematic";
	public static final String CMD_INSERT_UNICODE_DRAW = "insertunicodedrawing";
	public static final String CMD_INSERT_UNICODE_DING = "insertunicodedingbat";
	public static final String CMD_INSERT_UNICODE_SIGS = "insertunicodesignifier";
	public static final String CMD_INSERT_UNICODE_SPEC = "insertunicodespecial";
	public static final String CMD_FORM_INSERT = "insertform";
	public static final String CMD_FORM_TEXTFIELD = "inserttextfield";
	public static final String CMD_FORM_TEXTAREA = "inserttextarea";
	public static final String CMD_FORM_CHECKBOX = "insertcheckbox";
	public static final String CMD_FORM_RADIO = "insertradiobutton";
	public static final String CMD_FORM_PASSWORD = "insertpassword";
	public static final String CMD_FORM_BUTTON = "insertbutton";
	public static final String CMD_FORM_SUBMIT = "insertbuttonsubmit";
	public static final String CMD_FORM_RESET = "insertbuttonreset";
	public static final String CMD_ENTER_PARAGRAPH = "enterkeyparag";
	public static final String CMD_ENTER_BREAK = "enterkeybreak";
	public static final String CMD_SPELLCHECK = "spellcheck";
	public static final String CMD_HELP_ABOUT = "helpabout";
	public static final String CMD_DEBUG_DESCRIBE_DOC = "describedoc";
	public static final String CMD_DEBUG_DESCRIBE_CSS = "describecss";	
	public static final String CMD_DEBUG_CURRENT_TAGS = "whattags";

	// Menu & Tool Key Arrays
	private static Hashtable<String, JMenu>      htMenus = new Hashtable<String, JMenu>();
	private static Hashtable<String, JComponent> htTools = new Hashtable<String, JComponent>();

	protected EkitCoreData data = new EkitCoreData(new HTMLUtilities(this), "Ekit", "...",
			true, "#cccccc", 0, true, false, null, null, false, false, null, ".", 0,
			4, true, new String[] { "html", "htm", "shtml" }, new String[] { "css" }, new String[] { "gif", "jpg", "jpeg", "png" },
			new String[] { "rtf" }, new String[] { "b64" }, new String[] { "ser" }, KeyEvent.CTRL_MASK);

	/** Master Constructor
	  * @param sDocument         [String]  A text or HTML document to load in the editor upon startup.
	  * @param sStyleSheet       [String]  A CSS stylesheet to load in the editor upon startup.
	  * @param sRawDocument      [String]  A document encoded as a String to load in the editor upon startup.
	  * @param sdocSource        [StyledDocument] Optional document specification, using javax.swing.text.StyledDocument.
	  * @param urlStyleSheet     [URL]     A URL reference to the CSS style sheet.
	  * @param includeToolBar    [boolean] Specifies whether the app should include the toolbar(s).
	  * @param showViewSource    [boolean] Specifies whether or not to show the View Source window on startup.
	  * @param showMenuIcons     [boolean] Specifies whether or not to show icon pictures in menus.
	  * @param editModeExclusive [boolean] Specifies whether or not to use exclusive edit mode (recommended on).
	  * @param sLanguage         [String]  The language portion of the Internationalization Locale to run Ekit in.
	  * @param sCountry          [String]  The country portion of the Internationalization Locale to run Ekit in.
	  * @param base64            [boolean] Specifies whether the raw document is Base64 encoded or not.
	  * @param debugMode         [boolean] Specifies whether to show the Debug menu or not.
	  * @param hasSpellChecker   [boolean] Specifies whether or not this uses the SpellChecker module
	  * @param multiBar          [boolean] Specifies whether to use multiple toolbars or one big toolbar.
	  * @param toolbarSeq        [String]  Code string specifying the toolbar buttons to show.
	  * @param keepUnknownTags   [boolean] Specifies whether or not the parser should retain unknown tags.
	  * @param enterBreak        [boolean] Specifies whether the ENTER key should insert breaks instead of paragraph tags.
	  */
	public EkitCore(boolean isParentApplet, String sDocument, String sStyleSheet, String sRawDocument, StyledDocument sdocSource, URL urlStyleSheet, boolean includeToolBar, boolean showViewSource, boolean showMenuIcons, boolean editModeExclusive, String sLanguage, String sCountry, boolean base64, boolean debugMode, boolean hasSpellChecker, boolean multiBar, String toolbarSeq, boolean keepUnknownTags, boolean enterBreak)
	{
		super();

		data.setExclusiveEdit(editModeExclusive);
		data.setPreserveUnknownTags(keepUnknownTags);
		data.setEnterIsBreak(enterBreak);

		data.setFrameHandler(new Frame());

		// Determine if system clipboard is available (SecurityManager version)
/*
		SecurityManager secManager = System.getSecurityManager();
		if(secManager != null)
		{
			try
			{
				secManager.checkSystemClipboardAccess();
				sysClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			}
			catch(SecurityException se)
			{
				sysClipboard = null;
			}
		}
*/

		// Obtain system clipboard if available
		try
		{
			data.setSysClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
		}
		catch(Exception ex)
		{
			data.setSysClipboard(null);
		}

		// Plain text DataFlavor for unformatted paste
		try
		{
			data.setDfPlainText(new DataFlavor("text/plain; class=java.lang.String; charset=Unicode")); // Charsets usually available include Unicode, UTF-16, UTF-8, & US-ASCII
		}
		catch(ClassNotFoundException cnfe)
		{
			// it would be nice to use DataFlavor.plainTextFlavor, but that is deprecated
			// this will not work as desired, but it will prevent errors from being thrown later
			// alternately, we could flag up here that Unformatted Paste is not available and adjust the UI accordingly
			// however, the odds of java.lang.String not being found are pretty slim one imagines
			data.setDfPlainText(DataFlavor.stringFlavor);
		}

		/* Localize for language */
		Translatrix.setBundleName("com.hexidec.ekit.LanguageResources");
		Locale baseLocale = (Locale)null;
		if(sLanguage != null && sCountry != null)
		{
			baseLocale = new Locale(sLanguage, sCountry);
		}
		Translatrix.setLocale(baseLocale);

		/* Initialise system-specific control key value */
		if(!(GraphicsEnvironment.isHeadless()))
		{
			data.setCTRLKEY(Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		}

		/* Create the editor kit, document, and stylesheet */
		data.setJtpMain(new JTextPane());
		data.setHtmlKit(new ExtendedHTMLEditorKit());
		data.setHtmlDoc((ExtendedHTMLDocument)(data.getHtmlKit().createDefaultDocument()));
		data.getHtmlDoc().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		data.getHtmlDoc().setPreservesUnknownTags(data.isPreserveUnknownTags());
		data.setStyleSheet(data.getHtmlDoc().getStyleSheet());
		data.getHtmlKit().setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
		data.getJtpMain().setCursor(new Cursor(Cursor.TEXT_CURSOR));

		/* Set up the text pane */
		data.getJtpMain().setEditorKit(data.getHtmlKit());
		data.getJtpMain().setDocument(data.getHtmlDoc());
		data.getJtpMain().setMargin(new Insets(4, 4, 4, 4));
		data.getJtpMain().addKeyListener(this);
		data.getJtpMain().addFocusListener(this);
//		deleted dead code

		/* Create the source text area */
		if(sdocSource == null)
		{
			data.setJtpSource(new JTextArea());
			data.getJtpSource().setText(data.getJtpMain().getText());
		}
		else
		{
			data.setJtpSource(new JTextArea(sdocSource));
			data.getJtpMain().setText(data.getJtpSource().getText());
		}
		data.getJtpSource().setBackground(new Color(212, 212, 212));
		data.getJtpSource().setSelectionColor(new Color(255, 192, 192));
		data.getJtpSource().setMargin(new Insets(4, 4, 4, 4));
		data.getJtpSource().getDocument().addDocumentListener(this);
		data.getJtpSource().addFocusListener(this);
		data.getJtpSource().setCursor(new Cursor(Cursor.TEXT_CURSOR));
		data.getJtpSource().setColumns(1024);

		/* Add CaretListener for tracking caret location events */
		data.getJtpMain().addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent ce)
			{
				handleCaretPositionChange(ce);
			}
		});

		/* Set up the undo features */
		data.setUndoMngr(new UndoManager());
		data.setUndoAction(new UndoAction());
		data.setRedoAction(new RedoAction());
		data.getJtpMain().getDocument().addUndoableEditListener(new CustomUndoableEditListener());

		/* Insert raw document, if exists */
		if(sRawDocument != null && sRawDocument.length() > 0)
		{
			if(base64)
			{
				data.getJtpMain().setText(Base64Codec.decode(sRawDocument));
			}
			else
			{
				data.getJtpMain().setText(sRawDocument);
			}
		}
		data.getJtpMain().setCaretPosition(0);
		data.getJtpMain().getDocument().addDocumentListener(this);

		/* Import CSS from reference, if exists */
		if(urlStyleSheet != null)
		{
			try
			{
				String currDocText = data.getJtpMain().getText();
				data.setHtmlDoc((ExtendedHTMLDocument)(data.getHtmlKit().createDefaultDocument()));
				data.getHtmlDoc().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
				data.getHtmlDoc().setPreservesUnknownTags(data.isPreserveUnknownTags());
				data.setStyleSheet(data.getHtmlDoc().getStyleSheet());
				BufferedReader br = new BufferedReader(new InputStreamReader(urlStyleSheet.openStream()));
				data.getStyleSheet().loadRules(br, urlStyleSheet);
				br.close();
				data.setHtmlDoc(new ExtendedHTMLDocument(data.getStyleSheet()));
				registerDocument(data.getHtmlDoc());
				data.getJtpMain().setText(currDocText);
				data.getJtpSource().setText(data.getJtpMain().getText());
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
			}
		}

		/* Preload the specified HTML document, if exists */
		if(sDocument != null)
		{
			File defHTML = new File(sDocument);
			if(defHTML.exists())
			{
				try
				{
					openDocument(defHTML);
				}
				catch(Exception e)
				{
					logException("Exception in preloading HTML document", e);
				}
			}
		}

		/* Preload the specified CSS document, if exists */
		if(sStyleSheet != null)
		{
			File defCSS = new File(sStyleSheet);
			if(defCSS.exists())
			{
				try
				{
					openStyleSheet(defCSS);
				}
				catch(Exception e)
				{
					logException("Exception in preloading CSS stylesheet", e);
				}
			}
		}

		/* Collect the actions that the JTextPane is naturally aware of */
		Hashtable<Object, Action> actions = new Hashtable<Object, Action>();
		Action[] actionsArray = data.getJtpMain().getActions();
		for(Action a : actionsArray)
		{
			actions.put(a.getValue(Action.NAME), a);
		}

		/* Create shared actions */
		data.setActionFontBold(new StyledEditorKit.BoldAction());
		data.setActionFontItalic(new StyledEditorKit.ItalicAction());
		data.setActionFontUnderline(new StyledEditorKit.UnderlineAction());
		data.setActionFontStrike(new FormatAction(this, Translatrix.getTranslationString("FontStrike"), HTML.Tag.STRIKE));
		data.setActionFontSuperscript(new FormatAction(this, Translatrix.getTranslationString("FontSuperscript"), HTML.Tag.SUP));
		data.setActionFontSubscript(new FormatAction(this, Translatrix.getTranslationString("FontSubscript"), HTML.Tag.SUB));
		data.setActionListUnordered(new ListAutomationAction(this, Translatrix.getTranslationString("ListUnordered"), HTML.Tag.UL));
		data.setActionListOrdered(new ListAutomationAction(this, Translatrix.getTranslationString("ListOrdered"), HTML.Tag.OL));
		data.setActionSelectFont(new SetFontFamilyAction(this, "[MENUFONTSELECTOR]"));
		data.setActionAlignLeft(new AlignAction(this, Translatrix.getTranslationString("AlignLeft"), StyleConstants.ALIGN_LEFT));
		data.setActionAlignCenter(new AlignAction(this, Translatrix.getTranslationString("AlignCenter"), StyleConstants.ALIGN_CENTER));
		data.setActionAlignRight(new AlignAction(this, Translatrix.getTranslationString("AlignRight"), StyleConstants.ALIGN_RIGHT));
		data.setActionAlignJustified(new AlignAction(this, Translatrix.getTranslationString("AlignJustified"), StyleConstants.ALIGN_JUSTIFIED));
		data.setActionInsertAnchor(new CustomAction(this, Translatrix.getTranslationString("InsertAnchor") + data.getMenuDialog(), HTML.Tag.A));

		/* Build the menus */
		/* FILE Menu */
		data.setjMenuFile(new JMenu(Translatrix.getTranslationString("File")));
		htMenus.put(KEY_MENU_FILE, data.getjMenuFile());
		JMenuItem jmiNew       = new JMenuItem(Translatrix.getTranslationString("NewDocument"));                     jmiNew.setActionCommand(CMD_DOC_NEW);              jmiNew.addActionListener(this);       jmiNew.setAccelerator(KeyStroke.getKeyStroke('N', data.getCTRLKEY(), false));      if(showMenuIcons) { jmiNew.setIcon(getEkitIcon("New")); }; data.getjMenuFile().add(jmiNew);
		JMenuItem jmiNewStyled = new JMenuItem(Translatrix.getTranslationString("NewStyledDocument"));               jmiNewStyled.setActionCommand(CMD_DOC_NEW_STYLED); jmiNewStyled.addActionListener(this); if(showMenuIcons) { jmiNewStyled.setIcon(getEkitIcon("NewStyled")); };   data.getjMenuFile().add(jmiNewStyled);
		JMenuItem jmiOpenHTML  = new JMenuItem(Translatrix.getTranslationString("OpenDocument") + data.getMenuDialog());       jmiOpenHTML.setActionCommand(CMD_DOC_OPEN_HTML);   jmiOpenHTML.addActionListener(this);  jmiOpenHTML.setAccelerator(KeyStroke.getKeyStroke('O', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiOpenHTML.setIcon(getEkitIcon("Open")); }; data.getjMenuFile().add(jmiOpenHTML);
		JMenuItem jmiOpenCSS   = new JMenuItem(Translatrix.getTranslationString("OpenStyle") + data.getMenuDialog());          jmiOpenCSS.setActionCommand(CMD_DOC_OPEN_CSS);     jmiOpenCSS.addActionListener(this);   data.getjMenuFile().add(jmiOpenCSS);
		JMenuItem jmiOpenB64   = new JMenuItem(Translatrix.getTranslationString("OpenBase64Document") + data.getMenuDialog()); jmiOpenB64.setActionCommand(CMD_DOC_OPEN_BASE64);  jmiOpenB64.addActionListener(this);   data.getjMenuFile().add(jmiOpenB64);
		data.getjMenuFile().addSeparator();
		JMenuItem jmiSave      = new JMenuItem(Translatrix.getTranslationString("Save"));                  jmiSave.setActionCommand(CMD_DOC_SAVE);           jmiSave.addActionListener(this);     jmiSave.setAccelerator(KeyStroke.getKeyStroke('S', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiSave.setIcon(getEkitIcon("Save")); }; data.getjMenuFile().add(jmiSave);
		JMenuItem jmiSaveAs    = new JMenuItem(Translatrix.getTranslationString("SaveAs") + data.getMenuDialog());   jmiSaveAs.setActionCommand(CMD_DOC_SAVE_AS);      jmiSaveAs.addActionListener(this);   data.getjMenuFile().add(jmiSaveAs);
		JMenuItem jmiSaveBody  = new JMenuItem(Translatrix.getTranslationString("SaveBody") + data.getMenuDialog()); jmiSaveBody.setActionCommand(CMD_DOC_SAVE_BODY);  jmiSaveBody.addActionListener(this); data.getjMenuFile().add(jmiSaveBody);
		JMenuItem jmiSaveRTF   = new JMenuItem(Translatrix.getTranslationString("SaveRTF") + data.getMenuDialog());  jmiSaveRTF.setActionCommand(CMD_DOC_SAVE_RTF);    jmiSaveRTF.addActionListener(this);  data.getjMenuFile().add(jmiSaveRTF);
		JMenuItem jmiSaveB64   = new JMenuItem(Translatrix.getTranslationString("SaveB64") + data.getMenuDialog());  jmiSaveB64.setActionCommand(CMD_DOC_SAVE_BASE64); jmiSaveB64.addActionListener(this);  data.getjMenuFile().add(jmiSaveB64);
		data.getjMenuFile().addSeparator();
		JMenuItem jmiPrint     = new JMenuItem(Translatrix.getTranslationString("Print")); jmiPrint.setActionCommand(CMD_DOC_PRINT); jmiPrint.addActionListener(this); data.getjMenuFile().add(jmiPrint);
		data.getjMenuFile().addSeparator();
		JMenuItem jmiSerialOut = new JMenuItem(Translatrix.getTranslationString("Serialize") + data.getMenuDialog());   jmiSerialOut.setActionCommand(CMD_DOC_SERIALIZE_OUT); jmiSerialOut.addActionListener(this); data.getjMenuFile().add(jmiSerialOut);
		JMenuItem jmiSerialIn  = new JMenuItem(Translatrix.getTranslationString("ReadFromSer") + data.getMenuDialog()); jmiSerialIn.setActionCommand(CMD_DOC_SERIALIZE_IN);   jmiSerialIn.addActionListener(this);  data.getjMenuFile().add(jmiSerialIn);
		data.getjMenuFile().addSeparator();
		JMenuItem jmiExit      = new JMenuItem(Translatrix.getTranslationString("Exit")); jmiExit.setActionCommand(CMD_EXIT); jmiExit.addActionListener(this); data.getjMenuFile().add(jmiExit);

		/* EDIT Menu */
		data.setjMenuEdit(new JMenu(Translatrix.getTranslationString("Edit")));
		htMenus.put(KEY_MENU_EDIT, data.getjMenuEdit());
		if(data.getSysClipboard() != null)
		{
			// System Clipboard versions of menu commands
			JMenuItem jmiCut   = new JMenuItem(Translatrix.getTranslationString("Cut"));               jmiCut.setActionCommand(CMD_CLIP_CUT);            jmiCut.addActionListener(this);    jmiCut.setAccelerator(KeyStroke.getKeyStroke('X', data.getCTRLKEY(), false));   if(showMenuIcons) { jmiCut.setIcon(getEkitIcon("Cut")); }     data.getjMenuEdit().add(jmiCut);
			JMenuItem jmiCopy  = new JMenuItem(Translatrix.getTranslationString("Copy"));              jmiCopy.setActionCommand(CMD_CLIP_COPY);          jmiCopy.addActionListener(this);   jmiCopy.setAccelerator(KeyStroke.getKeyStroke('C', data.getCTRLKEY(), false));  if(showMenuIcons) { jmiCopy.setIcon(getEkitIcon("Copy")); }   data.getjMenuEdit().add(jmiCopy);
			JMenuItem jmiPaste = new JMenuItem(Translatrix.getTranslationString("Paste"));             jmiPaste.setActionCommand(CMD_CLIP_PASTE);        jmiPaste.addActionListener(this);  jmiPaste.setAccelerator(KeyStroke.getKeyStroke('V', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiPaste.setIcon(getEkitIcon("Paste")); } data.getjMenuEdit().add(jmiPaste);
			JMenuItem jmiPasteX = new JMenuItem(Translatrix.getTranslationString("PasteUnformatted")); jmiPasteX.setActionCommand(CMD_CLIP_PASTE_PLAIN); jmiPasteX.addActionListener(this); jmiPasteX.setAccelerator(KeyStroke.getKeyStroke('V', data.getCTRLKEY() + KeyEvent.SHIFT_MASK, false)); if(showMenuIcons) { jmiPasteX.setIcon(getEkitIcon("PasteUnformatted")); } data.getjMenuEdit().add(jmiPasteX);
		}
		else
		{
			// DefaultEditorKit versions of menu commands
			JMenuItem jmiCut   = new JMenuItem(new DefaultEditorKit.CutAction());   jmiCut.setText(Translatrix.getTranslationString("Cut"));             jmiCut.setAccelerator(KeyStroke.getKeyStroke('X', data.getCTRLKEY(), false));   if(showMenuIcons) { jmiCut.setIcon(getEkitIcon("Cut")); }     data.getjMenuEdit().add(jmiCut);
			JMenuItem jmiCopy  = new JMenuItem(new DefaultEditorKit.CopyAction());  jmiCopy.setText(Translatrix.getTranslationString("Copy"));           jmiCopy.setAccelerator(KeyStroke.getKeyStroke('C', data.getCTRLKEY(), false));  if(showMenuIcons) { jmiCopy.setIcon(getEkitIcon("Copy")); }   data.getjMenuEdit().add(jmiCopy);
			JMenuItem jmiPaste = new JMenuItem(new DefaultEditorKit.PasteAction()); jmiPaste.setText(Translatrix.getTranslationString("Paste"));         jmiPaste.setAccelerator(KeyStroke.getKeyStroke('V', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiPaste.setIcon(getEkitIcon("Paste")); } data.getjMenuEdit().add(jmiPaste);
			JMenuItem jmiPasteX = new JMenuItem(Translatrix.getTranslationString("PasteUnformatted")); jmiPasteX.setActionCommand(CMD_CLIP_PASTE_PLAIN); jmiPasteX.addActionListener(this); jmiPasteX.setAccelerator(KeyStroke.getKeyStroke('V', data.getCTRLKEY() + KeyEvent.SHIFT_MASK, false)); if(showMenuIcons) { jmiPasteX.setIcon(getEkitIcon("PasteUnformatted")); } data.getjMenuEdit().add(jmiPasteX);
		}
		data.getjMenuEdit().addSeparator();
		JMenuItem jmiUndo    = new JMenuItem(data.getUndoAction()); jmiUndo.setAccelerator(KeyStroke.getKeyStroke('Z', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiUndo.setIcon(getEkitIcon("Undo")); } data.getjMenuEdit().add(jmiUndo);
		JMenuItem jmiRedo    = new JMenuItem(data.getRedoAction()); jmiRedo.setAccelerator(KeyStroke.getKeyStroke('Y', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiRedo.setIcon(getEkitIcon("Redo")); } data.getjMenuEdit().add(jmiRedo);
		data.getjMenuEdit().addSeparator();
		JMenuItem jmiSelAll  = new JMenuItem((Action)actions.get(DefaultEditorKit.selectAllAction));       jmiSelAll.setText(Translatrix.getTranslationString("SelectAll"));        jmiSelAll.setAccelerator(KeyStroke.getKeyStroke('A', data.getCTRLKEY(), false)); data.getjMenuEdit().add(jmiSelAll);
		JMenuItem jmiSelPara = new JMenuItem((Action)actions.get(DefaultEditorKit.selectParagraphAction)); jmiSelPara.setText(Translatrix.getTranslationString("SelectParagraph")); data.getjMenuEdit().add(jmiSelPara);
		JMenuItem jmiSelLine = new JMenuItem((Action)actions.get(DefaultEditorKit.selectLineAction));      jmiSelLine.setText(Translatrix.getTranslationString("SelectLine"));      data.getjMenuEdit().add(jmiSelLine);
		JMenuItem jmiSelWord = new JMenuItem((Action)actions.get(DefaultEditorKit.selectWordAction));      jmiSelWord.setText(Translatrix.getTranslationString("SelectWord"));      data.getjMenuEdit().add(jmiSelWord);
		data.getjMenuEdit().addSeparator();
		JMenu jMenuEnterKey  = new JMenu(Translatrix.getTranslationString("EnterKeyMenu"));
		data.setJcbmiEnterKeyParag(new JCheckBoxMenuItem(Translatrix.getTranslationString("EnterKeyParag"), !data.isEnterIsBreak())); data.getJcbmiEnterKeyParag().setActionCommand(CMD_ENTER_PARAGRAPH); data.getJcbmiEnterKeyParag().addActionListener(this); jMenuEnterKey.add(data.getJcbmiEnterKeyParag());
		data.setJcbmiEnterKeyBreak(new JCheckBoxMenuItem(Translatrix.getTranslationString("EnterKeyBreak"), data.isEnterIsBreak()));  data.getJcbmiEnterKeyBreak().setActionCommand(CMD_ENTER_BREAK);     data.getJcbmiEnterKeyBreak().addActionListener(this); jMenuEnterKey.add(data.getJcbmiEnterKeyBreak());
		data.getjMenuEdit().add(jMenuEnterKey);

		/* VIEW Menu */
		data.setjMenuView(new JMenu(Translatrix.getTranslationString("View")));
		htMenus.put(KEY_MENU_VIEW, data.getjMenuView());
		if(includeToolBar)
		{
			if(multiBar)
			{
				data.setjMenuToolbars(new JMenu(Translatrix.getTranslationString("ViewToolbars")));

				data.setJcbmiViewToolbarMain(new JCheckBoxMenuItem(Translatrix.getTranslationString("ViewToolbarMain"), false));
					data.getJcbmiViewToolbarMain().setActionCommand(CMD_TOGGLE_TOOLBAR_MAIN);
					data.getJcbmiViewToolbarMain().addActionListener(this);
					data.getjMenuToolbars().add(data.getJcbmiViewToolbarMain());

				data.setJcbmiViewToolbarFormat(new JCheckBoxMenuItem(Translatrix.getTranslationString("ViewToolbarFormat"), false));
					data.getJcbmiViewToolbarFormat().setActionCommand(CMD_TOGGLE_TOOLBAR_FORMAT);
					data.getJcbmiViewToolbarFormat().addActionListener(this);
					data.getjMenuToolbars().add(data.getJcbmiViewToolbarFormat());

				data.setJcbmiViewToolbarStyles(new JCheckBoxMenuItem(Translatrix.getTranslationString("ViewToolbarStyles"), false));
					data.getJcbmiViewToolbarStyles().setActionCommand(CMD_TOGGLE_TOOLBAR_STYLES);
					data.getJcbmiViewToolbarStyles().addActionListener(this);
					data.getjMenuToolbars().add(data.getJcbmiViewToolbarStyles());

				data.getjMenuView().add(data.getjMenuToolbars());
			}
			else
			{
				data.setJcbmiViewToolbar(new JCheckBoxMenuItem(Translatrix.getTranslationString("ViewToolbar"), false));
					data.getJcbmiViewToolbar().setActionCommand(CMD_TOGGLE_TOOLBAR_SINGLE);
					data.getJcbmiViewToolbar().addActionListener(this);

				data.getjMenuView().add(data.getJcbmiViewToolbar());
			}
		}
		data.setJcbmiViewSource(new JCheckBoxMenuItem(Translatrix.getTranslationString("ViewSource"), false));  data.getJcbmiViewSource().setActionCommand(CMD_TOGGLE_SOURCE_VIEW);     data.getJcbmiViewSource().addActionListener(this);  data.getjMenuView().add(data.getJcbmiViewSource());

		/* FONT Menu */
		data.setjMenuFont(new JMenu(Translatrix.getTranslationString("Font")));
		htMenus.put(KEY_MENU_FONT, data.getjMenuFont());
		JMenuItem jmiBold      = new JMenuItem(data.getActionFontBold());      jmiBold.setText(Translatrix.getTranslationString("FontBold"));           jmiBold.setAccelerator(KeyStroke.getKeyStroke('B', data.getCTRLKEY(), false));      if(showMenuIcons) { jmiBold.setIcon(getEkitIcon("Bold")); }           data.getjMenuFont().add(jmiBold);
		JMenuItem jmiItalic    = new JMenuItem(data.getActionFontItalic());    jmiItalic.setText(Translatrix.getTranslationString("FontItalic"));       jmiItalic.setAccelerator(KeyStroke.getKeyStroke('I', data.getCTRLKEY(), false));    if(showMenuIcons) { jmiItalic.setIcon(getEkitIcon("Italic")); }       data.getjMenuFont().add(jmiItalic);
		JMenuItem jmiUnderline = new JMenuItem(data.getActionFontUnderline()); jmiUnderline.setText(Translatrix.getTranslationString("FontUnderline")); jmiUnderline.setAccelerator(KeyStroke.getKeyStroke('U', data.getCTRLKEY(), false)); if(showMenuIcons) { jmiUnderline.setIcon(getEkitIcon("Underline")); } data.getjMenuFont().add(jmiUnderline);
		JMenuItem jmiStrike    = new JMenuItem(data.getActionFontStrike());    jmiStrike.setText(Translatrix.getTranslationString("FontStrike"));                                                                                 if(showMenuIcons) { jmiStrike.setIcon(getEkitIcon("Strike")); }       data.getjMenuFont().add(jmiStrike);
		JMenuItem jmiSupscript = new JMenuItem(data.getActionFontSuperscript()); if(showMenuIcons) { jmiSupscript.setIcon(getEkitIcon("Super")); } data.getjMenuFont().add(jmiSupscript);
		JMenuItem jmiSubscript = new JMenuItem(data.getActionFontSubscript());   if(showMenuIcons) { jmiSubscript.setIcon(getEkitIcon("Sub")); }   data.getjMenuFont().add(jmiSubscript);
		data.getjMenuFont().addSeparator();
		data.getjMenuFont().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatBig"), HTML.Tag.BIG)));
		data.getjMenuFont().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatSmall"), HTML.Tag.SMALL)));
		JMenu jMenuFontSize = new JMenu(Translatrix.getTranslationString("FontSize"));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize1"), 8)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize2"), 10)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize3"), 12)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize4"), 14)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize5"), 18)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize6"), 24)));
			jMenuFontSize.add(new JMenuItem(new StyledEditorKit.FontSizeAction(Translatrix.getTranslationString("FontSize7"), 32)));
		data.getjMenuFont().add(jMenuFontSize);
		data.getjMenuFont().addSeparator();
		JMenu jMenuFontSub      = new JMenu(Translatrix.getTranslationString("Font"));
		JMenuItem jmiSelectFont = new JMenuItem(data.getActionSelectFont());                              jmiSelectFont.setText(Translatrix.getTranslationString("FontSelect") + data.getMenuDialog()); if(showMenuIcons) { jmiSelectFont.setIcon(getEkitIcon("FontFaces")); }      jMenuFontSub.add(jmiSelectFont);
		JMenuItem jmiSerif      = new JMenuItem((Action)actions.get("font-family-Serif"));      jmiSerif.setText(Translatrix.getTranslationString("FontSerif"));                    jMenuFontSub.add(jmiSerif);
		JMenuItem jmiSansSerif  = new JMenuItem((Action)actions.get("font-family-SansSerif"));  jmiSansSerif.setText(Translatrix.getTranslationString("FontSansserif"));            jMenuFontSub.add(jmiSansSerif);
		JMenuItem jmiMonospaced = new JMenuItem((Action)actions.get("font-family-Monospaced")); jmiMonospaced.setText(Translatrix.getTranslationString("FontMonospaced"));          jMenuFontSub.add(jmiMonospaced);
		data.getjMenuFont().add(jMenuFontSub);
		data.getjMenuFont().addSeparator();
		JMenu jMenuFontColor = new JMenu(Translatrix.getTranslationString("Color"));
			Hashtable<String, String> customAttr = new Hashtable<String, String>(); customAttr.put("color", "black");
			jMenuFontColor.add(new JMenuItem(new CustomAction(this, Translatrix.getTranslationString("CustomColor") + data.getMenuDialog(), HTML.Tag.FONT, customAttr)));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorAqua"),    new Color(  0,255,255))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorBlack"),   new Color(  0,  0,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorBlue"),    new Color(  0,  0,255))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorFuschia"), new Color(255,  0,255))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorGray"),    new Color(128,128,128))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorGreen"),   new Color(  0,128,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorLime"),    new Color(  0,255,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorMaroon"),  new Color(128,  0,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorNavy"),    new Color(  0,  0,128))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorOlive"),   new Color(128,128,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorPurple"),  new Color(128,  0,128))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorRed"),     new Color(255,  0,  0))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorSilver"),  new Color(192,192,192))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorTeal"),    new Color(  0,128,128))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorWhite"),   new Color(255,255,255))));
			jMenuFontColor.add(new JMenuItem(new StyledEditorKit.ForegroundAction(Translatrix.getTranslationString("ColorYellow"),  new Color(255,255,  0))));
		data.getjMenuFont().add(jMenuFontColor);

		/* FORMAT Menu */
		data.setjMenuFormat(new JMenu(Translatrix.getTranslationString("Format")));
		htMenus.put(KEY_MENU_FORMAT, data.getjMenuFormat());
		JMenu jMenuFormatAlign = new JMenu(Translatrix.getTranslationString("Align"));
			JMenuItem jmiAlignLeft = new JMenuItem(data.getActionAlignLeft());           if(showMenuIcons) { jmiAlignLeft.setIcon(getEkitIcon("AlignLeft")); };           jMenuFormatAlign.add(jmiAlignLeft);
			JMenuItem jmiAlignCenter = new JMenuItem(data.getActionAlignCenter());       if(showMenuIcons) { jmiAlignCenter.setIcon(getEkitIcon("AlignCenter")); };       jMenuFormatAlign.add(jmiAlignCenter);
			JMenuItem jmiAlignRight = new JMenuItem(data.getActionAlignRight());         if(showMenuIcons) { jmiAlignRight.setIcon(getEkitIcon("AlignRight")); };         jMenuFormatAlign.add(jmiAlignRight);
			JMenuItem jmiAlignJustified = new JMenuItem(data.getActionAlignJustified()); if(showMenuIcons) { jmiAlignJustified.setIcon(getEkitIcon("AlignJustified")); }; jMenuFormatAlign.add(jmiAlignJustified);
		data.getjMenuFormat().add(jMenuFormatAlign);
		data.getjMenuFormat().addSeparator();
		JMenu jMenuFormatHeading = new JMenu(Translatrix.getTranslationString("Heading"));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading1"), HTML.Tag.H1)));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading2"), HTML.Tag.H2)));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading3"), HTML.Tag.H3)));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading4"), HTML.Tag.H4)));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading5"), HTML.Tag.H5)));
			jMenuFormatHeading.add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("Heading6"), HTML.Tag.H6)));
		data.getjMenuFormat().add(jMenuFormatHeading);
		data.getjMenuFormat().addSeparator();
		JMenuItem jmiUList = new JMenuItem(data.getActionListUnordered()); if(showMenuIcons) { jmiUList.setIcon(getEkitIcon("UList")); } data.getjMenuFormat().add(jmiUList);
		JMenuItem jmiOList = new JMenuItem(data.getActionListOrdered());   if(showMenuIcons) { jmiOList.setIcon(getEkitIcon("OList")); } data.getjMenuFormat().add(jmiOList);
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("ListItem"), HTML.Tag.LI)));
		data.getjMenuFormat().addSeparator();
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatBlockquote"), HTML.Tag.BLOCKQUOTE)));
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatPre"), HTML.Tag.PRE)));
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatStrong"), HTML.Tag.STRONG)));
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatEmphasis"), HTML.Tag.EM)));
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatTT"), HTML.Tag.TT)));
		data.getjMenuFormat().add(new JMenuItem(new FormatAction(this, Translatrix.getTranslationString("FormatSpan"), HTML.Tag.SPAN)));

		/* INSERT Menu */
		data.setjMenuInsert(new JMenu(Translatrix.getTranslationString("Insert")));
		htMenus.put(KEY_MENU_INSERT, data.getjMenuInsert());
		JMenuItem jmiInsertAnchor = new JMenuItem(data.getActionInsertAnchor()); if(showMenuIcons) { jmiInsertAnchor.setIcon(getEkitIcon("Anchor")); }; data.getjMenuInsert().add(jmiInsertAnchor);
		JMenuItem jmiBreak        = new JMenuItem(Translatrix.getTranslationString("InsertBreak"));  jmiBreak.setActionCommand(CMD_INSERT_BREAK);   jmiBreak.addActionListener(this);   jmiBreak.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_MASK, false)); data.getjMenuInsert().add(jmiBreak);
		JMenuItem jmiNBSP         = new JMenuItem(Translatrix.getTranslationString("InsertNBSP"));   jmiNBSP.setActionCommand(CMD_INSERT_NBSP);     jmiNBSP.addActionListener(this);    jmiNBSP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_MASK, false)); data.getjMenuInsert().add(jmiNBSP);
		JMenu jMenuUnicode        = new JMenu(Translatrix.getTranslationString("InsertUnicodeCharacter")); if(showMenuIcons) { jMenuUnicode.setIcon(getEkitIcon("Unicode")); };
		JMenuItem jmiUnicodeAll   = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterAll") + data.getMenuDialog());  if(showMenuIcons) { jmiUnicodeAll.setIcon(getEkitIcon("Unicode")); }; jmiUnicodeAll.setActionCommand(CMD_INSERT_UNICODE_CHAR);      jmiUnicodeAll.addActionListener(this);   jMenuUnicode.add(jmiUnicodeAll);
		JMenuItem jmiUnicodeMath  = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterMath") + data.getMenuDialog()); if(showMenuIcons) { jmiUnicodeMath.setIcon(getEkitIcon("Math")); };   jmiUnicodeMath.setActionCommand(CMD_INSERT_UNICODE_MATH); jmiUnicodeMath.addActionListener(this);  jMenuUnicode.add(jmiUnicodeMath);
		JMenuItem jmiUnicodeDraw  = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterDraw") + data.getMenuDialog()); if(showMenuIcons) { jmiUnicodeDraw.setIcon(getEkitIcon("Draw")); };   jmiUnicodeDraw.setActionCommand(CMD_INSERT_UNICODE_DRAW); jmiUnicodeDraw.addActionListener(this);  jMenuUnicode.add(jmiUnicodeDraw);
		JMenuItem jmiUnicodeDing  = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterDing") + data.getMenuDialog()); jmiUnicodeDing.setActionCommand(CMD_INSERT_UNICODE_DING); jmiUnicodeDing.addActionListener(this);  jMenuUnicode.add(jmiUnicodeDing);
		JMenuItem jmiUnicodeSigs  = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterSigs") + data.getMenuDialog()); jmiUnicodeSigs.setActionCommand(CMD_INSERT_UNICODE_SIGS); jmiUnicodeSigs.addActionListener(this);  jMenuUnicode.add(jmiUnicodeSigs);
		JMenuItem jmiUnicodeSpec  = new JMenuItem(Translatrix.getTranslationString("InsertUnicodeCharacterSpec") + data.getMenuDialog()); jmiUnicodeSpec.setActionCommand(CMD_INSERT_UNICODE_SPEC); jmiUnicodeSpec.addActionListener(this);  jMenuUnicode.add(jmiUnicodeSpec);
		data.getjMenuInsert().add(jMenuUnicode);
		JMenuItem jmiHRule        = new JMenuItem(Translatrix.getTranslationString("InsertHorizontalRule")); jmiHRule.setActionCommand(CMD_INSERT_HR); jmiHRule.addActionListener(this); data.getjMenuInsert().add(jmiHRule);
		data.getjMenuInsert().addSeparator();
		if(!isParentApplet)
		{
			JMenuItem jmiImageLocal = new JMenuItem(Translatrix.getTranslationString("InsertLocalImage") + data.getMenuDialog());  jmiImageLocal.setActionCommand(CMD_INSERT_IMAGE_LOCAL); jmiImageLocal.addActionListener(this); data.getjMenuInsert().add(jmiImageLocal);
		}
		JMenuItem jmiImageURL     = new JMenuItem(Translatrix.getTranslationString("InsertURLImage") + data.getMenuDialog());    jmiImageURL.setActionCommand(CMD_INSERT_IMAGE_URL);     jmiImageURL.addActionListener(this);   data.getjMenuInsert().add(jmiImageURL);

		/* TABLE Menu */
		data.setjMenuTable(new JMenu(Translatrix.getTranslationString("Table")));
		htMenus.put(KEY_MENU_TABLE, data.getjMenuTable());
		JMenuItem jmiTable       = new JMenuItem(Translatrix.getTranslationString("InsertTable") + data.getMenuDialog()); if(showMenuIcons) { jmiTable.setIcon(getEkitIcon("TableCreate")); }; jmiTable.setActionCommand(CMD_TABLE_INSERT);             jmiTable.addActionListener(this);       data.getjMenuTable().add(jmiTable);
		data.getjMenuTable().addSeparator();
		JMenuItem jmiEditTable	 = new JMenuItem(Translatrix.getTranslationString("TableEdit") + data.getMenuDialog()); if(showMenuIcons) { jmiEditTable.setIcon(getEkitIcon("TableEdit")); } jmiEditTable.setActionCommand(CMD_TABLE_EDIT);	jmiEditTable.addActionListener(this);	data.getjMenuTable().add(jmiEditTable);
		JMenuItem jmiEditCell	 = new JMenuItem(Translatrix.getTranslationString("TableCellEdit") + data.getMenuDialog()); if(showMenuIcons) { jmiEditCell.setIcon(getEkitIcon("CellEdit")); } jmiEditCell.setActionCommand(CMD_TABLE_CELL_EDIT);	jmiEditCell.addActionListener(this);	data.getjMenuTable().add(jmiEditCell);
		data.getjMenuTable().addSeparator();
		JMenuItem jmiTableRow    = new JMenuItem(Translatrix.getTranslationString("InsertTableRow"));           if(showMenuIcons) { jmiTableRow.setIcon(getEkitIcon("InsertRow")); }; jmiTableRow.setActionCommand(CMD_TABLE_ROW_INSERT);       jmiTableRow.addActionListener(this);    data.getjMenuTable().add(jmiTableRow);
		JMenuItem jmiTableCol    = new JMenuItem(Translatrix.getTranslationString("InsertTableColumn"));        if(showMenuIcons) { jmiTableCol.setIcon(getEkitIcon("InsertColumn")); }; jmiTableCol.setActionCommand(CMD_TABLE_COLUMN_INSERT);    jmiTableCol.addActionListener(this);    data.getjMenuTable().add(jmiTableCol);
		data.getjMenuTable().addSeparator();
		JMenuItem jmiTableRowDel = new JMenuItem(Translatrix.getTranslationString("DeleteTableRow"));           if(showMenuIcons) { jmiTableRowDel.setIcon(getEkitIcon("DeleteRow")); }; jmiTableRowDel.setActionCommand(CMD_TABLE_ROW_DELETE);    jmiTableRowDel.addActionListener(this); data.getjMenuTable().add(jmiTableRowDel);
		JMenuItem jmiTableColDel = new JMenuItem(Translatrix.getTranslationString("DeleteTableColumn"));        if(showMenuIcons) { jmiTableColDel.setIcon(getEkitIcon("DeleteColumn")); }; jmiTableColDel.setActionCommand(CMD_TABLE_COLUMN_DELETE); jmiTableColDel.addActionListener(this); data.getjMenuTable().add(jmiTableColDel);

		/* FORMS Menu */
		data.setjMenuForms(new JMenu(Translatrix.getTranslationString("Forms")));
		htMenus.put(KEY_MENU_FORMS, data.getjMenuForms());
		JMenuItem jmiFormInsertForm   = new JMenuItem(Translatrix.getTranslationString("FormInsertForm")); jmiFormInsertForm.setActionCommand(CMD_FORM_INSERT);     jmiFormInsertForm.addActionListener(this); data.getjMenuForms().add(jmiFormInsertForm);
		data.getjMenuForms().addSeparator();
		JMenuItem jmiFormTextfield    = new JMenuItem(Translatrix.getTranslationString("FormTextfield"));  jmiFormTextfield.setActionCommand(CMD_FORM_TEXTFIELD); jmiFormTextfield.addActionListener(this);  data.getjMenuForms().add(jmiFormTextfield);
		JMenuItem jmiFormTextarea     = new JMenuItem(Translatrix.getTranslationString("FormTextarea"));   jmiFormTextarea.setActionCommand(CMD_FORM_TEXTAREA);   jmiFormTextarea.addActionListener(this);   data.getjMenuForms().add(jmiFormTextarea);
		JMenuItem jmiFormCheckbox     = new JMenuItem(Translatrix.getTranslationString("FormCheckbox"));   jmiFormCheckbox.setActionCommand(CMD_FORM_CHECKBOX);   jmiFormCheckbox.addActionListener(this);   data.getjMenuForms().add(jmiFormCheckbox);
		JMenuItem jmiFormRadio        = new JMenuItem(Translatrix.getTranslationString("FormRadio"));      jmiFormRadio.setActionCommand(CMD_FORM_RADIO);   jmiFormRadio.addActionListener(this);      data.getjMenuForms().add(jmiFormRadio);
		JMenuItem jmiFormPassword     = new JMenuItem(Translatrix.getTranslationString("FormPassword"));   jmiFormPassword.setActionCommand(CMD_FORM_PASSWORD);   jmiFormPassword.addActionListener(this);   data.getjMenuForms().add(jmiFormPassword);
		data.getjMenuForms().addSeparator();
		JMenuItem jmiFormButton       = new JMenuItem(Translatrix.getTranslationString("FormButton"));       jmiFormButton.setActionCommand(CMD_FORM_BUTTON);             jmiFormButton.addActionListener(this);       data.getjMenuForms().add(jmiFormButton);
		JMenuItem jmiFormButtonSubmit = new JMenuItem(Translatrix.getTranslationString("FormButtonSubmit")); jmiFormButtonSubmit.setActionCommand(CMD_FORM_SUBMIT); jmiFormButtonSubmit.addActionListener(this); data.getjMenuForms().add(jmiFormButtonSubmit);
		JMenuItem jmiFormButtonReset  = new JMenuItem(Translatrix.getTranslationString("FormButtonReset"));  jmiFormButtonReset.setActionCommand(CMD_FORM_RESET);   jmiFormButtonReset.addActionListener(this);  data.getjMenuForms().add(jmiFormButtonReset);

		/* TOOLS Menu */
		if(hasSpellChecker)
		{
			data.setjMenuTools(new JMenu(Translatrix.getTranslationString("Tools")));
			htMenus.put(KEY_MENU_TOOLS, data.getjMenuTools());
			JMenuItem jmiSpellcheck = new JMenuItem(Translatrix.getTranslationString("ToolSpellcheck")); jmiSpellcheck.setActionCommand(CMD_SPELLCHECK); jmiSpellcheck.addActionListener(this); data.getjMenuTools().add(jmiSpellcheck);
		}

		/* SEARCH Menu */
		data.setjMenuSearch(new JMenu(Translatrix.getTranslationString("Search")));
		htMenus.put(KEY_MENU_SEARCH, data.getjMenuSearch());
		JMenuItem jmiFind      = new JMenuItem(Translatrix.getTranslationString("SearchFind"));      if(showMenuIcons) { jmiFind.setIcon(getEkitIcon("Find")); };           jmiFind.setActionCommand(CMD_SEARCH_FIND);           jmiFind.addActionListener(this);      jmiFind.setAccelerator(KeyStroke.getKeyStroke('F', data.getCTRLKEY(), false));      data.getjMenuSearch().add(jmiFind);
		JMenuItem jmiFindAgain = new JMenuItem(Translatrix.getTranslationString("SearchFindAgain")); if(showMenuIcons) { jmiFindAgain.setIcon(getEkitIcon("FindAgain")); }; jmiFindAgain.setActionCommand(CMD_SEARCH_FIND_AGAIN); jmiFindAgain.addActionListener(this); jmiFindAgain.setAccelerator(KeyStroke.getKeyStroke('G', data.getCTRLKEY(), false)); data.getjMenuSearch().add(jmiFindAgain);
		JMenuItem jmiReplace   = new JMenuItem(Translatrix.getTranslationString("SearchReplace"));   if(showMenuIcons) { jmiReplace.setIcon(getEkitIcon("Replace")); };     jmiReplace.setActionCommand(CMD_SEARCH_REPLACE);     jmiReplace.addActionListener(this);   jmiReplace.setAccelerator(KeyStroke.getKeyStroke('R', data.getCTRLKEY(), false));   data.getjMenuSearch().add(jmiReplace);

		/* HELP Menu */
		data.setjMenuHelp(new JMenu(Translatrix.getTranslationString("Help")));
		htMenus.put(KEY_MENU_HELP, data.getjMenuHelp());
		JMenuItem jmiAbout = new JMenuItem(Translatrix.getTranslationString("About")); jmiAbout.setActionCommand(CMD_HELP_ABOUT); jmiAbout.addActionListener(this); data.getjMenuHelp().add(jmiAbout);

		/* DEBUG Menu */
		data.setjMenuDebug(new JMenu(Translatrix.getTranslationString("Debug")));
		htMenus.put(KEY_MENU_DEBUG, data.getjMenuDebug());
		JMenuItem jmiDesc    = new JMenuItem(Translatrix.getTranslationString("DescribeDoc")); jmiDesc.setActionCommand(CMD_DEBUG_DESCRIBE_DOC);       jmiDesc.addActionListener(this);    data.getjMenuDebug().add(jmiDesc);
		JMenuItem jmiDescCSS = new JMenuItem(Translatrix.getTranslationString("DescribeCSS")); jmiDescCSS.setActionCommand(CMD_DEBUG_DESCRIBE_CSS); jmiDescCSS.addActionListener(this); data.getjMenuDebug().add(jmiDescCSS);
		JMenuItem jmiTag     = new JMenuItem(Translatrix.getTranslationString("WhatTags"));    jmiTag.setActionCommand(CMD_DEBUG_CURRENT_TAGS);        jmiTag.addActionListener(this);     data.getjMenuDebug().add(jmiTag);

		/* Create menubar and add menus */
		data.setjMenuBar(new JMenuBar());
		data.getjMenuBar().add(data.getjMenuFile());
		data.getjMenuBar().add(data.getjMenuEdit());
		data.getjMenuBar().add(data.getjMenuView());
		data.getjMenuBar().add(data.getjMenuFont());
		data.getjMenuBar().add(data.getjMenuFormat());
		data.getjMenuBar().add(data.getjMenuSearch());
		data.getjMenuBar().add(data.getjMenuInsert());
		data.getjMenuBar().add(data.getjMenuTable());
		data.getjMenuBar().add(data.getjMenuForms());
		if(data.getjMenuTools() != null) { data.getjMenuBar().add(data.getjMenuTools()); }
		data.getjMenuBar().add(data.getjMenuHelp());
		if(debugMode)
		{
			data.getjMenuBar().add(data.getjMenuDebug());
		}

		/* Create toolbar tool objects */
		data.setJbtnNewHTML(new JButtonNoFocus(getEkitIcon("New")));
			data.getJbtnNewHTML().setToolTipText(Translatrix.getTranslationString("NewDocument"));
			data.getJbtnNewHTML().setActionCommand(CMD_DOC_NEW);
			data.getJbtnNewHTML().addActionListener(this);
			htTools.put(KEY_TOOL_NEW, data.getJbtnNewHTML());
		data.setJbtnNewStyledHTML(new JButtonNoFocus(getEkitIcon("NewStyled")));
			data.getJbtnNewStyledHTML().setToolTipText(Translatrix.getTranslationString("NewStyledDocument"));
			data.getJbtnNewStyledHTML().setActionCommand(CMD_DOC_NEW_STYLED);
			data.getJbtnNewStyledHTML().addActionListener(this);
			htTools.put(KEY_TOOL_NEWSTYLED, data.getJbtnNewStyledHTML());
		data.setJbtnOpenHTML(new JButtonNoFocus(getEkitIcon("Open")));
			data.getJbtnOpenHTML().setToolTipText(Translatrix.getTranslationString("OpenDocument"));
			data.getJbtnOpenHTML().setActionCommand(CMD_DOC_OPEN_HTML);
			data.getJbtnOpenHTML().addActionListener(this);
			htTools.put(KEY_TOOL_OPEN, data.getJbtnOpenHTML());
		data.setJbtnSaveHTML(new JButtonNoFocus(getEkitIcon("Save")));
			data.getJbtnSaveHTML().setToolTipText(Translatrix.getTranslationString("SaveDocument"));
			data.getJbtnSaveHTML().setActionCommand(CMD_DOC_SAVE_AS);
			data.getJbtnSaveHTML().addActionListener(this);
			htTools.put(KEY_TOOL_SAVE, data.getJbtnSaveHTML());
		data.setJbtnPrint(new JButtonNoFocus(getEkitIcon("Print")));
			data.getJbtnPrint().setToolTipText(Translatrix.getTranslationString("PrintDocument"));
			data.getJbtnPrint().setActionCommand(CMD_DOC_PRINT);
			data.getJbtnPrint().addActionListener(this);
			htTools.put(KEY_TOOL_PRINT, data.getJbtnPrint());
//		jbtnCut = new JButtonNoFocus(new DefaultEditorKit.CutAction());
		data.setJbtnCut(new JButtonNoFocus());
			data.getJbtnCut().setActionCommand(CMD_CLIP_CUT);
			data.getJbtnCut().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					EkitCore.this.actionPerformed(evt);
				}
			});
			data.getJbtnCut().setIcon(getEkitIcon("Cut"));
			data.getJbtnCut().setText(null);
			data.getJbtnCut().setToolTipText(Translatrix.getTranslationString("Cut"));
			htTools.put(KEY_TOOL_CUT, data.getJbtnCut());
//		jbtnCopy = new JButtonNoFocus(new DefaultEditorKit.CopyAction());
		data.setJbtnCopy(new JButtonNoFocus());
			data.getJbtnCopy().setActionCommand(CMD_CLIP_COPY);
			data.getJbtnCopy().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					EkitCore.this.actionPerformed(evt);
				}
			});
			data.getJbtnCopy().setIcon(getEkitIcon("Copy"));
			data.getJbtnCopy().setText(null);
			data.getJbtnCopy().setToolTipText(Translatrix.getTranslationString("Copy"));
			htTools.put(KEY_TOOL_COPY, data.getJbtnCopy());
//		jbtnPaste = new JButtonNoFocus(new DefaultEditorKit.PasteAction());
		data.setJbtnPaste(new JButtonNoFocus());
			data.getJbtnPaste().setActionCommand(CMD_CLIP_PASTE);
			data.getJbtnPaste().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					EkitCore.this.actionPerformed(evt);
				}
			});
			data.getJbtnPaste().setIcon(getEkitIcon("Paste"));
			data.getJbtnPaste().setText(null);
			data.getJbtnPaste().setToolTipText(Translatrix.getTranslationString("Paste"));
			htTools.put(KEY_TOOL_PASTE, data.getJbtnPaste());
		data.setJbtnPasteX(new JButtonNoFocus());
			data.getJbtnPasteX().setActionCommand(CMD_CLIP_PASTE_PLAIN);
			data.getJbtnPasteX().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					EkitCore.this.actionPerformed(evt);
				}
			});
			data.getJbtnPasteX().setIcon(getEkitIcon("PasteUnformatted"));
			data.getJbtnPasteX().setText(null);
			data.getJbtnPasteX().setToolTipText(Translatrix.getTranslationString("PasteUnformatted"));
			htTools.put(KEY_TOOL_PASTEX, data.getJbtnPasteX());
		data.setJbtnUndo(new JButtonNoFocus(data.getUndoAction()));
			data.getJbtnUndo().setIcon(getEkitIcon("Undo"));
			data.getJbtnUndo().setText(null);
			data.getJbtnUndo().setToolTipText(Translatrix.getTranslationString("Undo"));
			htTools.put(KEY_TOOL_UNDO, data.getJbtnUndo());
		data.setJbtnRedo(new JButtonNoFocus(data.getRedoAction()));
			data.getJbtnRedo().setIcon(getEkitIcon("Redo"));
			data.getJbtnRedo().setText(null);
			data.getJbtnRedo().setToolTipText(Translatrix.getTranslationString("Redo"));
			htTools.put(KEY_TOOL_REDO, data.getJbtnRedo());
		data.setJbtnBold(new JButtonNoFocus(data.getActionFontBold()));
			data.getJbtnBold().setIcon(getEkitIcon("Bold"));
			data.getJbtnBold().setText(null);
			data.getJbtnBold().setToolTipText(Translatrix.getTranslationString("FontBold"));
			htTools.put(KEY_TOOL_BOLD, data.getJbtnBold());
		data.setJbtnItalic(new JButtonNoFocus(data.getActionFontItalic()));
			data.getJbtnItalic().setIcon(getEkitIcon("Italic"));
			data.getJbtnItalic().setText(null);
			data.getJbtnItalic().setToolTipText(Translatrix.getTranslationString("FontItalic"));
			htTools.put(KEY_TOOL_ITALIC, data.getJbtnItalic());
		data.setJbtnUnderline(new JButtonNoFocus(data.getActionFontUnderline()));
			data.getJbtnUnderline().setIcon(getEkitIcon("Underline"));
			data.getJbtnUnderline().setText(null);
			data.getJbtnUnderline().setToolTipText(Translatrix.getTranslationString("FontUnderline"));
			htTools.put(KEY_TOOL_UNDERLINE, data.getJbtnUnderline());
		data.setJbtnStrike(new JButtonNoFocus(data.getActionFontStrike()));
			data.getJbtnStrike().setIcon(getEkitIcon("Strike"));
			data.getJbtnStrike().setText(null);
			data.getJbtnStrike().setToolTipText(Translatrix.getTranslationString("FontStrike"));
			htTools.put(KEY_TOOL_STRIKE, data.getJbtnStrike());
		data.setJbtnSuperscript(new JButtonNoFocus(data.getActionFontSuperscript()));
			data.getJbtnSuperscript().setIcon(getEkitIcon("Super"));
			data.getJbtnSuperscript().setText(null);
			data.getJbtnSuperscript().setToolTipText(Translatrix.getTranslationString("FontSuperscript"));
			htTools.put(KEY_TOOL_SUPER, data.getJbtnSuperscript());
		data.setJbtnSubscript(new JButtonNoFocus(data.getActionFontSubscript()));
			data.getJbtnSubscript().setIcon(getEkitIcon("Sub"));
			data.getJbtnSubscript().setText(null);
			data.getJbtnSubscript().setToolTipText(Translatrix.getTranslationString("FontSubscript"));
			htTools.put(KEY_TOOL_SUB, data.getJbtnSubscript());
		data.setJbtnUList(new JButtonNoFocus(data.getActionListUnordered()));
			data.getJbtnUList().setIcon(getEkitIcon("UList"));
			data.getJbtnUList().setText(null);
			data.getJbtnUList().setToolTipText(Translatrix.getTranslationString("ListUnordered"));
			htTools.put(KEY_TOOL_ULIST, data.getJbtnUList());
		data.setJbtnOList(new JButtonNoFocus(data.getActionListOrdered()));
			data.getJbtnOList().setIcon(getEkitIcon("OList"));
			data.getJbtnOList().setText(null);
			data.getJbtnOList().setToolTipText(Translatrix.getTranslationString("ListOrdered"));
			htTools.put(KEY_TOOL_OLIST, data.getJbtnOList());
		data.setJbtnAlignLeft(new JButtonNoFocus(data.getActionAlignLeft()));
			data.getJbtnAlignLeft().setIcon(getEkitIcon("AlignLeft"));
			data.getJbtnAlignLeft().setText(null);
			data.getJbtnAlignLeft().setToolTipText(Translatrix.getTranslationString("AlignLeft"));
			htTools.put(KEY_TOOL_ALIGNL, data.getJbtnAlignLeft());
		data.setJbtnAlignCenter(new JButtonNoFocus(data.getActionAlignCenter()));
			data.getJbtnAlignCenter().setIcon(getEkitIcon("AlignCenter"));
			data.getJbtnAlignCenter().setText(null);
			data.getJbtnAlignCenter().setToolTipText(Translatrix.getTranslationString("AlignCenter"));
			htTools.put(KEY_TOOL_ALIGNC, data.getJbtnAlignCenter());
		data.setJbtnAlignRight(new JButtonNoFocus(data.getActionAlignRight()));
			data.getJbtnAlignRight().setIcon(getEkitIcon("AlignRight"));
			data.getJbtnAlignRight().setText(null);
			data.getJbtnAlignRight().setToolTipText(Translatrix.getTranslationString("AlignRight"));
			htTools.put(KEY_TOOL_ALIGNR, data.getJbtnAlignRight());
		data.setJbtnAlignJustified(new JButtonNoFocus(data.getActionAlignJustified()));
			data.getJbtnAlignJustified().setIcon(getEkitIcon("AlignJustified"));
			data.getJbtnAlignJustified().setText(null);
			data.getJbtnAlignJustified().setToolTipText(Translatrix.getTranslationString("AlignJustified"));
			htTools.put(KEY_TOOL_ALIGNJ, data.getJbtnAlignJustified());
		data.setJbtnUnicode(new JButtonNoFocus());
			data.getJbtnUnicode().setActionCommand(CMD_INSERT_UNICODE_CHAR);
			data.getJbtnUnicode().addActionListener(this);
			data.getJbtnUnicode().setIcon(getEkitIcon("Unicode"));
			data.getJbtnUnicode().setText(null);
			data.getJbtnUnicode().setToolTipText(Translatrix.getTranslationString("ToolUnicode"));
			htTools.put(KEY_TOOL_UNICODE, data.getJbtnUnicode());
		data.setJbtnUnicodeMath(new JButtonNoFocus());
			data.getJbtnUnicodeMath().setActionCommand(CMD_INSERT_UNICODE_MATH);
			data.getJbtnUnicodeMath().addActionListener(this);
			data.getJbtnUnicodeMath().setIcon(getEkitIcon("Math"));
			data.getJbtnUnicodeMath().setText(null);
			data.getJbtnUnicodeMath().setToolTipText(Translatrix.getTranslationString("ToolUnicodeMath"));
			htTools.put(KEY_TOOL_UNIMATH, data.getJbtnUnicodeMath());
		data.setJbtnFind(new JButtonNoFocus());
			data.getJbtnFind().setActionCommand(CMD_SEARCH_FIND);
			data.getJbtnFind().addActionListener(this);
			data.getJbtnFind().setIcon(getEkitIcon("Find"));
			data.getJbtnFind().setText(null);
			data.getJbtnFind().setToolTipText(Translatrix.getTranslationString("SearchFind"));
			htTools.put(KEY_TOOL_FIND, data.getJbtnFind());
		data.setJbtnAnchor(new JButtonNoFocus(data.getActionInsertAnchor()));
			data.getJbtnAnchor().setIcon(getEkitIcon("Anchor"));
			data.getJbtnAnchor().setText(null);
			data.getJbtnAnchor().setToolTipText(Translatrix.getTranslationString("ToolAnchor"));
			htTools.put(KEY_TOOL_ANCHOR, data.getJbtnAnchor());
		data.setJtbtnViewSource(new JToggleButtonNoFocus(getEkitIcon("Source")));
			data.getJtbtnViewSource().setText(null);
			data.getJtbtnViewSource().setToolTipText(Translatrix.getTranslationString("ViewSource"));
			data.getJtbtnViewSource().setActionCommand(CMD_TOGGLE_SOURCE_VIEW);
			data.getJtbtnViewSource().addActionListener(this);
			data.getJtbtnViewSource().setPreferredSize(data.getJbtnAnchor().getPreferredSize());
			data.getJtbtnViewSource().setMinimumSize(data.getJbtnAnchor().getMinimumSize());
			data.getJtbtnViewSource().setMaximumSize(data.getJbtnAnchor().getMaximumSize());
			htTools.put(KEY_TOOL_SOURCE, data.getJtbtnViewSource());
		data.setJcmbStyleSelector(new JComboBoxNoFocus());
			data.getJcmbStyleSelector().setToolTipText(Translatrix.getTranslationString("PickCSSStyle"));
			data.getJcmbStyleSelector().setAction(new StylesAction(data.getJcmbStyleSelector()));
			htTools.put(KEY_TOOL_STYLES, data.getJcmbStyleSelector());
		String[] fonts = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			Vector<String> vcFontnames = new Vector<String>(fonts.length + 1);
			vcFontnames.add(Translatrix.getTranslationString("SelectorToolFontsDefaultFont"));
			for(String fontname : fonts)
			{
				vcFontnames.add(fontname);
			}
			Collections.sort(vcFontnames);
			data.setJcmbFontSelector(new JComboBoxNoFocus(vcFontnames));
			data.getJcmbFontSelector().setAction(new SetFontFamilyAction(this, "[EKITFONTSELECTOR]"));
			htTools.put(KEY_TOOL_FONTS, data.getJcmbFontSelector());
		data.setJbtnInsertTable(new JButtonNoFocus());
			data.getJbtnInsertTable().setActionCommand(CMD_TABLE_INSERT);
			data.getJbtnInsertTable().addActionListener(this);
			data.getJbtnInsertTable().setIcon(getEkitIcon("TableCreate"));
			data.getJbtnInsertTable().setText(null);
			data.getJbtnInsertTable().setToolTipText(Translatrix.getTranslationString("InsertTable"));
			htTools.put(KEY_TOOL_INSTABLE, data.getJbtnInsertTable());
		data.setJbtnEditTable(new JButtonNoFocus());
			data.getJbtnEditTable().setActionCommand(CMD_TABLE_EDIT);
			data.getJbtnEditTable().addActionListener(this);
			data.getJbtnEditTable().setIcon(getEkitIcon("TableEdit"));
			data.getJbtnEditTable().setText(null);
			data.getJbtnEditTable().setToolTipText(Translatrix.getTranslationString("TableEdit"));
			htTools.put(KEY_TOOL_EDITTABLE, data.getJbtnEditTable());
		data.setJbtnEditCell(new JButtonNoFocus());
			data.getJbtnEditCell().setActionCommand(CMD_TABLE_CELL_EDIT);
			data.getJbtnEditCell().addActionListener(this);
			data.getJbtnEditCell().setIcon(getEkitIcon("CellEdit"));
			data.getJbtnEditCell().setText(null);
			data.getJbtnEditCell().setToolTipText(Translatrix.getTranslationString("TableCellEdit"));
			htTools.put(KEY_TOOL_EDITCELL, data.getJbtnEditCell());			
		data.setJbtnInsertRow(new JButtonNoFocus());
			data.getJbtnInsertRow().setActionCommand(CMD_TABLE_ROW_INSERT);
			data.getJbtnInsertRow().addActionListener(this);
			data.getJbtnInsertRow().setIcon(getEkitIcon("InsertRow"));
			data.getJbtnInsertRow().setText(null);
			data.getJbtnInsertRow().setToolTipText(Translatrix.getTranslationString("InsertTableRow"));
			htTools.put(KEY_TOOL_INSERTROW, data.getJbtnInsertRow());
		data.setJbtnInsertColumn(new JButtonNoFocus());
			data.getJbtnInsertColumn().setActionCommand(CMD_TABLE_COLUMN_INSERT);
			data.getJbtnInsertColumn().addActionListener(this);
			data.getJbtnInsertColumn().setIcon(getEkitIcon("InsertColumn"));
			data.getJbtnInsertColumn().setText(null);
			data.getJbtnInsertColumn().setToolTipText(Translatrix.getTranslationString("InsertTableColumn"));
			htTools.put(KEY_TOOL_INSERTCOL, data.getJbtnInsertColumn());
		data.setJbtnDeleteRow(new JButtonNoFocus());
			data.getJbtnDeleteRow().setActionCommand(CMD_TABLE_ROW_DELETE);
			data.getJbtnDeleteRow().addActionListener(this);
			data.getJbtnDeleteRow().setIcon(getEkitIcon("DeleteRow"));
			data.getJbtnDeleteRow().setText(null);
			data.getJbtnDeleteRow().setToolTipText(Translatrix.getTranslationString("DeleteTableRow"));
			htTools.put(KEY_TOOL_DELETEROW, data.getJbtnDeleteRow());
		data.setJbtnDeleteColumn(new JButtonNoFocus());
			data.getJbtnDeleteColumn().setActionCommand(CMD_TABLE_COLUMN_DELETE);
			data.getJbtnDeleteColumn().addActionListener(this);
			data.getJbtnDeleteColumn().setIcon(getEkitIcon("DeleteColumn"));
			data.getJbtnDeleteColumn().setText(null);
			data.getJbtnDeleteColumn().setToolTipText(Translatrix.getTranslationString("DeleteTableColumn"));
			htTools.put(KEY_TOOL_DELETECOL, data.getJbtnDeleteColumn());

		/* Create the toolbar */
		if(multiBar)
		{
			data.setjToolBarMain(new JToolBar(JToolBar.HORIZONTAL));
			data.getjToolBarMain().setFloatable(false);
			data.setjToolBarFormat(new JToolBar(JToolBar.HORIZONTAL));
			data.getjToolBarFormat().setFloatable(false);
			data.setjToolBarStyles(new JToolBar(JToolBar.HORIZONTAL));
			data.getjToolBarStyles().setFloatable(false);

			initializeMultiToolbars(toolbarSeq);

			// fix the weird size preference of toggle buttons
			data.getJtbtnViewSource().setPreferredSize(data.getJbtnAnchor().getPreferredSize());
			data.getJtbtnViewSource().setMinimumSize(data.getJbtnAnchor().getMinimumSize());
			data.getJtbtnViewSource().setMaximumSize(data.getJbtnAnchor().getMaximumSize());
		}
		else if(includeToolBar)
		{
			data.setjToolBar(new JToolBar(JToolBar.HORIZONTAL));
			data.getjToolBar().setFloatable(false);

			initializeSingleToolbar(toolbarSeq);

			// fix the weird size preference of toggle buttons
			data.getJtbtnViewSource().setPreferredSize(data.getJbtnAnchor().getPreferredSize());
			data.getJtbtnViewSource().setMinimumSize(data.getJbtnAnchor().getMinimumSize());
			data.getJtbtnViewSource().setMaximumSize(data.getJbtnAnchor().getMaximumSize());
		}

		/* Create the scroll area for the text pane */
		JScrollPane jspViewport = new JScrollPane(data.getJtpMain());
		jspViewport.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspViewport.setPreferredSize(new Dimension(400, 400));
		jspViewport.setMinimumSize(new Dimension(32, 32));

		/* Create the scroll area for the source viewer */
		data.setJspSource(new JScrollPane(data.getJtpSource()));
		data.getJspSource().setPreferredSize(new Dimension(400, 100));
		data.getJspSource().setMinimumSize(new Dimension(32, 32));

		data.setJspltDisplay(new JSplitPane(JSplitPane.VERTICAL_SPLIT));
		data.getJspltDisplay().setTopComponent(jspViewport);
		if(showViewSource)
		{
			data.getJspltDisplay().setBottomComponent(data.getJspSource());
		}
		else
		{
			data.getJspltDisplay().setBottomComponent(null);
		}

		data.setiSplitPos(data.getJspltDisplay().getDividerLocation());

		registerDocumentStyles();

		/* Add the components to the app */
		this.setLayout(new BorderLayout());
		this.add(data.getJspltDisplay(), BorderLayout.CENTER);
	}

	/** Master Constructor from versions 1.3 and earlier
	  * @param sDocument         [String]  A text or HTML document to load in the editor upon startup.
	  * @param sStyleSheet       [String]  A CSS stylesheet to load in the editor upon startup.
	  * @param sRawDocument      [String]  A document encoded as a String to load in the editor upon startup.
	  * @param sdocSource        [StyledDocument] Optional document specification, using javax.swing.text.StyledDocument.
	  * @param urlStyleSheet     [URL]     A URL reference to the CSS style sheet.
	  * @param includeToolBar    [boolean] Specifies whether the app should include the toolbar(s).
	  * @param showViewSource    [boolean] Specifies whether or not to show the View Source window on startup.
	  * @param showMenuIcons     [boolean] Specifies whether or not to show icon pictures in menus.
	  * @param editModeExclusive [boolean] Specifies whether or not to use exclusive edit mode (recommended on).
	  * @param sLanguage         [String]  The language portion of the Internationalization Locale to run Ekit in.
	  * @param sCountry          [String]  The country portion of the Internationalization Locale to run Ekit in.
	  * @param base64            [boolean] Specifies whether the raw document is Base64 encoded or not.
	  * @param debugMode         [boolean] Specifies whether to show the Debug menu or not.
	  * @param hasSpellChecker   [boolean] Specifies whether or not this uses the SpellChecker module
	  * @param multiBar          [boolean] Specifies whether to use multiple toolbars or one big toolbar.
	  * @param toolbarSeq        [String]  Code string specifying the toolbar buttons to show.
	  * @param enterBreak        [boolean] Specifies whether the ENTER key should insert breaks instead of paragraph tags.
	  */
	public EkitCore(boolean isParentApplet, String sDocument, String sStyleSheet, String sRawDocument, StyledDocument sdocSource, URL urlStyleSheet, boolean includeToolBar, boolean showViewSource, boolean showMenuIcons, boolean editModeExclusive, String sLanguage, String sCountry, boolean base64, boolean debugMode, boolean hasSpellChecker, boolean multiBar, String toolbarSeq, boolean enterBreak)
	{
		this(isParentApplet, sDocument, sStyleSheet, sRawDocument, sdocSource, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, debugMode, hasSpellChecker, multiBar, toolbarSeq, false, enterBreak);
	}

	/** Raw/Base64 Document & Style Sheet URL Constructor (Ideal for EkitApplet)
	  * @param sRawDocument      [String]  A document encoded as a String to load in the editor upon startup.
	  * @param sRawDocument      [String]  A document encoded as a String to load in the editor upon startup.
	  * @param includeToolBar    [boolean] Specifies whether the app should include the toolbar(s).
	  * @param showViewSource    [boolean] Specifies whether or not to show the View Source window on startup.
	  * @param showMenuIcons     [boolean] Specifies whether or not to show icon pictures in menus.
	  * @param editModeExclusive [boolean] Specifies whether or not to use exclusive edit mode (recommended on).
	  * @param sLanguage         [String]  The language portion of the Internationalization Locale to run Ekit in.
	  * @param sCountry          [String]  The country portion of the Internationalization Locale to run Ekit in.
	  * @param base64            [boolean] Specifies whether the raw document is Base64 encoded or not.
	  * @param hasSpellChecker   [boolean] Specifies whether or not this uses the SpellChecker module
	  * @param multiBar          [boolean] Specifies whether to use multiple toolbars or one big toolbar.
	  * @param toolbarSeq        [String]  Code string specifying the toolbar buttons to show.
	  * @param enterBreak        [boolean] Specifies whether the ENTER key should insert breaks instead of paragraph tags.
	  */
	public EkitCore(boolean isParentApplet, String sRawDocument, URL urlStyleSheet, boolean includeToolBar, boolean showViewSource, boolean showMenuIcons, boolean editModeExclusive, String sLanguage, String sCountry, boolean base64, boolean hasSpellChecker, boolean multiBar, String toolbarSeq, boolean enterBreak)
	{
		this(isParentApplet, null, null, sRawDocument, (StyledDocument)null, urlStyleSheet, includeToolBar, showViewSource, showMenuIcons, editModeExclusive, sLanguage, sCountry, base64, false, hasSpellChecker, multiBar, toolbarSeq, enterBreak);
	}

	/** Parent Only Specified Constructor
	  */
	public EkitCore(boolean isParentApplet)
	{
		this(isParentApplet, (String)null, (String)null, (String)null, (StyledDocument)null, (URL)null, true, false, true, true, (String)null, (String)null, false, false, false, true, TOOLBAR_DEFAULT_MULTI, false);
	}

	/** Empty Constructor
	  */
	public EkitCore()
	{
		this(false);
	}

	/* ActionListener method */
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			String command = ae.getActionCommand();
			if(command.equals(CMD_DOC_NEW) || command.equals(CMD_DOC_NEW_STYLED))
			{
				SimpleInfoDialog sidAsk = new SimpleInfoDialog(this.getFrame(), "", true, Translatrix.getTranslationString("AskNewDocument"), SimpleInfoDialog.QUESTION);
				String decision = sidAsk.getDecisionValue();
				if(decision.equals(Translatrix.getTranslationString("DialogAccept")))
				{
					if(data.getStyleSheet() != null && command.equals(CMD_DOC_NEW_STYLED))
					{
						data.setHtmlDoc(new ExtendedHTMLDocument(data.getStyleSheet()));
					}
					else
					{
						data.setHtmlDoc((ExtendedHTMLDocument)(data.getHtmlKit().createDefaultDocument()));
						data.getHtmlDoc().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
						data.getHtmlDoc().setPreservesUnknownTags(data.isPreserveUnknownTags());
					}
//					jtpMain.setText("<HTML><BODY></BODY></HTML>");
					registerDocument(data.getHtmlDoc());
					data.getJtpSource().setText(data.getJtpMain().getText());
					data.setCurrentFile(null);
					updateTitle();
				}
			}
			else if(command.equals(CMD_DOC_OPEN_HTML))
			{
				openDocument(null);
			}
			else if(command.equals(CMD_DOC_OPEN_CSS))
			{
				openStyleSheet(null);
			}
			else if(command.equals(CMD_DOC_OPEN_BASE64))
			{
				openDocumentBase64(null);
			}
			else if(command.equals(CMD_DOC_SAVE))
			{
				writeOut((HTMLDocument)(data.getJtpMain().getDocument()), data.getCurrentFile());
				updateTitle();
			}
			else if(command.equals(CMD_DOC_SAVE_AS))
			{
				writeOut((HTMLDocument)(data.getJtpMain().getDocument()), null);
			}
			else if(command.equals(CMD_DOC_SAVE_BODY))
			{
				writeOutFragment((HTMLDocument)(data.getJtpMain().getDocument()),"body");
			}
			else if(command.equals(CMD_DOC_SAVE_RTF))
			{
				writeOutRTF((StyledDocument)(data.getJtpMain().getStyledDocument()));
			}
			else if(command.equals(CMD_DOC_SAVE_BASE64))
			{
				writeOutBase64(data.getJtpSource().getText());
			}
			else if(command.equals(CMD_CLIP_CUT))
			{
				if(data.getJspSource().isShowing() && data.getJtpSource().hasFocus())
				{
					data.getJtpSource().cut();
				}
				else
				{
					data.getJtpMain().cut();
				}
			}
			else if(command.equals(CMD_CLIP_COPY))
			{
				if(data.getJspSource().isShowing() && data.getJtpSource().hasFocus())
				{
					data.getJtpSource().copy();
				}
				else
				{
					data.getJtpMain().copy();
				}
			}
			else if(command.equals(CMD_CLIP_PASTE))
			{
				if(data.getJspSource().isShowing() && data.getJtpSource().hasFocus())
				{
					data.getJtpSource().paste();
				}
				else
				{
					data.getJtpMain().paste();
				}
			}
			else if(command.equals(CMD_CLIP_PASTE_PLAIN))
			{
				if(data.getJspSource().isShowing() && data.getJtpSource().hasFocus())
				{
					data.getJtpSource().paste();
				}
				else
				{
					try
					{
						if(data.getSysClipboard() != null)
						{
							data.getJtpMain().getDocument().insertString(data.getJtpMain().getCaretPosition(), data.getSysClipboard().getData(data.getDfPlainText()).toString(), (AttributeSet)null);
						}
						else
						{
							data.getJtpMain().getDocument().insertString(data.getJtpMain().getCaretPosition(), Toolkit.getDefaultToolkit().getSystemClipboard().getData(data.getDfPlainText()).toString(), (AttributeSet)null);
						}
			 			refreshOnUpdate();
					}
					catch(Exception e)
					{
						e.printStackTrace(System.out);
					}
				}
			}
			else if(command.equals(CMD_DOC_PRINT))
			{
				DocumentRenderer dr = new DocumentRenderer();
				dr.print(data.getHtmlDoc());
				
			}
			else if(command.equals(CMD_DEBUG_DESCRIBE_DOC))
			{
				System.out.println("------------DOCUMENT------------");
				System.out.println("Content Type : " + data.getJtpMain().getContentType());
				System.out.println("Editor Kit   : " + data.getJtpMain().getEditorKit());
				System.out.println("Doc Tree     :");
				System.out.println("");
				describeDocument(data.getJtpMain().getStyledDocument());
				System.out.println("--------------------------------");
				System.out.println("");
			}
			else if(command.equals(CMD_DEBUG_DESCRIBE_CSS))
			{
				System.out.println("-----------STYLESHEET-----------");
				System.out.println("Stylesheet Rules");
				Enumeration rules = data.getStyleSheet().getStyleNames();
				while(rules.hasMoreElements())
				{
					String ruleName = (String)(rules.nextElement());
					Style styleRule = data.getStyleSheet().getStyle(ruleName);
					System.out.println(styleRule.toString());
				}
				System.out.println("--------------------------------");
				System.out.println("");
			}
			else if(command.equals(CMD_DEBUG_CURRENT_TAGS))
			{
				System.out.println("Caret Position : " + data.getJtpMain().getCaretPosition());
				AttributeSet attribSet = data.getJtpMain().getCharacterAttributes();
				Enumeration attribs = attribSet.getAttributeNames();
				System.out.println("Attributes     : ");
				while(attribs.hasMoreElements())
				{
					String attribName = attribs.nextElement().toString();
					System.out.println("                 " + attribName + " | " + attribSet.getAttribute(attribName));
				}
			}
			else if(command.equals(CMD_TOGGLE_TOOLBAR_SINGLE))
			{
				data.getjToolBar().setVisible(data.getJcbmiViewToolbar().isSelected());
			}
			else if(command.equals(CMD_TOGGLE_TOOLBAR_MAIN))
			{
				data.getjToolBarMain().setVisible(data.getJcbmiViewToolbarMain().isSelected());
			}
			else if(command.equals(CMD_TOGGLE_TOOLBAR_FORMAT))
			{
				data.getjToolBarFormat().setVisible(data.getJcbmiViewToolbarFormat().isSelected());
			}
			else if(command.equals(CMD_TOGGLE_TOOLBAR_STYLES))
			{
				data.getjToolBarStyles().setVisible(data.getJcbmiViewToolbarStyles().isSelected());
			}
			else if(command.equals(CMD_TOGGLE_SOURCE_VIEW))
			{
				toggleSourceWindow();
			}
			else if(command.equals(CMD_DOC_SERIALIZE_OUT))
			{
				serializeOut((HTMLDocument)(data.getJtpMain().getDocument()));
			}
			else if(command.equals(CMD_DOC_SERIALIZE_IN))
			{
				serializeIn();
			}
			else if(command.equals(CMD_TABLE_INSERT))
			{
				String[] fieldNames  = { "rows", "cols", "border", "cellspacing", "cellpadding", "width", "valign" };
 				String[] fieldTypes  = { "text", "text", "text",   "text",        "text",        "text",  "combo" };
				String[] fieldValues = { "3",    "3",    "1",	   "2",	          "4",           "100%",  "top,middle,bottom" };
				insertTable((Hashtable)null, fieldNames, fieldTypes, fieldValues);
			}
			else if(command.equals(CMD_TABLE_EDIT))
			{
				editTable();
			}
			else if(command.equals(CMD_TABLE_CELL_EDIT))
			{
				editCell();
			}
			else if(command.equals(CMD_TABLE_ROW_INSERT))
			{
				insertTableRow();
			}
			else if(command.equals(CMD_TABLE_COLUMN_INSERT))
			{
				insertTableColumn();
			}
			else if(command.equals(CMD_TABLE_ROW_DELETE))
			{
				deleteTableRow();
			}
			else if(command.equals(CMD_TABLE_COLUMN_DELETE))
			{
				deleteTableColumn();
			}
			else if(command.equals(CMD_INSERT_BREAK))
			{
				insertBreak();
			}
			else if(command.equals(CMD_INSERT_NBSP))
			{
				insertNonbreakingSpace();
			}
			else if(command.equals(CMD_INSERT_HR))
			{
				insertHR();
			}
			else if(command.equals(CMD_INSERT_IMAGE_LOCAL))
			{
				insertLocalImage(null);
			}
			else if(command.equals(CMD_INSERT_IMAGE_URL))
			{
				insertURLImage();
			}
			else if(command.equals(CMD_INSERT_UNICODE_CHAR))
			{
				insertUnicode(UnicodeDialog.UNICODE_BASE);
			}
			else if(command.equals(CMD_INSERT_UNICODE_MATH))
			{
				insertUnicode(UnicodeDialog.UNICODE_MATH);
			}
			else if(command.equals(CMD_INSERT_UNICODE_DRAW))
			{
				insertUnicode(UnicodeDialog.UNICODE_DRAW);
			}
			else if(command.equals(CMD_INSERT_UNICODE_DING))
			{
				insertUnicode(UnicodeDialog.UNICODE_DING);
			}
			else if(command.equals(CMD_INSERT_UNICODE_SIGS))
			{
				insertUnicode(UnicodeDialog.UNICODE_SIGS);
			}
			else if(command.equals(CMD_INSERT_UNICODE_SPEC))
			{
				insertUnicode(UnicodeDialog.UNICODE_SPEC);
			}
			else if(command.equals(CMD_FORM_INSERT))
			{
				String[] fieldNames  = { "name", "method",   "enctype" };
				String[] fieldTypes  = { "text", "combo",    "text" };
				String[] fieldValues = { "",     "POST,GET", "text" };
				insertFormElement(HTML.Tag.FORM, "form", (Hashtable)null, fieldNames, fieldTypes, fieldValues, true);
			}
			else if(command.equals(CMD_FORM_TEXTFIELD))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "text");
				String[] fieldNames = { "name", "value", "size", "maxlength" };
				String[] fieldTypes = { "text", "text",  "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_TEXTAREA))
			{
				String[] fieldNames = { "name", "rows", "cols" };
				String[] fieldTypes = { "text", "text", "text" };
				insertFormElement(HTML.Tag.TEXTAREA, "textarea", (Hashtable)null, fieldNames, fieldTypes, true);
			}
			else if(command.equals(CMD_FORM_CHECKBOX))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "checkbox");
				String[] fieldNames = { "name", "checked" };
				String[] fieldTypes = { "text", "bool" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_RADIO))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "radio");
				String[] fieldNames = { "name", "checked" };
				String[] fieldTypes = { "text", "bool" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_PASSWORD))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "password");
				String[] fieldNames = { "name", "value", "size", "maxlength" };
				String[] fieldTypes = { "text", "text",  "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_BUTTON))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "button");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_SUBMIT))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "submit");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_FORM_RESET))
			{
				Hashtable<String, String> htAttribs = new Hashtable<String, String>();
				htAttribs.put("type", "reset");
				String[] fieldNames = { "name", "value" };
				String[] fieldTypes = { "text", "text" };
				insertFormElement(HTML.Tag.INPUT, "input", htAttribs, fieldNames, fieldTypes, false);
			}
			else if(command.equals(CMD_SEARCH_FIND))
			{
				doSearch((String)null, (String)null, false, data.isLastSearchCaseSetting(), data.isLastSearchTopSetting());
			}
			else if(command.equals(CMD_SEARCH_FIND_AGAIN))
			{
				doSearch(data.getLastSearchFindTerm(), (String)null, false, data.isLastSearchCaseSetting(), false);
			}
			else if(command.equals(CMD_SEARCH_REPLACE))
			{
				doSearch((String)null, (String)null, true, data.isLastSearchCaseSetting(), data.isLastSearchTopSetting());
			}
			else if(command.equals(CMD_EXIT))
			{
				this.dispose();
			}
			else if(command.equals(CMD_HELP_ABOUT))
			{
				new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("About"), true, Translatrix.getTranslationString("AboutMessage"), SimpleInfoDialog.INFO);
			}
			else if(command.equals(CMD_ENTER_PARAGRAPH))
			{
				setEnterKeyIsBreak(false);
			}
			else if(command.equals(CMD_ENTER_BREAK))
			{
				setEnterKeyIsBreak(true);
			}
			else if(command.equals(CMD_SPELLCHECK))
			{
				checkDocumentSpelling(data.getJtpMain().getDocument());
			}
		}
		catch(IOException ioe)
		{
			logException("IOException in actionPerformed method", ioe);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorIOException"), SimpleInfoDialog.ERROR);
		}
		catch(BadLocationException ble)
		{
			logException("BadLocationException in actionPerformed method", ble);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorBadLocationException"), SimpleInfoDialog.ERROR);
		}
		catch(NumberFormatException nfe)
		{
			logException("NumberFormatException in actionPerformed method", nfe);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorNumberFormatException"), SimpleInfoDialog.ERROR);
		}
		catch(ClassNotFoundException cnfe)
		{
			logException("ClassNotFound Exception in actionPerformed method", cnfe);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorClassNotFoundException "), SimpleInfoDialog.ERROR);
		}
		catch(RuntimeException re)
		{
			logException("RuntimeException in actionPerformed method", re);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorRuntimeException"), SimpleInfoDialog.ERROR);
		}
	}

	/* KeyListener methods */
	public void keyTyped(KeyEvent ke)
	{
		Element elem;
		int pos = this.getCaretPosition();
		int repos = -1;
		if(ke.getKeyChar() == KeyEvent.VK_BACK_SPACE)
		{
			try
			{
				if(pos > 0)
				{
					if(data.getJtpMain().getSelectedText() != null)
					{
						data.getHtmlUtilities().delete();
						refreshOnUpdate();
						return;
					}
					else
					{
						int sOffset = data.getHtmlDoc().getParagraphElement(pos).getStartOffset();
						if(sOffset == data.getJtpMain().getSelectionStart())
						{
							boolean content = true;
							if(data.getHtmlUtilities().checkParentsTag(HTML.Tag.LI))
							{
								elem = data.getHtmlUtilities().getListItemParent();
								content = false;
								int so = elem.getStartOffset();
								int eo = elem.getEndOffset();
								if(so + 1 < eo)
								{
									char[] temp = data.getJtpMain().getText(so, eo - so).toCharArray();
									for(char c : temp)
									{
										if(!(new Character(c)).isWhitespace(c))
										{
											content = true;
										}
									}
								}
								if(!content)
								{
									data.getHtmlUtilities().removeTag(elem, true);
									this.setCaretPosition(sOffset - 1);
									refreshOnUpdate();
									return;
								}
								else
								{
									data.getJtpMain().replaceSelection("");
									refreshOnUpdate();
									return;
								}
							}
							else if(data.getHtmlUtilities().checkParentsTag(HTML.Tag.TABLE))
							{
								data.getJtpMain().setCaretPosition(data.getJtpMain().getCaretPosition() - 1);
								ke.consume();
								refreshOnUpdate();
								return;
							}
						}
						data.getJtpMain().replaceSelection("");
						refreshOnUpdate();
						return;
					}
				}
			}
			catch(BadLocationException ble)
			{
				logException("BadLocationException in keyTyped method", ble);
				new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorBadLocationException"), SimpleInfoDialog.ERROR);
			}
			catch(IOException ioe)
			{
				logException("IOException in keyTyped method", ioe);
				new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorIOException"), SimpleInfoDialog.ERROR);
			}
		}
		else if(ke.getKeyChar() == KeyEvent.VK_ENTER)
		{
			try
			{
				if(data.getHtmlUtilities().checkParentsTag(HTML.Tag.UL) == true | data.getHtmlUtilities().checkParentsTag(HTML.Tag.OL) == true)
				{
					elem = data.getHtmlUtilities().getListItemParent();
					int so = elem.getStartOffset();
					int eo = elem.getEndOffset();
					char[] temp = this.getTextPane().getText(so,eo-so).toCharArray();
					boolean content = false;
					for(char c : temp)
					{
						if(!(new Character(c)).isWhitespace(c))
						{
							content = true;
						}
					}
					if(content)
					{
						int end = -1;
						int j = temp.length;
						do
						{
							j--;
							if(new Character(temp[j]).isLetterOrDigit(temp[j]))
							{
								end = j;
							}
						} while (end == -1 && j >= 0);
						j = end;
						do
						{
							j++;
							if(!new Character(temp[j]).isSpaceChar(temp[j]))
							{
								repos = j - end -1;
							}
						} while (repos == -1 && j < temp.length);
						if(repos == -1)
						{
							repos = 0;
						}
					}
					if(!content)
					{
						removeEmptyListElement(elem);
					}
					else
					{
						if(this.getCaretPosition() + 1 == elem.getEndOffset())
						{
							insertListStyle(elem);
							this.setCaretPosition(pos - repos);
						}
						else
						{
							int caret = this.getCaretPosition();
							String tempString = this.getTextPane().getText(caret, eo - caret);
							if(tempString != null && tempString.length() > 0)
							{
								this.getTextPane().select(caret, eo - 1);
								this.getTextPane().replaceSelection("");
								data.getHtmlUtilities().insertListElement(tempString);
								Element newLi = data.getHtmlUtilities().getListItemParent();
								this.setCaretPosition(newLi.getEndOffset() - 1);
							}
						}
					}
				}
				else if(data.isEnterIsBreak())
				{
					insertBreak();
					ke.consume();
				}
			}
			catch(BadLocationException ble)
			{
				logException("BadLocationException in keyTyped method", ble);
				new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorBadLocationException"), SimpleInfoDialog.ERROR);
			}
			catch(IOException ioe)
			{
				logException("IOException in keyTyped method", ioe);
				new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorIOException"), SimpleInfoDialog.ERROR);
			}
		}
	}
	public void keyPressed(KeyEvent ke) { if(ke.getKeyChar() == KeyEvent.VK_ENTER && data.isEnterIsBreak()) { ke.consume(); } }
	public void keyReleased(KeyEvent ke) { if(ke.getKeyChar() == KeyEvent.VK_ENTER && data.isEnterIsBreak()) { ke.consume(); } }

	/* FocusListener methods */
	public void focusGained(FocusEvent fe)
	{
		if(fe.getSource() == data.getJtpMain())
		{
			setFormattersActive(true);
		}
		else if(fe.getSource() == data.getJtpSource())
		{
			setFormattersActive(false);
		}
	}
	public void focusLost(FocusEvent fe) {}

	/* DocumentListener methods */
	public void changedUpdate(DocumentEvent de)	{ handleDocumentChange(de); }
	public void insertUpdate(DocumentEvent de)	{ handleDocumentChange(de); }
	public void removeUpdate(DocumentEvent de)	{ handleDocumentChange(de); }
	public void handleDocumentChange(DocumentEvent de)
	{
		if(!data.isExclusiveEdit())
		{
			if(isSourceWindowActive())
			{
				if(de.getDocument() instanceof HTMLDocument || de.getDocument() instanceof ExtendedHTMLDocument)
				{
					data.getJtpSource().getDocument().removeDocumentListener(this);
					data.getJtpSource().setText(data.getJtpMain().getText());
					data.getJtpSource().getDocument().addDocumentListener(this);
				}
				else if(de.getDocument() instanceof PlainDocument || de.getDocument() instanceof DefaultStyledDocument)
				{
					data.getJtpMain().getDocument().removeDocumentListener(this);
					data.getJtpMain().setText(data.getJtpSource().getText());
					data.getJtpMain().getDocument().addDocumentListener(this);
				}
			}
		}
	}

	/** Method for setting a document as the current document for the text pane
	  * and re-registering the controls and settings for it
	  */
	public void registerDocument(ExtendedHTMLDocument htmlDoc)
	{
		data.getJtpMain().setDocument(htmlDoc);
		data.getJtpMain().getDocument().addUndoableEditListener(new CustomUndoableEditListener());
		data.getJtpMain().getDocument().addDocumentListener(this);
		data.getJtpMain().setCaretPosition(0);
		purgeUndos();
		registerDocumentStyles();
	}

	/** Method for locating the available CSS style for the document and adding
	  * them to the styles selector
	  */
	public void registerDocumentStyles()
	{
		if(data.getJcmbStyleSelector() == null || data.getHtmlDoc() == null)
		{
			return;
		}
		data.getJcmbStyleSelector().setEnabled(false);
		data.getJcmbStyleSelector().removeAllItems();
		data.getJcmbStyleSelector().addItem(Translatrix.getTranslationString("NoCSSStyle"));
		for(Enumeration e = data.getHtmlDoc().getStyleNames(); e.hasMoreElements();)
		{
			String name = (String) e.nextElement();
			if(name.length() > 0 && name.charAt(0) == '.')
			{
				data.getJcmbStyleSelector().addItem(name.substring(1));
			}
		}
		data.getJcmbStyleSelector().setEnabled(true);
	}

	/** Method for inserting list elements
	  */
	public void insertListStyle(Element element)
	throws BadLocationException,IOException
	{
		if(element.getParentElement().getName() == "ol")
		{
			data.getActionListOrdered().actionPerformed(new ActionEvent(new Object(), 0, "newListPoint"));
		}
		else
		{
			data.getActionListUnordered().actionPerformed(new ActionEvent(new Object(), 0, "newListPoint"));
		}
	}

	/** Method for inserting an HTML Table
	  */
	private void insertTable(Hashtable attribs, String[] fieldNames, String[] fieldTypes, String[] fieldValues)
	throws IOException, BadLocationException, RuntimeException, NumberFormatException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		StringBuffer compositeElement = new StringBuffer("<TABLE");
		if(attribs != null && attribs.size() > 0)
		{
			Enumeration attribEntries = attribs.keys();
			while(attribEntries.hasMoreElements())
			{
				Object entryKey   = attribEntries.nextElement();
				Object entryValue = attribs.get(entryKey);
				if(entryValue != null && entryValue != "")
				{
					compositeElement.append(" " + entryKey + "=" + '"' + entryValue + '"');
				}
			}
		}
		int rows = 0;
		int cols = 0;
		if(fieldNames != null && fieldNames.length > 0)
		{
			PropertiesDialog propertiesDialog = new PropertiesDialog(this.getFrame(), fieldNames, fieldTypes, fieldValues, Translatrix.getTranslationString("TableDialogTitle"), true);
			propertiesDialog.setVisible(true);
			String decision = propertiesDialog.getDecisionValue();
			if(decision.equals(Translatrix.getTranslationString("DialogCancel")))
			{
				propertiesDialog.dispose();
				propertiesDialog = null;
				return;
			}
			else
			{
				for(String fieldName : fieldNames)
				{
					String propValue = propertiesDialog.getFieldValue(fieldName);
					if(propValue != null && propValue != "" && propValue.length() > 0)
					{
						if(fieldName.equals("rows"))
						{
							rows = Integer.parseInt(propValue);
						}
						else if(fieldName.equals("cols"))
						{
							cols = Integer.parseInt(propValue);
						}
						else
						{
							compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
						}
					}
				}
			}
			propertiesDialog.dispose();
			propertiesDialog = null;
		}
		compositeElement.append(">");
		for(int i = 0; i < rows; i++)
		{
			compositeElement.append("<TR>");
			for(int j = 0; j < cols; j++)
			{
				compositeElement.append("<TD></TD>");
			}
			compositeElement.append("</TR>");
		}
		compositeElement.append("</TABLE>&nbsp;");
		data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, compositeElement.toString(), 0, 0, HTML.Tag.TABLE);
		data.getJtpMain().setCaretPosition(caretPos + 1);
		refreshOnUpdate();
	}

	/** Method for editing an HTML Table
	  */
	private void editTable()
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element = data.getHtmlDoc().getCharacterElement(caretPos);
		Element elementParent = element.getParentElement();
		while(elementParent != null && !elementParent.getName().equals("table"))
		{
		elementParent = elementParent.getParentElement();
		}
		if (elementParent != null)
		{
			HTML.Attribute[] fieldKeys = { HTML.Attribute.BORDER, HTML.Attribute.CELLSPACING, HTML.Attribute.CELLPADDING, HTML.Attribute.WIDTH, HTML.Attribute.VALIGN };
			String[] fieldNames  = { "border", "cellspacing", "cellpadding", "width", "valign" };
			String[] fieldTypes  = { "text",   "text",        "text",        "text",  "combo" };
			String[] fieldValues = { "",       "",            "",            "",      "top,middle,bottom," };
			MutableAttributeSet myatr = (MutableAttributeSet)elementParent.getAttributes();
			for(int i = 0; i < fieldNames.length; i++)
			{
				if(myatr.isDefined(fieldKeys[i]))
				{
					if(fieldTypes[i].equals("combo"))
					{
						fieldValues[i] = myatr.getAttribute(fieldKeys[i]).toString() + "," + fieldValues[i];
					}
					else
					{
						fieldValues[i] = myatr.getAttribute(fieldKeys[i]).toString();
					}
				}
			}
			PropertiesDialog propertiesDialog = new PropertiesDialog(this.getFrame(), fieldNames, fieldTypes, fieldValues, Translatrix.getTranslationString("TableEdit"), true);
			propertiesDialog.setVisible(true);
			if(!propertiesDialog.getDecisionValue().equals(Translatrix.getTranslationString("DialogCancel")))
			{
				String myAtributes = "";
				SimpleAttributeSet mynew = new SimpleAttributeSet();
				for(int i = 0; i < fieldNames.length; i++)
				{
					String propValue = propertiesDialog.getFieldValue(fieldNames[i]);
					if(propValue != null && propValue.length() > 0)
					{
						myAtributes = myAtributes + fieldNames[i] + "=\"" + propValue + "\" ";
						mynew.addAttribute(fieldKeys[i],propValue);
					}
				}
				data.getHtmlDoc().replaceAttributes(elementParent, mynew, HTML.Tag.TABLE);
				refreshOnUpdate();
			}
			propertiesDialog.dispose();
		}
		else
		{
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Table"), true, Translatrix.getTranslationString("CursorNotInTable"));
		}
	}

	/** Method for editing HTML Table cells
	  */
	private void editCell()
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element = data.getHtmlDoc().getCharacterElement(caretPos);
		Element elementParent = element.getParentElement();
		while(elementParent != null && !elementParent.getName().equals("td"))
		{
			elementParent = elementParent.getParentElement();
		}
		if(elementParent != null)
		{
			HTML.Attribute[] fieldKeys = { HTML.Attribute.WIDTH,HTML.Attribute.HEIGHT,HTML.Attribute.ALIGN,HTML.Attribute.VALIGN,HTML.Attribute.BGCOLOR };
			String[] fieldNames  = { "width", "height", "align", "valign", "bgcolor" };
			String[] fieldTypes  = { "text",  "text",   "combo", "combo",  "combo" };
			String[] fieldValues = { "",      "",       "left,right,center", "top,middle,bottom", "none,aqua,black,fuchsia,gray,green,lime,maroon,navy,olive,purple,red,silver,teal,white,yellow" };
			MutableAttributeSet myatr = (MutableAttributeSet)elementParent.getAttributes();
			for(int i = 0; i < fieldNames.length; i++)
			{
				if(myatr.isDefined(fieldKeys[i]))
				{
					if(fieldTypes[i].equals("combo"))
					{
						fieldValues[i] = myatr.getAttribute(fieldKeys[i]).toString() + "," + fieldValues[i];
					}
					else
					{
						fieldValues[i] = myatr.getAttribute(fieldKeys[i]).toString();
					}
				}
			}
			PropertiesDialog propertiesDialog = new PropertiesDialog(this.getFrame(), fieldNames, fieldTypes, fieldValues, Translatrix.getTranslationString("TableCellEdit"), true);
			propertiesDialog.setVisible(true);
			if(!propertiesDialog.getDecisionValue().equals(Translatrix.getTranslationString("DialogCancel")))
			{
				String myAtributes = "";
				SimpleAttributeSet mynew = new SimpleAttributeSet();
				for(int i = 0; i < fieldNames.length; i++)
				{
					String propValue = propertiesDialog.getFieldValue(fieldNames[i]);
					if(propValue != null && propValue.length() > 0)
					{
						myAtributes = myAtributes + fieldNames[i] + "=\"" + propValue + "\" ";
						mynew.addAttribute(fieldKeys[i],propValue);
					}
				}
				data.getHtmlDoc().replaceAttributes(elementParent, mynew, HTML.Tag.TD);
				refreshOnUpdate();
			}
			propertiesDialog.dispose();
		}
		else
		{
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Cell"), true, Translatrix.getTranslationString("CursorNotInCell"));
		}
	}

	/** Method for inserting a row into an HTML Table
	  */
	private void insertTableRow()
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element = data.getHtmlDoc().getCharacterElement(data.getJtpMain().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint  = -1;
		int columnCount = -1;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("tr"))
			{
				startPoint  = elementParent.getStartOffset();
				columnCount = elementParent.getElementCount();
				break;
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && columnCount > -1)
		{
			data.getJtpMain().setCaretPosition(startPoint);
	 		StringBuffer sRow = new StringBuffer();
 			sRow.append("<TR>");
 			for(int i = 0; i < columnCount; i++)
 			{
 				sRow.append("<TD></TD>");
 			}
 			sRow.append("</TR>");
 			ActionEvent actionEvent = new ActionEvent(data.getJtpMain(), 0, "insertTableRow");
 			new HTMLEditorKit.InsertHTMLTextAction("insertTableRow", sRow.toString(), HTML.Tag.TABLE, HTML.Tag.TR).actionPerformed(actionEvent);
 			refreshOnUpdate();
 			data.getJtpMain().setCaretPosition(caretPos);
 		}
	}

	/** Method for inserting a column into an HTML Table
	  */
	private void insertTableColumn()
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element = data.getHtmlDoc().getCharacterElement(data.getJtpMain().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int rowCount   = -1;
		int cellOffset =  0;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("table"))
			{
				startPoint = elementParent.getStartOffset();
				rowCount   = elementParent.getElementCount();
				break;
			}
			else if(elementParent.getName().equals("tr"))
			{
				int rowCells = elementParent.getElementCount();
				for(int i = 0; i < rowCells; i++)
				{
					Element currentCell = elementParent.getElement(i);
					if(data.getJtpMain().getCaretPosition() >= currentCell.getStartOffset() && data.getJtpMain().getCaretPosition() <= currentCell.getEndOffset())
					{
						cellOffset = i;
					}
				}
				elementParent = elementParent.getParentElement();
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && rowCount > -1)
		{
			data.getJtpMain().setCaretPosition(startPoint);
			String sCell = "<TD></TD>";
			ActionEvent actionEvent = new ActionEvent(data.getJtpMain(), 0, "insertTableCell");
 			for(int i = 0; i < rowCount; i++)
 			{
 				Element row = elementParent.getElement(i);
 				Element whichCell = row.getElement(cellOffset);
 				data.getJtpMain().setCaretPosition(whichCell.getStartOffset());
				new HTMLEditorKit.InsertHTMLTextAction("insertTableCell", sCell, HTML.Tag.TR, HTML.Tag.TD, HTML.Tag.TH, HTML.Tag.TD).actionPerformed(actionEvent);
 			}
 			refreshOnUpdate();
 			data.getJtpMain().setCaretPosition(caretPos);
 		}
	}

	/** Method for inserting a cell into an HTML Table
	  */
	private void insertTableCell()
	{
		String sCell = "<TD></TD>";
		ActionEvent actionEvent = new ActionEvent(data.getJtpMain(), 0, "insertTableCell");
		new HTMLEditorKit.InsertHTMLTextAction("insertTableCell", sCell, HTML.Tag.TR, HTML.Tag.TD, HTML.Tag.TH, HTML.Tag.TD).actionPerformed(actionEvent);
		refreshOnUpdate();
	}

	/** Method for deleting a row from an HTML Table
	  */
	private void deleteTableRow()
	throws BadLocationException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element = data.getHtmlDoc().getCharacterElement(data.getJtpMain().getCaretPosition());
		Element elementParent = element.getParentElement();
		int startPoint = -1;
		int endPoint   = -1;
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("tr"))
			{
				startPoint = elementParent.getStartOffset();
				endPoint   = elementParent.getEndOffset();
				break;
			}
			else
			{
				elementParent = elementParent.getParentElement();
			}
		}
		if(startPoint > -1 && endPoint > startPoint)
		{
			data.getHtmlDoc().remove(startPoint, endPoint - startPoint);
			data.getJtpMain().setDocument(data.getHtmlDoc());
			registerDocument(data.getHtmlDoc());
 			refreshOnUpdate();
 			if(caretPos >= data.getHtmlDoc().getLength())
 			{
 				caretPos = data.getHtmlDoc().getLength() - 1;
 			}
 			data.getJtpMain().setCaretPosition(caretPos);
 		}
	}

	/** Method for deleting a column from an HTML Table
	  */
	private void deleteTableColumn()
	throws BadLocationException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		Element	element       = data.getHtmlDoc().getCharacterElement(data.getJtpMain().getCaretPosition());
		Element elementParent = element.getParentElement();
		Element	elementCell   = (Element)null;
		Element	elementRow    = (Element)null;
		Element	elementTable  = (Element)null;
		// Locate the table, row, and cell location of the cursor
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			if(elementParent.getName().equals("td"))
			{
				elementCell = elementParent;
			}
			else if(elementParent.getName().equals("tr"))
			{
				elementRow = elementParent;
			}
			else if(elementParent.getName().equals("table"))
			{
				elementTable = elementParent;
			}
			elementParent = elementParent.getParentElement();
		}
		int whichColumn = -1;
		if(elementCell != null && elementRow != null && elementTable != null)
		{
			// Find the column the cursor is in
			int myOffset = 0;
			for(int i = 0; i < elementRow.getElementCount(); i++)
			{
				if(elementCell == elementRow.getElement(i))
				{
					whichColumn = i;
					myOffset = elementCell.getEndOffset();
				}
			}
			if(whichColumn > -1)
			{
				// Iterate through the table rows, deleting cells from the indicated column
				int mycaretPos = caretPos;
				for(int i = 0; i < elementTable.getElementCount(); i++)
				{
					elementRow  = elementTable.getElement(i);
					elementCell = (elementRow.getElementCount() > whichColumn ? elementRow.getElement(whichColumn) : elementRow.getElement(elementRow.getElementCount() - 1));
					int columnCellStart = elementCell.getStartOffset();
					int columnCellEnd   = elementCell.getEndOffset();
					int dif	= columnCellEnd - columnCellStart;
					if(columnCellStart < myOffset)
					{
						mycaretPos = mycaretPos - dif;
						myOffset = myOffset-dif;
					}
					if(whichColumn==0)
					{
						data.getHtmlDoc().remove(columnCellStart, dif);
					}
					else
					{
						data.getHtmlDoc().remove(columnCellStart-1, dif);
					}
				}
				data.getJtpMain().setDocument(data.getHtmlDoc());
				registerDocument(data.getHtmlDoc());
	 			refreshOnUpdate();
	 			if(mycaretPos >= data.getHtmlDoc().getLength())
	 			{
	 				mycaretPos = data.getHtmlDoc().getLength() - 1;
	 			}
	 			if(mycaretPos < 1)
	 			{
	 				mycaretPos =  1;
 				}
	 			data.getJtpMain().setCaretPosition(mycaretPos);
			}
		}
	}

	/** Method for inserting a break (BR) element
	  */
	private void insertBreak()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, "<BR>", 0, 0, HTML.Tag.BR);
		data.getJtpMain().setCaretPosition(caretPos + 1);
	}

	/** Method for inserting a horizontal rule (HR) element
	  */
	private void insertHR()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, "<HR>", 0, 0, HTML.Tag.HR);
		data.getJtpMain().setCaretPosition(caretPos + 1);
	}

	/** Method for opening the Unicode dialog
	  */
	private void insertUnicode(int index)
	throws IOException, BadLocationException, RuntimeException
	{
		new UnicodeDialog(this, Translatrix.getTranslationString("UnicodeDialogTitle"), false, index);
	}

	/** Method for inserting Unicode characters via the UnicodeDialog class
	  */
	public void insertUnicodeChar(String sChar)
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		if(sChar != null)
		{
			data.getHtmlDoc().insertString(caretPos, sChar, data.getJtpMain().getInputAttributes());
			data.getJtpMain().setCaretPosition(caretPos + 1);
		}
	}

	/** Method for inserting a non-breaking space (&nbsp;)
	  */
	private void insertNonbreakingSpace()
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		data.getHtmlDoc().insertString(caretPos, "\240", data.getJtpMain().getInputAttributes());
		data.getJtpMain().setCaretPosition(caretPos + 1);
	}

	/** Method for inserting a form element
	  */
	private void insertFormElement(HTML.Tag baseTag, String baseElement, Hashtable attribs, String[] fieldNames, String[] fieldTypes, String[] fieldValues, boolean hasClosingTag)
	throws IOException, BadLocationException, RuntimeException
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		StringBuffer compositeElement = new StringBuffer("<" + baseElement);
		if(attribs != null && attribs.size() > 0)
		{
			Enumeration attribEntries = attribs.keys();
			while(attribEntries.hasMoreElements())
			{
				Object entryKey   = attribEntries.nextElement();
				Object entryValue = attribs.get(entryKey);
				if(entryValue != null && entryValue != "")
				{
					compositeElement.append(" " + entryKey + "=" + '"' + entryValue + '"');
				}
			}
		}
		if(fieldNames != null && fieldNames.length > 0)
		{
			PropertiesDialog propertiesDialog = new PropertiesDialog(this.getFrame(), fieldNames, fieldTypes, fieldValues, Translatrix.getTranslationString("FormDialogTitle"), true);
			propertiesDialog.setVisible(true);
			String decision = propertiesDialog.getDecisionValue();
			if(decision.equals(Translatrix.getTranslationString("DialogCancel")))
			{
				propertiesDialog.dispose();
				propertiesDialog = null;
				return;
			}
			else
			{
				for(String fieldName : fieldNames)
				{
					String propValue = propertiesDialog.getFieldValue(fieldName);
					if(propValue != null && propValue.length() > 0)
					{
						if(fieldName.equals("checked"))
						{
							if(propValue.equals("true"))
							{
								compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
							}
						}
						else
						{
							compositeElement.append(" " + fieldName + "=" + '"' + propValue + '"');
						}
					}
				}
			}
			propertiesDialog.dispose();
			propertiesDialog = null;
		}
		// --- Convenience for editing, this makes the FORM visible
		if(data.isUseFormIndicator() && baseElement.equals("form"))
		{
			compositeElement.append(" bgcolor=" + '"' + data.getClrFormIndicator() + '"');
		}
		// --- END
		compositeElement.append(">");
		if(baseTag == HTML.Tag.FORM)
		{
			compositeElement.append("<P>&nbsp;</P>");
			compositeElement.append("<P>&nbsp;</P>");
			compositeElement.append("<P>&nbsp;</P>");
		}
		if(hasClosingTag)
		{
			compositeElement.append("</" + baseElement + ">");
		}
		if(baseTag == HTML.Tag.FORM)
		{
			compositeElement.append("<P>&nbsp;</P>");
		}
		data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, compositeElement.toString(), 0, 0, baseTag);
		// jtpMain.setCaretPosition(caretPos + 1);
		refreshOnUpdate();
	}

	/** Alternate method call for inserting a form element
	  */
	private void insertFormElement(HTML.Tag baseTag, String baseElement, Hashtable attribs, String[] fieldNames, String[] fieldTypes, boolean hasClosingTag)
	throws IOException, BadLocationException, RuntimeException
	{
		insertFormElement(baseTag, baseElement, attribs, fieldNames, fieldTypes, new String[fieldNames.length], hasClosingTag);
	}

	/** Method that handles initial list insertion and deletion
	  */
	public void removeEmptyListElement(Element element)
	{
		Element h = data.getHtmlUtilities().getListItemParent();
		Element listPar = h.getParentElement();
		if(h != null)
		{
			data.getHtmlUtilities().removeTag(h, true);
			removeEmptyLists();
			refreshOnUpdate();
		}
	}

	public void removeEmptyLists()
	{
		javax.swing.text.ElementIterator ei = new javax.swing.text.ElementIterator(data.getHtmlDoc());
		Element ele;
		while((ele = ei.next()) != null)
		{
			if(ele.getName().equals("ul") || ele.getName().equals("ol"))
			{
				int listChildren = 0;
				for(int i = 0; i < ele.getElementCount(); i++)
				{
					if(ele.getElement(i).getName().equals("li"))
					{
						listChildren++;
					}
				}
				if(listChildren <= 0)
				{
					data.getHtmlUtilities().removeTag(ele, true);
				}
			}
		}
		refreshOnUpdate();
	}

	/** Method to initiate a find/replace operation
	  */
	private void doSearch(String searchFindTerm, String searchReplaceTerm, boolean bIsFindReplace, boolean bCaseSensitive, boolean bStartAtTop)
	{
		boolean bReplaceAll = false;
		JTextComponent searchPane = (JTextComponent)data.getJtpMain();
		if(data.getJspSource().isShowing() || data.getJtpSource().hasFocus())
		{
			searchPane = (JTextComponent)data.getJtpSource();
		}
		if(searchFindTerm == null || (bIsFindReplace && searchReplaceTerm == null))
		{
			SearchDialog sdSearchInput = new SearchDialog(this.getFrame(), Translatrix.getTranslationString("SearchDialogTitle"), true, bIsFindReplace, bCaseSensitive, bStartAtTop);
			searchFindTerm    = sdSearchInput.getFindTerm();
			searchReplaceTerm = sdSearchInput.getReplaceTerm();
			bCaseSensitive    = sdSearchInput.getCaseSensitive();
			bStartAtTop       = sdSearchInput.getStartAtTop();
			bReplaceAll       = sdSearchInput.getReplaceAll();
		}
		if(searchFindTerm != null && (!bIsFindReplace || searchReplaceTerm != null))
		{
			if(bReplaceAll)
			{
				int results = findText(searchFindTerm, searchReplaceTerm, bCaseSensitive, 0);
				int findOffset = 0;
				if(results > -1)
				{
					while(results > -1)
					{
						findOffset = findOffset + searchReplaceTerm.length();
						results    = findText(searchFindTerm, searchReplaceTerm, bCaseSensitive, findOffset);
					}
				}
				else
				{
					new SimpleInfoDialog(this.getFrame(), "", true, Translatrix.getTranslationString("ErrorNoOccurencesFound") + ":\n" + searchFindTerm, SimpleInfoDialog.WARNING);
				}
			}
			else
			{
				int results = findText(searchFindTerm, searchReplaceTerm, bCaseSensitive, (bStartAtTop ? 0 : searchPane.getCaretPosition()));
				if(results == -1)
				{
					new SimpleInfoDialog(this.getFrame(), "", true, Translatrix.getTranslationString("ErrorNoMatchFound") + ":\n" + searchFindTerm, SimpleInfoDialog.WARNING);
				}
			}
			data.setLastSearchFindTerm(new String(searchFindTerm));
			if(searchReplaceTerm != null)
			{
				data.setLastSearchReplaceTerm(new String(searchReplaceTerm));
			}
			else
			{
				data.setLastSearchReplaceTerm((String)null);
			}
			data.setLastSearchCaseSetting(bCaseSensitive);
			data.setLastSearchTopSetting(bStartAtTop);
		}
	}

	/** Method for finding (and optionally replacing) a string in the text
	  */
	private int findText(String findTerm, String replaceTerm, boolean bCaseSenstive, int iOffset)
	{
		JTextComponent jtpFindSource;
		if(isSourceWindowActive() || data.getJtpSource().hasFocus())
		{
			jtpFindSource = (JTextComponent)data.getJtpSource();
		}
		else
		{
			jtpFindSource = (JTextComponent)data.getJtpMain();
		}
		int searchPlace = -1;
		try
		{
			Document baseDocument = jtpFindSource.getDocument();
			searchPlace =
				(bCaseSenstive ?
					baseDocument.getText(0, baseDocument.getLength()).indexOf(findTerm, iOffset) :
					baseDocument.getText(0, baseDocument.getLength()).toLowerCase().indexOf(findTerm.toLowerCase(), iOffset)
				);
			if(searchPlace > -1)
			{
				if(replaceTerm != null)
				{
					AttributeSet attribs = null;
					if(baseDocument instanceof HTMLDocument)
					{
						Element element = ((HTMLDocument)baseDocument).getCharacterElement(searchPlace);
						attribs = element.getAttributes();
					}
					baseDocument.remove(searchPlace, findTerm.length());
					baseDocument.insertString(searchPlace, replaceTerm, attribs);
					jtpFindSource.setCaretPosition(searchPlace + replaceTerm.length());
					jtpFindSource.requestFocus();
					jtpFindSource.select(searchPlace, searchPlace + replaceTerm.length());
				}
				else
				{
					jtpFindSource.setCaretPosition(searchPlace + findTerm.length());
					jtpFindSource.requestFocus();
					jtpFindSource.select(searchPlace, searchPlace + findTerm.length());
				}
			}
		}
		catch(BadLocationException ble)
		{
			logException("BadLocationException in actionPerformed method", ble);
			new SimpleInfoDialog(this.getFrame(), Translatrix.getTranslationString("Error"), true, Translatrix.getTranslationString("ErrorBadLocationException"), SimpleInfoDialog.ERROR);
		}
		return searchPlace;
	}

	/** Method for inserting an image from a file
	  */
	private void insertLocalImage(File whatImage)
	throws IOException, BadLocationException, RuntimeException
	{
		if(whatImage == null)
		{
			getImageFromChooser(data.getImageChooserStartDir(), data.getExtsIMG(), Translatrix.getTranslationString("FiletypeIMG"));
		}
		else
		{
			data.setImageChooserStartDir(whatImage.getParent().toString());
			int caretPos = data.getJtpMain().getCaretPosition();
			data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, "<IMG SRC=\"" + whatImage + "\">", 0, 0, HTML.Tag.IMG);
			data.getJtpMain().setCaretPosition(caretPos + 1);
			refreshOnUpdate();
		}
	}

	/** Method for inserting an image from a URL
	  */
	public void insertURLImage()
	throws IOException, BadLocationException, RuntimeException
	{
		ImageURLDialog imgUrlDialog = new ImageURLDialog(this.getFrame(), Translatrix.getTranslationString("ImageURLDialogTitle"), true);
		imgUrlDialog.pack();
		imgUrlDialog.setVisible(true);
		String whatImage = imgUrlDialog.getImageUrl();
		if(whatImage != null && whatImage.length() > 0)
		{
			int caretPos = data.getJtpMain().getCaretPosition();
			String sImgTag = "<img src=\"" + whatImage + '"';
			if(imgUrlDialog.getImageAlt() != null && imgUrlDialog.getImageAlt().length() > 0) { sImgTag = sImgTag + " alt=\"" + imgUrlDialog.getImageAlt() + '"'; }
			if(imgUrlDialog.getImageWidth() != null && imgUrlDialog.getImageWidth().length() > 0) { sImgTag = sImgTag + " width=\"" + imgUrlDialog.getImageWidth() + '"'; }
			if(imgUrlDialog.getImageHeight() != null && imgUrlDialog.getImageHeight().length() > 0) { sImgTag = sImgTag + " height=\"" + imgUrlDialog.getImageHeight() + '"'; }
			sImgTag = sImgTag + "/>";
			data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, sImgTag, 0, 0, HTML.Tag.IMG);
			data.getJtpMain().setCaretPosition(caretPos + 1);
			refreshOnUpdate();
		}
	}

	/** Empty spell check method, overwritten by spell checker extension class
	  */
	public void checkDocumentSpelling(Document doc) { ; }

	/** Method for saving text as a complete HTML document
	  */
	public void writeOut(HTMLDocument doc, File whatFile)
	throws IOException, BadLocationException
	{
		if(whatFile == null)
		{
			whatFile = getFileFromChooser(".", JFileChooser.SAVE_DIALOG, data.getExtsHTML(), Translatrix.getTranslationString("FiletypeHTML"));
		}
		if(whatFile != null)
		{
			FileWriter fw = new FileWriter(whatFile);
			data.getHtmlKit().write(fw, doc, 0, doc.getLength());
			fw.flush();
			fw.close();
			data.setCurrentFile(whatFile);
			updateTitle();
		}
		refreshOnUpdate();
	}

	/** Method for saving text as an HTML fragment
	  */
	public void writeOutFragment(HTMLDocument doc, String containingTag, File fragFile)
	throws IOException, BadLocationException
	{
		FileWriter fw = new FileWriter(fragFile);
		String docTextCase = data.getJtpSource().getText().toLowerCase();
		int tagStart       = docTextCase.indexOf("<" + containingTag.toLowerCase());
		int tagStartClose  = docTextCase.indexOf(">", tagStart) + 1;
		String closeTag    = "</" + containingTag.toLowerCase() + ">";
		int tagEndOpen     = docTextCase.indexOf(closeTag);
		if(tagStartClose < 0) { tagStartClose = 0; }
		if(tagEndOpen < 0 || tagEndOpen > docTextCase.length()) { tagEndOpen = docTextCase.length(); }
		String bodyText = data.getJtpSource().getText().substring(tagStartClose, tagEndOpen);
		fw.write(bodyText, 0, bodyText.length());
		fw.flush();
		fw.close();
		refreshOnUpdate();
	}

	public void writeOutFragment(HTMLDocument doc, String containingTag)
	throws IOException, BadLocationException
	{
		File whatFile = getFileFromChooser(".", JFileChooser.SAVE_DIALOG, data.getExtsHTML(), Translatrix.getTranslationString("FiletypeHTML"));
		if(whatFile != null)
		{
			writeOutFragment(doc, containingTag, whatFile);
		}
	}

	/** Method for saving text as an RTF document
	  */
	public void writeOutRTF(StyledDocument doc, File rtfFile)
	throws IOException, BadLocationException
	{
		FileOutputStream fos = new FileOutputStream(rtfFile);
		RTFEditorKit rtfKit = new RTFEditorKit();
		rtfKit.write(fos, doc, 0, doc.getLength());
		fos.flush();
		fos.close();
		refreshOnUpdate();
	}

	public void writeOutRTF(StyledDocument doc)
	throws IOException, BadLocationException
	{
		File whatFile = getFileFromChooser(".", JFileChooser.SAVE_DIALOG, data.getExtsRTF(), Translatrix.getTranslationString("FiletypeRTF"));
		if(whatFile != null)
		{
			writeOutRTF(doc, whatFile);
		}
	}

	public String getRTFDocument()
	throws IOException, BadLocationException
	{
		StyledDocument doc = (StyledDocument)(data.getJtpMain().getStyledDocument());
		StringWriter strwriter = new StringWriter();
		RTFEditorKit rtfKit = new RTFEditorKit();
		rtfKit.write(strwriter, doc, 0, doc.getLength());
		return strwriter.toString();
	}

	/** Method for saving text as a Base64 encoded document
	  */
	public void writeOutBase64(String text, File b64File)
	throws IOException, BadLocationException
	{
		String base64text = Base64Codec.encode(text);
		FileWriter fw = new FileWriter(b64File);
		fw.write(base64text, 0, base64text.length());
		fw.flush();
		fw.close();
		refreshOnUpdate();
	}

	public void writeOutBase64(String text)
	throws IOException, BadLocationException
	{
		File whatFile = getFileFromChooser(".", JFileChooser.SAVE_DIALOG, data.getExtsB64(), Translatrix.getTranslationString("FiletypeB64"));
		if(whatFile != null)
		{
			writeOutBase64(text, whatFile);
		}
	}

	/**
	 * Method for saving the HTML document
	 * JJ: As writeOut() is private I added this because I found no easy way just to save the open document
	 * 
	 */
	public void saveDocument()
	throws IOException, BadLocationException
	{
		writeOut((HTMLDocument)(data.getJtpMain().getDocument()), data.getCurrentFile());
	}

	/**
	 * Method to invoke loading HTML into the app HTMLEditorKit.ParserCallback
	 * cb can be - null or - new EkitStandardParserCallback() or - another
	 * ParserCallback overwrite
	 * 
	 * If cb is not null the loaded Document will be parsed before it is
	 * inserted into the htmlKit and the JTextArea and the ParserCallback can
	 * be used to analyse the errors. This might carry an performance penalty but
	 * makes loading safer in certain situations.
	 * 
	 */
	private void openDocument(File whatFile)
	throws IOException, BadLocationException
	{
		this.openDocument(whatFile, null);
	}

	private void openDocument(File whatFile, HTMLEditorKit.ParserCallback cb)
	throws IOException, BadLocationException
	{
		if(whatFile == null)
		{
			whatFile = getFileFromChooser(".", JFileChooser.OPEN_DIALOG, data.getExtsHTML(), Translatrix.getTranslationString("FiletypeHTML"));
		}
		if(whatFile != null)
		{
			try
			{
				loadDocument(whatFile, null, cb);
			}
			catch(ChangedCharSetException ccse)
			{
				String charsetType = ccse.getCharSetSpec().toLowerCase();
				int pos = charsetType.indexOf("charset");
				if(pos == -1)
				{
					throw ccse;
				}
				while(pos < charsetType.length() && charsetType.charAt(pos) != '=')
				{
					pos++;
				}
				pos++; // Places file cursor past the equals sign (=)
				String whatEncoding = charsetType.substring(pos).trim();
				loadDocument(whatFile, whatEncoding, cb);
			}
		}
		refreshOnUpdate();
	}

	/**
	 * Method for loading HTML document
	 */
	public void loadDocument(File whatFile)
	throws IOException, BadLocationException
	{
		this.loadDocument(whatFile, (HTMLEditorKit.ParserCallback)null);
	}

	private void loadDocument(File whatFile, String whatEncoding)
	throws IOException, BadLocationException
	{
		this.loadDocument(whatFile, whatEncoding, (HTMLEditorKit.ParserCallback)null);
	}

	public void loadDocument(File whatFile, HTMLEditorKit.ParserCallback cb)
	throws IOException, BadLocationException
	{
		loadDocument(whatFile, null, cb);
	}

	/**
	 * Method for loading HTML document into the app, including document
	 * encoding setting
	 */
	private void loadDocument(File whatFile, String whatEncoding, HTMLEditorKit.ParserCallback cb)
	throws IOException, BadLocationException
	{
		Reader rp = null;
		Reader rr = null;
		data.setHtmlDoc((ExtendedHTMLDocument)(data.getHtmlKit().createDefaultDocument()));
		data.getHtmlDoc().putProperty("com.hexidec.ekit.docsource", whatFile.toString());
		try
		{
			if(whatEncoding == null)
			{
				rp = new InputStreamReader(new FileInputStream(whatFile));
				rr = new InputStreamReader(new FileInputStream(whatFile));
			}
			else
			{
				rp = new InputStreamReader(new FileInputStream(whatFile), whatEncoding);
				rr = new InputStreamReader(new FileInputStream(whatFile), whatEncoding);
			}
			data.getHtmlDoc().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			data.getHtmlDoc().setPreservesUnknownTags(data.isPreserveUnknownTags());
			if(cb != null)
			{
				HTMLEditorKit.Parser parser = data.getHtmlDoc().getParser();
				parser.parse(rp, cb, true);
				rp.close();
			}
			data.getHtmlKit().read(rr, data.getHtmlDoc(), 0);
			registerDocument(data.getHtmlDoc());
			data.getJtpSource().setText(data.getJtpMain().getText());
			data.setCurrentFile(whatFile);
			updateTitle();
		}
		finally
		{
			if(rr != null)
			{
				rr.close();
			}
		}
	}

	/** Method for loading a Base64 encoded document
	  */
	private void openDocumentBase64(File whatFile)
	throws IOException, BadLocationException
	{
		if(whatFile == null)
		{
			whatFile = getFileFromChooser(".", JFileChooser.OPEN_DIALOG, data.getExtsB64(), Translatrix.getTranslationString("FiletypeB64"));
		}
		if(whatFile != null)
		{
			FileReader fr = new FileReader(whatFile);
			int nextChar = 0;
			StringBuffer encodedText = new StringBuffer();
			try
			{
				while((nextChar = fr.read()) != -1)
				{
					encodedText.append((char)nextChar);
				}
				fr.close();
				data.getJtpSource().setText(Base64Codec.decode(encodedText.toString()));
				data.getJtpMain().setText(data.getJtpSource().getText());
				registerDocument((ExtendedHTMLDocument)(data.getJtpMain().getDocument()));
			}
			finally
			{
				if(fr != null)
				{
					fr.close();
				}
			}
		}
	}

	/** Method for loading a Stylesheet into the app
	  */
	private void openStyleSheet(File fileCSS)
	throws IOException
	{
		if(fileCSS == null)
		{
			fileCSS = getFileFromChooser(".", JFileChooser.OPEN_DIALOG, data.getExtsCSS(), Translatrix.getTranslationString("FiletypeCSS"));
		}
		if(fileCSS != null)
		{
			String currDocText = data.getJtpMain().getText();
			data.setHtmlDoc((ExtendedHTMLDocument)(data.getHtmlKit().createDefaultDocument()));
			data.getHtmlDoc().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			data.getHtmlDoc().setPreservesUnknownTags(data.isPreserveUnknownTags());
			data.setStyleSheet(data.getHtmlDoc().getStyleSheet());
			URL cssUrl = fileCSS.toURI().toURL();
			InputStream is = cssUrl.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			data.getStyleSheet().loadRules(br, cssUrl);
			br.close();
			data.setHtmlDoc(new ExtendedHTMLDocument(data.getStyleSheet()));
			registerDocument(data.getHtmlDoc());
			data.getJtpMain().setText(currDocText);
			data.getJtpSource().setText(data.getJtpMain().getText());
		}
		refreshOnUpdate();
	}

	/** Method for serializing the document out to a file
	  */
	public void serializeOut(HTMLDocument doc)
	throws IOException
	{
		File whatFile = getFileFromChooser(".", JFileChooser.SAVE_DIALOG, data.getExtsSer(), Translatrix.getTranslationString("FiletypeSer"));
		if(whatFile != null)
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(whatFile));
			oos.writeObject(doc);
			oos.flush();
			oos.close();
		}
		refreshOnUpdate();
	}

	/** Method for reading in a serialized document from a file
	  */
	public void serializeIn()
	throws IOException, ClassNotFoundException
	{
		File whatFile = getFileFromChooser(".", JFileChooser.OPEN_DIALOG, data.getExtsSer(), Translatrix.getTranslationString("FiletypeSer"));
		if(whatFile != null)
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(whatFile));
			data.setHtmlDoc((ExtendedHTMLDocument)(ois.readObject()));
			ois.close();
			registerDocument(data.getHtmlDoc());
			validate();
		}
		refreshOnUpdate();
	}

	/** Method for obtaining a File for input/output using a JFileChooser dialog
	  */
	private File getFileFromChooser(String startDir, int dialogType, String[] exts, String desc)
	{
		JFileChooser jfileDialog = new JFileChooser(startDir);
		jfileDialog.setDialogType(dialogType);
		jfileDialog.setFileFilter(new MutableFilter(exts, desc));
		int optionSelected = JFileChooser.CANCEL_OPTION;
		if(dialogType == JFileChooser.OPEN_DIALOG)
		{
			optionSelected = jfileDialog.showOpenDialog(this);
		}
		else if(dialogType == JFileChooser.SAVE_DIALOG)
		{
			optionSelected = jfileDialog.showSaveDialog(this);
		}
		else // default to an OPEN_DIALOG
		{
			optionSelected = jfileDialog.showOpenDialog(this);
		}
		if(optionSelected == JFileChooser.APPROVE_OPTION)
		{
			return jfileDialog.getSelectedFile();
		}
		return (File)null;
	}

	/** Method for constructing an IMG tag from a local image using a custom dialog
	  */
	private void getImageFromChooser(String startDir, String[] exts, String desc)
	{
		ImageFileDialog imgFileDialog = new ImageFileDialog(this.getFrame(), startDir, exts, desc, "", Translatrix.getTranslationString("ImageDialogTitle"), true);
		imgFileDialog.setVisible(true);
		String decision = imgFileDialog.getDecisionValue();
		if(decision.equals(Translatrix.getTranslationString("DialogAccept")))
		{
			try
			{
				File whatImage = imgFileDialog.getImageFile();
				if(whatImage != null)
				{
					data.setImageChooserStartDir(whatImage.getParent().toString());
					int caretPos = data.getJtpMain().getCaretPosition();
					String sImgTag = "<img src=\"" + whatImage + '"';
					if(imgFileDialog.getImageAlt() != null && imgFileDialog.getImageAlt().length() > 0) { sImgTag = sImgTag + " alt=\"" + imgFileDialog.getImageAlt() + '"'; }
					if(imgFileDialog.getImageWidth() != null && imgFileDialog.getImageWidth().length() > 0) { sImgTag = sImgTag + " width=\"" + imgFileDialog.getImageWidth() + '"'; }
					if(imgFileDialog.getImageHeight() != null && imgFileDialog.getImageHeight().length() > 0) { sImgTag = sImgTag + " height=\"" + imgFileDialog.getImageHeight() + '"'; }
					sImgTag = sImgTag + "/>";
					data.getHtmlKit().insertHTML(data.getHtmlDoc(), caretPos, sImgTag, 0, 0, HTML.Tag.IMG);
					data.getJtpMain().setCaretPosition(caretPos + 1);
					refreshOnUpdate();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
		imgFileDialog.dispose();
		imgFileDialog = null;
	}

	/** Method for describing the node hierarchy of the document
	  */
	private void describeDocument(StyledDocument doc)
	{
		Element[] elements = doc.getRootElements();
		for(Element elem : elements)
		{
			data.setIndent(data.getIndentStep());
			for(int j = 0; j < data.getIndent(); j++) { System.out.print(" "); }
			System.out.print(elem);
			traverseElement(elem);
			System.out.println("");
		}
	}

	/** Traverses nodes for the describing method
	  */
	private void traverseElement(Element element)
	{
		data.setIndent(data.getIndentStep());
		for(int i = 0; i < element.getElementCount(); i++)
		{
			for(int j = 0; j < data.getIndent(); j++) { System.out.print(" "); }
			System.out.print(element.getElement(i));
			traverseElement(element.getElement(i));
		}
		data.setIndent(data.getIndentStep());
	}

	/** Convenience method for obtaining the WYSIWYG JTextPane
	  */
	public JTextPane getTextPane()
	{
		return data.getJtpMain();
	}

	/** Convenience method for obtaining the Source JTextPane
	  */
	public JTextArea getSourcePane()
	{
		return data.getJtpSource();
	}

	/** Convenience method for obtaining the application as a Frame
	  */
	public Frame getFrame()
	{
		return data.getFrameHandler();
	}

	/** Convenience method for setting the parent Frame
	  */
	public void setFrame(Frame parentFrame)
	{
		data.setFrameHandler(parentFrame);
	}

	/** Convenience method for obtaining the pre-generated menu bar
	  */
	public JMenuBar getMenuBar()
	{
		return data.getjMenuBar();
	}
	
	/** Convenience method for obtaining htMenus
	 */
	public Hashtable<String, JMenu> getHTMenus()
	{
		return htMenus;
	}

	/** Convenience method for obtaining a custom menu bar
	  */
	public JMenuBar getCustomMenuBar(Vector<String> vcMenus)
	{
		data.setjMenuBar(new JMenuBar());
		for(int i = 0; i < vcMenus.size(); i++)
		{
			String menuToAdd = vcMenus.elementAt(i).toLowerCase();
			if(htMenus.containsKey(menuToAdd))
			{
				data.getjMenuBar().add((JMenu)(htMenus.get(menuToAdd)));
			}
		}
		return data.getjMenuBar();
	}
	
	

	/** Convenience method for creating the multiple toolbar set from a sequence string
	  */
	public void initializeMultiToolbars(String toolbarSeq)
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

		customizeToolBar(TOOLBAR_MAIN,   vcToolPicks.get(0), true);
		customizeToolBar(TOOLBAR_FORMAT, vcToolPicks.get(1), true);
		customizeToolBar(TOOLBAR_STYLES, vcToolPicks.get(2), true);
	}

	/** Convenience method for creating the single toolbar from a sequence string
	  */
	public void initializeSingleToolbar(String toolbarSeq)
	{
		Vector<String> vcToolPicks = new Vector<String>();
		StringTokenizer stToolbars = new StringTokenizer(toolbarSeq.toUpperCase(), "|");
		while(stToolbars.hasMoreTokens())
		{
			String sKey = stToolbars.nextToken();
			if(sKey.equals("*"))
			{
				// ignore "next toolbar" symbols in single toolbar processing
			}
			else
			{
				vcToolPicks.add(sKey);
			}
		}

		customizeToolBar(TOOLBAR_SINGLE, vcToolPicks, true);
	}

	/** Convenience method for obtaining the pre-generated toolbar
	  */
	public JToolBar getToolBar(boolean isShowing)
	{
		if(data.getjToolBar() != null)
		{
			data.getJcbmiViewToolbar().setState(isShowing);
			return data.getjToolBar();
		}
		return (JToolBar)null;
	}

	/** Convenience method for obtaining the pre-generated main toolbar
	  */
	public JToolBar getToolBarMain(boolean isShowing)
	{
		if(data.getjToolBarMain() != null)
		{
			data.getJcbmiViewToolbarMain().setState(isShowing);
			return data.getjToolBarMain();
		}
		return (JToolBar)null;
	}

	/** Convenience method for obtaining the pre-generated main toolbar
	  */
	public JToolBar getToolBarFormat(boolean isShowing)
	{
		if(data.getjToolBarFormat() != null)
		{
			data.getJcbmiViewToolbarFormat().setState(isShowing);
			return data.getjToolBarFormat();
		}
		return (JToolBar)null;
	}

	/** Convenience method for obtaining the pre-generated main toolbar
	  */
	public JToolBar getToolBarStyles(boolean isShowing)
	{
		if(data.getjToolBarStyles() != null)
		{
			data.getJcbmiViewToolbarStyles().setState(isShowing);
			return data.getjToolBarStyles();
		}
		return (JToolBar)null;
	}

	/** Convenience method for obtaining a custom toolbar
	  */
	public JToolBar customizeToolBar(int whichToolBar, Vector<String> vcTools, boolean isShowing)
	{
		JToolBar jToolBarX = new JToolBar(JToolBar.HORIZONTAL);
		jToolBarX.setFloatable(false);
		for(int i = 0; i < vcTools.size(); i++)
		{
			String toolToAdd = vcTools.elementAt(i).toUpperCase();
			if(toolToAdd.equals(KEY_TOOL_SEP))
			{
				jToolBarX.add(new JToolBar.Separator());
			}
			else if(htTools.containsKey(toolToAdd))
			{
				if(htTools.get(toolToAdd) instanceof JButtonNoFocus)
				{
					jToolBarX.add((JButtonNoFocus)(htTools.get(toolToAdd)));
				}
				else if(htTools.get(toolToAdd) instanceof JToggleButtonNoFocus)
				{
					jToolBarX.add((JToggleButtonNoFocus)(htTools.get(toolToAdd)));
				}
				else if(htTools.get(toolToAdd) instanceof JComboBoxNoFocus)
				{
					jToolBarX.add((JComboBoxNoFocus)(htTools.get(toolToAdd)));
				}
				else
				{
					jToolBarX.add((JComponent)(htTools.get(toolToAdd)));
				}
			}
		}
		if(whichToolBar == TOOLBAR_SINGLE)
		{
			data.setjToolBar(jToolBarX);
			data.getjToolBar().setVisible(isShowing);
			data.getJcbmiViewToolbar().setSelected(isShowing);
			return data.getjToolBar();
		}
		else if(whichToolBar == TOOLBAR_MAIN)
		{
			data.setjToolBarMain(jToolBarX);
			data.getjToolBarMain().setVisible(isShowing);
			data.getJcbmiViewToolbarMain().setSelected(isShowing);
			return data.getjToolBarMain();
		}
		else if(whichToolBar == TOOLBAR_FORMAT)
		{
			data.setjToolBarFormat(jToolBarX);
			data.getjToolBarFormat().setVisible(isShowing);
			data.getJcbmiViewToolbarFormat().setSelected(isShowing);
			return data.getjToolBarFormat();
		}
		else if(whichToolBar == TOOLBAR_STYLES)
		{
			data.setjToolBarStyles(jToolBarX);
			data.getjToolBarStyles().setVisible(isShowing);
			data.getJcbmiViewToolbarStyles().setSelected(isShowing);
			return data.getjToolBarStyles();
		}
		else
		{
			data.setjToolBarMain(jToolBarX);
			data.getjToolBarMain().setVisible(isShowing);
			data.getJcbmiViewToolbarMain().setSelected(isShowing);
			return data.getjToolBarMain();
		}
	}

	/** Convenience method for activating/deactivating formatting commands
	  * depending on the active editing pane
	  */
	private void setFormattersActive(boolean state)
	{
		data.getActionFontBold().setEnabled(state);
		data.getActionFontItalic().setEnabled(state);
		data.getActionFontUnderline().setEnabled(state);
		data.getActionFontStrike().setEnabled(state);
		data.getActionFontSuperscript().setEnabled(state);
		data.getActionFontSubscript().setEnabled(state);
		data.getActionListUnordered().setEnabled(state);
		data.getActionListOrdered().setEnabled(state);
		data.getActionSelectFont().setEnabled(state);
		data.getActionAlignLeft().setEnabled(state);
		data.getActionAlignCenter().setEnabled(state);
		data.getActionAlignRight().setEnabled(state);
		data.getActionAlignJustified().setEnabled(state);
		data.getActionInsertAnchor().setEnabled(state);
		data.getJbtnUnicode().setEnabled(state);
		data.getJbtnUnicodeMath().setEnabled(state);
		data.getJcmbStyleSelector().setEnabled(state);
		data.getJcmbFontSelector().setEnabled(state);
		data.getjMenuFont().setEnabled(state);
		data.getjMenuFormat().setEnabled(state);
		data.getjMenuInsert().setEnabled(state);
		data.getjMenuTable().setEnabled(state);
		data.getjMenuForms().setEnabled(state);
	}

	/** Convenience method for obtaining the current file handle
	  */
	public File getCurrentFile()
	{
		return data.getCurrentFile();
	}

	/** Convenience method for obtaining the application name
	  */
	public String getAppName()
	{
		return data.getAppName();
	}

	/** Convenience method for obtaining the document text
	  */
	public String getDocumentText()
	{
		if(isSourceWindowActive())
		{
			return data.getJtpSource().getText();
		}
		else
		{
			return data.getJtpMain().getText();
		}
	}

	/** Convenience method for obtaining the document text
	  * contained within a tag pair
	  */
	public String getDocumentSubText(String tagBlock)
	{
		return getSubText(tagBlock);
	}

	/** Method for extracting the text within a tag
	  */
	private String getSubText(String containingTag)
	{
		data.getJtpSource().setText(data.getJtpMain().getText());
		String docTextCase = data.getJtpSource().getText().toLowerCase();
		int tagStart       = docTextCase.indexOf("<" + containingTag.toLowerCase());
		int tagStartClose  = docTextCase.indexOf(">", tagStart) + 1;
		String closeTag    = "</" + containingTag.toLowerCase() + ">";
		int tagEndOpen     = docTextCase.indexOf(closeTag);
		if(tagStartClose < 0) { tagStartClose = 0; }
		if(tagEndOpen < 0 || tagEndOpen > docTextCase.length()) { tagEndOpen = docTextCase.length(); }
		return data.getJtpSource().getText().substring(tagStartClose, tagEndOpen);
	}

	/** Convenience method for obtaining the document text
		* contained within the BODY tags (a common request)
	  */
	public String getDocumentBody()
	{
		return getSubText("body");
	}

	/** Convenience method for setting the document text
	  */
	public void setDocumentText(String sText)
	{
		data.getJtpMain().setText(sText);
		((HTMLEditorKit)(data.getJtpMain().getEditorKit())).setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
		data.getJtpSource().setText(data.getJtpMain().getText());
	}

	/** Convenience method for setting the source document
	  */
	public void setSourceDocument(StyledDocument sDoc)
	{
		data.getJtpSource().getDocument().removeDocumentListener(this);
		data.getJtpSource().setDocument(sDoc);
		data.getJtpSource().getDocument().addDocumentListener(this);
		data.getJtpMain().setText(data.getJtpSource().getText());
		((HTMLEditorKit)(data.getJtpMain().getEditorKit())).setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
	}

	/** Convenience method for communicating the current font selection to the CustomAction class
	  */
	public String getFontNameFromSelector()
	{
		if(data.getJcmbFontSelector() == null || data.getJcmbFontSelector().getSelectedItem().equals(Translatrix.getTranslationString("SelectorToolFontsDefaultFont")))
		{
			return (String)null;
		}
		else
		{
			return data.getJcmbFontSelector().getSelectedItem().toString();
		}
	}

	/** Convenience method for obtaining the document text
	  */
	private void updateTitle()
	{
		data.getFrameHandler().setTitle(data.getAppName() + (data.getCurrentFile() == null ? "" : " - " + data.getCurrentFile().getName()));
	}

	/** Convenience method for clearing out the UndoManager
	  */
	public void purgeUndos()
	{
		if(data.getUndoMngr() != null)
		{
			data.getUndoMngr().discardAllEdits();
			data.getUndoAction().updateUndoState();
			data.getRedoAction().updateRedoState();
		}
	}

	/** Convenience method for refreshing and displaying changes
	  */
	public void refreshOnUpdate()
	{
		int caretPos = data.getJtpMain().getCaretPosition();
		data.getJtpMain().setText(data.getJtpMain().getText());
		data.getJtpSource().setText(data.getJtpMain().getText());
		data.getJtpMain().setText(data.getJtpSource().getText());
		try { data.getJtpMain().setCaretPosition(caretPos); } catch(IllegalArgumentException iea) { /* caret position bad, probably follows a deletion */ }
		this.repaint();
	}

	/** Convenience method for deallocating the app resources
	  */
	public void dispose()
	{
		data.getFrameHandler().dispose();
		System.exit(0);
	}

	/** Convenience method for fetching icon images from jar file
	  */
	private ImageIcon getEkitIcon(String iconName)
	{
		URL imageURL = getClass().getResource("icons/" + iconName + "HK.png");
		if(imageURL != null)
		{
			return new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageURL));
		}
		imageURL = getClass().getResource("icons/" + iconName + "HK.gif");
		if(imageURL != null)
		{
			return new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageURL));
		}
		return (ImageIcon)null;
	}

	/** Convenience method for outputting exceptions
	  */
	private void logException(String internalMessage, Exception e)
	{
		System.err.println(internalMessage);
		e.printStackTrace(System.err);
	}

	/** Convenience method for determining if the source window is active
	  */
	private boolean isSourceWindowActive()
	{
		return (data.getJspSource() != null && data.getJspSource() == data.getJspltDisplay().getRightComponent());
	}

	/** Method for toggling source window visibility
	  */
	private void toggleSourceWindow()
	{
		if(!(isSourceWindowActive()))
		{
			data.getJtpSource().setText(data.getJtpMain().getText());
			data.getJspltDisplay().setRightComponent(data.getJspSource());
			if(data.isExclusiveEdit())
			{
				data.getJspltDisplay().setDividerLocation(0);
				data.getJspltDisplay().setEnabled(false);
				data.getJtpSource().requestFocus();
			}
			else
			{
				data.getJspltDisplay().setDividerLocation(data.getiSplitPos());
				data.getJspltDisplay().setEnabled(true);
			}
		}
		else
		{
			data.getJtpMain().setText(data.getJtpSource().getText());
			data.setiSplitPos(data.getJspltDisplay().getDividerLocation());
			data.getJspltDisplay().remove(data.getJspSource());
			data.getJtpMain().requestFocus();
		}
		this.validate();
		data.getJcbmiViewSource().setSelected(isSourceWindowActive());
		data.getJtbtnViewSource().setSelected(isSourceWindowActive());
	}

	/** Searches the specified element for CLASS attribute setting
	  */
	private String findStyle(Element element)
	{
		AttributeSet as = element.getAttributes();
		if(as == null)
		{
			return null;
		}
		Object val = as.getAttribute(HTML.Attribute.CLASS);
		if(val != null && (val instanceof String))
		{
			return (String)val;
		}
		for(Enumeration e = as.getAttributeNames(); e.hasMoreElements();)
		{
			Object key = e.nextElement();
			if(key instanceof HTML.Tag)
			{
				AttributeSet eas = (AttributeSet)(as.getAttribute(key));
				if(eas != null)
				{
					val = eas.getAttribute(HTML.Attribute.CLASS);
					if(val != null)
					{
						return (String)val;
					}
				}
			}

		}
		return null;
	}

	/** Handles caret tracking and related events, such as displaying the current style
	  * of the text under the caret
	  */
	private void handleCaretPositionChange(CaretEvent ce)
	{
		int caretPos = ce.getDot();
		Element	element = data.getHtmlDoc().getCharacterElement(caretPos);
/*
//---- TAG EXPLICATOR CODE -------------------------------------------
		javax.swing.text.ElementIterator ei = new javax.swing.text.ElementIterator(htmlDoc);
		Element ele;
		while((ele = ei.next()) != null)
		{
			System.out.println("ELEMENT : " + ele.getName());
		}
		System.out.println("ELEMENT:" + element.getName());
		Element elementParent = element.getParentElement();
		System.out.println("ATTRS:");
		AttributeSet attribs = elementParent.getAttributes();
		for(Enumeration eAttrs = attribs.getAttributeNames(); eAttrs.hasMoreElements();)
		{
			System.out.println("  " + eAttrs.nextElement().toString());
		}
		while(elementParent != null && !elementParent.getName().equals("body"))
		{
			String parentName = elementParent.getName();
			System.out.println("PARENT:" + parentName);
			System.out.println("ATTRS:");
			attribs = elementParent.getAttributes();
			for(Enumeration eAttr = attribs.getAttributeNames(); eAttr.hasMoreElements();)
			{
				System.out.println("  " + eAttr.nextElement().toString());
			}
			elementParent = elementParent.getParentElement();
		}
//---- END -------------------------------------------
*/
		if(data.getJtpMain().hasFocus())
		{
			if(element == null)
			{
				return;
			}
			String style = null;
			Vector<Element> vcStyles = new Vector<Element>();
			while(element != null)
			{
				if(style == null)
				{
					style = findStyle(element);
				}
				vcStyles.add(element);
				element = element.getParentElement();
			}
			int stylefound = -1;
			if(style != null)
			{
				for(int i = 0; i < data.getJcmbStyleSelector().getItemCount(); i++)
				{
					String in = (String)(data.getJcmbStyleSelector().getItemAt(i));
					if(in.equalsIgnoreCase(style))
					{
						stylefound = i;
						break;
					}
				}
			}
			if(stylefound > -1)
			{
				data.getJcmbStyleSelector().getAction().setEnabled(false);
				data.getJcmbStyleSelector().setSelectedIndex(stylefound);
				data.getJcmbStyleSelector().getAction().setEnabled(true);
			}
			else
			{
				data.getJcmbStyleSelector().setSelectedIndex(0);
			}
			// see if current font face is set
			if(data.getJcmbFontSelector() != null && data.getJcmbFontSelector().isVisible())
			{
				AttributeSet mainAttrs = data.getJtpMain().getCharacterAttributes();
				Enumeration e = mainAttrs.getAttributeNames();
				Object activeFontName = (Object)(Translatrix.getTranslationString("SelectorToolFontsDefaultFont"));
				while(e.hasMoreElements())
				{
					Object nexte = e.nextElement();
					if(nexte.toString().toLowerCase().equals("face") || nexte.toString().toLowerCase().equals("font-family"))
					{
						activeFontName = mainAttrs.getAttribute(nexte);
						break;
					}
				}
				data.getJcmbFontSelector().getAction().setEnabled(false);
				data.getJcmbFontSelector().getModel().setSelectedItem(activeFontName);
				data.getJcmbFontSelector().getAction().setEnabled(true);
			}
		}
	}

	/** Utility methods
	  */
	public ExtendedHTMLDocument getExtendedHtmlDoc()
	{
		return (ExtendedHTMLDocument)data.getHtmlDoc();
	}

	public int getCaretPosition()
	{
		return data.getJtpMain().getCaretPosition();
	}

	public void setCaretPosition(int newPositon)
	{
		boolean end = true;
		do
		{
			end = true;
			try
			{
				data.getJtpMain().setCaretPosition(newPositon);
			}
			catch(IllegalArgumentException iae)
			{
				end = false;
				newPositon--;
			}
		} while(!end && newPositon >= 0);
	}

	/** Accessors for enter key behaviour flag
	  */
	  public boolean getEnterKeyIsBreak()
	  {
	  	return data.isEnterIsBreak();
	  }

	  public void setEnterKeyIsBreak(boolean b)
	  {
	  	data.setEnterIsBreak(b);
		data.getJcbmiEnterKeyParag().setSelected(!data.isEnterIsBreak());
		data.getJcbmiEnterKeyBreak().setSelected(data.isEnterIsBreak());
	  }

/* Inner Classes --------------------------------------------- */

	/** Class for implementing Undo as an autonomous action
	  */
	class UndoAction extends AbstractAction
	{
		public UndoAction()
		{
			super(Translatrix.getTranslationString("Undo"));
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				data.getUndoMngr().undo();
			}
			catch(CannotUndoException ex)
			{
				ex.printStackTrace();
			}
			updateUndoState();
			data.getRedoAction().updateRedoState();
		}

		protected void updateUndoState()
		{
			setEnabled(data.getUndoMngr().canUndo());
		}
	}

	/** Class for implementing Redo as an autonomous action
	  */
	class RedoAction extends AbstractAction
	{
		public RedoAction()
		{
			super(Translatrix.getTranslationString("Redo"));
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				data.getUndoMngr().redo();
			}
			catch(CannotUndoException ex)
			{
				ex.printStackTrace();
			}
			updateRedoState();
			data.getUndoAction().updateUndoState();
		}

		protected void updateRedoState()
		{
			setEnabled(data.getUndoMngr().canRedo());
		}
	}

	/** Class for implementing the Undo listener to handle the Undo and Redo actions
	  */
	class CustomUndoableEditListener implements UndoableEditListener
	{
		public void undoableEditHappened(UndoableEditEvent uee)
		{
			data.getUndoMngr().addEdit(uee.getEdit());
			data.getUndoAction().updateUndoState();
			data.getRedoAction().updateRedoState();
		}
	}

}
