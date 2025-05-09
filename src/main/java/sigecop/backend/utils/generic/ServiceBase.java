package sigecop.backend.utils.generic;

import sigecop.backend.utils.ObjectResponse;

import java.util.List;

public interface ServiceBase <T,F>{

    public List<T> list(F filter);

    public ObjectResponse<T> find(F filter);

    public ObjectResponse<T> save(F filter);

    public ObjectResponse<T> delete(F filter);

}
