package baseball.analyzer;

import baseball.domain.player.Hitter;
import baseball.domain.player.Pitcher;

import baseball.domain.player.Player;
import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.exception.PlayerNotFound;
import baseball.factory.PlayerCreateFactory;
import baseball.repository.PlayerRepository;

import java.util.Map;

public class PitchingAnalyzer {

    private final Hitter hitter ;
    private final Pitcher pitcher ;
    private final PitcherStat pitcherStat;
    private final HitterStat hitterStat;

    public PitchingAnalyzer(Hitter hitter, HitterStat hitterStat, Pitcher pitcher, PitcherStat pitcherStat) {
        if (hitter == null || pitcher == null) {
            throw new PlayerNotFound("[검색 오류] 선수를 찾지 못하였습니다.");
        }
        this.hitter = hitter;
        this.pitcher = pitcher;
        this.hitterStat = hitterStat;
        this.pitcherStat = pitcherStat;

    }

    public void solutionDesign() {
        // pitchingSolution(투수,타자)
        pitchingSolution();
        // hittingSolution(투수,타자)
        hittingSolution();



        // simulation(투수,타자)를 통해 상황을 예측해본다.
    }

    private void pitchingSolution() {
        //  투수의 구종(구속, rpm), 핵심구종 , era  /  타자의 약점존 , 강점존, avg , ops
        //  투수의 투구 ,  타자의 타석

        // 1. 오른손 투수 > 왼손 타자     반대동일
    }

    private void hittingSolution() {

    }

    public void simulationPitching() {

    }




}
