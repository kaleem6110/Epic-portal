
<%
	/**
	 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
	 *
	 * Permission is hereby granted, free of charge, to any person obtaining a copy
	 * of this software and associated documentation files (the "Software"), to deal
	 * in the Software without restriction, including without limitation the rights
	 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	 * copies of the Software, and to permit persons to whom the Software is
	 * furnished to do so, subject to the following conditions:
	 *
	 * The above copyright notice and this permission notice shall be included in
	 * all copies or substantial portions of the Software.
	 *
	 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	 * SOFTWARE.
	 */
%>

<%@ include file="/init.jsp"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.IOException"%>

<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO"%>
<%@ page import="com.liferay.geoipusersmap.model.MarkVO"%>

<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="com.maxmind.geoip.CountryCodes"%>



<c:choose>
	<c:when test="<%=Validator.isNotNull(license)%>">
		<style>
.ie6 .maps-content img {
	behavior: expression(this.pngSet = true);
}
</style>
		<script
			src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=license%>"
			type="text/javascript"></script>

		<script src="http://gmap.nurtext.de/js/jquery.gmap-1.1.0-min.js"
			type="text/javascript"></script>

		<script
			src="http://gmaps-utility-library-dev.googlecode.com/svn/tags/mapiconmaker/1.1/src/mapiconmaker.js"
			type="text/javascript"></script>
		<%
					float mapZoomLat = 0;
					float mapZoomLong = 0;
					boolean isUserFound = false;
					boolean isUserMarker = false;
					boolean anotherFlag = true;
					String cntCode = "";
					String jsonStr = "{ markers: [";

					User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
					/*System.out.println( " £££   userId : "+user.getUserId() + "email : "+user.getEmailAddress( ) );
					PortletPreferences prefs = renderRequest.getPreferences();	
					String userValue = prefs.getValue("Location-"+user.getUserId(), "");	
					System.out.println( " KALEEM : userValue :"+ userValue );*/
					String emailAddress = user.getEmailAddress();
					int isDefaultUser = 1;
					if (emailAddress != null
							&& !emailAddress.equals("default@liferay.com")) 
					{
						isDefaultUser = 0;

					}
					List<MarkVO> markList = new ArrayList<MarkVO>();
					markList = GeoIPUsersMapDAO.getUsersData();

					Integer num_users = GeoIPUsersMapDAO.getNumUsers();
					int num_marks = markList.size();
		%>
		<table style="width: 100%;">
			<tr>
				<td><h4
						style="width: 100%; text-align: left; color: #4444FF; margin: 0; padding: 0;">&nbsp;EPIC Community</h4></td>
				<td><h4
						style="width: 100%; text-align: right; color: #4444FF; margin: 0; padding: 0;"><%=num_users%> EPIC users spread over
						<%=num_marks%> 	countries
					</h4></td>
			</tr>
		</table>

		<div id="<portlet:namespace />map"
			style="height: <%=height%>px; width: 100%;"></div>

		<script type="text/javascript">

		var <portlet:namespace />globalMarkers = {};
		var <portlet:namespace />singleMarker={};
		var <portlet:namespace />centerAddress = "address: \"Dubai, UAE\"";
		
		var <portlet:namespace />isDefaultUser = "<%=isDefaultUser%>";
		 if (GBrowserIsCompatible())
		 {
			var <portlet:namespace />map;
			var <portlet:namespace />longitude = "";
			var <portlet:namespace />latitude = "";
			var <portlet:namespace />myJSONtext="";
			var <portlet:namespace />mapZoomLat ="";
			var <portlet:namespace />mapZoomLong ="";		
			
			//<portlet:namespace />map = new GMap(document.getElementById("<portlet:namespace />map"));  
			/**********************************************************
			<portlet:namespace />map.addControl(new GSmallMapControl());
			<portlet:namespace />map.addControl(new GMapTypeControl());	
			<portlet:namespace />map.addControl(new GMapTypeControl());
			<portlet:namespace />map.addControl(new GSmallMapControl());
			////////////////////////////////////////////////////////////*/ 
			        
           <portlet:namespace />map = jQuery("#<portlet:namespace />map");		
	
			// Create the marker and corresponding information window
			function <portlet:namespace />createInfoMarker(point, address, isUserMarker) 
			{
				var <portlet:namespace />marker;
				var <portlet:namespace />newIcon = MapIconMaker.createMarkerIcon({width: 20, height: 34, primaryColor: "#0000FF", cornercolor:"#0000FF"});
				if( <portlet:namespace />isUserMarker == true )
				{
					 <portlet:namespace />marker = new GMarker(point, {icon: newIcon});
				}
				else
				{
					 <portlet:namespace />marker = new GMarker(point);
				}
				GEvent.addListener(<portlet:namespace />marker, "click", function() { marker.openInfoWindowHtml(address); } );
				return marker;
			}
		
		


<%
String allUserIds = new Long(user.getUserId()).toString() ;
for (int i = 0; i < num_marks; i++) 
				{
						allUserIds+="DELIM";
						MarkVO mark = (MarkVO) markList.get(i);
						List<String> userIdList = mark.getUserIdList();

						String htmlCode = mark.getHtmlCode();
						Float longitude = mark.getLongitude();
						Float latitude = mark.getLatitude();
						cntCode = mark.getLocation().countryCode;
						CountryCodes cc = new CountryCodes();
						String ccName = cc.getCountry(cntCode);

						Integer is_auto = mark.getIs_Auto();
						

						for (int j = 0; j < userIdList.size(); j++) 
						{
							allUserIds = allUserIds + " : "+ userIdList.get(j) ;
							if (userIdList.get(j).equals( new Long(user.getUserId()).toString())) 
							{
								System.out.println(" User found : "+ user.getUserId() + " is_auto : "+ is_auto );
								isUserFound = true;
								isUserMarker = true;
								if (is_auto == 1) 
								{%>
																	
								<portlet:namespace />centerAddress = "latitude:\""+  "<%=latitude%>" +"\", longitude:\""+  "<%=longitude%>" + "\" , zoom:10";
								//alert('if '+ <portlet:namespace />centerAddress );
			
								<%} 
								else 
								{%>
									<portlet:namespace />centerAddress = "address:\""+  "<%=ccName%>"+"\" , zoom:5" ;
									//alert('else '+ <portlet:namespace />centerAddress );
								<%}
								
							}
						}
						if (isUserFound) 
						{
							mapZoomLat = mark.getLocation().latitude;
							mapZoomLong = mark.getLocation().longitude;
							isUserFound = false;
						}%>
						
						var allUserIds = "<%=allUserIds%>" + " :LEN:" +"<%=userIdList.size()%>";
						
						//alert( allUserIds );
						
			   		 var <portlet:namespace />point = new GPoint (<%=longitude%> , <%=latitude%>);   		
					 var <portlet:namespace />address = "<%=htmlCode%>";	
					 <portlet:namespace />longitude = "<%=longitude%>";
					 <portlet:namespace />latitude = "<%=latitude%>";		
					
					 var <portlet:namespace />is_auto = <%=is_auto%>;
		 
		
		
		 if( <portlet:namespace />is_auto==1 )
		 {
		 	<portlet:namespace />myJSONtext += " { latitude: "+ <portlet:namespace />latitude +" ,	longitude: "+ <portlet:namespace />longitude +" , html: \""+ <portlet:namespace />address+"\"  }";
		 }
		 else
		 {
		 	<portlet:namespace />myJSONtext += " { address: \""+ "<%=ccName%>" +"\" ,	 html: \""+ <portlet:namespace />address+"\" }";
		 }
		 
		 if( <%=i%>!=( <%=num_marks%>-1 ) )
		 {
		 	 <portlet:namespace />myJSONtext += ",";
		 }
		var <portlet:namespace />marker;
		
		<%if (isUserFound) {
							isUserFound = false;
							anotherFlag = false;%> //marker = createInfoMarker(point, address, true );
			
			
			
		<%} else {%>
		
			
			//marker = createInfoMarker(point, address);
		<%}%>
		
		//<portlet:namespace />map.addOverlay( <portlet:namespace />marker);
		<portlet:namespace />mapZoomLat ="<%=mapZoomLat%>";
		 <portlet:namespace />mapZoomLong ="<%=mapZoomLong%>";	
		
		 
<%}%>

        if( <portlet:namespace />isDefaultUser ==1 )
        {
        	<portlet:namespace />myJSONtext="";
        	<portlet:namespace />centerAddress= "address: \"Dubai, UAE\" , zoom:2" ;  
        }
       // alert('final' + <portlet:namespace />centerAddress );
        var <portlet:namespace />myObject = eval('( { markers: [' + <portlet:namespace />myJSONtext + ']  , '+<portlet:namespace />centerAddress+'  }  )');
        
        
       		 jQuery("#<portlet:namespace />map").gMap( <portlet:namespace />myObject  );
        
		
		
	
		}
		
		
		/********************************************************************************************************************
		//<portlet:namespace />map.centerAndZoom(new GPoint(80,10), 15);
		//<portlet:namespace />map.setCenter(new GLatLng( <portlet:namespace />mapZoomLat,<portlet:namespace />mapZoomLong), 5);		
		/*jQuery("#<portlet:namespace />map").gMap({ markers: [		{ latitude: 25.26439999999,	longitude: 55.311700002, html: "_latlng" } ,
                           											{ address: "Sharjah, UAE",   html: "The place I live" },
                            										{ address: "Langenargen, Germany", html: "_address" } 
                            									],
                  										//address: "Dubai, UAE",  zoom: 10 });                  										
                  										latitude: 25.26439999999,	longitude: 55.311700002, zoom: 10 }); */                  										
        // jQuery("#<portlet:namespace />map").gMap({ markers: <portlet:namespace />globalMarkers, address: "Dubai, UAE",  zoom: 10 });
       	// var myJSONtext = " { latitude: 25.26439999999,	longitude: 55.311700002, html: \"hello\" }, { address:\"Riyadh, KSA\", html: \"hello Riyadh\" } ";
      
       // <portlet:namespace />centerAddress = eval('('+ <portlet:namespace />centerAddress+')' );
       	/********************************************************************************************************************/
		</script>



	</c:when>
	<c:otherwise>
		<liferay-ui:message
			key="please-contact-the-administrator-to-setup-this-portlet" />
	</c:otherwise>
</c:choose>




