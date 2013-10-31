package com.decathlon.offroy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronExpression;

public class CronEXpressionTester {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		String cronExpression = "0 14 0-4 * * ?";

		CronExpression cronEx = new CronExpression(cronExpression);

		System.out
				.println("ExpressionSummary:" + cronEx.getExpressionSummary());

		Date now = new Date();
		System.out.println("NextValidTimeAfter:" + "("
				+ SimpleDateFormat.getInstance().format(now) + ")"
				+ cronEx.getNextValidTimeAfter(now));

	}

}
