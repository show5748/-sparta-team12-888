package com.example.intermediate.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postImage")
public class ImageMapper extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;


}
