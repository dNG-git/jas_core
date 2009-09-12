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
* de.direct_netware.signed.classes.gui.window.direct_set
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

package de.direct_netware.signed.classes.gui.window;
import de.direct_netware.signed.classes.gui.direct_window;
import de.direct_netware.signed.classes.gui.direct_window_borderless;

import java.awt.Container;
import java.awt.GridLayout;

public class direct_set extends Thread
{
	public static final int TYPE_WINDOW_BORDERLESS = 0;
	public static final int TYPE_WINDOW_DEFAULT = 1;
	public static final int TYPE_WINDOW_UNDEFINED = 2;

	protected Container container;
	protected boolean pack;
	protected direct_window window;
	protected direct_window_borderless window_borderless;
	protected int window_type = TYPE_WINDOW_UNDEFINED;

	public direct_set (direct_window f_window,Container f_container,boolean f_pack)
	{
		window = f_window;
		window_type = TYPE_WINDOW_DEFAULT;

		container = f_container;
		pack = f_pack;
		exec ();
	}

	public direct_set (direct_window_borderless f_window,Container f_container,boolean f_pack)
	{
		window_borderless = f_window;
		window_type = TYPE_WINDOW_BORDERLESS;

		container = f_container;
		pack = f_pack;
		exec ();
	}

	public void exec ()
	{
		Container f_container = null;

		if (window_type == TYPE_WINDOW_BORDERLESS) { f_container = window_borderless.get (); }
		else if (window_type == TYPE_WINDOW_DEFAULT) { f_container = window.getContentPane (); }

		if (f_container != null)
		{
			f_container.setVisible (false);

			f_container.removeAll ();
			f_container.setLayout (new GridLayout (1,1));
			f_container.add (container);

			f_container.setVisible (true);

			if (pack)
			{
				if (window_type == TYPE_WINDOW_BORDERLESS) { window_borderless.resizePack (); }
				else if (window_type == TYPE_WINDOW_DEFAULT) { window.resizePack (); }
			}
		}
	}
}

//j// EOF