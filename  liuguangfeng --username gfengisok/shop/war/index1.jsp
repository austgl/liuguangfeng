<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="la.shop.*"%>


<%@page import="java.io.File"%><html>
<body>
<h1>HelloWorld</h1>
<%
PersistenceManager pm = PMF.get().getPersistenceManager();
try {

	String query = "select from " + City.class.getName();
	List<City> cities = (List<City>) pm.newQuery(query)
			.execute();
	out.println("<table>");
	for (City city : cities) {
		
		out.println(city.dummp2Table());
	}
	out.println("</table>");
	
	query = "select from " + Site.class.getName();
	List<Site> sites = (List<Site>) pm.newQuery(query)
			.execute();
	out.println("<table>");
	for (Site site : sites) {
		out.println(site.dummp2Table());
	}
	out.println("</table>");
	
	
	query = "select from " + Info.class.getName();
	List<Info> infos = (List<Info>) pm.newQuery(query)
			.execute();
	out.println("<table border='1'>");
	for (Info info : infos) {
		out.println(info.dummp2Table());
		//pm.deletePersistent(info);
	}
	out.println("</table>");

} finally {
	pm.close();
}
System.out.println(new File(".").getAbsolutePath());
%>

</body>
</html>