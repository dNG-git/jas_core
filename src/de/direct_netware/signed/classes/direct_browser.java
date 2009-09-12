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
* Try to start the default desktop web browser with the given URL or show
* an error dialog.
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

package de.direct_netware.signed.classes;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.direct_webpage_internal;

import java.net.MalformedURLException;
import java.net.URL;

public class direct_browser
{
	public static void execute (Object f_parent,String f_url) throws MalformedURLException
	{
		URL f_url_parsed = new URL (f_url);
		direct_browser.execute (f_parent,f_url_parsed);
	}

	public static void execute (Object f_parent,URL f_url)
	{
		boolean f_error = false;

		try
		{
			if (Double.parseDouble (System.getProperty ("java.specification.version")) >= 1.6) { direct_browser_awtdesktop.execute (f_url); }
			else { f_error = true; }
		}
		catch (Throwable f_handled_exception) { f_error = true; }

		if (f_error) { direct_dispatcher.dispatch (new direct_webpage_internal (f_parent,f_url)); }
	}
}

//j// EOF