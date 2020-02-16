package sample;

public class Location {
    private int section;
    private int position;

    public Location(int section, int position) {
        this.section = section;
        this.position = position;
    }

    public Location() {
        section=10;
        position=1;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
