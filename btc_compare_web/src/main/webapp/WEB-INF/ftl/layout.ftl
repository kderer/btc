<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign indexUrl><@spring.url value="/index.html"/></#assign>

<#macro masterTemplate title="BTC Compare">
    <!DOCTYPE HTML>
	<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/css/style.css"/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/css/bootstrap-datetimepicker.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/bootstrap/css/bootstrap.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/jquery-ui/jquery-ui.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/jquery-ui/jquery-ui.structure.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url value="/assets/jquery-ui/jquery-ui.theme.min.css"/>">
        
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/jquery-1.11.2.min.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/moment.min.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/jquery-ui/jquery-ui.min.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/bootstrap/js/transition.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/bootstrap/js/collapse.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/highcharts.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/bootstrap/js/bootstrap.min.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/bootstrap-datetimepicker.min.js"/>"></script>
        <script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/btc_compare.js"/>"></script>
		<script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/verify.min.js"/>"></script>
		<script language="javascript" type="text/javascript" src="<@spring.url value="/assets/js/bootbox.min.js"/>"></script>
        
        <title>${title}</title>
    </head>
    <body>
    
    	<div class="container">
    		<div class="row">
		    	<div class="page-header">		    	
				  	<h1>
				  		BTC COMPARE CHARTS
				  		<small>Btcturk Btcchina Btce</small>
						<#include "menu.ftl">					  	
					</h1>  
				</div>
									    		
			    <div class="panel panel-default">		    			    
					<div class="panel-heading">
				    	<h3 class="panel-title">${title}</h3>
				  	</div>
				  	<div class="panel-body">
				  		<#if request_success_messages??>
				    		<div class="alert alert-success" role="alert">
				    			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    			 	<span aria-hidden="true">&times;</span>
				    			 </button>
				    			<#list request_success_messages as messageKey>
									<@spring.message code="${messageKey}"/>
								</#list>			    		
				    		</div>
				    	</#if>
				    	
				    	<#if request_error_messages??>
				    		<div class="alert alert-danger" role="alert">
				    			 <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    			 	<span aria-hidden="true">&times;</span>
				    			 </button>
				    			<#list request_error_messages as messageKey>
									<@spring.message code="${messageKey}"/><br>
								</#list>			    		
				    		</div>
				    	</#if>
				    	<#nested />
					</div>
				</div>
			</div>
    	</div>
     
    </body>
    </html>
</#macro>