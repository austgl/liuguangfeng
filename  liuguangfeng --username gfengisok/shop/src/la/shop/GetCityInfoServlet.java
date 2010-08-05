package la.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GetCityInfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String cid = req.getParameter("cid");
		if (cid != null) {
			resp.setContentType("text/plain; charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			PrintWriter pw = resp.getWriter();

			MyCache cache = MyCache.getCache();
			if (cache.getCount() == 0) {
				cache.initCities();
				cache.initSites();
				cache.initInfos();
			}
			ArrayList<Info> infos = cache.getInfos().get(Long.parseLong(cid));
			if (infos != null) {
				StringBuffer sb = new StringBuffer(" ");

				for (Info info : infos) {
					sb = sb.append("{" + "'cid': '" + info.getCid() + "',"
							+ "'iid': '" + info.getIid() + "'," + "'sid': '"
							+ info.getSid() + "'," + "'price_now': '"
							+ info.getPrice_now() + "',"
							+ "'price_original': '" + info.getPrice_original()
							+ "'," + "'price_save': '" + info.getPrice_save()
							+ "'," + "'discount': '" + info.getDiscount()
							+ "'," + "'dscription': '" + info.getDscription()
							+ "'," + "'url': '" + info.getUrl()
							+ "'," + "'imageUrl': '" + info.getImageUrl() + "',"
							+ "'startime': '" + info.getStartime() + "',"
							+ "'endtime': '" + info.getEndtime() + "',"
							+ "'title': '" + info.getTitle() + "'" + "},");
				}
				pw.print("({'cid': '" + cid + "','info': [" + sb.substring(0, sb.length()-1) + "]})");
			} else {
				// no info
				resp.getWriter().write("0");
			}

		} else {
			// error
			resp.getWriter().write("Error,why don't u give me City ID??");
		}

	}
}
