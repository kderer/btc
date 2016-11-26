<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form modelAttribute="editConfig" class="form-horizontal" 
	action="${pageContext.request.contextPath}/configuration/edit.html" method="post">
	
	<form:hidden path="id" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">
			<c:if test="${editConfig.id == null}">
				<spring:message code="label.config.add.title"/>				
			</c:if>			
			<c:if test="${editConfig.id != null}">
				<spring:message code="label.config.edit.title"/>				
			</c:if>			
		</h4>
	</div>
	
	<div class="modal-body">
		<c:if test="${request_success_messages != null && request_success_messages.size() > 0}" >
			<script type="text/javascript">
	   			$(function() {
	   				$('#editConfModal').on('hide.bs.modal', function () {
	   					configurationDatatable.ajax.reload();
					});
			   	});
			</script>
    		<div class="alert alert-success" role="alert">
    			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			 	<span aria-hidden="true">&times;</span>
    			 </button>
    			<c:forEach items="${request_success_messages}" var="messageKey">
					<spring:message code="${messageKey}"/><br>
				</c:forEach>			    		
    		</div>
    	</c:if>
    	
    	<c:if test="${request_error_messages != null && request_error_messages.size() > 0}">
    		<div class="alert alert-danger" role="alert">
    			 <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			 	<span aria-hidden="true">&times;</span>
    			 </button>
    			<c:forEach items="${request_error_messages}" var="messageKey">
					<spring:message code="${messageKey}"/><br>
				</c:forEach>
    		</div>
    	</c:if>
		
		<div class="form-group">
			<label for="nameDiv" class="col-sm-3 control-label">
				<spring:message code="label.config.edit.name"/>
			</label>			
			<div id="nameDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">
					<form:input path="name" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<form:errors path="name" element="label" cssClass="control-label"/>
				</div>
			</div>				
		</div>
		
		<div class="form-group">
			<label for="valueDiv" class="col-sm-3 control-label">
				<spring:message code="label.config.edit.value"/>
			</label>			
			<div id="valueDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">
					<form:input path="value" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<form:errors path="value" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="descriptionDiv" class="col-sm-3 control-label">
				<spring:message code="label.config.edit.description"/>
			</label>			
			<div id="descriptionDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">
					<form:textarea path="description" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<form:errors path="description" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>				
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<spring:message code="label.config.edit.button.close"/>
		</button>
		<button type="button" class="btn btn-primary" id="saveConfigButton">
			<spring:message code="label.config.edit.button.submit"/>
		</button>
    </div>

</form:form>

<script>
	$(function() {
		$('#saveConfigButton').modalSubmit();	
   	});
</script>

