<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="isUserAuthorized" value="false"/>
<sec:authorize access="isAuthenticated()">
	<c:set var="isUserAuthorized" value="true"/>
</sec:authorize>

<script type="text/javascript">
	function logout() {
		document.forms["logoutForm"].submit();
	}
	
	function gotoHome() {
		window.location.href='<spring:url value="/index.html"/>';
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
  	
  	<c:choose>  	
	  	<c:when test="${!isUserAuthorized}">
		  	<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
			    <li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/exchangeRates.html"/>">
						<span class="glyphicon glyphicon-usd menuspan"></span>
						<spring:message code="label.menu.exchangerates"/>
					</a>
				</li>
			    <li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/login.html"/>">
						<span class="glyphicon glyphicon-log-in menuspan"></span>
						<spring:message code="label.button.login"/>
					</a>
				</li>
		  	</ul>
		</c:when>
		<c:otherwise>
			<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
			    <li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/platform.html"/>">
						<span class="glyphicon glyphicon-bitcoin menuspan"></span>
						<spring:message code="label.menu.platformlist"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/configuration.html"/>">
						<span class="glyphicon glyphicon-cog menuspan"></span>
						<spring:message code="label.menu.config"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/order.html"/>">
						<span class="glyphicon glyphicon-shopping-cart menuspan"></span>
						<spring:message code="label.menu.userorderList"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/btcAccount.html"/>">
						<span class="glyphicon glyphicon-briefcase menuspan"></span>
						<spring:message code="label.menu.btcaccount"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/report/dailyprofit.html"/>">
						<span class="glyphicon glyphicon glyphicon-file menuspan"></span>
						<spring:message code="label.menu.report"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="<spring:url value="/exchangeRates.html"/>">
						<span class="glyphicon glyphicon-usd menuspan"></span>
						<spring:message code="label.menu.exchangerates"/>
					</a>
				</li>
				<li role="presentation">
					<a role="menuitem" class="menuanchor" tabindex="-1" href="javascript:logout()">
						<span class="glyphicon glyphicon-log-out menuspan"></span>
						<spring:message code="label.button.logout"/>
					</a>
				</li>
			</ul>
				
			<form action="<spring:url value="/logout.html"/>" class="pull-right" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</c:otherwise>
	</c:choose>

</div>
													
						