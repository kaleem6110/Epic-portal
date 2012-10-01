

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.WindowState" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<portlet:defineObjects />


<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String license = preferences.getValue("license", StringPool.BLANK);
int height = GetterUtil.getInteger(preferences.getValue("height", StringPool.BLANK), 300);


boolean isUserFound = false;
boolean isUserMarker = false;
boolean anotherFlag = true;
String cntCode = "af";
//String allUserIds = new Long(user.getUserId()).toString();
String projectVisa = "Afghanistan";
String ccName = "Afghanistan";
MappingBean mappingBean =new MappingBean();
mappingBean.setIso2Code( "af");
mappingBean.setIso3Code( "afg");
mappingBean.setCiaFactBook( "af");
mappingBean.setCountryName( "Afghanistan");
String iso3Code="AFG";

int isDefaultUser = 1;
List<MarkVO> markList = new ArrayList<MarkVO>();
//Integer num_users = null;
int num_marks =0;
User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
String emailAddress = user.getEmailAddress();


%>

<script type="text/javascript">

var wikiFlag=0;
var newsFlag=0;
var timeFlag=0;
var ciaFlag=0;
var logFlag=0;
var dsaFlag=0;
var ctyName="";
var tabberId="";
var iso3Code="";
/* Optional: Temporarily hide the "tabber" class so it does not "flash"
   on the page as plain HTML. After tabber runs, the class is changed
   to "tabberlive" and it will appear.
*/
document.write('<style type="text/css">.tabber{display:none;}<\/style>');

var tabberOptions = {


  'manualStartup':true,

  /* Optional: code to run after each tabber object has initialized */

  'onLoad': function(argsObj) {
    /* Display an alert only after tab2 */
    if (argsObj.tabber.id == 'tab2') {
      
    }
  },

  /* Optional: code to run when the user clicks a tab. If this
     function returns boolean false then the tab will not be changed
     (the click is canceled). If you do not return a value or return
     something that is not boolean false, */

  'onClick': function(argsObj) {

    var t = argsObj.tabber; /* Tabber object */
    var id = t.id; /* ID of the main tabber DIV */
    var i = argsObj.index; /* Which tab was clicked (0 is the first tab) */
    var e = argsObj.event; /* Event object */

    if (id == 'tab2') {
     // return confirm('Swtich to '+t.tabs[i].headingText+'?\nEvent type: '+e.type);
     
    	 tabberId = t.tabs[i].headingText;
    	
		switch(t.tabs[i].headingText)
		 {
		 
		 	case 'Project Visa': 
									/*alert('Project Visa is clicked');*/ 
									break;
									
			case 'Wiki': 			if( wikiFlag <1 )	{	
									toggleLoading(true);	
									jQuery("#ID_Wiki").attr("src","http://en.wikipedia.org/wiki/"+projectVisa );
									wikiFlag =1;}
									break;

			

			case 'News':  		guardian ="world/"+guardian;
						  		if( ctyName=="united kingdom")
							 	{
							 		guardian ="uk";
							 		/*jQuery("#ID_NEWS").attr("src","http://www.guardian.co.uk/"+guardian);*/
							 	}
							 	if( newsFlag <1 ){	
							 	toggleLoading(true);	
							 	jQuery("#ID_NEWS").attr("src","http://www.guardian.co.uk/"+guardian);
							 	newsFlag=1;}
								break;

			case 'Time and Weather': 
										if( timeFlag <1 ){	
										toggleLoading(true);	
									jQuery("#ID_TimeAndWeather").attr("src","http://www.timeanddate.com/worldclock/results.html?query="+ctyName );
		  							timeFlag=1;
		  							}
									break;

			case '(CIA) World Factbook':			if( fb!=null )
											 	 	{
											 	 		fb = "geos/"+fb+".html";
											 	 		
											 	 	}
											 	 	else
											 	 	{
											 	 		fb="";
											 	 	}
									if( ciaFlag <1 ){	
									toggleLoading(true);	
		 	 							jQuery("#ID_CIA_FB").attr("src","https://www.cia.gov/library/publications/the-world-factbook/"+fb );
		 	 							ciaFlag=1;
		 	 							}
		 	 							break;
			
			case 'Logistic Cluster':			if( logFlag <1 ){	
												toggleLoading(true);
												jQuery("#ID_LOG_CLUSTER").attr("src","http://www.logcluster.org/countries/"+iso3Code);
												logFlag=1;
												}
		  										break;
			
			
			
			
		}
    }
  },

  /* Optional: set an ID for each tab navigation link */
  'addLinkId': true

};

</script>
