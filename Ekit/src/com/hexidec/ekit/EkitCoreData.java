package com.hexidec.ekit;

import java.awt.Frame;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.StyledEditorKit.BoldAction;
import javax.swing.text.StyledEditorKit.ItalicAction;
import javax.swing.text.StyledEditorKit.UnderlineAction;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.UndoManager;

import com.hexidec.ekit.EkitCore.RedoAction;
import com.hexidec.ekit.EkitCore.UndoAction;
import com.hexidec.ekit.action.AlignAction;
import com.hexidec.ekit.action.CustomAction;
import com.hexidec.ekit.action.FormatAction;
import com.hexidec.ekit.action.ListAutomationAction;
import com.hexidec.ekit.action.SetFontFamilyAction;
import com.hexidec.ekit.component.ExtendedHTMLDocument;
import com.hexidec.ekit.component.ExtendedHTMLEditorKit;
import com.hexidec.ekit.component.HTMLUtilities;
import com.hexidec.ekit.component.JButtonNoFocus;
import com.hexidec.ekit.component.JComboBoxNoFocus;
import com.hexidec.ekit.component.JToggleButtonNoFocus;

public class EkitCoreData {
	private JSplitPane jspltDisplay;
	private JTextPane jtpMain;
	private ExtendedHTMLEditorKit htmlKit;
	private ExtendedHTMLDocument htmlDoc;
	private StyleSheet styleSheet;
	private JTextArea jtpSource;
	private JScrollPane jspSource;
	private JToolBar jToolBar;
	private JToolBar jToolBarMain;
	private JToolBar jToolBarFormat;
	private JToolBar jToolBarStyles;
	private JButtonNoFocus jbtnNewHTML;
	private JButtonNoFocus jbtnNewStyledHTML;
	private JButtonNoFocus jbtnOpenHTML;
	private JButtonNoFocus jbtnSaveHTML;
	private JButtonNoFocus jbtnPrint;
	private JButtonNoFocus jbtnCut;
	private JButtonNoFocus jbtnCopy;
	private JButtonNoFocus jbtnPaste;
	private JButtonNoFocus jbtnPasteX;
	private JButtonNoFocus jbtnUndo;
	private JButtonNoFocus jbtnRedo;
	private JButtonNoFocus jbtnBold;
	private JButtonNoFocus jbtnItalic;
	private JButtonNoFocus jbtnUnderline;
	private JButtonNoFocus jbtnStrike;
	private JButtonNoFocus jbtnSuperscript;
	private JButtonNoFocus jbtnSubscript;
	private JButtonNoFocus jbtnUList;
	private JButtonNoFocus jbtnOList;
	private JButtonNoFocus jbtnAlignLeft;
	private JButtonNoFocus jbtnAlignCenter;
	private JButtonNoFocus jbtnAlignRight;
	private JButtonNoFocus jbtnAlignJustified;
	private JButtonNoFocus jbtnFind;
	private JButtonNoFocus jbtnUnicode;
	private JButtonNoFocus jbtnUnicodeMath;
	private JButtonNoFocus jbtnAnchor;
	private JButtonNoFocus jbtnInsertTable;
	private JButtonNoFocus jbtnEditTable;
	private JButtonNoFocus jbtnEditCell;
	private JButtonNoFocus jbtnInsertRow;
	private JButtonNoFocus jbtnInsertColumn;
	private JButtonNoFocus jbtnDeleteRow;
	private JButtonNoFocus jbtnDeleteColumn;
	private JToggleButtonNoFocus jtbtnViewSource;
	private JComboBoxNoFocus jcmbStyleSelector;
	private JComboBoxNoFocus jcmbFontSelector;
	private Frame frameHandler;
	private HTMLUtilities htmlUtilities;
	private BoldAction actionFontBold;
	private ItalicAction actionFontItalic;
	private UnderlineAction actionFontUnderline;
	private FormatAction actionFontStrike;
	private FormatAction actionFontSuperscript;
	private FormatAction actionFontSubscript;
	private ListAutomationAction actionListUnordered;
	private ListAutomationAction actionListOrdered;
	private SetFontFamilyAction actionSelectFont;
	private AlignAction actionAlignLeft;
	private AlignAction actionAlignCenter;
	private AlignAction actionAlignRight;
	private AlignAction actionAlignJustified;
	private CustomAction actionInsertAnchor;
	private UndoManager undoMngr;
	private UndoAction undoAction;
	private RedoAction redoAction;
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
	private JMenu jMenuToolbars;
	private JCheckBoxMenuItem jcbmiViewToolbar;
	private JCheckBoxMenuItem jcbmiViewToolbarMain;
	private JCheckBoxMenuItem jcbmiViewToolbarFormat;
	private JCheckBoxMenuItem jcbmiViewToolbarStyles;
	private JCheckBoxMenuItem jcbmiViewSource;
	private JCheckBoxMenuItem jcbmiEnterKeyParag;
	private JCheckBoxMenuItem jcbmiEnterKeyBreak;
	private String appName;
	private String menuDialog;
	private boolean useFormIndicator;
	private String clrFormIndicator;
	private Clipboard sysClipboard;
	private DataFlavor dfPlainText;
	private int iSplitPos;
	private boolean exclusiveEdit;
	private boolean preserveUnknownTags;
	private String lastSearchFindTerm;
	private String lastSearchReplaceTerm;
	private boolean lastSearchCaseSetting;
	private boolean lastSearchTopSetting;
	private File currentFile;
	private String imageChooserStartDir;
	private int indent;
	private int indentStep;
	private boolean enterIsBreak;
	private String[] extsHTML;
	private String[] extsCSS;
	private String[] extsIMG;
	private String[] extsRTF;
	private String[] extsB64;
	private String[] extsSer;
	private int CTRLKEY;

