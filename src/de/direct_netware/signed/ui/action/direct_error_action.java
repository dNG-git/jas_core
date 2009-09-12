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
* de.direct_netware.signed.ui.action.direct_error_action
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
import de.direct_netware.signed.ui.direct_error_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class direct_error_action implements ActionListener
{
	protected static final int BUTTON_CLOSE = 0;
	protected static final int BUTTON_DETAILS_SHOW = 1;
	protected static final int BUTTON_DETAILS_HIDE = 2;
	protected static final int BUTTON_UNDEFINED = 3;

	protected int button = BUTTON_UNDEFINED;
	protected direct_error_ui error;

	public direct_error_action (direct_error_ui f_error,String f_id)
	{
		error = f_error;

		if (f_id.equals ("direct_error_button_close")) { button = BUTTON_CLOSE; }
		else if (f_id.equals ("direct_error_button_error_details_show")) { button = BUTTON_DETAILS_SHOW; }
		else if (f_id.equals ("direct_error_button_error_details_hide")) { button = BUTTON_DETAILS_HIDE; }
	}

	public void actionPerformed (ActionEvent f_event)
	{
		switch (button)
		{
		case BUTTON_CLOSE:
		{
			direct_dispatcher.dispatch (new direct_error_ui (error,direct_error_ui.UIMODE_FINALIZE));
			break;
		}
		case BUTTON_DETAILS_HIDE:
		{
			direct_dispatcher.dispatch (new direct_error_ui (error,direct_error_ui.UIMODE_DETAILS_HIDE));
			break;
		}
		case BUTTON_DETAILS_SHOW:
		{
			direct_dispatcher.dispatch (new direct_error_ui (error,direct_error_ui.UIMODE_DETAILS_SHOW));
			break;
		}
		}
	}
}

//j// EOF