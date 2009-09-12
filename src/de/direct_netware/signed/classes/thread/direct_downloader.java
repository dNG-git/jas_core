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
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.loader.direct_main_window;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.thread.direct_thread;
import de.direct_netware.signed.ui.direct_downloader_ui;
import de.direct_netware.signed.ui.direct_error_ui;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JProgressBar;

public class direct_downloader extends Thread implements direct_loader
{
	protected direct_main_window main_window;
	protected direct_dispatcher dispatcher;
	protected String[] download_list;
	protected direct_container downloader_container;
	protected double downloader_size_done;
	protected JProgressBar downloader_progressbar;
	protected double downloader_size_total;
	protected boolean error_continue;
	protected direct_thread error_thread;
	protected Class runtime_class;
	protected direct_downloader_thread success_thread;
	protected boolean success_indeterminate;

	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread) { this (f_parent,f_container,f_download_list,f_success_thread,null,true,false); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,boolean f_success_indeterminate) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate,false); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,boolean f_success_indeterminate,boolean f_error_continue) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate,f_error_continue); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,direct_thread f_error_thread) { this (f_parent,f_container,f_download_list,f_success_thread,f_error_thread,true,false); }

	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,direct_thread f_error_thread,boolean f_success_indeterminate,boolean f_error_continue)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		downloader_container = f_container;
		download_list = f_download_list;
		error_continue = f_error_continue;
		error_thread = f_error_thread;
		runtime_class = getClass ();
		success_thread = f_success_thread;
		success_indeterminate = f_success_indeterminate;
		start ();
	}

	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,null,true,false,f_runtime_class); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,boolean f_success_indeterminate,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate,false,f_runtime_class); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,boolean f_success_indeterminate,boolean f_error_continue,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,null,f_success_indeterminate,f_error_continue,f_runtime_class); }
	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,direct_thread f_error_thread,Class f_runtime_class) { this (f_parent,f_container,f_download_list,f_success_thread,f_error_thread,true,false,f_runtime_class); }

	public direct_downloader (Object f_parent,direct_container f_container,String[] f_download_list,direct_downloader_thread f_success_thread,direct_thread f_error_thread,boolean f_success_indeterminate,boolean f_error_continue,Class f_runtime_class)
	{
		if (f_parent instanceof direct_object)
		{
			direct_object f_object = (direct_object)f_parent;
			main_window = (direct_main_window)f_object.get_main ();
		}

		downloader_container = f_container;
		download_list = f_download_list;
		error_continue = f_error_continue;
		error_thread = f_error_thread;
		runtime_class = f_runtime_class;
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
		int f_download_files = 0;
		downloader_size_total = 1;
		Throwable f_exception = null;
		int f_files = download_list.length;
		boolean f_success = true;
		URL f_url;
		HttpURLConnection f_url_handler;

		String[] f_downloaded_list = new String[f_files];

		if (f_files > 0)
		{
			if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this)); }

			try
			{
				downloader_size_total = 0;

				for (int f_i = 0;f_i < f_files;f_i++)
				{
					f_url = new URL (download_list[f_i]);
					f_url_handler = (HttpURLConnection)f_url.openConnection ();
					f_url_handler.setDoInput (true);
					f_url_handler.setDoOutput (false);
					f_url_handler.setRequestMethod ("HEAD");

					try
					{
						f_url_handler.setConnectTimeout (2000);
						f_url_handler.setReadTimeout (10000);
					}
					catch (Throwable f_unhandled_exception) { }

					try
					{
						if (f_url_handler.getResponseCode () == HttpURLConnection.HTTP_OK)
						{
							f_download_files++;
							downloader_size_total += f_url_handler.getContentLength ();
						}
						else
						{
							f_downloaded_list[f_i] = download_list[f_i];
							download_list[f_i] = null;
							f_success = error_continue;
						}
					}
					catch (Throwable f_handled_exception)
					{
						if (error_continue)
						{
							f_downloaded_list[f_i] = download_list[f_i];
							download_list[f_i] = null;
						}
						else { f_exception = f_handled_exception; }

						f_success = error_continue;
					}
				}
			}
			catch (Throwable f_handled_exception)
			{
				f_exception = f_handled_exception;
				f_success = false;
			}

			if (f_success)
			{
				if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_container,downloader_size_total)); }
				direct_download f_download;

				try
				{
					downloader_size_done = 0;

					for (int f_i = 0;f_i < f_files;f_i++)
					{
						if ((download_list[f_i] != null)&&((f_exception == null)||(error_continue)))
						{
							if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_size_done)); }
							f_download = new direct_download (this,download_list[f_i]);

							while (f_download.is_downloading ())
							{
								direct_dispatcher.dispatch (new direct_downloader_ui (this,(downloader_size_done + (f_download.get_filesize_read ()))));
								Thread.sleep (150);
							}

							f_exception = f_download.get_exception ();

							if (f_exception == null)
							{
								downloader_size_done += f_download.get_filesize_read ();
								f_downloaded_list[f_i] = f_download.get_filename ();
							}
							else
							{
								downloader_size_done += f_download.get_size ();
								f_success = error_continue;
							}

							f_download.interrupt ();
						}
					}

					if (downloader_container != null) { direct_dispatcher.dispatch (new direct_downloader_ui (this,downloader_size_total)); }
				}
				catch (Throwable f_handled_exception)
				{
					f_exception = f_handled_exception;
					f_success = false;
				}
			}
		}

		if (((success_indeterminate)||(!f_success))&&(downloader_container != null)) { direct_dispatcher.dispatch (new direct_downloader_ui (this)); }

		if (f_success)
		{
			success_thread.set_result (download_list,f_downloaded_list);
			direct_dispatcher.dispatch (success_thread);
		}
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
	public synchronized double get_downloader_steps () { return downloader_size_total; }
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