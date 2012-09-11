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
* de.direct_netware.signed.classes.directEvars
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

/**
* evars (Extended variables) are our answer for dynamic data in (for example)
* SQL log tables.
*
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_core
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;mpl2
*            Mozilla Public License, v. 2.0
*/
public class directEvars
{
/**
	* To receive all data (key-value pairs) from evars, use "get ()". This
	* function needs for its recursive job a helper function.
	*
	* @param  fData Internally evars are XML strings containing base64-encoded
	*         data if chosen (for binary content).
	* @return Key-value pair array
	* @since  v0.1.00
*/
	public static directArray get (String fData)
	{
		directArray fReturn = null;

		directArray fResultArray = (new directXml ()).xml2array (fData.trim (),true,false);

		if ((fResultArray != null)&&(fResultArray.keyExists ("evars")))
		{
			fResultArray = getWalker (fResultArray.valueGetArray ("evars"));
			if (fResultArray.size () > 0) { fReturn = new directArray (fResultArray); }
		}

		return fReturn;
	}

/**
	* This is a helper function for "directEvars.get ()" to convert an XML
	* dictionary recursively.
	*
	* @param  fXmlArray XML nodes in a specific level.
	* @return (dict) Key-value pair array
	* @since  v0.1.00
*/
	protected static directArray getWalker (directArray fXmlArray)
	{
		directArray fReturn = new directArray ();

		if (fXmlArray != null)
		{
			if (fXmlArray.keyExists ("xml.item")) { fXmlArray.keyRemove ("xml.item"); }
			boolean fMtree = fXmlArray.keyExists ("xml.mtree");
			int fMtreeCounter = 0;
			if (fMtree) { fXmlArray.keyRemove ("xml.mtree"); }

			if (fXmlArray.size () > 0)
			{
				String fKey;
				directArray fXmlNodeArray;

				for (Iterator fIterator = fXmlArray.keySet().iterator ();fIterator.hasNext ();)
				{
					fKey = (String)fIterator.next ();
					fXmlNodeArray = (directArray)fXmlArray.valueGet (fKey);

					if ((fXmlNodeArray.keyExists ("xml.item"))||(fXmlNodeArray.keyExists ("xml.mtree"))) { fReturn.keyAdd (fKey,(getWalker (fXmlNodeArray))); }
					else if ((fXmlNodeArray.keyExists ("tag"))&&(!fXmlNodeArray.valueGet("tag").equals ("")))
					{
						if (fMtree) { fReturn.keyAdd (fMtreeCounter,(((fXmlNodeArray.keyExists ("attributes"))&&(fXmlNodeArray.valueGetArray("attributes").keyExists ("base64"))) ? directBasicFunctions.base64Decode ((String)fXmlNodeArray.valueGet ("value")) : fXmlNodeArray.valueGet ("value"))); }
						else { fReturn.keyAdd (fXmlNodeArray.valueGet ("tag"),(((fXmlNodeArray.keyExists ("attributes"))&&(fXmlNodeArray.valueGetArray("attributes").keyExists ("base64"))) ? directBasicFunctions.base64Decode ((String)fXmlNodeArray.valueGet ("value")) : fXmlNodeArray.valueGet ("value"))); }
					}

					if (fMtree) { fMtreeCounter++; }
				}
			}
		}

		return fReturn;
	}

/**
	* To save all data from f_data as an evars-string, call "write ()". The
	* helper function will encode relevant data with b64encode if applicable.
	*
	* @param  data_dict Input dictionary
	* @param  binary_safe True to encode values with base64.
	* @return (str) XML string
	* @since  v0.1.00
*/
	public static String write (directArray fDataArray) { return write (fDataArray,false); }
	public static String write (directArray fDataArray,boolean fBinarySafe)
	{
		String fReturn = "";
		directXml fXmlObject = new directXml ();

		if ((fDataArray != null)&&(fDataArray.size () > 0))
		{
			fDataArray = new directArray ("evars",fDataArray);
			fXmlObject.arrayImport (fDataArray,true);

			if (fBinarySafe)
			{
				fDataArray = new directArray (writeBase64Walker (fXmlObject.get ()));
				fReturn = fXmlObject.array2xml (fDataArray,false);
			}
			else { fReturn = fXmlObject.cacheExport (true); }
		}

		return fReturn;
	}

/**
	* This recursive function is used to protect binary data in a system optimized
	* for strings.
	*
	* @param  array $f_data_array Input array
	* @uses   direct_debug()
	* @uses   direct_evars_write_base64_walker()
	* @uses   USE_debug_reporting
	* @return array Output array (base64-encoded values if required)
	* @since  v0.1.03
*/
	protected static directArray writeBase64Walker (directArray fDataArray)
	{
		directArray fReturn = new directArray ();

		if ((fDataArray != null)&&(fDataArray.size () > 0))
		{
			String fKey;
			directArray fNodeArray;
			String fNodeValue;

			for (Iterator fIterator = fDataArray.keySet().iterator ();fIterator.hasNext ();)
			{
				fKey = (String)fIterator.next ();
				fNodeArray = (directArray)fDataArray.valueGet (fKey);
				Pattern fReWhitespace = Pattern.compile ("\\W");

				if (fNodeArray.keyExists ("xml.item")) { fReturn.keyAdd (fKey,(writeBase64Walker (fNodeArray))); }
				else if ((fNodeArray.keyExists ("tag"))&&(!fNodeArray.valueGet("tag").equals ("")))
				{
					fNodeValue = (String)fNodeArray.valueGet("value");

					if (fReWhitespace.matcher(fNodeValue).matches ())
					{
						if (fNodeArray.keyExists ("attributes")) { fNodeArray.valueGetArray("attributes").valueSet ("base64",1,true); }
						else { fNodeArray.keyAdd ("attributes",(new directArray ("base64",1))); }

						fNodeArray.valueSet ("value",(directBasicFunctions.base64Encode (fNodeValue)));
					}

					fReturn.keyAdd (fKey,fNodeArray);
				}
			}
		}

		return fReturn;
	}
}

//j// EOF