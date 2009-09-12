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
* de.direct_netware.signed.ui.direct_webpage_internal
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
import de.direct_netware.signed.classes.gui.direct_input;
import de.direct_netware.signed.classes.gui.direct_text;
import de.direct_netware.signed.classes.thread.direct_urlloader;
import de.direct_netware.signed.loader.direct_main_window;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.action.direct_webpage_internal_action;
import de.direct_netware.signed.ui.direct_swing;
import de.direct_netware.signed.ui.mouse.direct_webpage_internal_mouse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;

public class direct_webpage_internal extends direct_swing implements ComponentListener
{
	public static final int UIMODE_INIT = 0;
	public static final int UIMODE_FINALIZE = 1;
	public static final int UIMODE_PAGE_HIDE = 2;
	public static final int UIMODE_PAGE_SHOW = 3;
	public static final int UIMODE_PAGE_SHOW_INIT = 4;
	public static final int UIMODE_UNDEFINED = 5;
	public static final int UIMODE_URL_SELECTALL = 6;

	protected direct_main_window main_window;
	protected direct_container button_line;
	protected direct_dialog dialog;
	protected int mode = UIMODE_UNDEFINED;
	protected direct_container_gridbag page_container;
	protected URL page_url;
	protected direct_container webpage_container;
	protected direct_input webpage_url_input;

