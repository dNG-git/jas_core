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
* de.direct_netware.signed.ui.iclient.action.direct_iclient_ui
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
import de.direct_netware.signed.ui.direct_iclient_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class direct_iclient_action implements ActionListener
{
	protected static final int BUTTON_CLOSE = 0;
	protected static final int BUTTON_INSTALL = 1;
	protected static final int BUTTON_UNDEFINED = 2;

	protected int button = BUTTON_UNDEFINED;
	protected direct_iclient_ui iclient;

	public direct_iclient_action (direct_iclient_ui f_iclient,String f_id)
	{
		iclient = f_iclient;

		if (f_id.equals ("direct_iclient_button_close")) { button = BUTTON_CLOSE; }
		else if (f_id.equals ("direct_iclient_button_install")) { button = BUTTON_INSTALL; }
	}

	public void actionPerformed (ActionEvent f_event)
	{
		switch (button)
		{
		case BUTTON_CLOSE:
		{
			direct_dispatcher.dispatch (new direct_iclient_ui (iclient,direct_iclient_ui.UIMODE_FINALIZE));
			break;
		}
		case BUTTON_INSTALL:
		{
			direct_dispatcher.dispatch (new direct_iclient_ui (iclient,direct_iclient_ui.UIMODE_INSTALL));
			break;
		}
		}
	}
}

//j// EOF