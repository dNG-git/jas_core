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
* Creates a download thread to load classes dynamically when needed.
* Switches to a given thread when finished or on error.
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

package de.direct_netware.signed.classes.thread;
import de.direct_netware.signed.classes.gui.direct_container;
import de.direct_netware.signed.thread.direct_thread;

import javax.swing.JProgressBar;

public interface direct_loader extends direct_thread
{
	public abstract direct_container get_downloader_container ();
	public abstract JProgressBar get_downloader_progressbar ();
	public abstract double get_downloader_steps ();
	public abstract void set_downloader_progressbar (JProgressBar f_progressbar);
}

//j// EOF