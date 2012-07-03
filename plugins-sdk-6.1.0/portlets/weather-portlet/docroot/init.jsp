<%
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.Randomizer" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>
<%@ page import="com.liferay.weather.model.Weather" %>
<%@ page import="com.liferay.weather.util.WeatherUtil" %>

<%@ page import="java.util.Enumeration" %>

<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.ValidatorException" %>
<%@ page import="javax.portlet.WindowState" %>

<%@ page import="javax.portlet.PortletSession"%>


<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>


<%@ page import="java.io.File"%>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.*"%>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@ page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="org.xml.sax.SAXParseException"%>





<portlet:defineObjects />

<%
WindowState windowState = renderRequest.getWindowState();

PortletPreferences preferences = renderRequest.getPreferences();

User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();

String countryName=null;
String[] zips = preferences.getValues("zips", new String[0]);

try
{
	URL oracle = new URL("http://www.geoiptool.com/webapi.php");
	URLConnection yc = oracle.openConnection();
	BufferedReader in = new BufferedReader(new InputStreamReader(
                            yc.getInputStream()));
	String inputLine;


	while ((inputLine = in.readLine()) != null) 
	{	            
		if( inputLine.contains("IP:") )
		{

			System.out.println(inputLine.trim() );

		}
		if( inputLine.contains("imgx") )
		{
			String[] s = inputLine.split("imgx\">");
			//System.out.println(s[1] );
			countryName = s[1] ;
		}
	}

	in.close();
	
}catch(Exception e){}


boolean fahrenheit = GetterUtil.getBoolean(preferences.getValue("fahrenheit", StringPool.BLANK));
%>