	public EkitCoreData(HTMLUtilities htmlUtilities, String appName,
			String menuDialog, boolean useFormIndicator,
			String clrFormIndicator, int iSplitPos, boolean exclusiveEdit,
			boolean preserveUnknownTags, String lastSearchFindTerm,
			String lastSearchReplaceTerm, boolean lastSearchCaseSetting,
			boolean lastSearchTopSetting, File currentFile,
			String imageChooserStartDir, int indent, int indentStep,
			boolean enterIsBreak, String[] extsHTML, String[] extsCSS,
			String[] extsIMG, String[] extsRTF, String[] extsB64,
			String[] extsSer, int cTRLKEY) {
		this.htmlUtilities = htmlUtilities;
		this.appName = appName;
		this.menuDialog = menuDialog;
		this.useFormIndicator = useFormIndicator;
		this.clrFormIndicator = clrFormIndicator;
		this.iSplitPos = iSplitPos;
		this.exclusiveEdit = exclusiveEdit;
		this.preserveUnknownTags = preserveUnknownTags;
		this.lastSearchFindTerm = lastSearchFindTerm;
		this.lastSearchReplaceTerm = lastSearchReplaceTerm;
		this.lastSearchCaseSetting = lastSearchCaseSetting;
		this.lastSearchTopSetting = lastSearchTopSetting;
		this.currentFile = currentFile;
		this.imageChooserStartDir = imageChooserStartDir;
		this.indent = indent;
		this.indentStep = indentStep;
		this.enterIsBreak = enterIsBreak;
		this.extsHTML = extsHTML;
		this.extsCSS = extsCSS;
		this.extsIMG = extsIMG;
		this.extsRTF = extsRTF;
		this.extsB64 = extsB64;
		this.extsSer = extsSer;
		CTRLKEY = cTRLKEY;
	}

	public JSplitPane getJspltDisplay() {
		return jspltDisplay;
	}

	public void setJspltDisplay(JSplitPane jspltDisplay) {
		this.jspltDisplay = jspltDisplay;
	}

	public JTextPane getJtpMain() {
		return jtpMain;
	}

	public void setJtpMain(JTextPane jtpMain) {
		this.jtpMain = jtpMain;
	}

	public ExtendedHTMLEditorKit getHtmlKit() {
		return htmlKit;
	}

