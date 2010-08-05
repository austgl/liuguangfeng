package la.shop;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Link;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Site {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long sid;

	@Persistent
	private String sname;

	@Persistent
	private com.google.appengine.api.datastore.Link url;

	public Site(String sname, Link url) {
		super();
		this.sname = sname;
		this.url = url;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public com.google.appengine.api.datastore.Link getUrl() {
		return url;
	}

	public void setUrl(com.google.appengine.api.datastore.Link url) {
		this.url = url;
	}

	public String dummp2Table() {
		StringBuffer sb = new StringBuffer("<tr>");
		sb = sb.append("<td>" + this.getSid() + "</td>");
		sb = sb.append("<td>" + this.getUrl() + "</td>");
		sb = sb.append("<td>" + this.getSname() + "</td>");
		return sb.append("</tr>").toString();
	}

}