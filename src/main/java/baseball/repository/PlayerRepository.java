package baseball.repository;

import baseball.domain.stat.*;
import baseball.domain.player.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    private Map<Player, PlayerStat> playerRepositoryMap = new HashMap<>();


    public void save(Player player, PlayerStat playerStat) {
        playerRepositoryMap.put(player, playerStat);
    }

    public PlayerStat findByPlayer(Player player) {
        return playerRepositoryMap.get(player);
    }

    public Map<Player, PlayerStat> findAll() {
        return playerRepositoryMap;
    }
}
