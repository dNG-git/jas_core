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
* "direct_dialog" implements a typical dialog window including top and
* bottom bars.
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

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JSeparator;

public class direct_dialog
{
	protected direct_window window;
	protected direct_container container_bottom;
	protected direct_container container_center;
	protected direct_container container_left;
	protected direct_container container_main;
	protected direct_container container_right;
	protected direct_container container_top;
	protected String title;

	public direct_dialog (boolean f_top,boolean f_bottom) { this ("Untitled",f_top,f_bottom); }

	public direct_dialog (String f_title,boolean f_top,boolean f_bottom)
	{
		title = f_title;
		init (f_top,f_bottom);
	}

	public void finalize () { window.dispose (); }

	protected void init (boolean f_top,boolean f_bottom)
	{
		window = new direct_window (title);

/* -------------------------------------------------------------------------
Prepare the outer container (top / bottom)
------------------------------------------------------------------------- */

		container_main = new direct_container ();
		container_main.setLayout (new BorderLayout ());

		if (f_top)
		{
			Container f_container_top = new Container ();
			f_container_top.setLayout (new BoxLayout (f_container_top,BoxLayout.Y_AXIS));

			container_top = new direct_container ();
			f_container_top.add (container_top);
			f_container_top.add (new JSeparator ());

			container_main.add (f_container_top,BorderLayout.NORTH);
		}

		container_center = new direct_container ();
		container_main.add (container_center,BorderLayout.CENTER);

		if (f_bottom)
		{
			Container f_container_bottom = new Container ();
			f_container_bottom.setLayout (new BoxLayout (f_container_bottom,BoxLayout.Y_AXIS));

			container_bottom = new direct_container ();
			f_container_bottom.add (new JSeparator ());
			f_container_bottom.add (container_bottom);

			container_main.add (f_container_bottom,BorderLayout.SOUTH);
		}

		window.set (container_main);
	}

	public direct_container get_container_bottom () { return container_bottom; }
	public direct_container get_container_center () { return container_center; }

	public direct_container get_container_left ()
	{
		if (container_left == null)
		{
			container_left = new direct_container ();
			container_main.add (container_left,BorderLayout.WEST);
		}

		return container_left;
	}

	public direct_container get_container_right ()
	{
		if (container_right == null)
		{
			container_right = new direct_container ();
			container_main.add (container_right,BorderLayout.SOUTH);
		}

		return container_right;
	}

	public direct_container get_container_top () { return container_top; }
	public direct_window get_window () { return window; }

	public void set_container_bottom (Component f_component)
	{
		if (container_bottom != null) { container_bottom.set (f_component); }
	}

	public void set_container_center (Component f_component) { container_center.set (f_component); }

	public void set_container_left (Component f_component)
	{
		if (container_left == null)
		{
			container_left = new direct_container ();
			container_main.add (container_left,BorderLayout.WEST);
		}

		container_left.set (f_component);
	}

	public void set_container_right (Component f_component)
	{
		if (container_right == null)
		{
			container_right = new direct_container ();
			container_main.add (container_right,BorderLayout.SOUTH);
		}

		container_right.set (f_component);
	}

	public void set_container_top (Component f_component)
	{
		if (container_top != null) { container_top.set (f_component); }
	}

	public void requestFocus () { window.requestFocus (); }
	public void resizePack () { window.resizePack (); }
	public void resizePack (int f_width,int f_height) { window.resizePack (f_width,f_height); }
	public void resizePack (int f_width,int f_height,int f_width_additional,int f_height_additional) { window.resizePack (f_width,f_height,f_width_additional,f_height_additional); }
}

//j// EOF