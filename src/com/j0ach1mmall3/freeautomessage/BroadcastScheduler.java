package com.j0ach1mmall3.freeautomessage;

import com.j0ach1mmall3.freeautomessage.api.Broadcaster;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by j0ach1mmall3 on 2:00 19/08/2015 using IntelliJ IDEA.
 */
public class BroadcastScheduler extends BukkitRunnable {
    private Broadcaster broadcaster;

    public BroadcastScheduler(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void run() {
        broadcaster.broadcast();
    }
}
