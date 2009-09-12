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
* Enhanced version for adding an image in a container.
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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class direct_panel extends JPanel
{
	public static final long serialVersionUID = 1176007864222001176L;

	public direct_panel () { this ((new FlowLayout ()),false,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }
	public direct_panel (Border f_border) { this ((new FlowLayout ()),false,f_border); }
	public direct_panel (Icon f_icon) { this (f_icon,JLabel.CENTER,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }
	public direct_panel (Icon f_icon,Border f_border) { this (f_icon,JLabel.CENTER,f_border); }
	public direct_panel (Icon f_icon,int f_orientation) { this (f_icon,f_orientation,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_panel (Icon f_icon,int f_orientation,Border f_border)
	{
		super ();

		setBackground (new Color (255,255,255));
		setLayout (new GridLayout (1,1));
		add (new direct_image (f_icon,f_orientation));
		setBorder (f_border);
	}

	public direct_panel (direct_icon_painter f_image_icon) { this (f_image_icon,JLabel.CENTER,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }
	public direct_panel (direct_icon_painter f_image_icon,Border f_border) { this (f_image_icon,JLabel.CENTER,f_border); }
	public direct_panel (direct_icon_painter f_image_icon,int f_orientation) { this (f_image_icon,f_orientation,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_panel (direct_icon_painter f_image_icon,int f_orientation,Border f_border)
	{
		super ();

		setBackground (new Color (255,255,255));
		setLayout (new GridLayout (1,1));
		add (new direct_image ((f_image_icon.get_icon ()),f_orientation));
		setBorder (f_border);
	}

	public direct_panel (LayoutManager f_layout) { this (f_layout,false,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }
	public direct_panel (LayoutManager f_layout,Border f_border) { this (f_layout,false,f_border); }
	public direct_panel (LayoutManager f_layout,boolean f_double_buffered) { this (f_layout,f_double_buffered,(BorderFactory.createEtchedBorder (EtchedBorder.LOWERED))); }

	public direct_panel (LayoutManager f_layout,boolean f_double_buffered,Border f_border)
	{
		super (f_layout,f_double_buffered);

		setBackground (new Color (255,255,255));
		setBorder (f_border);
	}

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}
}

//j// EOF