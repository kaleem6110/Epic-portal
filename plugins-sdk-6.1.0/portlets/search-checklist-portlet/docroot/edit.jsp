

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ include file="/AjaxUtils.jsp"%>
<script type="text/javascript" src="/view-checklist-portlet/js/jquery-1.7.2.js" > </script>

<portlet:defineObjects />
<style type="text/css">
#addChecklist table tr td
{

	width:150px;
	padding:10px;
	
}
#addChecklist table tr td .second
{

	width:450px;
	padding:10px;
	
}

#addChecklist table tr td select
{

line-height:1.9em;
}
</style>


<div id="addChecklist">
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>addChecklistForm" >
<div id="<portlet:namespace />return" style="float:right;font-weight:bold;" onclick="<portlet:namespace />returnSearch()">

<a href="#" >Return to Search Page</a>
</div>

<table>
		<tr>
			<td>Enter Checklist Name :</td>
			<td><input id="name" name="name" style="width: 200px;"></input></td>
					
		</tr>
		
		<tr>
			<td style="vertical-align:top;">Enter Content :</td>
			<td  style="width:450px;"><liferay-ui:input-editor height="100"   width="500">
</liferay-ui:input-editor></td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td><input id="save" name="save" type="button" value="Save" onclick="<portlet:namespace />validateSave()" />
			<input id="cancel" name="cancel" type="reset" value="Cancel" style="margin-left:30px;"/></td>
			
		</tr>
		<tr>
			<td><input id="cmd" name="cmd" type="hidden" value="saveNewChecklist" /></td>
			<td>&nbsp;</td>
		</tr>

</table>
<input type="hidden" id="fckEditorContent" name="fckEditorContent" />
</form>
</div>
<script type="text/javascript" >

function <portlet:namespace />returnSearch()
{


document.getElementById("cmd").value="searchView";
document.getElementById("<portlet:namespace/>addChecklistForm").submit();

}
function <portlet:namespace />validateSave()
{
var <portlet:namespace />name = document.getElementById("name").value;


if( <portlet:namespace />name==""|| <portlet:namespace />name.length<6 )
 {
 
 	alert(' Please provide name');
 }
 else
 {
 	var fckEditorContent = window.<portlet:namespace />editor.getHTML();
 	/*alert( fckEditorContent );*/
 	var eee  =document.getElementById("fckEditorContent"); 
	if(eee)
	 {
	 	eee.value=fckEditorContent;
	 }
	 jQuery("#<portlet:namespace/>addChecklistForm").submit();
	/* var <portlet:namespace />ajax = new AjaxUtils();
	 var parameters = {} ;
	 parameters["fckEditorContent"]= fckEditorContent;
	 parameters["cmd"]= "saveNewChecklist";
	  parameters["name"]= <portlet:namespace />name;
	 <portlet:namespace />ajax.parameters = parameters;
	 <portlet:namespace />ajax.actionRequest("post" );*/
 }
	
}

function <portlet:namespace/>initEditor()
{
	
    return "Enter your content here!!!!" ;
}


</script>