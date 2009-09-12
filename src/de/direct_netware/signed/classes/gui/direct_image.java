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
* Enhanced version of JLabel for adding an image.
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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.Border;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class direct_image extends JLabel
{
	public static final long serialVersionUID = -350562427510472415L;

	public static final int TYPE_ICON = 0;
	public static final int TYPE_IMAGE = 1;
	public static final int TYPE_UNDEFINED = 2;

	protected Icon icon;
	protected ImageIcon image;
	protected int type = TYPE_UNDEFINED;

	public direct_image () { super ("<html><body><br></body></html>"); }

	public direct_image (Border f_border)
	{
		super ("<html><body><br></body></html>");
		setBorder (f_border);
	}

	public direct_image (Icon f_icon)
	{
		super (f_icon);
		icon = f_icon;
		type = TYPE_ICON;
	}

	public direct_image (Image f_image)
	{
		super ();
		image = new ImageIcon (f_image);
		type = TYPE_IMAGE;
		setIcon (image);
	}

	public direct_image (Icon f_icon,Border f_border)
	{
		super (f_icon);
		icon = f_icon;
		type = TYPE_ICON;
		setBorder (f_border);
	}

	public direct_image (Image f_image,Border f_border)
	{
		super ();
		image = new ImageIcon (f_image);
		type = TYPE_IMAGE;
		setBorder (f_border);
		setIcon (image);
	}

	public direct_image (Icon f_icon,int f_orientation)
	{
		super (f_icon,f_orientation);
		icon = f_icon;
		type = TYPE_ICON;
	}

	public direct_image (Image f_image,int f_orientation)
	{
		super ((new ImageIcon (f_image)),f_orientation);
		image = (ImageIcon)getIcon ();
		type = TYPE_IMAGE;
	}

	public direct_image (Icon f_icon,int f_orientation,Border f_border)
	{
		super (f_icon,f_orientation);
		icon = f_icon;
		type = TYPE_ICON;
		setBorder (f_border);
	}

	public direct_image (Image f_image,int f_orientation,Border f_border)
	{
		super ((new ImageIcon (f_image)),f_orientation);
		image = (ImageIcon)getIcon ();
		type = TYPE_IMAGE;
		setBorder (f_border);
	}

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}

	protected void init (Icon f_icon,int f_orientation)
	{
		icon = f_icon;
		type = TYPE_ICON;

		if (f_icon != null)
		{
			int f_padding = UIManager.getInt ("TabbedPane.textIconGap");

			if (f_padding > 0) { f_padding *= 5; }
			else { f_padding = 15; }

			setPreferredSize (new Dimension ((f_icon.getIconWidth () + f_padding),(f_icon.getIconHeight () + f_padding)));
		}
	}

	protected void init (ImageIcon f_image,int f_orientation)
	{
		image = f_image;
		type = TYPE_IMAGE;

		if (f_image != null)
		{
			int f_padding = UIManager.getInt ("TabbedPane.textIconGap");

			if (f_padding > 0) { f_padding *= 5; }
			else { f_padding = 15; }

			setPreferredSize (new Dimension ((f_image.getIconWidth () + f_padding),(f_image.getIconHeight () + f_padding)));
		}
	}
}

//j// EOF