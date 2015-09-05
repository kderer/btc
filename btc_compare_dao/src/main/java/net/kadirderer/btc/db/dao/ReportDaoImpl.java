package net.kadirderer.btc.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.kadirderer.btc.db.model.DailyReportDetail;

import org.springframework.stereotype.Service;

@Service		
public class ReportDaoImpl implements ReportDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<DailyReportDetail> queryDailyProfit() {
		Query query = entityManager.createNativeQuery("select date(CREATEDATE) as date, "
				+ "STATUS, count(*) as amount, sum(PROFIT) as profit "
				+ "from T_USERORDER "
				+ "where PROFIT <> 0 "
				+ "group by date(CREATEDATE), status "
				+ "order by CREATEDATE desc", "DailyProfitReport");
		
		List<DailyReportDetail> resultList = new ArrayList<DailyReportDetail>();
		List<Object[]> rawList = query.getResultList();		
		for(Object[] rawData : rawList) {
			DailyReportDetail detail = new DailyReportDetail();
			detail.setDate(rawData[0].toString());
			detail.setStatus((Character)rawData[1]);
			detail.setAmount(Integer.parseInt(rawData[2].toString()));
			detail.setTotalProfit(Double.parseDouble(rawData[3].toString()));
			
			resultList.add(detail);
		}
		
		return resultList;
	}

}
