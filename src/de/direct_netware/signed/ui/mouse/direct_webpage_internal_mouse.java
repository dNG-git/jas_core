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
* de.direct_netware.signed.ui.action.direct_webpage_internal_action
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

package de.direct_netware.signed.ui.mouse;
import de.direct_netware.signed.classes.gui.direct_input;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.direct_webpage_internal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class direct_webpage_internal_mouse extends MouseAdapter
{
	protected direct_input input;
	protected direct_webpage_internal webpage_internal;

	public direct_webpage_internal_mouse (direct_webpage_internal f_webpage_internal,direct_input f_input)
	{
		webpage_internal = f_webpage_internal;
		input = f_input;
	}

	public void mouseClicked (MouseEvent f_event)
	{
		if (f_event.getClickCount () > 1)
		{
			f_event.consume ();
			direct_dispatcher.dispatch (new direct_webpage_internal (webpage_internal,input));
		}
	}
}

//j// EOF