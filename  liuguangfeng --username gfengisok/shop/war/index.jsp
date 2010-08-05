<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="la.shop.*"%>


<%@page import="org.apache.jasper.tagplugins.jstl.ForEach"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>

<%
	//now init some variables 68339
	Cookie[] acookies = request.getCookies();
	HashMap<String, Cookie> cookies = new HashMap<String, Cookie>();
	if (acookies != null) {
		for (Cookie cookie : acookies) {
			cookies.put(cookie.getName(), cookie);
		}
	}
	//set default values
	if (!cookies.containsKey("city"))
		cookies.put("city", new Cookie("city", "1"));
	if (!cookies.containsKey("app"))
		cookies.put("app", new Cookie("app", "weather"));
	MyCache cache = MyCache.getCache();
	if (cache.getCount() == 0) {
		cache.initCities();
		cache.initSites();
		cache.initInfos();
	}
	ArrayList<Info> yourCityInfos = cache.getInfos().get(
			cookies.get("city").getValue());
	int headHeight = 133 + 28 * (cache.getCities().size() / cache
			.getMaxCityInOneColum());
	Long currentCityId = Long.parseLong(cookies.get("city").getValue());
	ArrayList<Info> currentInfos = cache.getInfos().get(currentCityId);
%>


<%@page import="java.util.Iterator"%>
<%@page import="com.google.appengine.api.datastore.Link"%><html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="index_files/Common.css" rel="stylesheet" type="text/css">
<link href="index_files/Layout.css" rel="stylesheet" type="text/css">
<link href="index_files/style.css" rel="stylesheet" type="text/css">
<link href="index_files/Controls.css" rel="stylesheet" type="text/css">
<link rel="SHORTCUT ICON" href="index_files/favicon.ico">

<title>一起shop啦!17shop.la团购信息聚合网</title>
<meta name="description" content="17shop.la 团购信息聚合网 省钱 省心 聚合互联网团购信息">
<script type="text/javascript" src="jquery-1.4.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#info_right_table div").bind("mouseover",function(){
    	$(this).removeClass('info_pricetag').addClass('info_pricetag_hover');
    }); 
    $("#info_right_table div").bind("mouseout",function(){
    	$(this).removeClass('info_pricetag_hover').addClass('info_pricetag');
    }); 
});
//default cache in js for the server
var defaultCity=<%=currentCityId%>;
var cityMap={};
<%java.util.Hashtable<Long, City> cacheCities = cache.getCities();
			for (Iterator<Long> itr = cacheCities.keySet().iterator(); itr
					.hasNext();) {
				Long key = itr.next();
				City c = cacheCities.get(key);
				out.println("cityMap['" + key + "']='" + c.getCname() + "';");
			}%>
var siteMap={};
<%java.util.Hashtable<Long, Site> cacheSites = cache.getSites();
			for (Iterator<Long> itr = cacheSites.keySet().iterator(); itr
					.hasNext();) {
				Long key = itr.next();
				Site s = cacheSites.get(key);
				out.println("siteMap['" + key + "']='" + s.getSname() + "';");
				out.println("siteMap['" + key + "Url']='" + s.getUrl() + "';");
			}%>
var infoMap={};
<%out.println("infoMap['" + currentCityId
					+ "']=$('#MainColumn').html();");%>

function citytoggle(t){
	if(t=="+"){
		$("#BannerContainer").css("height","<%=headHeight%>px");
		$("#cityUl").css("display","none");
		$("#cityAllUl").css("display","block");
	}else if(t=="-"){
		$("#BannerContainer").css("height","133px");
		$("#cityAllUl").css("display","none");
		$("#cityUl").css("display","block");
	}else{
		showInfo("Error","are you kiding me?");
	}
}
function cityChange(cn){
	if(cn==defaultCity)return false;
	//set default city in cookies
	document.cookie = "city="+cn+";expires=" + new Date((new Date()).getTime() +9999999).toGMTString(); ;
	$("#currentCity").html($("#cityA"+cn).html());
	defaultCity=cn;
	showCityInfo(defaultCity);
}

