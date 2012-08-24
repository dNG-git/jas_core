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
* de.direct_netware.signed.classes.directFileFunctions
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

import de.direct_netware.directFile;

public class directFileFunctions extends directFile
{
/**
	* Debug message container
*/
	protected directDebug Debug;

/**
	* Constructor __init__ (direct_file_functions)
	*
	* @since v0.1.00
*/
	public directFileFunctions ()
	{
		super ();

		directSettings fSettings = directSettings.getInstance ();
		Chmod = Integer.parseInt ((String)fSettings.valueGet ("jas_chmod_files_change","-1"));
		Debug = (directDebug)directGlobals.getInstance ("debug");
		TimeoutCount = Integer.parseInt ((String)fSettings.valueGet ("timeout","5"));
		Umask = Integer.parseInt ((String)fSettings.valueGet ("jas_umask_change","-1"));

		if (Debug != null) { Debug.add ("file_functions.directFileFunctions (new)"); }
	}

	public static boolean fileExists (String fFilePathname)
	{
		boolean fReturn = false;
		URL fURL;

		try { fURL = fileGetURL (fFilePathname,true); }
		catch (MalformedURLException fHandledException) { fURL = null; }

		if (fURL == null) { fReturn = false; }
		else if (fURL.getProtocol().equals ("jar")) { fReturn = true; }
		else
		{
			try { fReturn = new File(new URI (fURL.toExternalForm ())).exists (); }
			catch (Throwable fUnhandledException) { }
		}

		return fReturn;
	}

/**
	* Let's work with files - use "file_get ()" to get content from a local file.
	* A time check will stop the reading process before a script timeout occurs.
	*
	* @param  file_type Read mode to use. Options: "r", "s", "s0" and "s1" for ASCII
	*    (string); "a", "a0" and "a1" for ASCII (one line per array element)
	*    and "b" for binary. Use "a0" or "s0" to return the content as it is.
	*    "a1" and "s1" remove "<?php exit (); ?>" strings but whitespace
	*    characters at the start or end of the file content remain.
	* @param  file_pathname File path
	* @return (mixed) File content on success; False on error
	* @since  v0.1.00
*/
	public static byte[] fileGet (String fFileType,String fFilePathname) throws FileNotFoundException,IOException
	{
		directDebug fDebug = (directDebug)directGlobals.getInstance ("debug");
		if (fDebug != null) { fDebug.add ("file_functions.fileGet (" + fFileType + "," + fFilePathname + ")"); }

		InputStream fInputStream = null;
		byte[] fReturn = null;

		if ((fFilePathname.startsWith ("jar:file:"))||(fFilePathname.startsWith ("resource:")))
		{
			fInputStream = directFileFunctions.class.getResourceAsStream (fFilePathname.substring (9));
			if (fInputStream == null) { fFilePathname = ("." + (fFilePathname.substring (9))); }
		}

		if (fInputStream == null)
		{
			URI fFileURI = null;

			try { fFileURI = new URI (fileGetURL(fFilePathname).toExternalForm ()); }
			catch (Throwable fUnhandledException) { }

			if ((fFileURI != null)&&(new File(fFileURI).exists ()))
			{
				directFileFunctions fFileObject = new directFileFunctions ();

				if (fFileType.equals ("b")) { fFileObject.open (fFileURI.toString(),true,"rb"); }
				else { fFileObject.open (fFileURI.toString(),true,"r"); }

				if (fFileObject.resourceCheck ())
				{
					fReturn = fFileObject.read (0);
					fFileObject.close ();
				}
			}
			else { throw new FileNotFoundException ("Failed opening " + fFilePathname + " - file does not exist"); }
		}
		else
		{
			long fTimeout = Integer.parseInt ((String)directSettings.getInstance().valueGet ("timeout"));
			if (fTimeout < 0) { fTimeout = 5; }
			Date fTimeoutTime = new Date (new Date().getTime () + (fTimeout * 1000));

			ByteArrayOutputStream fBytesRead = new ByteArrayOutputStream ();
			byte[] fPartBytes = new byte[4096];
			int fPartBytesRead = 0;

			do
			{
				fPartBytesRead = fInputStream.read (fPartBytes);

				if (fPartBytesRead > 0)
				{
					fPartBytesRead = Math.min (fPartBytes.length,fPartBytesRead);
					fBytesRead.write (fPartBytes,0,fPartBytesRead);
				}
			}
			while ((fPartBytesRead > -1)&&(fTimeoutTime.after (new Date ())));

			if (fPartBytesRead == -1) { fReturn = fBytesRead.toByteArray (); }
		}


		return fReturn;
	}