	public void setHtmlKit(ExtendedHTMLEditorKit htmlKit) {
		this.htmlKit = htmlKit;
	}

	public ExtendedHTMLDocument getHtmlDoc() {
		return htmlDoc;
	}

	public void setHtmlDoc(ExtendedHTMLDocument htmlDoc) {
		this.htmlDoc = htmlDoc;
	}

	public StyleSheet getStyleSheet() {
		return styleSheet;
	}

	public void setStyleSheet(StyleSheet styleSheet) {
		this.styleSheet = styleSheet;
	}

	public JTextArea getJtpSource() {
		return jtpSource;
	}

	public void setJtpSource(JTextArea jtpSource) {
		this.jtpSource = jtpSource;
	}

	public JScrollPane getJspSource() {
		return jspSource;
	}

	public void setJspSource(JScrollPane jspSource) {
		this.jspSource = jspSource;
	}

	public JToolBar getjToolBar() {
		return jToolBar;
	}

	public void setjToolBar(JToolBar jToolBar) {
		this.jToolBar = jToolBar;
	}

	public JToolBar getjToolBarMain() {
		return jToolBarMain;
	}

	public void setjToolBarMain(JToolBar jToolBarMain) {
		this.jToolBarMain = jToolBarMain;
	}

	public JToolBar getjToolBarFormat() {
		return jToolBarFormat;
	}

	public void setjToolBarFormat(JToolBar jToolBarFormat) {
		this.jToolBarFormat = jToolBarFormat;
	}

	public JToolBar getjToolBarStyles() {
		return jToolBarStyles;
	}

	public void setjToolBarStyles(JToolBar jToolBarStyles) {
		this.jToolBarStyles = jToolBarStyles;
	}

	public JButtonNoFocus getJbtnNewHTML() {
		return jbtnNewHTML;
	}

	public void setJbtnNewHTML(JButtonNoFocus jbtnNewHTML) {
		this.jbtnNewHTML = jbtnNewHTML;
	}

	public JButtonNoFocus getJbtnNewStyledHTML() {
		return jbtnNewStyledHTML;
	}

	public void setJbtnNewStyledHTML(JButtonNoFocus jbtnNewStyledHTML) {
		this.jbtnNewStyledHTML = jbtnNewStyledHTML;
	}

	public JButtonNoFocus getJbtnOpenHTML() {
		return jbtnOpenHTML;
	}

	public void setJbtnOpenHTML(JButtonNoFocus jbtnOpenHTML) {
		this.jbtnOpenHTML = jbtnOpenHTML;
	}

	public JButtonNoFocus getJbtnSaveHTML() {
		return jbtnSaveHTML;
	}

	public void setJbtnSaveHTML(JButtonNoFocus jbtnSaveHTML) {
		this.jbtnSaveHTML = jbtnSaveHTML;
	}

	public JButtonNoFocus getJbtnPrint() {
		return jbtnPrint;
	}

	public void setJbtnPrint(JButtonNoFocus jbtnPrint) {
		this.jbtnPrint = jbtnPrint;
	}

	public JButtonNoFocus getJbtnCut() {
		return jbtnCut;
	}

	public void setJbtnCut(JButtonNoFocus jbtnCut) {
		this.jbtnCut = jbtnCut;
	}

	public JButtonNoFocus getJbtnCopy() {
		return jbtnCopy;
	}

	public void setJbtnCopy(JButtonNoFocus jbtnCopy) {
		this.jbtnCopy = jbtnCopy;
	}

	public JButtonNoFocus getJbtnPaste() {
		return jbtnPaste;
	}

	public void setJbtnPaste(JButtonNoFocus jbtnPaste) {
		this.jbtnPaste = jbtnPaste;
	}

	public JButtonNoFocus getJbtnPasteX() {
		return jbtnPasteX;
	}

	public void setJbtnPasteX(JButtonNoFocus jbtnPasteX) {
		this.jbtnPasteX = jbtnPasteX;
	}

	public JButtonNoFocus getJbtnUndo() {
		return jbtnUndo;
	}

