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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.threads.poolables.*; import gplx.core.brys.args.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_wtr implements gplx.core.brys.Bfr_arg, Xoh_wtr_itm {
	private final    Bfr_arg__bry anch_href = Bfr_arg__bry.New_empty(), anch_cls = Bfr_arg__bry.New_empty(), anch_content = Bfr_arg__bry.New_empty();
	private final    Bfr_arg__hatr_bry anch_title = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__title);
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		Bfr_arg_.Clear(anch_href, anch_cls, anch_content, anch_title);
		Xoh_lnke_data data = (Xoh_lnke_data)data_itm;
		anch_href.Set_by_mid(src, data.Href_bgn(), data.Href_end());
		anch_cls.Set_by_val(Xoh_lnke_dict_.To_html_class(data.Lnke_tid()));
		if		(data.Title_exists())	anch_title.Set_by_mid(src, data.Title_bgn(), data.Title_end());
		if		(data.Auto_exists())	anch_content.Set_by_arg(Xoh_lnke_wtr_arg__autonum.Instance.Set_by_auto_id(data.Auto_id()));
		else if (data.Capt_exists())	anch_content.Set_by_mid(src, data.Capt_bgn(), data.Capt_end());
		else							anch_content.Set_by_mid(src, data.Href_bgn(), data.Href_end());
		return true;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, anch_href, anch_cls, anch_content, anch_title);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnke_wtr rv = new Xoh_lnke_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_
	( "<a href=\"~{href}\" rel=\"nofollow\" class=\"external ~{cls}\"~{title}>~{content}</a>"
	, "href", "cls", "content", "title");
}
class Xoh_lnke_wtr_arg__autonum implements Bfr_arg {
	private int auto_id;
	public Bfr_arg Set_by_auto_id(int auto_id) {this.auto_id = auto_id; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(auto_id).Add_byte(Byte_ascii.Brack_end);
	}
        public static final    Xoh_lnke_wtr_arg__autonum Instance = new Xoh_lnke_wtr_arg__autonum(); Xoh_lnke_wtr_arg__autonum() {}
}
