package baseball.factory;

import baseball.domain.player.*;
import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.domain.stat.PlayerStat;

import java.util.*;

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
        // 선수 정보 입력
        Player commonHitter = createCommonHitter();
        // 타자 스탯 입력
        HitterStat hitterStat = inputHitterStat();

        return new PlayerCreateFactory(commonHitter, hitterStat);
    }

    public static  PlayerCreateFactory createPitcher() {

        // 선수 정보 입력
        Player commonPitcher = createCommonPitcher();
        // 투수 스탯 입력
        PitcherStat pitcherStat = inputPitcherStat();

        return new PlayerCreateFactory(commonPitcher, pitcherStat);
    }

    private static Map<PitchType,Pitch> inputPitches() {
        Map<PitchType,Pitch> pitchTypes = new HashMap<>();
        while (true) {
            System.out.print("구종(커브,직구,슬라이더 등)을 입력하세요(종료는 exit): ");
            String inputPitchType = scanner.nextLine();
            if (inputPitchType.equals("exit")) {
                break;
            }

            System.out.print("rpm을 입력하세요: ");
            int inputRpm = scanner.nextInt();

            System.out.print("구속을 입력하세요: ");
            int inputBallSpeed = scanner.nextInt();
            scanner.nextLine();

            PitchType pitchType = PitchType.findPitchTypeByKorean(inputPitchType);
            Pitch pitch = Pitch.createPitch(pitchType, inputRpm,inputBallSpeed);

            pitchTypes.put(pitchType, pitch);

        }
        return pitchTypes;
    }



    public static Hitter createCommonHitter() {
        Team matchedTeam = null;
        HandHitterType matchedHandType = null;
        HitterPosition matchedPosition = null;

        System.out.print("선수 이름을 입력하세요: ");
        String playerName = scanner.nextLine();

        System.out.print("팀 이름을 입력하세요 (롯데 자이언츠, 한화 이글스, lotte 등): ");
        String InputTeamName = scanner.nextLine();
        matchedTeam = Team.findTeamByKorean(InputTeamName);

        System.out.print("등번호를 입력하세요: ");
        int playerBackNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(좌타, 우타, 스위치히터)중 타자의 주손을 입력하세요: ");
        String handInputType = scanner.nextLine();
        matchedHandType = HandHitterType.findType(handInputType);

        System.out.print("선수의 포지션을 입력하세요(1B,SS,SP 등): ");
        String inputPosition = scanner.nextLine().toUpperCase();
        matchedPosition = HitterPosition.getPosition(inputPosition);

        return new Hitter(playerName, matchedTeam, playerBackNumber, matchedHandType, matchedPosition);

    }

    public static Pitcher createCommonPitcher() {
        Team matchedTeam = null;
        HandPitcherType matchedHandType = null;
        PitcherPosition matchedPosition = null;

        System.out.print("선수 이름을 입력하세요: ");
        String playerName = scanner.nextLine();

        System.out.print("팀 이름을 입력하세요 (롯데 자이언츠, 한화 이글스, lotte 등): ");
        String InputTeamName = scanner.nextLine();
        matchedTeam = Team.findTeamByKorean(InputTeamName);

        System.out.print("등번호를 입력하세요: ");
        int playerBackNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(좌투, 우투, 양투)중 투수의 투구 유형을 입력하세요: ");
        String handInputType = scanner.nextLine();
        matchedHandType = HandPitcherType.findType(handInputType);

        System.out.print("투수의 포지션을 입력하세요(SP,선발투수 등): ");
        String inputPosition = scanner.nextLine().toUpperCase();
        matchedPosition = PitcherPosition.getPosition(inputPosition);

        return new Pitcher(playerName, matchedTeam,playerBackNumber, matchedHandType, matchedPosition);

    }



    private static HitterStat inputHitterStat() {

        Zone matchedPowerZone = null;
        Zone matchedWeakZone = null;

        System.out.print("타자의 타율을 입력하세요(0~1.0사이) : ");
        double avg = scanner.nextDouble();

        System.out.print("타자의 OPS를 입력하세요(0~2.0 사이) : ");
        double ops = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("타자의 강점 존을 입력하세요(1~9) :");
        int powerZoneInputNumber = scanner.nextInt();
        matchedPowerZone = Zone.findFromNNumber(powerZoneInputNumber);

        System.out.print("타자의 약점 존을 입력하세요(1~9) :");
        int weakZoneInputNumber = scanner.nextInt();
        matchedWeakZone = Zone.findFromNNumber(weakZoneInputNumber);
        scanner.nextLine();

        return new HitterStat(ops, avg, matchedPowerZone, matchedWeakZone);
    }

    private static PitcherStat inputPitcherStat() {
        System.out.print("투수의 era를 입력하세요(0~100 사이) : ");
        double inputEra = scanner.nextDouble();
        scanner.nextLine();

        Map<PitchType,Pitch> pitches= inputPitches();  // 값을 여러개 받아야하기 떄문에 별도로 메서드 분리


        System.out.print("입력한 구종들 중 주무기 구종을 선택하세요: ");
        String inputStrongPitch = scanner.nextLine();
        PitchType strongPitchType = PitchType.findPitchTypeByKorean(inputStrongPitch);
        Pitch strongPitch = pitches.get(strongPitchType);

        return new PitcherStat(inputEra, pitches, strongPitch);
    }
}
