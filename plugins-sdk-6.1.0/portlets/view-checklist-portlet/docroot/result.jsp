

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO" %>
<%@ page import="com.liferay.geoipusersmap.model.CheckListBean" %>
<%@ page import="javax.portlet.PortletSession"%>
<%@ include file="/AjaxUtils.jsp"%>
<portlet:defineObjects />
<script type="text/javascript" src="/view-checklist-portlet/js/jquery-1.7.2.js" > </script>
<script type="text/javascript" src="/view-checklist-portlet/js/jquery.alerts.js" > </script>
<link href="/view-checklist-portlet/css/jquery.alerts.css" type="text/css" media="screen" />
<script type="text/javascript">

Liferay.bind('searchChecklist',
 function( data)
 {
  				var chklist_id = data.message;
   			  			
   				jQuery("#chklist_id").val( chklist_id );
 				jQuery("#<portlet:namespace/>viewChecklistForm").submit();
   			
});
function <portlet:namespace/>deleteList( chklist_id )
{
	jConfirm('Do you really want to delete?', 'Delete Confirmation', function(r) {
   				if( r ){
   				document.getElementById("del_chklist_id").value= ""+chklist_id ;
 				jQuery("#<portlet:namespace/>deleteChecklistForm").submit();
 				}
		});
}
function <portlet:namespace/>editList( chklist_id )
{
	jConfirm('Do you really want to edit?', 'Edit Confirmation', function(r) {
   				if( r ){
   				document.getElementById("edit_chklist_id").value= ""+chklist_id ;
   				alert( chklist_id );
 				jQuery("#<portlet:namespace/>editChecklistForm").submit();
 				}
		});
}

function <portlet:namespace />sharewithPublic( chklist_id )
{
	
	jConfirm('Do you really want to share?', 'Sharing Confirmation', function(r) {
   				if( r ){
   				document.getElementById("share_chklist_id").value= ""+chklist_id ;
 				jQuery("#<portlet:namespace/>shareChecklistForm").submit();
 				}
		});
	
	

}
function <portlet:namespace />unsharewithPublic( chklist_id )
{
	
		jConfirm('Do you really want to unshare?', 'Unshare Confirmation', function(r) {
   				if( r ){
   				document.getElementById("unshare_chklist_id").value= ""+chklist_id ;
 				jQuery("#<portlet:namespace/>unShareChecklistForm").submit();
 				}
		});
	
	

}
</script>
<style type="text/css">
#viewChecklist table tr 
{
	width:auto;
	padding:10px;
	line-height:1.9em;
	
}
#viewChecklist table tr td
{
	padding:10px;	
}
#viewChecklist table tr td .title
{
font-weight:bold;
font-size:24px;
padding-right:30px;
}
			/* Custom dialog styles */
			#mypopup_container {
				font-family: Georgia, serif;
				border: solid 1px #E8E8E8;
				background: #E8E8E8;
				border-color: #113F66;				
				 min-width: 306px; max-width: 306px;padding:20px; 
			}
			
			#mypopup_container #mypopup_title {
				
				font-weight: normal;
				text-align: left;
				background: #F6F6F6;
				border: solid 1px #E8E8E8;
				padding-left: 0.5em;
			}
			
			#mypopup_container #mypopup_content {
				background: none;
			}
			
			#mypopup_container #mypopup_message {
				padding-left: 2em;
				margin-bottom:20px;
			}
			
			#mypopup_container INPUT[type='button'] {
			
				
				background: ##F6F6F6;
				margin-bottom:10px;
				margin-left: 2em;
			}
</style>

<div id="viewChecklist">
<%
String description = renderRequest.getPortletSession().getAttribute("description",  PortletSession.APPLICATION_SCOPE).toString();
String name = renderRequest.getPortletSession().getAttribute("name",  PortletSession.APPLICATION_SCOPE).toString();
String author = renderRequest.getPortletSession().getAttribute("author",  PortletSession.APPLICATION_SCOPE).toString();
String chklist_id = renderRequest.getPortletSession().getAttribute("chklist_id",  PortletSession.APPLICATION_SCOPE).toString();
String isGlobal = renderRequest.getPortletSession().getAttribute("isGlobal",  PortletSession.APPLICATION_SCOPE).toString();
String createdDate = renderRequest.getPortletSession().getAttribute("createdDate",  PortletSession.APPLICATION_SCOPE).toString();
String isOwner = renderRequest.getPortletSession().getAttribute("isOwner",  PortletSession.APPLICATION_SCOPE).toString();
System.out.println(" isOwner : "+ isOwner );
description = description.replaceAll("<p></p>","");
%>

<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>viewChecklistForm" >

<table  style="width:100%;">
	<tr>	
		
		<td colspan="2" ><span class="title"><%=name%></span> &nbsp; 
		
		<% if( isGlobal.equals("0"))
		{	%>
			<a href="javascript:none;" onclick="<portlet:namespace />sharewithPublic(<%=chklist_id%>)"><img title="click to share with public" height="27" src="/view-checklist-portlet/images/share_icon.png" /></a>
		<%}	
		else if( isOwner=="1" )
		{%>
			<a href="javascript:none;" onclick="<portlet:namespace />unsharewithPublic(<%=chklist_id%>)"><img title="click to unshare" height="27" src="/view-checklist-portlet/images/unshare.jpg" /></a>
		<%}
		%>
		
		
		
		<span style="float:right;margin-right:0;font-family:sanseriff;font-size:12px;"> Author:  &nbsp;&nbsp;&nbsp;&nbsp; <%=author%> &nbsp; <img title="<%=author%>"  src="/view-checklist-portlet/images/icon_profile.gif" height="20" /> <br> Date :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <%=createdDate%> </span> <br> </td>
		</tr>
		<tr>
		<td colspan="2"> <%=description%> </td>
		</tr>
		<%
		if( isGlobal.equals("0")||isOwner=="1")
		{%>
		<tr>
			<td ><input style="display:none;" title="click to edit checklist" id="<portlet:namespace/>edit" type="button" value="EDIT" onclick="<portlet:namespace/>editList()" />
			
			<input title="click to delete checklist"id="<portlet:namespace/>delete" type="button" value="DELETE" onclick="<portlet:namespace/>deleteList(<%=chklist_id%>)" style="margin-left:30px;" /></td>
		</tr>
		<%}%>
		<tr>
			<td><input id="cmd" name="cmd" type="hidden" value="viewChecklist" /></td>
		
		</tr>

</table>
<input id="cmd" name="cmd" type="hidden" value="viewChecklist" />
<input id="chklist_id" name="chklist_id" type="hidden" value="-1" />
</form>

<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>deleteChecklistForm" >
<input id="cmd" name="cmd" type="hidden" value="deleteChecklist" />
<input id="del_chklist_id" name="del_chklist_id" type="hidden" value="" />
</form>
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>shareChecklistForm" >
<input id="cmd" name="cmd" type="hidden" value="shareChecklist" />
<input id="share_chklist_id" name="share_chklist_id" type="hidden" value="" />
</form>
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>unShareChecklistForm" >
<input id="cmd" name="cmd" type="hidden" value="unShareChecklist" />
<input id="unshare_chklist_id" name="unshare_chklist_id" type="hidden" value="" />
</form>
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>editChecklistForm" >
<input id="cmd" name="cmd" type="hidden" value="editChecklist" />
<input id="edit_chklist_id" name="edit_chklist_id" type="hidden" value="" />
</form>
</div>
<script type="text/javascript">
jQuery(document).ready(function(){
jQuery("<p></p>").hide();

});
</script>
