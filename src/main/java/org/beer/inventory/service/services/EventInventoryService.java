package org.beer.inventory.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beer.inventory.service.config.JmsConfig;
import org.beer.inventory.service.domain.BeerInventory;
import org.beer.inventory.service.repositories.BeerInventoryRepository;
import org.beer.inventory.service.web.mappers.BeerInventoryMapper;
import org.brewery.model.BeerDto;
import org.brewery.model.BeerInventoryDto;
import org.brewery.model.events.NewInventoryEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventInventoryService {

    public final BeerInventoryMapper mapper;
    public final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    @Transactional
    public void listener(NewInventoryEvent inventoryEvent){
        BeerDto beerDto = inventoryEvent.getBeerDto();
        BeerInventoryDto inventoryDto = BeerInventoryDto.builder()
                .beerId(beerDto.getId())
                .upc(beerDto.getUpc())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .build();
        BeerInventory beerInventory = mapper.beerInventoryDtoToBeerInventory(inventoryDto);
        log.debug("inventory is going to be saved : " + beerInventory);
        beerInventoryRepository.save(beerInventory);

    }
}
