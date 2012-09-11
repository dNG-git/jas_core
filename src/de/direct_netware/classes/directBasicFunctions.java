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
* de.direct_netware.signed.classes.directBasicFunctions
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

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.direct_netware.directArray;

/**
* Parse settings, check input or support localisations are required
* everywhere.
*
* @author    direct Netware Group
* @copyright (C) direct Netware Group - All rights reserved
* @package   jas_core
* @since     v0.1.00
* @license   http://www.direct-netware.de/redirect.php?licenses;mpl2
*            Mozilla Public License, v. 2.0
*/
public class directBasicFunctions
{
	protected static directBasicFunctions directBasicFunctionsInstance;
/**
	* Debug message container
*/
	protected directDebug Debug;
/**
	Logging object
*-/
	protected directLogger Logger;
/**
	* Settings singleton
*/
	protected directSettings Settings;
/**
	* Setting files cache
*/
	protected directArray SettingsCache;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor __init__ (direct_basic_functions)
	*
	* @since v0.1.00
*/
	protected directBasicFunctions ()
	{
		Settings = directSettings.getInstance ();
		String fEnvValue = Locale.getDefault().getLanguage ();

		if (!Settings.keyExists ("debug_reporting")) { Settings.keyAdd ("debug_reporting","0"); }
		if (!Settings.keyExists ("jas_memcache")) { Settings.keyAdd ("jas_memcache",""); }
		if (!Settings.keyExists ("lang")) { Settings.keyAdd ("lang",(fEnvValue.length () < 1) ? "en" : fEnvValue); }
		if (!Settings.keyExists ("timeout")) { Settings.keyAdd ("timeout",3600); }

		SettingsCache = new directArray ();
	}