	public void setJbtnUndo(JButtonNoFocus jbtnUndo) {
		this.jbtnUndo = jbtnUndo;
	}

	public JButtonNoFocus getJbtnRedo() {
		return jbtnRedo;
	}

	public void setJbtnRedo(JButtonNoFocus jbtnRedo) {
		this.jbtnRedo = jbtnRedo;
	}

	public JButtonNoFocus getJbtnBold() {
		return jbtnBold;
	}

	public void setJbtnBold(JButtonNoFocus jbtnBold) {
		this.jbtnBold = jbtnBold;
	}

	public JButtonNoFocus getJbtnItalic() {
		return jbtnItalic;
	}

	public void setJbtnItalic(JButtonNoFocus jbtnItalic) {
		this.jbtnItalic = jbtnItalic;
	}

	public JButtonNoFocus getJbtnUnderline() {
		return jbtnUnderline;
	}

	public void setJbtnUnderline(JButtonNoFocus jbtnUnderline) {
		this.jbtnUnderline = jbtnUnderline;
	}

	public JButtonNoFocus getJbtnStrike() {
		return jbtnStrike;
	}

	public void setJbtnStrike(JButtonNoFocus jbtnStrike) {
		this.jbtnStrike = jbtnStrike;
	}

	public JButtonNoFocus getJbtnSuperscript() {
		return jbtnSuperscript;
	}

	public void setJbtnSuperscript(JButtonNoFocus jbtnSuperscript) {
		this.jbtnSuperscript = jbtnSuperscript;
	}

	public JButtonNoFocus getJbtnSubscript() {
		return jbtnSubscript;
	}

	public void setJbtnSubscript(JButtonNoFocus jbtnSubscript) {
		this.jbtnSubscript = jbtnSubscript;
	}

	public JButtonNoFocus getJbtnUList() {
		return jbtnUList;
	}

	public void setJbtnUList(JButtonNoFocus jbtnUList) {
		this.jbtnUList = jbtnUList;
	}

	public JButtonNoFocus getJbtnOList() {
		return jbtnOList;
	}

	public void setJbtnOList(JButtonNoFocus jbtnOList) {
		this.jbtnOList = jbtnOList;
	}

	public JButtonNoFocus getJbtnAlignLeft() {
		return jbtnAlignLeft;
	}

	public void setJbtnAlignLeft(JButtonNoFocus jbtnAlignLeft) {
		this.jbtnAlignLeft = jbtnAlignLeft;
	}

	public JButtonNoFocus getJbtnAlignCenter() {
		return jbtnAlignCenter;
	}

	public void setJbtnAlignCenter(JButtonNoFocus jbtnAlignCenter) {
		this.jbtnAlignCenter = jbtnAlignCenter;
	}

	public JButtonNoFocus getJbtnAlignRight() {
		return jbtnAlignRight;
	}

	public void setJbtnAlignRight(JButtonNoFocus jbtnAlignRight) {
		this.jbtnAlignRight = jbtnAlignRight;
	}

	public JButtonNoFocus getJbtnAlignJustified() {
		return jbtnAlignJustified;
	}

	public void setJbtnAlignJustified(JButtonNoFocus jbtnAlignJustified) {
		this.jbtnAlignJustified = jbtnAlignJustified;
	}

	public JButtonNoFocus getJbtnFind() {
		return jbtnFind;
	}

	public void setJbtnFind(JButtonNoFocus jbtnFind) {
		this.jbtnFind = jbtnFind;
	}

	public JButtonNoFocus getJbtnUnicode() {
		return jbtnUnicode;
	}

	public void setJbtnUnicode(JButtonNoFocus jbtnUnicode) {
		this.jbtnUnicode = jbtnUnicode;
	}

	public JButtonNoFocus getJbtnUnicodeMath() {
		return jbtnUnicodeMath;
	}

	public void setJbtnUnicodeMath(JButtonNoFocus jbtnUnicodeMath) {
		this.jbtnUnicodeMath = jbtnUnicodeMath;
	}