	public direct_webpage_internal (Object f_parent,URL f_url)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		page_url = f_url;
		mode = UIMODE_INIT;
	}

	public direct_webpage_internal (Object f_parent,int f_mode)
	{
		if (f_parent instanceof direct_webpage_internal)
		{
			direct_webpage_internal f_webpage_internal = (direct_webpage_internal)f_parent;

			button_line = f_webpage_internal.get_button_line ();
			page_container = f_webpage_internal.get_page_container ();
			dialog = f_webpage_internal.get_dialog ();
			main_window = f_webpage_internal.get_main_window ();
			page_url = f_webpage_internal.get_page_url ();
		}

		mode = f_mode;
	}

	public direct_webpage_internal (Object f_parent,direct_container f_webpage_container)
	{
		if (f_parent instanceof direct_webpage_internal)
		{
			direct_webpage_internal f_webpage_internal = (direct_webpage_internal)f_parent;

			button_line = f_webpage_internal.get_button_line ();
			page_container = f_webpage_internal.get_page_container ();
			dialog = f_webpage_internal.get_dialog ();
			main_window = f_webpage_internal.get_main_window ();
			page_url = f_webpage_internal.get_page_url ();
		}

		webpage_container = f_webpage_container;
		mode = UIMODE_PAGE_SHOW;
	}

	public direct_webpage_internal (Object f_parent,direct_input f_webpage_url_input)
	{
		if (f_parent instanceof direct_webpage_internal)
		{
			direct_webpage_internal f_webpage_internal = (direct_webpage_internal)f_parent;

			main_window = f_webpage_internal.get_main_window ();
			webpage_url_input = f_webpage_url_input;
		}

		mode = UIMODE_URL_SELECTALL;
	}

	public void componentHidden (ComponentEvent f_event) { }
	public void componentMoved (ComponentEvent f_event) { }
	public void componentShown (ComponentEvent f_event) { }

	public void componentResized (ComponentEvent f_event)
	{
		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);

		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
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
				case UIMODE_FINALIZE:
				{
					finalize ();
					break;
				}
				case UIMODE_PAGE_HIDE:
				{
					webpage_hide ();
					break;
				}
				case UIMODE_URL_SELECTALL:
				{
					url_selectall ();
					break;
				}
				case UIMODE_PAGE_SHOW:
				{
					webpage_show ();
					break;
				}
				case UIMODE_PAGE_SHOW_INIT:
				{
					webpage_show_init ();
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

	public void finalize () { dialog.finalize (); }
	protected direct_main_window get_main_window () { return main_window; }
	protected direct_container get_button_line () { return button_line; }
	protected direct_dialog get_dialog () { return dialog; }
	protected direct_container_gridbag get_page_container () { return page_container; }
	protected URL get_page_url () { return page_url; }
	public Object get_main () { return main_window.get_main (); }
	public synchronized Object get_parent () { return main_window; }

	protected void init ()
	{
		direct_local f_local = main_window.get_local ();
		String f_url = page_url.toExternalForm ();

		String f_url_title = f_url;
		if (f_url_title.length () > 40) { f_url_title = (f_url_title.subSequence (0,36) + " ..."); }
		dialog = new direct_dialog (f_url_title,false,true);

/* -------------------------------------------------------------------------
Prepare a new container
------------------------------------------------------------------------- */

		Container f_field_container = dialog.get_container_center ();
		f_field_container.setLayout (new BorderLayout ());

/* -------------------------------------------------------------------------
Set up left error image
------------------------------------------------------------------------- */

		Icon f_warning_icon_object = UIManager.getIcon ("OptionPane.warningIcon");
		direct_panel f_warning_icon = new direct_panel (f_warning_icon_object);
		dialog.set_container_left (f_warning_icon);

/* -------------------------------------------------------------------------
Set up the details container and add the default error message
------------------------------------------------------------------------- */

		page_container = new direct_container_gridbag (1,direct_container_gridbag.BOTH,(new Insets (3,3,3,3)));
		dialog.set_container_center (page_container);

		direct_text f_warning_text = new direct_text (f_local.local_get ("core_webbrowser_unsupported"));
		page_container.add (f_warning_text,0,1,1,0,0,1);

		webpage_url_input = new direct_input (f_url);
		webpage_url_input.setEditable (false);
		webpage_url_input.addMouseListener (new direct_webpage_internal_mouse (this,webpage_url_input));
		page_container.add (webpage_url_input,0,1,1,1,0,1);

/* -------------------------------------------------------------------------
Set up the buttons line
------------------------------------------------------------------------- */

		button_line = new direct_container ();
		button_line.setLayout (new FlowLayout (FlowLayout.CENTER));

		direct_button f_button_dialog_details = new direct_button (f_local.local_get ("core_webbrowser_page_show"));
		f_button_dialog_details.addActionListener (new direct_webpage_internal_action (this,"direct_webpage_internal_button_webpage_show"));

		button_line.add (f_button_dialog_details,0);

		direct_button f_button_close = new direct_button (f_local.local_get ("core_close"));
		f_button_close.addActionListener (new direct_webpage_internal_action (this,"direct_webpage_internal_button_close"));

		button_line.add (f_button_close,1);
		dialog.set_container_bottom (button_line);

		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);

		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
		dialog.requestFocus ();

		set_result (dialog);
	}

	protected void webpage_hide ()
	{
		direct_local f_local = main_window.get_local ();

		direct_button f_button_dialog_details = new direct_button (f_local.local_get ("core_webbrowser_page_show"));
		f_button_dialog_details.addActionListener (new direct_webpage_internal_action (this,"direct_webpage_internal_button_webpage_show"));

		button_line.remove (0);
		button_line.add (f_button_dialog_details,0);

		page_container.remove (3);
		page_container.remove (2);

		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);
		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
	}

	protected void webpage_show ()
	{
		try
		{
			dialog.resizePack (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);

			JEditorPane f_webpage = new JEditorPane (page_url);
			f_webpage.setEditable (false);
			webpage_container.set (f_webpage);
		}
		catch (IOException f_handled_exception)
		{
			direct_local f_local = main_window.get_local ();
			direct_dispatcher.dispatch (new direct_error_ui (main_window,"core_error_standard",(f_local.local_get ("errors_core_webpage_invalid")),f_handled_exception));
			webpage_hide ();
		}
	}

	protected void webpage_show_init ()
	{
		direct_local f_local = main_window.get_local ();

		direct_button f_button_dialog_details = new direct_button (f_local.local_get ("core_webbrowser_page_hide"));
		f_button_dialog_details.addActionListener (new direct_webpage_internal_action (this,"direct_webpage_internal_button_webpage_hide"));

		button_line.remove (0);
		button_line.add (f_button_dialog_details,0);

		page_container.add (new JSeparator (JSeparator.HORIZONTAL),0,1,1,2,0,1,2);

		direct_frame f_webpage_scrollbar_container = new direct_frame (direct_frame.VERTICAL_SCROLLBAR_AS_NEEDED,direct_frame.HORIZONTAL_SCROLLBAR_AS_NEEDED,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED)));

		webpage_container = new direct_container ();

		f_webpage_scrollbar_container.add (webpage_container);
		page_container.add (f_webpage_scrollbar_container,0,1,1,3,1,1,3);

String f_download_list[] = {
 "de.direct_netware.classes.direct_webpage_preparser"
};

		new direct_urlloader (this,webpage_container,f_download_list,(new direct_webpage_internal (this,webpage_container)),(new direct_webpage_internal (main_window,direct_webpage_internal.UIMODE_PAGE_HIDE)));

		int f_width = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 2);
		int f_height = (int)Math.ceil (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 1.15);
		int f_scrollbar_size = (UIManager.getInt ("ScrollBar.width") * 2);
		dialog.resizePack (f_width,f_height,f_scrollbar_size,f_scrollbar_size);
	}

	protected void url_selectall () { webpage_url_input.selectAll (); }
}

//j// EOF