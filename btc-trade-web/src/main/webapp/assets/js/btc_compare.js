function drawChartsOnIndexPage() {

	var queryUrl = "queryRecordGroup.request?";
	queryUrl += $('#queryOptionsForm').serialize();

	$.getJSON(queryUrl, function(data) {

		var askSeries = [];
		var bidSeries = [];

		for ( var index in data.platformCodes) {
			var dataSet = {};
			dataSet.name = data.platformCodes[index];
			dataSet.data = data.platformAskpricelistMap[dataSet.name];

			askSeries.push(dataSet);

			var dataSetBid = {};
			dataSetBid.name = data.platformCodes[index];
			dataSetBid.data = data.platformBidpricelistMap[dataSet.name];

			bidSeries.push(dataSetBid);
		}

		$('#askChartContainer').highcharts({
			title : {
				text : 'Ask Rates ' + data.startTime + ' - ' + data.endTime,
				x : -20
			},
			xAxis : {
				categories : data.times,
				labels : {
					rotation : 285,
					x : 5
				},
				gridLineWidth : 1
			},
			yAxis : {
				title : {
					text : data.currency
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
			series : askSeries
		});

		$('#bidChartContainer').highcharts({
			title : {
				text : 'Bid Rates ' + data.startTime + ' - ' + data.endTime,
				x : -20
			},
			xAxis : {
				categories : data.times,
				labels : {
					rotation : 285,
					x : 5
				},
				gridLineWidth : 1
			},
			yAxis : {
				title : {
					text : data.currency
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
			series : bidSeries
		});

	});
}

(function ( $ ) {	 
    $.fn.modalSubmit = function(options) {
    	
    	var form = this.parents("form");
    	var modalContentDiv = this.parents(".modal-content");
    	
    	this.on('click', function() {    		
    		if (form && modalContentDiv) {
    			var method = 'POST';
    			if (options && options.method) {
    				method = options.method;
    			}    			
    			
    			var settings = $.extend({
    	            requestType: method
    	        }, options ); 
    			
    			$.ajax({
    		        type     : settings.requestType,
    		        cache    : false,
    		        url      : form.attr('action'),
    		        data     : form.serialize(),
    		        success  : function(data) {
    		        	modalContentDiv.empty().append(data);
    		        }
    			});
    		}
    	});        
    };   
 
}( jQuery ));