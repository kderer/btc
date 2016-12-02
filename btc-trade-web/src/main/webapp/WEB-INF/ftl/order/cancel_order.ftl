<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign cancelOrderAction><@spring.url value="/order/cancelOrder.html"/></#assign>

<@form.form modelAttribute="cancelOrder" class="form-horizontal" action="${cancelOrderAction}" method="post">
	
	<@form.hidden path="id" class="form-control"/>
	<@form.hidden path="returnId" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.listorder.cancelorder.title"/></h4>
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
			<label for="platformCodeDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.cancelorder.orderType"/>
			</label>			
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${cancelOrder.orderType}</div>
		</div>
		
		<div class="form-group">
			<label for="platformNameDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.cancelorder.price"/>
			</label>			
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${cancelOrder.price}</div>
		</div>
		
		<div class="form-group">
			<label for="platformCurrencyDiv" class="col-sm-3 control-label">
				<@spring.message code="label.listorder.cancelorder.amount"/>
			</label>
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${cancelOrder.amount}</div>
		</div>
		
		<#if cancelOrder.status == 'P'>
			<div class="alert alert-warning" role="alert">
				<table>
					<tr>
						<td>
							<span class="glyphicon glyphicon-warning-sign" style="font-size: 40px"></span>
						</td>
						<td style="padding-left: 5px">
							<strong>&nbsp;<@spring.message code="message.listorder.cancelorder.confirm"/></strong>
						</td>
					</tr>
				</table>			
			</div>
		</#if>
				
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">
			<@spring.message code="label.listorder.cancelorder.button.close"/>
		</button>
		<#if cancelOrder.status == 'P'>
			<button type="button" class="btn btn-primary" id="cancelOrderSubmitButton">
				<@spring.message code="label.listorder.cancelorder.button.submit"/>
			</button>
		</#if>
    </div>

</@form.form>

<#if cancelOrder.status == 'P'>
	<script language="javascript" type="text/javascript">
		$(function() {
			$('#cancelOrderSubmitButton').modalSubmit();	
	   	});
	</script>
</#if>