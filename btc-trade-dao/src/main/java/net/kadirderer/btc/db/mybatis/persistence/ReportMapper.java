package net.kadirderer.btc.db.mybatis.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import net.kadirderer.btc.db.mybatis.domain.DailyProfitDetail;

public interface ReportMapper {
	
	@Select("select temp1.date,"
				+ "temp1.amount sellAmount, "
				+ "temp1.profit sellProfit, "
				+ "temp2.amount buyAmount, "
				+ "temp2.profit buyProfit, "
				+ "(temp1.profit + temp2.profit) totalProfit "
			+ "from (select date(CREATEDATE) as date, "
					+ "count(*) as amount, "
					+ "sum((PRICE - BASEPRICE) * COMPLETEDAMOUNT) profit "
				+ "from T_USERORDER "
				+ "where BASEPRICE > 0 "
					+ "and COMPLETEDAMOUNT > 0 "
					+ "and ORDERTYPE = 'S' "
				+ "group by date(CREATEDATE), ORDERTYPE "
				+ "order by CREATEDATE desc) temp1 "
				+ "left join ((select date(CREATEDATE) as date, "
					+ "count(*) as amount, "
					+ "sum(-1 * (PRICE - BASEPRICE) * COMPLETEDAMOUNT) profit "
				+ "from T_USERORDER "
				+ "where BASEPRICE > 0 "
					+ "and COMPLETEDAMOUNT > 0 "
					+ "and ORDERTYPE = 'B' "
				+ "group by date(CREATEDATE), ORDERTYPE "
				+ "order by CREATEDATE desc) temp2) on temp2.date = temp1.date")
	public List<DailyProfitDetail> queryDailyProfit();

}
