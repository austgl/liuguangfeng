package la.shop;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class ShopAdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		System.out.println(req.getQueryString());
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String cmd = req.getParameter("cmd");
		if (cmd.equals("add")) {
			this.add(req);
		} else if (cmd.equals("delete")) {
			this.delete(req);
		} else if (cmd.equals("update")) {
			this.delete(req);
			this.add(req);
		} else {
			// error,unkonw cmd
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// pm.makePersistent(greeting);
		} finally {
			pm.close();
		}
	}

	public void add(HttpServletRequest req) {
		String table = req.getParameter("table");
		if (table.equals("city")) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(new City(req.getParameter("cname")));
			} finally {
				pm.close();
			}
		} else if (table.equals("site")) {
			com.google.appengine.api.datastore.Link url = new Link(req
					.getParameter("url"));
			String sname = req.getParameter("sname");
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(new Site(sname, url));
			} finally {
				pm.close();
			}

		} else if (table.equals("info")) {
			Long sid = Long.parseLong(req.getParameter("sid"));
			Long cid = Long.parseLong(req.getParameter("cid"));
			String price_now = req.getParameter("price_now");
			String price_original = req.getParameter("price_original");
			String price_save = req.getParameter("price_save");
			String discount = req.getParameter("discount");
			String dscription = req.getParameter("dscription");
			Link url = new Link(req.getParameter("url"));
			Link image = new Link(req.getParameter("image"));
			// TODO the time & date issue
			Date startime = new Date();// req.getParameter("startime"));
			Date endtime = new Date();// req.getParameter("endtime"));
			String title = req.getParameter("title");

			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				Info info = new Info(sid, cid, title, price_now,
						price_original, price_save, discount, dscription, url,image,
						 startime, endtime);
				pm.makePersistent(info);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pm.close();
			}

		} else {
			// error when no table selected
		}

	}

	public void delete(HttpServletRequest req) {

		String table = req.getParameter("table");
		if (table.equals("city")) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.deletePersistent(pm.getObjectById(City.class, req
						.getParameter("cid")));
			} finally {
				pm.close();
			}
		} else if (table.equals("site")) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.deletePersistent(pm.getObjectById(Site.class, req
						.getParameter("sid")));
			} finally {
				pm.close();
			}

		} else if (table.equals("info")) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.deletePersistent(pm.getObjectById(Info.class, req
						.getParameter("iid")));
			} finally {
				pm.close();
			}

		} else {
			// error when no table selected
		}

	}

}
