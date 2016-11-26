<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:importAttribute name="titleKey" />
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/css/style.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/css/bootstrap-datetimepicker.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/bootstrap/css/bootstrap.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/jquery-ui/jquery-ui.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/jquery-ui/jquery-ui.structure.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/jquery-ui/jquery-ui.theme.min.css"/>">
		<link rel="stylesheet" type="text/css" href="<spring:url value="/assets/css/bootstrap-switch.min.css"/>">
		
		<script type="text/javascript" src="<spring:url value="/assets/js/jquery-1.11.2.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/moment.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/jquery-ui/jquery-ui.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/bootstrap/js/transition.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/bootstrap/js/collapse.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/highcharts.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/bootstrap/js/bootstrap.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/bootstrap-datetimepicker.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/btc_compare.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/verify.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/bootbox.min.js"/>"></script>
		<script type="text/javascript" src="<spring:url value="/assets/js/bootstrap-switch.min.js"/>"></script>
	
		<title><spring:message code="${titleKey}"/></title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="page-header">
					<h1>
						BTC COMPARE CHARTS <small>Btcturk Btcchina Btce</small>
						<jsp:include page="menu.jsp"></jsp:include>
					</h1>
				</div>
	
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title"><spring:message code="${titleKey}"/></h3>
					</div>
					<div class="panel-body">
						<c:if test="${request_success_messages != null}">
							<div class="alert alert-success" role="alert">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<c:forEach items="${request_success_messages}" var="messageKey">
									<spring:message code="${messageKey}" />
								</c:forEach>
							</div>
						</c:if>
	
						<c:if test="${request_error_messages != null}">
							<div class="alert alert-danger" role="alert">
								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<c:forEach items="${request_error_messages}" var="messageKey">
									<spring:message code="${messageKey}" />
									<br>
								</c:forEach>
							</div>
						</c:if>
						<tiles:insertAttribute name="body" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>