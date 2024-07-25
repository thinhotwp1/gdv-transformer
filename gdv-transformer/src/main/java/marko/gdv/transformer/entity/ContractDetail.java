package marko.gdv.transformer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ContractDetail {
    private List<PersonDetail> personDetail;
    private CmContractDto cmContractDto;
    private CmContractPriceDto cmContractPriceDto;
}
