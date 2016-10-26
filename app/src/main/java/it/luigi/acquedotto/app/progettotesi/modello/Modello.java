package it.luigi.acquedotto.app.progettotesi.modello;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luigi on 09/10/2016.
 */

public class Modello {
    private Map<String, Object> cache = new HashMap<String, Object>();

    public void saveBean(String key, Object o){
        this.cache.put(key, o);
    }

    public Object getBean(String key){
        return this.cache.get(key);
    }
}
