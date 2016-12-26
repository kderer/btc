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
	</div>
</div>

<script type="text/javascript">
	$(function() {		
		var drawStatisticsCharts = function() {
			var queryUrl = '<c:url value="/report/statisticsData.request" />';
	
			$.getJSON(queryUrl, function(data) {	
				var statisticSeries = []
					lowestAskData = {},
					gmoaData = {},
					highestBidData = {},
					gmobData = {},
					dailyHighData = {};
				
				lowestAskData.name = 'Lowest Ask';
				gmoaData.name = 'GMOA';
				gmoaData.visible = false;
				highestBidData.name = 'Highest Bid';
				gmobData.name = 'GMOB';
				dailyHighData.name = 'Daily High';				
				lowestAskData.data = [];
				gmoaData.data = [];
				highestBidData.data = [];
				gmobData.data = [];
				dailyHighData.data = [];
				createTimes = [];
	
				for (var index in data) {
					lowestAskData.data.push(data[index].lowestAsk);
					gmoaData.data.push(data[index].gmoa);
					highestBidData.data.push(data[index].highestBid);
					gmobData.data.push(data[index].gmob);
					dailyHighData.data.push(data[index].highestGmob);
					createTimes.push(data[index].formattedTime);
				}
				
				statisticSeries.push(lowestAskData);
				statisticSeries.push(gmoaData);
				statisticSeries.push(highestBidData);
				statisticSeries.push(gmobData);
				statisticSeries.push(dailyHighData);			
	
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
					chart : {
						marginRight : 175,
						marginLeft : 100
					},
					series : statisticSeries
				});								
			});
		};
		
		$('#statisticsRefreshButtonTop').on('click', drawStatisticsCharts);
		
		drawStatisticsCharts();
   	});  
</script>