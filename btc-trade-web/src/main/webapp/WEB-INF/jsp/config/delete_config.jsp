<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form modelAttribute="deleteConfig" class="form-horizontal" 
	action="${pageContext.request.contextPath}/configuration/delete.html" method="post">
	
	<form:hidden path="id" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><spring:message code="label.config.delete.title"/></h4>
	</div>
	
	<div class="modal-body">
		<c:if test="${request_success_messages != null && request_success_messages.size() > 0}" >
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
				<spring:message code="label.config.delete.name"/>
			</label>			
			<div id="nameDiv" class="col-sm-9 valueLabel">${deleteConfig.name}</div>
		</div>
		
		<div class="form-group">
			<label for="valueDiv" class="col-sm-3 control-label">
				<spring:message code="label.config.delete.value"/>
			</label>			
			<div id="valueDiv" class="col-sm-9 valueLabel">${deleteConfig.value}</div>
		</div>
		
		<div class="form-group">
			<label for="descriptionDiv" class="col-sm-3 control-label">
				<spring:message code="label.config.delete.description"/>
			</label>			
			<div id="descriptionDiv" class="col-sm-9 valueLabel">${deleteConfig.description}</div>
		</div>
		
		<div class="alert alert-warning" role="alert">
			<table>
				<tr>
					<td>
						<span class="glyphicon glyphicon-warning-sign" style="font-size: 40px"></span>
					</td>
					<td style="padding-left: 5px">
						<strong>&nbsp;<spring:message code="message.configuration.delete.confirm"/></strong>
					</td>
				</tr>
			</table>			
		</div>				
	</div>	
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<spring:message code="label.config.delete.button.close"/>
		</button>
		<button type="submit" class="btn btn-primary" id="updateOrderSubmitButton">
			<spring:message code="label.config.delete.button.submit"/>
		</button>
    </div>

</form:form>

