package baseball.controller;

import baseball.analyzer.PitchingAnalyzer;
import baseball.domain.player.*;
import baseball.domain.stat.HitterStat;
import baseball.domain.stat.PitcherStat;
import baseball.domain.stat.PlayerStat;
import baseball.exception.InvalidPitchMetricException;
import baseball.exception.PitchTypeNotFound;
import baseball.exception.PlayerNotFound;
import baseball.repository.PlayerRepository;
import baseball.factory.PlayerCreateFactory;


import java.util.Map;
import java.util.Scanner;

public class BaseballController {
    private static final Scanner scanner = new Scanner(System.in);
    private final PlayerRepository repository = new PlayerRepository();


    private void registerPlayer() {
        System.out.println("=========선수 수동 등록=============");
        System.out.println("선수 유형을 선택하세요");
        System.out.println("1. 투수 || 2. 타자");
        int playerType = scanner.nextInt();
        scanner.nextLine();
        // 투수일 때, 외부 클래스에서 선수,선수스탯 생성 후 보관
        if (playerType == 1) {
            PlayerCreateFactory pitcherCreateFactory = PlayerCreateFactory.createPitcher();
            repository.save(pitcherCreateFactory.getPlayer(), pitcherCreateFactory.getPlayerStat());
    
        // 타자일 때, 동일
        } else if (playerType == 2) {
            PlayerCreateFactory hitterCreateFactory = PlayerCreateFactory.createHitter();
            repository.save(hitterCreateFactory.getPlayer(), hitterCreateFactory.getPlayerStat());

        } else {
            System.out.println("투수,타자 외의 잘못된 입력입니다.");
        }
    }


    private void selectPlayer(Player player) {
        PlayerStat playerStat = PlayerRepository.findStatByPlayer(player);
        if (playerStat == null) {
            System.out.println("조회 결과 선택한 선수가 존재하지 않습니다.");
        } else {
            System.out.println(player + " " + playerStat);
        }
    }

    public void printAllPlayer() {
        System.out.println("전체 선수를 조회하는 중...");
        // PlayerRepository에 getMap메서드 이용해서 실질적인 Map에 접근
        Map<Player, PlayerStat> repositoryAll = PlayerRepository.getMap();

        for (Map.Entry<Player, PlayerStat> playerPlusStatEntry : repositoryAll.entrySet()) {
            System.out.print(playerPlusStatEntry.getKey()+ ", " + playerPlusStatEntry.getValue());
            System.out.println();
        }
    }

    public void pitchingAnalyze() {
        Hitter hitter = null;
        Pitcher pitcher = null;
        // 투수,타자 각각 입력을 받는다
        System.out.println("====분석할 타자 정보 입력 단계====");
        Player inputHitter = findPlayerByUserInput();
        PlayerStat statByHitter = PlayerRepository.findStatByPlayer(inputHitter);
        if (inputHitter instanceof Hitter) {
             hitter = (Hitter) inputHitter;
        }

        System.out.println("====분석할 투수 정보 입력 단계====");
        Player inputPitcher = findPlayerByUserInput();
        PlayerStat statByPitcher = PlayerRepository.findStatByPlayer(inputPitcher);
        if (inputPitcher instanceof Pitcher) {
            pitcher = (Pitcher) inputPitcher;
        }
        PitchingAnalyzer pitchingAnalyzer = new PitchingAnalyzer(hitter,(HitterStat) statByHitter,pitcher,(PitcherStat) statByPitcher);
        pitchingAnalyzer.simulationPitching();
        pitchingAnalyzer.solutionDesign();

    }

    public void run() {
        while (true) {
            try {
                System.out.println("1. 선수 등록 || 2. 선수 조회 || 3. 전체 선수 조회  || 4. 시뮬레이션 || 5. 종료 ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        registerPlayer();
                    }
                    case 2 -> {
                        System.out.println("=========선택 선수 조회==========");
                        System.out.println("선수 유형을 선택하세요");
                        System.out.println("1. 투수 || 2. 타자");
                        int playerType = scanner.nextInt();
                        scanner.nextLine();
                        if (playerType == 1) {
                            Player inputPitcher = PlayerCreateFactory.createCommonPitcher();
                            selectPlayer(inputPitcher);

                        } else if (playerType == 2) {
                            Player inputHitter = PlayerCreateFactory.createCommonHitter();
                            selectPlayer(inputHitter);

                        } else {
                            System.out.println("투수,타자 외의 잘못된 입력입니다.");
                        }

                    }
                    case 3 -> {
                        printAllPlayer();
                    }
                    case 4 -> {
                        pitchingAnalyze();
                    }
                    case 5 -> {
                        System.out.println("프로그램 정상종료.");
                        return;
                    }
                    default -> System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                }

            } catch (PlayerNotFound playerNotFound) {
                System.out.println("\n[검색 오류] 선수를 찾을 수 없습니다." + playerNotFound.getMessage());
            } catch (PitchTypeNotFound pitchTypeNotFound) {
                System.out.println("\n[구종 오류] 구종이 올바르지 않습니다. " + pitchTypeNotFound.getMessage());
            } catch (InvalidPitchMetricException metricException) {
                System.out.println("\n[범위 오류] 입력한 숫자가 범위를 벗어났습니다."+ metricException.getMessage());
            } catch (IllegalArgumentException | java.util.InputMismatchException e) {
                System.out.println("\n[입력 오류] " + e.getMessage());
                System.out.println("메인 메뉴로 돌아갑니다. 다시 시도해 주세요.");
                scanner.nextLine();    // nextInt에 숫자 외 입력시 버퍼제거용
            }

        }
    }
    private static Player findPlayerByUserInput() {
        Team matchedTeam = null;
        System.out.print("선수 이름을 입력하세요: ");
        String playerName = scanner.nextLine();

        System.out.print("팀 이름을 입력하세요 (롯데 자이언츠, 한화 이글스, lotte 등): ");
        String InputTeamName = scanner.nextLine();
        matchedTeam = Team.findTeamByKorean(InputTeamName);

        System.out.print("등번호를 입력하세요: ");
        int playerBackNumber = scanner.nextInt();
        scanner.nextLine();

        return PlayerRepository.findPlayer(playerName, matchedTeam, playerBackNumber);
    }
}

