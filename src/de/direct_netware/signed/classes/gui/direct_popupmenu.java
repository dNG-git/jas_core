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
* Enhanced version of JPopupMenu.
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

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPopupMenu;

public class direct_popupmenu extends JPopupMenu implements MouseListener
{
	public static final long serialVersionUID = -1090407783786969391L;

	public direct_popupmenu ()
	{
		super ();
		setDoubleBuffered (true);
	}

	public direct_popupmenu (String f_title)
	{
		super (f_title);
		setDoubleBuffered (true);
	}

	public void mouseClicked (MouseEvent f_event)
	{
		if (f_event.isPopupTrigger ()) { show ((f_event.getComponent ()),(f_event.getX ()),(f_event.getY ())); }
	}

	public void mouseEntered (MouseEvent f_event) {}
	public void mouseExited (MouseEvent f_event) {}
	public void mousePressed (MouseEvent f_event) { mouseClicked (f_event); }
	public void mouseReleased (MouseEvent f_event) { mouseClicked (f_event); }

	public void add (direct_menuitem f_menuitem,ActionListener f_actionlistener)
	{
		f_menuitem.addActionListener (f_actionlistener);

		String f_menutitle = f_menuitem.getText ();
		if (f_menutitle.length () > 0) { f_menuitem.setMnemonic (f_menutitle.charAt (0)); }

		add (f_menuitem);
	}

	public void addSeperator () { addSeparator (); }

	public MouseListener getPopupListener () { return this; }

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}
}

//j// EOF