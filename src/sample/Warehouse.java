package sample;

public class Warehouse {
    private String managerName;
    private String address;
    private String mark;

    public Warehouse(String managerName, String address, String mark) {
        this.managerName = managerName;
        this.address = address;
        this.mark = mark;
    }

    public Warehouse() {
        managerName=null;
        address=null;
        mark=null;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