	protected void init ()
	{
		try { settingsGet (Settings.valueGet ("path_data") + "/settings/jas_core.xml"); }
		catch (FileNotFoundException fUnhandledException) { }

		if (Settings.valueGet("debug_reporting").equals ("1"))
		{
			Debug = directDebug.getInstance (true);
			Debug.add ("basic_functions_class.directBasicFunctions (new)");
		}
		else
		{
			Debug = null;
			directGlobals.setInstance ("debug",null);
			//if (("jas_log_level" in self.settings) and (hasattr (direct_logger,self.settings['jas_log_level']))): self.logger = direct_logger.py_get (getattr (direct_logger,self.settings['jas_log_level']))
		}

		directLocal.integration ("core");
	}

/*
	def memcache_get_file (self,file_pathname):
	#
		"""
Reads a file from the memcache or the filesystem. Certain system files are
read in on each page call. These small files are stored in the memcache
(ramfs on UNIX for example) to increase the read performance.

@param  file_pathname The file (which may also exist in the memcache)
@return (mixed) Data on success; false on error
@since  v0.1.00
		"""

		file_pathname = direct_str (file_pathname)

		if (self.debug != None): self.debug.append ("#echo(__FILEPATH__)# -basic_functions_class.memcache_get_file ({0})- (#echo(__LINE__)#)".format (file_pathname))
		f_return = False

		f_continue_check = True

		if ((len (self.settings['jas_memcache']) > 0) and (self.settings['jas_memcache_files'])):
		#
			f_cache_file = "{0}/{1}.{2}".format (self.settings['jas_memcache'],self.settings['jas_memcache_id'],(self.md5 ("{0}.{1}".format (path.dirname (file_pathname),(path.basename (file_pathname))))))

			if (path.exists (f_cache_file)):
			#
				f_continue_check = False
				f_return = direct_file_functions.file_get ("s",f_cache_file)
			#

			if (f_continue_check):
			#
				try:
				#
					shutil.copyfile (file_pathname,f_cache_file)
					os.chmod (f_cache_file,(stat.S_IRUSR | stat.S_IWUSR))
					f_return = direct_file_functions.file_get ("s",f_cache_file)
				#
				except:
				#
					if (path.exists (file_pathname)): f_return = direct_file_functions.file_get ("s",file_pathname)
				#
			#
		#
		elif (path.exists (file_pathname)): f_return = direct_file_functions.file_get ("s",file_pathname)

		return f_return
	#
*/
/**
	* This function uses preparsed XML files to increase performance. Please node
	* that these files are only readable as Python Pickle files.
	*
	* @param  file_pathname The XML file (which may also exist in the memcache)
	* @return (mixed) Parsed merged XML array on success
	* @since  v0.1.00
*/
	public directArray memcacheGetFileMergedXml (String fFilePathname)
	{
		if (Debug != null) { Debug.add ("basic_functions_class.memcacheGetFileMergedXml (" + fFilePathname + ")"); }
		directArray fReturn = new directArray ();

		boolean fContinueCheck = true;

/*		if ((len (self.settings['jas_memcache']) > 0) and (self.settings['jas_memcache_merged_xml_files'])):
		#
			f_cache_file = "{0}/{1}.{2}".format (self.settings['jas_memcache'],self.settings['jas_memcache_id'],(self.md5 ("{0}.{1}".format (path.dirname (file_pathname),(path.basename (file_pathname))))))

			if (path.exists (f_cache_file)):
			#
				f_continue_check = False
				f_file_data = direct_file_functions.file_get ("b",f_cache_file)

				try:
				#
					if (type (f_file_data) != bool): f_return = pickle.loads (f_file_data)
				#
				except: f_return = None
			#
		#
*/
		if (fContinueCheck)
		{
			if (directFileFunctions.fileExists (fFilePathname))
			{
				byte[] fFileContent;

				try { fFileContent = directFileFunctions.fileGet ("s",fFilePathname); }
				catch (Throwable fHandledException) { fFileContent = null; }

				String fFileData = ((fFileContent == null) ? null : new String (fFileContent));
				directXmlBridge fXmlObject = new directXmlBridge ();

				if (fFileData != null)
				{
					fReturn = fXmlObject.xml2array (fFileData,false);

/*					if ((len (self.settings['jas_memcache']) > 0) and (self.settings['jas_memcache_merged_xml_files']) and (type (f_return) != bool)):
					#
						try:
						#
							f_file_data = pickle.dumps (f_return)
							if (direct_file_functions.file_write (f_file_data,f_cache_file,"b")): os.chmod (f_cache_file,(stat.S_IRUSR | stat.S_IWUSR))
						#
						except: jass
					#
*/				}
			}
		}

		return fReturn;
	}
/*
	def memcache_write_file (self,data,file_pathname,file_type = "s0"):
	#
		"""
Writes data to a file (and deletes the old memcache copy).

@param  data Data string
@param  file_pathname Target file
@param  file_type Write mode to use. Options: "r", "s", "s0" and "s1" for ASCII
        (str); "a", "a0" and "a1" for ASCII (one line per array element)
        and "b" for binary. Use "a0" or "s0" to save the content as it is.
        "a1" and "s1" add "<?php exit (); ?>" strings but whitespace
        characters at the start or end of the file content remain.
@return (mixed) True on success
@since  v0.1.00
		"""

		file_pathname = direct_str (file_pathname)
		file_type = direct_str (file_type)

		if (self.debug != None): self.debug.append ("#echo(__FILEPATH__)# -basic_functions_class.memcache_write_file (data,{0},{1})- (#echo(__LINE__)#)".format (file_pathname,file_type))

		if ((len (self.settings['jas_memcache']) > 0) and ((self.settings['jas_memcache_files']) or (self.settings['jas_memcache_merged_xml_files']))):
		#
			f_cache_file = "{0}/{1}.{2}".format (self.settings['jas_memcache'],self.settings['jas_memcache_id'],(self.md5 ("{0}.{1}".format (path.dirname (file_pathname),(path.basename (file_pathname))))))
			if (path.exists (f_cache_file)): os.unlink (f_cache_file)
		#

		return direct_file_functions.file_write (data,file_pathname,file_type)
	#
*/
	// settingsGet (String fFilePathname,boolean fRequired = false,boolean fUseCache = true)
/**
	* Reads settings from file (XML-encoded) and adds them to direct_settings.
	*
	* @param  file_pathname The file containing settings
	* @param  required If the file is required (true) but does not exist,
	*         an OSError exception is raised.
	* @param  use_cache False to read a settings file even if it has already
	*         been parsed.
	* @return (bool) True on success; false on error
	* @since  v0.1.00
*/
	public boolean settingsGet (String fFilePathname) throws FileNotFoundException { return settingsGet (fFilePathname,false,true); }
	public boolean settingsGet (String fFilePathname,boolean fRequired) throws FileNotFoundException { return settingsGet (fFilePathname,fRequired,true); }
	public synchronized boolean settingsGet (String fFilePathname,boolean fRequired,boolean fUseCache) throws FileNotFoundException
	{
		if (Debug != null) { Debug.add ("basic_functions_class.settingsGet (" + fFilePathname + ",fRequired,fUseCache)"); }
		boolean fReturn = false;

		String fFilePathnameMD5 = md5 (fFilePathname);
		boolean fContinueCheck = ((fFilePathnameMD5 == null) ? false : true);

		if ((fUseCache)&&(SettingsCache.keyExists (fFilePathnameMD5)))
		{
			fReturn = true;
			fContinueCheck = false;
		}

		if (fContinueCheck)
		{
			directArray fXmlArray = memcacheGetFileMergedXml (fFilePathname);

			if (fXmlArray != null)
			{
				Pattern fReKeyReplace = Pattern.compile ("jas_settings_file_v(\\d+)_",Pattern.CASE_INSENSITIVE);
				SettingsCache.keyAdd (fFilePathnameMD5,fFilePathname);

				String fIteratorKey;
				directArray fIteratorValueArray;
				String fIteratorValueString;
				directArray fIteratorValue;
				Vector fSettingList;

				for (Iterator fIterator = fXmlArray.keySet().iterator ();fIterator.hasNext ();)
				{
					fIteratorKey = (String)fIterator.next ();
					fIteratorValue = fXmlArray.valueGetArray (fIteratorKey);

					if (fIteratorValue.keyExists ("tag"))
					{
						fIteratorValueString = (String)fIteratorValue.valueGet ("value");

						if (fIteratorValueString != null)
						{
							fIteratorKey = fReKeyReplace.matcher(fIteratorKey).replaceAll ("");
							if ((!Settings.keyExists (fIteratorKey))||(fIteratorValueString.length () > 0)) { Settings.valueSet (fIteratorKey,fIteratorValueString,true); }
						}
					}
					else if ((fIteratorValue.size () > 0)&&(fIteratorValue.keyExists (0))&&(fIteratorValue.valueGetArray(0).keyExists ("tag")))
					{
						fIteratorKey = (String)fIterator.next ();
						fSettingList = new Vector ();

						for (Iterator fIteratorValueIterator = fIteratorValue.values().iterator ();fIteratorValueIterator.hasNext ();)
						{
							fIteratorValueArray = (directArray)fIteratorValueIterator.next ();
							fIteratorValueString = (String)fIteratorValueArray.valueGet ("value");
							fSettingList.add (fIteratorValueString);
						}

						Settings.valueSet (fIteratorKey,fSettingList,true);
					}
				}

				fReturn = true;
			}
			else if (fRequired) { throw new FileNotFoundException ("The system could not load a required component.\n\n\"" + fFilePathname + "\" was not found"); }
		}

		return fReturn;
	}
/*
	def settings_write (self,settings,file_pathname):
	#
		"""
Writes the setting array to a file (XML-encoded).

@param  settings Settings array
@param  file_pathname The file containing settings
@return (bool) True on success; false on error
@since  v0.1.00
		"""

		file_pathname = direct_str (file_pathname)

		if (self.debug != None): self.debug.append ("#echo(__FILEPATH__)# -basic_functions_class.settings_write (settings,{0})- (#echo(__LINE__)#)".format (file_pathname))
		f_return = False

		f_xml_object = direct_xml.py_get ()

		if ((type (settings) == dict) and (f_xml_object != None)):
		#
			f_xml_object.node_add ("jas_settings_file_v1","",{ "xmlns": "urn:de.direct-netware.xmlns:jas.settings.v1" })

			for f_setting_key in settings:
			#
				f_setting_key = direct_str (f_setting_key)
				f_setting_value = direct_str (settings[f_setting_key])
				f_xml_object.node_add (("jas_settings_file_v1 {0}".format (f_setting_key.replace ("_"," "))),f_setting_value,{ "xml:space": "preserve" })
			#

			f_return = self.memcache_write_file (f_xml_object.cache_export (),file_pathname)
		#

		return f_return
	#
*/
/**
	* This function provides access to system wide information saved in variables.
	*
	* SECURITY WARNING: Do NOT use this function in areas where user has access to
	* via input fields or where user defined data is shown!
	*
	* @param  string $f_data Input string
	* @param  string $f_type Access variables of the given type. Available options
	*         are: "cachedata", "local", "output", "settings"
	* @uses   direct_debug()
	* @uses   USE_debug_reporting
	* @return string Filtered string
	* @since  v0.1.03
*/
	public String varfilter (String fData,String fType)
	{
		if (Debug != null) { Debug.add ("basic_functions_class.varfilter (fData," + fType + ")"); }

		if (fType.equals ("local")) { fData = varfilterLocal (fData); }
		else if (fType.equals ("settings")) { fData = varfilter (fData,directSettings.getInstance ()); }

		return fData;
	}

