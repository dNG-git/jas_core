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
* Try to start the default desktop web browser with the given URL (needs
* Java 6 or newer).
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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class direct_browser_awtdesktop
{
	public static void execute (URL f_url) throws IllegalArgumentException,IOException,UnsupportedOperationException
	{
		Desktop f_desktop = Desktop.getDesktop ();

		if ((f_desktop != null)&&(f_desktop.isSupported (Desktop.Action.BROWSE)))
		{
			try { f_desktop.browse (f_url.toURI ()); }
			catch (URISyntaxException f_handled_exception) { throw new IllegalArgumentException (); }
		}
		else { throw new UnsupportedOperationException (); }
	}
}

//j// EOF