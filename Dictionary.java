import java.lang.reflect.Array;
import java.unit.*;

class NativeDictionary<T>
{
    public int size;
    public String [] slots;
    public T [] values;
    private final int STEP = 3;

    public NativeDictionary(int sz, Class clazz)
    {
      size = sz;
      slots = new String[size];
      values = (T[]) Array.newInstance(clazz, this.size);
    }

    public int hashFun(String key)
    {
        byte[] bytes = key.getBytes();
        int sum = IntStream.range(0, bytes.length).map(x -> bytes[x]).sum();
        return sum % getSize();
    }

    public int seekSlots(String key)
    {
        int slot = hashFun(key);

        for (int i = 0; i< STEP; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null
                        || key.equals(getSlots()[slot])) {
                    return slot;
                }
                slot += STEP;
            }
            slot = slot - getSize();
        }
        return -1;
    }
    
    public boolean isKey(String key)
    {
        int slot = hashFun(key);

        for (int i = 0; i< STEP; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null) {
                    return false;
                }
                if (key.equals(getSlots()[slot])) {
                    return true;
                }
                slot += STEP;
            }
            slot = slot - getSize();
        }
        return false;
    }

    public void put(String key, T value)
    {
        int slot = seekSlots(key);

        if (slot != -1) {
            getSlots()[slot] = key;
            getValues()[slot] = value;
        }
    }

    public T get(String key)
    {
        int slot = hashFun(key);

        for (int i = 0; i< STEP; i++) {
            while (slot < getSize()) {
                if (getSlots()[slot] == null) {
                    return null;
                }
                if (key.equals(getSlots()[slot])) {
                    return getValues()[slot];
                }
                slot += STEP;
            }
            slot = slot - getSize();
        }
        return null;
    }
}
