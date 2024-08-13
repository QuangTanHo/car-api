package quanli.duan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.ShowRoomModel;

import java.util.List;

@Repository
public interface ShowRoomRepository  extends JpaRepository<ShowRoomModel, Integer> {
        String TABLE ="show_room";
        ShowRoomModel findByName(String name);
        Page<ShowRoomModel> findByBrandId(@Param("brandId") Integer brandId, Pageable pageable);
        Page<ShowRoomModel> findAll(Pageable pageable);

}
