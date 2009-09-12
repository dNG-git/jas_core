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
* direct_array is an array implementation providing numbered and Object
* indices.
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

package de.direct_netware.signed.classes;
import de.direct_netware.signed.classes.exception.dNGArrayKeyNotFound;

import java.util.Hashtable;

public class direct_array extends Hashtable
{
	public static final long serialVersionUID = 618805014943253525L;

	public direct_array () { super (); }

	public direct_array (Object[] f_values)
	{
		for (int f_i = 0;f_i < f_values.length;f_i++) { put ((String.valueOf (f_i)),f_values[f_i]); }
	}

	public direct_array (int[] f_values)
	{
		for (int f_i = 0;f_i < f_values.length;f_i++) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
	}

	public direct_array (double[] f_values)
	{
		for (int f_i = 0;f_i < f_values.length;f_i++) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
	}

	public direct_array (float[] f_values)
	{
		for (int f_i = 0;f_i < f_values.length;f_i++) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
	}

	public direct_array (Object[] f_keys,Object[] f_values)
	{
		int f_length = f_values.length;
		if (f_keys.length > f_values.length) { f_length = f_keys.length; }

		for (int f_i = 0;f_i < f_length;f_i++)
		{
			if (f_length > f_values.length) { put (f_keys[f_i],null); }
			else if (f_length > f_keys.length) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
			else { put (f_keys[f_i],f_values[f_i]); }
		}
	}

	public direct_array (Object[] f_keys,int[] f_values)
	{
		int f_length = f_values.length;
		if (f_keys.length > f_values.length) { f_length = f_keys.length; }

		for (int f_i = 0;f_i < f_length;f_i++)
		{
			if (f_length > f_values.length) { put (f_keys[f_i],null); }
			else if (f_length > f_keys.length) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
			else { put (f_keys[f_i],(String.valueOf (f_values[f_i]))); }
		}
	}

	public direct_array (Object[] f_keys,float[] f_values)
	{
		int f_length = f_values.length;
		if (f_keys.length > f_values.length) { f_length = f_keys.length; }

		for (int f_i = 0;f_i < f_length;f_i++)
		{
			if (f_length > f_values.length) { put (f_keys[f_i],null); }
			else if (f_length > f_keys.length) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
			else { put (f_keys[f_i],(String.valueOf (f_values[f_i]))); }
		}
	}

	public direct_array (Object[] f_keys,double[] f_values)
	{
		int f_length = f_values.length;
		if (f_keys.length > f_values.length) { f_length = f_keys.length; }

		for (int f_i = 0;f_i < f_length;f_i++)
		{
			if (f_length > f_values.length) { put (f_keys[f_i],null); }
			else if (f_length > f_keys.length) { put ((String.valueOf (f_i)),(String.valueOf (f_values[f_i]))); }
			else { put (f_keys[f_i],(String.valueOf (f_values[f_i]))); }
		}
	}

