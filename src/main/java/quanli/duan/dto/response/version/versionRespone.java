package quanli.duan.dto.response.version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class versionRespone {

    private Integer versionId;
    private String versionName;
    private String modelName;
    private String brandName;
    private String segment;
    private BigDecimal installmentPayment;

}
