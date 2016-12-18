<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign title><@spring.message code="label.dailyreport.title"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">

	<div class="row buttonActionDiv">
		<@c.url value="/report/statistics.html" var="url"></@c.url>
		<a class="btn btn-default" href="${url}" role="button">
			<span class="glyphicon glyphicon-plus"></span>
			<@spring.message code="label.report.statistics"/>
		</a>
	</div>
	
	<table class="table table-hover">
		<thead>
			<tr>
				<th><@spring.message code="label.dailyreport.date.header"/></th>
				<th style="text-align: right"><@spring.message code="label.dailyreport.buy.amount.header"/></th>
				<th style="text-align: right"><@spring.message code="label.dailyreport.buy.profit.header"/></th>
				<th style="text-align: right"><@spring.message code="label.dailyreport.sell.amount.header"/></th>
				<th style="text-align: right"><@spring.message code="label.dailyreport.sell.profit.header"/></th>
				<th style="text-align: right"><@spring.message code="label.dailyreport.total.profit.header"/></th>	
			</tr>
		</thead>
		<#list dailyProfitList as detail>
			<tr>
				<td>${detail.date}</td>
				<td style="text-align: right">${detail.buyAmount}</td>
				<td style="text-align: right">${detail.buyProfitStr}</td>
				<td style="text-align: right">${detail.sellAmount}</td>
				<td style="text-align: right">${detail.sellProfitStr}</td>
				<td style="text-align: right">${detail.totalProfitStr}</td>
			</tr>
		</#list>
	</table>

</@layout.masterTemplate>