	public boolean key_add (int f_key,Object f_value) { return key_add ((String.valueOf (f_key)),f_value,false); }
	public boolean key_add (int f_key,Object f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),f_value,f_overwrite); }
	public boolean key_add (int f_key,int f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (int f_key,int f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (int f_key,double f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (int f_key,double f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (int f_key,float f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (int f_key,float f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (double f_key,Object f_value) { return key_add ((String.valueOf (f_key)),f_value,false); }
	public boolean key_add (double f_key,Object f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),f_value,f_overwrite); }
	public boolean key_add (double f_key,int f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (double f_key,int f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (double f_key,double f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (double f_key,double f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (double f_key,float f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (double f_key,float f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (float f_key,Object f_value) { return key_add ((String.valueOf (f_key)),f_value,false); }
	public boolean key_add (float f_key,Object f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),f_value,f_overwrite); }
	public boolean key_add (float f_key,int f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (float f_key,int f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (float f_key,double f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (float f_key,double f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (float f_key,float f_value) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean key_add (float f_key,float f_value,boolean f_overwrite) { return key_add ((String.valueOf (f_key)),(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (Object f_key,Object f_value) { return key_add (f_key,f_value,false); }
	public boolean key_add (Object f_key,int f_value) { return key_add (f_key,(String.valueOf (f_value)),false); }
	public boolean key_add (Object f_key,int f_value,boolean f_overwrite) { return key_add (f_key,(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (Object f_key,double f_value) { return key_add (f_key,(String.valueOf (f_value)),false); }
	public boolean key_add (Object f_key,double f_value,boolean f_overwrite) { return key_add (f_key,(String.valueOf (f_value)),f_overwrite); }
	public boolean key_add (Object f_key,float f_value) { return key_add (f_key,(String.valueOf (f_value)),false); }
	public boolean key_add (Object f_key,float f_value,boolean f_overwrite) { return key_add (f_key,(String.valueOf (f_value)),f_overwrite); }

	public synchronized boolean key_add (Object f_key,Object f_value,boolean f_overwrite)
	{
		boolean f_return = false;

		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.key_add (f_keys[1],f_value,f_overwrite);
						f_key = null;
					}
				}
			}
		}

		if ((!f_return)&&(f_key != null)&&(f_value != null))
		{
			if (containsKey (f_key))
			{
				if (f_overwrite)
				{
					f_return = true;
					put (f_key,f_value);
				}
				else { f_return = false; }
			}
			else
			{
				f_return = true;
				put (f_key,f_value);
			}
		}

		return f_return;
	}

	public boolean key_exists (int f_key) { return key_exists (String.valueOf (f_key)); }
	public boolean key_exists (double f_key) { return key_exists (String.valueOf (f_key)); }
	public boolean key_exists (float f_key) { return key_exists (String.valueOf (f_key)); }

	public synchronized boolean key_exists (Object f_key)
	{
		boolean f_return = false;

		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.key_exists (f_keys[1]);
					}
				}
			}
		}

		if ((f_key != null)&&(!f_return)) { f_return = containsKey (f_key); }

		return f_return;
	}

	public int key_get_hash (int f_key) throws dNGArrayKeyNotFound { return key_get_hash (String.valueOf (f_key)); }
	public int key_get_hash (double f_key) throws dNGArrayKeyNotFound { return key_get_hash (String.valueOf (f_key)); }
	public int key_get_hash (float f_key) throws dNGArrayKeyNotFound { return key_get_hash (String.valueOf (f_key)); }

	protected int key_get_hash (Object f_key) throws dNGArrayKeyNotFound
	{
		int f_return = 0;

		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.key_get_hash (f_keys[1]);
					}
				}
			}
			else if ((f_keys.length == 1)&&(containsKey (f_key))) { f_return = f_key.hashCode (); }
			else { throw new dNGArrayKeyNotFound (); }
		}
		else if ((f_key == null)||(!containsKey (f_key))) { throw new dNGArrayKeyNotFound (); }
		else { f_return = f_key.hashCode (); }

		return f_return;
	}

	public boolean key_remove (int f_key) { return key_remove ((String.valueOf (f_key)),true); }
	public boolean key_remove (double f_key) { return key_remove ((String.valueOf (f_key)),true); }
	public boolean key_remove (float f_key) { return key_remove ((String.valueOf (f_key)),true); }
	public boolean key_remove (Object f_key) { return key_remove (f_key,true); }

	public synchronized boolean key_remove (Object f_key,boolean f_ignore_missing)
	{
		boolean f_return = false;


		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.key_remove (f_keys[1],f_ignore_missing);
						f_key = null;
					}
				}
			}
		}

		if (!f_return)
		{
			if ((f_key == null)||(!containsKey (f_key)))
			{
				if (!f_ignore_missing) { f_return = false; }
			}
			else { remove (f_key); }
		}

		return f_return;
	}

	public Object value_get (int f_key) { return value_get (String.valueOf (f_key)); }
	public Object value_get (double f_key) { return value_get (String.valueOf (f_key)); }
	public Object value_get (float f_key) { return value_get (String.valueOf (f_key)); }

	public Object value_get (Object f_key)
	{
		Object f_return = null;

		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.value_get (f_keys[1]);
					}
				}
			}
		}

		if ((f_key != null)&&(f_return == null)&&(containsKey (f_key))) { f_return = get (f_key); }

		return f_return;
	}

	public boolean value_set (int f_key,Object f_value) { return value_set ((String.valueOf (f_key)),f_value,false); }
	public boolean value_set (int f_key,Object f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),f_value,f_add); }
	public boolean value_set (int f_key,int f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (int f_key,int f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (int f_key,double f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (int f_key,double f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (int f_key,float f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (int f_key,float f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (double f_key,Object f_value) { return value_set ((String.valueOf (f_key)),f_value,false); }
	public boolean value_set (double f_key,Object f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),f_value,f_add); }
	public boolean value_set (double f_key,int f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (double f_key,int f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (double f_key,double f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (double f_key,double f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (double f_key,float f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (double f_key,float f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (float f_key,Object f_value) { return value_set ((String.valueOf (f_key)),f_value,false); }
	public boolean value_set (float f_key,Object f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),f_value,f_add); }
	public boolean value_set (float f_key,int f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (float f_key,int f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (float f_key,double f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (float f_key,double f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (float f_key,float f_value) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),false); }
	public boolean value_set (float f_key,float f_value,boolean f_add) { return value_set ((String.valueOf (f_key)),(String.valueOf (f_value)),f_add); }
	public boolean value_set (Object f_key,Object f_value) { return value_set (f_key,f_value,false); }
	public boolean value_set (Object f_key,int f_value) { return value_set (f_key,(String.valueOf (f_value)),false); }
	public boolean value_set (Object f_key,int f_value,boolean f_add) { return value_set (f_key,(String.valueOf (f_value)),f_add); }
	public boolean value_set (Object f_key,double f_value) { return value_set (f_key,(String.valueOf (f_value)),false); }
	public boolean value_set (Object f_key,double f_value,boolean f_add) { return value_set (f_key,(String.valueOf (f_value)),f_add); }
	public boolean value_set (Object f_key,float f_value) { return value_set (f_key,(String.valueOf (f_value)),false); }
	public boolean value_set (Object f_key,float f_value,boolean f_add) { return value_set (f_key,(String.valueOf (f_value)),f_add); }

	public synchronized boolean value_set (Object f_key,Object f_value,boolean f_add)
	{
		boolean f_return = false;

		if (f_key instanceof String)
		{
			String f_keys[] = { (String)f_key };
			if (f_keys[0].indexOf (" ") > -1) { f_keys = f_keys[0].split (" ",2); }

			if (f_keys.length > 1)
			{
				if (containsKey (f_keys[0]))
				{
					Object f_object = get (f_keys[0]);

					if (f_object instanceof direct_array)
					{
						direct_array f_array = (direct_array)f_object;
						f_return = f_array.value_set (f_keys[1],f_value,f_add);
						f_key = null;
					}
				}
			}
		}

		if ((!f_return)&&(f_key != null)&&(f_value != null))
		{
			if (containsKey (f_key))
			{
				f_return = true;
				put (f_key,f_value);
			}
			else if (f_add) { f_return = key_add (f_key,f_value); }
			else { f_return = false; }
		}

		return f_return;
	}
}

//j// EOF