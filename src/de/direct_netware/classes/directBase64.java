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
* de.direct_netware.signed.classes.directBase64
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

import java.io.UnsupportedEncodingException;
import java.lang.ThreadLocal;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

/**
* AbstractPreferences support the Base64 encoding. As we need a Java fallback
* if no external libraries are installed, we use the AbstractPreferences
* solution for it.
*
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_core
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;w3c
*            W3C (R) Software License
*/
public class directBase64 extends AbstractPreferences
{
	protected static ThreadLocal directBase64Instance = new ThreadLocal () { protected synchronized Object initialValue () { return new directBase64 (); } };
	protected String Data;

	protected directBase64 () { super (null,null); }

	public String base64Decode (String fData)
	{
		try { return base64Decode (fData,"UTF-8"); }
		catch (UnsupportedEncodingException fHandledException) { return null; }
	}

	public synchronized String base64Decode (String fData,String fCharset) throws UnsupportedEncodingException
	{
		put (null,fData);
		return new String (getByteArray (null,null),fCharset);
	}

	public synchronized String base64Encode (byte[] fData)
	{
		putByteArray (null,fData);
		return get (null,null);
	}

	public String base64Encode (String fData) { return base64Encode (fData.getBytes ()); }
	public String get (String fKey,String fValue) { return Data; }
	public void put (String fKey,String fValue) { Data = fValue; }

	protected AbstractPreferences childSpi (String name) { return null; }
	protected String[] childrenNamesSpi () throws BackingStoreException { return null; }
	protected void flushSpi () throws BackingStoreException { }
	protected String getSpi (String key) { return null; }
	protected String[] keysSpi () throws BackingStoreException { return null; }
	protected void putSpi (String key,String value) { }
	protected void removeNodeSpi () throws BackingStoreException { }
	protected void removeSpi (String key) { }
	protected void syncSpi () throws BackingStoreException { }

	protected static directBase64 getInstance () { return (directBase64)directBase64Instance.get (); }
	public static String decode (String fData) { return getInstance().base64Decode (fData); }
	public static String decode (String fData,String fCharset) throws UnsupportedEncodingException { return getInstance().base64Decode (fData,fCharset); }
	public static String encode (byte[] fData) { return getInstance().base64Encode (fData); }
	public static String encode (String fData) { return getInstance().base64Encode (fData); }
}

//j// EOF