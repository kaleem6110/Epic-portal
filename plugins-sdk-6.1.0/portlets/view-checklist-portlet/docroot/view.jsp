


<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ include file="/AjaxUtils.jsp"%>

<portlet:defineObjects />

<script type="text/javascript">

Liferay.bind('searchChecklist',
 function( data){
  			
   			var chklist_id = data.message;
   			/*if( chklist_id!=-1 ) 
   			{  */ 			
   				jQuery("#chklist_id").val( chklist_id );
 				jQuery("#<portlet:namespace/>viewChecklistForm").submit();
   			/*}*/   			
   			
 			
 	
 		
 	 	
 
});

</script>
<style type="text/css">
#viewChecklist table tr td
{

	width:50px;
	padding:10px;
	
}
#viewChecklist table tr td select
{

line-height:1.9em;
}
</style>

<div id="viewChecklist">
<div id="initialMessage">
Please select the checklist to view.
</div>	
<form method="POST" action="<portlet:actionURL />" id="<portlet:namespace/>viewChecklistForm" style="display:none;">
<input id="cmd" name="cmd" type="hidden" value="viewChecklist" />
<input id="chklist_id" name="chklist_id" type="hidden" value="-1" />
</form>
</div>

