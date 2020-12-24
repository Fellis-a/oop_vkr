package sample.models;

public class Admin extends User {
    private String fio;
    private String tag;
    private String tagDescription;



    public Admin(){};
    public Admin(String fio, String title, int year, int mark, Group group, String tag, String tagDesc){
        super( title, year, mark, group);
        this.setFio(fio);
        this.setTag(tag);
        this.setTagDescription(tagDesc);
    }

    @Override
    public String getDescription() {


        return String.format("Руководитель: %s, тег: %s, описание тега: %s ", getFio(), getTag(), getTag());
    }


    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
}
