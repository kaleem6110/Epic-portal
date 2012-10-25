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
import com.liferay.geoipusersmap.model.LiferayUsersMapDAO;
/*

 * Retrieve several attributes of a particular entry.
 *
 * [equivalent to getattrs.c in Netscape SDK]
 */
public class LDAPUtil 
{
	public static String screenName=null;
	
	public static boolean isPhoneAdded=false;
	
	public static final int listType_business=11006;
	public static final int listType_mobile=11008;
	public static final int listType_thuraya=11012;
	public static final int listType_wave=11010;
	public static final int listType_foodsat=11007;
	
	public static final int addressType_personal=11002;
	public static final int addressType_business=11000;
	public static final int addressType_other=11001;
	
	public static User user =null;
	
	
	public  static DirContext  getLDAPContextByAddress(Address address)
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPContextByAddress #####################");
		
		
		DirContext ctx = null;
		try{
			
			User user = UserLocalServiceUtil.getUserById( address.getUserId() );
			 System.out.println( " user "+user );
			
			 ctx = getLDAPContext( user );
			}catch(Exception e)
			{//e.printStackTrace();
				}
			
		
		System.out.println(" ############## END  LDAPUtil.getLDAPContextByAddress #####################");
		
		return ctx;

	}
	public  static DirContext  getLDAPContextByContact(Contact contact)
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPContextByContact #####################");
		DirContext ctx = null;
		try{
		User user = UserLocalServiceUtil.getUserByContactId( contact.getContactId() );
		System.out.println( " user "+user );
		 ctx = getLDAPContext( user );
		}catch(Exception e){// e.printStackTrace();
			
		}
		System.out.println(" ############## END  LDAPUtil.getLDAPContextByContact #####################");
		
		return ctx;

	}
	
	public static DirContext  getLDAPContext(User user)
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPContext #####################"+user.getScreenName()+"getPasswordUnecrypted "+user.getPasswordUnencrypted() );
		Hashtable env = new Hashtable(5, 0.75f);	
		
		DirContext ctx = null;
		if( user!=null)System.out.println(" # "+user.getPasswordUnencrypted()  );

		// env.put(LdapContext.CONTROL_FACTORIES,
		// conf.getProperty("ldap.factories.control"));
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://ldap-dev.globalepic.lu:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL,
				//"cn=wfp-write,ou=ldapAccess,dc=emergency,dc=lu");
				"uid="+user.getScreenName()+",ou=users,ou=people,dc=emergency,dc=lu");
		//env.put(Context.SECURITY_CREDENTIALS, "My3CatsOnATree");
		String pwd = LiferayUsersMapDAO.getPlainPassword( user.getUserId() );
		System.out.println("##### pwd : plain : "+pwd );
		
		env.put(Context.SECURITY_CREDENTIALS, pwd );
		env.put(Context.STATE_FACTORIES, "PersonStateFactory");
		env.put(Context.OBJECT_FACTORIES, "PersonObjectFactory");

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,
				"ldap://ldap-dev.globalepic.lu:389/dc=emergency,dc=lu");

		// env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);
		/* Specify host and port to use for directory service */
		// env.put(Context.PROVIDER_URL, Env.MY_SERVICE);
		try 
		{
			/* get a handle to an Initial DirContext */
			//DirContext ctx = new InitialDirContext(env);	
			System.out.println(" ############## END  LDAPUtil.getLDAPContext #####################");
			ctx=  new InitialDirContext(env);
		}
		catch(Exception e){	//e.printStackTrace();
			
		}
		
		return ctx;

	}

	public static LDAPUserInfo getLDAPUserInfoByContact( Contact contact )
	{
		
		System.out.println(" ############## START  LDAPUtil.getLDAPUser #####################");
		User user = null;
		try{
		 user = UserLocalServiceUtil.getUserByContactId( contact.getContactId() );
		 System.out.println(" user"+user );
		}catch(Exception e){ //e.printStackTrace();
			
		}
				
		System.out.println(" ############## END  LDAPUtil.getLDAPUser #####################");
		return getLDAPUserInfo(user);
	}
	
	public static LDAPUserInfo getLDAPUserInfo( User user )
	{
		
		System.out.println(" ############## START  LDAPUtil.getLDAPUser ##################### user"+user.getPasswordUnencrypted());
		
		LDAPUserInfo ldapUserInfo = new LDAPUserInfo();
		
		/* get a handle to an Initial DirContext */
		DirContext ctx = getLDAPContext( user );			
		
		//Attributes attrs = sr.getAttributes();			
		Attributes attrs = getAllAttributes ( ctx );
		
		populateLDAPUser(ldapUserInfo, attrs ) ;
		
		System.out.println(" ############## END  LDAPUtil.getLDAPUser #####################");
		return ldapUserInfo;
	}
	
	public static Attributes getAllAttributes(DirContext ctx)
	{
		System.out.println(" ############## START  LDAPUtil.getAllAttributes ##################### screenName :"+screenName);
		// Specify the search filter
		Attributes attrs = null;
		try
		{
		String FILTER = "(&(objectClass=top) ((uid=" + screenName + ")))";

		// limit returned attributes to those we care about
		String[] attrIDs = { "sn", "cn", "mobile", "postalCode","personalTitle","localityName",
				"telephoneNumber", "street", "communicationUri", "o", "c" ,"uid"};

		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(attrIDs);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		System.out.println(" 1");
		// Search for objects using filter and controls
		NamingEnumeration answer = ctx.search(
				"ldap://ldap-dev.globalepic.lu:389/uid=" + screenName
						+ ",ou=users,ou=people,dc=emergency,dc=lu", FILTER,
				ctls);
		System.out.println("2");
		SearchResult sr = (SearchResult) answer.next();
		System.out.println(" 3");
		
		 attrs = sr.getAttributes();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		System.out.println(" ############## END  LDAPUtil.getAllAttributes #####################");
		
		return attrs;
	}
	public static void populateLDAPUser(LDAPUserInfo ldapUserInfo, Attributes attrs ) 
	{	
		System.out.println(" ############## START  LDAPUtil.populateLDAPUser #####################");		
		try
		{
			//screenName = attrs.get("uid").toString();
			ldapUserInfo.surName = attrs.get("sn").toString();
			ldapUserInfo.givenName = attrs.get("cn").toString();
			
			Object mobileObj = attrs.get("mobile");
			Object postalCodeObj = attrs.get("postalCode");
			Object organizationObj = attrs.get("mobile");
			Object streetObj = attrs.get("street");
			Object countryObj = attrs.get("c");
			Object personalTitleObj = attrs.get("personalTitle");
			Object localityNameObj = attrs.get("localityName");
			
			if( mobileObj!=null) ldapUserInfo.mobile = mobileObj.toString();
			if( postalCodeObj!=null) ldapUserInfo.postalCode = postalCodeObj.toString().replace("postalCode:","");
			if( organizationObj!=null) ldapUserInfo.organization = organizationObj.toString();
			if( streetObj!=null) ldapUserInfo.street = streetObj.toString().replace("street:","");
			if( countryObj!=null) ldapUserInfo.country = countryObj.toString();
			if( personalTitleObj!=null) ldapUserInfo.personalTitle = personalTitleObj.toString();
			if( localityNameObj!=null) ldapUserInfo.city = localityNameObj.toString();
						
			System.out.println(" ############## ldapUserInfo  "+ldapUserInfo );
			System.out.println(" ############## ldapUserInfo.surName  "+ldapUserInfo.surName + " communicationUri :"+ attrs.get("communicationUri") );
			
			List<String> commuriList = null;
			
			if( attrs.get("communicationUri")!=null )
			{
				commuriList = getAttributeValueListByName( attrs, "communicationUri" );				
			}			
			for (int j = 0; commuriList!=null&& j < commuriList.size(); j++)
			{
				String temp = commuriList.get(j);	
				System.out.println(" ############## temp "+temp );
				if (temp.indexOf("gtalk") != -1) {
					ldapUserInfo.gtalk = temp.replace("gtalk:chat?jid=","");					
				} else if (temp.indexOf("msnim") != -1) {
					ldapUserInfo.msn = temp.replace("msnim:chat?contact=","");					
				} else if (temp.indexOf("skype") != -1) {
					ldapUserInfo.skype = temp.replace("skype:","");					
				} else if (temp.indexOf("sip") != -1) {
					ldapUserInfo.sip = temp.replace("sip:","");					
				} else if (temp.indexOf("VHF") != -1) {
					ldapUserInfo.vhf = temp.replace("VHFcallsign:","");					
				}
			}
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.populateLDAPUser #####################");
		
	}
	public static List<String> getAttributeValueListByName(Attributes attrs, String attributeName )
	{
		System.out.println(" ############## START  LDAPUtil.getAttributeValueListByName #####################"+attributeName);
		List<String> commuriList = null;	
		int i=0;
		try
		{
			if(attrs.get(attributeName)!=null )
			{
				commuriList = new ArrayList<String>();					
				NamingEnumeration nString = attrs.get(attributeName).getAll();			
				while (nString.hasMore()) 
				{
					commuriList.add(attrs.get(attributeName).get(i).toString());
					nString.next();		
					i=i+1;
				}
			}
		}
		catch(Exception e){ e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.getAttributeValueListByName #####################"+commuriList );
		return commuriList;
	}
	public static void beforeUpdateContact( Contact contact)
	{
		System.out.println(" ############## START  LDAPUtil.beforeUpdateContact ##################### contact: "+contact );		
		try 
		{		
			 DirContext ctx = getLDAPContextByContact(contact);		
			 Attributes attrs = getAllAttributes( ctx );			 
			 List<String> commuriList = getAttributeValueListByName( attrs ,"communicationUri");		
			 if( commuriList!=null)
			 {
			 	for (int j = 0; j < commuriList.size(); j++)
			 	 {
					String temp = commuriList.get(j);	
					if (temp.indexOf("gtalk") != -1){ temp = temp.replace("gtalk:chat?jid=","");contact.setIcqSn( temp ); }
					else if (temp.indexOf("msnim") != -1){ temp = temp.replace("msnim:chat?contact=",""); contact.setMsnSn( temp );	}
					else if (temp.indexOf("skype") != -1) { temp = temp.replace("skype:","");contact.setSkypeSn( temp );}
					else if (temp.indexOf("sip") != -1 ){ temp = temp.replace("sip:",""); contact.setAimSn( temp );}
					else if (temp.indexOf("VHF") != -1  ){ temp = temp.replace("VHFcallsign:",""); contact.setJabberSn( temp );	}					
				}
			}			
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.beforeUpdateContact #####################contact: "+contact );		
		
	}
	public static void updatePassword(User user)
	{
		
		System.out.println(" ############## START  LDAPUtil.updatePassword ##################### " );		
		try 
		{	
			
			 DirContext ctx = getLDAPContext(user);	
			 System.out.println(" 1"+ user.getPassword());
			 ModificationItem[]  mods= new ModificationItem[1];	
			 Attribute mod0 = new BasicAttribute("userPassword");	
			 mod0.add("{SHA}"+user.getPassword() );
			 System.out.println(" user.getPassword() "+user.getPassword() +" user.getPasswordUnencrypted()"+user.getPasswordUnencrypted());
			 System.out.println(" 2");
			 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			 System.out.println(" 3"+user.getScreenName() );
			// System.out.println(" ############## 44444444 password :"+ user.getPassword() );
			 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+user.getScreenName() +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			// LiferayUsersMapDAO.storePassword( user.getUserId(), user.getPasswordUnencrypted());
			 System.out.println(" 4444444444");
			// System.out.println(" ############## 44444444 password :"+ user.getPassword() );
			 
			
			 System.out.println(" 1  isPwdModified");
			boolean isPwdModified = LiferayUsersMapDAO.storePassword( user.getUserId(),user.getPasswordUnencrypted() );
				
				
				 System.out.println(" 22222222  ");
				 LiferayUsersMapDAO.updateOriginalPassword( user.getUserId(),user.getPassword() );
				 
				
				// System.out.println(" 3 isPwdModified"+isPwdModified);
			 
		}
		catch(Exception e){ e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.updatePassword ##################### " );		
		
	}
	public static void updateContact(Contact  contact, boolean isBefore)
	{
		
		System.out.println(" ############## START  LDAPUtil.updateContact ##################### contact: "+contact );		
		try 
		{	
			
			 DirContext ctx = getLDAPContextByContact(contact);	
			
			 ModificationItem[]  mods= new ModificationItem[1];			
			 //getIcqSn -> gtalk, sip/lync ->aim 
			 Attribute mod0 = new BasicAttribute("communicationUri");			 
			 String skype = contact.getSkypeSn();
			 String sip = contact.getAimSn();
			 String gtalk = contact.getIcqSn();
			 String msn = contact.getMsnSn();
			 String vhf = contact.getJabberSn();
			 
			 /*mod0.add(contact.getJabberSn());	
			 mod0.add(contact.getIcqSn());
			 mod0.add(contact.getMsnSn());	
			 mod0.add(contact.getSkypeSn());	
			 mod0.add(contact.getAimSn());	*/
			 if(contact.getJabberSn()!=null&& contact.getJabberSn()!="" ) mod0.add("VHFcallsign:"+contact.getJabberSn());	
			 if(contact.getIcqSn()!=null&& contact.getIcqSn()!="" ) mod0.add("gtalk:chat?jid="+contact.getIcqSn());
			 if(contact.getMsnSn()!=null&& contact.getMsnSn()!="" )  mod0.add("msnim:chat?contact="+contact.getMsnSn());	
			 if(contact.getSkypeSn()!=null&& contact.getSkypeSn()!="" ) mod0.add("skype:"+contact.getSkypeSn());	
			 if(contact.getAimSn()!=null&& contact.getAimSn()!="" )  mod0.add("sip:"+contact.getAimSn());
			 
			 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+screenName +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			 System.out.println(" ############## 44444444 contact :"+ contact );
			 //mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);
			// mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
			
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.updateContact #####################");
		
	}
	
	public static List<String> getLDAPPhoneByScreenName( User user )
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPPhoneByScreenName ##################### user: "+user );
		List<String> phoneList = null;
		try 
		{
			DirContext ctx = getLDAPContext( user );			 
			Attributes attrs = getAllAttributes( ctx );			
			phoneList = getAttributeValueListByName( attrs ,"telephoneNumber");
		}
		catch(Exception e) { e.printStackTrace(); }			 
	   System.out.println(" ############## END  LDAPUtil.getLDAPPhoneByScreenName ##################### user: "+user );	
	   
	   return phoneList;
	}
	public static void importPhones(User user )
	{
		System.out.println(" ############## START  LDAPUtil.importLDAPPhones user "+user);
		try
 		{
 			LDAPUtil.screenName=user.getScreenName();
 			
 			//List<Address> addressList = user.getAddresses();
 			//Contact contact = user.getContact(); 
 			//List<Phone> phoneList = user.getPhones();
 			//user.setJobTitle("Web Developer");				
			List<ListType> phoneTypes = ListTypeServiceUtil.getListTypes(Contact.class.getName()
			       + ListTypeConstants.PHONE);
			int phoneTypeId = 0;
			
			// find personal and fax type phones
			for (ListType phoneType : phoneTypes) 
			{
			     String phoneTypeName = phoneType.getName();
			     System.out.println(" phoneTypeName : "+phoneTypeName );
			     if ("personal".equals(phoneTypeName)) { phoneTypeId = phoneType.getListTypeId();   }
			    // if ("personal-fax".equals(phoneTypeName)) { phoneTypeId = phoneType.getListTypeId();     }
			 }	
			List<String> phoneList = getLDAPPhoneByScreenName( user );
			System.out.println(" phoneList from LDAP : "+ phoneList );
			
			if( phoneList!=null && phoneList.size()>0)
			{
			for( String phn : phoneList )
			{		
				
				if( phn.indexOf("Office")!=-1 || phn.indexOf("office")!=-1)
				{
					String phnArray [] = phn.split("Office:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("office:");	
					String number = phnArray[1];
					String ext="";
					if( number.indexOf("x")!=-1)
					{
						phnArray = number.split("x");
						ext=phnArray[1];
						number=phnArray[0];
					}
					System.out.println(" phn : "+ phn + ": number : "+number+ " ext :"+ext +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, ext, listType_business, true);		            
				}
				else if( phn.indexOf("WAVE")!=-1|| phn.indexOf("Wave")!=-1)
				{
					String phnArray [] = phn.split("WAVE:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("Wave:");			
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number+ " phoneTypeId :"+ phoneTypeId + "phnArray:"+phnArray ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_wave, true);		            
				}
				else if( phn.indexOf("Foodsat")!=-1|| phn.indexOf("foodsat")!=-1)
				{
					String phnArray [] = phn.split("Foodsat:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("foodsat:");			
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number+ " phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_foodsat, true);		            
				}
				else if( phn.indexOf("Thuraya")!=-1 || phn.indexOf("thuraya")!=-1)
				{
					String phnArray [] = phn.split("Thuraya:");
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_thuraya, true);		            
				}
				else if( phn.indexOf("Mobile")!=-1 || phn.indexOf("mobile")!=-1 )
				{
					String phnArray [] = phn.split("Mobile:");
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_mobile, true);		            
				}
					
				
			}
			
	        System.out.println("Handling bug LPS-17381: user.getContactId() " + user.getContactId());
	        isPhoneAdded = true;    
			}
           
 			System.out.println(" #####  END LDAPUtil.importLDAPPHones  ");
 			//super.exportToLDAP(user);
 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();	
 		}

		
	}
	public static User getUserByAddress( Address address) throws Exception
	{
		System.out.println(" ############## START  LDAPUtil.getUserByAddress address "+address);
		User user = UserLocalServiceUtil.getUserById( address.getUserId() );
		System.out.println(" ############## END  LDAPUtil.getUserByAddress address "+address);
		
		return user;
		
	}
	public static void importAddresses(Address address )
	{
		System.out.println(" ############## START  LDAPUtil.importAddresses address "+address);
		try
 		{
			User user = getUserByAddress( address );
			LDAPUserInfo  ldapuser = getLDAPUserInfo( user);
			/*System.out.println(ldapuser.street + " ldapuser.street "+ addressType_business );
			List<Address> addressList = user.getAddresses();
			long addressId = user.getContactId();
			if(addressList!=null || addressList.size()>0 )
			{
				for( Address address : addressList )
				{
					addressId = address.getAddressId();
					System.out.println("  inside for : addressId : "+ addressId );
				}
			}
			
			System.out.println("  outisde : addressId : "+ addressId );
			Address address = AddressLocalServiceUtil.createAddress( addressId );*/
			
			String street = ldapuser.street;
			String postalCode = ldapuser.postalCode;
			if( street!=null && street!="" ) street = street.replaceAll("street:","");
			if( postalCode!=null && postalCode!="" ) postalCode = postalCode.replaceAll("postalCode:","");
				
			address.setStreet1(ldapuser.street );
			address.setStreet2("" );
			address.setStreet3("" );
			address.setCity(ldapuser.city );
			address.setZip(ldapuser.postalCode );	
		//	getCountryIdByCode( ldapuser.country );
			address.setCountryId(getCountryIdByCode( ldapuser.country ) );
			//address.setRegionId(1001 );
			//address.setTypeId(addressType_personal );
			//address.setClassPK( Contact.class.getName());
			address.setPrimary(true);
			//address.setMailing(true);
			///address.setCompanyId( user.getCompanyId() );
			
			System.out.println(ldapuser.street + " ldapuser.street : address "+ address  );
			
			//AddressLocalServiceUtil.addAddress(user.getUserId(), Address.class.getName(), PortalUtil.getClassNameId(Address.class.getName()), ldapuser.street, "", "", "DUBAI", ldapuser.postalCode, 1, 217, addressType_personal, true, true);
			//AddressLocalServiceUtil.addAddress( address );
 		}
		catch (Exception e)
 		{
 			e.printStackTrace();	
 		}
		System.out.println(" ############## END  LDAPUtil.importAddresses address ");
	}
	public static void exportAddress(Address address )
	{		
		System.out.println(" ############## START  LDAPUtil.exportPhones ##################### address: "+address );		
		try 
		{
			/* get a handle to an Initial DirContext */
			DirContext ctx = getLDAPContextByAddress(address);			
			ModificationItem[] mods = new ModificationItem[ 4];		 
			 Attribute mod0 = new BasicAttribute("postalCode");
			 Attribute mod1 = new BasicAttribute("street");
			 Attribute mod2 = new BasicAttribute("localityName");
			 Attribute mod3 = new BasicAttribute("c");
		
			 mod0.add( address.getZip() );
			 mod1.add( address.getStreet1() );
			 mod2.add( address.getCity() );			
			 mod3.add(getCountryCodeById( address.getCountryId() ) );
			 
			 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			 mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
			 mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod2);
			 mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod3);
			 
			 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+screenName +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			 
			 
		}
		catch(Exception e){e.printStackTrace(); }
	}
	public static void exportPhones(User user )
	{		
		System.out.println(" ############## START  LDAPUtil.exportPhones #####################" );		
		try 
		{
			List<Phone>  phoneList = user.getPhones();
			
			/* get a handle to an Initial DirContext */
			DirContext ctx = getLDAPContext(user);			
			ModificationItem[] mods = new ModificationItem[ 1];		 
			 Attribute mod0 = new BasicAttribute("telephoneNumber");
			
			//Attributes attrs = getAllAttributes( ctx );
			int i=0;
			for( Phone phone : phoneList)
			 {
				 System.out.println(" ############## phone type Id :"+phone.getTypeId() );
				 String ext = phone.getExtension();
				 if(ext==null)ext="";
				 switch(phone.getTypeId())
				 {
				 	case listType_business :	mod0.add("Office:"+phone.getNumber().trim() ); break;
					case listType_mobile   :	mod0.add("Mobile:"+phone.getNumber().trim() ); break;
					case listType_wave :		mod0.add("WAVE:"+phone.getNumber().trim() );	  break;
					case listType_thuraya :		mod0.add("Thuraya:"+phone.getNumber().trim() ); break;												
					case listType_foodsat :		mod0.add("Foodsat:"+phone.getNumber().trim() );break;
				 }
				
				 i++;
			 }			 
			 System.out.println(" ############## mod0 "+mod0 );			
			 if(i>0)
			 {
				 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+screenName +",ou=users,ou=people,dc=emergency,dc=lu", mods);
				  
			 } 
			 System.out.println(" ############## 44444444");
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.exportPhones #####################");
		
	}
	public static long getCountryIdByCode(String code)
	 {
		System.out.println(" ############## START  LDAPUtil.getCountryIdByCode ##################### code "+code);
		  try
		  {
			  // Open the file that is the first 
			  // command line parameter
			  URL url = new URL("http://www-dev.globalepic.lu/html/css/liferay_countries.txt");
			  BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)  
			  {
					  // Print the content on the console
					 // System.out.println (" ££££££££££"+ strLine);
					  String strArray [] = strLine.split(";");
					  String ccode = strArray[2];	 
					  if( ccode!=null&& ccode.equalsIgnoreCase( code ) ) 
					  {  
						  String countryId = strArray[0];					  		
					  	  return Long.valueOf(countryId).longValue();
					  }
			  }
			  //Close the input stream
			  br.close();
			    
		  }
		  catch (Exception e)
		  {//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		   }
		  System.out.println(" ############## END  LDAPUtil.getCountryIdByCode #####################");
		  return 217;
	  }
	public static String getCountryCodeById(long cid)
	 {
		System.out.println(" ############## START  LDAPUtil.getCountryIdByCode #####################cid"+cid);
		  try
		  {
			  // Open the file that is the first 
			  // command line parameter
			  URL url = new URL("http://www-dev.globalepic.lu/html/css/liferay_countries.txt");
			  BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
              
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)  
			  {
					  // Print the content on the console
					 // System.out.println (" ££££££££££"+ strLine);
					  String strArray [] = strLine.split(";");
					  String ccode = strArray[2];
					  String countryId = strArray[0];		
					  if( cid == Long.valueOf(countryId).longValue() ) return ccode.toLowerCase();
			  }
			  //Close the input stream
			  br.close();
			    
		  }
		  catch (Exception e)
		  {//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		   }
		  System.out.println(" ############## END  LDAPUtil.getCountryCodeById #####################");
		  return "ae";
	  }
	public static void updateUser(User user)
	{
		
		System.out.println(" ############## START  LDAPUtil.updateUser ##################### " );		
		try 
		{	
			System.out.println(" user"+user.getPassword() +" unencrypt: "+user.getPasswordUnencrypted() );
			
			 DirContext ctx = getLDAPContext(user);	
			 
			 System.out.println(" 1"+ user.getPassword());
			 
			 ModificationItem[]  mods= new ModificationItem[3];	
			 Attribute mod0 = new BasicAttribute("sn");	
			 Attribute mod1 = new BasicAttribute("cn");	
			 Attribute mod2 = new BasicAttribute("title");	
			 
			 mod0.add(user.getLastName() );
			 mod1.add(user.getFirstName() );
			 mod2.add(user.getJobTitle() );
			 
			 System.out.println(" 2");
			 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
			 mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
			 mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod2);
			 
			 System.out.println(" 3"+user.getScreenName() );
			 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+user.getScreenName() +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			 System.out.println(" 4");
		
			 
		}
		catch(Exception e){ e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.updateUser ##################### " );		
		
	}
}
