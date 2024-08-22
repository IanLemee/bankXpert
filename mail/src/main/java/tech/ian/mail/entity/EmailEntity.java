package tech.ian.mail.entity;

import jakarta.persistence.*;
import lombok.Data;
import tech.ian.mail.enums.StatusEmail;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "emails")
@Data
public class EmailEntity implements Serializable {
    private static final long serialVersion = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;
    private UUID userID;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;


}
