package com.talkovia.services;

import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.dto.category.CategoryRequestDTO;
import com.talkovia.dto.category.CategoryResponseDTO;
import com.talkovia.dto.item.ItemRequestDTO;
import com.talkovia.dto.item.ItemResponseDTO;
import com.talkovia.mapper.CategoryMapper;
import com.talkovia.mapper.ItemMapper;
import com.talkovia.model.Category;
import com.talkovia.model.Item;
import com.talkovia.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
	}
	
	public List<ItemResponseDTO> getAllItems() {
		return itemMapper.entityListToResponseDTOList(itemRepository.findAll());
	}

	public ItemResponseDTO getItemById(Long id) {
		return itemMapper.entityToResponseDTO(itemRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Item not found! ID: " + id + ", Type: " + Item.class.getName())));
	}

	public void saveItem(ItemRequestDTO categoryRequestDTO) {
		itemRepository.save(itemMapper.requestDTOToEntity(categoryRequestDTO));
	}

	@Transactional
	public ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO) {
		Item existingItem = itemRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Item not found! ID: " + id + ", Type: " + Item.class.getName()));
		existingItem.setName(itemRequestDTO.name());
		existingItem.setDescription(itemRequestDTO.description());
		return itemMapper.entityToResponseDTO(existingItem);
	}

	public void deleteItemById(Long id) {
		if(!itemRepository.existsById(id)) {
			throw new ObjectNotFoundException("Item not found! ID: " + id + ", Type: " + Item.class.getName());
		}
		itemRepository.deleteById(id);
	}
	
	public void deleteAllItems() {
		itemRepository.deleteAll();
	}
}
