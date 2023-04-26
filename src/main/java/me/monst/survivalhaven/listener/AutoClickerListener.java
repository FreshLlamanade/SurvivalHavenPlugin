package me.monst.survivalhaven.listener;

import me.monst.pluginutil.lang.ColorStringBuilder;
import me.monst.survivalhaven.Permissions;
import me.monst.survivalhaven.SurvivalHavenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.event.block.Action.LEFT_CLICK_AIR;
import static org.bukkit.event.block.Action.LEFT_CLICK_BLOCK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK;
import static org.bukkit.inventory.EquipmentSlot.OFF_HAND;

public class AutoClickerListener implements Listener {
    
    private static class AutoClickerDetectedException extends Exception {}

    private final SurvivalHavenPlugin plugin;
    private final Map<UUID, ClickMonitor> clickMonitors = new HashMap<>();
    
    public AutoClickerListener(SurvivalHavenPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getCause() != ENTITY_ATTACK)
            return;
        if (!(e.getDamager() instanceof Player))
            return;
        handleClick((Player) e.getDamager());
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        if (e.getHand() == OFF_HAND)
            return;
        if (e.getAction() != LEFT_CLICK_AIR && e.getAction() != LEFT_CLICK_BLOCK)
            return;
        handleClick(e.getPlayer());
    }
    
    private void handleClick(Player player) {
        try {
            ClickMonitor monitor = clickMonitors.computeIfAbsent(player.getUniqueId(), uuid -> new ClickMonitor());
            monitor.recordClick();
            // Uncomment for debugging
//            player.sendMessage(monitor.toString());
        } catch (AutoClickerDetectedException ex) {
//            player.teleport(SPAWN_LOCATION);
//            player.sendMessage(plugin.config().detectionMessage.get());
            
            String logMessage = player.getName() + " clicked at a suspiciously regular interval.";
            plugin.getLogger().warning(logMessage);
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (Permissions.ADMIN.ownedBy(onlinePlayer)) {
                    onlinePlayer.sendMessage(ChatColor.RED + logMessage);
                }
            }
        }
    }
    
    private class ClickMonitor {
        private Long lastClickTime;
        private Integer bestGuessInterval;
        private Integer detectionLenience;
        private Integer observedError;
        
        private int counter = 0;
        
        void recordClick() throws AutoClickerDetectedException {
            long currentTime = System.currentTimeMillis();
            
            if (lastClickTime == null) { // First click
                lastClickTime = currentTime;
                return;
            }
            
            // Calculate the time since the last click
            int msSinceLastClick = (int) (currentTime - lastClickTime);
            lastClickTime = currentTime; // Update the last click time
            
            // If we don't have a best guess interval yet, set it to the time since the last click
            if (bestGuessInterval == null) {
                bestGuessInterval = msSinceLastClick;
                detectionLenience = calculateLenience();
                return;
            }
            
            // If this click did not come close enough to fitting the best guess interval,
            // reset the counter and update the best guess interval
            observedError = Math.abs(msSinceLastClick - bestGuessInterval);
            if (observedError > detectionLenience) {
                bestGuessInterval = msSinceLastClick;
                detectionLenience = calculateLenience();
                counter = 0;
                return;
            }
            
            counter++; // Increment the counter
            
            // Keep a running average of the best guess interval
            bestGuessInterval = addToRunningAverage(msSinceLastClick);
            detectionLenience = calculateLenience(); // Update the detection lenience
            
            // If the counter a multiple of the threshold, we assume the player is using an auto-clicker
            // Do not throw the exception if the player has already been detected
            if (counter % plugin.config().clickThreshold.get() == 0) {
                throw new AutoClickerDetectedException();
            }
        }
        
        private int addToRunningAverage(int newValue) {
            return ((bestGuessInterval * counter) + newValue) / (counter + 1);
        }
        
        private int calculateLenience() {
            return Math.min(bestGuessInterval / plugin.config().strictnessFactor.get(), plugin.config().maxLenience.get());
        }
        
        private void reset() {
            lastClickTime = null;
            bestGuessInterval = null;
            detectionLenience = null;
            observedError = null;
            counter = 0;
        }
        
        @Override
        public String toString() {
            return new ColorStringBuilder()
                    .yellow("Click interval: ").gray(bestGuessInterval).newLine()
                    .yellow("Error margin: ").green(detectionLenience).newLine()
                    .yellow("Observed error: ").red(observedError).newLine()
                    .yellow("Suspicious clicks counted: ").darkRed().bold(counter)
                    .toString();
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        ClickMonitor monitor = clickMonitors.get(e.getPlayer().getUniqueId());
        if (monitor == null)
            return;
        monitor.reset();
        // This makes sure that the player cannot simply walk back to where they were and continue using the auto-clicker,
        // as they will keep getting teleported back to spawn every time they move until turning it off.
    }
    
}
