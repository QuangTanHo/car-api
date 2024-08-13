package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.ShipmentRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ShipmentRepository.TABLE)
public class ShipmentModel {

    @Id
    @Column(nullable = false)
    private String shipmentId;
    private String orderId;
    private LocalDateTime shipmentDate;
    private BigDecimal shipmentCost;
}
