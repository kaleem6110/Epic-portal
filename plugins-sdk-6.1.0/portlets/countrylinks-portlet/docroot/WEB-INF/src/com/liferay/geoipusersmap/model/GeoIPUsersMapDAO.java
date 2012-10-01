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

package com.liferay.geoipusersmap.model;

import com.liferay.geoipusersmap.util.LPortalConnectionPool;
import com.liferay.geoipusersmap.model.UserVO;
import com.liferay.geoipusersmap.model.MarkVO;
import com.liferay.geoipusersmap.model.MappingBean;

import com.liferay.util.portlet.PortletProps;
import java.io.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.util.Map;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import com.maxmind.geoip.CountryCodes;
import com.maxmind.geoip.LatLngCountryBean;
import com.maxmind.geoip.LatLngfromCountryCodeBean;


import com.liferay.portal.model.UserTracker;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;

import com.liferay.chat.service.StatusLocalServiceUtil;
import com.liferay.chat.model.Status;

/**
 * <a href="GeoIPUsersMapDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mohammed Kaleem
 *
 */

public class GeoIPUsersMapDAO {

	//constants
	private static final String _LOCALNET_ADDRESS = "192.168.0.X"; //CHANGE YOUR LOCALNET ADDRESS HERE TO IGNORE THATS ENTRIES
	
	private static final String _GET_USER_DATA = "select userId, screenName, lastLoginIP, companyid, location, latitude, longitude, firstName, lastName, is_auto, emailaddress from User_ where emailaddress<>'default@liferay.com';";
	private static final String _GET_USER_LOCATION = "select location from  User_  where userid=?;";
	
	private static final String _UPDATE_USER_LOCATION = "update User_ set location= ? where userid=?;";
	private static final String _UPDATE_IS_AUTO_LOCATION = "update User_ set is_auto= ? where userid=?;";
	private static final String _GET_MAPPING_BY_CODE = "select * from  mapping  where iso2code=?;";
	private static final String _GET_MAPPING_BY_USER = "select * from  mapping  where iso2code in ( select location from user_ where userid=? );";
	
