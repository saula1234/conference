package ru.kulikov.saula.sched;

import java.util.Date;

public class ScheduleDTO {

    private String presentationName;

    private String roomName;

    private String time;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String presentationName, String roomName, String time) {
        this.presentationName = presentationName;
        this.roomName = roomName;
        this.time = time;
    }

    public String getPresentationName() {
        return presentationName;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
