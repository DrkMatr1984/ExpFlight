package com.theofficialstijn.expflight;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpFlight extends JavaPlugin
{
  private ArrayList<Player> players = new ArrayList<Player>();

  public void onEnable()
  {
    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      @SuppressWarnings("deprecation")
	public void run()
      {
        for (Player player : Bukkit.getOnlinePlayers()) {
          if(!canFlyCheck(player)){
          if ((!player.getAllowFlight()) && (player.getLevel() > 1)) {
            player.setAllowFlight(true);
          }
          if ((player.getAllowFlight()) && (player.getLevel() < 1)) {
            player.setAllowFlight(false);
          }
          if (player.isFlying()) {
            if (!ExpFlight.this.players.contains(player)) {
              ExpFlight.this.players.add(player);
              int l = player.getLevel();
              player.setLevel(l - 1);
            }
          }
          else if (ExpFlight.this.players.contains(player)) {
            ExpFlight.this.players.remove(player);
          }
          }
        }
      }
    }
    , 0L, 2L);

    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      @SuppressWarnings("deprecation")
	public void run()
      {
        for (Player player : Bukkit.getOnlinePlayers())
          if (ExpFlight.this.players.contains(player)) {
            int l = player.getLevel();
            player.setLevel(l - 1);
          }
      }
    }
    , 0L, 300L);
  }
  
  public boolean canFlyCheck(Player player){
	   if((player.hasPermission("essentials.fly")) || (player.getGameMode() == GameMode.CREATIVE) || (player.hasPermission("fly.use")) || (player.hasPermission("commandbook.flight.toggle"))){
		   return true;
	   }else
	   return false;
  }
}