<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>

<script>







function saveRow(btn){
	if(confirm("Please confirm the information")){

		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
		var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
		var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
		//saveToSheet(website, usr, password, 'update');
		testTbl.rows[rowIndex].cells[3].innerHTML='<input type="button" value="edit" onclick="editRow(this)">';
		
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
	  var params = "website=" + website + "&usr=" + usr + "&password=" + password + "&operation=" + operation;
	  abc.open("post","http://127.0.0.1:8888/passchip-service/Yalin",true);
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
	//sendRequest(website, usr, password, 'delete');
	remove(btn);		
}
function login(btn){
	alert("");
}

function cancelAddRow(btn){
	if(confirm("Are your sure to cancel")){
		remove(btn);
	}
}
function saveAddRow(btn){
	var rowIndex=btn.parentNode.parentNode.rowIndex;
	var testTbl =  document.getElementById("table");
	var website = testTbl.rows[rowIndex].cells[0].childNodes[0].value;
	var usr = testTbl.rows[rowIndex].cells[1].childNodes[0].value;
	var password = testTbl.rows[rowIndex].cells[2].childNodes[0].value;
	sendRequest(website, usr, password, 'add');
	testTbl.rows[rowIndex].cells[0].innerHTML='<input type="text" value=' + website + '>';
	testTbl.rows[rowIndex].cells[1].innerHTML='<input type="text" value=' + usr + '>';
	testTbl.rows[rowIndex].cells[2].innerHTML='<input type="password" value=' + password + '>';
	testTbl.rows[rowIndex].cells[3].innerHTML='<input type="button" value="edit" onclick="editRow(this)">';
	testTbl.rows[rowIndex].cells[4].innerHTML='<input type="button" value="remove" onclick="removeRow(this)">';
	testTbl.rows[rowIndex].cells[5].innerHTML='<input type="button" value="login" onclick="login(this)">';
}
function addRow(){
	var testTbl =  document.getElementById("table");
	var newTr = testTbl.insertRow(testTbl.rows.length);
	var newTd0 = newTr.insertCell(0);
	var newTd1 = newTr.insertCell(1);
	var newTd2 = newTr.insertCell(2);
	var newTd3 = newTr.insertCell(3);
	var newTd4 = newTr.insertCell(4);
	var newTd5 = newTr.insertCell(5);
	newTd0.innerHTML = '<select>	  <option value ="volvo">Volvo</option>	  <option value="audi">Audi</option>	</select>';
	newTd1.innerHTML = '<input type="text" id="usr">';
	newTd2.innerHTML = '<input type="passwsord" id="password">';
	newTd3.innerHTML = '<input type="button" value="done" onclick="saveAddRow(this)">';
	newTd4.innerHTML = '<input type="button" value="cancel" onclick="cancelAddRow(this)">';
	newTd5.innerHTML = '<input type="button" value="login" onclick="login(this)">';
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
		<td>facebook</td>
		<td><input type="text" name="usr", value="keyalin" ></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow()"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
		<td><input type="button" value="login" onclick="login(this)"></td>
	</tr>
	<tr>
		<td>twitter</td>
		<td><input type="text" name="usr", value="keyalin"></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow(this)"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
		<td><input type="button" value="login" onclick="login(this)"></td>
	</tr>
</table>
<input type="button" value="add" onclick="addRow()">




</body>
</html>