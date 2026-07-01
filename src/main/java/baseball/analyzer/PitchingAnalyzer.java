package baseball.analyzer;

import baseball.domain.player.*;

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
    private  double hitPercent;
    private  double outPercent;

    public PitchingAnalyzer(Hitter hitter, HitterStat hitterStat, Pitcher pitcher, PitcherStat pitcherStat) {
        if (hitter == null || pitcher == null) {
            throw new PlayerNotFound("[검색 오류] 선수를 찾지 못하였습니다.");
        }
        this.hitter = hitter;
        this.pitcher = pitcher;
        this.hitterStat = hitterStat;
        this.pitcherStat = pitcherStat;
        this.hitPercent = hitterStat.getAvg();
        this.outPercent = 1 - hitPercent;

    }




    public void simulationPitching() {
        //  투수의 구종(구속, rpm), 핵심구종 , era  /  타자의 약점존구종 , 강점존구종, avg , ops
        //  투수의 투구 ,  타자의 타석
        // avg를 기준으로 안타확률 조정
        // 1. 오른손 투수 > 오른손 타자     반대동일
        compareHandType();
        System.out.println("안타확률 = " +hitPercent*100+"%");
        // 2. 타자의 강/약점 구종이 투수의 구종에 포함되는지
        // 2-1 타자의 강점 구종이 투수 구종에 포함되는 경우
        // 2-2 타자의 약점 구종이 투수 구종에 포함되는 경우
        // 2-3 앞서 살핀 경우가 투수의 주무기 구종에 걸리는 경우
        // 3. 투수 구종의 rpm과 속도를 보고 하위권,평균,상위권 을 나타냄
    }

    private void compareHandType() {
        // 오른손 투수 오른손 타자 -2%
        // 왼손 투수 왼손 타자  일때 안타확률 -4%
        // 왼손투수 오른손타자 , 오른손 투수 왼손 타자 , 스위치히터 일때 안타확률 +2%
        if (pitcher.getHandType() == HandPitcherType.RIGHT && hitter.getHandType() == HandHitterType.RIGHT) {
            System.out.println("투수 "+pitcher.getName()+"은(는) 우투, 타자 "+hitter.getName()+"은(는) 우타이므로 안타 확률이 2% 감소합니다.");
            hitPercent -= 0.02;
        } else if (pitcher.getHandType() == HandPitcherType.LEFT && hitter.getHandType() == HandHitterType.LEFT) {
            System.out.println("투수 " + pitcher.getName() + "은(는) 좌투, 타자 " + hitter.getName() + "은(는) 좌타이므로 안타 확률이 2% 감소합니다.");
            hitPercent -= 0.04;
        } else if (pitcher.getHandType() == HandPitcherType.LEFT && hitter.getHandType() == HandHitterType.RIGHT
                || pitcher.getHandType() == HandPitcherType.RIGHT && hitter.getHandType() == HandHitterType.LEFT
                || hitter.getHandType()==HandHitterType.SWITCH) {
            System.out.println("투수 " + pitcher.getName() + "와 타자 " + hitter.getName() + "의 투타유형이 역상성이므로 안타 확률이 2% 증가합니다.");
            hitPercent += 0.02;
        }

    }


}
