package com.demo.testing.demo.service;

import static com.demo.testing.demo.repository.specification.ItemSpecifications.itemNameContains;
import static com.demo.testing.demo.repository.specification.ItemSpecifications.itemValueContains;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

	public List<Item> findItems() {
		return itemRepository.findAll();
	}

	public List<Item> findItems(String itemName, String itemValue) {
		Specification<Item> specification = Specification.
				where(itemName == null ? null : itemNameContains(itemName))
				.and(itemValue == null ? null : itemValueContains(itemValue));
		return itemRepository.findAll(specification);
	}
	

}
