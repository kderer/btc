<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign title><@spring.message code="label.dailyreport.title"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">
	
	<#list dailyProfitList as dailyProfit>
		<table class="table table-hover">
			<thead>
				<tr>
					<th colspan="3">${dailyProfit.date}</th>		
				</tr>
			</thead>
			<tr>
				<td><@spring.message code="label.dailyreport.orderType.header"/></td>
				<td><@spring.message code="label.dailyreport.amount.header"/></td>
				<td><@spring.message code="label.dailyreport.profit.header"/></td>
			</tr>			
			<#list dailyProfit.detailList as detail>
				<tr>
					<td>${detail.orderType}</td>
					<td>${detail.amount}</td>
					<td>${detail.totalProfit}</td>
				</tr>
			</#list>
		</table>
	</#list>

</@layout.masterTemplate>


