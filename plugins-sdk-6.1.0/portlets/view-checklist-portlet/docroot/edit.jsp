
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ page import="javax.portlet.PortletSession"%>
<script type="text/javascript" src="/view-checklist-portlet/js/jquery-1.7.2.js" > </script>

<portlet:defineObjects />
<style type="text/css">
#updateChecklist table tr td
{
	width:150px;
	padding:10px;	
}
#updateChecklist table tr td .second
{
	width:450px;
	padding:10px;	
}
#updateChecklist table tr td select
{
line-height:1.9em;
}
</style>
<%
String description = renderRequest.getPortletSession().getAttribute("description",  PortletSession.APPLICATION_SCOPE).toString();
String name = renderRequest.getPortletSession().getAttribute("name",  PortletSession.APPLICATION_SCOPE).toString();
String chklist_id = renderRequest.getPortletSession().getAttribute("chklist_id",  PortletSession.APPLICATION_SCOPE).toString();
description = description.replaceAll("<p></p>","");
description = description.replaceAll("&nbsp;","");
%>

<div id="updateChecklist">
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>updateChecklistForm" >
<div id="<portlet:namespace />return" style="float:right;font-weight:bold;" onclick="<portlet:namespace />returnView()">

<a href="#" >Return to View Page</a>
</div>

<table>
		<tr>
			<td> Checklist Name :</td>
			<td><input id="editname" name="editname" style="width: 200px;" value="<%=name%>" disabled></input></td>
					
		</tr>
		
		<tr>
			<td style="vertical-align:top;"> Content :</td>
			<td  style="width:450px;"><liferay-ui:input-editor height="100"   width="500">
</liferay-ui:input-editor></td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td><input id="update" name="Update" type="button" value="Update" onclick="<portlet:namespace />validateUpdate()" />
			<input id="cancel" name="cancel" type="button" value="Cancel" style="margin-left:30px;"/></td>
			
		</tr>
		<tr>
			<td><input id="cmd" name="cmd" type="hidden" value="updateChecklist" /></td>
			<td>&nbsp;</td>
		</tr>

</table>
<input type="hidden" id="fckEditorContent" name="fckEditorContent" />
</form>
</div>
<script type="text/javascript" >

function <portlet:namespace />returnView()
{
document.getElementById("cmd").value="resultView";
document.getElementById("<portlet:namespace/>updateChecklistForm").submit();
}
function <portlet:namespace />validateUpdate()
{
	var <portlet:namespace />editname = document.getElementById("editname").value;
 	var fckEditorContent = window.<portlet:namespace />editor.getHTML();
 	/*alert( fckEditorContent );*/
 	var eee  =document.getElementById("fckEditorContent"); 
	if(eee)
	 {
	 	eee.value=fckEditorContent;
	 }
	 jQuery("#<portlet:namespace/>updateChecklistForm").submit(); 
	
}

function <portlet:namespace/>initEditor()
{	
    return "<%=description%>" ;
}


</script>