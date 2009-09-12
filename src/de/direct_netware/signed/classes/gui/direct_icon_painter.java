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
* de.direct_netware.signed.ui.java2d.direct_icon_painter
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class direct_icon_painter extends BufferedImage
{
	protected Graphics2D g2d;

	public direct_icon_painter (int f_width,int f_height) { super (f_width,f_height,BufferedImage.TYPE_4BYTE_ABGR); }

	public ImageIcon get_icon () { return new ImageIcon (this); }

	public Graphics2D get_g2d ()
	{
		if (g2d == null)
		{
			g2d = createGraphics ();
			return g2d;
		}
		else { return g2d; }
	}
}

//j// EOF