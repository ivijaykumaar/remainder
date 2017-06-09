package com.atom.remainder;

/**
 * Created by Nelson Andrew on 06-02-2017.
 */

public class ListOfData {

    private String Id;
    private String Name;
    private String Date;
    private String Time;
    private String Description;
    private String Location;

    public ListOfData(String id,String name) {

        Id = id;
        Name = name;
//        Date = date;
//        Time = time;
//        Description = description;
//        Location = location;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
