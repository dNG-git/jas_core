//j// BOF

/*n// NOTE
----------------------------------------------------------------------------
direct JAS
Java Application Services
----------------------------------------------------------------------------
(C) direct Netware Group - All rights reserved
http://www.direct-netware.de/redirect.php?jas

This work is distributed under the W3C (R) Software License, but without any
warranty; without even the implied warranty of merchantability or fitness
for a particular purpose.
----------------------------------------------------------------------------
http://www.direct-netware.de/redirect.php?licenses;w3c
----------------------------------------------------------------------------
NOTE_END //n*/
/**
* Antialiased version of JTextField.
*
* @internal  We are using JavaDoc to automate the documentation process for
*            creating the Developer's Manual. All sections including these
*            special comments will be removed from the release source code.
*            Use the following line to ensure 76 character sizes:
* ----------------------------------------------------------------------------
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_basic
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;w3c
*            W3C (R) Software License
*/

package de.direct_netware.signed.classes.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class direct_input extends JTextField
{
	public static final long serialVersionUID = 7215137384569281201L;

	public direct_input () { this (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED)); }

	public direct_input (Border f_border)
	{
		super ();
		init ();
		setBorder (f_border);
	}

	public direct_input (int f_columns) { this (f_columns,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_input (int f_columns,Border f_border)
	{
		super (f_columns);
		init ();
		setBorder (f_border);
	}

	public direct_input (String f_content) { this (f_content,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_input (String f_content,Border f_border)
	{
		super (f_content);
		init ();
		setBorder (f_border);
	}

	public direct_input (String f_content,int f_columns) { this (f_content,f_columns,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_input (String f_content,int f_columns,Border f_border)
	{
		super (f_content,f_columns);
		init ();
		setBorder (f_border);
	}

	public direct_input (Document f_document,String f_content,int f_columns) { this (f_document,f_content,f_columns,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_input (Document f_document,String f_content,int f_columns,Border f_border)
	{
		super (f_document,f_content,f_columns);
		init ();
		setBorder (f_border);
	}

	protected void init () { setMargin (new Insets (2,2,2,2)); }

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}
}

//j// EOF