package quanli.duan.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
 abstract  class CommonModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean isDelete = true;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String updateBy;

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}

