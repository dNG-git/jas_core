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
* de.direct_netware.signed.loader.directCls
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

package de.direct_netware.loader;

import java.util.Iterator;
import java.util.Vector;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import de.direct_netware.classes.directDebug;
import de.direct_netware.classes.directGlobals;
import de.direct_netware.classes.exception.dNGException;

public class directCls
{
	protected static Vector directClsExitCallbacks = new Vector ();
	protected static Vector directClsRunCallbacks = new Vector ();
	protected static directCls directClsInstance;
/**
	* Debug message container
*/
	protected directDebug Debug;
	protected directClsRunnable Mainloop;
	protected OptionParser OptionParser;
	protected Thread ShutdownHook;

/* -------------------------------------------------------------------------
Construct the class
------------------------------------------------------------------------- */

/**
	* Constructor directCls
	*
	* @since v0.1.00
*/
	public directCls ()
	{
		Debug = (directDebug)directGlobals.getInstance ("debug");
		OptionParser = new OptionParser ();

		if (Debug != null) { Debug.add ("cls_handler.directCls (new)"); }
		directClsInstance = this;
	}

/**
	Prints the stack trace on this error event.

	@param py_exception Inner exception
	@since v0.1.00
*/
	public void error (Throwable fException)
	{
		dNGException.printCurrentStackTrace (fException,System.err);
		if (Debug != null) { System.err.print (Debug); }
	}

/**
	Executes registered callbacks before exiting this application.

	@since v0.1.00
*/
	public void exit () throws Throwable { exit (null); }
	public void exit (Throwable fException) throws Throwable
	{
		if (Debug != null) { Debug.add ("cls_handler.exit ()"); }
		directClsRunnable fRunnable;

		for (Iterator fIterator = directClsExitCallbacks.iterator ();fIterator.hasNext ();)
		{
			fRunnable = (directClsRunnable)fIterator.next ();
			fRunnable.call (directClsRunnable.MODE_EXIT);
		}

		if (ShutdownHook != null) { Runtime.getRuntime().removeShutdownHook (ShutdownHook); }

		if (fException == null) { System.exit (0); }
		else
		{
			dNGException.printCurrentStackTrace (fException,System.err);
			System.exit (125);
		}
	}

/**
Executes registered callbacks for the active application.

@since v0.1.00
*/
	public synchronized void run (String[] fArgs) throws Throwable
	{
		if (Debug != null) { Debug.add ("cls_handler.run (fArgs)"); }
		ShutdownHook = new Thread ()
		{
			public void run ()
			{
				try { exit (); }
				catch (Throwable fUnhandledException) { }
			}
		};

		Runtime.getRuntime().addShutdownHook (ShutdownHook);

		try
		{
			OptionSet fOptions = OptionParser.parse (fArgs);
			directClsRunnable fRunnable;

			for (Iterator fIterator = directClsRunCallbacks.iterator ();fIterator.hasNext ();)
			{
				fRunnable = (directClsRunnable)fIterator.next ();
				fRunnable.setOptions (fOptions);
				fRunnable.call (directClsRunnable.MODE_RUN);
			}

			OptionParser = null;
		}
		catch (Throwable fHandledException) { exit (fHandledException); }

		ThreadGroup fThreadGroup = null;
		ThreadGroup fMainThreadGroup;

		if (Mainloop == null)
		{
			fThreadGroup = Thread.currentThread().getThreadGroup ();
			fMainThreadGroup = fThreadGroup.getParent ();

			while (fMainThreadGroup != null )
			{
				fThreadGroup = fMainThreadGroup;
				fMainThreadGroup = fThreadGroup.getParent ();
			}
		}

		try
		{
			notifyAll ();

			if (Mainloop == null)
			{
				boolean fActives;
				Thread fMainThread = Thread.currentThread ();
				Thread[] fThreads;

				do
				{
					fActives = false;
					fThreads = new Thread[(fThreadGroup.activeCount ())];
					fThreadGroup.enumerate (fThreads);

					if (fThreads != null)
					{
						try
						{
							for (int fI = 0;fI < fThreads.length;fI++)
							{
								if ((fThreads[fI] != null)&&(!fMainThread.equals (fThreads[fI]))&&(fThreads[fI].isAlive ())&&(!fThreads[fI].isDaemon ())) { fThreads[fI].join (); }
							}
						}
						catch (Throwable fHandledException) { fActives = true; }
					}

					if (fActives) { Thread.sleep (50); }
				}
				while (fActives);
			}
			else { Mainloop.call (directClsRunnable.MODE_MAIN); }
		}
		catch (Throwable fHandledException) { error (fHandledException); }

		exit ();
	}

/**
	Register a callback for the application main loop.

	@param py_function Python callback
	@since v0.1.00
*/
	public void setMainloop (directClsRunnable fRunnable) throws RuntimeException
	{
		if (Debug != null) { Debug.add ("cls_handler.set_mainloop (fRunnable)"); }

		if (Mainloop == null) { Mainloop = fRunnable; }
		else { throw new RuntimeException ("Main loop already registered"); }
	}

/* -------------------------------------------------------------------------
Static methods
------------------------------------------------------------------------- */

/**
	Register a callback for the application exit event.

	@param py_function Python callback
	@since v0.1.00
*/
	public static void registerExitCallback (directClsRunnable fRunnable) { directClsExitCallbacks.add (fRunnable); }

/**
	Register a callback for the application main loop.

	@param py_function Python callback
	@since v0.1.00
*/
	public static void registerMainloop (directClsRunnable fRunnable) throws RuntimeException
	{
		if (directClsInstance == null) { throw new RuntimeException ("directCls not initialized"); }
		else { directClsInstance.setMainloop (fRunnable); }
	}

/**
	Register a callback for the application activation event.

	@param py_function Python callback
	@since v0.1.00
*/
	public static void registerRunCallback (directClsRunnable fRunnable) { directClsRunCallbacks.add (fRunnable); }
}

//j// EOF