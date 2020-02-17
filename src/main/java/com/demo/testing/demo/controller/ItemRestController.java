package com.demo.testing.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.testing.demo.model.Item;
import com.demo.testing.demo.service.ItemService;

@RestController
@RequestMapping("/api")
public class ItemRestController {

	@Autowired
	private ItemService itemService;

	@PostMapping("/items")
	public ResponseEntity<?> saveItem(@RequestBody Item item) {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.saveItem(item));
	}

	@GetMapping("/items")
	public ResponseEntity<?> getItems(@RequestParam(required = false, value = "name") String itemName,
			@RequestParam(required = false, value = "value") String itemValue) {
		return ResponseEntity.ok(itemService.findItems(itemName, itemValue));
	}

}
