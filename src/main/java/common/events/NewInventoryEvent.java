package common.events;

import guru.sfg.beer.inventory.service.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

    public NewInventoryEvent() {
        super();
    }
}
