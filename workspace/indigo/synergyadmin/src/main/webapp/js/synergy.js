{
	
	function createCookie(strCookie, strValor, lngDias)
	{
	    var dtmData = new Date();
	
	    if(lngDias)
	    {
	        dtmData.setTime(dtmData.getTime() + (lngDias * 24 * 60 * 60 * 1000));
	        var strExpires = "; expires=" + dtmData.toGMTString();
	    }
	    else
	    {
	        var strExpires = "";
	    }
	    document.cookie = strCookie + "=" + strValor + strExpires + "; path=/";
	}

	function getScreenSize() {
		return screen.width+";"+screen.height;
	}

	function getCurrentTime() {
		  
		var a_p = "";
		var d = new Date();
		var curr_hour = d.getHours();
		if (curr_hour < 12) {
			a_p = "AM";
	   	} else {
	   		a_p = "PM";
		}
	
	
		if (curr_hour == 0) {
		   curr_hour = 12;
	   	}
	
		if (curr_hour > 12) {
	   		curr_hour = curr_hour - 12;
	   	}
	
		var curr_min = d.getMinutes();
	
		curr_min = curr_min + "";
	
		if (curr_min.length == 1) {
			curr_min = "0" + curr_min;
		}
	
		return curr_hour + ":" + curr_min + "" + a_p;
   }

	///*************************************************////
	//SCREEN SHARE FUNCTIONS//
	var viewSsWnd=null;
	var started=false;
	function startShareScreen(ssId){
		var param = "\\\\vscr=screen\\vid:svc2&bitrate:400&keyint:10000&bits:4&upd:1000";
		document.JScrCap.start_streaming("0", "rtmp://synergychat.com:1937/splitstream+"+ssId+param);
		document.JScrCap.set_EventEnable(1);
	}

	function stopShareScreen(){
		document.JScrCap.stop_streaming();
	}


	function JScrCap_Event(operation, code, desc) {
		document.getElementById('SynergyChatFlash').onShareScreenEvent(operation, code, desc);
		//document.getElementById('evtstatus').value = ("op: " + operation + ", code: " + code + ", desc: " + desc);
	}   

	function startViewSharedScreen(ssId){
		var n = "wnd_"+Math.floor(Math.random()*1000);
//		viewSsWnd = window.open("http://www.synergychat.com:9091/ShareScreenViewer.jsp?ssId="+ssId,n,"width=600,height=400");
		//viewSsWnd = window.open("http://localhost:8080/synergychat/ShareScreenViewer.jsp?ssId="+ssId,n,"width=600,height=400");
	}

	function stopViewSharedScreen(ssId){
		if(viewSsWnd!=null){
			viewSsWnd.close();
		}	
	}

	var insOk=false;

	function setupShareScreen(){
		if(insOk==false){
			var applet = '<applet codebase="https://www.synergychat.com:8443/synergytest/thirdparty" code="CToolkitUIStarter.class" name="Java Screen Capture" archive="screenshare.jar,JScrCapUI.jar" width="1" height="1" id="JScrCap" mayscript="yes" scriptable="true"> </applet>';
			document.getElementById('ssContainer').innerHTML = applet;
			insOk=true;
		}
	}
	//END SHARE SCREEN//
	
	
	function clearField(f){
		f.value="";
	}
	function openSynergyWindow(cid,oMode,canResize,toDestId){
		var n = "wnd_"+Math.floor(Math.random()*1000);
		window.open("http://www.synergychat.com/chat?op=openSynergyPanel2&cid="+cid+"&oMode="+oMode+"&canResize="+canResize+"&toDestId="+toDestId,n,"width=550,height=500,resizable=1");
	}	
	
}