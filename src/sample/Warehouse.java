package sample;

public class Warehouse {
    private int id;
    private String managerName;
    private String address;
    private String mark;
    private String image;

    public Warehouse(int id, String managerName, String address, String mark, String image) {
        this.id = id;
        this.managerName = managerName;
        this.address = address;
        this.mark = mark;
        this.image = image;
    }

    public Warehouse() {
        managerName=null;
        address=null;
        mark=null;
        image=null;
        id=0;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return mark;
    }
}
