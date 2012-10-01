
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/init.jsp"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.IOException"%>

<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO"%>

<%@ page import="com.maxmind.geoip.CountryCodes"%>
<%@ page import="com.liferay.geoipusersmap.model.MarkVO"%>
<%@ page import="com.maxmind.geoip.Location"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.model.User"%>

<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>

<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="javax.portlet.PortletSession"%>


<%@ page import="com.maxmind.geoip.CountryCodes"%>
<%@ page import="java.util.Map"%>
<portlet:defineObjects />

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>

<%

 // System.out.println( " @@@ : Client IP :" + com.liferay.portal.util.PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr() );

	User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
	//System.out.println( " £££   userId : "+user.getUserId() + "email : "+user.getEmailAddress( ) );
	//PortletSession portletSession = renderRequest.getPortletSession();	
	//renderRequest.getPortletSession().setAttribute("City-"+user.getUserId(),"Dubai" , PortletSession.APPLICATION_SCOPE);
	String emailAddress = user.getEmailAddress();
	String countryName="";
	String countryCode="";
	String projectVisa="";
	
	 Object obj = renderRequest.getPortletSession().getAttribute("code-"+user.getUserId(),  PortletSession.APPLICATION_SCOPE);
	    
	 if( obj != null )
	 {
	    	countryCode = obj.toString();
	 }
		
	if( emailAddress!=null && !emailAddress.equals("default@liferay.com"))
	{	
	
		List<MarkVO> markList = new ArrayList<MarkVO>();
		
		markList = GeoIPUsersMapDAO.getUsersData( user.getUserId() );
		
		MarkVO mark = (MarkVO) markList.get(0);
		
		String htmlCode = mark.getHtmlCode();
		Float longitude = mark.getLongitude();
		Float latitude = mark.getLatitude();	
    
   		countryCode = 	mark.getLocation().countryCode;
    	countryName = 	mark.getLocation().countryName;
    	if( countryName.indexOf(" ")!=-1)
    	{
    	String pArray[] = countryName.split(" ");
    	if( pArray!= null && pArray.length >0)
    	{
    		for( int i=0; i<pArray.length; i++)
    		{
    			if(i > 0) projectVisa= projectVisa+ "_";
    			String s = pArray[i];
    			if( s!=null)
    			{
    				if( s.equalsIgnoreCase("and"))
    				{
    					s = s.toLowerCase();
    				}
    				else s=  s.substring(0,1).toUpperCase()  + s.substring(1, s.length()).toLowerCase();    			
    				projectVisa = projectVisa + s ;
    			}
    		}
    	}
    	 
    	}
    	else
    	{
    		 projectVisa = countryName;
    		 projectVisa=  projectVisa.substring(0,1).toUpperCase()  + projectVisa.substring(1, projectVisa.length()).toLowerCase();  
    	}
    	//out.println( htmlCode   );
    	
    	String guardian ="world/"+projectVisa.toLowerCase();
 		if(countryName.equalsIgnoreCase("united arab emirates")){
 			guardian ="world/united-arab-emirates"; 
 		}else if(countryName.equalsIgnoreCase("dominican republic")){
 				  guardian ="world/dominicanrepublic";
 		}else if(countryName.equalsIgnoreCase("united states")){
 			 guardian ="usa";  			
		}else if(countryName.equalsIgnoreCase("united kingdom")){
		 guardian ="uk"; 
		}
 		
 		String countryCode3Digit = mark.getLocation().countryCode3Digit;
 		if( countryCode3Digit!=null)
 		{
 			countryCode3Digit= countryCode3Digit.toLowerCase();
 			if(countryCode3Digit.equals("uae") )
 			{
 				countryCode3Digit="are";
 			} 					
 		}
    	%>
    	
    	Useful links for <%=countryName.toUpperCase()%> : <br>
    	<ul>
    	<li> <a href="http://en.wikipedia.org/wiki/<%=projectVisa %>" target="_blank"> Wikipedia </a> <br></li>
    	<li> <a href="http://reliefweb.int/country/<%=countryCode3Digit%>" target="_blank"> Relief Web </a> <br></li>
    	<li>  <a href="https://www.cia.gov/library/publications/the-world-factbook/geos/<%=countryCode.toLowerCase() %>.html" target="_blank"> (CIA) World Factbook</a></li>
    	<li> <a href="http://www.projectvisa.com/visainformation/<%=projectVisa %>" target="_blank"> Project Visa</a> <br></li>
    	<li> <a href="http://www.guardian.co.uk/<%=guardian%>" target="_blank"> NEWS</a> <br></li>
    	<li> <a href="http://www.timeanddate.com/worldclock/results.html?query=<%=countryName.toLowerCase() %>" target="_blank"> Time and Weather</a> <br></li>    	
    	<li> <a href="http://www.logcluster.org/countries" target="_blank"> Logistic Cluster</a> <br></li>
    	<li> <a href="http://ictemergency.wfp.org/web/ictepr/countries/" target="_blank"> Emergency Telecoms Cluster</a> <br></li>
    	<li> <a href="http://apps.who.int/bfi/tsy/PerDiem.aspx" target="_blank"> UN DSA Rates</a> <br></li>
    	<li> <a href="http://treasury.un.org/operationalrates/OperationalRates.aspx" target="_blank"> UN Exchange  Rates</a> <br></li>

    	</ul>
	<% 
	}
	else
	{
		
		%>
		Some useful links   : <br>
		<ul>
		<li>  <a href="https://www.cia.gov/library/publications/the-world-factbook/index.html" target="_blank"> (CIA) World Factbook</a></li>
		<li> <a href="http://www.projectvisa.com/" target="_blank"> Project Visa</a> <br></li>
			<li> <a href="http://reliefweb.int/countries" target="_blank"> Relief Web </a> <br></li>
		<li> <a href="http://www.guardian.co.uk/world" target="_blank"> NEWS</a> <br></li>
		<li> <a href="http://www.logcluster.org/countries" target="_blank"> Logistic Cluster</a> <br></li>
		<li> <a href="http://ictemergency.wfp.org/web/ictepr/countries/" target="_blank"> Emergency Telecoms Cluster</a> <br></li>
		<li> <a href="http://apps.who.int/bfi/tsy/PerDiem.aspx" target="_blank"> UN DSA Rates</a> <br></li>
		<li> <a href="http://treasury.un.org/operationalrates/OperationalRates.aspx" target="_blank"> UN Exchange  Rates</a> <br></li>
		
		</ul>
		<%
	}
	
	 %>




