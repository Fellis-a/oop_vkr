package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class User {
    public Integer id = null;

    public enum Group {ist, asu, evm, ibb}
    private Group group;
    private String title;
    private int year;
    private int mark;

    public User(){};

    public User(String title, int year, int mark, Group group) {
        this.setTitle(title);
        this.setYear(year);
        this.setMark(mark);
        this.setGroup(group);

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


    public String getDescription() {
        String typeString = "";
        switch (this.group)
        {
            case ist:
                typeString = "ИСТ";
                break;
            case asu:
                typeString = "АСУ";
                break;
            case evm:
                typeString = "ЭВМ";
                break;
            case ibb:
                typeString = "ИББ";
                break;
        }
        return typeString;
    }

}
