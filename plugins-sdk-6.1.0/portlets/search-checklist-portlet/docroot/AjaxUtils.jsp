
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%><script type="text/javascript">
	
	function AjaxUtils()
	{
		this.parameters = {};
		this.callbackfunc = "";
		this.namespace = "<portlet:namespace />";
		this.renderUrl = "<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"></portlet:renderURL>";
		this.actionUrl = "<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"></portlet:actionURL>";
		this.renderRequest = sendAjaxRenderRequest;
		this.actionRequest = sendAjaxActionRequest;
		this.rowRenderRequest = sendAjaxRenderRequestForRow;
		this.parentObj = {};
	}

	function sendAjaxRenderRequest( type )
	{
		var url = this.renderUrl;
		var namespace = this.namespace;
		var callbackfunc = this.callbackfunc;
		var parentObj = this.parentObj;
		
		jQuery.each( this.parameters, function( paramName, paramVal )
			{
				url = url + "&" + namespace + paramName + "=" + paramVal;
			}
		);
		
		url = url + "&r=" + Math.random();
		
		if( type=="get" )
		{
			jQuery.get( url, function( data ){ eval(callbackfunc+"(data, parentObj)"); } );
		}
		else if( type=="getjson" )
		{
			jQuery.getJSON( url, function( data ){ eval(callbackfunc+"(data, parentObj)"); } );
		}
		else if( type=="post" )
		{
			jQuery.post( url, function( data ){ eval(callbackfunc+"(data, parentObj)"); } );
		}
	}

	function sendAjaxRenderRequestForRow( type, rowId )
	{
		var url = this.renderUrl;
		var namespace = this.namespace;
		var callbackfunc = this.callbackfunc;
		var parentObj = this.parentObj;
		
		jQuery.each( this.parameters, function( paramName, paramVal )
			{
				url = url + "&" + namespace + paramName + "=" + paramVal;
			}
		);
		
		url = url + "&r=" + Math.random();
		
		if( type=="get" )
		{
			jQuery.get( url, function( data ){ eval(callbackfunc+"(data, parentObj,'" + rowId + "')"); } );
		}
		else if( type=="getjson" )
		{
			jQuery.getJSON( url, function( data ){ eval(callbackfunc+"(data, parentObj, '" + rowId + "')"); } );
		}
		else if( type=="post" )
		{
			jQuery.post( url, function( data ){ eval(callbackfunc+"(data, parentObj,'" + rowId + "')"); } );
		}
	}

	function sendAjaxActionRequest( type )
	{
		var url = this.actionUrl;
		var namespace = this.namespace;
		var callbackfunc = this.callbackfunc;
		var parentObj = this.parentObj;	
		
		url = url + "&r=" + Math.random();
		if( type=="get" )
		{
			jQuery.each( this.parameters, function( paramName, paramVal )
				{
					url = url + "&" + namespace + paramName + "=" + paramVal;
				}
			);
			
			jQuery.get( url, function( data ){ eval(callbackfunc+"(data, parentObj)"); } );
		}
		else if( type=="getjson" )
		{
			jQuery.each( this.parameters, function( paramName, paramVal )
				{
					url = url + "&" + namespace + paramName + "=" + paramVal;
				}
			);
			
			jQuery.getJSON( url, function( data ){ eval(callbackfunc+"(data, parentObj)"); } );
		}
		else if( type=="post" )
		{
			jQuery.post( url, this.parameters, function( data ){ eval(callbackfunc+"(data, parentObj)"); }, "json" );
		}
	}

	function sendMultipartRequest( formName, targetFrameId )
	{
		var url = this.actionUrl;
		var namespace = this.namespace;
		var callbackfunc = this.callbackfunc;
		var parentObj = this.parentObj;
		
		jQuery.each( this.parameters, function( paramName, paramVal )
			{
				url = url + "&" + namespace + paramName + "=" + paramVal;
			}
		);
		
		url = url + "&r=" + Math.random();

		var form = jQuery( "#" + formName );

		if( form.encoding ) 
		{
	        form.encoding = 'multipart/form-data';
	    } else 
		{
	        form.enctype = 'multipart/form-data';
	    }
	    
		form.attr( "action", url );
		form.attr( "method", "POST" );
		form.attr( "target", targetFrameId );
		
		form.submit();
	}

</script>
