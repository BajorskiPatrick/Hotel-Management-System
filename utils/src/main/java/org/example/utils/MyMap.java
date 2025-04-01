package org.example.utils;

import java.util.*;

/**
 * Klasa MyMap implementująca interfejs Map
 *
 * @param <K> typ kluczy
 * @param <V> typ wartości
 */
public class MyMap<K, V> implements Map<K, V> {
    private final ArrayList<K> keys;
    private final ArrayList<V> values;
    int size;

    /**
     * Konstruktor domyślny inicjalizujący atrybuty
     */
    public MyMap() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        size = 0;
    }

    /**
     * Metoda sprawdzająca, czy podany klucz znajduje się w mapie
     *
     * @param key klucz, którego obecność w mapie sprawdzamy
     * @return true - jeśli klucz jest w mapie, false - jeśli go nie ma
     */
    @Override
    public boolean contains(K key) {
        return keys.contains(key);
    }

    /**
     * Metoda sprawdzająca, czy podana wartość znajduje się w mapie
     *
     * @param value wartość, której obecność w mapie sprawdzamy
     * @return true - jeśli wartość znajduje się w mapie, false - jeśli nie
     */
    public boolean containsValue(V value) {
        return values.contains(value);
    }

    /**
     * Metoda zwracająca wartość dla podanego klucza
     *
     * @param key klucz, dla którego chcemy zwrócić odpowiadającą mu wartość
     * @return wartość odpowiadająca podanemu kluczowi, a jeśli klucza nie ma w mapie, to null
     */
    @Override
    public V get(K key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }

    /**
     * Metoda dodająca parę ustawiająca wartość klucza key na wartość value.
     * Jeśli danego klucza nie ma w mapie, to jest dodawany z podaną wartością.
     * Natomiast, gdy podany klucz jest już w mapie, to jego wartość jest zastępowana przez wartość podaną
     *
     * @param key   klucz, z którym chcemy powiązać podaną wartość
     * @param value wartość, którą chcemy powiązać z podanym kluczem
     * @return true - jeśli utworzono nową parę lub zmieniono wartość, false - klucz lub wartość były null
     */
    @Override
    public boolean put(K key, V value) {
        if (key == null || value == null) return false;
        if (this.contains(key)) {
            values.set(keys.indexOf(key), value);
        } else {
            keys.add(key);
            values.add(value);
            size += 1;
        }
        return true;
    }

    /**
     * Metoda usuwająca parę [klucz, wartość] dla podanego klucza
     *
     * @param key klucz, którego mapowanie chcemy usunąć
     * @return true - jeśli uda się usunąć klucz, false - gdy nie uda się go usunąć (nie było go w mapie albo był null)
     */
    @Override
    public boolean remove(K key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            values.remove(index);
            keys.remove(index);
            size -= 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metoda zwracająca listę zawierającą wszystkie klucze tej mapy
     *
     * @return utworzona lista jako java.util.List
     */
    @Override
    public List<K> keys() {
        return new ArrayList<>(keys);
    }

    /**
     * Metoda zwracająca rozmiar mapy (ilość par [klucz, wartość])
     *
     * @return rozmiar mapy
     */
    public int size() {
        return size;
    }

    /**
     * Metoda informująca, czy mapa jest pusta
     *
     * @return true - jeśli mapa jest pusta, false - jeśli nie jest
     */
    public boolean isEmpty() {
        return size == 0;
    }
}