	public static MappingBean getMappingBeanByCode( String iso2code , boolean status)
	{
		MappingBean bean =new MappingBean();
		
		System.out.println( " &&&&&&&&&&&&&&&&&  getMappingBeanByCode  START :  iso2code "+iso2code );
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{	
			
			con = LPortalConnectionPool.getConnection();
			if( status == true )
			{
				ps = con.prepareStatement(_GET_MAPPING_BY_CODE);
				ps.setString(1, iso2code );
			}
			else
			{
				ps = con.prepareStatement(_GET_MAPPING_BY_USER);
				ps.setLong(1, Long.parseLong(iso2code) );
			}
		
			rs=ps.executeQuery();
			while (rs.next()) 
			{				
				bean.setIso2Code( rs.getString(1) );
				bean.setIso3Code( rs.getString(2) );
				bean.setProjectVisa_Wiki( rs.getString(3) );
				bean.setCountryName( rs.getString(4) );
				bean.setCiaFactBook( rs.getString(5) );	
				System.out.println( " &&&&&&&&&&&&&&&&& rs.getString(1): "+rs.getString(1)+ rs.getString(2) + rs.getString(3)+ rs.getString(4));
				
				return bean;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		System.out.println( " &&&&&&&&&&&&&&&&&  getMappingBeanByCode  END : bean : "+bean );
		
		return bean;
	}
	public boolean updateUserLocation( String userId, String code, String isAutoSelected )
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			con = LPortalConnectionPool.getConnection();
			ps = con.prepareStatement(_UPDATE_USER_LOCATION);
			ps.setString(1, code);
			ps.setInt(2, Integer.parseInt(userId) );
			int res =ps.executeUpdate();
			
			System.out.println("£££££££££ _UPDATE_USER_LOCATION  res : "+res);
			
			if( isAutoSelected!=null && isAutoSelected!="" )
			{
				ps = con.prepareStatement(_UPDATE_IS_AUTO_LOCATION);
				ps.setInt(1, Integer.parseInt(isAutoSelected) );
				ps.setInt(2, Integer.parseInt(userId) );
				 res =ps.executeUpdate();
				
				System.out.println("£££££££££_UPDATE_IS_AUTO_LOCATION  res : "+res);
				
			}
			
			
			if( res >= 0 )
			{
				return true;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return false;
		
	}
	public static List<MarkVO> getUsersData() {
		List<MarkVO>  ret = new ArrayList<MarkVO>();
		List<UserVO> userList = new ArrayList<UserVO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserVO user = new UserVO();
		Location location = null;
		
		
		LookupService lookupService = null;	
		try {
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null) {			
				con = LPortalConnectionPool.getConnection();
				ps = con.prepareStatement(_GET_USER_DATA);
				rs=ps.executeQuery();
				while (rs.next()) {
					String ip = rs.getString(3);
					if( ip!=null && ip.trim().equals("127.0.0.1"))
					{
						ip="94.200.128.200";
					}
					if (ip.length()>6) {
						
						System.out.println(" FROM DB ***************************** Location : "+rs.getString(5)+" Latitude :"+rs.getString(6)+" Longitude : "+rs.getString(7) );
						String loc = rs.getString(5);
						Float lat =  rs.getString(6)!=null ? Float.parseFloat( rs.getString(6) ) : null;
						Float lng =  rs.getString(7)!=null ? Float.parseFloat( rs.getString(7) ) : null;
						
						if( loc!=null && loc!="" /*&& 2==3*/ )
						{
							CountryCodes cc = new CountryCodes();
							
							LatLngfromCountryCodeBean llcc = new LatLngfromCountryCodeBean();
							LatLngCountryBean ltbean = llcc.getBean( loc );
							
							String cName = cc.getCountry( loc ); 
							location = new Location();
							location.countryCode = loc;
							location.countryName = cName;
							
							if( lat ==null) { lat = Float.parseFloat( new Double( ltbean.Lat ).toString() ); }
							if( lng==null){ lng = Float.parseFloat( new Double( ltbean.Long ).toString() );}
							
							location.latitude = lat;
							location.longitude =lng;
							
							System.out.println(" IF *** Location : "+rs.getString(5) + location.latitude + location.longitude );
						}
						else
						{
							location = lookupService.getLocation(ip);
						}
						
						user= new UserVO(rs.getInt(1),rs.getString(2),ip, location, Integer.parseInt( rs.getString(4) ), rs.getString(8), rs.getString(9), Integer.parseInt(rs.getString(10) ), rs.getString(11) );
						userList.add(user);
					}
				}
			}
			List userIdlist = null;
		for (int i=0;i<userList.size();i++) {
			//System.out.println(" ££££££££££££££   userList.size() : "+ userList.size() );
			user = (UserVO) userList.get(i);
			boolean encontrado = false;
			int j = 0;
			MarkVO mark = new MarkVO();
			
			while ((!encontrado)&&(j<ret.size())&&(!GeoIPUsersMapDAO.isLocalIP(user.getIp()))) {
				mark = (MarkVO) ret.get(j);
				j++;
				if (user.isSameLocation(mark)) {
					encontrado=true;
					//mark.addScreenname(user.getScreenname());
					String srnName = user.getFirstName() + " "+ user.getLastName() +":DELIM:"+user.getEmailAddress();
					System.out.println("srnName : "+ srnName );
					mark.addScreenname( srnName );
					userIdlist.add( user.getUserid().toString() );
					
				}
				
			}
			boolean isLive = isLiveUser( user.getUserid(), user.getCompanyId() ); 
			
			if ((!encontrado)&&(!GeoIPUsersMapDAO.isLocalIP(user.getIp()))) {
					location=user.getLocation();
					List list = new ArrayList();
					userIdlist = new ArrayList();
					//list.add(user.getScreenname());
					list.add(user.getFirstName() + " "+ user.getLastName() +":DELIM:"+user.getEmailAddress() );
					userIdlist.add( user.getUserid().toString() );
					ret.add(new MarkVO(list,location, isLive, userIdlist , user.getIs_Auto() ) );
					System.out.println(" user.getIs_Auto() : "+ user.getIs_Auto() );
			}

			
			
		}
		} catch (Exception e) {e.printStackTrace();}
    
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return ret;
}
public static boolean isLiveUser(Integer userId, Integer companyId  )
{
	//System.out.println(" ***********  IsLive user called : userId : "+userId + " : companyId : "+companyId  );
	
	// Map<String,UserTracker> map= LiveUsers.getSessionUsers(companyId);	 
	 //Map<String,UserTracker> map=WebAppPool.get( String.valueOf(companyId), WebKeys.LIVE_SESSION_USERS);
	/* Map<String,UserTracker> map= null;
	 MethodKey key = new MethodKey("com.liferay.portal.liveusers.LiveUsers", "getSessionUsers", long.class);
     try {
    	  Object object=  PortalClassInvoker.invoke(false, key, companyId);
    	  System.out.println( " object : "+object );
    	 
     } catch (Exception e) {
        // throw new RuntimeException(e);
    	 System.out.println( " Exception : "+e );
     }
     
     System.out.println(" ***********  map : "+map  );
     
     */
	
	try
	{
	
		Status status = StatusLocalServiceUtil.getUserStatus(userId );
		System.out.println(" £££ status :"+ status );
		return status.getOnline();
	
	}
	catch(Exception e )
	{
		System.out.println( e );
	}
	 
	 
	
	return false;
}
	public static Integer getNumUsers() {
		Integer ret = 0; 
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ip = null;
		try {
			con = LPortalConnectionPool.getConnection();
			ps  = con.prepareStatement(_GET_USER_DATA);
			rs = ps.executeQuery();
			while (rs.next()) { 
				ip = rs.getString(3);
				if( ip!=null && ip.trim().equals("127.0.0.1"))
				{
					ip="94.200.128.203";
				}
				if ((ip!=null)&&(ip!="")&&(ip.length()>7)) {if(!GeoIPUsersMapDAO.isLocalIP(ip)) ret++;}
			}	
		} catch (Exception e) {e.printStackTrace();}
    		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}

		return ret;
	}

