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
	private String firstName;
	private String lastName;
	

	//Constructores
	public MarkVO() {
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

	public MarkVO (List screennameList, Location location, String firstName, String lastName){
		this.screennameList 	= screennameList;
		this.location 		= location;
		this.firstName		= firstName;
		this.lastName		= lastName;
	}

	//GETTERS & SETTERS
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
	
	public String getHtmlCode () {
		if( location.city == null )
		{
			location.city ="";
		}
		String ret = "Location : <img alt='flag' src='/geoip-usersmap-portlet/images/flags/"+this.location.countryCode.toLowerCase()+".gif'>&nbsp;&nbsp;<span style='font-size:1.4em;'><b>"+this.location.countryName+"</b>,&nbsp;"+location.city+"</span><div style='font-size:1.2em;'>";
		String aux =  "<b>"+ (String) this.firstName + " " + this.lastName + "</b><br>";	
			
		return "Welcome "+aux+ ret;
	}
}
