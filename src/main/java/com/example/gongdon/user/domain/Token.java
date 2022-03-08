package com.example.gongdon.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Token {

    // 토큰 만료 시간
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column
    @Email
    private String receiverEmail;

    @Column
    private boolean verification;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime expirationDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    // 이메일 인증 토큰 생성
    public static Token create(String receiverEmail) {
        Token confirmationToken = new Token();
        confirmationToken.receiverEmail = receiverEmail;
        confirmationToken.expirationDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분 후 만료
        confirmationToken.verification = false;
        return  confirmationToken;
    }

    // 토큰 사용으로 인한 만료
    public void usedToken() {
        this.verification = true;
    }

    // 토큰 유효 기간 만료 여부 검사
    public boolean checkExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }
}
