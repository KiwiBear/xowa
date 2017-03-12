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
package gplx.xowa.addons.wikis.searchs.fulltexts.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.core.btries.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.apps.cfgs.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.specials.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.caches.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.uis.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.gflucenes.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.brutes.*;
class Xosearch_fulltext_svc implements Gfo_invk {
	private final    Xoa_app app;
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xosearch_fulltext_special.Prototype.Special__meta().Ttl_bry());
	private final    Xosearch_cache_mgr cache_mgr = new Xosearch_cache_mgr();
	private final    Xosearch_searcher_ui searcher_cbk;
	public Xosearch_fulltext_svc(Xoa_app app) {
		this.app = app;
		this.searcher_cbk = new Xosearch_searcher_ui__gui(app.Gui__cbk_mgr(), cbk_trg);
	}
	public void Search(Json_nde args) {
		// for now, always clear cache; "get_lines_rest" will only work for latest search
		cache_mgr.Clear();

		// get search_args
		Xosearch_searcher_args search_args = Xosearch_searcher_args.New_by_json(args);
		search_args.query_id = cache_mgr.Next_qry_id();
		
		// autosave any changes if enabled
		Xocfg_mgr cfg_mgr = app.Cfg();
		if (cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.options.autosave_enabled", true)) {
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.case_match", search_args.case_match);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_bgn", search_args.auto_wildcard_bgn);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_end", search_args.auto_wildcard_end);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.expand_matches_section", search_args.expand_matches_section);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.show_all_matches", search_args.show_all_matches);
			cfg_mgr.Get_int_app_or ("xowa.addon.search.fulltext.special.max_pages_per_wiki", search_args.max_pages_per_wiki);
			cfg_mgr.Get_str_app_or ("xowa.addon.search.fulltext.special.namespaces", search_args.namespaces);
		}

		// launch thread
		gplx.core.threads.Thread_adp_.Start_by_val("search", Cancelable_.Never, this, Invk__search, search_args);
	}
	private void Search(Xosearch_searcher_args args) {
		try {
			// loop wikis
			byte[][] wiki_domains = Bry_split_.Split(args.wikis, Byte_ascii.Pipe_bry);
			for (byte[] wiki_domain : wiki_domains) {
				// get wiki and notify
				Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_domain);
				searcher_cbk.Send_wiki_add(wiki_domain);

				// get searcher and search
				Xosearch_searcher searcher = Get_searcher(wiki);
				searcher.Search(searcher_cbk, wiki, args);
			}
		} catch (Exception exc) {
			if (app.Tid_is_edit())
				((Xoae_app)app).Gui_mgr().Kit().Ask_ok("", "", Err_.Message_gplx_full(exc));
		}
	} 			
	public void Get_lines_rest(Json_nde args) {
		Get_lines_rest(args.Get_as_int("qry_id"), args.Get_as_bry("wiki"), args.Get_as_int("page_id"));
	}
	private void Get_lines_rest(int qry_id, byte[] wiki_bry, int page_id) {
		Xosearch_cache_line[] lines = cache_mgr.Get_lines_rest(qry_id, wiki_bry, page_id);
		for (Xosearch_cache_line line : lines) {
			Xosearch_searcher_line match = new Xosearch_searcher_line(String_.new_u8(wiki_bry), page_id, line.Line_seq() + 1, String_.new_u8(line.Line_html()));
			searcher_cbk.Send_line_add(match);
		}
	}
	private Xosearch_searcher Get_searcher(Xow_wiki wiki) {
		if (Io_mgr.Instance.ExistsDir(wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "search"))) {
			return new Xosearch_searcher__lucene();
		}
		else {
			return new Xosearch_searcher__brute(app, cbk_trg, cache_mgr);
		}
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search))           this.Search((Xosearch_searcher_args)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}   private static final String Invk__search = "search";
}
