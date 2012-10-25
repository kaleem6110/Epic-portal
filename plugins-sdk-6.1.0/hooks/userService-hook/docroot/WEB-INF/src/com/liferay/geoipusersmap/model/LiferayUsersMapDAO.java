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
import javax.portlet.RenderRequest;


import com.liferay.util.portlet.PortletProps;

import javax.portlet.PortletSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Random;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;


/**
 * <a href="LiferayUsersMapDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class LiferayUsersMapDAO {

	
	private static final String _UPDATE_PASSWORD = "update User_ set plain= ? where userid=?;";
	private static final String _GET_PASSWORD_BY_ID ="select plain from user_ where userid=?;"; 
	private static final String _UPDATE_ORIGINAL_PASSWORD ="update User_ set password_= ? where userid=?;";
	private static String algorithm = "DESede";
	private static String DB_NAME = "liferay-portal";
	private static String DB_USER_NAME = "kmohammed";
	private static String DB_PWD = "welcome";
	private static String connectionURL = "jdbc:postgresql://localhost:5432/liferay-portal";

	

	public static boolean storePassword( long userId, String pwd )
	{
		System.out.println(" ################################################");
		System.out.println(" START LiferayUsersMapDAO.storePassword ######## pwd : "+pwd);
		System.out.println(" ################################################");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isStored = false;
		
		try 
		{
//			Class.forName("org.postgresql.Driver");
//			System.out.println(" #11111111");	
//			con = DriverManager.getConnection (connectionURL,DB_USER_NAME,DB_PWD );
//			System.out.println(" #con"+con);
			con = LPortalConnectionPool.getConnection();
			//Context ctx = new InitialContext();   
			//DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Liferay");   
			//con = ds.getConnection(); 
			
			ps = con.prepareStatement(_UPDATE_PASSWORD);
			System.out.println(" #ps"+ps);
			
			// Key key = KeyGenerator.getInstance(algorithm).generateKey();
			// Cipher  cipher = Cipher.getInstance(algorithm);
			 
		    //  byte[] encryptionBytes = encrypt(pwd.trim(),key, cipher);
		    //  String encryptedPwd = new String(encryptionBytes,"UTF-8");
		      
		      
			ps.setString(1, pwd.trim() );
			System.out.println(" kkkkk");
			ps.setLong(2, userId  );

			System.out.println(" lllllps:"+ps);
			int res =ps.executeUpdate();

			System.out.println(" mmmmm");
			
			System.out.println("£££££££££  res : "+res);
			
			if( res >= 0 )
			{
				isStored= true;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ################################################");
		System.out.println(" END LiferayUsersMapDAO.storePassword ######## UserID : "+userId);
		System.out.println(" ################################################");
		
		return isStored;
		
	}
	public static String getPlainPassword( long userId )
	{
		System.out.println(" ################################################");
		System.out.println(" START LiferayUsersMapDAO.getPlainPassword ######## UserID : "+userId);
		System.out.println(" ################################################");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String encryptionBytesFromDB = null;
		
		try 
		{
			
//			Class.forName("org.postgresql.Driver");
//			con = DriverManager.getConnection (connectionURL,DB_USER_NAME,DB_PWD );
			con = LPortalConnectionPool.getConnection();
			//Context ctx = new InitialContext();   
			//DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Liferay");   
			// con = ds.getConnection(); 
			ps = con.prepareStatement(_GET_PASSWORD_BY_ID );
			
			ps.setLong(1, userId  );
	
			rs=ps.executeQuery();
			while (rs.next()) 
			{				
				 encryptionBytesFromDB = rs.getString(1);
				System.out.println(" encryptionBytesFromDB "+encryptionBytesFromDB);
				
			//	Key  key = KeyGenerator.getInstance(algorithm).generateKey();
				//Cipher  cipher = Cipher.getInstance(algorithm);
			//	String pwd = decrypt(encryptionBytesFromDB.getBytes(), key, cipher);
				//System.out.println("£££££££££  res  pwd : "+pwd );
				//ps.close();
				//con.close();
				
						
			}
			
			System.out.println("£££££££££  res : "+rs);
			
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ################################################");
		System.out.println(" END LiferayUsersMapDAO.getPlainPassword ######## UserID : "+userId);
		System.out.println(" ################################################");
		
		return encryptionBytesFromDB;
		
	}
	public static boolean updateOriginalPassword( long userId, String pwd )
	{
		System.out.println(" ################################################");
		System.out.println(" START LiferayUsersMapDAO.updateOriginalPassword ######## UserID : "+userId);
		System.out.println(" ################################################");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isUpdated = false;
		
		try 
		{
			
//			Class.forName("org.postgresql.Driver");
//			con = DriverManager.getConnection (connectionURL);
			con = LPortalConnectionPool.getConnection();
			//Context ctx = new InitialContext();   
			//DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Liferay");   
			// con = ds.getConnection(); 
			
			ps = con.prepareStatement(_UPDATE_ORIGINAL_PASSWORD);
		
		      
		      
			ps.setString(1, pwd.trim() );
			ps.setLong(2, userId  );
			int res =ps.executeUpdate();
			
			System.out.println("£££££££££  res : "+res);
			
			if( res >= 0 )
			{
				
				isUpdated= true;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		System.out.println(" ################################################");
		System.out.println(" END LiferayUsersMapDAO.updateOriginalPassword ######## UserID : "+userId);
		System.out.println(" ################################################");
		
		return isUpdated;
		
	}
	 private static byte[] encrypt(String pwd,Key key, Cipher cipher) throws InvalidKeyException, BadPaddingException,
     IllegalBlockSizeException
     {
		 System.out.println(" ########## START LiferayUsersMapDAO encrypt ################pwd"+pwd);
		 cipher.init(Cipher.ENCRYPT_MODE, key);
		 byte[] inputBytes = pwd.getBytes();
		 System.out.println(" ############### END LiferayUsersMapDAO encrypt ##########pwd encrypted"+inputBytes);
		 
		 return cipher.doFinal(inputBytes);
     }
	 private static String decrypt(byte[] encryptionBytes, Key key, Cipher cipherr) throws InvalidKeyException,
     BadPaddingException, IllegalBlockSizeException 
     {
		 System.out.println(" ########## START LiferayUsersMapDAO decrypt ################encryptionBytes:"+encryptionBytes);
		 cipherr.init(Cipher.DECRYPT_MODE, key);
		 byte[] recoveredBytes = cipherr.doFinal(encryptionBytes);
		 System.out.println(" recoveredBytes "+recoveredBytes );
		 String recovered = new String(recoveredBytes);
		 System.out.println(" ########## START LiferayUsersMapDAO decrypt ################recovered"+recovered);
		 
		 return recovered;
      }
	

}
