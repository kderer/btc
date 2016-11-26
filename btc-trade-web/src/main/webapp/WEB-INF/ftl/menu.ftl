<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]>
<#assign loginUrl><@spring.url value="/login.html"/></#assign>
<#assign logoutUrl><@spring.url value="/logout.html"/></#assign>
<#assign indexUrl><@spring.url value="/index.html"/></#assign>
<#assign platformMaintanenceUrl><@spring.url value="/platform.html"/></#assign>
<#assign orderListUrl><@spring.url value="/order.html"/></#assign>
<#assign exchangeRatesUrl><@spring.url value="/exchangeRates.html"/></#assign>
<#assign configurationUrl><@spring.url value="/configuration.html"/></#assign>
<#assign reportUrl><@spring.url value="/report/dailyprofit.html"/></#assign>
<#assign btcAccountUrl><@spring.url value="/btcAccount.html"/></#assign>

<#assign isUserAuthorized="false"/>
<@sec.authorize access="isAuthenticated()">
	<#assign isUserAuthorized="true"/>
</@sec.authorize>

<script language="javascript" type="text/javascript">
	function logout() {
		document.forms["logoutForm"].submit();
	}
	
	function gotoHome() {
		window.location.href='${indexUrl}';
	}
</script>

<div class="dropdown pull-right">
	<button class="btn btn-default menubutton" type="button" id="homePageButton">
		<span class="glyphicon glyphicon-home menuspan" style="font-size: 20px;"
			onclick="gotoHome()"></span>
  	</button>
	<button class="btn btn-default dropdown-toggle menubutton" type="button" id="dropdownMenu1"
  		data-toggle="dropdown" aria-expanded="true">
		<span class="glyphicon glyphicon-menu-hamburger" style="font-size: 20px;"></span>
  	</button>
  	<#if isUserAuthorized == "false">
	  	<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
		    <li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${exchangeRatesUrl}">
					<span class="glyphicon glyphicon-usd menuspan"></span>
					<@spring.message code="label.menu.exchangerates"/>
				</a>
			</li>
		    <li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${loginUrl}">
					<span class="glyphicon glyphicon-log-in menuspan"></span>
					<@spring.message code="label.button.login"/>
				</a>
			</li>
	  	</ul>
	<#else>
		<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
		    <li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${platformMaintanenceUrl}">
					<span class="glyphicon glyphicon-bitcoin menuspan"></span>
					<@spring.message code="label.menu.platformlist"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${configurationUrl}">
					<span class="glyphicon glyphicon-cog menuspan"></span>
					<@spring.message code="label.menu.config"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${orderListUrl}">
					<span class="glyphicon glyphicon-shopping-cart menuspan"></span>
					<@spring.message code="label.menu.userorderList"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${btcAccountUrl}">
					<span class="glyphicon glyphicon-briefcase menuspan"></span>
					<@spring.message code="label.menu.btcaccount"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${reportUrl}">
					<span class="glyphicon glyphicon glyphicon-file menuspan"></span>
					<@spring.message code="label.menu.report"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="${exchangeRatesUrl}">
					<span class="glyphicon glyphicon-usd menuspan"></span>
					<@spring.message code="label.menu.exchangerates"/>
				</a>
			</li>
			<li role="presentation">
				<a role="menuitem" class="menuanchor" tabindex="-1" href="javascript:logout()">
					<span class="glyphicon glyphicon-log-out menuspan"></span>
					<@spring.message code="label.button.logout"/>
				</a>
			</li>
		</ul>
			
		<form action="${logoutUrl}" class="pull-right" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>	
		
	</#if>

</div>
													
						