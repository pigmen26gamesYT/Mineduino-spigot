package eu.razniewski.mineduino.worldtominecraft;

import eu.razniewski.mineduino.MineduinoPlugin;
import eu.razniewski.mineduino.locator.Locator;
import eu.razniewski.mineduino.utils.ParsedTopic;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

public class ChestDestroyListener implements Listener {
    @EventHandler
    public void onChestDestroy(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.CHEST)) {
            Chest chest = (Chest)e.getBlock().getState();
            if(chest.getCustomName() != null && chest.getCustomName().startsWith("MD")) {
                Optional<ParsedTopic> topic = ParsedTopic.from(chest.getCustomName());
                if(topic.isPresent() && topic.get().getType().equals("smart")) {
                    Locator locator = MineduinoPlugin.getInstance().getSmartChestLocator();
                    locator.removeAll(e.getBlock().getLocation());
                    e.getPlayer().sendMessage("[MD] Smart chest deleted!");
                }
                else if(topic.isPresent() && topic.get().getType().equals("simple")) {
                    Locator locator = MineduinoPlugin.getInstance().getLocator();
                    locator.removeAll(e.getBlock().getLocation());
                    e.getPlayer().sendMessage("[MD] Simple input chest deleted!");
                }
            }
        }
    }
}
