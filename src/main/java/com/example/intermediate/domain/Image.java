package com.example.intermediate.domain;

import com.example.intermediate.validator.URLvalidator;
import com.example.intermediate.validator.ValidateObject;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postImage")
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgURL;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    public Image(Post post, String imageUrl){
        URLvalidator.isValidURL(imageUrl);
        ValidateObject.postValidate(post);
        this.post=post;
        this.imgURL = imageUrl;
    }

}
