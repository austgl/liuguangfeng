package la.shop;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Link;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Info {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long iid;

	@Persistent
	private Long sid;

	@Persistent
	private Long cid;

	@Persistent
	private String title;

	@Persistent
	private String price_now;

	@Persistent
	private String price_original;

	@Persistent
	private String price_save;

	@Persistent
	private String discount;

	@Persistent
	private String dscription;

	@Persistent
	private com.google.appengine.api.datastore.Link url;
	
	@Persistent
	private com.google.appengine.api.datastore.Link imageUrl;
	
	@Persistent
	private Date startime;

	@Persistent
	private Date endtime;

	public Info(Long sid, Long cid, String title, String priceNow,
			String priceOriginal, String priceSave, String discount,
			String dscription, Link url,Link imageUrl,Date startime, Date endtime) {
		super();
		this.sid = sid;
		this.cid = cid;
		this.title = title;
		price_now = priceNow;
		price_original = priceOriginal;
		price_save = priceSave;
		this.discount = discount;
		this.dscription = dscription;
		this.url = url;
		this.imageUrl=imageUrl;
		this.startime = startime;
		this.endtime = endtime;
	}

	public Long getIid() {
		return iid;
	}

	public com.google.appengine.api.datastore.Link getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(com.google.appengine.api.datastore.Link imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setIid(Long iid) {
		this.iid = iid;
	}


	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice_now() {
		return price_now;
	}

	public void setPrice_now(String priceNow) {
		price_now = priceNow;
	}

	public String getPrice_original() {
		return price_original;
	}

	public void setPrice_original(String priceOriginal) {
		price_original = priceOriginal;
	}

	public String getPrice_save() {
		return price_save;
	}

	public void setPrice_save(String priceSave) {
		price_save = priceSave;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDscription() {
		return dscription;
	}

	public void setDscription(String dscription) {
		this.dscription = dscription;
	}

	public com.google.appengine.api.datastore.Link getUrl() {
		return url;
	}

	public void setUrl(com.google.appengine.api.datastore.Link url) {
		this.url = url;
	}

	public Date getStartime() {
		return startime;
	}

	public void setStartime(Date startime) {
		this.startime = startime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String dummp2Table() {
		StringBuffer sb = new StringBuffer("<tr>");
		sb = sb.append("<td>" + this.getIid() + "</td>");
		sb = sb.append("<td>" + this.getCid() + "</td>");
		sb = sb.append("<td>" + this.getSid() + "</td>");
		sb = sb.append("<td>" + this.getPrice_now() + "</td>");
		sb = sb.append("<td>" + this.getPrice_original() + "</td>");
		sb = sb.append("<td>" + this.getPrice_save() + "</td>");
		sb = sb.append("<td>" + this.getDiscount() + "</td>");
		sb = sb.append("<td>" + this.getDscription() + "</td>");
		sb = sb.append("<td>" + this.getUrl() + "</td>");
		sb = sb.append("<td>" + this.getImageUrl() + "</td>");
		sb = sb.append("<td>" + this.getStartime() + "</td>");
		sb = sb.append("<td>" + this.getEndtime() + "</td>");
		sb = sb.append("<td>" + this.getTitle() + "</td>");

		return sb.append("</tr>").toString();
	}
}