	public JButtonNoFocus getJbtnAnchor() {
		return jbtnAnchor;
	}

	public void setJbtnAnchor(JButtonNoFocus jbtnAnchor) {
		this.jbtnAnchor = jbtnAnchor;
	}

	public JButtonNoFocus getJbtnInsertTable() {
		return jbtnInsertTable;
	}

	public void setJbtnInsertTable(JButtonNoFocus jbtnInsertTable) {
		this.jbtnInsertTable = jbtnInsertTable;
	}

	public JButtonNoFocus getJbtnEditTable() {
		return jbtnEditTable;
	}

	public void setJbtnEditTable(JButtonNoFocus jbtnEditTable) {
		this.jbtnEditTable = jbtnEditTable;
	}

	public JButtonNoFocus getJbtnEditCell() {
		return jbtnEditCell;
	}

	public void setJbtnEditCell(JButtonNoFocus jbtnEditCell) {
		this.jbtnEditCell = jbtnEditCell;
	}

	public JButtonNoFocus getJbtnInsertRow() {
		return jbtnInsertRow;
	}

	public void setJbtnInsertRow(JButtonNoFocus jbtnInsertRow) {
		this.jbtnInsertRow = jbtnInsertRow;
	}

	public JButtonNoFocus getJbtnInsertColumn() {
		return jbtnInsertColumn;
	}

	public void setJbtnInsertColumn(JButtonNoFocus jbtnInsertColumn) {
		this.jbtnInsertColumn = jbtnInsertColumn;
	}

	public JButtonNoFocus getJbtnDeleteRow() {
		return jbtnDeleteRow;
	}

	public void setJbtnDeleteRow(JButtonNoFocus jbtnDeleteRow) {
		this.jbtnDeleteRow = jbtnDeleteRow;
	}

	public JButtonNoFocus getJbtnDeleteColumn() {
		return jbtnDeleteColumn;
	}

	public void setJbtnDeleteColumn(JButtonNoFocus jbtnDeleteColumn) {
		this.jbtnDeleteColumn = jbtnDeleteColumn;
	}

	public JToggleButtonNoFocus getJtbtnViewSource() {
		return jtbtnViewSource;
	}

	public void setJtbtnViewSource(JToggleButtonNoFocus jtbtnViewSource) {
		this.jtbtnViewSource = jtbtnViewSource;
	}

	public JComboBoxNoFocus getJcmbStyleSelector() {
		return jcmbStyleSelector;
	}

	public void setJcmbStyleSelector(JComboBoxNoFocus jcmbStyleSelector) {
		this.jcmbStyleSelector = jcmbStyleSelector;
	}

	public JComboBoxNoFocus getJcmbFontSelector() {
		return jcmbFontSelector;
	}

	public void setJcmbFontSelector(JComboBoxNoFocus jcmbFontSelector) {
		this.jcmbFontSelector = jcmbFontSelector;
	}

	public Frame getFrameHandler() {
		return frameHandler;
	}

	public void setFrameHandler(Frame frameHandler) {
		this.frameHandler = frameHandler;
	}

	public HTMLUtilities getHtmlUtilities() {
		return htmlUtilities;
	}

	public void setHtmlUtilities(HTMLUtilities htmlUtilities) {
		this.htmlUtilities = htmlUtilities;
	}

	public BoldAction getActionFontBold() {
		return actionFontBold;
	}

	public void setActionFontBold(BoldAction actionFontBold) {
		this.actionFontBold = actionFontBold;
	}

	public ItalicAction getActionFontItalic() {
		return actionFontItalic;
	}

	public void setActionFontItalic(ItalicAction actionFontItalic) {
		this.actionFontItalic = actionFontItalic;
	}

	public UnderlineAction getActionFontUnderline() {
		return actionFontUnderline;
	}

	public void setActionFontUnderline(UnderlineAction actionFontUnderline) {
		this.actionFontUnderline = actionFontUnderline;
	}

	public FormatAction getActionFontStrike() {
		return actionFontStrike;
	}

