package com.vedruna.watchlist.persistance.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false)
    Integer filmId;

    @NotBlank(message = "Title is required and must not be blank")
    @Size(max = 45, message = "Title must be at most 45 characters long")
    @Column(name = "title", nullable = false, unique = true)
    String title;

    @NotNull(message = "Release date is required and must not be blank")
    @PastOrPresent(message = "Release date must be in the past or present")
    @Column(name = "release_date", nullable = false)
    Date releaseDate;

    @ManyToMany(mappedBy="filmsWatchedByThisUser", 
                cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<User> usersWatchedThisFilm;
}
