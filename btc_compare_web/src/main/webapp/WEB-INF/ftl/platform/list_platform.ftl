<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign title><@spring.message code="label.listplatform.title"/></#assign>
<#assign editTitle><@spring.message code="label.listplatform.edit.title"/></#assign>
<#assign deleteTitle><@spring.message code="label.listplatform.delete.title"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">

	<script language="javascript" type="text/javascript">
		$(function() {			
			$('#deletePlatformModal').on('hide.bs.modal', function () {
	   			$('#deletePlatformModal').removeData();
			});
	   	});
	</script>

	<div class="row buttonActionDiv">
		<@c.url value="/platform/add.html" var="url"></@c.url>
		<a class="btn btn-default" href="${url}" role="button">
			<span class="glyphicon glyphicon-plus"></span>
			<@spring.message code="label.listplatform.add"/>
		</a>
	</div>
	
	<table class="table table-hover" id="platformListTable">
		<thead>
			<tr>
				<th>#</th>
				<th><@spring.message code="label.listplatform.platformname"/></th>
				<th><@spring.message code="label.listplatform.platformcode"/></th>
				<th><@spring.message code="label.listplatform.platformhomepageurl"/></th>
				<th><@spring.message code="label.listplatform.edit"/></th>			
			</tr>
		</thead>
		<tbody>
		<#list platforms as platform>
			<tr>
				<td>${platform_index + 1}</td>
				<td>${platform.name}</td>
				<td>${platform.code}</td>
				<td>
					<a href="${platform.url}" target="_blank">${platform.url}</a>
				</td>
				<td>
					<div class="btn-group" role="group" aria-label="...">
					 	<@c.url value="/platform/edit.html" var="url">
							<@c.param name="id" value="${platform.id}"/>
						</@c.url>
						<a href="${url}" class="btn btn-default" title="${editTitle}"/>
							<span class="glyphicon glyphicon-pencil"></span>
						</a>
						
						<@c.url value="/platform/delete.html" var="url">
							<@c.param name="id" value="${platform.id}"/>
						</@c.url>
						<a href="${url}" class="btn btn-default" title="${deleteTitle}" data-toggle="modal" data-target="#deletePlatformModal"/>
							<span class="glyphicon glyphicon-trash"></span>
						</a>
					</div>						
				</td>	
			</tr>
		</#list>
		</tbody>		 	
	</table>
	
	<div class="modal fade" id="deletePlatformModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
	    	
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</@layout.masterTemplate>


