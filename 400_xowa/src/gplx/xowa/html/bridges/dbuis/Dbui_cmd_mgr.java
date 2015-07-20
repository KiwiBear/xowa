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
package gplx.xowa.html.bridges.dbuis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.bridges.*;
import gplx.core.json.*; import gplx.xowa.html.bridges.dbuis.tbls.*;
public class Dbui_cmd_mgr {
	private final Hash_adp_bry hash = Hash_adp_bry.cs_();
	private boolean init;
	public void Init_by_bridge(Bridge_cmd_mgr cmd_mgr) {
		if (init) return;
		init = true;
		cmd_mgr.Add(new Dbui_cmd_row_edit		("xowa.dbui.edit_bgn", this));
		cmd_mgr.Add(new Dbui_cmd_row_save		("xowa.dbui.save_bgn", this));
		cmd_mgr.Add(new Dbui_cmd_row_del		("xowa.dbui.delete_bgn", this));
		cmd_mgr.Add(new Dbui_cmd_row_reorder	("xowa.dbui.reorder_bgn", this));
	}
	public void Add(Dbui_tbl_itm tbl)	{hash.Add_bry_obj(tbl.Key(), tbl);}
	public String Del(Json_nde data)	{return Get_tbl(data).Del (data.Get_bry(Arg_row_id), data.Get_bry(Arg_row_pkey));}
	public String Edit(Json_nde data)	{return Get_tbl(data).Edit(data.Get_bry(Arg_row_id), data.Get_bry(Arg_row_pkey));}
	public String Save(Json_nde data)	{return Get_tbl(data).Save(data.Get_bry(Arg_row_id), data.Get_bry(Arg_row_pkey), To_hash(data.Get(Arg_vals)));}
	public String Reorder(Json_nde data){
		byte[] pkeys_concat = data.Get_bry(Arg_pkeys);
		return Get_tbl(data).Reorder(Bry_.Split(pkeys_concat, Byte_ascii.Pipe), -1);
	}
	private Dbui_tbl_itm Get_tbl(Json_nde data) {
		byte[] tbl_key = data.Get_bry(Arg_tbl_key);
		Dbui_tbl_itm rv = (Dbui_tbl_itm)hash.Get_by(tbl_key); if (rv == null) throw Err_.new_("dbui", "unknown tbl_key", "tbl_key", tbl_key);
		return rv;
	}
	private static Dbui_val_hash To_hash(Json_grp grp) {
		Dbui_val_hash rv = new Dbui_val_hash();
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Json_itm_kv kv = (Json_itm_kv)grp.Get_at(i);
			Json_nde nde = (Json_nde)kv.Val();
			Json_itm_kv key = (Json_itm_kv)nde.Get_itm(Arg_key);
			Json_itm_kv val = (Json_itm_kv)nde.Get_itm(Arg_val);
			Dbui_val_itm fld = new Dbui_val_itm(val.Val().Data_bry(), Bry_.Empty);
			rv.Add(key.Val().Data_bry(), fld);
		}
		return rv;
	}
        public static final Dbui_cmd_mgr I = new Dbui_cmd_mgr(); Dbui_cmd_mgr() {}
	private static final byte[] 
	  Arg_tbl_key = Bry_.new_a7("tbl_key"), Arg_row_pkey = Bry_.new_a7("row_pkey"), Arg_row_id = Bry_.new_a7("row_id")
	, Arg_vals = Bry_.new_a7("vals"), Arg_key = Bry_.new_a7("key"), Arg_val = Bry_.new_a7("val")
	, Arg_pkeys = Bry_.new_a7("pkeys")
	;
}
class Dbui_cmd_row_del implements Bridge_cmd_itm {
	private final Dbui_cmd_mgr mgr;
	public Dbui_cmd_row_del(String key, Dbui_cmd_mgr mgr) {this.key = Bry_.new_u8(key); this.mgr = mgr;}
	public byte[] Key() {return key;} private final byte[] key;
	public String Exec(Json_nde data) {return mgr.Del(data);}
}
class Dbui_cmd_row_edit implements Bridge_cmd_itm {
	private final Dbui_cmd_mgr mgr;
	public Dbui_cmd_row_edit(String key, Dbui_cmd_mgr mgr) {this.key = Bry_.new_u8(key); this.mgr = mgr;}
	public byte[] Key() {return key;} private final byte[] key;
	public String Exec(Json_nde data) {return mgr.Edit(data);}
}
class Dbui_cmd_row_save implements Bridge_cmd_itm {
	private final Dbui_cmd_mgr mgr;
	public Dbui_cmd_row_save(String key, Dbui_cmd_mgr mgr) {this.key = Bry_.new_u8(key); this.mgr = mgr;}
	public byte[] Key() {return key;} private final byte[] key;
	public String Exec(Json_nde data) {return mgr.Save(data);}
}
class Dbui_cmd_row_reorder implements Bridge_cmd_itm {
	private final Dbui_cmd_mgr mgr;
	public Dbui_cmd_row_reorder(String key, Dbui_cmd_mgr mgr) {this.key = Bry_.new_u8(key); this.mgr = mgr;}
	public byte[] Key() {return key;} private final byte[] key;
	public String Exec(Json_nde data) {return mgr.Reorder(data);}
}
