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
* Loads classes with the given ClassLoader.
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

import java.lang.ClassNotFoundException;

public class direct_downloader_context_class
{
	protected ClassLoader classloader;

	public direct_downloader_context_class () { classloader = ClassLoader.getSystemClassLoader (); }

	public direct_downloader_context_class (ClassLoader f_classloader)
	{
		if (f_classloader == null) { classloader = ClassLoader.getSystemClassLoader (); }
		else { classloader = f_classloader; }
	}

	public synchronized boolean download_class (String f_classname)
	{
		boolean f_return = true;

		try { classloader.loadClass (f_classname); }
		catch (ClassNotFoundException f_handled_exception) { f_return = false; }

		return f_return;
	}
}

//j// EOF