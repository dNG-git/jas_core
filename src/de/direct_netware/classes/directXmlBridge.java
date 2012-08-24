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
* de.direct_netware.signed.classes.directXmlBridge
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

import de.direct_netware.directXmlReader;

public class directXmlBridge extends directXmlReader
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
	public directXmlBridge () { this (true); }
	public directXmlBridge (boolean fParseOnly)
	{
		super ("UTF-8",fParseOnly);

		directLocal fLocal = directLocal.getInstance ();
		directSettings fSettings = directSettings.getInstance ();

		Debug = (directDebug)directGlobals.getInstance ("debug");
		if (fLocal.keyExists ("lang_charset")) { DataCharset = (String)fLocal.valueGet ("lang_charset"); }
		if (fSettings.keyExists ("timeout")) { TimeoutCount = Integer.parseInt ((String)fSettings.valueGet ("timeout")); }

		if (Debug != null) { Debug.add ("xml_bridge.directXmlBridge (new)"); }
	}
}

//j// EOF