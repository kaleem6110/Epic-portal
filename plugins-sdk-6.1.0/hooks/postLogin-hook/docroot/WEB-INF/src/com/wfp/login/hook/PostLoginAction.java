package com.wfp.login.hook;

import com.liferay.portal.kernel.events.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.InetAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.model.User;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;

import java.util.*;
import java.sql.DriverManager;

import java.io.*;
import java.net.*;

import java.io.*;
public class PostLoginAction extends Action 
{

	private static final String UPDATE_IP = "update user_ set lastloginip=?  where userid=?";
	private static final String UPDATE_LOC = "update user_ set  location=? where userid=?";

	public void run(HttpServletRequest request, HttpServletResponse res)
	{
		System.out.println("## PostLoginAction : My custom login action");
		

		Long userId = PortalUtil.getUserId(request);
		
		Long userID = (Long) request.getSession().getAttribute(com.liferay.portal.kernel.util.WebKeys.USER_ID);
		
		//User user = UserLocalServiceUtil.getUserById(userId.longValue());
		
		System.out.println(" PostLoginAction :  userId : "+userId+ "userID :"+userID );
		
		
		
		Object isSet = request.getSession().getAttribute("isSet");

		if (userId != null && userId > 0 ) 
		{

			System.out.println(" PostLoginAction : forwarded : "
					+ request.getHeader("x-forwarded-for"));

			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try 
			{
				ClassLoader classLoader = getClass().getClassLoader();

				Properties _props = new Properties();

				_props.load(classLoader
						.getResourceAsStream("lportal-connection-pool.properties"));

				String driverClass = _props.getProperty("driver.class");
				String jdbcUrl = _props.getProperty("jdbc.url");
				String userName = _props.getProperty("user");
				String password = _props.getProperty("password");

				Properties connectionProps = new Properties();

				connectionProps.put("user", userName);
				connectionProps.put("password", password);

				con = DriverManager.getConnection(jdbcUrl, connectionProps);

				System.out.println("   Hook con : " + con);
				CountryCodes cc = new CountryCodes();
				
				
				InetAddress thisIp = InetAddress.getLocalHost();
				     System.out.println("IP:"+thisIp.getHostAddress());

				// Print out the IP address of the caller
				String ip = thisIp.getHostAddress() ;//getUserIP();
				
				System.out.println("IP address : " + ip);
				ps = con.prepareStatement(UPDATE_IP);

				ps.setLong(2, userId);
				ps.setString(1, ip);
				int result = ps.executeUpdate();
				System.out.println("1 result : " + result + " userId : "
						+ userId);
				
				ps = con.prepareStatement(UPDATE_LOC);
				ps.setLong(2, userId );
				ps.setString(1, cc.getCountryCodeByName( getUserCountry() ));
				 result = ps.executeUpdate();
				System.out.println("2  result : " + result + " userId : "
						+ userId);
				
				request.getSession().setAttribute("isSet", ""+userId );

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
						ps.close();
					}
				} catch (Exception e) {
				}
			}
		}
		else
		{
			System.out.println(" UserId is : "+userId + " so unable to proceed");
		}
	}

	public static String getUserCountry() throws Exception {
		System.out
				.println(" #########~START getUserCountry() ##################");
		URL oracle = new URL("http://www.geoiptool.com/webapi.php");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		String countryName = null;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("IP:")) {

				System.out.println(inputLine.trim());

			}
			if (inputLine.contains("imgx")) {
				String[] s = inputLine.split("imgx\">");
				System.out.println(s[1]);
				countryName = s[1];
			}
		}
		in.close();
		System.out.println(" #########~END getUserCountry()  countryName : "
				+ countryName + "##################");

		return countryName;

	}

	public static String getUserIP() throws Exception {
		System.out.println(" #########~START getUserIP() ##################");
		String ip = null;
		URL oracle = new URL("http://www.geoiptool.com/webapi.php");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("IP:")) {
				System.out.println(inputLine.trim());
				String[] s = inputLine.split("IP:");
				System.out.println(s[1]);
				ip = s[1];
				System.out.println(" getUserIP from URL : ip : " + ip);
				if( ip!= null)ip = ip.trim();

			}

		}
		in.close();
		System.out.println(" #########~END getUserIP() ##################");
		
		return ip;

	}

}
