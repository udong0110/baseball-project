package baseball.domain.stat;

public class HitterStat extends PlayerStat {

    private double ops;
    private double avg;

    public HitterStat(double ops, double avg) {
        if (avg < 0.0 || avg > 1.0) {
            throw new IllegalArgumentException("타율 수치 오류(0.0 ~ 1.0 사이여야 함): " + avg);
        }
        if (ops < 0.0 || ops > 2.0) {
            throw new IllegalArgumentException("OPS 수치 오류(음수 불가): " + ops);
        }

        this.ops = ops;
        this.avg = avg;

    }

    public double getOps() {
        return ops;
    }

    public double getAvg() {
        return avg;
    }
}
