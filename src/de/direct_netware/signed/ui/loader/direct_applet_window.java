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
* de.direct_netware.signed.ui.loader.direct_applet_window
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
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.classes.gui.direct_container_gridbag;
import de.direct_netware.signed.classes.gui.direct_text;
import de.direct_netware.signed.classes.gui.direct_window;
import de.direct_netware.signed.loader.direct_applet;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.direct_error_ui;
import de.direct_netware.signed.ui.direct_swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.UIManager;

public class direct_applet_window extends direct_swing implements MouseListener,WindowListener
{
	protected direct_applet applet;
	protected Container container;
	protected String title;
	protected direct_window window;

	public direct_applet_window (Object f_parent,Container f_container,String f_title)
	{
		if (f_parent instanceof direct_applet) { applet = (direct_applet)f_parent; }
		container = f_container;
		title = f_title;
	}

	public void mouseClicked (MouseEvent f_event) { window.dispose (); }
	public void mouseEntered (MouseEvent f_event) { }
	public void mouseExited (MouseEvent f_event) { }
	public void mousePressed (MouseEvent f_event) { }
	public void mouseReleased (MouseEvent f_event) { }

	public void run ()
	{
		if (applet instanceof direct_applet)
		{
			try { init (); }
			catch (Throwable f_handled_exception)
			{
				direct_dispatcher.dispatch (new direct_error_ui (applet,"core_error_fatal",f_handled_exception));
				applet.set_error_last (f_handled_exception.toString ());
			}
		}
		else { throw new RuntimeException ("Not instance of de.direct_netware.signed.loader.direct_applet"); }
	}

	public void windowActivated (WindowEvent f_event) { }

	public void windowClosed (WindowEvent f_event)
	{
		applet.removeAll ();
		applet.setBackground (new Color (127,127,127,127));

		direct_container f_container = applet.get ();
		f_container.set (container);

		applet.set_window (null);
	}

	public void windowClosing (WindowEvent f_event) { }
	public void windowDeactivated (WindowEvent f_event) { }
	public void windowDeiconified (WindowEvent f_event) { }
	public void windowIconified (WindowEvent f_event) { }
	public void windowOpened (WindowEvent f_event) { }

	protected direct_applet get_applet () { return applet; }
	protected Container get_container () { return container; }
	public Object get_main () { return applet.get_main (); }
	public synchronized Object get_parent () { return applet; }

	protected void init ()
	{
		direct_local f_local = applet.get_local ();

		applet.removeAll ();

		window = new direct_window (title);
		window.addWindowListener (this);

/* -------------------------------------------------------------------------
Move the applet content container to a separate window
------------------------------------------------------------------------- */

		int f_width = container.getWidth ();
		int f_height = container.getHeight ();

		if ((f_width > 0)&&(f_height > 0)) { window.setResize (container,f_width,f_height); }
		else { window.set (container,true); }

		window.requestFocus ();

/* -------------------------------------------------------------------------
Add the "undo separation" button to the applet
------------------------------------------------------------------------- */

		direct_container_gridbag f_undo_area = new direct_container_gridbag (1,direct_container_gridbag.NONE);
		f_undo_area.addMouseListener (this);

		direct_text f_merge_onclick = new direct_text (f_local.local_get ("core_separate_merge"));
		f_merge_onclick.setBackground (UIManager.getColor ("Button.background"));
		f_merge_onclick.setFont (UIManager.getFont ("Button.font"));
		f_merge_onclick.setForeground (UIManager.getColor ("Button.foreground"));

		f_undo_area.setBackground (UIManager.getColor ("Button.background"));
		f_undo_area.add (f_merge_onclick,0,1,0,1);

		applet.setBackground (UIManager.getColor ("Button.background"));

		direct_container f_container = applet.get ();
		f_container.set (f_undo_area);

		applet.set_window (window);
		set_result (window);
	}
}

//j// EOF