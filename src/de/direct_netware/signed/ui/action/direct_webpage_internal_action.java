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

package de.direct_netware.signed.ui.action;
import de.direct_netware.signed.thread.direct_dispatcher;
import de.direct_netware.signed.ui.direct_webpage_internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class direct_webpage_internal_action implements ActionListener
{
	protected static final int BUTTON_CLOSE = 0;
	protected static final int BUTTON_DETAILS_SHOW = 1;
	protected static final int BUTTON_DETAILS_HIDE = 2;
	protected static final int BUTTON_UNDEFINED = 3;

	protected int button = BUTTON_UNDEFINED;
	protected direct_webpage_internal webpage_internal;

	public direct_webpage_internal_action (direct_webpage_internal f_webpage_internal,String f_id)
	{
		webpage_internal = f_webpage_internal;

		if (f_id.equals ("direct_webpage_internal_button_close")) { button = BUTTON_CLOSE; }
		else if (f_id.equals ("direct_webpage_internal_button_webpage_show")) { button = BUTTON_DETAILS_SHOW; }
		else if (f_id.equals ("direct_webpage_internal_button_webpage_hide")) { button = BUTTON_DETAILS_HIDE; }
	}

	public void actionPerformed (ActionEvent f_event)
	{
		switch (button)
		{
		case BUTTON_CLOSE:
		{
			direct_dispatcher.dispatch (new direct_webpage_internal (webpage_internal,direct_webpage_internal.UIMODE_FINALIZE));
			break;
		}
		case BUTTON_DETAILS_HIDE:
		{
			direct_dispatcher.dispatch (new direct_webpage_internal (webpage_internal,direct_webpage_internal.UIMODE_PAGE_HIDE));
			break;
		}
		case BUTTON_DETAILS_SHOW:
		{
			direct_dispatcher.dispatch (new direct_webpage_internal (webpage_internal,direct_webpage_internal.UIMODE_PAGE_SHOW_INIT));
			break;
		}
		}
	}
}

//j// EOF