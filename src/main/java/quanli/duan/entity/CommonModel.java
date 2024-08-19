package quanli.duan.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
 abstract  class CommonModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = true;

    @Column(name = "create_date", nullable = true)
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = true)
    private LocalDateTime updateDate;

    @Column(name = "update_by", nullable = true)
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

