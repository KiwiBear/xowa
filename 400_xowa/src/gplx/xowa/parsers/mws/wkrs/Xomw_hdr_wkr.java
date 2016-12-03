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
package gplx.xowa.parsers.mws.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
public class Xomw_hdr_wkr {
	private Bry_bfr bfr;
	private Xomw_parser_ctx pctx;
	private int src_end;
	private Xomw_hdr_cbk cbk;
	public byte[] Src()			{return src;} private byte[] src;
	public int Hdr_len()		{return hdr_len;} private int hdr_len;
	public int Hdr_lhs_bgn()	{return hdr_lhs_bgn;} private int hdr_lhs_bgn;
	public int Hdr_lhs_end()	{return hdr_lhs_end;} private int hdr_lhs_end;
	public int Hdr_rhs_bgn()	{return hdr_rhs_bgn;} private int hdr_rhs_bgn;
	public int Hdr_rhs_end()	{return hdr_rhs_end;} private int hdr_rhs_end;
	public void Parse(Bry_bfr bfr, Xomw_parser_ctx pctx, byte[] src, int src_bgn, int src_end, Xomw_hdr_cbk cbk) {	// REF.MW: /includes/parser/Parser.php|doHeadings
		// init members
		this.bfr = bfr;
		this.pctx = pctx;
		this.src = src;
		this.src_end = src_end;
		this.cbk = cbk;

		// do loop
		int pos = src_bgn;
		int txt_bgn = pos == Xomw_parser_ctx.Pos__bos ? 0 : pos;
		byte b = Byte_ascii.Nl;
		while (true) {
			int nxt = pos + 1;
			if (	b == Byte_ascii.Nl
				&&	nxt < src_end
				&&	src[nxt] == Byte_ascii.Eq
				) {	// if \n, check if "="
				int rv = Parse_hdr_nl(txt_bgn, pos, nxt + 1);
				if (rv < 0) {
					pos = rv * -1;
				}
				else
					pos = txt_bgn = rv;
			}
			else
				++pos;
			if (pos == src_end) {
				if (txt_bgn != src_end)	// PERF: don't call Add_mid() if hdr is at end of EOS
					bfr.Add_mid(src, txt_bgn, src_end);
				break;
			}
			b = src[pos];
		}
	}
	private int Parse_hdr_nl(int txt_bgn, int nl_lhs, int pos) {
		// calc pos and len
		this.hdr_lhs_bgn = nl_lhs + 1;
		this.hdr_lhs_end = Bry_find_.Find_fwd_while(src, pos, src_end, Byte_ascii.Eq); 
		int nl_rhs = Bry_find_.Find_fwd_or(src, Byte_ascii.Nl, hdr_lhs_end + 1, src_end, src_end);	// no "\n"; src_end is rest of text; EX: "\n==<text>EOS
		this.hdr_rhs_end = Bry_find_.Find_bwd__skip_ws(src, nl_rhs, hdr_lhs_end);
		this.hdr_rhs_bgn = Bry_find_.Find_bwd__skip(src, hdr_rhs_end - 1, hdr_lhs_end, Byte_ascii.Eq);
		int hdr_lhs_len = hdr_lhs_end - hdr_lhs_bgn;
		int hdr_rhs_len = hdr_rhs_end - hdr_rhs_bgn;
		if (hdr_rhs_len == 0) {	// handle rare situations like "\n====\n"
			int hdr_lhs_len_half = hdr_lhs_len / 2;
			hdr_rhs_len = hdr_lhs_len - hdr_lhs_len_half;
			hdr_lhs_len = hdr_lhs_len_half;
			this.hdr_lhs_end = hdr_lhs_bgn + hdr_lhs_len;
			this.hdr_rhs_bgn = hdr_lhs_end;
		}

		// bld bry
		this.hdr_len = hdr_lhs_len < hdr_rhs_len ? hdr_lhs_len : hdr_rhs_len;
		if (nl_lhs > txt_bgn)
			bfr.Add_mid(src, txt_bgn, nl_lhs);	// add all txt up to nl_lhs

		if (nl_lhs != Xomw_parser_ctx.Pos__bos) bfr.Add_byte_nl();
		cbk.Write(bfr, pctx, this);
		return nl_rhs;
	}
}
//	for ( $i = 6; $i >= 1; --$i ) {
//		$h = str_repeat( '=', $i );
//		$text = preg_replace( "/^$h(.+)$h\\s*$/m", "<h$i>\\1</h$i>", $text );
//	}
