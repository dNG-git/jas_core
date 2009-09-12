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
* Antialiased version of JLabel for adding text. This class supports
* hyperlinks in JLabels.
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

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import javax.swing.border.Border;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.Position.Bias;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.View;
import javax.swing.UIManager;

public class direct_text extends JLabel
{
	public static final long serialVersionUID = -1575135142968995202L;

	public static final int CENTER = JLabel.CENTER;
	public static final int JUSTIFY = -102400; // 32 * 1 = 8 * 2 + 4 * 4
	public static final int LEADING = JLabel.LEADING;
	public static final int LEFT = JLabel.LEFT;
	public static final int RIGHT = JLabel.RIGHT;
	public static final int TRAILING = JLabel.TRAILING;

	protected HTMLDocument html_document;

	public direct_text ()
	{
		super ("<html><head></head><body><br></body></html>",JLabel.LEADING);
		init ();
		html_prefetch_document ();
	}

	public direct_text (Border f_border)
	{
		super ("<html><head></head><body><br></body></html>",JLabel.LEADING);
		init ();
		html_prefetch_document ();
		setBorder (f_border);
	}

	public direct_text (String f_content)
	{
		super ("<html><head></head></html>",JLabel.LEADING);
		init ();
		setText (f_content);
	}

	public direct_text (String f_content,Border f_border)
	{
		super ("<html><head></head></html>",JLabel.LEADING);
		init ();
		setBorder (f_border);
		setText (f_content,JLabel.LEADING);
	}

	public direct_text (String f_content,int f_orientation)
	{
		super ("<html></html>",f_orientation);
		init ();
		setText (f_content,f_orientation);
	}

	public direct_text (String f_content,int f_orientation,Border f_border)
	{
		super ("<html></html>",f_orientation);
		init ();
		setBorder (f_border);
		setText (f_content,f_orientation);
	}

	public direct_text (String f_content,Icon f_icon,int f_orientation)
	{
		super ("<html></html>",f_icon,f_orientation);
		init ();
		setText (f_content,f_orientation);
	}

	public direct_text (String f_content,Icon f_icon,int f_orientation,Border f_border)
	{
		super ("<html></html>",f_icon,f_orientation);
		init ();
		setBorder (f_border);
		setText (f_content,f_orientation);
	}

	protected void paintComponent (Graphics f_graphics)
	{
		Graphics2D f_graphics2D = (Graphics2D)f_graphics;
		f_graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent (f_graphics);
	}

	public String[] html_position_get_link (int f_position)
	{
		String[] f_return = { null,null };

		if (html_document != null)
		{
			Element f_element = html_document.getCharacterElement (f_position);

			while (f_element != null)
			{
				Object f_element_object = f_element.getAttributes().getAttribute (Tag.A);

				if ((f_element_object != null)&&(f_element_object instanceof SimpleAttributeSet))
				{
					SimpleAttributeSet f_element_a = (SimpleAttributeSet)f_element_object;

					f_element_object = f_element_a.getAttribute((Object)Attribute.HREF);
					if (f_element_object != null) { f_return[0] = f_element_object.toString (); }

					f_element_object = f_element_a.getAttribute((Object)Attribute.TARGET);
					if (f_element_object != null) { f_return[1] = f_element_object.toString (); }
				}

				f_element = f_element.getParentElement ();
			}
		}

		return f_return;
	}

	public String[] html_position_get_link (Point f_position)
	{
		String[] f_return = { null,null };

		if (html_document != null)
		{
			View f_view = (View)getClientProperty ("html");

			if (f_view != null)
			{
				Rectangle2D.Float f_view_shape = new Rectangle2D.Float (0,0,(getWidth ()),(getHeight ()));
				Bias[] f_view_bias = { Bias.Backward };
				int f_position_match = f_view.viewToModel (f_position.x,f_position.y,f_view_shape,f_view_bias);

				f_return = html_position_get_link (f_position_match);
			}
		}

		return f_return;
	}

	public void html_prefetch_document ()
	{
		View f_view = (View)getClientProperty ("html");
		Document f_document = null;

		if (f_view != null) { f_document = f_view.getDocument (); }
		if (f_document instanceof HTMLDocument) { html_document = (HTMLDocument)f_document; }
	}

	public String html_text_align_get (int f_orientation)
	{
		String f_return = "justify";

		switch (f_orientation)
		{
		case JLabel.CENTER:
		{
			f_return = "center";
			break;
		}
		case direct_text.JUSTIFY: { break; }
		case JLabel.LEFT:
		{
			f_return = "left";
			break;
		}
		case JLabel.RIGHT:
		{
			f_return = "right";
			break;
		}
		default:
		{
			boolean f_locale_ltr = ComponentOrientation.getOrientation(UIManager.getDefaults().getDefaultLocale ()).isLeftToRight ();

			if (f_orientation == JLabel.LEADING)
			{
				if (f_locale_ltr) { f_return = "left"; }
				else { f_return = "right"; }
			}
			else if (f_orientation == JLabel.TRAILING)
			{
				if (f_locale_ltr) { f_return = "right"; }
				else { f_return = "left"; }
			}
		}
		}

		return f_return;
	}

	protected void init ()
	{
		Font f_font = UIManager.getFont ("Label.font");
		setFont (f_font);
	}

	public void setText (String f_content) { setText (f_content,direct_text.JUSTIFY); }

	public void setText (String f_content,int f_orientation)
	{
		try
		{
			if (Double.parseDouble (System.getProperty ("java.specification.version")) < 1.5) { f_orientation = direct_text.LEADING; }
		}
		catch (Throwable f_handled_exception) { f_orientation = direct_text.LEADING; }

		super.setText ("<html><div style='text-align:" + (html_text_align_get (f_orientation)) + ";white-space:normal'>" + f_content + "</div></html>");
		html_prefetch_document ();
	}
}

//j// EOF