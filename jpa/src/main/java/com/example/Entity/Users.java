package com.example.Entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.cfg.context.Cascadable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Users implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name ="username")
    private String username;
    @NotBlank
    private String email;
    @Size(max =  120)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns =  @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    private boolean isEnabled;
    @JsonIgnore //chú thích cấp thuộc tính (field) này cũng được sử dụng để loai trừ field trong quá trình Serialization and Deserialization dữ liệu JSON.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Post> post;
    @Column(name= "create_at")
    @CreationTimestamp
    private Date createat;
    @Column(name ="update_at")
    @UpdateTimestamp
    private Date updateat;
    public Users(String name, String email,String pass) {
        
        this.username = name;
        this.email = email;
        this.password = pass;

    }  

}
