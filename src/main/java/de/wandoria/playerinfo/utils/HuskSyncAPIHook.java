package de.wandoria.playerinfo.utils;

import net.william278.husksync.api.BukkitHuskSyncAPI;
import net.william278.husksync.api.HuskSyncAPI;

public class HuskSyncAPIHook {

    public HuskSyncAPI huskSyncAPI;

    public HuskSyncAPIHook() {
        this.huskSyncAPI = BukkitHuskSyncAPI.getInstance();
    }

    public HuskSyncAPI getHuskSyncAPI() {
        return huskSyncAPI;
    }

}
