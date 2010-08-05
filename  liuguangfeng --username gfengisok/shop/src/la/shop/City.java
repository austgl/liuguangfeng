package la.shop;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class City {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long cid;
    @Persistent
    private String cname;

    public City(String cname) {
        this.cname=cname;
    }

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
	public String dummp2Table(){
		StringBuffer sb=new StringBuffer("<tr>");
		sb=sb.append("<td>"+this.getCid()+"</td>");
		sb=sb.append("<td>"+this.getCname()+"</td>");
		return sb.append("</tr>").toString();
	}

}