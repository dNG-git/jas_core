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
* de.direct_netware.signed.classes.gui.window.direct_init
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
import de.direct_netware.signed.classes.gui.direct_container_gridbag;
import de.direct_netware.signed.classes.gui.direct_window;
import de.direct_netware.signed.classes.gui.direct_window_borderless;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class direct_init
{
	public static final int TYPE_WINDOW_BORDERLESS = 0;
	public static final int TYPE_WINDOW_DEFAULT = 1;
	public static final int TYPE_WINDOW_UNDEFINED = 2;

	protected boolean window_interactive;
	protected String title;
	protected direct_window window;
	protected direct_window_borderless window_borderless;
	protected Container window_borderless_container;
	protected int window_type = TYPE_WINDOW_UNDEFINED;

	public direct_init (direct_window f_window,String f_title,boolean f_interactive)
	{
		window = f_window;
		window_type = TYPE_WINDOW_DEFAULT;
		window_interactive = f_interactive;
		title = f_title;

		exec ();
	}

	public direct_init (direct_window_borderless f_window,Container f_container,String f_title,boolean f_interactive)
	{
		window_borderless = f_window;
		window_borderless_container = f_container;
		window_type = TYPE_WINDOW_BORDERLESS;
		window_interactive = f_interactive;
		title = f_title;

		exec ();
	}

	public void exec ()
	{
		Container f_container = null;

		if (window_type == TYPE_WINDOW_BORDERLESS)
		{
			if (window_interactive)
			{
				try { window_borderless.setLocationByPlatform (true); }
				catch (Throwable f_unhandled_exception) { }
			}
			else
			{
				window_borderless.setResizable (false);

				try { window_borderless.setLocationByPlatform (false); }
				catch (Throwable f_unhandled_exception) { }
			}

			f_container = window_borderless_container;

			JPanel f_window_box = new JPanel ();
			Border f_window_box_border = UIManager.getBorder ("PopupMenu.border");
			Color f_window_box_bgcolor;
			Color f_window_box_fgcolor;
			Font f_window_box_font;

			if (f_window_box_border == null)
			{
				f_window_box_border = UIManager.getBorder ("InternalFrame.border");
				f_window_box_bgcolor = UIManager.getColor ("Viewport.background");
				f_window_box_fgcolor = UIManager.getColor ("Viewport.foreground");
				f_window_box_font = UIManager.getFont ("Viewport.font");
			}
			else
			{
				f_window_box_bgcolor = UIManager.getColor ("PopupMenu.background");
				f_window_box_fgcolor = UIManager.getColor ("PopupMenu.foreground");
				f_window_box_font = UIManager.getFont ("PopupMenu.font");
			}

			f_window_box.setBackground (f_window_box_bgcolor);
			f_window_box.setBorder (f_window_box_border);
			f_window_box.setFont (f_window_box_font);
			f_window_box.setForeground (f_window_box_fgcolor);
			f_window_box.setLayout (new GridLayout (1,1));

			f_window_box.add (f_container);

			window_borderless.setContentPane (f_window_box);

			try { window_borderless.setAlwaysOnTop (true); }
			catch (Throwable f_unhandled_exception) { }

			window_borderless.setBackground (new Color (127,127,127,127));
			window_borderless.setDefaultCloseOperation (direct_window.DISPOSE_ON_CLOSE);
			window_borderless.setExtendedState (direct_window_borderless.NORMAL);
			window_borderless.setTitle (title);
		}
		else if (window_type == TYPE_WINDOW_DEFAULT)
		{
			if (window_interactive)
			{
				try { window.setLocationByPlatform (true); }
				catch (Throwable f_unhandled_exception) { }
			}
			else
			{
				window.setResizable (false);

				try { window.setLocationByPlatform (false); }
				catch (Throwable f_unhandled_exception) { }
			}

			window.setBackground (new Color (127,127,127,127));
			window.setDefaultCloseOperation (direct_window.DISPOSE_ON_CLOSE);
			window.setExtendedState (direct_window.NORMAL);
			window.setTitle (title);
		}

		JProgressBar f_progressbar = new JProgressBar ();
		f_progressbar.setIndeterminate (true);

		direct_container_gridbag f_container_waiting = new direct_container_gridbag (1,direct_container_gridbag.NONE);
		f_container_waiting.add (f_progressbar,0,1,0,1);

		if (window_type == TYPE_WINDOW_BORDERLESS)
		{
			window_borderless.set (f_container_waiting,true);
			window_borderless.resizePack ();
			window_borderless.setVisible (true);
		}
		else if (window_type == TYPE_WINDOW_DEFAULT)
		{
			window.set (f_container_waiting,true);
			window.resizePack ();
			window.setVisible (true);
		}
	}
}

//j// EOF