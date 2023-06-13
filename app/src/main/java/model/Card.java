package model;

public class Card {
    private int id;
    private String name;
    private String image;
    private int power;
    private boolean special;
    public Card(int id, String name, String image, int power, boolean special) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.power = power;
        this.special = special;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }
}


