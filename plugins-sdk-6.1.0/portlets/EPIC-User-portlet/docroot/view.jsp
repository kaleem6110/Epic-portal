
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/init.jsp"%>
<%@ include file="/AjaxUtils.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.IOException"%>

<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO"%>
<%@ page import="com.liferay.geoipusersmap.portlet.GeoIPUsersMapPortlet"%>
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

<script type="text/javascript">


function <portlet:namespace />getguestip(json)
{
      		//	alert(json.ip); // alerts the ip address
      
      			var <portlet:namespace />ajax = new AjaxUtils();
	  			var parameters = {} ;
	  			parameters["ip"]= json.ip;
	  			parameters["cmd"]= "updateIP";
	  			<portlet:namespace />ajax.parameters = parameters;
	  			<portlet:namespace />ajax.actionRequest("get" );
	  			
	  			
}
    
    


</script>

<%

 // System.out.println( " @@@ : Client IP :" + com.liferay.portal.util.PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr() );

	User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
	//System.out.println( " £££   userId : "+user.getUserId() + "email : "+user.getEmailAddress( ) );
	//PortletSession portletSession = renderRequest.getPortletSession();	
	//renderRequest.getPortletSession().setAttribute("City-"+user.getUserId(),"Dubai" , PortletSession.APPLICATION_SCOPE);
	String emailAddress = user.getEmailAddress();
	String countryName="";
	String countryCode="";
	
	Object obj =null;
	
	String autoSelectedCode= null;
	
		
	if( emailAddress!=null && !emailAddress.equals("default@liferay.com"))
	{	
	
		List<MarkVO> markList = new ArrayList<MarkVO>();
		System.out.println("  34334343434343");
		markList = GeoIPUsersMapDAO.getUsersData( user.getUserId(), renderRequest  );
		obj = renderRequest.getPortletSession().getAttribute("code-"+user.getUserId(),  PortletSession.APPLICATION_SCOPE);
	    
		 if( obj != null )
		 {
		    	countryCode = obj.toString();
		 }
		System.out.println("  ERRR countryCode:"+ countryCode);
		Integer num_users = GeoIPUsersMapDAO.getNumUsers();
		
		int num_marks= markList.size();
		
		MarkVO mark = (MarkVO) markList.get(0);
		
		String htmlCode = mark.getHtmlCode();
		Float longitude = mark.getLongitude();
		Float latitude = mark.getLatitude();	
		countryCode = 	mark.getLocation().countryCode;
		countryName = 	mark.getLocation().countryName;
    
   		System.out.println( " countryCode  countryCode   countryCode : "+countryCode );
    
   		
	  
		
%>


<div style="line-height: 2.0em"> Welcome <b> <%=mark.getFirstName()%> <%=mark.getLastName()%> </b>   </div>

<form id="myform" action="<liferay-portlet:renderURL />">
	
	<div id="auto_text">
	The system has determined that you are logging in from  <b><%=countryName%></b> <br>
	If this is incorrect, please use the dropdown below to set the correct country. <br>
	</div>
	<div id="manual_text"> </div>
	
	<img id="c_image" src="/geoip-usersmap-portlet/images/flags/<%=countryCode.toLowerCase()%>.gif" />
				
		<select id="location" style="width: 220px;">
		
		<%
					boolean isAutoSelected = false;
					CountryCodes countryCodesObj = new CountryCodes();
					Map<String, String>  countryCodesMap = countryCodesObj.lookup;
					for ( Map.Entry<String, String> entry : countryCodesMap.entrySet() ) 
					{   				
						
						
						Object obj1 = renderRequest.getPortletSession().getAttribute("isAutoSet-"+user.getUserId(), PortletSession.APPLICATION_SCOPE);
						
						if(obj1==null && countryCode.equals( entry.getKey())  )
    					{
							//System.out.println(  "KALEEM  : "+ entry.getKey() + " obj1 : "+ obj1 + ": "+countryCode+ " : "+entry.getKey() );
							renderRequest.getPortletSession().setAttribute("isAutoSet-"+user.getUserId(),"Auto( "+entry.getValue()+" )" , PortletSession.APPLICATION_SCOPE);
							renderRequest.getPortletSession().setAttribute("isAutoSetCode-"+user.getUserId(),  entry.getKey() , PortletSession.APPLICATION_SCOPE);
							obj1 = renderRequest.getPortletSession().getAttribute("isAutoSet-"+user.getUserId(), PortletSession.APPLICATION_SCOPE);
							isAutoSelected = true;
							autoSelectedCode=countryCode;
							
    	%>					
    						<option value="<%=entry.getKey()%>"> Auto ( <%=entry.getValue()%> ) </option>
    		<%
    					}
    					else
    					{
    						Object cccode = renderRequest.getPortletSession().getAttribute("isAutoSetCode-"+user.getUserId(), PortletSession.APPLICATION_SCOPE);
    						
    						Object cccodeName = renderRequest.getPortletSession().getAttribute("isAutoSet-"+user.getUserId(), PortletSession.APPLICATION_SCOPE);
    						//System.out.println(  " KKKKKKK  cccode.toString()  : "+ cccode+  " entry.getKey() : "+ entry.getKey()  );
    						if(cccode!=null &&  cccodeName!=null&& cccode.toString().equals(entry.getKey() ) )
    						{
    							isAutoSelected = true;
    							autoSelectedCode = cccode.toString();
    							System.out.println(  " LLLLL  cccode.toString()  : "+ cccode.toString() +  " entry.getKey() : "+ entry.getKey()  );
    							%>
    							<option value="<%=entry.getKey()%>"> <%=cccodeName.toString()%> </option>
    						<%}
    						else
    						{%>
    							<option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>
    						<%}
    		
    					}		
		
					}
					
					
		
%>

		</select>		
</form>

<%
		
	
	

	
	
	}
	else
	{
		 obj = renderRequest.getPortletSession().getAttribute("code-"+user.getUserId(),  PortletSession.APPLICATION_SCOPE);
	    
		
		 if( obj != null )
		 {
		    	countryCode = obj.toString();
		 }
		 
		String userId= renderRequest.getRemoteUser();
			
			
		String ccName  = renderRequest.getParameter("Location" );
		String ccode =renderRequest.getParameter("code" );
		
		System.out.println(" ccName "+ccName+ " ccode "+ccode );
		if(ccName== null)
		{
			ccName = "INDIA";
		}
		if(ccode!= null)
		{
			ccode = ccode.toLowerCase();
			//countryCode = ccode;
		}
		
		
		%>
		Welcome <b>Guest!</b> <br>
		<%
		
		//PortletPreferences prefs = renderRequest.getPreferences();
		//String userValue = prefs.getValue("Location-"+user.getUserId(), "");	
		//System.out.println( " & userValue :"+ userValue );	
		//prefs.setValue("Location-"+user.getUserId(), countryName );	
		//prefs.store();
		//renderRequest.getPortletSession().setAttribute("Location-"+user.getUserId(),countryName , PortletSession.APPLICATION_SCOPE);
		//renderRequest.getPortletSession().setAttribute("code-"+user.getUserId(),countryCode , PortletSession.APPLICATION_SCOPE);
		
		//String locationn = (String)renderRequest.getPortletSession().getAttribute("Location-"+user.getUserId(),PortletSession.APPLICATION_SCOPE);
		
		//System.out.println("htmlCode :"+htmlCode );
		//htmlCode="hdkjfhdkjhjkhfd";
		///*PortletPreferences preferences1 = (PortletPreferences)request.getPreferences();
	    //System.out.println("Action: Preferences = " + preferences1);
	    //System.out.println("Action: Preferences map leeg? = "+ preferences1.getMap().isEmpty());
	    //System.out.println("Action: Preferences - test = " + preferences1.getValue("country", "default value"));*/
		 // request.getSession().setAttribute("code-"+user.getUserId(), code );
	    //request.getSession().setAttribute("countryName-"+user.getUserId(), countryName );

	}
	
	

