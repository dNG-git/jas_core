/*- coding: utf-8 -*/
//j// BOF

/*n// NOTE
----------------------------------------------------------------------------
direct JAS
Java Application Services
----------------------------------------------------------------------------
(C) direct Netware Group - All rights reserved
http://www.direct-netware.de/redirect.php?jas

This Source Code Form is subject to the terms of the Mozilla Public License,
v. 2.0. If a copy of the MPL was not distributed with this file, You can
obtain one at http://mozilla.org/MPL/2.0/.
----------------------------------------------------------------------------
http://www.direct-netware.de/redirect.php?licenses;mpl2
----------------------------------------------------------------------------
NOTE_END //n*/
/**
* de.direct_netware.signed.classes.directGlobals
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
* @license   http://www.direct-netware.de/redirect.php?licenses;mpl2
*            Mozilla Public License, v. 2.0
*/

package de.direct_netware.classes;

import de.direct_netware.directArray;

public class directGlobals extends directArray
{
	protected static directGlobals directGlobalsInstance;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor directGlobals
	*
	* @since v0.1.00
*/
	protected directGlobals () { super (); }

/* -------------------------------------------------------------------------
Static methods
------------------------------------------------------------------------- */

	public static synchronized Object getInstance (Object fKey)
	{
		Object fReturn = null;

		if (directGlobalsInstance == null) { directGlobalsInstance = new directGlobals (); }
		if (directGlobalsInstance.keyExists (fKey)) { fReturn = directGlobalsInstance.valueGet (fKey); }

		return fReturn;
	}

	public static synchronized boolean setInstance (Object fKey,Object fObject)
	{
		if (directGlobalsInstance == null) { directGlobalsInstance = new directGlobals (); }
		return directGlobalsInstance.valueSet (fKey,fObject,true);
	}
}

//j// EOF