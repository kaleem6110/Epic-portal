
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<portlet:defineObjects />
<script type="text/javascript" src="/view-checklist-portlet/js/jquery-1.7.2.js" > </script>
<script type="text/javascript">

function <portlet:namespace/>searchList()
{
 	var selectedValue = document.getElementById("<portlet:namespace />checklist").value;
 	if( selectedValue < 0 ) selectedValue = -1 ;
 	setCookie("checklist", ""+selectedValue,1 );	
	Liferay.trigger("searchChecklist", {"message": selectedValue });	
	  
	
		
}
function <portlet:namespace/>newList()
{
	document.getElementById("<portlet:namespace/>checklistForm").submit();

}
</script>
<style type="text/css">
#checklist table tr td
{

	width:150px;
	padding:10px;
	
}
#checklist table tr td select
{

line-height:1.9em;
}
</style>

<div id="checklist">	
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>checklistForm" >
<table>
	<tr>
			<td>Select Checklist :</td>
			<td><select title="select a checklist to view" id="<portlet:namespace />checklist" onchange="<portlet:namespace/>searchList()" style="width: 300px;">
					<option value="-1">Please select checklist</option>
				
					<%
					Map<String, String>  checklistMap = new HashMap<String, String>(); 
					String userId = renderRequest.getRemoteUser();
					if( userId!=null )
					{
							GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
						 	checklistMap = dao.fetchChecklistByUserId( userId );		
					}
					for ( Map.Entry<String, String> entry : checklistMap.entrySet() ) 
					{%>				
						<option value="<%=entry.getKey()%>"> <%=entry.getValue()%>  </option>
					<%}
					%>
					<option value="-2"> ---------------------------  </option>
					<option value="-3"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; PUBLIC </option>	
					<option value="-4"> ---------------------------  </option>						
					
					<%
					if( userId!=null )						
					{
							GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
						 	checklistMap = dao.fetchPublicChecklist();		
					}
					for ( Map.Entry<String, String> entry : checklistMap.entrySet() ) 
					{%>				
						<option value="<%=entry.getKey()%>"> <%=entry.getValue()%>  </option>
					<%}
					%>
					
			</select></td>
		</tr>
		<tr>
			<td><!-- <input id="<portlet:namespace/>search" type="button" value="Search" onclick="<portlet:namespace/>searchList()"/>-->&nbsp;</td>
			<td><input title="click to add a new checklist" id="<portlet:namespace/>new" type="button" value="New Checklist" onclick="<portlet:namespace/>newList()"/></td>
			
		</tr>
		<tr>
			<td><input id="cmd" name="cmd" type="hidden" value="addNewChecklist" /></td>
			<td>&nbsp;</td>
		</tr>

</table>
</form>
</div>
<script type="text/javascript">
function setCookie(c_name,value,exdays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate() + exdays);
var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
document.cookie=c_name + "=" + c_value;
}
function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
{
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}
var val = getCookie("checklist");
jQuery("#<portlet:namespace />checklist option[value='"+val+"']").attr("selected","selected");

</script>