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

import com.liferay.geoipusersmap.model.CheckListBean;

import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.jsp.JSPPortlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletSession;
import com.liferay.util.bridges.mvc.MVCPortlet;


import com.liferay.geoipusersmap.model.GeoIPUsersMapDAO;

/**
 * <a href="ViewCheckListPortlet..java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class ViewCheckListPortlet extends MVCPortlet {

	public void init() throws PortletException {
		editJSP = getInitParameter("edit-jsp");
		helpJSP = getInitParameter("help-jsp");
		viewJSP = getInitParameter("view-jsp");
		resultJSP = getInitParameter("result-jsp");
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
		System.out.println(" !!!!!!!!!!!!!!!!!!!ViewCheckListPortlet doView RENDER START !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
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
			System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!actionType : "+actionType);
			
			if( userId!=null && userId!="0")
			{
				if( actionType.equals("viewChecklist")||  actionType.equals("updateChecklist") )
				{
					String chklist_id = renderRequest.getParameter("chklist_id");
					if(  chklist_id!=null&& Integer.parseInt(chklist_id)>0)
					{
						String description= renderRequest.getParameter("description");
						String name= renderRequest.getParameter("name");
						String author= renderRequest.getParameter("author");
						String isGlobal= renderRequest.getParameter("isGlobal");
						String createdDate = renderRequest.getParameter("createdDate");
						String isOwner = renderRequest.getParameter("isOwner");
						portletSession.setAttribute("chklist_id", chklist_id,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("description", description,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("name", name,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("author", author,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("isGlobal", isGlobal,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("createdDate", createdDate,PortletSession.APPLICATION_SCOPE);
						portletSession.setAttribute("isOwner", isOwner,PortletSession.APPLICATION_SCOPE);
						include(resultJSP, renderRequest, renderResponse);
					}
					else
					{
						include(viewJSP, renderRequest, renderResponse);	
					}
				}
				else if (actionType.equals("deleteChecklist")) 
				{	
					String chklist_id = renderRequest.getParameter("chklist_id");
					System.out.println(" 1222 chklist_id : "+ chklist_id );
					
					include(viewJSP, renderRequest, renderResponse);	
				}
				else if (actionType.equals("editChecklist")) 
				{	
					String chklist_id = renderRequest.getParameter("chklist_id");
					String name = renderRequest.getParameter("name");
					String description = renderRequest.getParameter("description");
					
					System.out.println(" 1222 chklist_id : "+ chklist_id );
					portletSession.setAttribute("chklist_id", chklist_id,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("name",  name,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("description", description,PortletSession.APPLICATION_SCOPE);
					
					include(editJSP, renderRequest, renderResponse);	
				}
				else if (actionType.equals("shareChecklist")||actionType.equals("unShareChecklist")) 
				{	
					String chklist_id = renderRequest.getParameter("chklist_id");
					String description= renderRequest.getParameter("description");
					String name= renderRequest.getParameter("name");
					String isGlobal= renderRequest.getParameter("isGlobal");
					String author= renderRequest.getParameter("author");
					
					portletSession.setAttribute("chklist_id", chklist_id,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("description", description,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("name", name,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("author", author,PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("isGlobal", isGlobal,PortletSession.APPLICATION_SCOPE);
					System.out.println(" 1222 chklist_id : "+ chklist_id );
					
					include(resultJSP, renderRequest, renderResponse);	
				}
				else if (actionType.equals("searchView")) 
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
		System.out.println(" !!!!!!!!!!!!!!!!!!!ViewCheckListPortlet doView RENDER END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
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
		System.out.println(" !!!!!!!!!!!!!!!!!!!ViewCheckListPortlet processAction  START !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");

		try
		{
		PortletSession portletSession = actionRequest.getPortletSession();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);		
		System.out.println("!!!!!!!!!!!!!!!!cmd :"+ cmd );
		String userId = actionRequest.getRemoteUser();
	
		if (cmd.equals("viewChecklist")) 
		{			
			String chlistId = ParamUtil.getString(actionRequest,"chklist_id");
			System.out.println("chlistId :"+chlistId );
			if( chlistId!=null&& Integer.parseInt(chlistId)>0 )
			{
				GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();				
				CheckListBean chklistBean = dao.fetchChecklistByChkId( chlistId.trim() );				
				String description=getDescription(chklistBean.getDescription() ) ;		
				
				actionResponse.setRenderParameter("actionType", cmd );
				actionResponse.setRenderParameter("name", chklistBean.getName() );
				actionResponse.setRenderParameter("chklist_id", chlistId );
				actionResponse.setRenderParameter("description", description );
				actionResponse.setRenderParameter("author", chklistBean.getOwnerName() ); 
				actionResponse.setRenderParameter("isGlobal", chklistBean.getIsGlobal() );
				actionResponse.setRenderParameter("createdDate", chklistBean.getCreatedDate() );
				actionResponse.setRenderParameter("isOwner", "0" );
				if( userId.equals( chklistBean.getOwnerId() ))
				{
					actionResponse.setRenderParameter("isOwner", "1" );
				}
				System.out.println( "@@@@@ description "+description);
			}
			else
			{
				actionResponse.setRenderParameter("actionType", cmd );
				
			}
		}
		else if (cmd.equals("updateChecklist")) 
		{		
			
			
			String description = ParamUtil.getString(actionRequest, "fckEditorContent");
			String chklist_id = ParamUtil.getString(actionRequest, "chklist_id");
			System.out.println("description :"+ description+ " chklist_id: "+chklist_id );
			CheckListBean chklistBean = GeoIPUsersMapDAO.updateChecklist( chklist_id , description);
			description=getDescription(chklistBean.getDescription() ) ;		
			
			actionResponse.setRenderParameter("actionType", cmd );
			actionResponse.setRenderParameter("name", chklistBean.getName() );
			actionResponse.setRenderParameter("chklist_id", chklist_id );
			actionResponse.setRenderParameter("description", description );
			actionResponse.setRenderParameter("author", chklistBean.getOwnerName() ); 
			actionResponse.setRenderParameter("isGlobal", chklistBean.getIsGlobal() );
			actionResponse.setRenderParameter("createdDate", chklistBean.getCreatedDate() );
			actionResponse.setRenderParameter("isOwner", "0" );
			if( userId.equals( chklistBean.getOwnerId() ))
			{
				actionResponse.setRenderParameter("isOwner", "1" );
			}
			System.out.println( "@@@@@ description "+description);
		}
		else if (cmd.equals("deleteChecklist")) 
		{	
			String chklist_id =actionRequest.getParameter("del_chklist_id");
			System.out.println(" 222 chklist_id : "+ chklist_id );
			GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
			if( chklist_id!= null )  dao.deleteChecklistById( chklist_id );	
			
			actionResponse.setRenderParameter("actionType", cmd );
			
		}
		else if (cmd.equals("editChecklist")) 
		{	
			String chklist_id =actionRequest.getParameter("edit_chklist_id");
			System.out.println(" 333 chklist_id : "+ chklist_id );	
			GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();				
			CheckListBean chklistBean = dao.fetchChecklistByChkId( chklist_id.trim() );				
			String descn=chklistBean.getDescription() ;
			descn=descn.replaceAll("</p>", "");
			 descn=descn.replaceAll("&nbsp;", "");
			 descn=descn.replaceAll("</p>", "");		
			 descn=descn.replaceAll("<br/>", "");
			 descn =descn.replaceAll("<p></p>", "");
			
			actionResponse.setRenderParameter("actionType", cmd );
			actionResponse.setRenderParameter("chklist_id", chklist_id );
			actionResponse.setRenderParameter("name", chklistBean.getName()  );
			actionResponse.setRenderParameter("description", descn );
		}
		else if (cmd.equals("shareChecklist")) 
		{	
			String chlistId =actionRequest.getParameter("share_chklist_id");
			System.out.println(" 222 chklist_id : "+ chlistId );
			GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
			if( chlistId!= null )  dao.shareCheckListWithPublic( chlistId, "1" );
			
			CheckListBean chklistBean = dao.fetchChecklistByChkId( chlistId.trim() );
			String description=getDescription(chklistBean.getDescription() ) ;	
			
			actionResponse.setRenderParameter("actionType", cmd );
			actionResponse.setRenderParameter("name", chklistBean.getName() );
			actionResponse.setRenderParameter("chklist_id", chlistId );
			actionResponse.setRenderParameter("description", description );
			actionResponse.setRenderParameter("author", chklistBean.getOwnerName() );
			actionResponse.setRenderParameter("isGlobal", chklistBean.getIsGlobal() );
			
		}
		else if (cmd.equals("unShareChecklist")) 
		{	
			String chlistId =actionRequest.getParameter("unshare_chklist_id");
			System.out.println(" 222 chklist_id : "+ chlistId );
			GeoIPUsersMapDAO dao = new GeoIPUsersMapDAO();
			if( chlistId!= null )  dao.shareCheckListWithPublic( chlistId, "0" );
			
			CheckListBean chklistBean = dao.fetchChecklistByChkId( chlistId.trim() );
			String description=getDescription(chklistBean.getDescription() ) ;	
			
			actionResponse.setRenderParameter("actionType", cmd );
			actionResponse.setRenderParameter("name", chklistBean.getName() );
			actionResponse.setRenderParameter("chklist_id", chlistId );
			actionResponse.setRenderParameter("description", description );
			actionResponse.setRenderParameter("author", chklistBean.getOwnerName() );
			actionResponse.setRenderParameter("isGlobal", chklistBean.getIsGlobal() );
			
		}
		else 
		{			
			
			actionResponse.setRenderParameter("actionType", cmd );
		}
		}catch(Exception e){e.printStackTrace(); }
		

		
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!ViewCheckListPortlet processAction  END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
		System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
	}
 protected String getDescription( String descn)
 {
	 System.out.println(" !!!!START getDescription   !!!!!!!!!!!!!!!!!!!!!!! "); 
	 	
		 descn=descn.replaceAll("</p>", "");
		 descn=descn.replaceAll("&nbsp;", "");
		 descn=descn.replaceAll("</p>", "");		
		 descn=descn.replaceAll("<br/>", "");
		 descn =descn.replaceAll("<p></p>", "");
		 
	 	String[] descriptionArray = descn.split("<p>");
		String description="";
		for(int i=0;i<descriptionArray.length;i++)
		{
			String text= descriptionArray[i];
			
			if(text!=null && text.trim()!=""&& text.length()>5)
			{
				
				//description = description +"<img  height='12' src='/view-checklist-portlet/images/tick_mark.gif'></img> &nbsp; "+text+"<br>";
				description = description +"<input type='checkbox'  height='12' checked></input> &nbsp; "+text+"<br>";
				
			}				
		}
		
		System.out.println(" !!!!END getDescription   !!!!!!!!!!!!!!!!!!!!!!! "); 
		return description;
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
	protected String resultJSP;

	private static Log _log = LogFactoryUtil.getLog(ViewCheckListPortlet.class);

}
