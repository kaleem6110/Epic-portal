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

package com.liferay.weather.util;
import com.maxmind.geoip.LPortalConnectionPool;
import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.liferay.util.portlet.PortletProps;
import com.liferay.portal.kernel.util.StringPool;
import java.io.File;
import java.net.*;
import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import com.maxmind.geoip.LookupService;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.weather.model.Weather;

/**
 * @author Brian Wing Shun Chan
 */
public class WeatherUtil {
	
	private static final String _GET_USER_LOC = "select  location from User_ where userId=?;";
	

	public static Weather getWeather(String zip) {
		WebCacheItem wci = new WeatherWebCacheItem(zip);

		String key = WeatherUtil.class.getName() + StringPool.PERIOD + zip;

		try {
			return (Weather)WebCachePoolUtil.get(key, wci);
		}
		catch (ClassCastException cce) {
			WebCachePoolUtil.remove(key);

			return (Weather)WebCachePoolUtil.get(key, wci);
		}
	}
	public static String getCapitalServiceByCode( String code)
	{
		String city = "";
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
	    		   city= cd.getData() ;
	    	}
			}catch(Exception e){  city= ""; e.printStackTrace(); }
	    	
		
		
		return city;
		
		
	}
	public static String getCapitalService( String countryName)
	{
		String city = "";
		String code = CountryCodes.getCountryCodeByName( countryName );
		city = getCapitalServiceByCode( code );	
		return city;
		
		
	}
	/**
	* Constructs a Document object by reading from an input stream.
	*/
	    public static Document parse (InputStream is) {
	        Document ret = null;
	        DocumentBuilderFactory domFactory;
	        DocumentBuilder builder;
	 
	        try {
	            domFactory = DocumentBuilderFactory.newInstance();
	            domFactory.setValidating(false);
	            domFactory.setNamespaceAware(false);
	            builder = domFactory.newDocumentBuilder();
	 
	            ret = builder.parse(is);
	        }
	        catch (Exception ex) {
	            System.out.println("unable to load XML: " + ex);
	        }
	        return ret;
	    }
	    public static String getUserLocation(String userId) {
	    	System.out.println("START WeatherUtil : getUserLocation: " + userId);
			String ret = null; 
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			LookupService lookupService = null;	
			try {
				lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
				if (lookupService != null) 
				{
					System.out.println("1");
					con = LPortalConnectionPool.getConnection();
					System.out.println("2");
					ps  = con.prepareStatement(_GET_USER_LOC);
					ps.setInt(1,Integer.parseInt(userId) ); 
					System.out.println("3");
					rs = ps.executeQuery();
					System.out.println("5");
					while (rs.next()) { 
						ret = rs.getString(1);
						System.out.println("END WeatherUtil : getUserLocation: " + ret);
						
					}	
					
				}
			
			} catch (Exception e) {e.printStackTrace();}
	    		
			finally {LPortalConnectionPool.cleanUp(con, ps, rs);}

			return ret;
		}
	    public static List<String> getAllCitiesByCountry( String ctryName)
		{
	    	List<String> citiesList = new ArrayList<String>();
			try{
		        URL url = new URL("http://www.webservicex.net/globalweather.asmx/GetCitiesByCountry?CountryName="+ctryName);
		        URLConnection conn = url.openConnection();

		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document doc = builder.parse(conn.getInputStream());
		      	
		        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		        NodeList nList = doc.getElementsByTagName("Table");
		      	System.out.println("-----------------------");
		      	for (int temp = 0; temp < nList.getLength(); temp++) 
		      	{
		      		 
		 		   Node nNode = nList.item(temp);
		 		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		  
		 		      Element eElement = (Element) nNode;
		 		      String city = getTagValue("City", eElement) ;
		 		      System.out.println("city : " + temp + " : "+city );
		 		      citiesList.add( city );
		  
		 		   }
		 		}
		 	  } catch (Exception e) {
		 		e.printStackTrace();
		 	  }
		    	
			
			
			return citiesList;
			
			
		}
	    private static String getTagValue(String sTag, Element eElement) {
	    	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	     
	            Node nValue = (Node) nlList.item(0);
	     
	    	return nValue.getNodeValue();
	      }
	    static Node getNode(String tagName, NodeList nodes) {
	        for ( int x = 0; x < nodes.getLength(); x++ ) {
	            Node node = nodes.item(x);
	            if (node.getNodeName().equalsIgnoreCase(tagName)) {
	                return node;
	            }
	        }
	     
	        return null;
	    }
	     
	    static String getNodeValue( Node node ) {
	        NodeList childNodes = node.getChildNodes();
	        for (int x = 0; x < childNodes.getLength(); x++ ) {
	            Node data = childNodes.item(x);
	            if ( data.getNodeType() == Node.TEXT_NODE )
	                return data.getNodeValue();
	        }
	        return "";
	    }
	     
	    static String getNodeValue(String tagName, NodeList nodes ) {
	        for ( int x = 0; x < nodes.getLength(); x++ ) {
	            Node node = nodes.item(x);
	            if (node.getNodeName().equalsIgnoreCase(tagName)) {
	                NodeList childNodes = node.getChildNodes();
	                for (int y = 0; y < childNodes.getLength(); y++ ) {
	                    Node data = childNodes.item(y);
	                    if ( data.getNodeType() == Node.TEXT_NODE )
	                        return data.getNodeValue();
	                }
	            }
	        }
	        return "";
	    }

}