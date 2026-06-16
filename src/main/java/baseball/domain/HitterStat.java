package baseball.domain;

public class HitterStat extends PlayerStat{

    private double ops;
    private double avg;

    public HitterStat(double ops, double avg) {
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
