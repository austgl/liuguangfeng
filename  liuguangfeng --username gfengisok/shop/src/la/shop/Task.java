package la.shop;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Task {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long tid;

	@Persistent
	private Long sid;

	@Persistent
	private Long cid;

	@Persistent
	private String itemRoot;
	
	public String getItemRoot() {
		return itemRoot;
	}

	public void setItemRoot(String itemRoot) {
		this.itemRoot = itemRoot;
	}

	@Persistent
	private String infoUrl;

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

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
	private String url;

	@Persistent
	private String imageUrl;

	@Persistent
	private String startime;

	@Persistent
	private String endtime;

	public Task(Long sid, Long cid, String infoUrl,String itemRoot, String title,
			String priceNow, String priceOriginal, String priceSave,
			String discount, String dscription, String url, String imageUrl,
			String startime, String endtime) {
		super();
		this.sid = sid;
		this.cid = cid;
		this.title = title;
		this.itemRoot = itemRoot;
		this.infoUrl=infoUrl;
		price_now = priceNow;
		price_original = priceOriginal;
		price_save = priceSave;
		this.discount = discount;
		this.dscription = dscription;
		this.url = url;
		this.imageUrl = imageUrl;
		this.startime = startime;
		this.endtime = endtime;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long iid) {
		this.tid = iid;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStartime() {
		return startime;
	}

	public void setStartime(String startime) {
		this.startime = startime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String dummp2Table() {
		StringBuffer sb = new StringBuffer("<tr>");
		sb = sb.append("<td>" + this.getTid() + "</td>");
		sb = sb.append("<td>" + this.getCid() + "</td>");
		sb = sb.append("<td>" + this.getSid() + "</td>");
		sb = sb.append("<td>" + this.getItemRoot() + "</td>");
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