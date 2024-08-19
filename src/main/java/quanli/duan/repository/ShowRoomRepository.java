package quanli.duan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.ShowRoomModel;


@Repository
public interface ShowRoomRepository  extends JpaRepository<ShowRoomModel, Integer> {
        String TABLE ="show_room";
        ShowRoomModel findByName(String name);
        Page<ShowRoomModel> findByBrandIdAndIsDelete(Integer brandId,Boolean isDelete, Pageable pageable);
        Page<ShowRoomModel> findAllByIsDelete(Boolean isDelete, Pageable pageable);
        Page<ShowRoomModel> findAllByOutstandingAndIsDelete(String outstanding,Boolean isDelete, Pageable pageable);

}
