/**
 * 
 */
package com.sample.util;

/**
 * @author kaleem.mohammed
 *
 */
import java.io.*;
import java.net.URL;
import java.util.Hashtable;

import java.util.Enumeration;
import java.util.List;

import java.util.ArrayList;
import javax.naming.*;
import javax.naming.directory.*;
import com.sample.util.LDAPUserInfo;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Phone;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
/*

 * Retrieve several attributes of a particular entry.
 *
 * [equivalent to getattrs.c in Netscape SDK]
 */
public class LiferayDBUtil
{
	public static final String STORE_PASSWORD ="insert into user_ (plain) values( ? ) where userid=?";
	
	
}