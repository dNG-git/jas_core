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
* Enhanced version of JFrame.
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
import de.direct_netware.signed.classes.gui.window.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class direct_window extends JFrame implements ComponentListener
{
	public static final long serialVersionUID = 2936873883631955903L;

	protected Component component_parent;

	public direct_window ()
	{
		super ();
		init ("Untitled",true);
	}

	public direct_window (Component f_component)
	{
		super ();
		component_parent = f_component;
		init ("Untitled",false);
	}

	public direct_window (String f_title)
	{
		super ();
		init (f_title,true);
	}

	public direct_window (String f_title,Component f_component)
	{
		super ();
		component_parent = f_component;
		init (f_title,false);
	}

	public void componentHidden (ComponentEvent f_event) { }

	public void componentMoved (ComponentEvent f_event)
	{
		if (component_parent != null) { update_position (); }
	}

	public void componentResized (ComponentEvent f_event) { }
	public void componentShown (ComponentEvent f_event) { }

	public direct_container get () { return new direct_container (getContentPane ()); }

	protected void init (String f_title,boolean f_interactive)
	{
		new direct_init (this,f_title,f_interactive);

		if (component_parent != null)
		{
			component_parent.addComponentListener (this);
			update_position ();
		}
	}

	public void update_position ()
	{
		Dimension f_screen = getToolkit().getScreenSize ();
		int f_width_screen = (int)Math.ceil (f_screen.getWidth ());
		int f_height_screen = (int)Math.ceil (f_screen.getHeight ());
		int f_top = (int)Math.ceil ((f_height_screen) / 2);
		int f_left = (int)Math.ceil ((f_width_screen) / 2);
		f_screen = null;

		if (component_parent.isVisible ())
		{
			Point f_position_parent = component_parent.getLocationOnScreen ();
			f_top = (int)Math.ceil (((component_parent.getHeight ()) / 2) + (f_position_parent.getY ()));
			f_left = (int)Math.ceil (((component_parent.getWidth ()) / 2) + (f_position_parent.getX ()));
		}
		else
		{
			Point f_position_parent = new Point ((component_parent.getX ()),(component_parent.getY ()));

			if ((component_parent.getParent ()) != null)
			{
				SwingUtilities.convertPointToScreen (f_position_parent,(component_parent.getParent ()));
				f_top = (int)Math.ceil (((component_parent.getHeight ()) / 2) + (f_position_parent.getY ()));
				f_left = (int)Math.ceil (((component_parent.getWidth ()) / 2) + (f_position_parent.getX ()));
			}
		}

		int f_width = getWidth ();
		int f_width_center = (int)Math.ceil (f_width / 2);

		if (f_left - f_width_center >= 0)
		{
			if (f_left + f_width < f_width_screen) { f_left -= f_width_center; }
			else { f_left -= f_width; }
		}
		else { f_left = 0; }

		int f_height = getHeight ();
		int f_height_center = (int)Math.ceil (f_height / 2);

		if (f_top - f_height_center >= 0)
		{
			if (f_top + f_width < f_height_screen) { f_top -= f_height_center; }
			else { f_top -= f_height; }
		}
		else { f_top = 0; }

		setLocation (f_left,f_top);
	}

	public void resizePack ()
	{
		new direct_resizePack (this);
		if (component_parent != null) { update_position (); }
	}

	public void resizePack (int f_width,int f_height) { resizePack (f_width,f_height,0,0); }
	public void resizePack (int f_width,int f_height,int f_width_additional,int f_height_additional) { resizePack (f_width,f_height,f_width_additional,f_height_additional,false); }

	public void resizePack (int f_width,int f_height,int f_width_additional,int f_height_additional,boolean f_forced)
	{
		new direct_resizePack (this,f_width,f_height,f_width_additional,f_height_additional,f_forced);
		if (component_parent != null) { update_position (); }
	}

	public void set (Container f_container) { set (f_container,false); }
	public void set (Container f_container,boolean f_pack) { new direct_set (this,f_container,f_pack); }

	public void setResize (Container f_field_container,int f_width,int f_height) { setResize (f_field_container,f_width,f_height,0,0); }

	public void setResize (Container f_field_container,int f_width,int f_height,int f_width_additional,int f_height_additional)
	{
		set (f_field_container,false);
		resizePack (f_width,f_height,f_width_additional,f_height_additional,true);
	}

	public void setResizePack (Container f_field_container,int f_width,int f_height) { setResizePack (f_field_container,f_width,f_height,0,0); }

	public void setResizePack (Container f_field_container,int f_width,int f_height,int f_width_additional,int f_height_additional)
	{
		set (f_field_container,false);
		resizePack (f_width,f_height,f_width_additional,f_height_additional);
	}
}

//j// EOF