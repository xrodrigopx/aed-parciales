package ucu.edu.aed.tda.hash;

import ucu.edu.aed.tda.hash.impl.Hash;

public class HashTest extends AbstractTHashTest {

    @Override
    protected <K, V> THash<K, V> crearHash(int elementosEsperados) {
        return new Hash<>(elementosEsperados);
    }
}
