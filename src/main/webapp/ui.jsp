<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="test.Test"%>
<%@ page import="test.SpreadSheet"%>

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

function sendRequest(){
	alert("bcd");
	var abc = new XMLHttpRequest();
	//xmlhttp.open("GET","passchhip.html",true);
	alert("abc");
	if (abc!=null)
	  {
	  alert("send");
	  var params = "user=ipsum&password=binny";
	  abc.open("post","http://127.0.0.1:8888/passchip-service/test",true);
	  abc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		abc.setRequestHeader("Content-length", params.length);
		abc.setRequestHeader("Connection", "close");
	  abc.send(params);
	  abc.onreadystatechange = function() {//Call a function when the state changes.
	    if(abc.readyState == 4 && abc.status == 200) {
	        alert("200");
	    }
		}
	  alert(abc.responseText);
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
<input type="button" value="testServlet" onclick="sendRequest()">




</body>
=======
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="passchip.shared.Control"%>
<%@ page import="passchip.shared.SpreadSheet"%>
<%@ page import="test.Test"%>




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
	var newTd5 = newTr.insertCell(5);
	newTd0.innerHTML = '<input type="text">';
	newTd1.innerHTML = '<input type="text">';
	newTd2.innerHTML = '<input type="text" id="usr">';
	newTd3.innerHTML = '<input type="passwsord" id="password">';
	newTd4.innerHTML = '<input type="button" value="save" onclick="saveRow(this)">';
	newTd5.innerHTML = '<input type="button" value="remove" onclick="removeRow(this)">';
}
</script>
<script>
function removeRow(btn){
<%Control c = new Control();



%>

alert(<%c.getX()%>);

	if(confirm("Are your sure")){
		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		//send info
		testTbl.deleteRow(rowIndex);
	}
}

function saveRow(btn){
	if(confirm("Are your sure to save")){
	//information
	// an instance here 
		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		testTbl.rows[rowIndex].cells[4].innerHTML='<input type="button" value="edit" onclick="editRow(this)">';
	}
}

function editRow(btn){
	if(confirm("Are your sure to update")){
		var rowIndex=btn.parentNode.parentNode.rowIndex;
		var testTbl =  document.getElementById("table");
		alert(testTbl.rows[rowIndex].cells[2].childNodes[0].value);
		alert(testTbl.rows[rowIndex].cells[1].innerHTML);
		alert(testTbl.rows[rowIndex].cells[1].innerText);
		testTbl.rows[rowIndex].cells[4].innerHTML='<input type="button" value="save" onclick="saveRow(this)">';
	}
}
</script>

<body>

<table id="table" border="10">
	<tr>
	
		<td>website</td>
		<td>url</td>
		<td>usr</td>
		<td>password</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td><a href="http://www.facebook.com">facebook</a></td>
		<td><a href="http://www.facebook.com">http://www.facebook.com</a></td>
		<td><input type="text" name="usr", value="keyalin" ></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow()"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
	</tr>
	<tr>
		<td><a href="http://www.twitter.com">twitter</a></td>
		<td><a href="http://www.twitter.com">http://www.twitter.com</a></td>
		<td><input type="text" name="usr", value="keyalin"></td>
		<td><input type="password" name="password", value="112"></td>
		<td><input type="button" value="edit" onclick="editRow(this)"></td>
		<td><input type="button" value="remove" onclick="removeRow(this)"></td>
	</tr>
</table>
<input type="button" value="add" onclick="addRow()">


</body>
>>>>>>> ce0eb4e6f24d7680c0343b2be3fb0401bde8fb7d
</html>