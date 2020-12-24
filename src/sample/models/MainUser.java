package sample.models;

public class MainUser extends User{

    private String fio;
    private boolean isEssay;
    private String tag;



    public MainUser() {};

    public MainUser(String fio, String title, int year, int mark, Group group, boolean isEssay, String tag){
        super( title, year, mark, group);
        this.setFio(fio);
        this.setEssay(isEssay);
        this.setTag(tag);
    }



    @Override
    public String getDescription() {

        String isEssay = this.isEssay() ? "есть" : "нет";

        return String.format(" Руководитель: %s, наличие реферата: %s, теги: ", getFio(), isEssay, getTag());
    }


    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public boolean isEssay() {
        return isEssay;
    }

    public void setEssay(boolean essay) {
        isEssay = essay;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
