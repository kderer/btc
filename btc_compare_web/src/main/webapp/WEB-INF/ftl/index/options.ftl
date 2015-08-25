<script language="javascript" type="text/javascript">
	
   $(function(){
      $('#startTimePickerDiv').datetimepicker({
      	format: 'MM/DD/YYYY HH:mm',
      	defaultDate: moment().subtract(2, 'hour')
      });
      
      $('#endTimePickerDiv').datetimepicker({
      	format: 'MM/DD/YYYY HH:mm',
      	defaultDate: moment()
      });      
   })
</script>

<div class="collapse" id="optionsDiv">
	<div class="panel-body">
		<form class="form-horizontal" id="queryOptionsForm">
		
			<div class="form-group">
				<label for="platformListCheckDiv" class="col-sm-2 control-label">Select Platform</label>			
				
				<div id="platformListCheckDiv" class="col-sm-10">
					<#list platformList as platform>
						<div class="col-sm-2">
			      			<div class="checkbox">
				        		<label>
				          			<input type="checkbox" name="platformIdList" value="${platform.id}" checked> ${platform.name}
				        		</label>
			  				</div>
						</div>
					</#list>
				</div>
			</div>
			
			<div class="form-group">
				<label for="recordAmountOptionDiv" class="col-sm-2 control-label">Select Record Amount</label>			
				
				<div id="recordAmountOptionDiv" class="col-sm-10">
					<div class="col-sm-4">
		      			<select name="recordAmount" class="form-control">
		      				<option value="10">10</option>
		      				<option value="20">20</option>
		      				<option value="30" selected>30</option>
		      				<option value="40">40</option>
		      				<option value="50">50</option>
		      				<option value="60">60</option>
		      			</select>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="currencyOptionDiv" class="col-sm-2 control-label">Select Currency</label>			
				
				<div id="currencyOptionDiv" class="col-sm-10">
					<div class="col-sm-4">
		      			<select name="currency" class="form-control">		      				
		      				<#list platformList as platform>
								<option value="${platform.currency}">${platform.currency}</option>
							</#list>
		      			</select>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="startTimeDiv" class="col-sm-2 control-label">Start time</label>
				<div id="startTimeDiv" class="col-sm-10">
					<div class="col-sm-4">
						<div class="input-group date" id="startTimePickerDiv">
			      			<input name="startTime" class="form-control">
			      			<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="form-group">
				<label for="endTimeDiv" class="col-sm-2 control-label">End Time</label>
				<div id="endTimeDiv" class="col-sm-10">
					<div class="col-sm-4">
						<div class="input-group date" id="endTimePickerDiv">
			      			<input name="endTime" class="form-control">
			      			<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			      		</div>
					</div>
				</div>
			</div>
			
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="col-sm-4">
			    		<button type="submit" onclick="drawChartsOnIndexPage(); return false;" class="btn btn-default">Query</button>
			    	</div>
			    </div>
			</div>
		
		</form>	
	</div>
	
</div>


