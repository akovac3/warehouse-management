package sample;

public class AnnualReport {
    private int id;
    private int year;
    private int profit;
    private int cost;

    public AnnualReport(int id, int year, int profit, int cost) {
        this.id = id;
        this.year = year;
        this.profit = profit;
        this.cost = cost;
    }

    public AnnualReport() {
        id=0;
        year=2020;
        profit=0;
        cost=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
