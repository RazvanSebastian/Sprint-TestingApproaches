package com.demo.testing.demo.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.repository.ItemRepository;
import com.demo.testing.demo.service.ItemService;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
	Item testItem;

	@Mock
	ItemRepository itemRepository;

	@InjectMocks
	ItemService itemService;

	@Before
	public void setup() {
		testItem = new Item();
		testItem.setId(1);
		testItem.setName("testName");
		testItem.setValue("testValue");
	}

	@Test
	public void whenSaveItem_thenSuccess() {
		when(itemRepository.save(Mockito.any())).thenReturn(testItem);
		assertThat(itemService.saveItem(testItem).getId()).isEqualTo(1l);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenFindItemsByName_thenSuccess() {
		when(itemRepository.findAll(Mockito.any(Specification.class))).thenReturn(Arrays.asList(testItem));
		assertThat(itemService.findItems(testItem.getName(), null)).hasSize(1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenFindItemsByValue_thenSuccess() {
		when(itemRepository.findAll(Mockito.any(Specification.class))).thenReturn(Arrays.asList(testItem));
		assertThat(itemService.findItems(null, testItem.getValue())).hasSize(1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenFindItemsByNameAndValue_thenSuccess() {
		when(itemRepository.findAll(Mockito.any(Specification.class))).thenReturn(Arrays.asList(testItem));
		assertThat(itemService.findItems(testItem.getName(), testItem.getValue())).hasSize(1);
	}

}
