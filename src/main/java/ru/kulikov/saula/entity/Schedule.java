package ru.kulikov.saula.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.kulikov.saula.sched.DateHandler;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_schedule")
public class Schedule {

    @Column(name = "start_time")
    @JsonDeserialize(using = DateHandler.class)
    private Date startTime;

    @Column(name = "end_time")
    @JsonDeserialize(using = DateHandler.class)
    private Date endTime;

    @Column(name = "room_id")
    private int roomId;

    @Id
    @Column(name = "pres_id")
    private int presentationId;

    @OneToOne
    @JoinColumn(name = "pres_id")
    private Presentation presentation;

    @ManyToOne
    @JoinColumn(name="room_id", insertable = false, updatable = false)
    private Room room;

    public Room getRoom() {
        return room;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int presentationId) {
        this.presentationId = presentationId;
    }

}
