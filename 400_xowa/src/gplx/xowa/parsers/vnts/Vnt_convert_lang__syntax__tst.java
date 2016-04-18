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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
public class Vnt_convert_lang__syntax__tst {	// REF: https://www.mediawiki.org/wiki/Writing_systems/Syntax
	private final Vnt_convert_lang_fxt fxt = new Vnt_convert_lang_fxt();
	@Test   public void Bidi() {
		String text = "-{zh-hans:a;zh-hant:b}-";
		fxt.Test_parse_many(text, "a", "zh-hans", "zh-cn", "zh-sg", "zh");
		fxt.Test_parse_many(text, "b", "zh-hant", "zh-hk", "zh-tw");
	}
	@Test   public void Undi() {
		String text = "-{H|cn_k=>zh-cn:cn_v}-cn_k";
		fxt.Test_parse_many(text, "cn_k", "zh", "zh-hans", "zh-hant", "zh-hk", "zh-my", "zh-mo", "zh-sg", "zh-tw");
		fxt.Test_parse_many(text, "cn_v", "zh-cn");
	}
	@Test   public void Raw() {
		fxt.Test_parse_many("-{a}-", "a", "zh-hans", "zh-cn", "zh-sg", "zh", "zh-hant", "zh-hk", "zh-tw");
		fxt.Test_parse_many("-{R|a}-", "a", "zh-hans", "zh-cn", "zh-sg", "zh", "zh-hant", "zh-hk", "zh-tw");
	}
	@Test   public void Hide() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw", "zh-tw");
		fxt.Test_parse_many(text, "cn hk tw", "zh", "zh-hans", "zh-hant");
	}
	@Test   public void Aout() {
		String text = "-{A|zh-cn:cn;zh-hk:hk;zh-tw:tw}- cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn cn", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw tw", "zh-tw");
		fxt.Test_parse_many(text, "cn cn hk tw", "zh", "zh-hans");
		fxt.Test_parse_many(text, "tw cn hk tw", "zh-hant");
		fxt.Test_parse_many("h-{}-k", "hk", "zh-cn");	// semi-disabled
	}
	@Test   public void Del() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw-{-|zh-cn:cn;zh-hk:hk;zh-tw:tw}- cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn cn hk tw", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk cn hk tw", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw cn hk tw", "zh-tw");
		fxt.Test_parse_many(text, "cn hk tw cn hk tw", "zh", "zh-hans", "zh-hant");
	}
	@Test   public void Title() {
		fxt.Test_parse_title("-{}-", null, "", "zh-cn");
		String text = "-{T|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw";
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-cn");
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-sg");
		fxt.Test_parse_title(text, "hk", "cn hk tw", "zh-hk");
		fxt.Test_parse_title(text, "tw", "cn hk tw", "zh-tw");
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-hans");
		fxt.Test_parse_title(text, "tw", "cn hk tw", "zh-hant");
		fxt.Test_parse_title(text, null, "cn hk tw", "zh");
	}
	@Test   public void Descrip() {
		String text = "-{D|zh-cn:cn;zh-hk:hk;zh-tw:tw}-";
		fxt.Test_parse_many(text, "ZH-CN:cn;ZH-HK:hk;ZH-TW:tw;", "zh", "zh-hans", "zh-hant", "zh-cn", "zh-hk", "zh-my", "zh-mo", "zh-sg", "zh-tw");
	}
	@Test   public void Mixture() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}--{zh;zh-hans;zh-hant|cn hk tw}- -{zh;zh-cn;zh-hk;zh-tw|cn hk tw}-";
		fxt.Test_parse_many(text, "cn hk tw cn cn cn", "zh-cn", "zh-sg", "zh-hans");
		fxt.Test_parse_many(text, "cn hk tw hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "cn hk tw tw tw tw", "zh-tw", "zh-hant");
		fxt.Test_parse_many(text, "cn hk tw cn hk tw", "zh");
	}
	@Test   public void Descrip__undi() {fxt.Test_parse("-{D|cn_k=>zh-cn:cn_v;hk_k=>zh-hk:hk_v}-", "cn_k⇒ZH-CN:cn_v;hk_k⇒ZH-HK:hk_v;");}
	@Test   public void Descrip__mixd() {fxt.Test_parse("-{D|zh-tw:tw_v;cn_k=>zh-cn:cn_v;hk_k=>zh-hk:hk_v;zh-mo:mo_v}-", "ZH-TW:tw_v;ZH-MO:mo_v;cn_k⇒ZH-CN:cn_v;hk_k⇒ZH-HK:hk_v;");}
}
class Vnt_convert_lang_fxt {
	private final Vnt_convert_lang converter_lang;
	private final Xol_convert_mgr convert_mgr = new Xol_convert_mgr();
	private final Xol_vnt_regy vnt_regy = Xol_vnt_regy_fxt.new_chinese();
	private Xol_vnt_itm vnt_itm;
	public Vnt_convert_lang_fxt() {
		converter_lang = new Vnt_convert_lang(convert_mgr, vnt_regy);
		this.Clear();
	}
	public void Clear() {
		convert_mgr.Init(vnt_regy);
		Init_cur("zh-cn");
	}
	public Vnt_convert_lang_fxt Init_cur(String vnt) {
		byte[] cur_vnt = Bry_.new_a7(vnt);
		this.vnt_itm = vnt_regy.Get_by(cur_vnt);
		return this;
	}
	public void Test_parse(String raw, String expd) {
		Tfds.Eq_str(expd, String_.new_u8(converter_lang.Parse_page(vnt_itm, -1, Bry_.new_u8(raw))));
	}
	public void Test_parse_many(String raw, String expd, String... vnts) {
		int len = vnts.length;
		for (int i = 0; i < len; ++i) {
			String vnt_key = vnts[i];
			Init_cur(vnt_key);
			Xol_vnt_itm vnt = vnt_regy.Get_by(Bry_.new_a7(vnt_key));
			Tfds.Eq_str(expd, String_.new_u8(converter_lang.Parse_page(vnt, -1, Bry_.new_u8(raw))), vnt_key);
		}
	}
	public void Test_parse_title(String raw, String expd_title, String expd_text, String vnt_key) {
		Init_cur(vnt_key);
		Xol_vnt_itm vnt = vnt_regy.Get_by(Bry_.new_a7(vnt_key));
		Tfds.Eq_str(expd_text, String_.new_u8(converter_lang.Parse_page(vnt, -1, Bry_.new_u8(raw))), vnt_key);
		Tfds.Eq_str(expd_title, converter_lang.Converted_title());
	}
}