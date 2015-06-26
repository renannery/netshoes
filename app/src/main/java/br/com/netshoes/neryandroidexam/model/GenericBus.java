package br.com.netshoes.neryandroidexam.model;


import java.util.ArrayList;

public class GenericBus<T> {

    public static final int SHOTS_LIST = 0;
    public static final int SHOTS_DETAILS = 1;

    private int key;
    private ArrayList<T> objects;
    private Object object;


    public GenericBus(int key) {
        this.key = key;
        this.object = null;
    }

    public GenericBus(int key, ArrayList<T> objects) {
        this.key = key;
        this.objects = objects;
    }

    public GenericBus(int key, Object object) {
        this.key = key;
        this.object = object;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<T> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<T> objects) {
        this.objects = objects;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}