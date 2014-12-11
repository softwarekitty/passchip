<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<script>
function addRow(){
	var testTbl =  document.getElementById("table");
	var newTr = testTbl.insertRow(testTbl.rows.length);
	var newTd0 = newTr.insertCell(0);
	var newTd1 = newTr.insertCell(1);
	var newTd2 = newTr.insertCell(2);
	var newTd3 = newTr.insertCell(3);
	var newTd4 = newTr.insertCell(4);
	newTd0.innerHTML = '<select>	  <option value ="volvo">Volvo</option>	  <option value="audi">Audi</option>	</select>';
	newTd1.innerHTML = '<input type="text" id="usr">';
	newTd2.innerHTML = '<input type="passwsord" id="password">';
	newTd3.innerHTML = '<input type="button" value="done" onclick="saveAddRow(this)">';
	newTd4.innerHTML = '<input type="button" value="cancel" onclick="cancelAddRow(this)">';
}
</script>
<script>

function saveAddRow(btn){
	var rowIndex=btn.parentNode.parentNode.rowIndex;
	var testTbl =  document.getElementById("table");
	var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
	var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
	var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
	//saveToSheet(website, usr, password);
}

function cancelAddRow(btn){
	if(confirm("Are your sure to cancel")){
		remove(btn);
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
	//removeOnSheet(website, usr, password);		
}

function saveRow(btn){
	if(confirm("Please confirm the information")){

		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
		var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
		var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
		testTbl.rows[rowIndex].cells[3].innerHTML='<input type="button" value="edit" onclick="editRow(this)">';
		//saveToSheet(website, usr, password);
	}
}

function editRow(btn){
	var rowIndex=btn.parentNode.parentNode.rowIndex;
	var testTbl =  document.getElementById("table");
	testTbl.rows[rowIndex].cells[3].innerHTML='<input type="button" value="save" onclick="saveRow(this)">';
}

function sendRequest(website, usr, password, operation){
	var abc = new XMLHttpRequest();
	if (abc!=null)
	  {
	  alert("send");
	  var params = "website=" + website + "&usr=" + usr + "&password=" + password + "&operation=" + operation;
	  abc.open("post","http://127.0.0.1:8888/passchip-service/Yalin",true);
	  abc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		abc.setRequestHeader("Content-length", params.length);
		abc.setRequestHeader("Connection", "close");
	  abc.send(params);
	  abc.onreadystatechange = function() {//Call a function when the state changes.
	    if(abc.readyState == 4 && abc.status == 300) {
	        alert("200");
	    }
		}
	  }
	else
	  {
	  alert("Your browser does not support XMLHTTP.");
	  }
}
</script>

<body>

<table id="table" border="10">
	<tr>
	
		<td>website</td>
		<td>usr</td>
		<td>password</td>
		<td>update info</td>
		<td>remove website</td>
		<td>click to login</td>
	</tr>
	<tr>
		<td><a href="http://www.facebook.com">facebook</a></td>
		<td><input type="text" name="usr", value="keyalin" ></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow()"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
		<td><input type="button" value="login" onclick="login(this)"></td>
	</tr>
	<tr>
		<td><a href="http://www.twitter.com">twitter</a></td>
		<td><input type="text" name="usr", value="keyalin"></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow(this)"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
		<td><input type="button" value="login" onclick="login(this)"></td>
	</tr>
</table>
<input type="button" value="add" onclick="addRow()">
<input type="button" value="testServlet" onclick="sendRequest('facebook', 'ke1', 'name1', 'delete')">




</body>
</html>