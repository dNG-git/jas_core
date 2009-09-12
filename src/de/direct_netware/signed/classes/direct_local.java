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
* de.direct_netware.signed.classes.direct_local
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

public class direct_local extends direct_array
{
	public static final long serialVersionUID = -2186575985548624393L;

	public direct_local () { super (); }

	public synchronized String local_get (String f_id)
	{
		Object f_return = value_get (f_id);

		if (f_return == null) { return (" " + f_id + " "); }
		else { return String.valueOf (f_return); }
	}

	public synchronized void local_remove (String f_id,String f_value) { key_remove (f_id); }
	public synchronized void local_set (String f_id,String f_value) { key_add (f_id,f_value,true); }
}

//j// EOF