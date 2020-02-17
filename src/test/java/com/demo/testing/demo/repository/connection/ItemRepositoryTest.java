package com.demo.testing.demo.repository.connection;

import static com.demo.testing.demo.repository.specification.ItemSpecifications.itemNameContains;

import static com.demo.testing.demo.repository.specification.ItemSpecifications.itemValueContains;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.repository.ItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {
	private Item item1 = new Item("item1", "value1");
	private Item item2 = new Item("item2", "value2");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ItemRepository itemRepository;

	@Before
	public void initTestData() {
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.flush();
	}

	@After
	public void clear() {
		entityManager.clear();
	}

	@Test
	public void whenFindingItemByName_thenCorrect() {
		List<Item> items = itemRepository.findAll(getItemSpecification("item1", null));
		assertThat(items.size()).isEqualTo(1);
	}

	@Test
	public void whenFindingItemByValue_thenCorrect() {
		List<Item> items = itemRepository.findAll(getItemSpecification(null, "value1"));
		assertThat(items.size()).isEqualTo(1);
	}

	@Test
	public void whenFindingItemByNameOrValue_thenCorrect() {
		List<Item> items = itemRepository.findAll(getItemSpecification("item1", "value1"));
		assertThat(items.size()).isEqualTo(1);
	}

	@Test
	public void whenFindingAllItems_thenCorrect() {
		List<Item> items = itemRepository.findAll();
		assertThat(items.size()).isEqualTo(2);
	}

	private Specification<Item> getItemSpecification(String itemName, String itemValue) {
		return Specification.where(itemName == null ? null : itemNameContains(itemName))
				.and(itemValue == null ? null : itemValueContains(itemValue));
	}
}
