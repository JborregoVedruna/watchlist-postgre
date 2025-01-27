package com.vedruna.watchlist.persistance.model;

import com.vedruna.watchlist.validation.DNI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dnis")
public class DNICard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dni_id", nullable = false)
    Integer dniId;

    @NotBlank(message = "DNI number is required and must not be blank")
    @Pattern(regexp = "^\\d{8}[A-Z]$", message = "DNI number must be 8 digits followed by a letter")
    @DNI(message = "DNI number is not valid")
    @Column(name = "number", nullable = false, unique = true)
    String number;

    @NotBlank(message = "Front image is required and must not be blank")
    @Column(name = "front_img", nullable = true)
    String frontImg;

    @NotBlank(message = "Back image is required and must not be blank")
    @Column(name = "back_img", nullable = true)
    String backImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Users_user_id", referencedColumnName = "user_id")
    User dniOwner;
}
