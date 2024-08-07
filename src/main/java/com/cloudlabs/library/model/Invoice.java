package com.cloudlabs.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "final_date", nullable = false)
    private LocalDate finalDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private InvoiceStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
