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
* Creates a download thread to load classes dynamically when needed.
* Switches to a given thread when finished or on error.
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

package de.direct_netware.signed.classes.thread;
import de.direct_netware.signed.classes.direct_downloader_class;
import de.direct_netware.signed.classes.direct_downloader_context_class;
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.loader.direct_main_window;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.thread.direct_thread;
import de.direct_netware.signed.ui.direct_downloader_ui;
import de.direct_netware.signed.ui.direct_error_ui;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import javax.swing.JProgressBar;

public class direct_urlloader extends Thread implements direct_loader
{
	protected ClassLoader classloader;
	protected direct_dispatcher dispatcher;
	protected String[] download_list;
	protected direct_container downloader_container;
	protected JProgressBar downloader_progressbar;
	protected double downloader_steps;
	protected direct_thread error_thread;
	protected direct_main_window main_window;
	protected direct_thread success_thread;
	protected boolean success_indeterminate;

	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread) { this (f_parent,f_container,f_download_list,f_success_thread,null,true); }
	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,boolean f_success_indeterminate) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate); }
	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,direct_thread f_error_thread) { this (f_parent,f_container,f_download_list,f_success_thread,f_error_thread,true); }

	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,direct_thread f_error_thread,boolean f_success_indeterminate)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		downloader_container = f_container;
		download_list = f_download_list;
		error_thread = f_error_thread;
		classloader = ClassLoader.getSystemClassLoader ();
		success_thread = f_success_thread;
		success_indeterminate = f_success_indeterminate;
		start ();
	}

	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,null,true,f_runtime_class); }
	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,boolean f_success_indeterminate,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate,f_runtime_class); }
	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,direct_thread f_error_thread,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,f_error_thread,true,f_runtime_class); }

	public direct_urlloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_thread f_success_thread,direct_thread f_error_thread,boolean f_success_indeterminate,Class f_runtime_class)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		downloader_container = f_container;
		download_list = f_download_list;
		error_thread = f_error_thread;
		classloader = f_runtime_class.getClassLoader ();
		success_thread = f_success_thread;
		success_indeterminate = f_success_indeterminate;
		start ();
	}

	public void finalize ()
	{
		dispatcher = null;

		try { super.finalize (); }
		catch (Throwable f_unhandled_exception) { }
	}

	public void run ()
	{
		downloader_steps = download_list.length;
		boolean f_success = false;
		URL[] f_url_absolute = main_window.get_download_classes_urls ();
		Throwable f_exception = null;

		if (downloader_steps > 0)
		{
			if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_container,downloader_steps)); }

			try
			{
				for (int f_i = 0;f_i < downloader_steps;f_i++)
				{
					if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,f_i)); }
					AccessController.doPrivileged (new direct_downloader_class (f_url_absolute,download_list[f_i],classloader));
				}

				if (downloader_container != null)
				{
					direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_steps));
					f_success = true;
				}
			}
			catch (PrivilegedActionException f_handled_privileged_exception)
			{
				f_exception = f_handled_privileged_exception;
				f_success = false;
			}
			catch (SecurityException f_handled_security_exception)
			{
				try
				{
					direct_downloader_context_class f_class_downloader = new direct_downloader_context_class (classloader);
					f_exception = f_handled_security_exception;

					for (int f_i = 0;f_i < downloader_steps;f_i++)
					{
						if (f_success)
						{
							if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,f_i)); }

							if (download_list[f_i].indexOf (".jar:") > -1) { f_success = false; }
							else { f_success = f_class_downloader.download_class (download_list[f_i]); }
						}
					}

					if (downloader_container != null)
					{
						direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_steps));
						f_success = true;
					}
				}
				catch (Throwable f_handled_exception) { f_exception = f_handled_exception; }
			}
		}

		if (((success_indeterminate)||(!f_success))&&(downloader_container != null)) { direct_dispatcher.dispatch (new direct_downloader_ui (this)); }

		if (f_success) { direct_dispatcher.dispatch (success_thread); }
		else if (error_thread != null) { direct_dispatcher.dispatch (error_thread); }
		else if (downloader_container != null)
		{
			if (f_exception == null)
			{
				direct_local f_local = main_window.get_local ();
				direct_dispatcher.dispatch (new direct_error_ui (main_window,"core_error_fatal",(f_local.local_get ("errors_core_unknown_error"))));
			}
			else { direct_dispatcher.dispatch (new direct_error_ui (main_window,"core_error_fatal",f_exception)); }
		}
	}

	public synchronized direct_container get_downloader_container () { return downloader_container; }
	public synchronized JProgressBar get_downloader_progressbar () { return downloader_progressbar; }
	public synchronized double get_downloader_steps () { return downloader_steps; }
	public synchronized Object get_main () { return main_window.get_main (); }
	public synchronized Object get_parent () { return main_window; }
	public synchronized void set_dispatcher (direct_dispatcher f_dispatcher) { dispatcher = f_dispatcher; }
	public synchronized void set_downloader_progressbar (JProgressBar f_progressbar) { downloader_progressbar = f_progressbar; }

	protected void set_result (Object f_result)
	{
		if (dispatcher != null) { dispatcher.set_result (f_result); }
	}
}

//j// EOF