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
* directArray is an array implementation providing numbered and Object
* indices.
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

import java.util.Iterator;
import java.util.regex.Pattern;

import de.direct_netware.directArray;

public class directLocal extends directArray
{
	protected static directLocal directLocalInstance;
	protected static directArray IntegrationModules;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor directLocal
	*
	* @since v0.1.00
*/
	protected directLocal () { super (); }

	protected synchronized boolean integrate (String fModule,String fDLang,boolean fForce,String fLangForced)
	{
		boolean fReturn = false;

		if (IntegrationModules == null) { IntegrationModules = new directArray (); }

		directSettings fSettings = directSettings.getInstance ();
		String fLang = ((fLangForced == null) ? (String)fSettings.valueGet("lang",fDLang) : fLangForced);
		fModule = fModule.replaceAll ("\\W","");

		if ((!(IntegrationModules.keyExists (fModule)))||(fForce))
		{
			IntegrationModules.valueSet (fModule,"1");
			directArray fXmlArray = null;

			if (directFileFunctions.fileExists (fSettings.valueGet ("path_lang") + "/jas_" + fModule + "." + fLang + ".xml")) { fXmlArray = directBasicFunctions.getInstance().memcacheGetFileMergedXml (fSettings.valueGet ("path_lang") + "/jas_" + fModule + "." + fLang + ".xml"); }
			else if ((fSettings.keyExists ("jas_lang"))&&(directFileFunctions.fileExists (fSettings.valueGet ("path_lang") + "/jas_" + fModule + "." + (fSettings.valueGet ("jas_lang")) + ".xml"))) { fXmlArray = directBasicFunctions.getInstance().memcacheGetFileMergedXml (fSettings.valueGet ("path_lang") + "/jas_" + fModule + "." + (fSettings.valueGet ("jas_lang")) + ".xml"); }
			else { fXmlArray = directBasicFunctions.getInstance().memcacheGetFileMergedXml (fSettings.valueGet ("path_lang") + "/jas_" + fModule + "." + fDLang + ".xml"); }

			if (fXmlArray != null)
			{
				Pattern fReKeyReplace = Pattern.compile ("jas_lang_file_v(\\d+)_",Pattern.CASE_INSENSITIVE);

				String fIteratorKey;
				directArray fIteratorValue;

				for (Iterator fIterator = fXmlArray.keySet().iterator ();fIterator.hasNext ();)
				{
					fIteratorKey = (String)fIterator.next ();
					fIteratorValue = fXmlArray.valueGetArray (fIteratorKey);

					if (fIteratorValue.keyExists ("value"))
					{
						fIteratorKey = fReKeyReplace.matcher(fIteratorKey).replaceAll ("");
						valueSet (fIteratorKey,((String)fIteratorValue.valueGet ("value")),true);
					}
				}

				fReturn = true;
			}
		}

		return fReturn;
	}

/* -------------------------------------------------------------------------
Static methods
------------------------------------------------------------------------- */

	public static synchronized directLocal getInstance ()
	{
		if (directLocalInstance == null) { directLocalInstance = new directLocal (); }
		return directLocalInstance;
	}

	public static String get (String fStringID) { return get (fStringID,"html"); }

	public static synchronized String get (String fStringID,String fHandling)
	{
		if (directLocalInstance == null) { directLocalInstance = new directLocal (); }
		String fReturn = " " + fStringID + " ";

		if (fHandling.equals ("html"))
		{
			if (directLocalInstance.keyExists (fStringID + "_html")) { fReturn = (String)directLocalInstance.valueGet (fStringID + "_html"); }
			else if (directLocalInstance.keyExists (fStringID + "_universal")) { fReturn = (String)directLocalInstance.valueGet (fStringID + "_universal"); }
		}
		else
		{
			if (directLocalInstance.keyExists (fStringID + "_universal")) { fReturn = (String)directLocalInstance.valueGet (fStringID + "_universal"); }
			else if (directLocalInstance.keyExists (fStringID + "_text")) { fReturn = (String)directLocalInstance.valueGet (fStringID + "_text"); }

			if (fHandling.equals ("text_quoted")) { fReturn = fReturn.replaceAll ("\"","\\\""); }
		}

		return fReturn;
	}

	public static boolean integration (String fModule) { return integration (fModule,"en",false,null); }
	public static boolean integration (String fModule,String fDLang) { return integration (fModule,fDLang,false,null); }
	public static boolean integration (String fModule,String fDLang,boolean fForce) { return integration (fModule,fDLang,fForce,null); }

	public static synchronized boolean integration (String fModule,String fDLang,boolean fForce,String fLangForced)
	{
		if (directLocalInstance == null) { directLocalInstance = new directLocal (); }
		return directLocalInstance.integrate (fModule,fDLang,fForce,fLangForced);
	}
}

//j// EOF