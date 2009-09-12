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
* de.direct_netware.signed.ui.loader.direct_applet_ui
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

package de.direct_netware.signed.ui.loader;
import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.loader.direct_applet;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.direct_error_ui;
import de.direct_netware.signed.ui.direct_swing;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.UIManager;

public class direct_applet_ui extends direct_swing
{
	public static final int UIMODE_INIT = 0;
	public static final int UIMODE_UNDEFINED = 1;

	protected direct_applet applet;
	protected int mode = UIMODE_UNDEFINED;

	public direct_applet_ui (Object f_parent,int f_mode)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			applet = (direct_applet)f_object.get_main ();
		}

		mode = f_mode;
	}

	public void run ()
	{
		if (applet instanceof direct_applet)
		{
			try
			{
				if (mode == UIMODE_INIT) { init (); }
			}
			catch (Throwable f_handled_exception)
			{
				direct_dispatcher.dispatch (new direct_error_ui (applet,"core_error_fatal",f_handled_exception));
				applet.set_error_last (f_handled_exception.toString ());
			}
		}
		else { throw new RuntimeException ("Not instance of de.direct_netware.signed.loader.direct_applet"); }
	}

	public Object get_main () { return applet.get_main (); }
	public synchronized Object get_parent () { return applet; }

	protected void init ()
	{
		boolean f_ui_init_success = true;

		try { UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ()); }
		catch (Throwable f_unhandled_exception) { f_ui_init_success = false; }

		if (!f_ui_init_success)
		{
			try
			{
				UIManager.setLookAndFeel (UIManager.getCrossPlatformLookAndFeelClassName ());
				f_ui_init_success = true;
			}
			catch (Throwable f_unhandled_exception) { }
		}

		try
		{
			Class.forName ("java.awt.GraphicsEnvironment");
			Class.forName ("java.awt.RenderingHints");
			Class.forName ("javax.swing.border.Border");

			applet.setBackground (new Color (127,127,127,127));
			applet.set_compatible (true);

			direct_container f_container = applet.get ();
			f_container.setLayout (new GridLayout (1,1));
			applet.set (f_container);
		}
		catch (Throwable f_unhandled_exception) { applet.set_error_last (f_unhandled_exception.toString ()); }
	}
}

//j// EOF