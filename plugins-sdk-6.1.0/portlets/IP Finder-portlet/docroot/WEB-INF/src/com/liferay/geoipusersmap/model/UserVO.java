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

import com.liferay.geoipusersmap.model.MarkVO;

import com.maxmind.geoip.Location;

/**
 * <a href="UserVO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class UserVO {

	//Private properties
	private Integer userid;
	private String screenname;
	private String ip;
	private Location location;
	private String firstName;
	private String lastName;

	//Constructores
	public UserVO() {
	}

	public UserVO ( Integer userid, String screenname, String ip, Location location, String firstName, String lastName){
		this.userid 		= userid;
		this.screenname 	= screenname;
		this.ip 		= ip;
		this.location		= location;
		this.firstName		= firstName;
		this.lastName		= lastName;
	}
	
	public String getFirstName () {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public String getLastName () {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}


	//GETTERS & SETTERS
	public Integer getUserid () {
		return userid;
	}
	
	public void setUserid (Integer userid) {
		this.userid=userid;
	}

	public String getScreenname () {
		return screenname;
	}
	
	public void setScreenname (String screenname) {
		this.screenname=screenname;
	}

	public String getIp () {
		return ip;
	}
	
	public void setIp (String ip) {
		this.ip=ip;
	}

	public Location getLocation () {
		return location;
	}
	
	public void setLocation (Location location) {
		this.location=location;
	}

	public Float getLatitude() {
		Float ret=this.location!=null?this.location.latitude:0;
		return ret;
	}

	public Float getLongitude() {
		Float ret=this.location!=null?this.location.longitude:0;
		return ret;
	}

	public boolean isSameLocation(MarkVO mark) {
		boolean ret=false;
		if ((this.getLongitude()-mark.getLongitude()==0)||(this.getLatitude()-mark.getLatitude()==0)) ret=true;
		return ret;	
	}
}
