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
* de.direct_netware.signed.classes.gui.window.direct_resizePack
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
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

public class direct_resizePack
{
	public static final int MODE_DYNAMIC = 0;
	public static final int MODE_FORCED = 1;
	public static final int MODE_PREFERRED = 2;
	public static final int MODE_UNDEFINED = 3;

	public static final int TYPE_WINDOW_BORDERLESS = 0;
	public static final int TYPE_WINDOW_DEFAULT = 1;
	public static final int TYPE_WINDOW_UNDEFINED = 2;

	protected int height;
	protected int height_additional;
	protected int mode = MODE_UNDEFINED;
	protected direct_window window;
	protected direct_window_borderless window_borderless;
	protected int window_type = TYPE_WINDOW_UNDEFINED;
	protected int width;
	protected int width_additional;

	public direct_resizePack (direct_window f_window) { this (f_window,false); }

	public direct_resizePack (direct_window f_window,boolean f_forced)
	{
		if (f_forced) { mode = MODE_FORCED; }
		else { mode = MODE_PREFERRED; }

		window = f_window;
		window_type = TYPE_WINDOW_DEFAULT;
		exec ();
	}

	public direct_resizePack (direct_window_borderless f_window) { this (f_window,false); }

	public direct_resizePack (direct_window_borderless f_window,boolean f_forced)
	{
		if (f_forced) { mode = MODE_FORCED; }
		else { mode = MODE_PREFERRED; }

		window_borderless = f_window;
		window_type = TYPE_WINDOW_BORDERLESS;
		exec ();
	}

	public direct_resizePack (direct_window f_window,int f_width,int f_height,int f_width_additional,int f_height_additional) { this (f_window,f_width,f_height,f_width_additional,f_height_additional,false); }

	public direct_resizePack (direct_window f_window,int f_width,int f_height,int f_width_additional,int f_height_additional,boolean f_forced)
	{
		if (f_forced) { mode = MODE_FORCED; }
		else { mode = MODE_DYNAMIC; }

		window = f_window;
		window_type = TYPE_WINDOW_DEFAULT;

		width = f_width;
		height = f_height;
		width_additional = f_width_additional;
		height_additional = f_height_additional;
		exec ();
	}

	public direct_resizePack (direct_window_borderless f_window,int f_width,int f_height,int f_width_additional,int f_height_additional) { this (f_window,f_width,f_height,f_width_additional,f_height_additional,false); }

	public direct_resizePack (direct_window_borderless f_window,int f_width,int f_height,int f_width_additional,int f_height_additional,boolean f_forced)
	{
		if (f_forced) { mode = MODE_FORCED; }
		else { mode = MODE_DYNAMIC; }

		window_borderless = f_window;
		window_type = TYPE_WINDOW_BORDERLESS;

		width = f_width;
		height = f_height;
		width_additional = f_width_additional;
		height_additional = f_height_additional;
		exec ();
	}

	public void exec ()
	{
		Container f_container = null;

		if (window_type == TYPE_WINDOW_BORDERLESS) { f_container = window_borderless.getContentPane (); }
		else if (window_type == TYPE_WINDOW_DEFAULT) { f_container = window.getContentPane (); }

		if (f_container != null)
		{
			int f_width_max = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
			if (f_width_max < width) { width = f_width_max; }

			int f_height_max = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
			if (f_height_max < height) { height = f_height_max; }

			if (window_type == TYPE_WINDOW_BORDERLESS) { window_borderless.pack (); }
			else if (window_type == TYPE_WINDOW_DEFAULT) { window.pack (); }

			Dimension f_preferred_size = f_container.getPreferredSize ();

			if (mode == MODE_PREFERRED)
			{
				width = f_preferred_size.width;
				height = f_preferred_size.height;
			}
			else
			{
				if (((f_preferred_size.width + width_additional) < width)&&(width < f_width_max)&&(mode == MODE_DYNAMIC)) { width = (f_preferred_size.width + width_additional); }
				else { width += width_additional; }

				if (((f_preferred_size.height + height_additional) < height)&&(height < f_height_max)&&(mode == MODE_DYNAMIC)) { height = (f_preferred_size.height + height_additional); }
				else { height += height_additional; }
			}

			f_container.setSize (width,height);

			if (window_type == TYPE_WINDOW_BORDERLESS)
			{
				if ((width == f_width_max)&&(height == f_height_max)) { window_borderless.setExtendedState (direct_window_borderless.MAXIMIZED_BOTH); }
				else if (width == f_width_max) { window_borderless.setExtendedState (direct_window_borderless.MAXIMIZED_HORIZ); }
				else if (height == f_height_max) { window_borderless.setExtendedState (direct_window_borderless.MAXIMIZED_VERT); }
			}
			else if (window_type == TYPE_WINDOW_DEFAULT)
			{
				if ((width == f_width_max)&&(height == f_height_max)) { window.setExtendedState (direct_window.MAXIMIZED_BOTH); }
				else if (width == f_width_max) { window.setExtendedState (direct_window.MAXIMIZED_HORIZ); }
				else if (height == f_height_max) { window.setExtendedState (direct_window.MAXIMIZED_VERT); }
			}
		}
	}
}

//j// EOF