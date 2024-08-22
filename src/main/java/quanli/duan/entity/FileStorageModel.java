package quanli.duan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import quanli.duan.repository.FileStorageRepository;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = FileStorageRepository.TABLE)
public class FileStorageModel {
    @Id
    @Column(nullable = false)
    private String fileId;
    private String fileDirectory;
    private String rawFileName;
    private String fileName;
    private String fileExtension;
    private String description;
    private String fileType;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
