package ccl.securitybackend.utils.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

public abstract class DtoGeneric<E,D> {

    public static <E, D> List<D> fromEntities(List<E> entities, Class<D> dtoClass) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(entity -> fromEntity(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public static <E, D> D fromEntity(E entity, Class<D> dtoClass) {
        try {
            if (entity == null) {
                return dtoClass.getDeclaredConstructor().newInstance();
            }

            D dto = dtoClass.getDeclaredConstructor().newInstance();
            DtoGeneric<E, D> dtoGeneric = (DtoGeneric<E, D>) dtoClass.getDeclaredConstructor().newInstance();
            return dtoGeneric.mapEntityToDto(entity, dto);
        } catch (Exception e) {
            throw new RuntimeException("Error creando el DTO", e);
        }
    }

    protected abstract D mapEntityToDto(E entity, D dto);
}