	public static boolean isLocalIP(String ip) {
		boolean ret = true;
		try{		
			String aux = _LOCALNET_ADDRESS.substring(0,_LOCALNET_ADDRESS.indexOf("X")-1);
			String[] splitedAux = aux.split("\\.");
			
			String[] splitedIP  = ip.split("\\.");

			for (int i=0;i<splitedAux.length;i++) {if (!splitedAux[i].equals(splitedIP[i])) ret=false;}
		} catch (Exception e) {e.printStackTrace();}
		return ret;
	}
	 public static MappingBean getBeanByCode(String iso2Code)
	  {

			System.out.println("getBeanByCode START : iso2Code "+ iso2Code );
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("mapping.txt");
			  System.out.println("getBeanByCode  : fstream "+ fstream );
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  MappingBean mappingBean= null;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)  
			  {
			  // Print the content on the console		
			String strArray[] = strLine.split("-");
			String iso3 =strArray[0];
			String iso2 =strArray[1];
			String cia =strArray[2];
			String cName =strArray[3];
			System.out.println ( );
			String projectVisa="";
			if (cName.indexOf(" ") != -1) 
			{
				String pArray[] = cName.split(" ");
				if (pArray != null && pArray.length > 0) 
				{
					for (int k = 0; k < pArray.length; k++) 
					{
						if (k > 0) 	projectVisa = projectVisa + "_";
						
						String s = pArray[k];
						
						if (s != null) 
						{
							if (s.equalsIgnoreCase("and")) 
							{
								s = s.toLowerCase();
							}
							else
							{
								s = s.substring(0, 1)
										.toUpperCase()
										+ s.substring(1,
												s.length())
												.toLowerCase();
							}
							projectVisa = projectVisa + s;
						}
					}
				}

			} 
			else
			{
				projectVisa = cName;
				projectVisa = projectVisa.substring(0, 1)
						.toUpperCase()
						+ projectVisa.substring(1,
								projectVisa.length())
								.toLowerCase();
				
			}
			System.out.println(iso3.trim() + "  " +iso2.trim() + " " +cia.trim() + " " +cName.trim() + "  "+ projectVisa);
					if(iso2Code.equalsIgnoreCase( iso2.trim() ) )
					{
						
						
						mappingBean = new MappingBean();
						mappingBean.setIso2Code( iso2.trim() );
						mappingBean.setIso3Code( iso3.trim() );
						mappingBean.setProjectVisa_Wiki( projectVisa );
						mappingBean.setCountryName( cName.trim() );
						mappingBean.setCiaFactBook( cia.trim() );	
						System.out.println("getBeanByCode END : iso2Code "+ iso2Code );
						return mappingBean;

					}
			
	  }
	  //Close the input stream
	  in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
		 System.out.println("getBeanByCode END : iso2Code "+ iso2Code );
			 
		return null;
	  }

	
}
