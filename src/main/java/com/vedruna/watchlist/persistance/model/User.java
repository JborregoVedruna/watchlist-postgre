package com.vedruna.watchlist.persistance.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Integer userId;
    
    @NotBlank(message = "Username is required and must not be blank")
    @Size(min=3, max = 20, message = "Username must be between 3 and 20 characters long")
    @Column(name = "username", nullable = false, unique = true)
    String username;

    @NotBlank(message = "Password is required and must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false, columnDefinition = "char")
    String password;
    
    @NotBlank(message = "Email is required and must not be blank")
    @Size(max = 90, message = "Email must be at most 90 characters long")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Roles_rol_id", referencedColumnName = "rol_id")
    Rol userRol;

    @OneToOne(mappedBy = "dniOwner", orphanRemoval = true, 
              cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    DNICard document;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name="users_haswatched_films", 
               joinColumns={@JoinColumn(name="Users_user_id", referencedColumnName = "user_id")}, 
               inverseJoinColumns={@JoinColumn(name="Films_film_id", referencedColumnName = "film_id")})
    List<Film> filmsWatchedByThisUser;
}
