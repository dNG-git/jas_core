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
* Enhanced version of JScrollPane.
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

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class direct_frame extends JScrollPane
{
	public static final long serialVersionUID = -2499859704676288591L;

	protected JViewport scrollbar_container;

	public direct_frame ()
	{
		super ();
		init ();
		setBorder (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED));
	}

	public direct_frame (Border f_border)
	{
		super ();
		init ();
		setBorder (f_border);
	}

	public direct_frame (Component f_component)
	{
		super (f_component);
		init ();
		setBorder (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED));
	}

	public direct_frame (Component f_component,Border f_border)
	{
		super (f_component);
		init ();
		setBorder (f_border);
	}

	public direct_frame (int f_scrollbar_y,int f_scrollbar_x)
	{
		super (f_scrollbar_y,f_scrollbar_x);
		init ();
		setBorder (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED));
	}

	public direct_frame (int f_scrollbar_y,int f_scrollbar_x,Border f_border)
	{
		super (f_scrollbar_y,f_scrollbar_x);
		init ();
		setBorder (f_border);
	}

	public direct_frame (Component f_component,int f_scrollbar_y,int f_scrollbar_x)
	{
		super (f_component,f_scrollbar_y,f_scrollbar_x);
		init ();
		setBorder (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED));
	}

	public direct_frame (Component f_component,int f_scrollbar_y,int f_scrollbar_x,Border f_border)
	{
		super (f_component,f_scrollbar_y,f_scrollbar_x);
		init ();
		setBorder (f_border);
	}

	public Component add (Component f_component) { return scrollbar_container.add (f_component); }
	public Component add (Component f_component,int f_index) { return scrollbar_container.add (f_component,f_index); }
	public void remove (Component f_component) { scrollbar_container.remove (f_component); }
	public void remove (int f_index) { scrollbar_container.remove (f_index); }

	protected void init ()
	{
		scrollbar_container = getViewport ();

		if (getHorizontalScrollBarPolicy () == JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) { scrollbar_container.setLayout (new BoxLayout (scrollbar_container,BoxLayout.Y_AXIS)); }
		else if (getVerticalScrollBarPolicy () == JScrollPane.VERTICAL_SCROLLBAR_NEVER) { scrollbar_container.setLayout (new FlowLayout (FlowLayout.LEADING)); }
	}
}

//j// EOF