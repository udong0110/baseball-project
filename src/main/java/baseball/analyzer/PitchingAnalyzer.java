package baseball.analyzer;

import baseball.domain.player.Hitter;
import baseball.domain.player.Pitcher;

import baseball.repository.PlayerRepository;

import java.util.Map;

public class PitchingAnalyzer {

    private final PlayerRepository playerRepository;
    private Hitter pitcher;
    private Pitcher hitter;

    public PitchingAnalyzer(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void solutionDesign() {
        // 선수(투수,타자)의 입력을 받는다

        // 저장소에 선수가 존재한다면 필드변수에 저장한다
        // 저장된 변수를 이용해서 solution을 제공해준다

        // pitchingSolution(투수,타자)
        // hittingSolution(투수,타자)

        // simulation(투수,타자)를 통해 상황을 예측해본다.
    }

    public void simulationPitching() {

    }



}