	public void setActionFontStrike(FormatAction actionFontStrike) {
		this.actionFontStrike = actionFontStrike;
	}

	public FormatAction getActionFontSuperscript() {
		return actionFontSuperscript;
	}

	public void setActionFontSuperscript(FormatAction actionFontSuperscript) {
		this.actionFontSuperscript = actionFontSuperscript;
	}

	public FormatAction getActionFontSubscript() {
		return actionFontSubscript;
	}

	public void setActionFontSubscript(FormatAction actionFontSubscript) {
		this.actionFontSubscript = actionFontSubscript;
	}

	public ListAutomationAction getActionListUnordered() {
		return actionListUnordered;
	}

	public void setActionListUnordered(ListAutomationAction actionListUnordered) {
		this.actionListUnordered = actionListUnordered;
	}

	public ListAutomationAction getActionListOrdered() {
		return actionListOrdered;
	}

	public void setActionListOrdered(ListAutomationAction actionListOrdered) {
		this.actionListOrdered = actionListOrdered;
	}

	public SetFontFamilyAction getActionSelectFont() {
		return actionSelectFont;
	}

	public void setActionSelectFont(SetFontFamilyAction actionSelectFont) {
		this.actionSelectFont = actionSelectFont;
	}

	public AlignAction getActionAlignLeft() {
		return actionAlignLeft;
	}

	public void setActionAlignLeft(AlignAction actionAlignLeft) {
		this.actionAlignLeft = actionAlignLeft;
	}

	public AlignAction getActionAlignCenter() {
		return actionAlignCenter;
	}

	public void setActionAlignCenter(AlignAction actionAlignCenter) {
		this.actionAlignCenter = actionAlignCenter;
	}

	public AlignAction getActionAlignRight() {
		return actionAlignRight;
	}

	public void setActionAlignRight(AlignAction actionAlignRight) {
		this.actionAlignRight = actionAlignRight;
	}

	public AlignAction getActionAlignJustified() {
		return actionAlignJustified;
	}

	public void setActionAlignJustified(AlignAction actionAlignJustified) {
		this.actionAlignJustified = actionAlignJustified;
	}

	public CustomAction getActionInsertAnchor() {
		return actionInsertAnchor;
	}

	public void setActionInsertAnchor(CustomAction actionInsertAnchor) {
		this.actionInsertAnchor = actionInsertAnchor;
	}

	public UndoManager getUndoMngr() {
		return undoMngr;
	}

	public void setUndoMngr(UndoManager undoMngr) {
		this.undoMngr = undoMngr;
	}

	public UndoAction getUndoAction() {
		return undoAction;
	}

	public void setUndoAction(UndoAction undoAction) {
		this.undoAction = undoAction;
	}

	public RedoAction getRedoAction() {
		return redoAction;
	}

	public void setRedoAction(RedoAction redoAction) {
		this.redoAction = redoAction;
	}

	public JMenuBar getjMenuBar() {
		return jMenuBar;
	}

	public void setjMenuBar(JMenuBar jMenuBar) {
		this.jMenuBar = jMenuBar;
	}

	public JMenu getjMenuFile() {
		return jMenuFile;
	}

	public void setjMenuFile(JMenu jMenuFile) {
		this.jMenuFile = jMenuFile;
	}

	public JMenu getjMenuEdit() {
		return jMenuEdit;
	}

	public void setjMenuEdit(JMenu jMenuEdit) {
		this.jMenuEdit = jMenuEdit;
	}

	public JMenu getjMenuView() {
		return jMenuView;
	}

	public void setjMenuView(JMenu jMenuView) {
		this.jMenuView = jMenuView;
	}

	public JMenu getjMenuFont() {
		return jMenuFont;
	}

	public void setjMenuFont(JMenu jMenuFont) {
		this.jMenuFont = jMenuFont;
	}

	public JMenu getjMenuFormat() {
		return jMenuFormat;
	}

	public void setjMenuFormat(JMenu jMenuFormat) {
		this.jMenuFormat = jMenuFormat;
	}

	public JMenu getjMenuInsert() {
		return jMenuInsert;
	}

