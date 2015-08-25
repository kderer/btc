<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../layout.ftl" as layout />
<#assign title><@spring.message code="label.listorder.title"/></#assign>
<@layout.masterTemplate title="${title}">

	<script language="javascript" type="text/javascript" src="<@spring.url value="/assets/datatables/datatables.min.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/datatables/datatables.min.css"/>">
	
	<script language="javascript" type="text/javascript">
		$(function(){
			var userOrderDatatable = $('#userOrderListTable').DataTable({
		        "ajax": '<@spring.url value="/order/query.request"/>',
        		"serverSide": true,
        		ordering: false,
        		searching: false,        		
        		"columnDefs": [ {
				    "targets": 0,
				    "data": null,
				    "render": function ( data, type, full, meta ) {
					      return meta.settings._iDisplayStart + meta.row + 1;
					    }
				  	}, {
				  		"targets": 1,
				    	"data": "platformId",
				  	}, {
				  		"targets": 2,
				    	"data": "orderType",
				  	}, {
				  		"targets": 3,
				    	"data": "price",
				  	}, {
				  		"targets": 4,
				    	"data": "amount",
				  	}, {
				  		"targets": 5,
				    	"data": "completedAmount",
				  	}, {
				  		"targets": 6,
				    	"data": "profit",
				  	}, {
				  		"targets": 7,
				    	"data": "status",
				  	}, {
				  		"targets": 8,
				    	"data": "createDateStr",
				  	}],
		        "lengthMenu": [ 10, 25, 50, 75, 100 ],
		        "pageLength": 25
		    });
		    
		    $('#filterOrdersSubmitBtn').on('click', function() {
		    	var queryUrl = "order/query.request?";
				queryUrl += $('#filterOrderForm').serialize();
				
				userOrderDatatable.ajax.url(queryUrl).load();	
		    });
	   	});	   
	</script>
	
	<div class="panel panel-default">
		<div class="panel-heading">
	    	<h3 style="cursor: pointer" class="panel-title" data-toggle="collapse" data-target="#filterOrderDiv">
	    		<@spring.message code="label.filterorder.title"/>
	    	</h3>				    	
	  	</div>
	  	<#include "filter_order.ftl">
	</div>
	
	<table id="userOrderListTable" class="table table-hover">
		<thead>
			<tr>
				<th>#</th>
				<th><@spring.message code="label.listorder.platform"/></th>
				<th><@spring.message code="label.listorder.ordertype"/></th>
				<th><@spring.message code="label.listorder.price"/></th>
				<th><@spring.message code="label.listorder.orderamount"/></th>
				<th><@spring.message code="label.listorder.completedamount"/></th>				
				<th><@spring.message code="label.listorder.profit"/></th>
				<th><@spring.message code="label.listorder.status"/></th>
				<th><@spring.message code="label.listorder.createdate"/></th>
			</tr>
		</thead>
	</table>

</@layout.masterTemplate>