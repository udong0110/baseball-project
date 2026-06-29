package baseball.domain.player;

public enum     Team {
    LOTTE("롯데 자이언츠","LOTTE"),
    HANHWA("한화 이글스","HANWHA"),
    DOOSAN("두산 베어스","DOOSAN"),
    KIWOOM("키움 히어로즈","KIWOOM"),
    LG("엘지 트윈스","LG"),
    SAMSUNG("삼성 라이온즈","SAMSUNG"),
    KT("케이티 위즈","KT"),
    SSG("SSG 랜더스","SSG"),
    NC("엔씨 다이노스","NC"),
    KIA("기아 타이거즈","KIA");

    private final String teamKoreanName;
    private final String teamEnglishName;


    Team(String teamName, String teamEnglishName) {
        this.teamKoreanName = teamName;
        this.teamEnglishName = teamEnglishName;
    }

    public String getTeamKoreanName() {
        return teamKoreanName;
    }

    public static Team findTeamByKorean(String inputTeamName) {
        for (Team team : Team.values()) {
            if (team.teamKoreanName.equals(inputTeamName)|| team.teamEnglishName.equalsIgnoreCase(inputTeamName)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return teamKoreanName;
    }
}
