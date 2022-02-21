package ru.kulikov.saula.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.kulikov.saula.sched.DateHandler;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_schedule")
public class Schedule {

    @Column(name = "time")
    @JsonDeserialize(using = DateHandler.class)
    private Date time;

    @Column(name = "room_id")
    private int roomId;

    @Id
    @Column(name = "pres_id")
    private int presentationId;

    public Schedule() { }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int room_id) {
        this.roomId = room_id;
    }

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int pres_id) {
        this.presentationId = pres_id;
    }

}
