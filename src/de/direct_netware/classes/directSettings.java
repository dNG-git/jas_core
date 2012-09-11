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
* de.direct_netware.signed.classes.directSettings
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import de.direct_netware.directArray;

public class directSettings extends directArray
{
	protected static directSettings directSettingsInstance;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor directSettings
	*
	* @since v0.1.00
*/
	protected directSettings ()
	{
		super ();

		String fEnvValue;

		try { fEnvValue = System.getenv ("dNGpath"); }
		catch (Error fHandledException) { fEnvValue = System.getProperty ("dNGpath"); }

		try
		{
			String fPath;

			if (fEnvValue == null) { fPath = ((File.separatorChar == '/') ? System.getProperty ("user.dir") : System.getProperty("user.dir").replace (File.separatorChar,'/')); }
			else { fPath = new URL("file:" + fEnvValue).getPath (); }

			if (fPath.endsWith ("/")) { fPath = fPath.substring (0,(fPath.length () - 1)); }
			keyAdd ("path_base",fPath);
		}
		catch (MalformedURLException fHandledException) { valueSet ("path_base","file:.",true); }

		try
		{
			try { fEnvValue = System.getenv ("dNGpathData"); }
			catch (Error fHandledException) { fEnvValue = System.getProperty ("dNGpathData"); }

			if (fEnvValue == null) { keyAdd ("path_data","resource:/data"); }
			else { keyAdd ("path_data",(new URL("file:" + fEnvValue).getPath ())); }
		}
		catch (MalformedURLException fHandledException) { valueSet ("path_base",(valueGet ("path_base") + "/data"),true); }

		try
		{
			try { fEnvValue = System.getenv ("dNGpathLang"); }
			catch (Error fHandledException) { fEnvValue = System.getProperty ("dNGpathLang"); }

			if (fEnvValue == null) { keyAdd ("path_lang","resource:/lang"); }
			else { keyAdd ("path_lang",(new URL("file:" + fEnvValue).getPath ())); }
		}
		catch (MalformedURLException fHandledException) { valueSet ("path_lang",(valueGet ("path_base") + "lang"),true); }
	}

	public synchronized boolean keyAdd (Object fKey,Object fValue,boolean fOverwrite)
	{
		if (super.keyAdd (fKey,fValue,fOverwrite))
		{
			String fPropertyKey = (String)fKey;
			if (fValue instanceof String) { System.setProperty ("de.direct_netware.settings." + fPropertyKey.replaceAll ("\0","."),(String)fValue); }
			return true;
		}
		else { return false; }
	}

	public synchronized boolean keyRemove (Object fKey,boolean fIgnoreMissing)
	{
		if (super.keyRemove (fKey,fIgnoreMissing))
		{
			String fPropertyKey = (String)fKey;
			System.clearProperty ("de.direct_netware.settings." + fPropertyKey.replaceAll ("\0","."));
			return true;
		}
		else { return false; }
	}

	public synchronized boolean valueSet (Object fKey,Object fValue,boolean fAdd)
	{
		if (super.valueSet (fKey,fValue,fAdd))
		{
			String fPropertyKey = (String)fKey;
			if (fValue instanceof String) { System.setProperty ("de.direct_netware.settings." + fPropertyKey.replaceAll ("\0","."),(String)fValue); }
			return true;
		}
		else { return false; }
	}

/* -------------------------------------------------------------------------
Static methods
------------------------------------------------------------------------- */

	public static synchronized directSettings getInstance ()
	{
		directSettings fReturn = (directSettings)directGlobals.getInstance ("settings");

		if (fReturn == null)
		{
			fReturn = new directSettings ();
			directGlobals.setInstance ("settings",fReturn);
		}

		return fReturn;
	}
}

//j// EOF