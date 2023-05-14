package com.springboot.ecommerce.model.userMeta;


import com.springboot.ecommerce.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserMeta implements Serializable {
    @SequenceGenerator(
            name = "user_meta_sequence",
            sequenceName = "user_meta_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            generator = "user_meta_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDate birthday;
    private String mobile;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserMetaGender userMetaGender;

    private String address;

    @OneToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;
}