	public void setjMenuInsert(JMenu jMenuInsert) {
		this.jMenuInsert = jMenuInsert;
	}

	public JMenu getjMenuTable() {
		return jMenuTable;
	}

	public void setjMenuTable(JMenu jMenuTable) {
		this.jMenuTable = jMenuTable;
	}

	public JMenu getjMenuForms() {
		return jMenuForms;
	}

	public void setjMenuForms(JMenu jMenuForms) {
		this.jMenuForms = jMenuForms;
	}

	public JMenu getjMenuSearch() {
		return jMenuSearch;
	}

	public void setjMenuSearch(JMenu jMenuSearch) {
		this.jMenuSearch = jMenuSearch;
	}

	public JMenu getjMenuTools() {
		return jMenuTools;
	}

	public void setjMenuTools(JMenu jMenuTools) {
		this.jMenuTools = jMenuTools;
	}

	public JMenu getjMenuHelp() {
		return jMenuHelp;
	}

	public void setjMenuHelp(JMenu jMenuHelp) {
		this.jMenuHelp = jMenuHelp;
	}

	public JMenu getjMenuDebug() {
		return jMenuDebug;
	}

	public void setjMenuDebug(JMenu jMenuDebug) {
		this.jMenuDebug = jMenuDebug;
	}

	public JMenu getjMenuToolbars() {
		return jMenuToolbars;
	}

	public void setjMenuToolbars(JMenu jMenuToolbars) {
		this.jMenuToolbars = jMenuToolbars;
	}

	public JCheckBoxMenuItem getJcbmiViewToolbar() {
		return jcbmiViewToolbar;
	}

	public void setJcbmiViewToolbar(JCheckBoxMenuItem jcbmiViewToolbar) {
		this.jcbmiViewToolbar = jcbmiViewToolbar;
	}

	public JCheckBoxMenuItem getJcbmiViewToolbarMain() {
		return jcbmiViewToolbarMain;
	}

	public void setJcbmiViewToolbarMain(JCheckBoxMenuItem jcbmiViewToolbarMain) {
		this.jcbmiViewToolbarMain = jcbmiViewToolbarMain;
	}

	public JCheckBoxMenuItem getJcbmiViewToolbarFormat() {
		return jcbmiViewToolbarFormat;
	}

	public void setJcbmiViewToolbarFormat(
			JCheckBoxMenuItem jcbmiViewToolbarFormat) {
		this.jcbmiViewToolbarFormat = jcbmiViewToolbarFormat;
	}

	public JCheckBoxMenuItem getJcbmiViewToolbarStyles() {
		return jcbmiViewToolbarStyles;
	}

	public void setJcbmiViewToolbarStyles(
			JCheckBoxMenuItem jcbmiViewToolbarStyles) {
		this.jcbmiViewToolbarStyles = jcbmiViewToolbarStyles;
	}

	public JCheckBoxMenuItem getJcbmiViewSource() {
		return jcbmiViewSource;
	}

	public void setJcbmiViewSource(JCheckBoxMenuItem jcbmiViewSource) {
		this.jcbmiViewSource = jcbmiViewSource;
	}

	public JCheckBoxMenuItem getJcbmiEnterKeyParag() {
		return jcbmiEnterKeyParag;
	}

	public void setJcbmiEnterKeyParag(JCheckBoxMenuItem jcbmiEnterKeyParag) {
		this.jcbmiEnterKeyParag = jcbmiEnterKeyParag;
	}

	public JCheckBoxMenuItem getJcbmiEnterKeyBreak() {
		return jcbmiEnterKeyBreak;
	}

