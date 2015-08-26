<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<script language="javascript" type="text/javascript">
	
   $(function(){
      $('#createdateStartDiv').datetimepicker({
      	format: 'MM/DD/YYYY HH:mm'
      });
      
      $('#createdateEndDiv').datetimepicker({
      	format: 'MM/DD/YYYY HH:mm',
      });      
   });   
</script>

<div class="collapse" id="filterOrderDiv">
	<div class="panel-body">
		<form class="form-horizontal" id="filterOrderForm">
		
			<div class="form-group">
				<label for="platformListCheckDiv" class="col-sm-2 control-label">
					<@spring.message code="label.filterorder.platform"/>
				</label>			
				
				<div id="platformListCheckDiv" class="col-sm-10">
					<#list platformList as platform>
						<div class="col-sm-2">
			      			<div class="checkbox">
				        		<label>
				          			<input type="checkbox" name="platformIdList" value="${platform.id}" checked> 
				          				${platform.name}
				        		</label>
			  				</div>
						</div>
					</#list>
				</div>
			</div>
			
			<div class="form-group">
				<label for="statusDiv" class="col-sm-2 control-label">
					<@spring.message code="label.filterorder.status"/>					
				</label>			
				
				<div id="statusDiv" class="col-sm-10">
					<#list statusList as status>
						<div class="col-sm-2">
			      			<div class="checkbox">
				        		<label>
				          			<input type="checkbox" name="statusList" value="${status.code}"> 
				          				<@spring.message code="${status.i18NKey}"/>
				        		</label>
			  				</div>
						</div>
					</#list>
				</div>
			</div>
			
			<div class="form-group">
				<label for="orderTypeDiv" class="col-sm-2 control-label">
					<@spring.message code="label.filterorder.orderType"/>					
				</label>			
				
				<div id="orderTypeDiv" class="col-sm-10">
					<#list orderTypeList as orderType>
						<div class="col-sm-2">
			      			<div class="checkbox">
				        		<label>
				          			<input type="checkbox" name="orderTypeList" value="${orderType.code}"> 
				          				<@spring.message code="${orderType.i18NKey}"/>
				        		</label>
			  				</div>
						</div>
					</#list>
				</div>
			</div>
			
			<div class="form-group">
				<label for="profitDiv" class="col-sm-2 control-label">
					<@spring.message code="label.filterorder.profit"/>					
				</label>			
				
				<div id="profitDiv" class="col-sm-10">
					<div class="col-sm-4">
		      			<input name="profitStart" class="form-control">
					</div>
					<div class="col-sm-4">
		      			<input name="profitEnd" class="form-control">
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="createdateDiv" class="col-sm-2 control-label">
					<@spring.message code="label.filterorder.createdate"/>					
				</label>			
				
				<div id="createdateDiv" class="col-sm-10">
					<div class="col-sm-4">
						<div class="input-group date" id="createdateStartDiv">
			      			<input name="createDateStartStr" class="form-control">
			      			<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group date" id="createdateEndDiv">
			      			<input name="createDateEndStr" class="form-control">
			      			<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
			</div>		
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="col-sm-4">
			    		<button type="button" id="filterOrdersSubmitBtn" class="btn btn-default">
			    			<@spring.message code="label.filterorder.submit"/>
			    		</button>
			    	</div>
			    </div>
			</div>
		
		</form>	
	</div>
	
</div>


