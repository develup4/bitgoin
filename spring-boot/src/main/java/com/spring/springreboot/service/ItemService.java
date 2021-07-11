package com.spring.springreboot.service;

import com.spring.springreboot.domain.item.Item;
import com.spring.springreboot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 아래와 같은 서비스는 단순한 위임 로직만 가지고 있다.
// 레어어링 외에는 의미는 없다. 고민해볼 필요가 있다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional  // Mutation이므로 필요
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