	public static String base64Decode (String fData) { return directBase64.decode (fData); }
	public static String base64Decode (String fData,String fCharset) throws UnsupportedEncodingException { return directBase64.decode (fData,fCharset); }
	public static String base64Encode (byte[] fData) { return directBase64.encode (fData); }
	public static String base64Encode (String fData) { return directBase64.encode (fData); }

	public static String byteGetHex (byte[] Bytes)
	{
		String fReturn = ""; 
		char[] fHexChars = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' };

		for (int i = 0;i < Bytes.length;i++)
		{
			fReturn += fHexChars[(Bytes[i] & 0xF0) >> 4];
			fReturn += fHexChars[Bytes[i] & 0x0F];
		}

		return fReturn;
	}

	public static synchronized directBasicFunctions getInstance ()
	{
		directBasicFunctions fReturn = (directBasicFunctions)directGlobals.getInstance ("basic_functions");

		if (fReturn == null)
		{
			fReturn = new directBasicFunctions ();

			directGlobals.setInstance ("basic_functions",fReturn);
			fReturn.init ();
		}

		return fReturn;
	}

	public static byte[] hexGetByte (String Hex)
	{
		ByteBuffer fByteBuffer = ByteBuffer.allocate (Hex.length () / 2);

		while (Hex.length() > 1)
		{
			fByteBuffer.put (Integer.getInteger(Hex.subSequence(0,2).toString (),16).byteValue ());
			Hex = Hex.substring (2);
		}

		return fByteBuffer.array ();
	}

