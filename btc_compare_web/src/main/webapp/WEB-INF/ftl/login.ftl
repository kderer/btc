<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign title><@spring.message code="label.login.title"/></#assign>
	
<#import "layout.ftl" as layout />

<@layout.masterTemplate title="${title}">
	<form class="form-horizontal" id="loginForm" action="login.html" method="post">		
					
		<div class="form-group">
			<label for="usernameDiv" class="col-sm-2 control-label"><@spring.message code="label.login.username"/></label>			
			
			<div id="usernameDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="username" class="form-control"/>
				</div>
				<div class="col-sm-4 has-error">
					<@form.errors path="name" cssClass="error" />
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="passwordDiv" class="col-sm-2 control-label"><@spring.message code="label.login.password"/></label>			
			
			<div id="passwordDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input type="password" name="password" class="form-control"/>
				</div>
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-sm-4">
		    		<input type="submit" class="btn btn-default" />
		    	</div>
		    </div>
		</div>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	</form>

</@layout.masterTemplate>