function showCityInfo(city){
	if(infoMap[city]){
		 $("#MainColumn").html(infoMap[city]);
		  $("#info_right_table div").bind("mouseover",function(){
	          	$(this).removeClass('info_pricetag').addClass('info_pricetag_hover');
	          }); 
	      $("#info_right_table div").bind("mouseout",function(){
	          	$(this).removeClass('info_pricetag_hover').addClass('info_pricetag');
	         }); 
		return false;
	}
 $.getJSON("/ajax?cid="+city, function(data){
		var content=$("#noinfo");
		
		 $.each(data.info, function(i,item){
			//console.log(item.title);
			 if(i==0)content="";
			  //alert(item.title); 
				content=content+
			 "<table>"+
			 "<tr>"+
			 "<td id='info_left' valign='top'>"+
			 "<div><img src='/images/avatar.gif'></div>"+
			 "</td>"+
			 "<td id='info_right' valign='top'>"+
			 "<div>"+
			 "<div class='info_right_top'>来自<strong> "+siteMap[item.sid]+"</strong>&nbsp;的消息:<br>"+
			 "<table id='info_right_table'>"+
			 "<tr>"+
			 "<td colspan='3'><a href='#'>"+item.title+"</a>"+
			 "</td>"+
			 "</tr>"+
			 "<tr>"+
			 "<td>"+
			 "<div class='info_pricetag'><b>现价:</b>"+item.price_now+"<b>元</b></div>"+
			 "</td>"+
			 "<td>"+
			 "<div class='info_pricetag'>"+
			 "<b>节省:</b>"+item.price_save+"<b>元</b></div>"+
			 "</td>"+
			 "<td rowspan='3'><img class='info_right_img' alt='des' src='"+item.imageUrl+"' /></td>"+
			 "</tr>"+
			 "<tr>"+
			 "<td>"+
			 "<div class='info_pricetag'>"+
			 "<b>原价:</b>"+item.price_original+"<b>元</b></div>"+
			 "</td>"+
			 "<td>"+
			 "<div class='info_pricetag'>"+
			 "<b>折扣:</b>"+item.discount+"<b>折</b></div>"+
			 "</td>"+
			 "</tr>"+
			 "<tr>"+
			 "<td><a href='#'><img src='/images/button-buy.png'></img></a></td>"+
			 "<td><input type='image' src='/images/button-seemap.png'"+
			 "onclick='seeMap()'></input></td>"+
			 "</tr>"+
			 "</table>"+
			 "</div>"+
			 "<div class='info_right_foot'></div>"+
			 "</div>"+
			 "</td>"+
			 "</tr>"+
			 "</table>"
	          });
		 $("#MainColumn").html(content);
		  infoMap[city]=content;
		  $("#info_right_table div").bind("mouseover",function(){
	          	$(this).removeClass('info_pricetag').addClass('info_pricetag_hover');
	          }); 
	      $("#info_right_table div").bind("mouseout",function(){
	          	$(this).removeClass('info_pricetag_hover').addClass('info_pricetag');
	         }); 
		});
}
function filterAsCatelog(){
	
}
function showInfo(title,content){
	$("#NoPluginWarningContainerPad").show();
}
function seeMap(){
	alert('seemap');
}
</script>
</head>
<body>


<div id="BannerContainer">
<div id="BannerContent">
<div id="Logo"><a href="#"> <img
	src="index_files/Banner-Logo.png" alt="17shop.la logo" border="0"
	height="60" width="201"></a></div>
<div id="ctl00_ctl00_pnlSearch">

<div id="Search">
<div id="SearchBox"><input value="Search" name="input_searchbox"
	id="input_searchbox" class="Watermark" type="text"></div>
</div>

</div>
<div id="AcctButton"><a id="a_login" href="#">Login</a></div>
<div id="HelpButton"><a id="a_help" href="/faq.htm" target="blank">Help</a></div>

<div class="MainNav">
<div class="Tab"><a href="/blog.htm"><img alt="关于我们"
	src="index_files/tab-server-inactive.png" style="border-width: 0px;">
</a></div>
<div class="Tab"><a href="/faq.htm"><img alt="常见问题"
	src="index_files/tab-games-inactive.png" style="border-width: 0px;">
</a></div>
<div class="Tab"><a href="/navigation.htm"><img alt="站点导航"
	src="index_files/tab-tools-inactive.png" style="border-width: 0px;">
</a></div>
<div class="Tab"><a href="/recent.htm"><img alt="recent"
	src="index_files/tab-productivity-inactive.png"
	style="border-width: 0px;"> </a></div>
<div class="Tab"><a href="#"><img alt="今日团购"
	src="index_files/tab-home-active.png" style="border-width: 0px;">
</a></div>
</div>
<div class="SubNav">
<div class="ListHome">
<ul id="cityUl">
<li><div class="ListContent">当前城市&nbsp;<strong id="currentCity"><%=cache.getCities().get(currentCityId).getCname() %></strong></div></li>
<li><div class="ListContent"><a href="javascript:void(0);" onclick="javascript:$('#cityList').slideToggle('slow');void (0);">切换到其他城市&nbsp;<span style="color: blue;">▼</span></a></div></li>
</ul>
</div>
</div>

</div>
</div>


<!--info-->
<div id="cityList"
	class="cityList">
<ul><%=cache.getCityList()%>
</ul>
<div style="clear:both;"></div>
</div>

<div id="Container">

<div id="ContentContainerPad">


<div id="LeftColumn">
<div id="LeftColumnBorder">
<div id="LeftColumnContent">
<h3></h3>
<h3><span id="ctl00_ctl00_body_navTopCats_lblHeader">信息分类</span></h3>

<div id="ctl00_ctl00_body_navTopCats_divContainer" class="List">

