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
* de.direct_netware.signed.ui.direct_error_ui
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
import de.direct_netware.signed.loader.direct_main_window;
import de.direct_netware.signed.ui.action.direct_error_action;

import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JSeparator;
import javax.swing.UIManager;

public class direct_error_ui extends direct_swing
{
	public static final int UIMODE_DETAILS_HIDE = 0;
	public static final int UIMODE_DETAILS_SHOW = 1;
	public static final int UIMODE_FINALIZE = 2;
	public static final int UIMODE_INIT = 3;
	public static final int UIMODE_UNDEFINED = 4;

	protected direct_container button_line;
	protected direct_container_gridbag details_container;
	protected direct_dialog dialog;
	protected String error_title;
	protected String error_text;
	protected Throwable error_exception;
	protected direct_main_window main_window;
	protected int mode = UIMODE_UNDEFINED;

	public direct_error_ui (Object f_parent,String f_error_title,String f_error_text)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		error_title = f_error_title;
		error_text = f_error_text;
		mode = UIMODE_INIT;
	}

	public direct_error_ui (Object f_parent,String f_error_title,Throwable f_error_exception)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		error_title = f_error_title;
		error_text = "errors_core_unknown_error";
		error_exception = f_error_exception;
		mode = UIMODE_INIT;
	}

	public direct_error_ui (Object f_parent,String f_error_title,String f_error_text,Throwable f_error_exception)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		error_title = f_error_title;
		error_text = f_error_text;
		error_exception = f_error_exception;
		mode = UIMODE_INIT;
	}

	public direct_error_ui (Object f_parent,int f_mode)
	{
		if (f_parent instanceof direct_error_ui)
		{
			direct_error_ui f_error_ui = (direct_error_ui)f_parent;

			button_line = f_error_ui.get_button_line ();
			details_container = f_error_ui.get_details_container ();
			dialog = f_error_ui.get_dialog ();
			error_title = f_error_ui.get_error_title ();
			error_text = f_error_ui.get_error_text ();
			error_exception = f_error_ui.get_error_exception ();
			main_window = f_error_ui.get_main_window ();
		}

		mode = f_mode;
	}

	public void run ()
	{
		if (main_window instanceof direct_main_window)
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
				case UIMODE_DETAILS_HIDE:
				{
					details (false);
					break;
				}
				case UIMODE_DETAILS_SHOW:
				{
					details (true);
					break;
				}
				case UIMODE_FINALIZE:
				{
					finalize ();
					break;
				}
				}
			}
			catch (Throwable f_handled_exception) { main_window.set_error_last (f_handled_exception.toString ()); }
		}
		else
		{
			if (error_exception != null) { error_exception.printStackTrace (); }
			throw new RuntimeException ("Not instance of de.direct_netware.signed.loader.direct_main_window");
		}
	}

	public void finalize () { dialog.finalize (); }
	protected direct_main_window get_main_window () { return main_window; }
	protected direct_container get_button_line () { return button_line; }
	protected direct_container_gridbag get_details_container () { return details_container; }
	protected direct_dialog get_dialog () { return dialog; }
	protected String get_error_title () { return error_title; }
	protected String get_error_text () { return error_text; }
	protected Throwable get_error_exception () { return error_exception; }
	public Object get_main () { return main_window.get_main (); }
	public Object get_parent () { return main_window; }

	protected void init ()
	{
		direct_local f_local = main_window.get_local ();
		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);

		dialog = new direct_dialog (f_local.local_get (error_title),false,true);

/* -------------------------------------------------------------------------
Set up left error image
------------------------------------------------------------------------- */

		Icon f_error_icon_object = UIManager.getIcon ("OptionPane.errorIcon");

		if (f_error_icon_object != null)
		{
			direct_panel f_error_icon = new direct_panel (f_error_icon_object);
			dialog.set_container_left (f_error_icon);
		}

/* -------------------------------------------------------------------------
Set up the details container and add the default error message
------------------------------------------------------------------------- */

		direct_frame f_scrollbar_container = new direct_frame (direct_frame.VERTICAL_SCROLLBAR_AS_NEEDED,direct_frame.HORIZONTAL_SCROLLBAR_NEVER,BorderFactory.createEmptyBorder ());
		direct_textblock f_error_text = new direct_textblock (error_text,f_width,0);

		details_container = new direct_container_gridbag (1,direct_container_gridbag.HORIZONTAL,(new Insets (3,3,3,3)));
		details_container.add (f_error_text,0,1,0,1);

		f_scrollbar_container.add (details_container);
		dialog.set_container_center (f_scrollbar_container);

/* -------------------------------------------------------------------------
Set up the buttons line
------------------------------------------------------------------------- */

		button_line = new direct_container ();
		button_line.setLayout (new FlowLayout (FlowLayout.CENTER));

		direct_button f_button_error_details = new direct_button (f_local.local_get ("core_detailed_information_show"));

		if (error_exception == null) { f_button_error_details.setEnabled (false); }
		else { f_button_error_details.addActionListener (new direct_error_action (this,"direct_error_button_error_details_show")); }

		button_line.add (f_button_error_details,0);

		direct_button f_button_close = new direct_button (f_local.local_get ("core_close"));
		f_button_close.addActionListener (new direct_error_action (this,"direct_error_button_close"));

		button_line.add (f_button_close,1);
		dialog.set_container_bottom (button_line);

		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);

		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
		dialog.requestFocus ();

		set_result (dialog);
	}

	protected void details (boolean f_show)
	{
		direct_local f_local = main_window.get_local ();
		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);

		if (f_show)
		{
			direct_button f_button_error_details = new direct_button (f_local.local_get ("core_detailed_information_hide"));
			f_button_error_details.addActionListener (new direct_error_action (this,"direct_error_button_error_details_hide"));
			button_line.remove (0);
			button_line.add (f_button_error_details,0);

String f_error_text_details = new String ("<span style='font-weight:bold'>" + (error_exception.getClass().getName ()) + ":</span><br><br>" +
(error_exception.getLocalizedMessage ()));

			StackTraceElement[] f_backtrace = error_exception.getStackTrace ();

			if (f_backtrace.length > 0)
			{
				f_error_text_details += "<br><br><span style='font-weight:bold'>Backtrace:</span><br>";

				for (int f_i = 0;f_i < f_backtrace.length;f_i++)
				{
					if (f_backtrace[f_i].isNativeMethod ()) { f_error_text_details += ("<br>&lt;&lt;Native&gt;&gt;-&gt;" + (f_backtrace[f_i].getMethodName ())); }
					else { f_error_text_details += ("<br>" + (f_backtrace[f_i].getFileName ()) + ": " + (f_backtrace[f_i].getClassName ()) + "-&gt;" + (f_backtrace[f_i].getMethodName ()) + " (" + (f_backtrace[f_i].getLineNumber ()) + ")"); }
				}
			}

			details_container.add (new JSeparator (JSeparator.HORIZONTAL),0,1,1,1,1);
			details_container.add (new direct_textblock (f_error_text_details,f_width,0),0,1,2,1,2);

			dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
		}
		else
		{
			direct_button f_button_error_details = new direct_button (f_local.local_get ("core_detailed_information_show"));
			f_button_error_details.addActionListener (new direct_error_action (this,"direct_error_button_error_details_show"));

			button_line.remove (0);
			button_line.add (f_button_error_details,0);

			details_container.remove (2);
			details_container.remove (1);

			dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
		}
	}
}

//j// EOF