<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign title><@spring.message code="label.updateconfig.title"/></#assign>
<#assign updateConfigAction><@spring.url value="/config/update.html"/></#assign>
	
<#import "../layout.ftl" as layout />

<@layout.masterTemplate title="${title}">
	
	<form class="form-horizontal" action="${updateConfigAction}" method="post">
	
		<div class="form-group">
			<label for="recordGroupDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.recordgroupjob.enabled"/>
			</label>			
			
			<div id="recordGroupDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="recordgroupjob_enabled" class="form-control"
						value="${recordgroupjob_enabled}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="autoTradeDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.autotrade.enabled"/>
			</label>			
			
			<div id="autoTradeDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="autotrade_enabled" class="form-control"
						value="${autotrade_enabled}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="sellOrderCheckPeriodDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.sellorder.checkperiod"/>
			</label>
			
			<div id="sellOrderCheckPeriodDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="sellorder_check_period" class="form-control"
						value="${sellorder_check_period}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="sellOrderDeltaDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.sellorder.delta"/>
			</label>
			
			<div id="sellOrderDeltaDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="sellorder_delta" class="form-control"
						value="${sellorder_delta}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="sellReOrderDeltaDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.sellreorder.delta"/>
			</label>
			
			<div id="sellReOrderDeltaDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="sellreorder_delta" class="form-control"
						value="${sellreorder_delta}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="sellOrderTimeLimitDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.sellorder.timelimit"/>
			</label>
			
			<div id="sellOrderTimeLimitDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="sellorder_time_limit" class="form-control"
						value="${sellorder_time_limit}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="buyOrderCheckPeriodDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.buyorder.checkperiod"/>
			</label>
			
			<div id="buyOrderCheckPeriodDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="buyorder_check_period" class="form-control"
						value="${buyorder_check_period}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="buyOrderDeltaDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.buyorder.delta"/>
			</label>
			
			<div id="buyOrderDeltaDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="buyorder_delta" class="form-control"
						value="${buyorder_delta}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="buyReOrderDeltaDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.buyreorder.delta"/>
			</label>
			
			<div id="buyReOrderDeltaDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="buyreorder_delta" class="form-control"
						value="${buyreorder_delta}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="buyOrderTimeLimitDiv" class="col-sm-2 control-label">
				<@spring.message code="label.updateconfig.buyorder.timelimit"/>
			</label>
			
			<div id="sellOrderTimeLimitDiv" class="col-sm-10">
				<div class="col-sm-4">
					<input name="buyorder_time_limit" class="form-control"
						value="${buyorder_time_limit}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div class="col-sm-4">
		    		<input type="submit" class="btn btn-default" value="<@spring.message code="label.updateconfig.submit"/>"/>
		    	</div>
		    </div>
		</div>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	</form>

</@layout.masterTemplate>