<ul>

	<li onmouseover="this.className='Hover'" onmouseout="this.className=''"
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_ListItem" class="">
	<div class="ListContent"><a
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_lnkLink"
		href="http://spoon.net/Browsers">Browsers</a></div>
	</li>

	<li onmouseover="this.className='Hover'" onmouseout="this.className=''"
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_ListItem" class="">
	<div class="ListContent"><a
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_lnkLink"
		href="http://spoon.net/Browsers">Browsers</a></div>
	</li>

	<li onmouseover="this.className='Hover'" onmouseout="this.className=''"
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_ListItem" class="">
	<div class="ListContent"><a
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_lnkLink"
		href="http://spoon.net/Browsers">Browsers</a></div>
	</li>

	<li onmouseover="this.className='Hover'" onmouseout="this.className=''"
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_ListItem" class="">
	<div class="ListContent"><a
		id="ctl00_ctl00_body_navTopCats_lvLinks_ctrl0_lnkLink"
		href="http://spoon.net/Browsers">Browsers</a></div>
	</li>



</ul>

</div>
<h3> </h3>

</div>
</div>
</div>
<div id="MainColumn"><!--infos to display--> <%
 	if (currentInfos.size() == 0) {
 %>
<table>
	<tr>
		<td id='info_left' valign='top'>
		<div><img src='/images/oops.png'></div>
		</td>
		<td id='info_right' valign='top'>
		<div>
		<div class='info_right_top'>
		<h3>没有消息返回!</h3>
		<p style='margin-right: 25px; margin-bottom: 0;'>目前，我们还没有收集到关于该城市的团购信息，但是，请不要失望，我们的爬虫正在互联网上游荡，当它看见关于该城市的团购信息后就会通知我们，当审核通过后，您就会在这里看到。您可以订阅该城市，当有消息时，我们会在第一时间通知您。</p>

		</div>
		<div class='info_right_foot'></div>
		</div>
		</td>
	</tr>
</table>
<%
	} else {
		for (Info i : currentInfos) {
%>
<table>
	<tr>
		<td id='info_left' valign='top'>
		<div><img src='/images/avatar.gif'></div>
		</td>
		<td id='info_right' valign='top'>
		<div>
		<div class='info_right_top'>来自<strong> <%=cache.getSites().get(i.getSid()).getSname()%></strong>&nbsp;的消息:<br>
		<table id='info_right_table'>
			<tr>
				<td colspan='3'><a target='_blank' href='<%=i.getUrl()%>'><%=i.getTitle()%></a>
				</td>
			</tr>
			<tr>
				<td>
				<div class='info_pricetag'><b>现价:</b><%=i.getPrice_now()%><b>元</b></div>

				</td>
				<td>
				<div class='info_pricetag'><b>节省:</b><%=i.getPrice_save()%><b>元</b></div>
				</td>
				<td rowspan='3'><img class='info_right_img' alt="des"
					src="<%=i.getImageUrl()%>" /></td>
			</tr>
			<tr>
				<td>
				<div class='info_pricetag'><b>原价:</b><%=i.getPrice_original()%><b>元</b></div>
				</td>
				<td>
				<div class='info_pricetag'><b>折扣:</b><%=i.getDiscount()%><b>折</b></div>
				</td>
			</tr>
			<tr>
				<td><a href='#'><img src='/images/button-buy.png'></img></a></td>
				<td><input type='image' src='/images/button-seemap.png'
					onclick='seeMap()'></input></td>
			</tr>
		</table>
		</div>
		<div class='info_right_foot'></div>
		</div>
		</td>
	</tr>
</table>

<!--end of infos--> <%
 	}
 	}
 %>
</div>

<div class="Clear"></div>
<div id="ContentFooterPad">
<div id="ContentFooter"><b><a href="/joinus.htm">join us!</a></b>
| <a href="/about.htm">About</a> | <a href="/faq.htm">Help</a></div>
</div>
</div>
<!-- End page content --> <!-- Start footer -->
<div id="FooterContentContainer">
<div id="FooterContentPad">
<div id="FooterContent"><span class="LegalText"> ©2010
一起购物啦 (17shop.la)版权所有  |  <a href="/privacy.htm">使用前必读</a></span></div>
</div>
</div>
</div>

<div id="hiddenInfoDivs" style="display: none;">
<table id="noinfo">
	<tr>
		<td id='info_left' valign='top'>
		<div><img src='/images/oops.png'></div>
		</td>
		<td id='info_right' valign='top'>
		<div>
		<div class='info_right_top'>
		<h3>没有消息返回!</h3>
		<p style='margin-right: 25px; margin-bottom: 0;'>目前，我们还没有收集到关于该城市的团购信息，但是，请不要失望，我们的爬虫正在互联网上游荡，当它看见关于该城市的团购信息后就会通知我们，当审核通过后，您就会在这里看到。您可以订阅该城市，当有消息时，我们会在第一时间通知您。</p>

		</div>
		<div class='info_right_foot'></div>
		</div>
		</td>
	</tr>
</table>
</div>

<!-- End footer -->
</body>
</html>