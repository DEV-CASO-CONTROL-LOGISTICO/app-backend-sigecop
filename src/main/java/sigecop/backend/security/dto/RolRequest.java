package sigecop.backend.security.dto;

import sigecop.backend.utils.generic.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolRequest extends RequestBase {
    private String codigo;
    private String nombre;
    private List<Integer> paginas;

    public List<Integer> getPaginas(){
        if(paginas==null){
            paginas=new ArrayList<>();
        }
        return paginas;
    }

}
