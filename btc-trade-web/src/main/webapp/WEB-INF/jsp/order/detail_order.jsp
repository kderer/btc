<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form modelAttribute="order" class="form-horizontal"
	action="${pageContext.request.contextPath}/order/detail.html">
	
	<input type="hidden" name="uoId" value="${order.id}" />

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">
			<spring:message code="label.listorder.detail.title" />
		</h4>
	</div>

	<div class="modal-body">
		<div class="form-group">			
			<label for="basePriceDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.baseprice" />
			</label>
			<div id="basePriceDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.basePrice}"></c:out>
			</div>
			
			<label for="priceDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.price" />
			</label>
			<div id="priceDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.price}"></c:out>
			</div>
			
			<label for="amountDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.amount" />
			</label>
			<div id="amountDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.amount}"></c:out>
			</div>
		</div>
		
		<div class="form-group">
			<label for="typeDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.type" />
			</label>
			<div id="typeDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.orderType}"></c:out>
			</div>
					
			<label for="autoTradeDiv" class="col-sm-2 control-label"> <spring:message
				code="label.listorder.detail.autotrade" />
			</label>
			<div id="autoTradeDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.autoTrade}"></c:out>
			</div>
			
			<label for="autoUpdateDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.autoupdate" />
			</label>
			<div id="autoUpdateDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.autoUpdate}"></c:out>
			</div>			
		</div>
		
		<div class="form-group">
			<label for="statusDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.status" />
			</label>
			<div id="completedDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.status}"></c:out>
			</div>
				
			<label for="createDateDiv" class="col-sm-2 control-label"> <spring:message
					code="label.listorder.detail.createdate" />
			</label>
			<div id="createDateDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.createDateStr}"></c:out>
			</div>
			
			<label for="updateDateDiv" class="col-sm-2 control-label"> <spring:message
				code="label.listorder.detail.updatedate" />
			</label>
			<div id="updateDateDiv" class="col-sm-2 valueLabel">
				<c:out value="${order.updateDateStr}"></c:out>
			</div>
		</div>
				
		<div class="row">
			<div id="graphDiv" style="padding: 10px">
			
			</div>
		</div>
	</div>

	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="detailRefreshButton">
			<spring:message code="label.listorder.detail.button.refresh" />
		</button>
	</div>
</form:form>

<script>
	$(function() {
		$('#detailRefreshButton').modalSubmit({'method':'GET'});
		
		var graphData = <c:out escapeXml="false" value="${graphData}"></c:out>,
			series = [], 
			gmoaData = {},
			gmobData = {},
			orderData = {},
			basePriceData = {},
			indexArray = [];
		
		gmoaData.name = 'GM of Asks'
		gmoaData.data = graphData.GMOA;
		series.push(gmoaData);
		
		gmobData.name = 'GM of Bids'
		gmobData.data = graphData.GMOB;
		series.push(gmobData);
		
		orderData.name = 'Order'
		orderData.data = graphData.Order;
		series.push(orderData);
		
		basePriceData.name = 'Base Price'
		basePriceData.data = graphData.BasePrice;
		series.push(basePriceData);
		
		var counter = 1;
		for (var index in gmoaData.data) {
			indexArray.push(counter);
			counter += 1;
		}
		
		$('#graphDiv').highcharts({
			title : {
				text : 'Latest Geometric Mean of Ask And Bids',
				x : -20
			},
			xAxis : {
				categories : indexArray,
				labels : {
					rotation : 0,
					x : 1
				},
				gridLineWidth : 1
			},
			yAxis : {
				title : {
					text : 'RMB'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				shared : true,
				valueDecimals : 3
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'top',
				borderWidth : 0,
				floating: true
			},
			chart : {
				marginRight : 50
			},
			series : series
		});
	});
</script>

