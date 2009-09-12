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
* The "JS worker" is a translation layer for JavaScript commands.
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
import de.direct_netware.signed.loader.direct_applet;

import java.security.PrivilegedExceptionAction;

public class direct_js implements PrivilegedExceptionAction
{
	protected direct_applet applet;
	protected String key;
	protected String method;
	protected String value;

	public direct_js (direct_applet f_applet,String f_method,String f_key,String f_value)
	{
		applet = f_applet;
		method = f_method;
		key = f_key;
		value = f_value;
	}

	public Object run () throws Exception
	{
		String f_return = "";

		if (method.equalsIgnoreCase ("compatible_check")) { f_return = applet.compatibility (); }
		if (method.equalsIgnoreCase ("error_last_get")) { f_return = applet.get_error_last (); }
		if (method.equalsIgnoreCase ("exists_check")) { f_return = "1"; }
		if (method.equalsIgnoreCase ("ready_check")) { f_return = applet.ready (); }

		return f_return;
	}
}

//j// EOF