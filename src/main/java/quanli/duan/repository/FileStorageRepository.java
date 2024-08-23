package quanli.duan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import quanli.duan.entity.FileStorageModel;

import java.util.List;
import java.util.Optional;

public interface FileStorageRepository extends JpaRepository<FileStorageModel, String> {
    String TABLE = "file_storage";

    @Query(value = "SELECT * FROM " + TABLE +
            " WHERE fileId IN (:fileIds)", nativeQuery = true)
    List<FileStorageModel> findAllByFileId(@Param("fileIds") List<String> fileIds);

    @Query(value = "SELECT * FROM " + TABLE +
            " WHERE fileType IN (:type)", nativeQuery = true)
    List<FileStorageModel> findAllByFileType(@Param("type") List<String> type);

    @Query(value = "SELECT * FROM " + TABLE +
            " WHERE fileName LIKE %:fileName% AND fileType LIKE %:fileType% ", nativeQuery = true)
    Page<FileStorageModel> findByFileNameAndFileType(@Param("fileName") String fileName, @Param("fileType") String fileType, Pageable pageable);

    Boolean existsByRawFileName(String originalFilename);

    FileStorageModel findByRawFileName(String originalFilename);
}
