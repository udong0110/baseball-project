package baseball.analyzer;

import baseball.domain.player.Hitter;
import baseball.domain.player.Pitcher;

import baseball.domain.player.Player;
import baseball.exception.PlayerNotFound;
import baseball.factory.PlayerCreateFactory;
import baseball.repository.PlayerRepository;

import java.util.Map;

public class PitchingAnalyzer {

    private final PlayerRepository playerRepository;
    private final Hitter hitter ;
    private final Pitcher pitcher ;

    public PitchingAnalyzer(PlayerRepository playerRepository, Hitter hitter, Pitcher pitcher) {
        this.playerRepository = playerRepository;
        if (hitter == null || pitcher == null) {
            throw new PlayerNotFound("[검색 오류] 선수를 찾지 못하였습니다.");
        }
        this.hitter = hitter;
        this.pitcher = pitcher;
    }

    public void solutionDesign() {

        // pitchingSolution(투수,타자)
        // hittingSolution(투수,타자)

        // simulation(투수,타자)를 통해 상황을 예측해본다.
    }

    public void simulationPitching() {

    }



}
