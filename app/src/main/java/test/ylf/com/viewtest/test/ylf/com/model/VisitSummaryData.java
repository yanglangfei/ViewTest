package test.ylf.com.viewtest.test.ylf.com.model;

public class VisitSummaryData {
    private int id;
    private String name;
    private int visitTotal;
    private int workerTotal;
    private float visitDaily;

    public VisitSummaryData(int id, String name, int visitTotal, int workerTotal, float visitDaily) {
        this.id = id;
        this.name = name;
        this.visitTotal = visitTotal;
        this.workerTotal = workerTotal;
        this.visitDaily = visitDaily;
    }

    public VisitSummaryData(String name, int visitTotal, int workerTotal, float visitDaily) {
        this.name = name;
        this.visitTotal = visitTotal;
        this.workerTotal = workerTotal;
        this.visitDaily = visitDaily;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVisitTotal() {
        return visitTotal;
    }

    public void setVisitTotal(int visitTotal) {
        this.visitTotal = visitTotal;
    }

    public int getWorkerTotal() {
        return workerTotal;
    }

    public void setWorkerTotal(int workerTotal) {
        this.workerTotal = workerTotal;
    }

    public float getVisitDaily() {
        return visitDaily;
    }

    public void setVisitDaily(int visitDaily) {
        this.visitDaily = visitDaily;
    }
}