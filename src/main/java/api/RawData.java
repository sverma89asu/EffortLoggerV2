package api;

import api.exceptions.APITransformException;
import api.exceptions.InvalidDatatypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public final class RawData<T extends IRawImplementer<T>> {

    private final List<JsonNode> data;
    private final Class<T> tClass;
    private Constructor<T> cachedConstructor;

    public final String errorMessage;
    public final int status;

    public RawData(String errorMessage, int status) {
        data = null;
        tClass = null;
        cachedConstructor = null;
        this.errorMessage = errorMessage;
        this.status = status;

        if (this.status == 500) {
            System.err.println("Error 500 " + errorMessage);
        }
    }

    public RawData(int status, JsonNode node, Class<T> tClass) {
        this.status = status;
        this.errorMessage = "";

        data = new ArrayList<>();
        node.iterator().forEachRemaining(data::add);
        this.tClass = tClass;

        cachedConstructor = null;
    }

    public boolean ok() {
        return this.status == 200;
    }

    public List<T> castSafe() {
        if (!ok()) {
            System.err.println("Error " + status + " " + errorMessage);
            return List.of();
        }

        try {
            return cast();
        } catch (APITransformException e) {
            System.err.println(e.getMessage());
            return List.of();
        }
    }

    public List<T> cast() throws APITransformException {
        if (!ok()) {
            throw new APITransformException("Invalid RawData, not OK");
        }

        try {
            if (cachedConstructor == null) {
                cachedConstructor = tClass.getDeclaredConstructor();
                cachedConstructor.setAccessible(true);
            }

            ArrayList<T> data = new ArrayList<>();

            for (JsonNode datum : this.data) {
                T obj = cachedConstructor.newInstance();
                obj.construct(datum);

                data.add(obj);
            }

            return data;
        } catch (InstantiationException e) {
            throw new InvalidDatatypeException("Invalid class type " + tClass.getName());
        } catch (IllegalAccessException e) {
            throw new InvalidDatatypeException("Nonsense error invalid constructor on type " + tClass.getName());
        } catch (InvocationTargetException e) {
            throw new APITransformException(e);
        } catch (NoSuchMethodException e) {
            throw new InvalidDatatypeException("No empty constructor on type " + tClass.getName());
        }
    }

    // to make up for the removal of Optional<T>, so existing code should still work
    public RawData<T> get() {
        return this;
    }

}