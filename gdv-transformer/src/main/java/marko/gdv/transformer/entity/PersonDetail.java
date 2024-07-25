package marko.gdv.transformer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class PersonDetail {
    private PmPersonDetailDto pmPersonDetailDto;
    private PmPersonDto pmPersonDto;
    private AdAddressDto adAddressDto;
}
