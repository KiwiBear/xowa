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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.*;
public class Xoh_img_mgr {
	private final    List_adp list = List_adp_.New();
	private int uid_nxt = -1;
	public void Clear() {
		this.uid_nxt = -1;
		list.Clear();
	}
	public int Len() {return list.Count();}
	public Xof_fsdb_itm Get_at(int i) {return (Xof_fsdb_itm)list.Get_at(i);}
	public Xof_fsdb_itm Make_aud() {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_at_hdoc(++uid_nxt, Xof_html_elem.Tid_aud);
		list.Add(itm);
		return itm;
	}
	public Xof_fsdb_itm Make_img(boolean img_is_gallery) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_at_hdoc(++uid_nxt, img_is_gallery ? Xof_html_elem.Tid_gallery : Xof_html_elem.Tid_img);
		if (img_is_gallery)
			itm.Html_gallery_mgr_h_(gplx.xowa.xtns.gallery.Gallery_xnde.Default);	// TODO_OLD:set to <gallery heights=val>; PAGE:en.w:National_Gallery_of_Art; DATE:2016-06-25
		list.Add(itm);
		return itm;
	}
	public void To_bfr(Bry_bfr bfr) {
		int len = this.Len();
		for (int i = 0; i < len; ++i) {
			Xof_fsdb_itm itm = this.Get_at(i);
			itm.To_bfr(bfr);
		}
	}
	public static final String Str__html_uid = "xoimg_";
	public static final    byte[] Bry__html_uid = Bry_.new_a7(Str__html_uid);
}
