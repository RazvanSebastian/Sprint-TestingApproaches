package com.demo.testing.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.demo.testing.demo.model.Item;

public class ItemSpecifications {

	public static Specification<Item> itemNameContains(String value) {
		return (root, query, builder) -> builder.like(root.get("name"), "%" + value + "%");
	}

	public static Specification<Item> itemValueContains(String value) {
		return (root, query, builder) -> builder.like(root.get("value"), "%" + value + "%");
	}
}