	public static String[] fileGetArray (String fFileType,String fFilePathname) throws FileNotFoundException,IOException
	{
		String[] fReturn = null;

		byte[] fFileContent = fileGet (fFileType,fFilePathname);

		if (fFileContent != null)
		{
			String fFileLines = new String (fFileContent);
			fFileLines = fFileLines.replaceAll ("\r","");
			if (fFileType != "a0") { fFileLines = fileGetRemovePHPLine (fFileLines); }
			if (fFileType != "a1") { fFileLines = fFileLines.trim (); }
			fReturn = fFileLines.split ("\n");
		}

		return fReturn;
	}

	protected static String fileGetRemovePHPLine (String fData) { return Pattern.compile("^<\\?php exit(.*?); \\?>\n",Pattern.CASE_INSENSITIVE).matcher(fData).replaceFirst (""); }

	public static String fileGetString (String fFileType,String fFilePathname) throws FileNotFoundException,IOException
	{
		String fReturn = null;

		byte[] fFileContent = fileGet (fFileType,fFilePathname);

		if (fFileContent != null)
		{
			fReturn = new String (fFileContent);
			fReturn = fReturn.replaceAll ("\r","");
			if (fFileType != "s0") { fReturn = fileGetRemovePHPLine (fReturn); }
			if (fFileType != "s1") { fReturn = fReturn.trim (); }
		}

		return fReturn;
	}

	public static URL fileGetURL (String fFilePathname) throws MalformedURLException { return fileGetURL (fFilePathname,false); }

	public static URL fileGetURL (String fFilePathname,boolean fLocalResourceCheck) throws MalformedURLException
	{
		URL fReturn = null;

		if (fFilePathname.startsWith ("resource:"))
		{
			fReturn = directFileFunctions.class.getResource (fFilePathname.substring (9));
			if ((fReturn == null)&&(fLocalResourceCheck)) { fFilePathname = ("." + (fFilePathname.substring (9))); }
		}

		if (fReturn == null)
		{
			if (fFilePathname.startsWith("file:./")) { fFilePathname = fFilePathname.replaceFirst ("file:./",("file:" + (directSettings.getInstance().valueGet ("path_base")) + "/")); }

			try { fReturn = new URL (fFilePathname); }
			catch (MalformedURLException fHandledException)
			{
				if (!(fFilePathname.startsWith("file:"))) { fFilePathname = "file:" + fFilePathname; }
				if (fFilePathname.startsWith("file:./")) { fFilePathname = fFilePathname.replaceFirst ("file:./",("file:" + (directSettings.getInstance().valueGet ("path_base")) + "/")); }
				fReturn = new URL (fFilePathname);
			}
		}

		return fReturn;
	}

/*
		def file_write (data,file_pathname,file_type = ""):
		#
			"""
	The following function will save given data (as data) to a file.

	@param  data Data to write (array or string)
	@param  file_pathname File path
	@param  file_type Write mode to use. Options: "r", "s", "s0" and "s1" for ASCII
	        (string); "a", "a0" and "a1" for ASCII (one line per array element)
	        and "b" for binary. Use "a0" or "s0" to save the content as it is.
	        "a1" and "s1" add "<?php exit (); ?>" strings but whitespace
	        characters at the start or end of the file content remain.
	@return (bool) True on success
	@since  v0.1.00
			"""

			file_type = direct_str (file_type)
			file_pathname = direct_str (file_pathname)
			f_file_object = direct_file_functions.py_get ()

			if (f_file_object == None): return False
			else:
			#
				if (f_file_object.debug != None): f_file_object.debug.append ("#echo(__FILEPATH__)# -file_functions.file_write (data,{0},{1})- (#echo(__LINE__)#)".format (file_pathname,file_type))

				if (type (data) == list): f_file_content = "\n".join (data)
				else: f_file_content = data

				if ((file_type == "a") or (file_type == "r") or (file_type == "s")): f_file_content = f_file_content.strip ()

				if (file_type == "b"): f_file_object.open (file_pathname,False,"wb")
				else: f_file_object.open (file_pathname,False,"w")

				if ((file_type == "a0") or (file_type == "b") or (file_type == "s0")): f_file_object.write (f_file_content)
				else: f_file_object.write ("<?php exit (); ?>\n{0}".format (direct_str (f_file_content)))

				return f_file_object.close ()
			#
		#
		file_write = staticmethod (file_write)
*/
}

//j// EOF