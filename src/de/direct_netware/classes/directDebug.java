/*- coding: utf-8 -*/
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
* de.direct_netware.signed.classes.directDebug
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

package de.direct_netware.classes;

import java.util.Collection;
import java.util.Vector;

public class directDebug extends Vector
{
	private static final long serialVersionUID = -1792424742942016994L;

	protected static directDebug directDebugInstance;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor directDebug
	*
	* @param Entries to add
	* @since v0.1.00
*/
	protected directDebug (Collection fEntries)
	{
		super ();
		if (fEntries != null) { addAll (fEntries); }
	}

/* -------------------------------------------------------------------------
Static methods
------------------------------------------------------------------------- */

	public static synchronized directDebug getInstance () { return getInstance (null,false); }
	public static synchronized directDebug getInstance (boolean fDebug) { return getInstance (null,fDebug); }

	public static synchronized directDebug getInstance (Collection fEntries,boolean fDebug)
	{
		if ((fDebug)&&(directDebugInstance == null)) { directDebugInstance = new directDebug (fEntries); }
		return directDebugInstance;
	}
}

//j// EOF