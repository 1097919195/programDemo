package bean;

/**
 * Created by asus on 2018/4/17.
 */

public class ChildBean {
    private String childStr;
    private String station,buscode,timeStart,timeEnd;

    public ChildBean(String childStr, String station, String buscode, String timeStart, String timeEnd) {
        this.childStr = childStr;
        this.station = station;
        this.buscode = buscode;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getChildStr() {
        return childStr;
    }

    public void setChildStr(String childStr) {
        this.childStr = childStr;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getBuscode() {
        return buscode;
    }

    public void setBuscode(String buscode) {
        this.buscode = buscode;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
