<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign updateOrderAction><@spring.url value="/order/updateOrder.html"/></#assign>

<script language="javascript" type="text/javascript">
	$(function() {
		$('#updateOrderSubmitButton').modalSubmit();	
   	});
</script>

<@form.form modelAttribute="updateOrder" class="form-horizontal" action="${updateOrderAction}" method="post">
	
	<@form.hidden path="id" class="form-control"/>
	<@form.hidden path="returnId" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.listorder.updateorder.title"/></h4>
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
		
		<div class="form-group">
			<label for="orderTypeDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.updateorder.orderType"/>
			</label>			
			<div id="orderTypeDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">${updateOrder.orderType}</div>
			</div>
			<@form.hidden path="orderType" class="form-control"/>
		</div>
		
		<div class="form-group">
			<label for="currentPriceDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.updateorder.price.current"/>
			</label>			
			<div id="currentPriceDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">${updateOrder.currentPrice}</div>
			</div>
			<@form.hidden path="currentPrice" class="form-control"/>
		</div>
		
		<div class="form-group">
			<label for="orderPriceDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.updateorder.price"/>
			</label>			
			<div id="orderPriceDiv" class="col-sm-9">
				<div class="col-sm-12">
					<@form.input path="price" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<@form.errors path="price" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="orderAmountDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.updateorder.amount"/>
			</label>
			<div id="orderAmountDiv" class="col-sm-9 valueLabel">
				<div class="col-sm-12">${updateOrder.amount}</div>
			</div>
			<@form.hidden path="amount" class="form-control"/>
		</div>
				
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<@spring.message code="label.listorder.updateorder.button.close"/>
		</button>
		<button type="button" class="btn btn-primary" id="updateOrderSubmitButton">
			<@spring.message code="label.listorder.updateorder.button.submit"/>
		</button>
    </div>

</@form.form>


