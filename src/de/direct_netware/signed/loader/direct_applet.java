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
* "direct_applet" is the applet interface for JAS enabled applications. The
* main purpose of this applet loader is to do some tests and load objects
* on an on-demand base. Furthermore it filters unsupported versions of the
* Java API. It also provides the interface to (X)HTML JavaScript (if
* provided by the application specific directAppletLoader).
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

package de.direct_netware.signed.loader;
import de.direct_netware.signed.classes.direct_js;
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.classes.gui.direct_window;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.loader.direct_applet_ui;
import de.direct_netware.signed.ui.loader.direct_applet_window;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import netscape.javascript.JSObject;

public class direct_applet extends Applet implements direct_main_window
{
	public static final long serialVersionUID = 6264548137275689065L;

	protected direct_window applet_window;
	protected direct_container container;
	protected boolean compatible;
	protected String error_last;
	protected direct_local local;
	protected boolean ready;

	public void init ()
	{
		String f_request_callback = import_request ("jsCall","[\r|\n]",null);
		Object[] f_request_callback_params = { import_request ("jsID","[\r|\n]",null) };

		if ((f_request_callback != null)&&(f_request_callback_params[0] != null))
		{
			JSObject f_js_object = JSObject.getWindow (this);
			f_js_object.call (f_request_callback,f_request_callback_params);
		}

		compatible = false;
		container = new direct_container (this);
		error_last = "";
		local = new direct_local ();
		ready = false;

		setBackground (new Color (127,127,127,127));

		try { direct_dispatcher.execute (new direct_applet_ui (this,direct_applet_ui.UIMODE_INIT)); }
		catch (Throwable f_handled_exception) { throw new RuntimeException (); }
	}

	public String[][] getParameterInfo ()
	{
String[][] f_return = {
 {"archive","URL","Absolute URL to the JAR file"},
 {"jsCall","URL","JavaScript function to call when initialized"},
 {"jsID","URL","ID to use for the JavaScript call"}
};

		return f_return;
	}

	public synchronized String compatibility ()
	{
		if (compatible) { return "1"; }
		else { return "0"; }
	}

	public synchronized String import_request (String f_key,String f_filter,String f_prevalue)
	{
		String f_return = null;

		if (f_key.length () > 0)
		{
			f_return = getParameter (f_key);
			if (f_return == null) { f_return = System.getProperty (f_key); }

			if (f_return == null) { f_return = f_prevalue; }
			else { f_return = f_return.replaceAll (f_filter,""); }
		}

		return f_return;
	}

	public String js (String f_method) { return js (f_method,"",""); }
	public String js (String f_method,String f_value) { return js (f_method,"",f_value); }

	public synchronized String js (String f_method,String f_key,String f_value)
	{
		String f_return = "";

		try { f_return = (String)AccessController.doPrivileged (new direct_js (this,f_method,f_key,f_value)); }
		catch (Throwable f_handled_exception) { f_return = ""; }

		return f_return;
	}

	public synchronized direct_container get () { return container; }

	public synchronized URL[] get_download_classes_urls ()
	{
		String f_request_archive = import_request ("archive","[\r|\n]","");
		URL f_url_absolute[];

		if ((f_request_archive == null)||(f_request_archive.length () == 0)) { f_url_absolute = new URL[] { getCodeBase () }; }
		else
		{
			try { f_url_absolute = new URL[] { (getCodeBase ()),(new URL (f_request_archive)) }; }
			catch (MalformedURLException f_handled_exception) { f_url_absolute = new URL[] { getCodeBase () }; }
		}

		return f_url_absolute;
	}

	public synchronized String get_error_last () { return error_last; }
	public synchronized direct_local get_local () { return local; }
	public synchronized Object get_main () { return this; }
	public Object get_parent () { return null; }
	public synchronized URL get_resource (String f_file_classpath) { return getClass().getResource ("/de/direct_netware/" + f_file_classpath); }

	public synchronized String ready ()
	{
		if (ready) { return "1"; }
		else { return "0"; }
	}

	public synchronized void separate_window (String f_title)
	{
		if (applet_window == null)
		{
			Container f_container_component = (Container)this.getComponent (0);
			direct_dispatcher.dispatch (new direct_applet_window (this,f_container_component,f_title));
		}
	}

	public synchronized void set (Component f_component) { container.set (f_component); }
	public synchronized void set_compatible (boolean f_result) { compatible = f_result; }
	public synchronized void set_error_last (String f_error) { error_last = f_error; }
	public synchronized void set_ready (boolean f_status) { ready = f_status; }
	public synchronized void set_window (direct_window f_window) { applet_window = f_window; }
}

//j// EOF