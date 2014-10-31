/***************REGULAR IMAGES **********************************/
var rslt = navigator.appVersion.match(/MSIE (\d+\.\d+)/, '');
var itsAllGood = (rslt != null && Number(rslt[1]) >= 5.5 && Number(rslt[1]) < 7);

if(itsAllGood) {
	if (navigator.platform == "Win32" && navigator.appName == "Microsoft Internet Explorer" && window.attachEvent) {
		window.attachEvent("onload", fnLoadPngs);
	}
}

function fnLoadPngs() {
	var rslt = navigator.appVersion.match(/MSIE (\d+\.\d+)/, '');
	var itsAllGood = (rslt != null && Number(rslt[1]) >= 5.5 && Number(rslt[1]) < 7);

	for (var i = document.images.length - 1, img = null; (img = document.images[i]); i--) {
		if (itsAllGood && img.src.match(/\.png$/i) != null) {
			var src = img.src;
			var div = document.createElement("DIV");
			div.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizing='scale')"
			div.style.width = img.width + "px";
			div.style.height = img.height + "px";
			img.replaceNode(div);
		}
		img.style.visibility = "visible";
	}
}

/***************BACKGROUND IMAGES **********************************/
var bgsleight	= function() {
	
	function addLoadEvent(func) {
		var oldonload = window.onload;
		if (typeof window.onload != 'function') {
			window.onload = func;
		} else {
			window.onload = function() {
				if (oldonload) {
					oldonload();
				}
				func();
			}
		}
	}
	
	function fnLoadPngs() {
		var rslt = navigator.appVersion.match(/MSIE (\d+\.\d+)/, '');
		var itsAllGood = (rslt != null && Number(rslt[1]) >= 5.5 && Number(rslt[1]) < 7);
		for (var i = document.all.length - 1, obj = null; (obj = document.all[i]); i--) {
			if (itsAllGood && obj.currentStyle.backgroundImage.match(/\.png/i) != null) {
				fnFixPng(obj);
				obj.attachEvent("onpropertychange", fnPropertyChanged);
			}
		}
	}

	function fnPropertyChanged() {
		if (window.event.propertyName == "style.backgroundImage") {
			var el = window.event.srcElement;
			if (!el.currentStyle.backgroundImage.match(/images\/x\.gif/i)) {
				var bg	= el.currentStyle.backgroundImage;
				var src = bg.substring(5,bg.length-2);
				el.filters.item(0).src = src;
				el.style.backgroundImage = "url(images/x.gif)";
			}
		}
	}

	function fnFixPng(obj) {
		var bg	= obj.currentStyle.backgroundImage;
		var src = bg.substring(5,bg.length-2);
		obj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizingMethod='scale')";
		obj.style.backgroundImage = "url(images/x.gif)";
	}
	
	
	return {	
		init: function() {
			if (navigator.platform == "Win32" && navigator.appName == "Microsoft Internet Explorer" && window.attachEvent) {
				addLoadEvent(fnLoadPngs);
			}
			
		}
	}
	
}();

bgsleight.init();