package com.phonepe.rest.client.locator;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.phonepe.rest.client.dto.Payment;
import com.phonepe.rest.client.dto.PaymentResponse;

@Component
@PropertySource(value = "classpath:application.properties")
public class RuleUtil {
	@Autowired
	private RestTemplate template;

	HttpHeaders headers = null;
	ObjectMapper mapper = null;
	@Autowired
	private Environment env;

	@PostConstruct
	public void init() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		mapper = new ObjectMapper();
	}

	public PaymentResponse processPayment(Payment payment) {
		PaymentResponse response = null;
		String jsonRequest = null;
		String result = "";
		String URL = "";
		try {
			URL = env.getProperty("payment.url");
			jsonRequest = mapper.writeValueAsString(payment);
			HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);
			result = template.postForObject(URL, entity, String.class);
			response = mapper.readValue(result, PaymentResponse.class);
		} catch (IOException e) {
			System.out.println("ERROR : " + e.getMessage());
		}

		return response;

	}

	public PaymentResponse getTransaction(String vendor) {
		PaymentResponse response = null;
		String result = "";
		String URL = "";
		try {
			URL = env.getProperty("transaction.url") + vendor;
			result = template.getForObject(URL, String.class);
			response = mapper.readValue(result, PaymentResponse.class);
		} catch (IOException e) {
			System.out.println("ERROR : " + e.getMessage());
		}

		return response;
	}
}
