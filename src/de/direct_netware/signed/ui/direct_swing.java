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
* This interface identifies Swing objects (that need to dispatched to the
* Event Dispatching Thread).
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

package de.direct_netware.signed.ui;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.thread.direct_thread;

public abstract class direct_swing extends Thread implements direct_thread
{
	protected direct_dispatcher dispatcher;

	public void finalize ()
	{
		dispatcher = null;

		try { super.finalize (); }
		catch (Throwable f_unhandled_exception) { }
	}

	public synchronized void set_dispatcher (direct_dispatcher f_dispatcher) { dispatcher = f_dispatcher; }

	protected void set_result (Object f_result)
	{
		if (dispatcher != null) { dispatcher.set_result (f_result); }
	}
}

//j// EOF