	public static byte[] md5 (byte[] fData)
	{
		byte[] fReturn = null;

		try
		{
			com.twmacinta.util.MD5 fMD5 = new com.twmacinta.util.MD5 ();
			fMD5.Update (fData);
			fReturn = fMD5.Final ();
		}
		catch (Throwable fUnhandledException) { }

		if (fReturn == null)
		{
			try
			{
				MessageDigest fMD5 = MessageDigest.getInstance ("MD5");
				fReturn = fMD5.digest (fData);
			}
			catch (NoSuchAlgorithmException fUnhandledException) { }
		}

		return fReturn;
	}

	public static String md5 (String fData) { return byteGetHex (md5 (fData.getBytes ())); }

/**
	* This function provides access to system wide information saved in variables.
	*
	* SECURITY WARNING: Do NOT use this function in areas where user has access to
	* via input fields or where user defined data is shown!
	*
	* @param  string $f_data Input string
	* @param  string $f_type Access variables of the given type. Available options
	*         are: "cachedata", "local", "output", "settings"
	* @uses   direct_debug()
	* @uses   USE_debug_reporting
	* @return string Filtered string
	* @since  v0.1.03
*/
	public static String varfilter (String fData,directArray fVariables)
	{
		Matcher fResultObject = Pattern.compile("\\{var:(\\w+)\\}",Pattern.CASE_INSENSITIVE).matcher (fData);

		if (fResultObject.find ())
		{
			Pattern fReObject = Pattern.compile ("\\W+");
			String fVar;

			do
			{
				fVar = fReObject.matcher(fResultObject.group (1)).replaceAll ("");
				if (fVar.length () > 0) { fData = fData.replaceFirst (Pattern.quote (fResultObject.group (0)),((String)fVariables.valueGet (fResultObject.group (1),("{var:" + fResultObject.group (1) + "}")))); }
			}
			while (fResultObject.find ());
		}

		return fData;
	}

/**
	* This function provides access to system wide information saved in variables.
	*
	* SECURITY WARNING: Do NOT use this function in areas where user has access to
	* via input fields or where user defined data is shown!
	*
	* @param  string $f_data Input string
	* @param  string $f_type Access variables of the given type. Available options
	*         are: "cachedata", "local", "output", "settings"
	* @uses   direct_debug()
	* @uses   USE_debug_reporting
	* @return string Filtered string
	* @since  v0.1.03
*/
	public static String varfilterLocal (String fData)
	{
		Matcher fResultObject = Pattern.compile("\\{var:(\\w+)\\}",Pattern.CASE_INSENSITIVE).matcher (fData);

		if (fResultObject.find ())
		{
			Pattern fReObject = Pattern.compile ("\\W+");
			String fVar;

			do
			{
				fVar = fReObject.matcher(fResultObject.group (1)).replaceAll ("");
				if (fVar.length () > 0) { fData = fData.replaceFirst (Pattern.quote (fResultObject.group (0)),(directLocal.get (fResultObject.group (1)))); }
			}
			while (fResultObject.find ());
		}

		return fData;
	}
}

//j// EOF