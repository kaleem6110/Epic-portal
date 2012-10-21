/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.sample.hook;
import com.sample.util.*;
import com.liferay.portal.ModelListenerException;

import com.liferay.portal.model.User;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.AddressWrapper;
import com.liferay.portal.model.BaseModelListener;

import com.liferay.portal.kernel.language.LanguageUtil;

import com.liferay.portal.kernel.util.GetterUtil;



import com.liferay.portal.service.MembershipRequestLocalServiceUtil;

import com.liferay.portal.service.ServiceContext;

import com.liferay.portal.service.ServiceContextThreadLocal;

import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.List;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class MyContactListener extends BaseModelListener<Contact> 
{
	public static boolean flag=true;
	
	 public void onBeforeCreate(Contact contact) throws ModelListenerException 
	 {
		 	System.out.println(" #####   START MyContactListener.onBeforeCreate : contact"+ contact );
		 	LDAPUserInfo ldapUser = LDAPUtil.getLDAPUserInfo();
		 	System.out.println( " ldapUser "+ ldapUser );
		 	contact.setFacebookSn(ldapUser.facebook);
			contact.setYmSn(ldapUser.ym);
			contact.setSkypeSn(ldapUser.skype);
			contact.setMySpaceSn(ldapUser.mySpace);
			contact.setTwitterSn(ldapUser.twitter);
			contact.setMsnSn(ldapUser.msn);
			contact.setIcqSn(ldapUser.gtalk);
			contact.setAimSn(ldapUser.sip);
			contact.setJabberSn(ldapUser.vhf);		
			
		 	super.onBeforeCreate(contact);
			System.out.println("##### END  MyContactListener.onBeforeCreate : contact"+ contact );
	 }
	 public void onAfterCreate(Contact contact) throws ModelListenerException 
	 {
		 System.out.println(" #####  start MyContactListener.onAfterCreate : contact"+ contact );
		 
		 	if( (contact.getJabberSn()==null||contact.getJabberSn()!="") &&
		 			(contact.getMsnSn()!=null||contact.getMsnSn()!="") && 
		 			(contact.getMySpaceSn()!=null||contact.getMySpaceSn()!="") &&
		 			(contact.getSkypeSn()!=null||contact.getSkypeSn()!="") &&
		 			(contact.getIcqSn()!=null||contact.getIcqSn()!="") 
		 			
		 			 )
		 	{
		 		
		 	LDAPUserInfo ldapUser = LDAPUtil.getLDAPUserInfo();
		 	System.out.println( " ldapUser "+ ldapUser );
		 	contact.setFacebookSn(ldapUser.facebook);
			contact.setYmSn(ldapUser.ym);
			contact.setSkypeSn(ldapUser.skype);
			contact.setMySpaceSn(ldapUser.mySpace);
			contact.setTwitterSn(ldapUser.twitter);
			contact.setMsnSn(ldapUser.msn);
			contact.setIcqSn(ldapUser.gtalk);
			contact.setAimSn(ldapUser.sip);
			contact.setJabberSn(ldapUser.vhf);
			super.onAfterCreate(contact);
		
			
		 	}
		 	System.out.println(" ##### end  MyContactListener.onAfterCreate : contact"+ contact );
		 
		
	 }
	 public void   onBeforeUpdate(Contact contact) throws ModelListenerException 
		{
			
				System.out.println(" #####  KALEEM START MyContactListener.onBeforeUpdate : Contact"+ contact );
				LDAPUtil.beforeUpdateContact(contact );
				//LDAPUtil.updateContact(contact,true );
				
				System.out.println(" #####   END MyContactListener.onBeforeUpdate : Contact"+ contact );
		}
	 public void   onAfterUpdate(Contact contact) throws ModelListenerException 
	{
		
			System.out.println(" #####   START MyContactListener.onAfterUpdate : Contact"+ contact );
			LDAPUtil.updateContact(contact,false );
			
			System.out.println(" #####   END MyContactListener.onAfterUpdate : Contact"+ contact );
	}

	
}