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
    }




    // avg를 기준으로 안타확률 조정
    public void simulationPitching() {
        System.out.println("선수 데이터 분석 중......");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("가상 시뮬레이션 중......");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=========시뮬레이션 결과==========");
        // 투타유형으로 안타 확률 조정하는 메서드
        compareHandType();

        compareRpmSpeed();
        // 2. 타자의 강/약점 구종이 투수의 구종에 포함되는지
        // 2-1 타자의 강점 구종이 투수 구종에 포함되는 경우
        // 2-2 타자의 약점 구종이 투수 구종에 포함되는 경우
        // 2-3 앞서 살핀 경우가 투수의 주무기 구종에 걸리는 경우
        // 3. 투수 구종의 rpm과 속도를 보고 하위권,평균,상위권 을 나타냄




        if (hitPercent <= 0.00) {
            hitPercent = 0.05;
        }
        outPercent = 1 - hitPercent;
        System.out.println("안타확률 = " +hitPercent*100+"%");
        System.out.println("범타확률 = " +outPercent*100+"%");
    }

    private void compareRpmSpeed() {
        // 타자의 체감상 볼의 회전수보다 구속이 더 큰 영향을 끼치므로 보정 상한선의 차이를 둠
        Pitch strongPitch = pitcherStat.getStrongPitch();
        PitchType pitchType = strongPitch.getPitchType();

        double ballSpeed = strongPitch.getBallSpeed();
        double avgSpeed = pitchType.getAvgSpeed();

        double rpm = strongPitch.getRpm();
        double avgRpm = pitchType.getAvgRpm();

        // 구속 5단계 차등 적용 보정 로직
        double speedDiff = ballSpeed - avgSpeed;

        if (speedDiff >= 6.0) {
            System.out.println("투수의 구속이 리그 최상위권이므로 타자의 안타 확률이 5% 감소합니다.");
            hitPercent -= 0.05;
        } else if (speedDiff >= 2.0) {
            System.out.println("투수의 구속이 리그 상위권이므로 타자의 안타 확률이 3% 감소합니다.");
            hitPercent -= 0.03;
        } else if (speedDiff <= -6.0) {
            System.out.println("투수의 구속이 리그 최하위권이므로 타자의 안타 확률이 5% 증가합니다.");
            hitPercent += 0.05;
        } else if (speedDiff <= -2.0) {
            System.out.println("투수의 구속이 리그 하위권이므로 타자의 안타 확률이 3% 증가합니다.");
            hitPercent += 0.03;
        } else {
            System.out.println("투수의 구속이 리그 평균 수준이므로 상성 보정이 없습니다.");
        }

        // RPM 5단계 차등 적용 보정 로직

        double rpmDiff = rpm - avgRpm;

        if (rpmDiff >= 200.0) {
            System.out.println("주무기 회전수가 리그 최상위권이므로 타자의 안타 확률이 3% 감소합니다.");
            hitPercent -= 0.03;
        } else if (rpmDiff >= 70.0) {
            System.out.println("주무기 회전수가 리그 상위권이므로 타자의 안타 확률이 1.5% 감소합니다.");
            hitPercent -= 0.015;
        } else if (rpmDiff <= -200.0) {
            System.out.println("주무기 회전수가 리그 최하위권이므로 타자의 안타 확률이 3% 증가합니다.");
            hitPercent += 0.03;
        } else if (rpmDiff <= -70.0) {
            System.out.println("주무기 회전수가 리그 하위권이므로 타자의 안타 확률이 1.5% 증가합니다.");
            hitPercent += 0.015;
        } else {
            System.out.println("주무기 회전수가 리그 평균 수준이므로 회전 보정이 없습니다.");
        }
    }

    public void compareHandType() {
        HandPitcherType pitcherHand = pitcher.getHandType();
        HandHitterType hitterHand = hitter.getHandType();

        if (pitcherHand == HandPitcherType.RIGHT && hitterHand == HandHitterType.RIGHT) {
            System.out.println("투수 " + pitcher.getName() + "은(는) 우투, 타자 " + hitter.getName() + "은(는) 우타이므로 안타 확률이 2% 감소합니다.");
            hitPercent -= 0.02;
        } else if (pitcherHand == HandPitcherType.LEFT && hitterHand == HandHitterType.LEFT) {
            System.out.println("투수 " + pitcher.getName() + "은(는) 좌투, 타자 " + hitter.getName() + "은(는) 좌타이므로 안타 확률이 4% 감소합니다.");
            hitPercent -= 0.04;
        } else {
            System.out.println("역상성 매치업이므로 안타 확률이 2% 증가합니다.");
            hitPercent += 0.02;
        }
    }


}
