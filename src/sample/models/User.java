package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class User {
    private Integer id = null;


    public enum Group {ist, asu, evm, ibb}
    private Group group;
    private String title;
    private int year;
    private int mark;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



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

    public String getYearString() {
        return String.valueOf(year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMark() {
        return mark;
    }

    public String getMarkString() {
        return String.valueOf(mark); }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setMark(int mark) {
        this.mark = mark;
    }

    public String date(){
        Date date = new Date();
        String currentDate = sdf.format(date);
        return currentDate;
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
