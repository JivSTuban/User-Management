package com.csit321.tuban.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class MyUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String phoneNumber;

    private String profilePicture;

    private Boolean isDeleted;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;
}
