package pl.com.tutti.tuttiserver.service.structures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<U, V>
{
    public final U key;
    public final V value;

    public Pair(U key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!key.equals(pair.key))
            return false;
        return value.equals(pair.value);
    }

    @Override
    public int hashCode()
    {
        return 31 * key.hashCode() + value.hashCode();
    }

    @Override
    public String toString()
    {
        return "(" + key + ", " + value + ")";
    }

    public static <U, V> Pair <U, V> of(U a, V b)
    {
        return new Pair<>(a, b);
    }
}
