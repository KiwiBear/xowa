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
package gplx.xowa.addons.wikis.searchs.fulltexts.searchers.uis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*; import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.*;
public class Xosearch_searcher_line {
	public Xosearch_searcher_line(String wiki_domain, int page_id, int found_idx, String excerpt) {
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.found_idx = found_idx;
		this.excerpt = excerpt;
	}
	public String Wiki_domain() {return wiki_domain;} private final    String wiki_domain;
	public int Page_id() {return page_id;} private final    int page_id;
	public int Found_idx() {return found_idx;} private final    int found_idx;
	public String Excerpt() {return excerpt;} private final    String excerpt;
}
