package ru.kulikov.saula.sched;

public class ScheduleDTO {

    private String presentationName;

    private String roomName;

    private String startTime;

    private String endTime;

    public ScheduleDTO(String presentationName, String roomName, String startTime, String endTime) {
        this.presentationName = presentationName;
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
