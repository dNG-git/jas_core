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
* de.direct_netware.signed.classes.exception.dNGException
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

package de.direct_netware.classes.exception;

import java.io.PrintStream;

/**
* The extended dNGException is used to redirect exceptions to output streams.
*
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_core
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;mpl2
*            Mozilla Public License, v. 2.0
*/
public class dNGException extends Exception
{
	private static final long serialVersionUID = -3979786808924102226L;

	protected StackTraceElement[] excTraceback;

/* -------------------------------------------------------------------------
Extend the class
------------------------------------------------------------------------- */

/**
	Constructor __init__ (dNGException)

	@param value Exception message value
	@param py_exception Inner exception
	@since v0.1.00
*/
	public dNGException (String fValue) { this (fValue,null); }
	public dNGException (String fValue,Throwable fException)
	{
		super (fValue);

		if (fException == null) { excTraceback = getStackTrace (); }
		else
		{
			excTraceback = fException.getStackTrace ();
			initCause (fException);
		}
	}

/**
	Prints the stack trace to the given output stream.

	@param out_stream Output stream
	@since v0.1.00
*/
	public void printStackTrace (PrintStream fOutStream)
	{
		if (fOutStream != null) { printStackTrace (fOutStream); }
	}

/**
	Prints the stack trace to the given output stream.

	@param out_stream Output stream
	@since v0.1.00
*/
	public static void printCurrentStackTrace (Throwable fException,PrintStream fOutStream)
	{
		if (fOutStream != null) { fException.printStackTrace (fOutStream); }
	}
}

//j// EOF