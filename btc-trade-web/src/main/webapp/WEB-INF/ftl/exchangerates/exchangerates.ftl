<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign title><@spring.message code="label.exhchangerates.title"/></#assign>
<#assign rebaseTitle><@spring.message code="label.exhchangerates.rebase"/></#assign>

<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">

	<script language="javascript" type="text/javascript">
		function updateRatios() {
			var baseamount = parseFloat($("#amountText").val());
			if(baseamount) {
				$("#exchangeRatioTable td:nth-child(2)").each(
					function() {
						var ratio = $(this).prev().find("input").val();
						$(this).text(ratio * baseamount);
					}
				);
			}
		}	
	</script>
	
	<form class="form-horizontal">
		
		<div class="form-group">
			<label for="baseCurrencyDiv" class="col-sm-2 control-label">
				<@spring.message code="label.exhchangerates.basecurrency"/>
			</label>			
			
			<div id="baseCurrencyDiv" class="col-sm-10">
				<div class="col-sm-4 valueLabel">${exchangeRatesTable.base}</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="amountDiv" class="col-sm-2 control-label">
				<@spring.message code="label.exhchangerates.amount"/>
			</label>			
			
			<div id="amountDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input id="amountText" value="1.0" class="form-control" onkeyup="updateRatios()"/>
				</div>
			</div>
		</div>
	
	<form>
	
	<table class="table table-hover" id="exchangeRatioTable">
		<thead>
			<tr>
				<th><@spring.message code="label.exhchangerates.currency"/></th>
				<th><@spring.message code="label.exhchangerates.ratio"/></th>
				<th><@spring.message code="label.exhchangerates.changebase"/></th>	
			</tr>
		</thead>
		<tbody>
		<#list exchangeRatesTable.rates?keys as key>
			<tr>
				<td>${key}<input type="hidden" value="${exchangeRatesTable.rates[key]}"/></td>
				<td>${exchangeRatesTable.rates[key]}</td>
				<td>
					<@c.url value="/exchangeRates/rebase.html" var="url">
						<@c.param name="currency" value="${key}"/>
					</@c.url>
					<a href="${url}" class="btn btn-default" title="${rebaseTitle}"/>
						<span class="glyphicon glyphicon-hand-up"></span>
					</a>					
				</td>	
			</tr>
		</#list>
		</tbody>		 	
	</table>
	
	
</@layout.masterTemplate>