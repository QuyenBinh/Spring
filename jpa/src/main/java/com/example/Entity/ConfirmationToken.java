package com.example.Entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@javax.persistence.Table(name = "confirmationtoken")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long id;
    @Column(name = "confirmation_token")
    private String confirmationtoken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  
    private Users user;

    public ConfirmationToken(Users user)  {

        this.user = user;
        this.createdDate = new Date();
        this.confirmationtoken = UUID.randomUUID().toString();

    }

}
