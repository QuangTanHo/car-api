package quanli.duan.dto.request.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListFileIdRequest {
    @JsonProperty("file_id_list")
    private List<String> fileIdList;
}
