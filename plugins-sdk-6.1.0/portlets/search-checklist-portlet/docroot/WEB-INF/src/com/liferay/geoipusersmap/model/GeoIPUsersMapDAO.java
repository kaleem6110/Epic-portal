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

import com.liferay.util.portlet.PortletProps;
import java.util.HashMap;

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



/**
 * <a href="GeoIPUsersMapDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author MOHAMMED KALEEM
 *
 */

public class GeoIPUsersMapDAO {

	//constants
	private static final String _LOCALNET_ADDRESS = "192.168.0.X"; //CHANGE YOUR LOCALNET ADDRESS HERE TO IGNORE THATS ENTRIES
	
	private static final String _GET_PUBLIC_CHECKLISTS = "select chklist_id, chklist_name from checklist where is_global=?;";
	private static final String _GET_USER_CHECKLISTS = "select chklist_id, chklist_name from checklist where owner_Id=? and is_global=0";
	private static final String _SAVE_USER_CHECKLISTS = "insert into checklist(chklist_name, owner_Id, description,ownername ) values (?,?,?,?);";
	private static final String _GET_FULL_NAME_BY_USER_ID="select firstname,lastname from user_ where userId=?;";
	
	public static Map<String, String> fetchChecklistByUserId(String userId)
	{
		System.out.println(" ####################### START GeoIPUsersMapDAO.fetchChecklistByUserId : userId"+userId);
		Map<String, String> checklistMap = new HashMap<String,String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LookupService lookupService = null;	
		try 
		{
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null)
			{			
				con = LPortalConnectionPool.getConnection();
				ps = con.prepareStatement(_GET_USER_CHECKLISTS);
				ps.setLong(1, Long.parseLong(userId));
				rs=ps.executeQuery();
				while (rs.next()) 
				{						
					checklistMap.put(rs.getString(1), rs.getString(2));
					System.out.println( rs.getString(1) + rs.getString(2) );
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ####################### END GeoIPUsersMapDAO.fetchChecklistByUserId : userId"+userId);
		return checklistMap;
}
	public static Map<String, String> fetchPublicChecklist()
	{
		System.out.println(" ####################### START GeoIPUsersMapDAO.fetchPublicChecklist") ;
		Map<String, String> checklistMap = new HashMap<String,String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LookupService lookupService = null;	
		try 
		{
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null)
			{			
				con = LPortalConnectionPool.getConnection();
				ps = con.prepareStatement(_GET_PUBLIC_CHECKLISTS);
				ps.setInt(1, new Integer(1) );
				rs=ps.executeQuery();
				while (rs.next()) 
				{						
					checklistMap.put(rs.getString(1), rs.getString(2));
					System.out.println( rs.getString(1) + rs.getString(2) );
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ####################### END GeoIPUsersMapDAO.fetchPublicChecklist : ");
		return checklistMap;
}
	public static boolean saveChecklist(String userId, String name, String description)
	{
		System.out.println(" ####################### START GeoIPUsersMapDAO.saveChecklist : ");
		boolean isSuccess=false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LookupService lookupService = null;	
		String fullName = null;
		try 
		{
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null)
			{			
				con = LPortalConnectionPool.getConnection();
				ps= con.prepareStatement(_GET_FULL_NAME_BY_USER_ID);
				ps.setLong(1, Long.parseLong(userId));
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					 fullName =  rs.getString(1) + " "+rs.getString(2) ;
				
				}	
				
				ps = con.prepareStatement(_SAVE_USER_CHECKLISTS);
				ps.setLong(2, Long.parseLong(userId));
				ps.setString(1, name.trim());
				ps.setString(3, description.trim());
				ps.setString(4, fullName.trim());
				
				int result =ps.executeUpdate();
				if( result > 0 )
				{
					System.out.println(" result : "+result);
					isSuccess = true;
				}
						
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ####################### END GeoIPUsersMapDAO.saveChecklist : ");
		return isSuccess;
}

}
