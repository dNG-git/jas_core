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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.UIManager;

public class direct_textblock extends JTextPane
{
	public static final long serialVersionUID = -4982065456899139327L;

	public static final int CENTER = JLabel.CENTER;
	public static final int JUSTIFY = -102400; // 32 * 1 = 8 * 2 + 4 * 4
	public static final int LEADING = JLabel.LEADING;
	public static final int LEFT = JLabel.LEFT;
	public static final int RIGHT = JLabel.RIGHT;
	public static final int TRAILING = JLabel.TRAILING;

	protected HTMLDocument html_document;
	protected int height_forced = 0;
	protected int width_forced = 0;

	public direct_textblock ()
	{
		super ();
		setEditorKit (new HTMLEditorKit ());
		init ();
		html_prefetch_document ();
	}

	public direct_textblock (int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		setEditorKit (new HTMLEditorKit ());
		init ();
		html_prefetch_document ();
	}

	public direct_textblock (Border f_border)
	{
		super ();
		setEditorKit (new HTMLEditorKit ());
		init ();
		html_prefetch_document ();
		setBorder (f_border);
	}

	public direct_textblock (Border f_border,int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		setEditorKit (new HTMLEditorKit ());
		init ();
		html_prefetch_document ();
		setBorder (f_border);
	}

	public direct_textblock (String f_content)
	{
		super ();
		setEditorKit (new HTMLEditorKit ());
		init ();
		setText (f_content);
	}

	public direct_textblock (String f_content,int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		setEditorKit (new HTMLEditorKit ());
		init ();
		setText (f_content);
	}

	public direct_textblock (String f_content,Border f_border)
	{
		super ();
		init ();
		setBorder (f_border);
		setText (f_content);
	}

	public direct_textblock (String f_content,Border f_border,int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		init ();
		setBorder (f_border);
		setText (f_content);
	}

	public direct_textblock (String f_content,int f_orientation)
	{
		super ();
		setEditorKit (new HTMLEditorKit ());
		init ();
		setText (f_content,f_orientation);
	}

	public direct_textblock (String f_content,int f_orientation,int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		setEditorKit (new HTMLEditorKit ());
		init ();
		setText (f_content,f_orientation);
	}

	public direct_textblock (String f_content,int f_orientation,Border f_border)
	{
		super ();
		setEditorKit (new HTMLEditorKit ());
		init ();
		setBorder (f_border);
		setText (f_content,f_orientation);
	}

	public direct_textblock (String f_content,int f_orientation,Border f_border,int f_width_forced,int f_height_forced)
	{
		super ();
		height_forced = f_height_forced;
		width_forced = f_width_forced;

		setEditorKit (new HTMLEditorKit ());
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
			int f_position_match = viewToModel (f_position);
			f_return = html_position_get_link (f_position_match);
		}

		return f_return;
	}

	public void html_prefetch_document ()
	{
		Document f_document = getDocument ();
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
		case direct_textblock.JUSTIFY: { break; }
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
		setEditable (false);
		setBackground (UIManager.getColor ("Label.background"));
		setFont (UIManager.getFont ("Label.font"));
		setForeground (UIManager.getColor ("Label.foreground"));
	}

	public void setText (String f_content) { setText (f_content,direct_textblock.JUSTIFY); }

	public void setText (String f_content,int f_orientation)
	{
		try
		{
			if (Double.parseDouble (System.getProperty ("java.specification.version")) < 1.5) { f_orientation = direct_textblock.LEADING; }
		}
		catch (Throwable f_handled_exception) { f_orientation = direct_textblock.LEADING; }

		Font f_font = UIManager.getFont ("Label.font");
		String f_size_forced = "";

		if (width_forced > 0) { f_size_forced += "width:" + width_forced + "px;"; }
		if (height_forced > 0) { f_size_forced += "height:" + height_forced + "px;"; }

		super.setText ("<html><div style='" + f_size_forced + "font-family:" + (f_font.getName ()) + ";font-size:" + (f_font.getSize ()) + "pt;text-align:" + (html_text_align_get (f_orientation)) + ";white-space:normal'>" + f_content + "</div></html>");
		html_prefetch_document ();
	}
}

//j// EOF