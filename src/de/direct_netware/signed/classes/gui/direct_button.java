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
* Antialiased version of JButton.
*
* @internal  We are using JavaDoc to automate the documentation process for
*            creating the Developer's Manual. All sections including these
*            special comments will be removed from the release source code.
*            Use the following line to ensure 76 character sizes:
* ----------------------------------------------------------------------------
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_core
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;w3c
*            W3C (R) Software License
*/

package de.direct_netware.signed.classes.gui;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.Border;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;

public class direct_button extends JButton
{
	public static final long serialVersionUID = 735299592508243254L;

	public direct_button ()
	{
		super ();
		init ();
	}

	public direct_button (Border f_border)
	{
		super ();
		init ();
		setBorder (f_border);
	}

	public direct_button (Action f_action)
	{
		super (f_action);
		init ();
	}

	public direct_button (Action f_action,Border f_border)
	{
		super (f_action);
		init ();
		setBorder (f_border);
	}

	public direct_button (Icon f_icon)
	{
		super (f_icon);
		init ();
	}

	public direct_button (Icon f_icon,Border f_border)
	{
		super (f_icon);
		init ();
		setBorder (f_border);
	}

	public direct_button (String f_button_title)
	{
		super (f_button_title);
		init ();
	}

	public direct_button (String f_button_title,Border f_border)
	{
		super (f_button_title);
		init ();
		setBorder (f_border);
	}

	public direct_button (String f_button_title,Icon f_icon)
	{
		super (f_button_title,f_icon);
		init ();
	}

	public direct_button (String f_button_title,Icon f_icon,Border f_border)
	{
		super (f_button_title,f_icon);
		init ();
		setBorder (f_border);
	}

	protected void init ()
	{
		Font f_font = UIManager.getFont ("Button.font");
		setFont (f_font);
	}

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}
}

//j// EOF