	public void setJcbmiEnterKeyBreak(JCheckBoxMenuItem jcbmiEnterKeyBreak) {
		this.jcbmiEnterKeyBreak = jcbmiEnterKeyBreak;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMenuDialog() {
		return menuDialog;
	}

	public void setMenuDialog(String menuDialog) {
		this.menuDialog = menuDialog;
	}

	public boolean isUseFormIndicator() {
		return useFormIndicator;
	}

	public void setUseFormIndicator(boolean useFormIndicator) {
		this.useFormIndicator = useFormIndicator;
	}

	public String getClrFormIndicator() {
		return clrFormIndicator;
	}

	public void setClrFormIndicator(String clrFormIndicator) {
		this.clrFormIndicator = clrFormIndicator;
	}

	public Clipboard getSysClipboard() {
		return sysClipboard;
	}

	public void setSysClipboard(Clipboard sysClipboard) {
		this.sysClipboard = sysClipboard;
	}

	public DataFlavor getDfPlainText() {
		return dfPlainText;
	}

	public void setDfPlainText(DataFlavor dfPlainText) {
		this.dfPlainText = dfPlainText;
	}

	public int getiSplitPos() {
		return iSplitPos;
	}

	public void setiSplitPos(int iSplitPos) {
		this.iSplitPos = iSplitPos;
	}

	public boolean isExclusiveEdit() {
		return exclusiveEdit;
	}

	public void setExclusiveEdit(boolean exclusiveEdit) {
		this.exclusiveEdit = exclusiveEdit;
	}

	public boolean isPreserveUnknownTags() {
		return preserveUnknownTags;
	}

	public void setPreserveUnknownTags(boolean preserveUnknownTags) {
		this.preserveUnknownTags = preserveUnknownTags;
	}

	public String getLastSearchFindTerm() {
		return lastSearchFindTerm;
	}

	public void setLastSearchFindTerm(String lastSearchFindTerm) {
		this.lastSearchFindTerm = lastSearchFindTerm;
	}

	public String getLastSearchReplaceTerm() {
		return lastSearchReplaceTerm;
	}

	public void setLastSearchReplaceTerm(String lastSearchReplaceTerm) {
		this.lastSearchReplaceTerm = lastSearchReplaceTerm;
	}

	public boolean isLastSearchCaseSetting() {
		return lastSearchCaseSetting;
	}

	public void setLastSearchCaseSetting(boolean lastSearchCaseSetting) {
		this.lastSearchCaseSetting = lastSearchCaseSetting;
	}

	public boolean isLastSearchTopSetting() {
		return lastSearchTopSetting;
	}

	public void setLastSearchTopSetting(boolean lastSearchTopSetting) {
		this.lastSearchTopSetting = lastSearchTopSetting;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public String getImageChooserStartDir() {
		return imageChooserStartDir;
	}

	public void setImageChooserStartDir(String imageChooserStartDir) {
		this.imageChooserStartDir = imageChooserStartDir;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public int getIndentStep() {
		return indentStep;
	}

	public void setIndentStep(int indentStep) {
		this.indentStep = indentStep;
	}

	public boolean isEnterIsBreak() {
		return enterIsBreak;
	}

	public void setEnterIsBreak(boolean enterIsBreak) {
		this.enterIsBreak = enterIsBreak;
	}

	public String[] getExtsHTML() {
		return extsHTML;
	}

	public void setExtsHTML(String[] extsHTML) {
		this.extsHTML = extsHTML;
	}

	public String[] getExtsCSS() {
		return extsCSS;
	}

	public void setExtsCSS(String[] extsCSS) {
		this.extsCSS = extsCSS;
	}

	public String[] getExtsIMG() {
		return extsIMG;
	}

	public void setExtsIMG(String[] extsIMG) {
		this.extsIMG = extsIMG;
	}

	public String[] getExtsRTF() {
		return extsRTF;
	}

	public void setExtsRTF(String[] extsRTF) {
		this.extsRTF = extsRTF;
	}

	public String[] getExtsB64() {
		return extsB64;
	}

	public void setExtsB64(String[] extsB64) {
		this.extsB64 = extsB64;
	}

	public String[] getExtsSer() {
		return extsSer;
	}

	public void setExtsSer(String[] extsSer) {
		this.extsSer = extsSer;
	}

	public int getCTRLKEY() {
		return CTRLKEY;
	}

	public void setCTRLKEY(int cTRLKEY) {
		CTRLKEY = cTRLKEY;
	}
}