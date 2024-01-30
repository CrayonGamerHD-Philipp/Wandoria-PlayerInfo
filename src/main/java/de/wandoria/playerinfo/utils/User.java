package de.wandoria.playerinfo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.william278.husksync.api.BukkitHuskSyncAPI;
import net.william278.husksync.api.HuskSyncAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class User {

    private final HuskSyncAPI huskAPI = BukkitHuskSyncAPI.getInstance();
    private static HashMap<UUID, UUID> target = new HashMap<>();
    UUID uuid;
    String banner;

    Inventory inventory; // https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/PlayerInventory.html
    Inventory enderchst;

    // Getter
    public OfflinePlayer getTargetPlayer() {
        return Bukkit.getOfflinePlayer(target.get(this.uuid));
    }

    // Setter
    public void setTarget(UUID uuid) {
        target.put(this.uuid, uuid);
    }

    // Remove
    public void removeTarget() {
        target.remove(this.uuid);
    }

    // Methods
    public boolean bannerExistsInDatabase (){
        MySQL mysql = new MySQL(null, null, null, null, null);

        try {
            ResultSet rs = mysql.query("SELECT * FROM playerbanner WHERE uuid=" + this.uuid + "'");

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadBanner() {

        if (bannerExistsInDatabase()) {
            Player player = Bukkit.getPlayer(this.uuid);

            MySQL mySQL = new MySQL(null, null, null, null, null);

            ResultSet rs = mySQL.query("SELECT * FROM playerbanner WHERE uuid='" + this.uuid + "'");

            try {
                rs.next();

                this.banner = rs.getString("banner");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else return;
    }

    public void saveBanner() {

        if (bannerExistsInDatabase()) {
            MySQL mySQL = new MySQL(null, null, null, null, null);

            mySQL.update("UPDATE `playerbanner` SET `banner` = '" + this.banner + "' WHERE `uuid` = '" + this.uuid + "'");
        } else {
            MySQL mySQL = new MySQL(null, null, null, null, null);

            mySQL.update("INSERT INTO `playerbanner` (`uuid`, `banner`) VALUES ('" + this.uuid + "', '" + this.banner + "')");
        }
    }

}
