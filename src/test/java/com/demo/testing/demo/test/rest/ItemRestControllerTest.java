package com.demo.testing.demo.test.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import com.demo.testing.demo.controller.ItemRestController;
import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemRestController.class)
public class ItemRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ItemService itemService;

	@Test
	public void givenItemName_whenGetItems_thenReturnArray() throws Exception {
		List<Item> list = Arrays.asList(new Item("item1", "value1"), new Item("item2", "value2"));		
		when(itemService.findItems()).thenReturn(list);
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "item1");
		requestParams.add("value", "value1");
		mvc.perform(get("/api/items")
				.params(requestParams)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void givenItem_whetPostItem_thenCreateItem() throws Exception {
		final Item newItem = new Item(1L, "newItem1", "newValue1");
		when(itemService.saveItem(Mockito.any())).thenReturn(newItem);
		mvc.perform(post("/api/items")
				.content(asJsonString(newItem))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
