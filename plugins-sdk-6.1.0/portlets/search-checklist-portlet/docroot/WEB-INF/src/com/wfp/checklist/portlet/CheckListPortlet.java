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

package com.wfp.checklist.portlet;
import java.util.Map;
import java.util.HashMap;
import com.liferay.portal.kernel.log.Log;



import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.jsp.JSPPortlet;

import java.io.IOException;
import javax.portlet.WindowState;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletSession;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.PortletURL;

import com.liferay.geoipusersmap.model.GeoIPUsersMapDAO;

/**
 * <a href="CheckListPortlet..java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class CheckListPortlet extends MVCPortlet {

	public void init() throws PortletException {
		editJSP = getInitParameter("edit-jsp");
		helpJSP = getInitParameter("help-jsp");
		viewJSP = getInitParameter("view-jsp");
	}

	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException 
	{

		String jspPage = renderRequest.getParameter("jspPage");

		if (jspPage != null) {
			include(jspPage, renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException 
	{

		if (renderRequest.getPreferences() == null) 
		{
			super.doEdit(renderRequest, renderResponse);
		}
		else 
		{
			include(editJSP, renderRequest, renderResponse);
		}
	}

	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(helpJSP, renderRequest, renderResponse); 
	}

	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException 
		{
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!ChecklistPortlet doView RENDER START !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		PortletSession portletSession = renderRequest.getPortletSession();		
		String userId = renderRequest.getRemoteUser();
		String actionType = null;
		Object actOnj = renderRequest.getParameter("actionType");
		System.out.println(" !!!!actOnj :"+actOnj );
		
		if( actOnj!= null ) 
		{
			actionType = actOnj.toString(); 
			System.out.println(" !!!!!"+actionType ); 
			String code=null;
			if( userId!=null && userId!="0")
			{
				if( actionType.equals("addNewChecklist") )
				{
					PortletURL portletURL = renderResponse.createRenderURL();
					portletURL.setWindowState(WindowState.MAXIMIZED);
					include(editJSP, renderRequest, renderResponse);					
					System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!addNewChecklist RenderRequest -> !!!!!!! ");
					
				}
				else if (actionType.equals("saveNewChecklist")) 
				{			
					
					include(viewJSP, renderRequest, renderResponse);	
				}
			
			}
		}
		else
		{
			include(viewJSP, renderRequest, renderResponse);
			//super.doView(renderRequest, renderResponse);
			
		}
		
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!ChecklistPortlet doView RENDER END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
	}
	private Map<String, String> fetchChecklistByUserId( String userId)
	{
		System.out.println(" !!!!!!!!!!!!!!!START fetchChecklistByUserId !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+userId);
		GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
		Map<String, String> checklistMap = dao.fetchChecklistByUserId( userId );
		System.out.println(" !!!!!!!!!!!!!!END fetchChecklistByUserId!!!!!!!!!!!!!!!!!!!!!!!! ");
		
		return checklistMap;
		
	}
	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException 
	{
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!ChecklistPortlet processAction  START !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");

		try
		{
		PortletSession portletSession = actionRequest.getPortletSession();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);		
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!cmd !!!!!!!!!!!!! "+ cmd );
		String userId = actionRequest.getRemoteUser();
	
		if (cmd.equals("addNewChecklist")) 
		{			
			
			actionResponse.setRenderParameter("actionType", cmd );
		}
		else if (cmd.equals("saveNewChecklist")) 
		{			
			
			actionResponse.setRenderParameter("actionType", cmd );
			String fckEditorContent = ParamUtil.getString(actionRequest, "fckEditorContent");
			String name = ParamUtil.getString(actionRequest, "name");
			System.out.println("fckEditorContent :"+ fckEditorContent+ " name: "+name );
			saveChecklist( userId , name, fckEditorContent );
			actionResponse.setRenderParameter("actionType", cmd );
		}
		}catch(Exception e){e.printStackTrace(); }
		

		
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!ChecklistPortlet processAction  END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
	}
	public boolean saveChecklist(String userId,String name, String description )
	{
		return GeoIPUsersMapDAO.saveChecklist( userId, name, description);
	
	}
	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException 
	{

		PortletRequestDispatcher portletRequestDispatcher =
			getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) 
		{
			_log.error(path + " is not a valid include");
		}
		else
		{
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
	

	protected String editJSP;
	protected String helpJSP;
	protected String viewJSP;

	private static Log _log = LogFactoryUtil.getLog(CheckListPortlet.class);

}
