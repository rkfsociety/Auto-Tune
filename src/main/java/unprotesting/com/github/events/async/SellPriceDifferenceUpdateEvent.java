package unprotesting.com.github.events.async;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import unprotesting.com.github.Main;
import unprotesting.com.github.config.Config;

public class SellPriceDifferenceUpdateEvent extends Event{

    @Getter
    private final HandlerList Handlers = new HandlerList();

    public SellPriceDifferenceUpdateEvent(boolean isAsync){
        super(isAsync);
        if (Config.isSellPriceDifferenceVariationEnabled()){
            updateSellPriceDifference();
        }
    }

    private void updateSellPriceDifference(){
        double SPD = Main.getCache().getECONOMY_INFO().getSellPriceDifference();
        double fraction = Config.getSellPriceVariationUpdatePeriod()/Config.getSellPriceVariationTimePeriod();
        double change = fraction*(Config.getSellPriceDifferenceVariationStart()
         - Main.getDataFiles().getConfig().getDouble("sell-price-difference", 10));
        double newSPD = SPD - change;
        if (newSPD > Main.getDataFiles().getConfig().getDouble("sell-price-difference", 10)){
            Main.getCache().getECONOMY_INFO().updateSellPriceDifference(newSPD);
        }
    }
    
}
