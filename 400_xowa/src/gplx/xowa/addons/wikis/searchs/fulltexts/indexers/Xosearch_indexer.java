/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.searchs.fulltexts.indexers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.gflucene.*;
public class Xosearch_indexer {
	private final    Gflucene_index_bldr index_wtr = new Gflucene_index_bldr();
	public void Init(Xow_wiki wiki) {
		Io_url search_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "search");
		Io_mgr.Instance.DeleteDirDeep(search_dir);
		index_wtr.Init(search_dir.Xto_api());
	}
	public void Index(Xoae_page wpg) {
		// TODO: skip if not main_ns
		Index(wpg.Db().Page().Id(), wpg.Db().Page().Score(), wpg.Ttl().Page_txt(), wpg.Db().Html().Html_bry());
	}
	public void Index(int page_id, int score, byte[] ttl, byte[] html) {
		Gflucene_index_data data = new Gflucene_index_data(page_id, score, String_.new_u8(ttl), String_.new_u8(html));
		index_wtr.Exec(data);
	}
	public void Term() {
		index_wtr.Term();
	}
}
