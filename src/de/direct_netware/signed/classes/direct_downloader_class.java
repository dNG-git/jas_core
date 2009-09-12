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
* Enhanced version of URLClassLoader. It's based on a post about
* "getSystemClassLoader ()" by FBL at http://forum.java.sun.com.
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

package de.direct_netware.signed.classes;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Method;
import java.lang.SecurityException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.prefs.Preferences;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class direct_downloader_class implements PrivilegedExceptionAction
{
	protected ClassLoader classloader;
	protected String classname;
	protected URL[] urls;

	public direct_downloader_class (URL[] f_urls,String f_classname) throws SecurityException
	{
		super ();
		classloader = ClassLoader.getSystemClassLoader ();
		classname = f_classname;
		urls = f_urls;
	}

	public direct_downloader_class (URL[] f_urls,String f_classname,ClassLoader f_classloader_parent) throws SecurityException
	{
		super ();
		classloader = f_classloader_parent;
		classname = f_classname;
		urls = f_urls;
	}

	public Object run () throws PrivilegedActionException
	{
		boolean f_return = true;

		if (classloader instanceof URLClassLoader)
		{
			try
			{
				Method f_addURL_method = URLClassLoader.class.getDeclaredMethod ("addURL",(new Class[] { URL.class }));
				f_addURL_method.setAccessible (true);
				int f_urls_count = urls.length;

				for (int f_i = 0;f_i < f_urls_count;f_i++) { f_addURL_method.invoke (classloader,(new Object[] { urls[f_i] })); }

				if (classname.indexOf (".jar:") > -1)
				{
					Preferences f_preferences = Preferences.userNodeForPackage (de.direct_netware.signed.direct_object.class);
					String f_iclient_path = f_preferences.get ("de.direct-netware.iclient.codebase",null);
					String[] f_iclient_request = classname.split (".jar:",2);

					String f_separator = System.getProperty ("file.separator");

					if ((f_iclient_path == null)||(f_separator == null)) { classloader.loadClass (f_iclient_request[1]); }
					else
					{
						if (f_iclient_request.length == 2)
						{
							f_addURL_method.invoke (classloader,(new Object[] { new URL ("file://" + f_iclient_path + f_separator + f_iclient_request[0] + ".jar") }));
							classloader.loadClass (f_iclient_request[1]);
						}
						else { f_return = false; }
					}
				}
				else
				{
					classloader.loadClass (classname);
					f_return = true;
				}
			}
			catch (Throwable f_handled_exception) { f_return = false; }
		}

		if (!f_return) { throw new PrivilegedActionException (new ClassNotFoundException ()); }
		return null;
	}
}

//j// EOF