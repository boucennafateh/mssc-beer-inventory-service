package org.beer.inventory.service.services;

import org.brewery.model.BeerOrderDto;

public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);
}
