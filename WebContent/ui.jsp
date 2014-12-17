<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.appspot.passchip_service.WorkSheetContent" %>
<%@ page import="com.appspot.passchip_service.UserEntry" %>
<%@ page import="java.util.List" %>

<html>

<script>


//<request>, 获得sheetiD, username, password
// getSheetInfo(sheetId, username, password)

	<%List<UserEntry> list = WorkSheetContent.getSheetContent("a");%>





function sendRequest(website, usr, password, operation, index){
	var abc = new XMLHttpRequest();
	if (abc!=null)
	  {
	  var params = "website=" + website + "&usr=" + usr + "&password=" + password + "&operation=" + operation + "&index=" + index;
	  abc.open("post","Yalin",true);
	  abc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		abc.setRequestHeader("Content-length", params.length);
		abc.setRequestHeader("Connection", "close");
	  abc.send(params);
	  abc.onreadystatechange = function() {//Call a function when the state changes.
	    if(abc.readyState == 4 && abc.status == 300) {
	        alert("success");
	    }
		}
	  }
	else
	  {
	  alert("Your browser does not support XMLHTTP.");
	  }
}


</script>
<script>

function editRow(btn){
	
	if(confirm("Please confirm the information")){

		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
		var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
		var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
		sendRequest(website, usr, password, 'update', rowIndex);
		
	}
}

function remove(btn){
		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		testTbl.deleteRow(rowIndex);
}

//remove a row in the table
function removeRow(btn){
	var rowIndex=btn.parentNode.parentNode.rowIndex;
	var testTbl =  document.getElementById("table");
	var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
	var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
	var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
	sendRequest(website, usr, password, 'delete', rowIndex);
	remove(btn);		
}
function login(btn){
	
	alert(<%=request.getAttribute("chipID")%>);
}

function cancelAddRow(btn){
	if(confirm("Are your sure to cancel")){
		remove(btn);
	}
}
var jsp;
function saveAddRow(btn){
	var rowIndex=btn.parentNode.parentNode.rowIndex;
	var testTbl =  document.getElementById("table");
	var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
	var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
	var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
	if(website == "skype"){
		jsp = "/skypelogin.jsp";
	}
	else if(website == "disqus"){
		jsp = "/disquslogin.jsp";
	}
	sendRequest(website, usr, password, 'add', rowIndex);
	testTbl.rows[rowIndex].cells[0].innerHTML='<input type="text" value=' + website + '>';
	testTbl.rows[rowIndex].cells[1].innerHTML='<input type="text" value=' + usr + '>';
	testTbl.rows[rowIndex].cells[2].innerHTML='<input type="password" value=' + password + '>';
	testTbl.rows[rowIndex].cells[3].innerHTML='<input type="button" value="edit" onclick="editRow(this)">';
	testTbl.rows[rowIndex].cells[4].innerHTML='<input type="button" value="remove" onclick="removeRow(this)">';
	testTbl.rows[rowIndex].cells[5].innerHTML='<form name="input" action="'+ jsp + '" method="post" target="_blank"><input type="hidden" name="uname" value="' + usr + '"><input type="hidden" name="passw" value="' + password + '"><input type="submit" value="Login"></form>';
}
function addRow(){
	alert(<%=list.get(0).website%>);
	var testTbl =  document.getElementById("table");
	var newTr = testTbl.insertRow(testTbl.rows.length);
	var newTd0 = newTr.insertCell(0);
	var newTd1 = newTr.insertCell(1);
	var newTd2 = newTr.insertCell(2);
	var newTd3 = newTr.insertCell(3);
	var newTd4 = newTr.insertCell(4);
	var newTd5 = newTr.insertCell(5);
	
	newTd0.innerHTML = '<select>	  <option value ="skype">skype</option>	  <option value="disqus">disque</option>	</select>';
	newTd1.innerHTML = '<input type="text" id="usr">';
	newTd2.innerHTML = '<input type="passwsord" id="password">';
	newTd3.innerHTML = '<input type="button" value="done" onclick="saveAddRow(this)">';
	newTd4.innerHTML = '<input type="button" value="cancel" onclick="cancelAddRow(this)">';
	//newTd5.innerHTML = '<form name="input" action="/disquslogin.jsp" method="post" target="_blank"><input type="hidden" name="uname" value="northcrestUser"><input type="hidden" name="passw" value="testpassword"><input type="submit" value="Login"></form>';
}
</script>


<body>
<background>
<p><font size="20" color="red">The is a setting page, user add, delete, update and login supported website</font></p>

<table id="table" border="10" align="center">
	<tr>
	
		<td>website</td>
		<td>usr</td>
		<td>password</td>
		<td>update info</td>
		<td>remove website</td>
		<td>click to login</td>
	</tr>

</table>
	<script>

	//addRow();
	</script>

<p align="center"><input type="button" value="add" onclick="addRow()"></p>




</body>
</html>