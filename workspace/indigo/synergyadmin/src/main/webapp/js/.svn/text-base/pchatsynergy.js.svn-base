{
	
var psyncuid=null;
	
	function initProactiveChat(cid,layout){
		var curPage = window.location.pathname;
	
		if(hasProactiveCookie()){
			return;
		}
		
		var embed = '<embed id="proacSynChat" height="0" width="0" flashvars="cid='+cid+'&curPage='+curPage+'&visid='+psyncuid+'" scale="exactfit" allowScriptAccess="always" allowfullscreen="false" quality="high" name="proacSynChat" style="" src="http://www.synergychat.com:9091/flash/proactiveSynergyChat.swf" type="application/x-shockwave-flash"/>';
		var pop="";
		//if(!layout || layout==null || layout==1){
			pop = '<div id="syncinvwnd" style="height:230px;background-color:white;display:none;position:absolute;top:50px;left:40px;">'+
			'<div id="dTop" style="background-image:url(http://www.synergychat.com:9091/img/D1.jpg);height:165px;width:400px;"></div>'+
			'<div id="dBottom" style="background-image:url(http://www.synergychat.com:9091/img/D2.jpg);height:68px;width:400px;">'+
			'<div id="start" style="position:relative;top:7px;left:65px;height:34px;width:138px;">'+
			'<a onclick="startChat(\''+cid+'\');" style="cursor:pointer">'+
			'<div id="start_link" style="background-image:url(http://www.synergychat.com:9091/img/start_up.jpg);height:34px;width:138px;"></div></a>'+
			'</div><div id="stop" style="position:relative;top:-27px;left:208px;height:34px;width:138px;">'+
			'<a onclick="hideChatInvite()" style="cursor:pointer">'+
			'<div id="stop_link" style="background-image:url(http://www.synergychat.com:9091/img/stop_up.jpg);height:34px;width:138px;"></div></a>'+
			'</div>	</div>	</div>';
		//}
		document.write(embed+pop);
	}
	
	function hideChatInvite(){
		document.getElementById('syncinvwnd').style.display='none';
	}


function showChatInvite(cid,_userid,_proacId, msg){
		if(hasProactiveCookie()){
			return;
		}
		createCookie("psynchatcookie"+cid, "true", 5);
		psynid=_proacId;
		psyncuid=_userid;
		
		var synwnd = document.getElementById('syncinvwnd');
		synwnd.style.display='block';		
		synwnd.style.left = (screen.width / 2)- (synwnd.clientWidth/2)+"px";
		synwnd.style.top = (((screen.height/2) - (synwnd.clientHeight)/2)+window.pageYOffset-125)+"px";
		var dmsg = document.createElement("div");
		dmsg.innerHTML = "<div style='border:1px solid blue;background-color:white;width:320px;height:60px;font-size:12pt'>"+msg+"</div>";
		dmsg.style.position = "relative";
		dmsg.style.left = 45+"px";
		dmsg.style.top = -130+"px";
		synwnd.appendChild(dmsg);
	}

	var psynid="";
	
	function startChat(cid){
		hideChatInvite();
		if(psyncuid==null){
			alert("Chat couldn't be started. Please refresh this page.");
			return;
		}
		var n = "wnd_"+Math.floor(Math.random()*1000);
		var url = "http://www.synergychat.com:9091/chat?op=openSynergyPanel2&canResize=false&oMode=5&cid="+cid+"&toDestId="+psyncuid+"&proacId="+psynid;

		window.open(url,n,"width=550,height=500,resizable=1");
		
		//var d = "<div style='position:absolute;top:100px; left:200px'><iframe width='550' height='500' src='http://www.synergychat.com:9091/chat?op=openSynergyPanel2&canResize=false&oMode=2&cid="+cid+"&toDestId="+userid+"' frameborder=0></iframe></div>";
		//document.body.innerHTML+=d;
	}
	
	function hasProactiveCookie(){
		var psynchatcookie = readCookie("psynchatcookie");
		//if user already got invite, then return.
		if(psynchatcookie!=null && psynchatcookie=="true"){
			return true;
		}
		return false;
	}
	
	function createCookie(name,value,minutes) {        
        if (minutes) {
            var date = new Date();
            date.setTime(date.getTime()+(minutes*/*24*60**/60*1000));
            var expires = "; expires="+date.toGMTString();
        }
        else var expires = "";
        document.cookie = name+"="+value+expires+"; path=/";
    }

    function readCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        //alert(document.cookie);
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }

    function eraseCookie(name) {        
        createCookie(name,"",-1);
    }
	
}