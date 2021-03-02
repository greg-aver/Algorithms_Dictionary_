    @Test
    public void hashFunTest() {
        int size = 704_999; 
        NativeDictionary<Integer> dictionary = new NativeDictionary<Integer>(size, Integer.class);
        for (int i = 0; i < size; i++) {
            String key = String.valueOf((int) (1_000_000_000 * Math.random()));
            Integer value = i;
            int code = dictionary.hashCode(key);
            int fun = dictionary.hashFun(key);

            assertThat(code)
                    .as("error hashCode! \n hashCode = %d, value = %s, hashFun = %d", code, key, fun)
                    .matches(x -> x < size && x >= 0);
            assertThat(fun).as("error hashFun! \n hashCode = %d, value = %s, hashFun = %d", code, key, fun)
                    .matches(x -> x < size && x >= -1);
            
            dictionary.put(key, value);
        }
    }
