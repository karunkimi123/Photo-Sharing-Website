<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
  <title>Twitter - Result</title>
  <link rel="stylesheet" href="/PhotoGallery/styles/style2.css" type="text/css" />
  <script src="/PhotoGallery/js/canvasjs.min.js"></script>
  
<script type="text/javascript">
window.onload = function () {
	var chart = new CanvasJS.Chart("chartContainer",
	{
		backgroundColor: "#0a0a0a",
		exportFileName: "Pie Chart",
		exportEnabled: false,
		legend:{
			verticalAlign: "bottom",
			horizontalAlign: "center"
		},
		data: [
		{       
			type: "pie",
			showInLegend: false,
			toolTipContent: "{legendText}: <strong>{y}</strong>",
			indexLabel: "{label} ({y})",
			dataPoints: [
				<c:forEach items="${result}" var="party">
				{  y: <c:out value='${party.value}'/>, legendText: "<c:out value='${party.key}'/>", label: "<c:out value='${party.key}'/>" },
				</c:forEach>
			]
	}
	]
	});
	chart.render();
}
</script>
        
</head>

<body>	
	<div id="result" class="result">
	<table align="center" style="margin-top:50px;" width="100%">
	<tr>
		<td colspan="2">
		<form action="/compute" method="post">
		<table align="center" style="width:400px;margin-top:50px;">
		<tr>
			<td rowspan="3">
				<table align="center" style="width:400px;margin-top:10px;">
				<tr>
					<th>Select Parties</th>
				</tr>
				<tr>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="Labour Party">
	   	    			<label>Labour Party</label>
	   	    		</td>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="National Future Party">
	   	    			<label>National Future Party</label>
	   	    		</td>
	   	    	</tr>
	   	    	<tr>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="Maori Party">
	   	    			<label>Maori Party</label>
	   	    		</td>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="Mana Party">
	   	    			<label>Mana Party</label>
	   	    		</td>
	   	    	</tr>
	   	    	<tr>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="ACT Party">
	   	    			<label>ACT Party</label>
	   	    		</td>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="Green Party">
	   	    			<label>Green Party</label>
	   	    		</td>
	   	    	</tr>
	   	    	<tr>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="New Zealand First Party">
	   	    			<label>New Zealand First Party</label>
	   	    		</td>
	   	    		<td>
	   	    			<input type="checkbox" name="party" value="United Future Party">
	   	    			<label>United Future Party</label>
	   	    		</td>
	   	    	</tr>
	        </table>
			</td>
		</tr>
		<tr>
			<td>
				<input type="date" name="start" value="<c:out value='${startTime}'/>">
			</td>
			<td>
				<input type="date" name="end" value="<c:out value='${endTime}'/>">
			</td>
			<td>
				<input type="submit" name="Submit" value="Submit">
			</td>
		</tr>
		<tr>
			<td colspan="3"><label>Tweets counts between <c:out value='${startTime}'/> and <c:out value='${endTime}'/> </label></td>
		</tr>
		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td>		
		<table align="center" style="width:400px;margin-top:10px;">
			<tr>
				<th>Party Name</th>
				<th>Tweets Count</th>
			</tr>
			<tr>
   	    		<td><c:out value='${overallElectionKey}'/></td>
   	    		<td align="center"><c:out value='${overallElectionCount}'/></td>
   	    	</tr>
   	    	<c:forEach items="${result}" var="party">
   	    	<tr>
   	    		<td><c:out value='${party.key}'/></td>
   	    		<td align="center"><c:out value='${party.value}'/></td>
   	    	</tr>
          	</c:forEach>
         </table>
         </td>
         <td>
         	<table align="center" style="width:800px;margin-top:10px;">
         	<tr>
      			<td><div id="chartContainer" style="height: 400px; width: 100%;"></div></td>
      		</tr>
      		</table> 	
         </td>
    </tr>
    </table>
    </div>
</body>

</html>