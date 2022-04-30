package com.pancarte.architecte.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
/**
 * classe representant un rendez-vous
 * @author Marjorie Pancarte
 * @version 1.0
 */
@Entity
@Table(name = "meeting", schema = "public")
@Getter
@Setter
public class Meeting {
    public Meeting() {
    }

    public Meeting(int id, boolean invitationSended, boolean meetingValidate, Timestamp dateSended, String purpose, boolean closed, Timestamp dateMeeting, String email) {
        this.id= id ;
        this.invitationSended = invitationSended;
        this.meetingValidate = meetingValidate;
        this.dateSended = dateSended;
        this.purpose = purpose;
        this.closed = closed;
        this.dateMeeting = dateMeeting;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_meeting")
    private int id;
    @Column(name = "invitationSended")
    private boolean invitationSended;

    @Column(name = "meetingValidate")
    private boolean meetingValidate;

    @Column(name = "date_sended")
    private Timestamp dateSended;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "closed")
    private boolean closed;
    @Column(name = "date_meeting")
    private Timestamp dateMeeting;

    @Column(name = "email")
    private String email;
}
