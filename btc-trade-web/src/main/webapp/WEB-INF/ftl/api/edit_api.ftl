<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign editFormAction><@spring.url value="/platformApi/edit.html"/></#assign>

<script language="javascript" type="text/javascript">
	$(function() {
		$('#editApiSubmitButton').modalSubmit();	
   	});
</script>

<@form.form modelAttribute="editPlatformApi" class="form-horizontal" action="${editFormAction}" method="post">
	
	<@form.hidden path="id"/>

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title"><@spring.message code="label.editapi.title"/></h4>
	</div>
	<div class="modal-body">
		<#if request_success_messages??>
			<script language="javascript" type="text/javascript">
	   			$(function() {
	   				$('#editApiModal').on('hide.bs.modal', function () {
			   			location.reload();
					});
					
					$('#addApiModal').on('hide.bs.modal', function () {
			   			location.reload();
					});
			   	});
			</script>
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
			<label for="apiTypeDiv" class="col-sm-3 control-label"><@spring.message code="label.editapi.type"/></label>			
		
			<div id="apiTypeDiv" class="col-sm-9">
				<div class="col-sm-12">
					<@form.select path="type" class="form-control">
						<@form.option value=""><@spring.message code="label.select.default.select"/></@form.option>
						<#list apiTypes as apiType>
							<@form.option value="${apiType}">${apiType}</@form.option>
						</#list>
					</@form.select>
				</div>
				<div class="col-sm-12 has-error">
					<@form.errors path="type" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="apiUrlDiv" class="col-sm-3 control-label"><@spring.message code="label.editapi.url"/></label>			
			<div id="apiUrlDiv" class="col-sm-9">
				<div class="col-sm-12">
					<@form.input path="url" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<@form.errors path="url" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>		
					
		<div class="form-group">
			<label for="apiUrlDiv" class="col-sm-3 control-label"><@spring.message code="label.editapi.returnType"/></label>
			<div id="apiUrlDiv" class="col-sm-9">
				<div class="col-sm-12">
					<@form.input path="returnType" class="form-control"/>
				</div>
				<div class="col-sm-12 has-error">
					<@form.errors path="returnType" element="label" cssClass="control-label"/>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<@form.hidden path="platformId"/>

    <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal"><@spring.message code="label.editapi.button.close"/></button>
		<button type="button" class="btn btn-primary" id="editApiSubmitButton"><@spring.message code="label.editapi.button.submit"/></button>
    </div>

</@form.form>


