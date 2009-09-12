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
* This interface defines all required basic methods for JAS enabled
* applications.
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

package de.direct_netware.signed.loader;
import de.direct_netware.signed.direct_object;
import de.direct_netware.signed.classes.direct_local;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.classes.gui.direct_window;

import java.awt.Component;
import java.net.URL;

public interface direct_main_window extends direct_object
{
	public abstract direct_container get ();
	public abstract URL[] get_download_classes_urls ();
	public abstract direct_local get_local ();
	public abstract String get_error_last ();
	public abstract URL get_resource (String f_file_classpath);
	public abstract Object get_main ();
	public abstract Object get_parent ();
	public abstract void set (Component f_component);
	public abstract void set_error_last (String f_error);
	public abstract void set_window (direct_window f_window);
}

//j// EOF