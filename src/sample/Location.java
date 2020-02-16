package sample;

public class Location {
    private int section;
    private int position;

    public Location(int section, int position) throws IllegalLocationException {
        this.setSection(section);
        this.setPosition(position);
    }

    public Location() {
        section=10;
        position=1;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) throws IllegalLocationException {
        if(section<10 || section%10!=0)throw new IllegalLocationException("Wrong section");
        this.section = section;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) throws IllegalLocationException {
        if(position<1) throw new IllegalLocationException("Wrong position");
        this.position = position;
    }
}
