/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.loginpost.action;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.model.GeoIPUsersMapDAO;
import com.liferay.model.LDAPUserInfo;
import com.liferay.model.LDAPUtil;

public class CustomPostLogin extends Action {
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {
	//do something before the login
    	HttpSession session = request.getSession();
    	

    		String userId = request.getRemoteUser();
    		System.out.println("##### CustomPostLogin:: KuserId: "+ userId );

    	if( session.getAttribute("LIFERAY_SHARED_globalAttribute")==null)
    	session.setAttribute("LIFERAY_SHARED_globalAttribute", ""+userId); 
    	else
    	session.setAttribute("LIFERAY_SHARED_globalAttribute", "newcontextScope"); 
    	
    	boolean onlineFlag = GeoIPUsersMapDAO.updateUserStatus( userId , true );
    	System.out.println("##### CustomPostLogin:: onlineFlag: "+ onlineFlag );
    	
    	LDAPUtil util  =  new LDAPUtil();
    	LDAPUserInfo ldapUser = util.getLDAPUserInfo( GeoIPUsersMapDAO.getScreenName(userId));
    			
    	if( ldapUser!=null )	GeoIPUsersMapDAO.importIMfromLDAP( userId, ldapUser);
    	
    }
}
