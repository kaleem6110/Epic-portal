// jQuery Alert Dialogs Plugin
//
// Version 1.1
//
// Cory S.N. LaViska
// A Beautiful Site (http://abeautifulsite.net/)
// 14 May 2009
//
// Visit http://abeautifulsite.net/notebook/87 for more information
//
// Usage:
//		jAlert( message, [title, callback] )
//		jConfirm( message, [title, callback] )
//		jPrompt( message, [value, title, callback] )
// 
// History:
//
//		1.00 - Released (29 December 2008)
//
//		1.01 - Fixed bug where unbinding would destroy all resize events
//
// License:
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
(function($) {
	
	$.alerts = {
		
		// These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time
		
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .01,                // transparency level of overlay
		overlayColor: '#FFF',               // base color of overlay
		draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
		okButton: '&nbsp;OK&nbsp;',         // text for the OK button
		cancelButton: '&nbsp;Cancel&nbsp;', // text for the Cancel button
		dialogClass: null,                  // if specified, this class will be applied to all dialogs
		
		// Public methods
		
		alert: function(message, title, callback) {
			if( title == null ) title = 'Alert';
			$.alerts._show(title, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		
		confirm: function(message, title, callback) {
			if( title == null ) title = 'Confirm';
			$.alerts._show(title, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},
			
		prompt: function(message, value, title, callback) {
			if( title == null ) title = 'Prompt';
			$.alerts._show(title, message, value, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		
		// Private methods
		
		_show: function(title, msg, value, type, callback) {
			
			$.alerts._hide();
			$.alerts._overlay('show');
			
			$("BODY").append(
			  '<div id="mypopup_container" style="max-width:310px;padding:10px;">' +
			    '<h1 id="mypopup_title"></h1>' +
			    '<div id="mypopup_content">' +
			      '<div id="mypopup_message" style="margin-bottom:20px;"></div>' +
				'</div>' +
			  '</div>');
			
			if( $.alerts.dialogClass ) $("#mypopup_container").addClass($.alerts.dialogClass);
			
			// IE6 Fix
			var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed'; 
			
			$("#mypopup_container").css({
				position: pos,
				zIndex: 99999,
				padding: 0,
				margin: 0
			});
			
			$("#mypopup_title").text(title);
			$("#mypopup_content").addClass(type);
			$("#mypopup_message").text(msg);
			$("#mypopup_message").html( $("#mypopup_message").text().replace(/\n/g, '<br />') );
			
			$("#mypopup_container").css({
				minWidth: $("#mypopup_container").outerWidth(),
				maxWidth: $("#mypopup_container").outerWidth()
			});
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);
			
			switch( type ) {
				case 'alert':
					$("#mypopup_message").after('<div id="mypopup_panel"><input type="button" value="' + $.alerts.okButton + '" id="mypopup_ok" /></div>');
					$("#mypopup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#mypopup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#mypopup_ok").trigger('click');
					});
				break;
				case 'confirm':
					$("#mypopup_message").after('<div id="mypopup_panel"><input type="button" value="' + $.alerts.okButton + '" id="mypopup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="mypopup_cancel" /></div>');
					$("#mypopup_ok").click( function() {
						$.alerts._hide();
						if( callback ) callback(true);
					});
					$("#mypopup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback(false);
					});
					$("#mypopup_ok").focus();
					$("#mypopup_ok, #mypopup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#mypopup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#mypopup_cancel").trigger('click');
					});
				break;
				case 'prompt':
					$("#mypopup_message").append('<br /><input type="text" size="30" id="mypopup_prompt" />').after('<div id="mypopup_panel"><input type="button" value="' + $.alerts.okButton + '" id="mypopup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="mypopup_cancel" /></div>');
					$("#mypopup_prompt").width( $("#mypopup_message").width() );
					$("#mypopup_ok").click( function() {
						var val = $("#mypopup_prompt").val();
						$.alerts._hide();
						if( callback ) callback( val );
					});
					$("#mypopup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback( null );
					});
					$("#mypopup_prompt, #mypopup_ok, #mypopup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#mypopup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#mypopup_cancel").trigger('click');
					});
					if( value ) $("#mypopup_prompt").val(value);
					$("#mypopup_prompt").focus().select();
				break;
			}
			
			// Make draggable
			if( $.alerts.draggable ) {
				try {
					$("#mypopup_container").draggable({ handle: $("#mypopup_title") });
					$("#mypopup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		_hide: function() {
			$("#mypopup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},
		
		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="mypopup_overlay"></div>');
					$("#mypopup_overlay").css({
						position: 'absolute',
						zIndex: 99998,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),
						background: $.alerts.overlayColor,
						opacity: $.alerts.overlayOpacity
					});
				break;
				case 'hide':
					$("#mypopup_overlay").remove();
				break;
			}
		},
		
		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#mypopup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#mypopup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			// IE6 fix
			if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();
			
			$("#mypopup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#mypopup_overlay").height( $(document).height() );
		},
		
		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
		
	}
	
	// Shortuct functions
	jAlert = function(message, title, callback) {
		$.alerts.alert(message, title, callback);
	}
	
	jConfirm = function(message, title, callback) {
		$.alerts.confirm(message, title, callback);
	};
		
	jPrompt = function(message, value, title, callback) {
		$.alerts.prompt(message, value, title, callback);
	};
	
})(jQuery);