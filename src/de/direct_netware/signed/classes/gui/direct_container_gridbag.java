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
* Enhanced version of Container with a gridbag LayoutManager.
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class direct_container_gridbag extends direct_container
{
	public static final long serialVersionUID = 387543313275991524L;

	public static final int BOTH = GridBagConstraints.BOTH;
	public static final int CENTER = GridBagConstraints.CENTER;
	public static final int EAST = GridBagConstraints.EAST;
	public static final int FIRST_LINE_END = GridBagConstraints.FIRST_LINE_END;
	public static final int FIRST_LINE_START = GridBagConstraints.FIRST_LINE_START;
	public static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	public static final int LAST_LINE_END = GridBagConstraints.LAST_LINE_END;
	public static final int LAST_LINE_START = GridBagConstraints.LAST_LINE_START;
	public static final int LINE_END = GridBagConstraints.LINE_END;
	public static final int LINE_START = GridBagConstraints.LINE_START;
	public static final int NONE = GridBagConstraints.NONE;
	public static final int NORTH = GridBagConstraints.NORTH;
	public static final int NORTHEAST = GridBagConstraints.NORTHEAST;
	public static final int NORTHWEST = GridBagConstraints.NORTHWEST;
	public static final int PAGE_END = GridBagConstraints.PAGE_END;
	public static final int PAGE_START = GridBagConstraints.PAGE_START;
	public static final int RELATIVE = GridBagConstraints.RELATIVE;
	public static final int REMAINDER = GridBagConstraints.REMAINDER;
	public static final int SOUTH = GridBagConstraints.SOUTH;
	public static final int SOUTHEAST = GridBagConstraints.SOUTHEAST;
	public static final int SOUTHWEST = GridBagConstraints.SOUTHWEST;
	public static final int VERTICAL = GridBagConstraints.VERTICAL;
	public static final int WEST = GridBagConstraints.WEST;

	protected int anchor;
	protected int cols_max;
	protected GridBagConstraints constraints;
	protected int fill;
	protected Insets insets;
	protected double weight_x;
	protected double weight_y;

	public direct_container_gridbag (int f_cols,int f_fill) { this (f_cols,f_fill,(new Insets (0,0,0,0))); }

	public direct_container_gridbag (int f_cols,int f_fill,Insets f_insets)
	{
		super ();
		setLayout (new GridBagLayout ());
		anchor = CENTER;
		cols_max = f_cols;
		constraints = new GridBagConstraints ();
		insets = f_insets;

		switch (f_fill)
		{
		case (HORIZONTAL):
		{
			fill = HORIZONTAL;
			weight_x = 1;
			weight_y = 0;
			break;
		}
		case (VERTICAL):
		{
			fill = VERTICAL;
			weight_x = 0;
			weight_y = 1;
			break;
		}
		case (BOTH):
		{
			fill = BOTH;
			weight_x = 1;
			weight_y = 1;
			break;
		}
		default:
		{
			fill = NONE;
			weight_x = 0;
			weight_y = 0;
			break;
		}
		}
	}

	public void add (Component f_component,int f_x,int f_colspan,int f_y,int f_rowspan) { add (f_component,f_x,f_colspan,f_y,f_rowspan,-1); }
	public void add (Component f_component,int f_x,int f_colspan,int f_y,int f_rowspan,int f_index) { add (this,constraints,anchor,fill,insets,cols_max,f_component,f_x,weight_x,f_colspan,f_y,weight_y,f_rowspan,f_index); }
	public void add (Component f_component,int f_x,int f_weight_x,int f_colspan,int f_y,int f_weight_y,int f_rowspan) { add (f_component,f_x,f_weight_x,f_colspan,f_y,f_weight_y,f_rowspan,-1); }
	public void add (Component f_component,int f_x,int f_weight_x,int f_colspan,int f_y,int f_weight_y,int f_rowspan,int f_index) { add (this,constraints,anchor,fill,insets,cols_max,f_component,f_x,f_weight_x,f_colspan,f_y,f_weight_y,f_rowspan,f_index); }

	public void add (direct_container_gridbag f_container,GridBagConstraints f_constraints,int f_anchor,int f_fill,Insets f_insets,int f_cols_max,Component f_component,int f_x,double f_weight_x,int f_colspan,int f_y,double f_weight_y,int f_rowspan,int f_index)
	{
		if ((f_colspan > 0)&&(f_rowspan > 0))
		{
			if ((f_x + f_colspan) > f_cols_max) { f_colspan = (f_x - f_colspan); }

			f_constraints.anchor = f_anchor;
			f_constraints.fill = f_fill;

			if ((f_x + f_colspan) == f_cols_max)
			{
				f_constraints.gridx = f_x;
				f_constraints.gridwidth = direct_container_gridbag.REMAINDER;
			}
			else
			{
				f_constraints.gridx = f_x;
				f_constraints.gridwidth = f_colspan;
			}

			f_constraints.gridy = f_y;
			f_constraints.gridheight = f_rowspan;
			f_constraints.insets = f_insets;
			f_constraints.weightx = f_weight_x;
			f_constraints.weighty = f_weight_y;

			if (f_index < 0) { f_container.add (f_component,f_constraints); }
			else { f_container.add (f_component,f_constraints,f_index); }

			f_container.validate ();
		}
	}

	public void removeAll ()
	{
		super.removeAll ();
		setLayout (new GridBagLayout ());
	}
}

//j// EOF