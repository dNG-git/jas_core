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
* This class dispatches GUI work to the corresponding object.
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

package de.direct_netware.signed.thread;
import de.direct_netware.signed.ui.direct_swing;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

public class direct_dispatcher
{
	protected Object thread_result;
	protected boolean thread_result_available;

	protected direct_dispatcher ()
	{
		thread_result = null;
		thread_result_available = false;
	}

	public static void dispatch (Runnable f_runnable) { dispatch (new Thread (f_runnable)); }

	public static void dispatch (Thread f_thread)
	{
		if (f_thread instanceof direct_swing)
		{
			if (SwingUtilities.isEventDispatchThread ()) { f_thread.run (); }
			else { SwingUtilities.invokeLater (f_thread); }
		}
		else { f_thread.start (); }
	}

	public static Object execute (Thread f_thread) throws InterruptedException,InvocationTargetException
	{
		direct_dispatcher f_dispatcher = new direct_dispatcher ();
		direct_thread f_thread_dispatched = null;

		if (f_thread instanceof direct_swing)
		{
			f_thread_dispatched = (direct_thread)f_thread;
			f_thread_dispatched.set_dispatcher (f_dispatcher);

			if (f_thread_dispatched instanceof direct_swing)
			{
				if (SwingUtilities.isEventDispatchThread ()) { f_thread_dispatched.run (); }
				else { SwingUtilities.invokeAndWait (f_thread_dispatched); }
			}
			else
			{
				f_thread_dispatched.start ();
				f_thread_dispatched.join ();
			}

			return f_dispatcher.get_result ();
		}
		else { return null; }
	}

	public void finalize () { thread_result = null; }

	public synchronized Object get_result () { return thread_result; }

	public synchronized void set_result (Object f_result)
	{
		thread_result = f_result;
		thread_result_available = true;
	}
}

//j// EOF