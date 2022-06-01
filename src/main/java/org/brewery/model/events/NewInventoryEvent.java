package org.brewery.model.events;

import org.brewery.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

    public NewInventoryEvent() {
        super();
    }
}
