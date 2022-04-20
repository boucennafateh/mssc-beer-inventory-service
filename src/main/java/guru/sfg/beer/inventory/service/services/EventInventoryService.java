package guru.sfg.beer.inventory.service.services;

import common.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.mappers.BeerInventoryMapper;
import guru.sfg.beer.inventory.service.web.model.BeerDto;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
