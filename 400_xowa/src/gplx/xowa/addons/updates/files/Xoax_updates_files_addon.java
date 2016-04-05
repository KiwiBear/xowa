/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.builds.utils_rankings.bldrs.*;
public class Xoax_updates_files_addon implements Xoax_addon_itm, Xoax_addon_bldr {
	public Xob_cmd[] Cmds_ary() {
		return new Xob_cmd[] 
		{ Xobldr__deletion_db__make.Prototype
		, Xobldr__deletion_db__exec.Prototype
		, Xobldr__deletion_db__small_files.Prototype
		, Xobldr__deletion_db__temp.Prototype
		};
	}

	public static final    byte[] ADDON_KEY = Bry_.new_a7("xowa.updates.files");
	public byte[] Addon__key()	{return ADDON_KEY;}
}
