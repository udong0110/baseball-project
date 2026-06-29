package baseball.repository;

import baseball.domain.stat.*;
import baseball.domain.player.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    private static final Map<Player, PlayerStat> playerRepositoryMap = new HashMap<>();


    public void save(Player player, PlayerStat playerStat) {
        playerRepositoryMap.put(player, playerStat);
    }

    public static Map<Player, PlayerStat> getMap() {
        return playerRepositoryMap;
    }

    public static PlayerStat findByPlayer(Player player) {
        return playerRepositoryMap.get(player);
    }

   /* public static Map.Entry<Player, PlayerStat> getEntry() {
    }*/

}
