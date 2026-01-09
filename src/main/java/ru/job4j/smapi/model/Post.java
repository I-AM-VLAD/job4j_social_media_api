package ru.job4j.smapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
@Schema(description = "Post Model Information")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Owner of post", example = "Andrew")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "Title", example = "My news")
    private String title;

    @Schema(description = "Content", example = "Today was very busy day, but...")
    private String content;

    @Schema(description = "Link to the image", example = "https://www.google.com/search?sca_esv=2e0ce5bd22b7d86a&sxsrf")
    @Column(name = "image_url")
    private String imageUrl;

    @Schema(description = "Date of creating", example = "2026-01-09T15:15:15")
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Schema(description = "Date of updating", example = "2026-02-09T15:15:15")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
