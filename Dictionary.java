import java.lang.reflect.Array;
import java.util.*;

public class NativeDictionary<T> {
    public int size;
    public String [] slots;
    public T [] values;
    private final int step = 3;

    public NativeDictionary(int sz, Class clazz)
    {
        size = sz;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
    }

    public int hashCode(String key)
    {
        byte[] bytes = key.getBytes();
        int sum = IntStream.range(0, bytes.length).map(x -> bytes[x]).sum();
        return sum % getSize();
    }

    public int hashFun(String key)
    {
        int slot = hashCode(key);

        for (int i=0; i< step; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null)) {
                    return slot;
                }
                slot += step;
            }
            slot = slot - getSize();
        }
        return -1;
    }

    public boolean is_key(String key) {
        int slot = hashCode(key);

        for (int i=0; i< step; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null) {
                    return false;
                }
                if (key.equals(getSlots()[slot])) {
                    return true;
                }
                slot += step;
            }
            slot = slot - getSize();
        }
        return false;
    }

    public void put(String key, T value)
    {
        int slot = hashFun(key);

        if (slot != -1) {
            getSlots()[slot] = key;
            getValues()[slot] = value;
        }
    }

    public int find(String key)
    {
        int slot = hashCode(key);

        for (int i=0; i< step; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null) {
                    return -1;
                }
                if (key.equals(getSlots()[slot])) {
                    return slot;
                }
                slot += step;
            }
            slot = slot - getSize();
        }
        return -1;
    }

    public T get(String key)
    {
        int slot = hashCode(key);

        for (int i=0; i< step; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null) {
                    return null;
                }
                if (key.equals(getSlots()[slot])) {
                    return getValues()[slot];
                }
                slot += step;
            }
            slot = slot - getSize();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NativeDictionary hashTable = (NativeDictionary) o;

        if (size != hashTable.size) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(slots, hashTable.slots) && Arrays.equals(values, hashTable.values);
    }

    public int getSize() {
        return size;
    }

    public String[] getSlots() {
        return slots;
    }

    public T[] getValues() {
        return values;
    }
}
