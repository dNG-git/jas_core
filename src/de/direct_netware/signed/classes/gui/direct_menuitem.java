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
* Enhanced version of JMenuItem.
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class direct_menuitem extends JMenuItem
{
	public static final long serialVersionUID = -5635803555355860001L;

	public direct_menuitem () { super (); }
	public direct_menuitem (Icon f_icon) { super (f_icon); }

	public direct_menuitem (Icon f_icon,int f_keycode)
	{
		super (f_icon);
		setMnemonic (f_keycode);
	}

	public direct_menuitem (String f_item) { super (f_item); }
	public direct_menuitem (String f_title,int f_keycode) { super (f_title,f_keycode); }
	public direct_menuitem (String f_item,Icon f_icon) { super (f_item,f_icon); }

	public direct_menuitem (String f_title,Icon f_icon,int f_keycode)
	{
		super (f_title,f_icon);
		setMnemonic (f_keycode);
	}

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}
}

//j// EOF