package sample;

public class AnnualReport {
    private int year;
    private int profit;
    private int cost;

    public AnnualReport(int year, int profit, int cost) {
        this.year = year;
        this.profit = profit;
        this.cost = cost;
    }

    public AnnualReport() {
        year=2020;
        profit=0;
        cost=0;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
