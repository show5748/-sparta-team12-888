package com.example.intermediate.domain;

import com.example.intermediate.controller.request.PostRequestDto;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @OneToMany(mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostHeart> postHearts;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @OneToOne
  private ImageMapper image;

  public void update(PostRequestDto postRequestDto, ImageMapper image) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
    this.image = image;
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

  public int getHeartNumber(){
    return postHearts.size();
  }

}
