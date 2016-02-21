<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign title><@spring.message code="label.newplatform.title"/></#assign>
<#assign addFormAction><@spring.url value="/platform/add.html"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">
	
	<div class="row buttonActionDiv">
		<@c.url value="/platform.html" var="url"></@c.url>
		<a class="btn btn-default" href="${url}" role="button">
			<span class="glyphicon glyphicon-list"></span>
			<@spring.message code="label.listplatform.backtolist"/>
		</a>
	</div>

	<@form.form modelAttribute="newPlatform" class="form-horizontal" action="${addFormAction}" method="post">
	
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
		    		<input type="submit" class="btn btn-default" value="<@spring.message code="label.newplatform.submit"/>"/>
		    	</div>
		    </div>
		</div>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	</@form.form>

</@layout.masterTemplate>


