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

    public static PlayerStat findStatByPlayer(Player player) {
        return playerRepositoryMap.get(player);
    }

    public static Player findPlayer(String name, Team team, int backNumber) {
        for (Player keyPlayer : playerRepositoryMap.keySet()) {
            if (keyPlayer.getName().equalsIgnoreCase(name)
            && keyPlayer.getTeam().equals(team)
            && keyPlayer.getBackNumber()==backNumber) {
                return keyPlayer;
            }
        }
        return null;
    }



}
