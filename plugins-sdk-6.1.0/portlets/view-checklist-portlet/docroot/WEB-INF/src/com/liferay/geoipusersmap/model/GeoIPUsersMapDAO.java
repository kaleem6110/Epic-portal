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

import com.liferay.geoipusersmap.model.CheckListBean;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * <a href="GeoIPUsersMapDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author MOHAMMED KALEEM
 *
 */

public class GeoIPUsersMapDAO {

	//constants
	private static final String _LOCALNET_ADDRESS = "192.168.0.X"; //CHANGE YOUR LOCALNET ADDRESS HERE TO IGNORE THATS ENTRIES

	private static final String _GET_USER_CHECKLISTS = "select chklist_id, chklist_name from checklist where owner_Id=?;";
	private static final String _GET_USER_CHECKLISTS_BY_CHKLIST_ID = "select chklist_id, chklist_name, description,ownername, is_global,createdDate,owner_id from checklist where  chklist_id=?";
	private static final String _DELETE_CHKLIST_BY_ID ="delete from checklist where chklist_id=?;";
	private static final String _SHARE_CHKLIST_BY_ID ="update checklist set is_global=? where chklist_id=?;";
	private static final String _UPDATE_CHKLIST_BY_ID="update checklist set description=? where chklist_id=?;";
	
	public static Map<String, String> fetchChecklistByUserId(String userId)
	{
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
		
		return checklistMap;
}
	public static CheckListBean fetchChecklistByChkId(String chklistId)
	{
		Map<String, String> checklistMap = new HashMap<String,String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LookupService lookupService = null;	
		CheckListBean chkBen= new CheckListBean();
		 SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null)
			{			
				con = LPortalConnectionPool.getConnection();
				ps = con.prepareStatement(_GET_USER_CHECKLISTS_BY_CHKLIST_ID);
				ps.setLong(1, Long.parseLong(chklistId));
				rs=ps.executeQuery();
				while (rs.next()) 
				{						
					chkBen.setId( Long.parseLong(rs.getString(1))) ;
					chkBen.setName( rs.getString(2));
					chkBen.setDescription( rs.getString(3));
					chkBen.setOwnerName( rs.getString(4));
					chkBen.setIsGlobal( rs.getString(5 ) );
					
					chkBen.setCreatedDate( dateformatMMDDYYYY.format( rs.getDate(6 ) )  );
					chkBen.setOwnerId( rs.getString(7 ) );
					System.out.println( "IN DAO :"+rs.getString(2) + rs.getString(3)+ rs.getString(4) );
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return chkBen;
}
	public static void deleteChecklistById(String chklist_id)
	{
		System.out.println( " #### START GeoIPUsersMapDAO.deleteChecklistById : chklist_id"+chklist_id );
		boolean isSuccess = false;
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
				ps = con.prepareStatement(_DELETE_CHKLIST_BY_ID);
				ps.setLong(1, Long.parseLong(chklist_id));
				int i=ps.executeUpdate();
				if(i>0)
				{						
					isSuccess = true;
					System.out.println( " #### Deleted successfully: chklist_id"+chklist_id );
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println( " #### END GeoIPUsersMapDAO.deleteChecklistById : isSuccess"+isSuccess );
		
}
	public static void shareCheckListWithPublic(String chklist_id, String status)
	{
		System.out.println( " #### START GeoIPUsersMapDAO.shareCheckListWithPublic : chklist_id"+chklist_id );
		boolean isSuccess = false;
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
				ps = con.prepareStatement(_SHARE_CHKLIST_BY_ID);
				ps.setLong(2, Long.parseLong(chklist_id));
				ps.setInt(1, Integer.parseInt(status ));
				int i=ps.executeUpdate();
				if(i>0)
				{						
					isSuccess = true;
					System.out.println( " #### shareCheckListWithPublic successfully: chklist_id"+chklist_id );
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println( " #### END GeoIPUsersMapDAO.shareCheckListWithPublic : isSuccess"+isSuccess );
		
}
	public static CheckListBean updateChecklist(String chklist_id, String description)
	{
		System.out.println( " #### START GeoIPUsersMapDAO.updateChecklist : chklist_id"+chklist_id );
		boolean isSuccess = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LookupService lookupService = null;	
		CheckListBean bean = null;
		try 
		{
			lookupService = new LookupService(PortletProps.get("maxmind.database.file"),LookupService.GEOIP_MEMORY_CACHE);
			if (lookupService != null)
			{			
				con = LPortalConnectionPool.getConnection();
				ps = con.prepareStatement(_UPDATE_CHKLIST_BY_ID);			
				ps.setString(1, description );
				ps.setLong(2, Long.parseLong(chklist_id));
				int i=ps.executeUpdate();
				if(i>0)
				{						
					isSuccess = true;
					System.out.println( " #### updateChecklist successfully: chklist_id"+chklist_id );
					bean = fetchChecklistByChkId( chklist_id);
				
				}			
			}
		} 
		catch (Exception e) {e.printStackTrace();}
		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println( " #### END GeoIPUsersMapDAO.updateChecklist : isSuccess"+isSuccess );
		
		return bean;
		
}

}
