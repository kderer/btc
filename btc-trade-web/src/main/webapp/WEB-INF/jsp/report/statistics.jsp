<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title"><spring:message code="label.index.charts"/></h3>
	</div>
		
	<div class="panel-body">	
		<div class="row buttonActionDiv">
			<button type="button" class="btn btn-default" id="statisticsRefreshButtonTop">
				<span class="glyphicon glyphicon-refresh"></span>
				<spring:message code="label.statistics.chart.refresh"/>
			</button>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title"><spring:message code="label.statistics.chart.statistics"/></h3>
					</div>
					<div class="panel-body">
						<div id="statisticsChartContainer"></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title"><spring:message code="label.statistics.chart.buyorder"/></h3>
					</div>
					<div class="panel-body">
						<div id="bosChartContainer"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row buttonActionDiv">
			<button type="button" class="btn btn-default" id="statisticsRefreshButtonBottom">
				<span class="glyphicon glyphicon-refresh"></span>
				<spring:message code="label.statistics.chart.refresh"/>
			</button>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {		
		var drawStatisticsCharts = function() {
			var queryUrl = '<c:url value="/report/statisticsData.request" />';
	
			$.getJSON(queryUrl, function(data) {	
				var statisticSeries = [],
					bosSeries = [],
					checkDeltaData = {},
					highestLastGmobDiffData = {},
					highestGmobPriceDiffData = {},
					lowestAskData = {},
					gmoaData = {},
					highestBidData = {},
					gmobData = {},
					highestGmobData = {},
					lastHighestGmobData = {};
				
				checkDeltaData.name = 'Check Delta';
				highestLastGmobDiffData.name = 'HGMOB LaGMOB Diff';
				highestGmobPriceDiffData.name = 'HGMOB Price Diff';
				lowestAskData.name = 'Lowest Ask';
				gmoaData.name = 'GMOA';
				gmoaData.visible = false;
				highestBidData.name = 'Highest Bid';
				gmobData.name = 'GMOB';
				highestGmobData.name = 'Highest GMOB';
				highestGmobData.visible = false;
				lastHighestGmobData.name = 'Last GMOB';
				checkDeltaData.data = [];
				highestLastGmobDiffData.data = [];
				highestGmobPriceDiffData.data = [];
				lowestAskData.data = [];
				gmoaData.data = [];
				highestBidData.data = [];
				gmobData.data = [];
				highestGmobData.data = [];
				lastHighestGmobData.data = [];
				createTimes = [];
	
				for (var index in data) {
					checkDeltaData.data.push(data[index].checkDelta);
					highestLastGmobDiffData.data.push(data[index].highestLastGmobDiff);
					highestGmobPriceDiffData.data.push(data[index].highestGmobPriceDiff);
					lowestAskData.data.push(data[index].lowestAsk);
					gmoaData.data.push(data[index].gmoa);
					highestBidData.data.push(data[index].highestBid);
					gmobData.data.push(data[index].gmob);
					highestGmobData.data.push(data[index].highestGmob);
					lastHighestGmobData.data.push(data[index].lastHighestGmob);
					createTimes.push(data[index].formattedTime);
				}
				
				statisticSeries.push(lowestAskData);
				statisticSeries.push(gmoaData);
				statisticSeries.push(highestBidData);
				statisticSeries.push(gmobData);
				statisticSeries.push(highestGmobData);
				statisticSeries.push(lastHighestGmobData);
				
				bosSeries.push(checkDeltaData);
				bosSeries.push(highestLastGmobDiffData);
				bosSeries.push(highestGmobPriceDiffData);				
	
				$('#statisticsChartContainer').highcharts({
					title : {
						text : 'Statistics of The Last Five Minutes',
						x : -20
					},
					xAxis : {
						categories : createTimes,
						labels : {
							rotation : 285,
							x : 5
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
						verticalAlign : 'middle',
						borderWidth : 0
					},
					series : statisticSeries
				});
	
				$('#bosChartContainer').highcharts({
					title : {
						text : 'Buy Order statistics of The Last Five Minutes',
						x : -20
					},
					xAxis : {
						categories : createTimes,
						labels : {
							rotation : 285,
							x : 5
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
						verticalAlign : 'middle',
						borderWidth : 0
					},
					series : bosSeries
				});				
			});
		};
		
		$('#statisticsRefreshButtonTop').on('click', drawStatisticsCharts);
		$('#statisticsRefreshButtonBottom').on('click', drawStatisticsCharts);
		
		drawStatisticsCharts();		
   	});  
</script>