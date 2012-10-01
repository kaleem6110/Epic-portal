
<%@ include file="/init.jsp"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.IOException"%>

<%@ page import="com.liferay.geoipusersmap.model.GeoIPUsersMapDAO"%>
<%@ page import="com.liferay.geoipusersmap.model.MarkVO"%>
<%@ page import="com.liferay.geoipusersmap.model.MappingBean"%>

<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="com.maxmind.geoip.CountryCodes"%>
<%@ page import="java.util.Map"%>

<portlet:defineObjects />



<c:choose> 
<c:when test="<%=Validator.isNotNull(license)%>">


	<%
			
			if (emailAddress != null && !emailAddress.equals("default@liferay.com")) 
			{
				
				

	%>
	
<script src="/rcs-slider-portlet/js/jquery-1.7.1.min.js" type="text/javascript"></script>

<link href="/countrylinks-portlet/css/example.css" rel="stylesheet" type="text/css"></link>

<script type="text/javascript" src="/countrylinks-portlet/js/tabber.js"></script>

<%@ include file="/AjaxUtils.jsp"%>



<div id="myContainer"> 
								
	<div style="line-height: 2.0em;display:in-line; float:left;"> 

	Please select a country to get the information.  &nbsp;&nbsp;&nbsp;<img id="cc_image" src="" ></img> &nbsp;
	</div>
	<form id="<portlet:namespace />searchForm" action="<portlet:actionURL />" method="post">
	<input type="hidden" id="countryCode" name="countryCode" />
	<input type="hidden" id="countryName" name="countryName" />
	<input type="hidden" id="cmd" name="cmd" value="changeCountryLinks"/>
	

	<select id="<portlet:namespace />selectCountry"  style="width:250px;display:inline;" >

			<%
					
						 isDefaultUser = 0;						
						 markList = GeoIPUsersMapDAO.getUsersData();						
						// num_users = GeoIPUsersMapDAO.getNumUsers();
						 num_marks = markList.size();
						 CountryCodes countryCodesObj = new CountryCodes();
						Map<String, String>  countryCodesMap = countryCodesObj.lookup;
						
						for ( Map.Entry<String, String> entry : countryCodesMap.entrySet() ) 
						{   	
							%>
							<option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>
						<%
							
							
						}
					
					%>
		</select> <img src="/countrylinks-portlet/images/loading3.gif" id="loading3" style="margin-left:30px;" ></img>
		
		</form>
		<p style="clear:both;">
		<br/>
		</p>
		<div class="tabber" id="tab2">
		
			<div class="tabbertab">
				<h2>Project Visa</h2>
				<p style="height: 100%;">
					<iframe id="ID_ProjectVisa" src="" width="100%" height="1000px;" scrolling="no"  onload="toggleLoading(false)"></iframe>
				</p>
			</div>


			<div class="tabbertab">
				<h2>Wiki</h2>
				<p style="height: 100%;">
					<iframe id="ID_Wiki" src="" width="100%" height="1200px;"  onload="toggleLoading(false)"></iframe>
				</p>
			</div>
			<div class="tabbertab">
				<h2>News</h2>
				<p style="height: 100%;">
					<iframe id="ID_NEWS" src="" width="100%" height="1200px;" onload="toggleLoading(false)"></iframe>
				</p>
			</div>
			<div class="tabbertab">
				<h2>Time and Weather</h2>
				<p style="height: 100%;">
					<iframe id="ID_TimeAndWeather" src="" width="100%" height="1200px;"  onload="toggleLoading(false)"></iframe>
				</p>
			</div>
			<div class="tabbertab">
				<h2>(CIA) World Factbook</h2>
				<p style="height: 100%;">
					<iframe id="ID_CIA_FB" src="" width="100%" height="1200px;"  onload="toggleLoading(false)" ></iframe>
				</p>
			</div>

			<div class="tabbertab">
				<h2>Logistic Cluster</h2>
				<p style="height: 100%;">
					<iframe id="ID_LOG_CLUSTER" src=""
						width="100%" height="1200px;"  onload="toggleLoading(false)"></iframe>
				</p>
			</div>
			<div class="tabbertab">
				<h2>UN DSA Rates</h2>
				<p style="height: 100%;">
					<iframe id="ID_UN_DSA"
						src="http://apps.who.int/bfi/tsy/PerDiem.aspx" width="100%"
						height="1200px;"></iframe>
				</p>
			</div>

		</div>

		
		<script type="text/javascript">
	
		
		document.write('<style type="text/css">.tabber{display:none;}<\/style>');
		
			
	
			var <portlet:namespace />isDefaultUser = "<%=isDefaultUser%>";		
			var <portlet:namespace />userCountry="India";
			
				<%
					
					if( isDefaultUser==0 )
					{
					for (int i = 0; i < num_marks; i++) 
					{
						//allUserIds += "DELIM";
						MarkVO mark = (MarkVO) markList.get(i);
						List<String> userIdList = mark.getUserIdList();

						String htmlCode = mark.getHtmlCode();
						Float longitude = mark.getLongitude();
						Float latitude = mark.getLatitude();
						cntCode = mark.getLocation().countryCode;
						CountryCodes cc = new CountryCodes();
						ccName = cc.getCountry(cntCode);
						
						Integer is_auto = mark.getIs_Auto();

						for (int j = 0; j < userIdList.size(); j++) 
						{
							//allUserIds = allUserIds + " : " + userIdList.get(j);
							if (userIdList.get(j).equals( new Long(user.getUserId()).toString())) 
							{
								System.out.println(" KKKK User found : "+ user.getUserId() + " is_auto : "+ is_auto);
								isUserFound = true;
								isUserMarker = true;								
								Object obj1 =  renderRequest.getPortletSession().getAttribute("mappingBean-"+user.getUserId(), PortletSession.APPLICATION_SCOPE);
								String str=null;
								if( obj1!=null)
								{
									str =obj1.toString();
								}
								System.out.println(" obj1 :"+obj1 +" : str : "+ str );
								if( str== null )								
								{
									 mappingBean = GeoIPUsersMapDAO.getMappingBeanByCode( ""+user.getUserId(), false );
									 ccName = mappingBean.getCountryName();
									 cntCode = mappingBean.getIso2Code();
									 iso3Code=mappingBean.getIso3Code();
								}								
								else
								{
									
									String strArray[]= str.split("-");
									mappingBean = new MappingBean();								
									mappingBean.setIso2Code( strArray[0]);
									mappingBean.setIso3Code( strArray[1]);
									mappingBean.setCiaFactBook( strArray[2]);
									mappingBean.setCountryName( strArray[3]);
									
									ccName = mappingBean.getCountryName();
									cntCode = mappingBean.getIso2Code();
									iso3Code=mappingBean.getIso3Code();
									renderRequest.getPortletSession().setAttribute("mappingBean-"+user.getUserId(),str, PortletSession.APPLICATION_SCOPE);
								}								
									System.out.println(" mappingBean mappingBean mappingBean : "+str);
									
								 break;		
								
								
								
							}
						}
						if( isUserMarker ) break;
						
						
					}
					}
				%>
			 	var projectVisa="";
			 	 ctyName = "<%=ccName.toLowerCase().trim()%>";
				 var pArray= ctyName.split(/[\s]+/);
	 			 
	 		  	if( ctyName.indexOf(" ")!=-1 && pArray!=null )
    			{
    				for( var i=0; i<pArray.length; i++)
    				{
    					if(i > 0) projectVisa= projectVisa+ "_";
    					var s = pArray[i];
    					if( s!=null)
    					{
    						if( s=="and")
    						{
    							s = s.toLowerCase();
    						}
    						else s=  s.substring(0,1).toUpperCase()  + s.substring(1, s.length).toLowerCase();    			
    						projectVisa = projectVisa + s ;
    					}
    				}
    			}  
    			else
    			{
    			 	projectVisa = ctyName;
    		 		projectVisa=  projectVisa.substring(0,1).toUpperCase()  + projectVisa.substring(1, projectVisa.length).toLowerCase();  
    			}
    			var guardian =projectVisa.toLowerCase();
    			switch(ctyName.toLowerCase())
    			{
    				case "united arab emirates":  guardian ="united-arab-emirates"; break;
    				case "dominican republic":  guardian ="dominicanrepublic"; break;
    				case "united states":  guardian ="usa"; break;
    			}
    			/*if( ctyName=="united arab emirates")
			 	{
			 		guardian ="united-arab-emirates";
			 	}*/
			 	
    			
       		var fb = "<%=mappingBean.getCiaFactBook()%>" ;
        	if( <portlet:namespace />isDefaultUser== 0 )
        	{
		 		jQuery("#ID_ProjectVisa").attr("src","http://www.projectvisa.com/visainformation/"+projectVisa );
		 	 	//jQuery("#ID_Wiki").attr("src","http://en.wikipedia.org/wiki/"+projectVisa );
		 	 	
		 		iso3Code = "<%=iso3Code.toLowerCase()%>";
		 	 	
		 		
		  	
		  		
			}
		  	else
		  	{
		  		jQuery("#container").html(" Please login to use this application");
		  		document.write('<style type="text/css">.tabber{display:none;}<\/style>');
		  		/*jQuery("#ID_ProjectVisa").attr("src","http://www.projectvisa.com/visainformation/United_Arab_Emirates");
		 	 	jQuery("#ID_Wiki").attr("src","http://en.wikipedia.org/wiki/United_Arab_Emirates");
		 	 	jQuery("#ID_NEWS").attr("src","http://www.guardian.co.uk/world/");
		 		jQuery("#ID_CIA_FB").attr("src","https://www.cia.gov/library/publications/the-world-factbook/geos/ae.html" );
		  		jQuery("#ID_TimeAndWeather").attr("src","http://www.timeanddate.com/worldclock/results.html?query=dubai" );*/
		  
		  	
		  	}
		  	
		  	var <portlet:namespace />my_options = jQuery("#" + jQuery("#<portlet:namespace />selectCountry").attr("id") + " option");	
								  
				<portlet:namespace />my_options.sort(function(a,b) 
				{
				        if (a.text > b.text) return 1;
				        else if (a.text < b.text) return -1;
				        else return 0;
   				});
		  		
		  jQuery("#<portlet:namespace />selectCountry").change( function()
	   		{
	   			jQuery("#loading3").css("display","inline");
	 			 var uiCode = jQuery("#<portlet:namespace />selectCountry option:selected").val();
	 			 var ctyName = jQuery("#<portlet:namespace />selectCountry option:selected").text();
		  		if(  <portlet:namespace />isDefaultUser== 1 )
		  		{
	 			 jQuery("#ID_TimeAndWeather").attr("src","http://www.timeanddate.com/worldclock/results.html?query="+ ctyName.toLowerCase() );
	 			jQuery("#ID_CIA_FB").attr("src","https://www.cia.gov/library/publications/the-world-factbook/" );
		  		jQuery("#ID_ProjectVisa").attr("src","http://www.projectvisa.com/visainformation/"+projectVisa);
		 	 	jQuery("#ID_Wiki").attr("src","http://en.wikipedia.org/wiki/"+projectVisa);		 	 	
		 	 	jQuery("#cc_image").attr("src","/geoip-usersmap-portlet/images/flags/"+uiCode.toLowerCase()+".gif");
		 	 	}
		 	 	else
		 	 	{
		 	 		var <portlet:namespace />ajax = new AjaxUtils();
		  			var parameters = {} ;
		  			parameters["countryCode"]= uiCode;
		  			parameters["countryName"]= ctyName;
		  			parameters["cmd"]= "changeCountryLinks";		  	
		  			<portlet:namespace />ajax.parameters = parameters;
		  			<portlet:namespace />ajax.callbackfunc="<portlet:namespace />refresh";
		  			/*<portlet:namespace />ajax.actionRequest("get" );*/
		  			
		  			jQuery("#countryCode").val(uiCode);
		  			jQuery("#countryName").val(ctyName);		  			
		  			jQuery("#<portlet:namespace />searchForm").submit();
		 	 	}	 			
		  
	 		}); 
	 		
	 		jQuery('#<portlet:namespace />selectCountry').empty().append( <portlet:namespace />my_options );
	 		var uCCode ="<%=cntCode.toUpperCase()%>";
	 		
	 		 var eee  =document.getElementById('<portlet:namespace />selectCountry');
	 		 if(eee)
	 		 {
	 		 	eee.value=uCCode;
	 		 }
	 		
   			/*jQuery("#<portlet:namespace />selectCountry option[value=\""+ uCCode +"\"]").attr("selected","selected");*/
		  	jQuery("#cc_image").attr("src","/geoip-usersmap-portlet/images/flags/"+"<%=cntCode.toLowerCase()%>"+".gif");
			
			function <portlet:namespace />refresh()
			{
				setTimeout(function() {window.location.reload();},6000);
				
			}
			
			
   		 	/*jQuery('iframe').contents().find('body a').click(function()
   		 	{ 
       			 window.open(this.href);
       			 return false;
    		});*/
    		
    		function toggleLoading( flag ) 
    		{   
    			if( flag ) 
    				jQuery("#loading3").css("display","inline");
    				
    			else jQuery("#loading3").css("display","none");	
 			 	
			};
    		
			tabberAutomatic(tabberOptions);
       	
		</script>
<%
}
					else
					{
						out.println("Please login to use this application.");
					}
					%>
					
					
</div>

	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-contact-the-administrator-to-setup-this-portlet" />
	</c:otherwise>
</c:choose>




