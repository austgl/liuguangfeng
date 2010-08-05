package la.shop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * @author logan liu input an url and output the infos to DB.
 * 
 */
public class InfoParser {


	@SuppressWarnings("unchecked")
	private void parserTask(Task t) {
		try {
			org.jdom.Document doc = new SAXBuilder().build(new URL(t
					.getInfoUrl()));
			Element rootElement = doc.getRootElement();
			if(rootElement.getChild(t.getItemRoot())!=null){
				writeInfo(rootElement.getChild(t.getItemRoot()),t);
				return;
			}
			// In order to find the Root Element for infos,i use BFS.
			LinkedList<Element> temp = new LinkedList<Element>();
			temp.addLast(rootElement);
			while (!temp.isEmpty()) {
				Element r = temp.pop();
				if (r.getName().equals(t.getItemRoot())) {
					// find the root element,it contains many infos
					writeInfo(r,t);
					break;
				} else {
					for (Element element : (List<Element>) r.getChildren()) {
						temp.addLast(element);
					}
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//give me the root element of infos,i'll transport them into database
	@SuppressWarnings("unchecked")
	private void writeInfo(Element rootElement,Task t) {
		// TODO Auto-generated method stub
		for (Element e : (List<Element>)rootElement.getChildren()) {
			//for one info
			Long cid=Long.parseLong(e.getChildTextTrim(t.getCid().toString()));
			String imageurl=e.getChildTextTrim(t.getImageUrl());
			Long sid=Long.parseLong(e.getChildTextTrim(t.getSid().toString()));
			String price_now=e.getChildTextTrim(t.getPrice_now());
			String price_original=e.getChildTextTrim(t.getPrice_original());
			String price_save=e.getChildTextTrim(t.getPrice_save());
			String discount=e.getChildTextTrim(t.getDiscount());
			String dscription=e.getChildTextTrim(t.getDscription());
			String url=e.getChildTextTrim(t.getUrl());
			String startime=e.getChildTextTrim(t.getStartime());
			String endtime=e.getChildTextTrim(t.getEndtime());
			String itemroot=e.getChildTextTrim(t.getItemRoot());
			String infourl=e.getChildTextTrim(t.getInfoUrl());
			String title=e.getChildTextTrim(t.getTitle());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyCache cache = MyCache.getCache();
		InfoParser i = new InfoParser();
		for (Task t : cache.getTasks()) {
			System.out.println(t.dummp2Table());
			i.parserTask(t);
		}
	}

}
