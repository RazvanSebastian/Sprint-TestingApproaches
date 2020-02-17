package com.demo.testing.demo.IT;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.repository.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemsRestControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private ItemRepository itemRepository;

	TestRestTemplate restTemplate;
	String uri;
	HttpHeaders httpHeaders;

	@PostConstruct
	public void setup() {
		restTemplate = new TestRestTemplate();
		uri = "http://localhost:" + port;
		httpHeaders = new HttpHeaders();
	}

	@Before
	public void initialize() {
		itemRepository.save(new Item("testItem1", "value1"));
		itemRepository.save(new Item("testItem2", "value2"));
	}

	@After
	public void clear() {
		itemRepository.deleteAll();
	}

	@Test
	public void testCreateItem() {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Item> requestEntity = new HttpEntity<Item>(new Item("item1", "value1"), httpHeaders);
		ResponseEntity<Item> response = restTemplate.exchange(uri + "/api/items", HttpMethod.POST, requestEntity,
				Item.class);
		assertThat(response.getBody().getName()).isEqualTo("item1");
		assertThat(response.getBody().getValue()).isEqualTo("value1");
	}

	@Test
	public void testGetAllItems_nameContains() {
		HttpEntity<Item> requesEntity = new HttpEntity<Item>(null, httpHeaders);
		ResponseEntity<List> response = restTemplate.exchange(uri + "/api/items?name=testItem1", HttpMethod.GET,
				requesEntity, List.class);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void testGetAllItems_valueContains() {
		HttpEntity<Item> requesEntity = new HttpEntity<Item>(null, httpHeaders);
		ResponseEntity<List> response = restTemplate.exchange(uri + "/api/items?value=value1", HttpMethod.GET,
				requesEntity, List.class);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void testGetAllItems_nameAndValueContains() {
		HttpEntity<Item> requesEntity = new HttpEntity<Item>(null, httpHeaders);
		ResponseEntity<List> response = restTemplate.exchange(uri + "/api/items?name=testItem&value=value",
				HttpMethod.GET, requesEntity, List.class);
		assertThat(response.getBody().size()).isEqualTo(2);
	}

}
