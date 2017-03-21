package by.bsu.rfct.fclearn.service.dto;

import java.util.Objects;

/**
 * Abstraction for Data Transfer Object
 *
 * @param <K> id type
 */
public abstract class AbstractDTO<K> {

    protected K id;

    public AbstractDTO() {

    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractDTO<?> that = (AbstractDTO<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
