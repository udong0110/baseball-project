package baseball.factory;

import baseball.domain.player.*;
import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.domain.stat.PlayerStat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerCreateFactory {

    private Player player;
    private PlayerStat playerStat;
    private static Scanner scanner = new Scanner(System.in);


    public PlayerCreateFactory(Player player, PlayerStat playerStat) {
        this.player = player;
        this.playerStat = playerStat;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerStat getPlayerStat() {
        return playerStat;
    }

    public static PlayerCreateFactory createHitter() {
        Team matchedTeam = null;
        HandType matchedHandType = null;
        Zone matchedPowerZone = null;
        Zone matchedWeakZone = null;

        // 타자 정보 입력
        System.out.print("타자 이름을 입력하세요: ");
        String hitterName = scanner.nextLine();
        System.out.print("팀 영문명을 입력하세요 (lotte, doosan 등): ");
        String teamInputName = scanner.nextLine().toUpperCase();

        matchedTeam = Team.valueOf(teamInputName);

        System.out.print("(왼손, 오른손, 양손)중 타자의 주손을 입력하세요");
        String handInputType = scanner.nextLine();
        matchedHandType = HandType.findType(handInputType);

        System.out.println("타자의 강점 존을 입력하세요(1~9) :");
        int powerZoneInputNumber = scanner.nextInt();
        matchedPowerZone = Zone.findFromNNumber(powerZoneInputNumber);

        System.out.print("타자의 약점 존을 입력하세요(1~9) :");
        int weakZoneInputNumber = scanner.nextInt();
        matchedWeakZone = Zone.findFromNNumber(weakZoneInputNumber);
        scanner.nextLine();

        // 타자 스탯 입력
        System.out.print("타자의 타율을 입력하세요(0~1.0사이) : ");
        double avg = scanner.nextDouble();

        System.out.print("타자의 OPS를 입력하세요(0~2.0 사이) : ");
        double ops = scanner.nextDouble();
        scanner.nextLine();

        return new PlayerCreateFactory(new Hitter(hitterName, matchedTeam, matchedHandType, matchedPowerZone, matchedWeakZone), new HitterStat(ops, avg));
    }

    public static  PlayerCreateFactory createPitcher() {
        Team matchedTeam = null;
        HandType matchedHandType = null;
        PitchType matchedStrongPitchType = null;

        System.out.print("투수 이름을 입력하세요: ");
        String pitcherName = scanner.nextLine();

        System.out.print("팀 영문명을 입력하세요 (lotte, doosan 등): ");
        String teamInputName = scanner.nextLine().toUpperCase();

        matchedTeam = Team.valueOf(teamInputName);

        System.out.print("(왼손, 오른손, 양손)중 투수의 주손을 입력하세요");
        String handInputType = scanner.nextLine();
        matchedHandType = HandType.findType(handInputType);

        List<PitchType> pitchTypes= inputPitchType();


        System.out.print("주무기 구종을 입력하세요(curveball,fastball,slider 등) :");
        String inputPitchType = scanner.nextLine();
        matchedStrongPitchType = PitchType.findPitchTypeByKorean(inputPitchType);

        // 투수 스탯 입력
        System.out.print("투수의 era를 입력하세요(0~100 사이) : ");
        double inputEra = scanner.nextDouble();
        scanner.nextLine();

        return new PlayerCreateFactory(new Pitcher(pitcherName, matchedTeam, matchedHandType, pitchTypes, matchedStrongPitchType), new PitcherStat(inputEra));

    }

    private static List<PitchType> inputPitchType() {
        List<PitchType> pitchTypes = new ArrayList<>();
        System.out.print("구종을 입력하세요(종료는 exit) :  ");
        while (true) {
            String inputPitchType = scanner.nextLine();
            if (inputPitchType.equals("exit")) {
                break;
            }
            PitchType foundPitchType = PitchType.findPitchTypeByKorean(inputPitchType);
            if (foundPitchType != null) {
                pitchTypes.add(foundPitchType);
            } else {
                System.out.println("잘못된 구종 입력입니다. 다시 입력해주세요.");
            }
        }
        return pitchTypes;
    }
}
