<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ page import="com.maxmind.geoip.CountryCodes"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.*"%>
<portlet:defineObjects />



<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>

<select id="rss_location" style="width: 220px;">

<%
					boolean isAutoSelected = false;
					CountryCodes countryCodesObj = new CountryCodes();
					Map<String, String>  countryCodesMap = countryCodesObj.lookup;
					for ( Map.Entry<String, String> entry : countryCodesMap.entrySet() ) 
					{   				
						
						%>
    							<option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>
    						<%
    				}
    				
    				%> </select>
    				
    				<div > </div>
    				<iframe id="rss_location_feed" src="" width="100%"  height="800px" border=no scrolling=no></iframe>
    				
    				
<script type="text/javascript" >
			
				
			var my_options = jQuery("#" + jQuery("#rss_location").attr('id') + ' option');	
			  
			my_options.sort(function(a,b) 
			{
       			if (a.text > b.text) return 1;
       			else if (a.text < b.text) return -1;
       			else return 0;
			});

			jQuery('#rss_location').empty().append( my_options );
			
			  jQuery("#rss_location").change( function()
	 		  {
	 			
	 			 var ctyName = jQuery("#rss_location option:selected").text();
	 			ctyName=  ctyName.replace(" ", "_");
	 		
	 			  jQuery("#rss_location_feed").attr("src","http://www.irinnews.org/RSS/"+ctyName+".xml" );
	 			 
	 		});
	 			 
	 			 
		</script>

    				</script>