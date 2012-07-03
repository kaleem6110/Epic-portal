<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/iframe/init.jsp" %>

<%
List<String> hiddenVariablesList = ListUtil.toList(StringUtil.split(hiddenVariables, CharPool.SEMICOLON));

hiddenVariablesList.addAll(iframeVariables);
%>

<html dir="<liferay-ui:message key="lang.dir" />">

<head>
	<meta content="no-cache" http-equiv="Cache-Control" />
	<meta content="no-cache" http-equiv="Pragma" />
	<meta content="0" http-equiv="Expires" />
</head>

<body onLoad="setTimeout('document.fm.submit()', 100);">

<form action="<%= HtmlUtil.escape(src) %>" method="<%= formMethod %>" name="fm">

<%
for (String hiddenVariable : hiddenVariablesList) {
	String hiddenKey = StringPool.BLANK;
	String hiddenValue = StringPool.BLANK;

	int pos = hiddenVariable.indexOf(StringPool.EQUAL);

	if (pos != -1) {
		hiddenKey = hiddenVariable.substring(0, pos);
		hiddenValue = hiddenVariable.substring(pos + 1, hiddenVariable.length());
	}
%>

	<input name="<%= hiddenKey %>" type="hidden" value="<%= hiddenValue %>" />

<%
}

if (Validator.isNull(userNameField)) {
	/* NLcom BEGIN REPLACE */
	//int pos = userName.indexOf(StringPool.EQUAL);
	/* NLcom BY */
	int pos = formUserName.indexOf(StringPool.EQUAL);
	/* NLcom END REPLACE */

	if (pos != -1) {
		/* NLcom BEGIN REPLACE */
		//String fieldValuePair = userName;
		/* NLcom BY */
		String fieldValuePair = formUserName;
		/* NLcom END REPLACE */

		userNameField = fieldValuePair.substring(0, pos);
		/* NLcom BEGIN REPLACE */
		//userName = fieldValuePair.substring(pos + 1);
		/* NLcom BY */
		formUserName = fieldValuePair.substring(pos + 1);
		/* NLcom END REPLACE */

		/* NLcom BEGIN REPLACE */
		//preferences.setValue("userName", userName);
		/* NLcom BY */
		preferences.setValue("userName", formUserName);
		/* NLcom END REPLACE */
		preferences.setValue("userNameField", userNameField);

		preferences.store();
	}
}

if (Validator.isNotNull(userNameField)) {
	/* NLcom BEGIN REPLACE */
	//userName = IFrameUtil.getUserName(renderRequest, userName);
	/* NLcom BY */
	formUserName = IFrameUtil.getUserName(renderRequest, formUserName);
	/* NLcom END REPLACE */
}
%>

<%-- NLcom BEGIN REPLACE --%>
<%-- <input name="<%= userNameField %>" type="hidden" value="<%= userName %>" /> --%>
<%-- NLcom BY --%>
<input name="<%= userNameField %>" type="hidden" value="<%= formUserName %>" />
<%-- NLcom END REPLACE --%>

<%
if (Validator.isNull(passwordField)) {
	/* NLcom BEGIN REPLACE */
	//int pos = password.indexOf(StringPool.EQUAL);
	/* NLcom BY */
	int pos = formPassword.indexOf(StringPool.EQUAL);
	/* NLcom END REPLACE */

	if (pos != -1) {
		/* NLcom BEGIN REPLACE */
		//String fieldValuePair = password;
		/* NLcom BY */
		String fieldValuePair = formPassword;
		/* NLcom END REPLACE */

		passwordField = fieldValuePair.substring(0, pos);
		/* NLcom BEGIN REPLACE */
		//password = fieldValuePair.substring(pos + 1);
		/* NLcom BY */
		formPassword = fieldValuePair.substring(pos + 1);
		/* NLcom END REPLACE */

		/* NLcom BEGIN REPLACE */
		//preferences.setValue("password", password);
		/* NLcom BY */
		preferences.setValue("password", formPassword);
		/* NLcom END REPLACE */
		preferences.setValue("passwordField", passwordField);

		preferences.store();
	}
}

if (Validator.isNotNull(passwordField)) {
	/* NLcom BEGIN REPLACE */
	//password = IFrameUtil.getPassword(renderRequest, password);
	/* NLcom BY */
	formPassword = IFrameUtil.getPassword(renderRequest, formPassword);
	/* NLcom END REPLACE */
}
%>

<%-- NLcom BEGIN REPLACE --%>
<%-- <input name="<%= passwordField %>" type="hidden" value="<%= password %>" /> --%>
<%-- NLcom BY --%>
<input name="<%= passwordField %>" type="hidden" value="<%= formPassword %>" />
<%-- NLcom END REPLACE --%>

</form>

</body>

</html>
