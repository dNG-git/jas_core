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
* de.direct_netware.signed.ui.direct_iclient_ui
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
import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_button;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.classes.gui.direct_container_gridbag;
import de.direct_netware.signed.classes.gui.direct_dialog;
import de.direct_netware.signed.classes.gui.direct_frame;
import de.direct_netware.signed.classes.gui.direct_panel;
import de.direct_netware.signed.classes.gui.direct_textblock;
import de.direct_netware.signed.loader.direct_applet;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.action.direct_iclient_action;

import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.UIManager;

public class direct_iclient_ui extends direct_swing
{
	public static final long serialVersionUID = -4982065456899139327L;

	public static final int UIMODE_INSTALL = 0;
	public static final int UIMODE_FINALIZE = 1;
	public static final int UIMODE_INIT = 2;
	public static final int UIMODE_UNDEFINED = 3;

	protected direct_applet applet;
	protected direct_container button_line;
	protected String class_name;
	protected direct_container_gridbag details_container;
	protected direct_dialog dialog;
	protected String java_version;
	protected int mode = UIMODE_UNDEFINED;

	public direct_iclient_ui (Object f_parent,String f_class_name,String f_java_version)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			applet = (direct_applet)f_object.get_main ();
		}

		class_name = f_class_name;
		java_version = f_java_version;
		mode = UIMODE_INIT;
	}

	public direct_iclient_ui (Object f_parent,int f_mode)
	{
		if (f_parent instanceof direct_iclient_ui)
		{
			direct_iclient_ui f_iclient_ui = (direct_iclient_ui)f_parent;

			applet = f_iclient_ui.get_applet ();
			button_line = f_iclient_ui.get_button_line ();
			class_name = f_iclient_ui.get_class_name ();
			details_container = f_iclient_ui.get_details_container ();
			dialog = f_iclient_ui.get_dialog ();
			java_version = f_iclient_ui.get_java_version ();
		}

		mode = f_mode;
	}

	public void run ()
	{
		if (applet instanceof direct_applet)
		{
			try
			{
				switch (mode)
				{
				case UIMODE_INIT:
				{
					init ();
					break;
				}
				case UIMODE_INSTALL:
				{
					install ();
					break;
				}
				case UIMODE_FINALIZE:
				{
					finalize ();
					break;
				}
				}
			}
			catch (Throwable f_handled_exception)
			{
				direct_dispatcher.dispatch (new direct_error_ui (applet,"core_error_fatal",f_handled_exception));
				applet.set_error_last (f_handled_exception.toString ());
			}
		}
		else { throw new RuntimeException ("Not instance of de.direct_netware.signed.loader.direct_applet"); }
	}

	public void finalize () { dialog.finalize (); }
	protected direct_applet get_applet () { return applet; }
	protected direct_container get_button_line () { return button_line; }
	protected String get_class_name () { return class_name; }
	protected direct_container_gridbag get_details_container () { return details_container; }
	protected direct_dialog get_dialog () { return dialog; }
	protected String get_java_version () { return java_version; }
	public Object get_main () { return applet.get_main (); }
	public Object get_parent () { return applet; }

	protected void init ()
	{
		direct_local f_local = applet.get_local ();
		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		boolean f_version_check = false;
		if (Double.parseDouble (System.getProperty ("java.specification.version")) >= (Double.parseDouble (java_version))) { f_version_check = true; }

		dialog = new direct_dialog (f_local.local_get ("core_iclient_required_title"),false,true);

/* -------------------------------------------------------------------------
Set up left warning image
------------------------------------------------------------------------- */

		if (f_version_check)
		{
			Icon f_warning_icon_object = UIManager.getIcon ("OptionPane.warningIcon");

			if (f_warning_icon_object != null)
			{
				direct_panel f_warning_icon = new direct_panel (f_warning_icon_object);
				dialog.set_container_left (f_warning_icon);
			}
		}
		else
		{
			Icon f_error_icon_object = UIManager.getIcon ("OptionPane.errorIcon");

			if (f_error_icon_object != null)
			{
				direct_panel f_error_icon = new direct_panel (f_error_icon_object);
				dialog.set_container_left (f_error_icon);
			}
		}

/* -------------------------------------------------------------------------
Set up the details container and add the default warning message
------------------------------------------------------------------------- */

		direct_frame f_iclient_block = new direct_frame (direct_frame.VERTICAL_SCROLLBAR_AS_NEEDED,direct_frame.HORIZONTAL_SCROLLBAR_NEVER,BorderFactory.createEmptyBorder ());
		f_iclient_block.add (new direct_textblock (f_local.local_get ("core_iclient_required_1") + class_name + (f_local.local_get ("core_iclient_required_2")) + java_version + (f_local.local_get ("core_iclient_required_3")),f_width,0));

		details_container = new direct_container_gridbag (1,direct_container_gridbag.HORIZONTAL,(new Insets (3,3,3,3)));
		details_container.add (f_iclient_block,0,1,0,1);
		dialog.set_container_center (details_container);

/* -------------------------------------------------------------------------
Set up the buttons line
------------------------------------------------------------------------- */

		button_line = new direct_container ();
		button_line.setLayout (new FlowLayout (FlowLayout.CENTER));

		Preferences f_preferences = Preferences.userNodeForPackage (de.direct_netware.signed.direct_object.class);
		String f_iclient_path = f_preferences.get ("de.direct-netware.iclient.codebase",null);

		if (f_iclient_path == null)
		{
			direct_button f_button_iclient_install = new direct_button (f_local.local_get ("core_iclient_installer_install"));

			if (f_version_check) { f_button_iclient_install.addActionListener (new direct_iclient_action (this,"direct_iclient_button_install")); }
			else { f_button_iclient_install.setEnabled (false); }

			button_line.add (f_button_iclient_install,0);
		}
		else
		{
			direct_button f_button_iclient_update = new direct_button (f_local.local_get ("core_iclient_installer_update"));

			if (f_version_check) { f_button_iclient_update.addActionListener (new direct_iclient_action (this,"direct_iclient_button_install")); }
			else { f_button_iclient_update.setEnabled (false); }

			button_line.add (f_button_iclient_update,0);
		}

		direct_button f_button_close = new direct_button (f_local.local_get ("core_close"));
		f_button_close.addActionListener (new direct_iclient_action (this,"direct_iclient_button_close"));

		button_line.add (f_button_close,1);
		dialog.set_container_bottom (button_line);

		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);

		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
		dialog.requestFocus ();

		set_result (dialog);
	}

	protected void install ()
	{
		try { applet.getAppletContext().showDocument (new URL ("http://www.direct-netware.de/redirect.php?iclient:jnlp"),"_blank"); }
		catch (MalformedURLException f_unhandled_exception) { }

		dialog.finalize ();
	}
}

//j// EOF