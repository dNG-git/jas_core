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
* Enhanced version of Container.
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

package de.direct_netware.signed.classes.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

public class direct_container extends Container
{
	public static final long serialVersionUID = 1626380973660778284L;

	protected Container container;

	public direct_container ()
	{
		super ();
		container = this;
	}

	public direct_container (Component f_component)
	{
		if (f_component instanceof Container) { container = (Container)f_component; }
		else
		{
			Container f_container = new Container ();
			f_container.add (f_component);
			container = f_container;
		}
	}

	public direct_container (Container f_container) { container = f_container; }

	public Container getManaged () { return container; }

	public void set (Component f_component)
	{
		container.removeAll ();
		container.setLayout (new GridLayout (1,1));
		container.add (f_component);
		container.validate ();
	}

	public void setManaged (Container f_container) { container = f_container; }
}

//j// EOF