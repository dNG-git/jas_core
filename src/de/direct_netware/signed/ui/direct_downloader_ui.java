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
* de.direct_netware.signed.ui.direct_loader_ui
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

package de.direct_netware.signed.ui;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;

import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.classes.gui.direct_container_gridbag;
import de.direct_netware.signed.classes.gui.direct_window_borderless;
import de.direct_netware.signed.classes.thread.direct_loader;
import de.direct_netware.signed.loader.direct_main_window;
import de.direct_netware.signed.thread.direct_dispatcher;

import javax.swing.JProgressBar;

public class direct_downloader_ui extends direct_swing
{
	public static final int UIMODE_DETERMINABLE = 0;
	public static final int UIMODE_INDETERMINABLE = 1;
	public static final int UIMODE_UNDEFINED = 2;
	public static final int UIMODE_UPDATE = 3;

	protected direct_main_window main_window;
	protected direct_container container;
	protected direct_container_gridbag container_gridbag;
	protected direct_loader downloader;
	protected direct_window_borderless downloader_window;
	protected int mode = UIMODE_UNDEFINED;
	protected double step;
	protected double steps;

	public direct_downloader_ui (Object f_parent)
	{
		if (f_parent instanceof direct_loader)
		{
			downloader = (direct_loader)f_parent;
			main_window = (direct_main_window)downloader.get_main ();
			container = downloader.get_downloader_container ();
		}

		mode = UIMODE_INDETERMINABLE;
	}

	public direct_downloader_ui (Object f_parent,direct_container f_container,double f_steps)
	{
		if (f_parent instanceof direct_loader)
		{
			downloader = (direct_loader)f_parent;
			main_window = (direct_main_window)downloader.get_main ();
		}

		container = f_container;
		steps = f_steps;
		mode = UIMODE_DETERMINABLE;
	}

	public direct_downloader_ui (Object f_parent,direct_container_gridbag f_container_gridbag,double f_steps)
	{
		if (f_parent instanceof direct_loader)
		{
			downloader = (direct_loader)f_parent;
			main_window = (direct_main_window)downloader.get_main ();
			container_gridbag = f_container_gridbag;
		}

		steps = f_steps;
		mode = UIMODE_DETERMINABLE;
	}

	public direct_downloader_ui (Object f_parent,double f_step)
	{
		if (f_parent instanceof direct_object)
		{
			downloader = (direct_loader)f_parent;
			main_window = (direct_main_window)downloader.get_main ();
		}

		step = f_step;
		mode = UIMODE_UPDATE;
	}

	public void run ()
	{
		if (main_window instanceof direct_main_window)
		{
			try
			{
				if ((container == null)&&(container_gridbag != null))
				{
					container_gridbag.removeAll ();

					container = new direct_container ();
					container.setBackground (new Color (127,127,127,127));
					container.setLayout (new GridLayout (1,1));

					container_gridbag.add (container,0,1,0,1);
				}

				switch (mode)
				{
				case UIMODE_DETERMINABLE:
				{
					determinable ();
					break;
				}
				case UIMODE_INDETERMINABLE:
				{
					indeterminable ();
					break;
				}
				case UIMODE_UPDATE:
				{
					update ();
					break;
				}
				}
			}
			catch (Throwable f_handled_exception)
			{
				direct_dispatcher.dispatch (new direct_error_ui (main_window,"core_error_fatal",f_handled_exception));
				main_window.set_error_last (f_handled_exception.toString ());
			}
		}
		else { throw new RuntimeException ("Not instance of de.direct_netware.signed.loader.direct_main_window"); }
	}

	protected void determinable ()
	{
		JProgressBar f_progressbar = new JProgressBar ();
		f_progressbar.setIndeterminate (false);
		f_progressbar.setValue (0);
		f_progressbar.setMaximum (1000);

		direct_container_gridbag f_container_waiting = new direct_container_gridbag (1,direct_container_gridbag.HORIZONTAL,(new Insets (3,3,3,3)));
		f_container_waiting.add (f_progressbar,0,1,0,1);

		downloader.set_downloader_progressbar (f_progressbar);
		container.set (f_container_waiting);
	}

	public Object get_main () { return main_window.get_main (); }
	public Object get_parent () { return main_window; }

	protected void indeterminable ()
	{
		JProgressBar f_progressbar = new JProgressBar ();
		f_progressbar.setIndeterminate (true);

		direct_container_gridbag f_container_waiting = new direct_container_gridbag (1,direct_container_gridbag.HORIZONTAL,(new Insets (3,3,3,3)));
		f_container_waiting.add (f_progressbar,0,1,0,1);

		downloader.set_downloader_progressbar (f_progressbar);
		container.set (f_container_waiting);
	}

	protected void update ()
	{
		JProgressBar f_progressbar = downloader.get_downloader_progressbar ();
		if (f_progressbar != null) { f_progressbar.setValue ((int)Math.ceil ((step / (downloader.get_downloader_steps ())) * 1000)); }
	}
}

//j// EOF