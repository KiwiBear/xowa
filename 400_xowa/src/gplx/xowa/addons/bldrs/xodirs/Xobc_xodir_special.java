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
package gplx.xowa.addons.bldrs.xodirs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.addons.bldrs.centrals.*;
import gplx.xowa.addons.wikis.imports.*;
public class Xobc_xodir_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		byte[] path = url_args.Read_bry_or(Bry__path, null);
		if (path != null) { // path selected; set cfg and redirect to Download Central
			Xobc_xodir_cfg.Set_app_str__selected(wiki.App(), path);
			// On_path_selected.Invk(null, -1, "", null);
			// page.Redirect().Itms__add__special(wiki, Xobc_task_special.Prototype.Special__meta());
			page.Redirect().Itms__add__special(wiki, Prototype.Special__meta());
			return;
		}
		
		new Xobc_xodir_html().Bld_page_by_mustache(wiki.App(), page, this);
	}
	private static final    byte[] Bry__path = Bry_.new_a7("path");

	Xobc_xodir_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xobc_xodir_special(Xow_special_meta.New_xo("XowaRootDir", "XOWA Folder Selection"));

	public static Gfo_invk On_path_selected = Gfo_invk_.Noop;
}
