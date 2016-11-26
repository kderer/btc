<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/datatables/dataTables.bootstrap.min.css"/>">

<div class="row buttonActionDiv">
	<c:url value="/configuration/add.html" var="url"></c:url>
	<a class="btn btn-default" href="${url}" role="button" data-toggle="modal" data-target="#editConfModal">
		<span class="glyphicon glyphicon-plus"></span>
		<spring:message code="label.config.add.button"/>
	</a>
</div>

<table id="configurationListTable" class="table table-hover">
	<thead>
		<tr>
			<th>#</th>
			<th><spring:message code="label.config.list.name" /></th>
			<th><spring:message code="label.config.list.value" /></th>
			<th><spring:message code="label.config.list.description" /></th>
			<th><spring:message code="label.config.list.edit" /></th>
		</tr>
	</thead>
</table>

<div class="modal fade" id="deleteConfModal">
	<div class="modal-dialog">
    	<div class="modal-content">
    	
    	</div>
  	</div>
</div>
	
<div class="modal fade" id="editConfModal">
	<div class="modal-dialog">
    	<div class="modal-content">
    	
    	</div>
  	</div>
</div>

<script src="<spring:url value="/assets/datatables/jquery.dataTables.min.js"/>"></script>
<script src="<spring:url value="/assets/datatables/dataTables.bootstrap.min.js"/>"></script>

<script>
	var configurationDatatable;
	
	$(function(){
		configurationDatatable = $('#configurationListTable').DataTable({
	        "ajax": '<spring:url value="/configuration/query.request"/>',
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
			    	"data": "name",
			  	}, {
			  		"targets": 2,
			    	"data": "value",
			  	}, {
			  		"targets": 3,
			    	"data": "description",
			  	}, {
			  		"targets": 4,
			    	"data"   : null,
			    	"render" : function ( data, type, row ) {
			    		var html = '<div class="btn-group" role="group" aria-label="...">' +
						    	'<a href="<spring:url value="/configuration/edit.request"/>?id=' + data.id + '" class="btn btn-default" ' + 
					    			'title="Update" data-toggle="modal" data-target="#editConfModal"/>' +
									'<span class="glyphicon glyphicon-pencil"></span>' +
								'</a>' +
			    				'<a href="<spring:url value="/configuration/delete.request"/>?id=' + data.id + '" class="btn btn-default" ' +
				    	        	'title="Delete" data-toggle="modal" data-target="#deleteConfModal"/>' +
									'<span class="glyphicon glyphicon-trash"></span>' +
								'</a>' +									
								
			    			'</div>';
			    		
			    		return html;
			    	}
			  	}],
	        "lengthMenu": [ 10, 25, 50, 75, 100 ],
	        "pageLength": 25
	    });
	    
	    $('#deleteConfModal').on('hide.bs.modal', function () {
   			$('#deleteConfModal').removeData();
		});
		
		$('#editConfModal').on('hide.bs.modal', function () {
   			$('#editConfModal').removeData();
		});
   	});	   
</script>