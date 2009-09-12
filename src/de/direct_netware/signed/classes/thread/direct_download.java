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
import de.direct_netware.signed.loader.direct_main_window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class direct_download extends Thread
{
	protected direct_main_window main_window;
	protected boolean download_done;
	protected Throwable download_exception;
	protected double download_size;
	protected URL download_url;
	protected File file_allocated;
	protected double filesize_read;
	protected direct_loader loader;

	public direct_download (Object f_parent,String f_url) throws IOException
	{
		if (f_parent instanceof direct_loader)
		{
			loader = (direct_loader)f_parent;
			main_window = (direct_main_window)loader.get_main ();
		}

		if (f_url != null)
		{
			download_url = new URL (f_url);

			file_allocated = File.createTempFile ("jas",".download");
			file_allocated.deleteOnExit ();
		}

		download_done = false;
		download_exception = null;
		download_size = 0;
		filesize_read = 0;

		start ();
	}

	public void run ()
	{
		if (download_url != null)
		{
			HttpURLConnection f_url_handler = null;

			try
			{
				f_url_handler = (HttpURLConnection)download_url.openConnection ();
				f_url_handler.setDoInput (true);
				f_url_handler.setDoOutput (false);
				f_url_handler.setRequestMethod ("GET");

				try
				{
					f_url_handler.setConnectTimeout (2000);
					f_url_handler.setReadTimeout (10000);
				}
				catch (Throwable f_unhandled_exception) { }

				if ((f_url_handler.getResponseCode ()) == HttpURLConnection.HTTP_OK)
				{
					download_size = f_url_handler.getContentLength ();

					byte[] f_buffer = new byte[128];
					int f_buffer_size = 0;
					InputStream f_ip = f_url_handler.getInputStream ();
					FileOutputStream f_op = new FileOutputStream (file_allocated);

					while (f_buffer_size > -1)
					{
						f_buffer_size = f_ip.read (f_buffer);

						if (f_buffer_size > 0)
						{
							if (f_buffer.length != f_buffer_size)
							{
								try
								{
									if (Double.parseDouble (System.getProperty ("java.specification.version")) >= 1.6) { f_buffer = Arrays.copyOf (f_buffer,f_buffer_size); }
								}
								catch (Throwable f_unhandled_exception) { }

								if (f_buffer.length != f_buffer_size)
								{
									byte[] f_buffer_copy = new byte[f_buffer_size];
									System.arraycopy (f_buffer,0,f_buffer_copy,0,(Math.min (f_buffer.length,f_buffer_size)));
									f_buffer = f_buffer_copy;
								}
							}

							filesize_read += f_buffer_size;
							f_op.write (f_buffer);
						}
					}

					f_ip.close ();
					f_op.close ();
				}
				else { throw new IOException ("HTTP error code " + String.valueOf (f_url_handler.getResponseCode ())); }
			}
			catch (Throwable f_handled_exception) { download_exception = f_handled_exception; }

			if (f_url_handler != null) { f_url_handler.disconnect (); }
		}

		download_done = true;

		try
		{
			while (true) { Thread.sleep (360000); }
		}
		catch (InterruptedException f_unhandled_exception) { }
	}

	public synchronized String get_filename ()
	{
		if (download_url == null) { return null; }
		else { return file_allocated.getAbsolutePath (); }
	}

	public synchronized double get_filesize_read () { return filesize_read; }
	public synchronized Throwable get_exception () { return download_exception; }
	public synchronized double get_size () { return download_size; }

	public synchronized boolean is_downloading ()
	{
		if (download_done) { return false; }
		else { return true; }
	}
}

//j// EOF