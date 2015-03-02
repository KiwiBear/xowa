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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.*;
public class Xof_orig_itm {
	public byte			Repo() {return repo;} private byte repo;
	public byte[]		Page() {return page;} private byte[] page;
	public int			Ext() {return ext;} private int ext;
	public int			W() {return w;} private int w;
	public int			H() {return h;} private int h;
	public byte[]		Redirect() {return redirect;} private byte[] redirect;	// redirect trg; EX: A.png is redirected to B.jpg; record will have A.png|jpg|220|200|B.jpg where jpg|220|200 are the attributes of B.jpg
	public byte			Status() {return status;} private byte status;
	public void Clear() {
		this.repo = Repo_null;
		this.page = this.redirect = null;
		this.ext = Xof_ext_.Id_unknown;
		this.w = this.h = Xof_img_size.Null;
		this.status = Status_null;
	}
	public Xof_orig_itm Init(byte repo, byte[] page, int ext, int w, int h, byte[] redirect) {return Init(repo, page, ext, w, h, redirect, Status_null);}
	public Xof_orig_itm Init(byte repo, byte[] page, int ext, int w, int h, byte[] redirect, byte status) {
		this.repo = repo; this.page = page; this.ext = ext;
		this.w = w; this.h = h;  this.redirect = redirect; this.status = status;
		return this;
	}
	public static final byte Status_null = Byte_.Max_value_127;
	public static final byte Repo_comm = 0, Repo_wiki = 1, Repo_null = Byte_.Max_value_127;	// SERIALIZED: "wiki_orig.orig_repo"
	public static byte Repo_by_domain(byte[] domain) {return Bry_.Eq(domain, Xow_domain_.Domain_bry_commons) ? Repo_comm : Repo_wiki;}
	public static Xof_orig_itm new_clone(Xof_orig_itm itm) {
		return new Xof_orig_itm().Init(itm.repo, itm.page, itm.ext, itm.w, itm.h, itm.redirect, itm.status);
	}
	public static final Xof_orig_itm Null = null;
}