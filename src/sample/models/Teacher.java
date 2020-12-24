package sample.models;

public class Teacher extends User{

    public boolean isEssay;

    public Teacher() {};

    public Teacher( String title, int year, int mark, Group group, boolean isEssay){
        super( title, year, mark, group);
        this.isEssay = isEssay;

    }

    @Override
    public String getDescription() {

        String isEssay = this.isEssay ? "есть" : "нет";

        return String.format(" Наличие реферата: %s ", isEssay);

    }

}
