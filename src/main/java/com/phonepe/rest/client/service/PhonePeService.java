package com.phonepe.rest.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonepe.rest.client.dto.Payment;
import com.phonepe.rest.client.dto.PaymentResponse;
import com.phonepe.rest.client.locator.RuleUtil;

@Service
public class PhonePeService {
	@Autowired
	private RuleUtil util;

	public PaymentResponse quickPay(Payment payment) {
		return util.processPayment(payment);
	}

	public PaymentResponse getPaymentsByVendor(String vendor) {
		return util.getTransaction(vendor);
	}

}
