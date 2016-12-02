<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign retryOrderAction><@spring.url value="/order/retryOrder.html"/></#assign>

<@form.form modelAttribute="failedOrder" class="form-horizontal" action="${retryOrderAction}" method="post">
	
	<@form.hidden path="id" class="form-control"/>
	
	<@form.hidden path="userOrderId" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.listorder.failedorder.title"/></h4>
	</div>
	
	<div class="modal-body">
		<#if request_success_messages??>
    		<div class="alert alert-success" role="alert">
    			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			 	<span aria-hidden="true">&times;</span>
    			 </button>
    			<#list request_success_messages as messageKey>
					<@spring.message code="${messageKey}"/>
				</#list>			    		
    		</div>
    	</#if>
    	
    	<#if request_error_messages??>
    		<div class="alert alert-danger" role="alert">
    			 <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			 	<span aria-hidden="true">&times;</span>
    			 </button>
    			<#list request_error_messages as messageKey>
					<@spring.message code="${messageKey}"/><br>
				</#list>			    		
    		</div>
    	</#if>
		
		<#if failedOrder.id??>
			<div class="form-group">
				<label for="orderTypeDiv" class="col-sm-3 control-label">
					<@spring.message code="label.listorder.failedorder.orderType"/>
				</label>			
				<div id="orderTypeDiv" class="col-sm-9 valueLabel">${failedOrder.orderType}</div>
			</div>
			
			<div class="form-group">
				<label for="priceDiv" class="col-sm-3 control-label">
					<@spring.message code="label.listorder.failedorder.price"/>
				</label>			
				<div id="priceDiv" class="col-sm-9 valueLabel">${failedOrder.price}</div>
			</div>
			
			<div class="form-group">
				<label for="amountDiv" class="col-sm-3 control-label">
					<@spring.message code="label.listorder.failedorder.amount"/>
				</label>
				<div id="amountDiv" class="col-sm-9 valueLabel">${failedOrder.amount}</div>
			</div>
			
			<div class="form-group">
				<label for="errorCodeDiv" class="col-sm-3 control-label">
					<@spring.message code="label.listorder.failedorder.errorCode"/>
				</label>
				<div id="errorCodeDiv" class="col-sm-9 valueLabel">${failedOrder.errorCode}</div>
			</div>
			
			<div class="form-group">
				<label for="messageDiv" class="col-sm-3 control-label">
					<@spring.message code="label.listorder.failedorder.message"/>
				</label>
				<div id="messageDiv" class="col-sm-9 valueLabel">${failedOrder.message}</div>
			</div>
			
			<#if failedOrder.retryResult??>
				<div class="form-group">
					<label for="retryResultDiv" class="col-sm-3 control-label">
						<@spring.message code="label.listorder.failedorder.retryresult"/>
					</label>
					<div id="retryStatusDiv" class="col-sm-9 valueLabel">${failedOrder.retryResult}</div>
				</div>
			</#if>
		<#else>
			<@spring.message code="message.listorder.failedorder.notfound"/>
		</#if>
						
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<@spring.message code="label.listorder.failedorder.button.close"/>
		</button>
		<#if !failedOrder.retryResult??>
			<button type="button" class="btn btn-primary" id="failedOrderRetryButton">
				<@spring.message code="label.listorder.failedorder.button.retry"/>
			</button>
		</#if>
    </div>

</@form.form>

<#if !failedOrder.retryResult??>
	<script language="javascript" type="text/javascript">
		$(function() {
			$('#failedOrderRetryButton').modalSubmit();	
	   	});
	</script>
</#if>