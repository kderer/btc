<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign deleteFormAction><@spring.url value="/platform/delete.html"/></#assign>

<@form.form modelAttribute="deletePlatform" class="form-horizontal" action="${deleteFormAction}" method="post">
	
	<@form.hidden path="id" class="form-control"/>
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.deleteplatform.title"/></h4>
	</div>
	
	<div class="modal-body">
		<div class="form-group">
			<label for="platformCodeDiv" class="col-sm-3 control-label"><@spring.message code="label.deleteplatform.code"/></label>			
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${deletePlatform.code}</div>
		</div>
		
		<div class="form-group">
			<label for="platformNameDiv" class="col-sm-3 control-label"><@spring.message code="label.deleteplatform.name"/></label>			
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${deletePlatform.name}</div>
		</div>
		
		<div class="form-group">
			<label for="platformCurrencyDiv" class="col-sm-3 control-label"><@spring.message code="label.deleteplatform.currency"/></label>
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${deletePlatform.currency}</div>
		</div>		
		
		<div class="form-group">
			<label for="platformUrlDiv" class="col-sm-3 control-label"><@spring.message code="label.deleteplatform.url"/></label>			
			<div id="platformCodeDiv" class="col-sm-9 valueLabel">${deletePlatform.url}</div>
		</div>
		
		<div class="alert alert-warning" role="alert">
			<span class="glyphicon glyphicon-warning-sign" style="font-size: 40px"></span>
			<strong>&nbsp;<@spring.message code="message.deleteplatform.confirm"/></strong>
		</div>
				
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal"><@spring.message code="label.deleteplatform.button.close"/></button>
		<button type="submit" class="btn btn-primary" id="deletePlatformSubmitButton"><@spring.message code="label.deleteplatform.button.submit"/></button>
    </div>

</@form.form>


