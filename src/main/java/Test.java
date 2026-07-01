import baseball.analyzer.PitchingAnalyzer;
import baseball.domain.player.*;
import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        // 1. 테스트용 투수 데이터 생성
        Pitcher mockPitcher = new Pitcher("박세웅", Team.LOTTE, 43, HandPitcherType.RIGHT, PitcherPosition.SP);

// 1-1. 투수 구종 Map 조립
        Map<PitchType, Pitch> pitchMap = new HashMap<>();
        Pitch slider = new Pitch(PitchType.SLIDER, 2500, 142); // 2500RPM, 142km 슬라이더
        pitchMap.put(PitchType.SLIDER, slider);

        PitcherStat mockPitcherStat = new PitcherStat(3.50, pitchMap, slider); // era=3.50, 주무기=슬라이더


// 2. 테스트용 타자 데이터 생성 (동우님이 말씀하신 전민재 선수!)
        Hitter mockHitter = new Hitter("전민재", Team.LOTTE, 17, HandHitterType.SWITCH, HitterPosition.SS);

// 💡 [ZonePitch 적용!] 강점존과 약점존을 각각 Zone과 PitchType을 조합해서 새로 생성합니다.
        ZonePitch powerZonePitch = new ZonePitch(Zone.LEFT_HIGH, PitchType.FASTBALL); // 좌측상단 직구에 강함
        ZonePitch weakZonePitch = new ZonePitch(Zone.RIGHT_LOW, PitchType.SLIDER);    // 우측하단 슬라이더에 약함

// 💡 생성된 ZonePitch 객체 2개를 HitterStat에 쏙 넣어줍니다!
        HitterStat mockHitterStat = new HitterStat(1.0, 0.289, powerZonePitch, weakZonePitch);


// 3. 분석기 생성 후 시뮬레이션 메서드 호출!
        PitchingAnalyzer analyzer = new PitchingAnalyzer(mockHitter,mockHitterStat,mockPitcher,mockPitcherStat); // repository 주입

// 우리가 완성한 분석기 시뮬레이션 메서드 실행!
        analyzer.simulationPitching();
    }
}
