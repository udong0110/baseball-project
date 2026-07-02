package baseball.analyzer;

import baseball.domain.player.*;

import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.exception.PlayerNotFound;
import baseball.factory.PlayerCreateFactory;
import baseball.repository.PlayerRepository;

import java.util.Map;
import java.util.Set;

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


        // 투타유형으로 안타 기댓값 조정하는 메서드
        compareHandType();
        // rpm, 구속 각각 리그 평균과 비교하여 보정치, 안타 기댓값 조정하는 메서드
        compareRpmSpeed();
        // 타자의 강/약점 구종에 따라 보정치, 안타 기댓값 조정하는 메서드
        compareZonePitch();


        // 표본이 적지만 타율이 0할인 타자는 초기화
        // 후에 타석수 필드를 추가해 정교하게 리팩토링 예정
        if (hitPercent <= 0.00) {
            hitPercent = 0.05;
        }
        outPercent = 1 - hitPercent;
        System.out.println("안타확률 = " +hitPercent*100+"%");
        System.out.println("범타확률 = " +outPercent*100+"%");
    }


    public void solutionDesign() {

    }









    private void compareZonePitch() {
        PitchType hitterPowerType = hitterStat.getPowerZonePitch().getPitchType(); // 타자의 강점 구종
        PitchType hitterWeakType = hitterStat.getWeakZonePitch().getPitchType(); // 타자의 약점 구종
        Pitch strongPitch = pitcherStat.getStrongPitch();
        PitchType pitcherPowerType = strongPitch.getPitchType();  // 투수의 강점 구종
        Map<PitchType, Pitch> pitchTypes = pitcherStat.getPitch();//투수가 가지고 있는 구종

        // 투수 보유 구종 중 타자의 강/약점 구종에 걸릴 때 차등 보정치 적용
        // 투수가 타자보다 유리하다는 전제로 보정치 적용

        boolean hasPowerMatch = pitchTypes.containsKey(hitterPowerType);
        boolean hasWeakMatch = pitchTypes.containsKey(hitterWeakType);

        // 투수 보유 구종에 타자 강/약점 구종이 존재할 때 안타 기댓값 상승
        // 1-1. 타자의 강점 구종이 투수 구종에 포함될 때
        if (hasPowerMatch) {
            if (pitcherPowerType == hitterPowerType) {
                // 타자의 강점 구종이 투수의 주 구종이라면 어쩔수 없이 던지지만 승부를 피한다는 선택지가 있으므로 7% 증가 적용
                System.out.println("[강점 매칭] 투수의 주 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 5% 증가합니다.");
                hitPercent *= 1.05;
            } else {
                // 타자의 강점 구종이 투수의 부 구종이라면 피한다는 선택지가 있으므로 2% 증가 적용
                System.out.println("[강점 매칭] 투수의 보유 구종이 타자의 강점 구종에 걸리므로 안타 기댓값이 2% 증가합니다.");
                hitPercent *= 1.02;
            }
        }
        // 1-2 타자의 약점 구종이 투수 구종에 포함될 때
        if (hasWeakMatch) {
            if (pitcherPowerType == hitterWeakType) {
                // 타자의 약점 구종이 투수의 주 구종이라면 투수는 주 구종으로 승부를 볼 확률이 높아서 10% 감소 적용
                System.out.println("[약점 매칭] 투수의 주 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 10% 감소합니다.");
                hitPercent *= 0.9;
            } else {
                System.out.println("[약점 매칭] 투수의 보유 구종이 타자의 약점 구종에 걸리므로 안타 기댓값이 5% 감소합니다.");
                hitPercent *= 0.95;
            }
        }
        if (!hasPowerMatch && !hasWeakMatch){
            System.out.println("구종에 따른 강/약점이 존재하지 않아 안타 기댓값 증감이 이뤄지지 않습니다.");
        }
    }

    private void compareRpmSpeed() {
        Pitch strongPitch = pitcherStat.getStrongPitch();
        PitchType pitchType = strongPitch.getPitchType();

        double ballSpeed = strongPitch.getBallSpeed();
        double avgSpeed = pitchType.getAvgSpeed();
        double rpm = strongPitch.getRpm();
        double avgRpm = pitchType.getAvgRpm();

        // rpm과 speed를 각각 다르게 보정하는 이유는 타자 스탯 데이터에 rpm보다 구속에 더 영향을 받기 때문.
        //  구속 5단계 비율 보정
        double speedDiff = ballSpeed - avgSpeed;
        if (speedDiff >= 6.0) {
            System.out.println("투수의 구속이 리그 최상위권이므로 안타 기댓값이 5% 감소합니다.");
            hitPercent *= 0.95;
        } else if (speedDiff >= 2.0) {
            System.out.println("투수의 구속이 리그 상위권이므로 안타 기댓값이 3% 감소합니다.");
            hitPercent *= 0.97;
        } else if (speedDiff <= -6.0) {
            System.out.println("투수의 구속이 리그 최하위권이므로 안타 기댓값이 5% 증가합니다.");
            hitPercent *= 1.05;
        } else if (speedDiff <= -2.0) {
            System.out.println("투수의 구속이 리그 하위권이므로 안타 기댓값이 3% 증가합니다.");
            hitPercent *= 1.03;
        }

        // rpm 5단계 비율 보정
        double rpmDiff = rpm - avgRpm;
        if (rpmDiff >= 200.0) {
            System.out.println("주구종 회전수가 리그 최상위권이므로 안타 기댓값이 3% 감소합니다.");
            hitPercent *= 0.97;
        } else if (rpmDiff >= 70.0) {
            System.out.println("주구종 회전수가 리그 상위권이므로 안타 기댓값이 1.5% 감소합니다.");
            hitPercent *= 0.985;
        } else if (rpmDiff <= -200.0) {
            System.out.println("주구종 회전수가 리그 최하위권이므로 안타 기댓값이 3% 증가합니다.");
            hitPercent *= 1.03;
        } else if (rpmDiff <= -70.0) {
            System.out.println("주구종 회전수가 리그 하위권이므로 안타 기댓값이 1.5% 증가합니다.");
            hitPercent *= 1.015;
        }
    }

    public void compareHandType() {
        HandPitcherType pitcherHand = pitcher.getHandType();
        HandHitterType hitterHand = hitter.getHandType();

        if (pitcherHand == HandPitcherType.RIGHT && hitterHand == HandHitterType.RIGHT) {
            System.out.println("투수 " + pitcher.getName() + "(우투), 타자 " + hitter.getName() + " (우타)이므로 안타 기댓값이 2% 감소합니다.");
            hitPercent *= 0.98;
        } else if (pitcherHand == HandPitcherType.LEFT && hitterHand == HandHitterType.LEFT) {
            System.out.println("투수 " + pitcher.getName() + "(좌투), 타자 " + hitter.getName() + "(좌타)이므로 안타 기댓값이 4% 감소합니다.");
            hitPercent *= 0.96;
        } else {
            System.out.println("역상성 매치업이므로 안타 기댓값이 2% 증가합니다.");
            hitPercent *= 1.02;
        }
    }


}
