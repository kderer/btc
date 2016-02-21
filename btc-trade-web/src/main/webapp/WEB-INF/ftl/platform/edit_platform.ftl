<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign title><@spring.message code="label.editplatform.title"/></#assign>
<#assign editFormAction><@spring.url value="/platform/edit.html"/></#assign>
<#assign editTitle><@spring.message code="label.editplatform.apilist.edit.title"/></#assign>
<#assign deleteTitle><@spring.message code="label.editplatform.apilist.delete.title"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">

<script language="javascript" type="text/javascript">
	$(function() {
		$('#editApiModal').on('hide.bs.modal', function () {
   			$('#editApiModal').removeData();
		});
		
		$('#addApiModal').on('hide.bs.modal', function () {
   			$('#addApiModal').removeData();
		});
   	});
</script>

	<div class="row buttonActionDiv">
		<@c.url value="/platform.html" var="url"></@c.url>
		<a class="btn btn-default" href="${url}" role="button">
			<span class="glyphicon glyphicon-list"></span>
			<@spring.message code="label.listplatform.backtolist"/>
		</a>
	</div>

	<@form.form modelAttribute="editPlatform" class="form-horizontal" action="${editFormAction}" method="post">
		
		<@form.hidden path="id" class="form-control"/>
		
		<div class="form-group">
			<label for="platformCodeDiv" class="col-sm-2 control-label"><@spring.message code="label.newplatform.code"/></label>			
			
			<div id="platformCodeDiv" class="col-sm-10">
				<div class="col-sm-4">
					<@form.input path="code" class="form-control"/>
				</div>
				<div class="col-sm-4 has-error">
					<label class="control-label"><@form.errors path="code" /></label>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="platformNameDiv" class="col-sm-2 control-label"><@spring.message code="label.newplatform.name"/></label>			
			
			<div id="platformNameDiv" class="col-sm-10">
				<div class="col-sm-4">
					<@form.input path="name" class="form-control"/>
				</div>
				<div class="col-sm-4 has-error">
					<label class="control-label"><@form.errors path="name" /></label>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="platformCurrencyDiv" class="col-sm-2 control-label"><@spring.message code="label.newplatform.currency"/></label>
			
			<div id="platformCurrencyDiv" class="col-sm-10">
				<div class="col-sm-4">
					<@form.input path="currency" class="form-control"/>
				</div>
				<div class="col-sm-4 has-error">
					<label class="control-label"><@form.errors path="currency" /></label>
				</div>
			</div>
		</div>
		
		
		<div class="form-group">
			<label for="platformUrlDiv" class="col-sm-2 control-label"><@spring.message code="label.newplatform.url"/></label>			
			
			<div id="platformUrlDiv" class="col-sm-10">
				<div class="col-sm-4">
					<@form.input path="url" class="form-control"/>
				</div>
				<div class="col-sm-4 has-error">
					<label class="control-label"><@form.errors path="url"/></label>
				</div>
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-sm-4">
		    		<input type="submit" class="btn btn-default" value="<@spring.message code="label.editplatform.submit"/>"/>
		    	</div>
		    </div>
		</div>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	</@form.form>

	<div class="panel panel-default">
		<div class="panel-heading">
	    	<h3 class="panel-title"><@spring.message code="label.editplatform.apilist.title"/></h3>
	  	</div>
	  	<div class="panel-body">
	  		<div class="row buttonActionDiv">
				<@c.url value="/platformApi/add.html" var="url">
					<@c.param name="platformId" value="${editPlatform.id}"/>
				</@c.url>
				<a class="btn btn-default" href="${url}" role="button" data-toggle="modal" data-target="#addApiModal">
					<span class="glyphicon glyphicon-plus"></span>
					<@spring.message code="label.editplatform.apilist.add"/>
				</a>
			</div>
			<table class="table table-hover" id="editPlatformApiListTable">
			<thead>
				<tr>
					<th>#</th>
					<th><@spring.message code="label.editplatform.apilist.type"/></th>
					<th><@spring.message code="label.editplatform.apilist.url"/></th>
					<th><@spring.message code="label.editplatform.apilist.returnType"/></th>
					<th><@spring.message code="label.editplatform.apilist.edit"/></th>			
				</tr>
			</thead>
			<tbody>
			<#list editPlatform.apiList as api>
				<tr>
					<td>${api_index + 1}</td>
					<td>${api.type}</td>
					<td>${api.url}</td>
					<td>${api.returnType}</td>
					<td>
						<div class="btn-group" role="group" aria-label="...">
						 	<@c.url value="/platformApi/edit.html" var="url">
								<@c.param name="id" value="${api.id}"/>
							</@c.url>
							<a href="${url}" class="btn btn-default" title="${editTitle}" data-toggle="modal" data-target="#editApiModal">
								<span class="glyphicon glyphicon-pencil"></span>
							</a>
							
							<@c.url value="/platformApi/delete.html" var="url">
								<@c.param name="id" value="${api.id}"/>
							</@c.url>
							<a href="${url}" class="btn btn-default" title="${deleteTitle}" data-toggle="modal" data-target="#deleteApiModal"/>
								<span class="glyphicon glyphicon-trash"></span>
							</a>
						</div>						
					</td>
				</tr>
			</#list>
			</tbody>		 	
		</table>
	</div>
	
	<div class="modal fade" id="editApiModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
	      		
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="addApiModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
	      		
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="modal fade" id="deleteApiModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
	    	
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</@layout.masterTemplate>


