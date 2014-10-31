{
	var server = "..";

	var userKey = null;
	
	var canSendPend = true;
	
	function setUserKey(key){
		userKey = key;
		checkPending();
	}
	
	function checkPending(){
		if(canSendPend==true){
			//Comentado por teste
			sendAjaxReq("/synergychat/chat","op=hasPend&userKey="+userKey);
			setTimeout("checkPending()",3000);
		}
	}
	
	function sendAjaxReq(req, pars){
		var url = req;
		if(pars==null){
			pars = '';
		}		
			
		var myAjax = new Ajax.Request(
			url,
			{
				method: 'get', 
				parameters: pars, 
				onComplete: showResponse
			});
	}
	
	function showResponse(origReq){
		eval(origReq.responseText);		
	}
	
	var pendChatId=null;
	
	function showChatInvite(chatId){
		pendChatId = chatId;
		var d = document.getElementById("incomingChat");
		d.innerHTML = "Incoming Chat <button onclick='acceptChatInvite()'>Accept</button>&nbsp;<button onclick='rejectChatInvite()'>Reject</button>";
		d.style.display="";
	}
	
	function joinChat(ci){
		isJoining = true;
		window.location = "/synergychat/chat?op=joinChat&userKey="+userKey+"&ci="+ci;
		window.curChatId = ci;
	}
	
	function rejectChatInvite(){
		if(confirm("Are you sure you want to reject this Incoming Chat?")){
			sendAjaxReq("/synergychat/chat","?op=rejectChat&ci="+pendChatId);
		}
	}
	
	function acceptChatInvite(){
		curChatId = pendChatId;
		cliGoBusy();		
		var d = document.getElementById("incomingChat");
		d.style.display = "none";
		window.open("/synergychat/chat?op=acceptChat&ci="+pendChatId);
		pendChatId=null;
	}
	
	function userRejectedChat(ci){
		document.write("Your chat invite has being rejected.");
	}
	
	function userLeavedChat(ci){
		if(confirm("The other user has leaved the chat. This chat session is beeing closed.")){
			window.close();
		}
	}
	
	function unloadUserPage(){
		serverGoOffline();
	}
	
	var curChatId = null;
	
	var isJoining = false;
	
	function unloadChat(){
		sendAjaxReq("/synergychat/chat","op=leaveChat&ci="+curChatId+"&userKey="+userKey);
		if(window.opener!=null){
			window.opener.cliGoOnline();
		}
	}
	
	function unloadChatConnecting(){
		if(isJoining==false){
			sendAjaxReq("/synergychat/chat","op=leaveChat&ci="+curChatId+"&userKey="+userKey);
		}
	}
	
	function serverChangeState(st){
		sendAjaxReq("/synergychat/chat","?op=changeState&state="+busy+"&userKey="+userKey);	
	}
	
	function serverGoOnline(){
		serverChangeState(0)
	}
	
	function serverGoOffline(){
		serverChangeState(1)
	}
	
	function serverGoBusy(){
		serverChangeState(2)
	}	
	
	function cliChangeState(st){
		if(st=="0"){
			cliGoOnline();			
		}else if(st=="1"){
			cliGoOffline();
		}else if(st=="2"){
			cliGoBusy();
		}
	}
	
	function cliGoOnline(){
		alert("online");
		canSendPend = true;
	}
	
	function cliGoOffline(){
		
	}
	
	function cliGoBusy(){
		alert("busy");
		canSendPend = false;
	}
	
	function viewSharedFile(fileId, contType, toRoom){	
		//var ret = window.open(server + '/shared/'+fileId+'?contType='+contType+'&toRoom='+toRoom);
		var ret = window.open('shared/'+fileId+'?contType='+contType+'&toRoom='+toRoom);
		try{
			ret.focus()
			}catch(e){
				alert(e);
			}
		if(ret == null){
			alert("erro");
		}		
		window.event.cancelBubble = true;
	}
	
	function addSharedFile(fileId, fileName, contType, un){
		var args = fileId.split("_");
		var wnd = document.getElementById("wnd_"+args[0]);
		if(wnd != null){
			wnd.firstChild.contentWindow.addSharedFile(fileId, fileName, contType, un);
			return;
		}
		var table = $("br_share");
		var rN = table.tBodies[0].rows.length;
		var tr = document.createElement("TR");
		tr.id = args[0]+"r_"+rN;
		var td1 = document.createElement("TD");
		var tdCont = "<td height=\"25\" colspan=\"5\" align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3A3A3A\">"+                                                
                     "<tr>"+
                       "<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">"+
                         "<tr>"+
                           "<td height=\"17\" align=\"center\" class=\"style34\"><span style='width: 60px;overflow: hidden'>"+un+"</span></td>"+
                           "<td height=\"17\" align=\"center\" class=\"style34\"><span style='width: 90px;overflow: hidden'>"+fileName+"</span></td>"+
                           "<td align=\"center\" class=\"style34\">"+
                           "<input name=\"Submit5\" type=\"submit\" class=\"buttonsubview\" style=\"background-image: url(image/bgshared.gif)\" onclick=\"viewSharedFile('_"+fileId+"/"+fileName+"', '"+contType+"',false)\" title='View File' value=\"View\" />&nbsp;"+
                           "<input name=\"Submit5\" type=\"submit\" class=\"buttonsubview\" style=\"background-image: url(image/bgshared.gif)\" onclick=\"viewSharedFile('_"+fileId+"/"+fileName+"', '"+contType+"',true)\" title='Open to Room' value=\"O.R.\" />"+
                           "</td>"+
                         "</tr>"+
                         "<tr>"+
                           "<td height=\"1\" colspan=\"3\" align=\"center\" bgcolor=\"#494949\"><img src=\"image/glass.gif\" width=\"1\" height=\"1\" /></td>"+
                         "</tr>"+
                       "</table></td>"+
                     "</tr>"+
                 "</table></td>";
		td1.innerHTML = tdCont;
		/*var td2 = document.createElement("TD");
		td2.innerHTML = fileName;
		var td3 = document.createElement("TD");
		td3.innerHTML = "<button onclick=\"viewSharedFile('"+fileId+"/"+fileName+"', '"+contType+"')\">View</button>";
		tr.appendChild(td1);
		tr.appendChild(td2);*/
		tr.appendChild(td1);		
		table.tBodies[0].appendChild(tr);
	}
	
	function showResponse(origReq){
		eval(origReq.responseText);		
	}
	
	function startCallback() {
            // make something useful before submit (onStart)
            return true;
    }

    function completeCallback(response) {
    		//code necessary for firefox;
    		if(response.indexOf("pre>")>=0){
    			response = response.replace("<pre>","");
    			response = response.replace("</pre>","");
    		}    	
    		eval(response);
            // make something useful after (onComplete)
            /*document.getElementById('nr').innerHTML = parseInt(document.getElementById('nr').innerHTML) + 1;
            document.getElementById('r').innerHTML = response;*/
    }
}