%>
<c:choose>
	<c:when test="<%= GeoIPUsersMapPortlet.isFirstTimeLoad(renderRequest) %>">
<script type="application/javascript" src="http://jsonip.appspot.com/?callback=<portlet:namespace />getguestip"></script> 
</c:when>
</c:choose>



<script type="text/javascript">


 		var code = "<%=countryCode%>" ;
	  	var autoCode = "<%=autoSelectedCode%>" ;

	  jQuery("#location").change( function()
	   {
	 			 var uiCode = jQuery("#location option:selected").val();
	 			 var ctyName = jQuery("#location option:selected").text();
	 			 var isAutoSelected = "0";
	 			 
	 			 if( autoCode == uiCode )
	 			 {
	 			 	isAutoSelected="1";
	 			 }
	 			 else
	 			 {
	 				 isAutoSelected="0";
	 			 }
	 			 
	 			if( uiCode!= '-1' )
	 			{
		  			var imageURL = "/geoip-usersmap-portlet/images/flags/"+code.toLowerCase()+".gif";	  
		  			jQuery("#c_image").attr("src",imageURL );	
		  			  			
		  			var <portlet:namespace />ajax = new AjaxUtils();
		  			var parameters = {} ;
		  			parameters["code"]= uiCode;
		  			parameters["countryName"]= ctyName;
		  			parameters["cmd"]= "changeLocation";
		  			parameters["isAuto"]= isAutoSelected;
		  			<portlet:namespace />ajax.parameters = parameters;
		  			<portlet:namespace />ajax.callbackfunc="reloadPage";
		  			<portlet:namespace />ajax.actionRequest("get" );
		  			
		  			
		  			//alert("1");
		  			// Liferay.trigger('selectedLocation',{"code": uiCode,"countryName": ctyName, "cmd" : "changeLocation" });
		  			/*//jQuery("body").append("<form id='form1'></form>");
		  			//jQuery("#form1").submit();*/
		  			//window.location.reload();
		  		}
		  		else
		  		{
		  			window.location.reload();
		  		}
	  			
	  			
	  			
	  	
	   });	  
	   
	  
	   jQuery("#location option[value='"+autoCode+"']").attr("selected","selected");
	    var ctryName = jQuery("#location option:selected").text();
	    
	     jQuery("#location option[value='"+autoCode+"']").remove();
	   
		var my_options = jQuery("#" + jQuery("#location").attr('id') + ' option');	
								  
		my_options.sort(function(a,b) 
				{
				        if (a.text > b.text) return 1;
				        else if (a.text < b.text) return -1;
				        else return 0;
   				});
  
   		jQuery('#location').empty();
   		jQuery('#location').append( "<option value='"+autoCode+"' >" + ctryName +" </option>" );
   		jQuery('#location').append( "<option value='-1' > ----------------------- </option>" );
   		jQuery('#location').append( my_options );
   		
   		 jQuery("#location option[value='"+code+"']").attr("selected","selected");
	     ctryName = jQuery("#location option:selected").text();

  
  		jQuery("#"+jQuery('#location').attr('id')+" option").attr('selected', false);		
		
		
		
			//alert( code );   
	   jQuery("#location option[value='"+code+"']").attr("selected","selected");
	   //var ctryName = jQuery("#location option:selected").text();
	
	  
	   	
	   	var msg = " You have manually selected your current location as <b>" + "<%=CountryCodes.getCountry(countryCode)%>" ;
	   	msg+="</b><br>If this is incorrect, please use the dropdown below to set the correct country. ";
		var loc = jQuery("#location option:selected").text();
		var indexx = loc.search("Aut");
		//alert( ' indexx : '+indexx );
		if( indexx==-1 )
		{
					jQuery("#auto_text").hide();
					jQuery("#manual_text").html( msg );
		}
	function reloadPage()
	   {
	   		//alert( ' reloadPage is called ');
	   		window.location.reload();
	   		
	   	}
	   
	   
	</script>

