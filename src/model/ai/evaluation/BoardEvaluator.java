package model.ai.evaluation;

public abstract class BoardEvaluator implements BoardEvaluatorInterface {
    protected double weight;

    public BoardEvaluator() {
        this.weight = 1.0;
    }

    public BoardEvaluator(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
