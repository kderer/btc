<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<spring:message code="label.btcaccount.balance.title" />
		</h3>
	</div>
	<div class="panel-body">
		<table id="btcAccountListTable" class="table table-hover">
			<thead>
				<tr>
					<th><spring:message
							code="label.btcaccount.balance.header.platform" /></th>
					<th><spring:message
							code="label.btcaccount.balance.header.currency" /></th>
					<th><spring:message code="label.btcaccount.balance.header.btc" /></th>
					<th><spring:message code="label.btcaccount.balance.header.sweep" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${btcAccountInfoList}" var="accountInfo">
					<tr>
						<td><c:out value="${accountInfo.platformName}" /></td>
						<td><c:out value="${accountInfo.currencyBalance}" /></td>
						<td><c:out value="${accountInfo.btcBalance}" /></td>
						<td>
							<a href='<spring:url value="/btcAccount/sweep.html"></spring:url>' class="btn btn-default">
								<span class="glyphicon glyphicon-fire"></span>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<spring:message code="label.btcaccount.buyorder.title" />
		</h3>
	</div>
	<div class="panel-body">
		<form:form method="post" modelAttribute="buyOrder"
			action="${pageContext.request.contextPath}/btcAccount/buyOrder.html"
			class="form-horizontal" id="buyOrderForm">
			
			<div class="form-group">
				<label for="buyOrderPlatformListDiv" class="col-sm-2 control-label">
					<spring:message code="label.filterorder.platform"/>
				</label>			
				
				<div id="buyOrderPlatformListDiv" class="col-sm-10">
					<div class="col-sm-4">
		      			<form:select path="platformId" class="form-control">
							<form:option value="">
								<spring:message code="label.select.default.select"/>
							</form:option>
							<c:forEach items="${platformList}" var="platform">
								<form:option value="${platform.id}">
									<c:out value="${platform.name}"/>
								</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="amountDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.buyorder.btc.amount" />
				</label>
				<div id="amountDiv" class="col-sm-10">
					<div class="col-sm-4">
						<input name="amount" class="form-control">
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="priceDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.buyorder.btc.price" />
				</label>
				<div id="amountDiv" class="col-sm-10">
					<div class="col-sm-4">
						<input name="price" class="form-control">
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="isAutoTradeDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.buyorder.isautotrade" />
				</label>
				<div id="isAutoTradeDiv" class="col-sm-10">
					<div class="col-sm-4">
						<div class="checkbox">
							<label> 
								<input type="checkbox" name="isAutoTrade">
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="col-sm-4">
						<button type="submit" class="btn btn-default">
							<spring:message code="label.btcaccount.buyorder.submit"/>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">
			<spring:message code="label.btcaccount.sellorder.title" />
		</h3>
	</div>
	<div class="panel-body">
		<form:form method="post" modelAttribute="sellOrder"
			action="${pageContext.request.contextPath}/btcAccount/sellOrder.html"
			class="form-horizontal" id="sellOrderForm">
			
			<div class="form-group">
				<label for="sellOrderPlatformListDiv" class="col-sm-2 control-label">
					<spring:message code="label.filterorder.platform"/>
				</label>			
				
				<div id="sellOrderPlatformListDiv" class="col-sm-10">
					<div class="col-sm-4">
		      			<form:select path="platformId" class="form-control">
							<form:option value="">
								<spring:message code="label.select.default.select"/>
							</form:option>
							<c:forEach items="${platformList}" var="platform">
								<form:option value="${platform.id}">
									<c:out value="${platform.name}"/>
								</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="sellAmountDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.sellorder.btc.amount" />
				</label>
				<div id="sellAmountDiv" class="col-sm-10">
					<div class="col-sm-4">
						<input name="amount" class="form-control">
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="sellPriceDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.sellorder.btc.price" />
				</label>
				<div id="sellPriceDiv" class="col-sm-10">
					<div class="col-sm-4">
						<input name="price" class="form-control">
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="sellIsAutoTradeDiv" class="col-sm-2 control-label">
					<spring:message code="label.btcaccount.sellorder.isautotrade" />
				</label>
				<div id="sellIsAutoTradeDiv" class="col-sm-10">
					<div class="col-sm-4">
						<div class="checkbox">
							<label> 
								<input type="checkbox" name="isAutoTrade">
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="col-sm-4">
						<button type="submit" class="btn btn-default">
							<spring:message code="label.btcaccount.sellorder.submit"/>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>

