package com.gamecouch.gcs.gamecouchsystem;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class Getnextid {

	@Test
	public void test() {
		var lookup = new Lookup();
		long result = lookup.getNextID(Customer.class);
		System.out.println(result);
		assert false;
	}

}
