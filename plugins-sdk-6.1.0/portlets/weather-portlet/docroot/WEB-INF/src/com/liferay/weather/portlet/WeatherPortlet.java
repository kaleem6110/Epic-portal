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

package com.liferay.weather.portlet;

import com.liferay.portal.kernel.servlet.SessionErrors;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import javax.portlet.PortletSession;

/**
 * @author Brian Wing Shun Chan
 */
public class WeatherPortlet extends MVCPortlet {

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		if (actionRequest.getPortletMode().equals(PortletMode.EDIT)) {
			updatePreferences(actionRequest, actionResponse);
		}
	}

	protected void updatePreferences(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		PortletPreferences preferences = actionRequest.getPreferences();
		//actionRequest.getPortletSession().geAttribute()
		String[] zips = StringUtil.split(
			ParamUtil.getString(actionRequest, "zips"), "\n");
		
		int size= zips.length;


		boolean celsius = ParamUtil.get(actionRequest, "celsius", true);

		preferences.setValues("zips", zips);
		preferences.setValue("celsius", String.valueOf(celsius));

		try {
			preferences.store();
		}
		catch (ValidatorException ve) {
			SessionErrors.add(
				actionRequest, ValidatorException.class.getName(), ve);

			return;
		}

		PortletConfig portletConfig = getPortletConfig();

		SessionMessages.add(
			actionRequest, portletConfig.getPortletName() + ".doEdit");
	}
	public static String getCapitalCity (String code) 
	{
		try{
        URL url = new URL("http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/CapitalCity?sCountryISOCode="+code);
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());
      	NodeList nodes = doc.getElementsByTagName("string");
    	Element line = (Element) nodes.item(0);  

    	 Node child = line.getFirstChild();
    	 if (child instanceof CharacterData) 
   	 	{
     		 CharacterData cd = (CharacterData) child;      
    		 return  cd.getData() ;
    	}
		}catch(Exception e){}
    	 return "";
}

}