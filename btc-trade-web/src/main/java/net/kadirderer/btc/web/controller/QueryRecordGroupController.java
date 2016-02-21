package net.kadirderer.btc.web.controller;

import net.kadirderer.btc.service.RecordGroupQueryService;
import net.kadirderer.btc.web.dto.RecordGroupLineChartDataResponse;
import net.kadirderer.btc.web.dto.RecordGroupQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryRecordGroupController {
	
	@Autowired
	private RecordGroupQueryService recordGroupQueryService;
	
	@RequestMapping("/queryRecordGroup")
    public RecordGroupLineChartDataResponse queryRecordGroup(@ModelAttribute RecordGroupQuery queryOptions) {		
		
        return recordGroupQueryService.queryByOptions(queryOptions);
    }

}
