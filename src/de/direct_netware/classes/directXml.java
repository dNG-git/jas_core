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
* de.direct_netware.signed.classes.directXml
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

import de.direct_netware.directXmlWriter;

public class directXml extends directXmlWriter
{
/**
	* Debug message container
*/
	protected directDebug Debug;

/**
	* Constructor __init__ (direct_xml_bridge)
	*
	* @param parse_only Parse data only
	* @since v0.1.00
*/
	public directXml ()
	{
		super ("UTF-8");

		directLocal fLocal = directLocal.getInstance ();
		directSettings fSettings = directSettings.getInstance ();

		Debug = (directDebug)directGlobals.getInstance ("debug");
		if (fLocal.keyExists ("lang_charset")) { DataCharset = (String)fLocal.valueGet ("lang_charset"); }
		if (fSettings.keyExists ("timeout")) { TimeoutCount = Integer.parseInt ((String)fSettings.valueGet ("timeout")); }

		if (Debug != null) { Debug.add ("xml.directXml (new)"); }
	}
}

//j// EOF