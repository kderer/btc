package net.kadirderer.btc.impl.util;

import java.util.List;

import net.kadirderer.btc.db.model.Statistics;

public class PriceAnalyzer {
	
	private List<Statistics> statisticsList;
	private int percentage;
	private double lastGmob;
	private double previousGmob;
	
	public PriceAnalyzer(List<Statistics> statistics, int percentage) {
		this.statisticsList = statistics;
		this.percentage = percentage;
		
		calculate();		
	}
	
	private void calculate() {
		if (statisticsList != null && statisticsList.size() > 0) {			
			int amountToAnalyze = (int)(statisticsList.size() * ((double)percentage / (double)100));
			
			if (amountToAnalyze > 0) {
				double product = 1.0;
				for (int i = 0; i < amountToAnalyze; i++) {
					product *= statisticsList.get(i).getGmob();
				}
				
				lastGmob = Math.pow(product, 1.0 / amountToAnalyze);
				
				product = 1.0;
				for (int i = amountToAnalyze; i < statisticsList.size(); i++) {
					product *= statisticsList.get(i).getGmob();
				}
				
				previousGmob = Math.pow(product, 1.0 / (statisticsList.size() - amountToAnalyze));
			}
		}
	}
	
	public boolean isPriceIncreasing() {
		return lastGmob > previousGmob;
	}
	
	public double getLastGmob() {
		return lastGmob;
	}
	
	public double getPreviosGmob() {
		return previousGmob;
	}

}
