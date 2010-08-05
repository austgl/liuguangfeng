package la.shop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;

public class Utils {
	public static void getCode(String classname) {
		// TODO Auto-generated method stub
		Method[] fs = null;
		try {
			fs = Class.forName(classname).getMethods();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<String> ls = new LinkedList<String>();
		for (Method f : fs) {
			String s = f.getName();
			int i = 0;
			if (s.startsWith("set") && (!s.endsWith("id") || (s.length() == 6))) {
				s = s.substring(3);
//				System.out.println("\"'"+s.toLowerCase()+"': '\"+info.get"+s+"()+\"',\"+");
				//ls.add(s.toLowerCase());
				System.out.println("String "+s.toLowerCase()+"=e.getChildTextTrim(t.get"+s+"());");
			}
		}

		System.out.println();
		for (String string : ls) {
			System.out.println(string + ":<input type=\"text\" id=\"" + string
					+ "\"name=\"" + string + "\"><br>");
		}
		System.out.println();
		System.out.print("$.get('/admin?cmd=add&table=info&");
		for (String string : ls) {
//			System.out.print(string + "='+$('#" + string + "').val()+'&");
//			System.out.println("String "+string+"=e.getChildTextTrim(t.getTitle());");
		}
		System.out.println("')");

		System.out.println();

		System.out.println();
	}

	public static String showCity() {
		String res = "<table>";
		Method[] fs = null;
		try {
			fs = City.class.getMethods();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<String> ls = new LinkedList<String>();
		for (Method f : fs) {
			String s = f.getName();
			if (s.startsWith("set")) {
				s = s.substring(3);
				ls.add(s);
			}
		}

		// for table header
		res = res + "<tr>";
		for (String string : ls) {
			res = res + "<td>" + string + "</td>";
		}
		res = res + "</tr>";
		// for the table content
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String query = "select from " + City.class.getName();
		List<City> r = (List<City>) pm.newQuery(query).execute();
		System.out.println("r is " + (r == null ? "" : "not ") + "null");

		if (!r.isEmpty()) {
			res = res + "<tr>";
			for (Object ar : r) {
				try {
					Object os[] = {};
					res = res + "<td>"
							+ City.class.getMethod("get" + ar).invoke(r, os)
							+ "</td>";
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			res = res + "</tr>";
		}
		return res + "</table>";
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		// City c=new City("�Ϻ�");
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		// try {
		// pm.makePersistent(c);
		// } finally {
		// pm.close();
		// }

		getCode("la.shop.City");
		getCode("la.shop.Info");
		getCode("la.shop.Site");
		getCode("la.shop.Task");
		// showCity();
	}

}
