
package com.liferay.geoipusersmap.model;

//author: Mohammed Kaleem

public class CheckListBean
{
	//instance variables
	private Long id;
	private String name;
	private String description;
	private String ownerName;
	private String createdDate;
	private String isGlobal;
	private String ownerId;
	
	//Setter and Getters
	public void setId(Long id)
	{
		this.id=id;
	}
	public Long getId()
	{
		return id;		
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;		
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public String getDescription()
	{
		return description;		
	}
	public void setOwnerName(String ownerName)
	{
		this.ownerName=ownerName;
	}
	public String getOwnerName()
	{
		return ownerName;		
	}
	public void setCreatedDate(String createdDate)
	{
		this.createdDate=createdDate;
	}
	public String getCreatedDate()
	{
		return createdDate;		
	}
	public void setIsGlobal(String isGlobal)
	{
		this.isGlobal=isGlobal;
	}
	public String getIsGlobal()
	{
		return isGlobal;		
	}
	public void setOwnerId(String ownerId)
	{
		this.ownerId=ownerId;
	}
	public String getOwnerId()
	{
		return ownerId;		
	}
}