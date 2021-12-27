package com.leovegas.wallet.utility;

import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerator {

			
	public NumberGenerator() {
		
	}
	
	
	/**
	 * Generate the account numbers between 1000000000 to 9999999999L
	 * */
	public long getAccountNumber() {
		return generateAccountNumber();
	}

	
	private long generateAccountNumber() {
		
		return ThreadLocalRandom.current().nextLong(1000000000, 9999999999L);
	}

}
