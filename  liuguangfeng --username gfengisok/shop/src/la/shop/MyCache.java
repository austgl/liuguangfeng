package la.shop;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;

public class MyCache {
	private static MyCache cache = null;
	private int count = 0;
	private Hashtable<Long, City> cities;
	private Hashtable<Long, Site> sites;
	private Hashtable<Long, ArrayList<Info>> infos;// one city for many infos
	private ArrayList<Task> tasks=new ArrayList<Task>();
	private String cityList = "";
	private Long defaultCityId = new Long(1);
	private int maxCityInOneColum = 8;

	public int getMaxCityInOneColum() {
		return maxCityInOneColum;
	}

	public void setMaxCityInOneColum(int maxCityInOneColum) {
		this.maxCityInOneColum = maxCityInOneColum;
	}

	private MyCache() {
	}

	public static MyCache getCache() {
		return MyCache.cache == null ? new MyCache() : MyCache.cache;
	}

	@SuppressWarnings("unchecked")
	public void initCities() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String query = "select from " + City.class.getName();
			List<City> acities = (List<City>) pm.newQuery(query).execute();
			cities = new Hashtable<Long, City>();
			for (City city : acities) {
				cityList=cityList+formatOneCity(city.getCname(),city.getCid());
				cities.put(city.getCid(), city);
			}
			this.setCount();// inform the system that one has init
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void initSites() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String query = "select from " + Site.class.getName();
			List<Site> asites = (List<Site>) pm.newQuery(query).execute();
			sites = new Hashtable<Long, Site>();
			for (Site site : asites) {
				sites.put(site.getSid(), site);
			}
			this.setCount();// inform the system that one has init
		} finally {
			pm.close();
		}

	}

	private String formatOneCity(String cityName, Long cityNum) {
		return "<li id=\"cityLi_" + cityNum
				+ "\"><a id=\"cityA" + cityNum
				+ "\" title=\"查看"+cityName+"的信息\" href=\"javascript:cityChange('" + cityNum
				+ "');void(0);\">" + cityName + "</a></li>";
	}

	@SuppressWarnings("unchecked")
	public void initInfos() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// TODO not all,but today's
			infos = new Hashtable<Long, ArrayList<Info>>();
			String query = "select from " + Info.class.getName();
			List<Info> ainfos = (List<Info>) pm.newQuery(query).execute();
			for (Iterator<Long> itr = cities.keySet().iterator(); itr.hasNext();) {
				Long key = itr.next();
				ArrayList<Info> infosInCity = new ArrayList<Info>();
				for (Info info : ainfos) {
					if (info.getCid().equals(key)) {
						infosInCity.add(info);
					}
				}
				infos.put(key, infosInCity);
			}
			this.setCount();// inform the system that one has init
		} finally {
			pm.close();
		}

	}

	public String getCityList() {
		return cityList;
	}


	public Long getDefaultCityId() {
		return defaultCityId;
	}

	public void setDefaultCityId(Long defaultCityId) {
		this.defaultCityId = defaultCityId;
	}

	public Hashtable<Long, City> getCities() {
		return cities;
	}

	public Hashtable<Long, Site> getSites() {
		return sites;
	}

	public Hashtable<Long, ArrayList<Info>> getInfos() {
		return infos;
	}

	public int getCount() {
		return count;
	}

	public void setCount() {
		count++;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public ArrayList<Task> getTasks() {
		if(tasks.size()==0){
			tasks.add(new Task(new Long(2), new Long(1), "http://www.meituan.com/api/v1/shanghai/deals","deals", "title", "price", "value", "discount_amount", "discount_percent", "title", "deal_url", "medium_image_url", "start_date", "end_date"));
		}
		return tasks;//;
	}
}
