<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign deleteFormAction><@spring.url value="/platformApi/delete.html"/></#assign>

<@form.form modelAttribute="deletePlatformApi" class="form-horizontal" action="${deleteFormAction}" method="post">
	
	<@form.hidden path="id" class="form-control"/>

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.deleteapi.title"/></h4>
	</div>
	<div class="modal-body">
		<div class="form-group">
			<label for="apiTypeDiv" class="col-sm-3 control-label"><@spring.message code="label.deleteapi.type"/></label>
			<div id="apiTypeDiv" class="col-sm-9 valueLabel">${deletePlatformApi.type}</div>
		</div>
		
		<div class="form-group">
			<label for="apiUrlDiv" class="col-sm-3 control-label"><@spring.message code="label.editapi.url"/></label>			
			<div id="apiTypeDiv" class="col-sm-9 valueLabel">${deletePlatformApi.url}</div>
		</div>		
					
		<div class="form-group">
			<label for="apiUrlDiv" class="col-sm-3 control-label"><@spring.message code="label.editapi.returnType"/></label>
			<div id="apiTypeDiv" class="col-sm-9 valueLabel">${deletePlatformApi.returnType}</div>
		</div>
		
		<div class="alert alert-warning" role="alert">
			<table>
				<tr>
					<td>
						<span class="glyphicon glyphicon-warning-sign" style="font-size: 40px"></span>
					</td>
					<td style="padding-left: 5px">
						<strong>&nbsp;<@spring.message code="message.deleteapi.confirm"/></strong>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<@form.hidden path="platformId"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal"><@spring.message code="label.deleteapi.button.close"/></button>
		<button type="submit" class="btn btn-primary" id="deleteApiSubmitButton"><@spring.message code="label.deleteapi.button.submit"/></button>
    </div>

</@form.form>


