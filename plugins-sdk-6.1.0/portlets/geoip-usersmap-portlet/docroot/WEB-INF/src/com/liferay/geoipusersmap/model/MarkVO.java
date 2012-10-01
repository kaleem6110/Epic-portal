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

import java.util.ArrayList;
import java.util.List;

import com.maxmind.geoip.Location;

/**
 * <a href="MarkVO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class MarkVO {

	//Private properties
	private List screennameList;
	private Location location;
	private Integer is_auto;
	
	private boolean isLive;
	private List<String> userIdList;
	
	
	
	
	

	//Constructores
	public MarkVO() {
	}
	public MarkVO (List screennameList, Location location, boolean isLive){
		this.screennameList 	= screennameList;
		this.location 		= location;
		this.isLive 		= isLive;
		
		
		
	}

	public MarkVO (List screennameList, Location location, boolean isLive, List<String> userIdList){
		this.screennameList 	= screennameList;
		this.location 		= location;
		this.isLive 		= isLive;
		this.userIdList 		= userIdList;
		
		
	}
	public MarkVO (List screennameList, Location location, boolean isLive, List<String> userIdList, Integer is_auto){
		this.screennameList 	= screennameList;
		this.location 		= location;
		this.isLive 		= isLive;
		this.userIdList 		= userIdList;
		this.is_auto 		= is_auto;
		
		
	}

	//GETTERS & SETTERS
	public Integer getIs_Auto () {
		return is_auto;
	}
	
	public void setIs_Auto (Integer is_auto) {
		this.is_auto=is_auto;
	}
	public List<String> getUserIdList() {
		return userIdList;
	}
	
	public void setUserIdList(List userIdList) {
		this.userIdList=userIdList;
	}
	public List getScreennameList () {
		return screennameList;
	}
	
	public void setScreennameList(List screennameList) {
		this.screennameList=screennameList;
	}

	public Location getLocation () {
		return this.location;
	}

	public void setLocation (Location location) {
		this.location = location;
	}	

	public Float getLongitude () {
		return this.location.longitude;
	}
	
	public Float getLatitude () {
		return this.location.latitude;
	}
	
	public void addScreenname (String screenname) {
		this.screennameList.add(screenname);
	}
	public boolean getIsLive()
	{
		return this.isLive;
		
	}
	public void setIsLive(boolean isLive) {
		this.isLive=isLive;
	}

	
	public String getHtmlCode () 
	{
		if( this.location.city== null )
		{
			this.location.city="";
		}
		String ret = "<img alt='flag' src='/geoip-usersmap-portlet/images/flags/"+this.location.countryCode.toLowerCase()+".gif'>&nbsp;&nbsp;<span style='font-size:0.9em;'><b>"+this.location.countryName+"</b>,&nbsp;"+this.location.city+"</span><br><br><div style='font-size:0.8em;'>";
		String aux = "";
		for (int i=0;i<this.screennameList.size();i++) {
			aux = (String) this.screennameList.get(i);
			System.out.println(" aux "+ aux) ;
			//aux = "";//this.firstName +" "+ this.lastName;
			if( aux==null ||aux=="")
			{
				aux =".:DELIM:.";
			}
			String auxArray[] = aux.split(":DELIM:");
			//ret+="<b>User:</b>&nbsp;&nbsp;"+auxArray[0]+"  <a href='mailto:" + auxArray[1]+"' > <img title='click to send email' height='12' width='12' alt='email' src='/geoip-usersmap-portlet/images/icon_send_email.gif' ></img> </a> &nbsp; &nbsp; &nbsp; <a href='sip:" + auxArray[1]+"' > <img src='/geoip-usersmap-portlet/images/quote.gif' alt='Lync icon' title='click to start conversation' height='12' width='12' ></img> </a><br>";
			ret+=" <a href='mailto:" + auxArray[1]+"' style='text-decoration:none;'><img title='click to send email' height='12' width='12' alt='email' src='/geoip-usersmap-portlet/images/icon_send_email.gif' ></img>  &nbsp; &nbsp;  "+auxArray[0]+"</a> &nbsp; <a href='sip:" + auxArray[1]+"' ><img src='/geoip-usersmap-portlet/images/quote.gif' alt='Lync icon' title='click to start conversation' height='12' width='12' ></img> </a>&nbsp;&nbsp;<br/>";
			/*if( this.isLive )
			{
				ret+="&nbsp;&nbsp;<img alt='status' src='/geoip-usersmap-portlet/images/online.gif'></img><br>";						
			}
			else
			{
				ret+="&nbsp;&nbsp;<img alt='status' src='/geoip-usersmap-portlet/images/offline.gif'></img><br>";						
			}*/
				
			
		}
		return ret;
	}
}
