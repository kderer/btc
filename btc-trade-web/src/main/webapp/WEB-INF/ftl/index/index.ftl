<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign title><@spring.message code="label.index.title"/></#assign>

<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">

	<script language="javascript" type="text/javascript">
		$(function(){
			setTimeout('drawChartsOnIndexPage()', 1000);			
	   	});	   
	</script>
	
	<div class="panel panel-default">
		<div class="panel-heading">
	    	<h3 style="cursor: pointer" class="panel-title" data-toggle="collapse" data-target="#optionsDiv">
	    		<@spring.message code="label.index.options"/>
	    	</h3>				    	
	  	</div>
	  	<#include "options.ftl">
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
	    	<h3 class="panel-title"><@spring.message code="label.index.charts"/></h3>
	  	</div>
	  	<div class="panel-body">	
			<div class="row">	
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="panel panel-primary">
						<div class="panel-heading">
					    	<h3 class="panel-title"><@spring.message code="label.index.chart.bid"/></h3>
					  	</div>
					  	<div class="panel-body">
					    	<div id="bidChartContainer"></div>
						</div>
					</div>
				</div>
				<div class="col-md-1"></div>	
			</div>		
			
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="panel panel-primary">
						<div class="panel-heading">
					    	<h3 class="panel-title"><@spring.message code="label.index.chart.ask"/></h3>
					  	</div>
					  	<div class="panel-body">
					    	<div id="askChartContainer"></div>	
						</div>
					</div>	
				</div>
				<div class="col-md-1"></div>	
			</div>
		</div>
	</div>
</@layout.masterTemplate>