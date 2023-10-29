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

    public RawData(JsonNode node, Class<T> tClass) {
        data = new ArrayList<>();
        node.iterator().forEachRemaining(data::add);
        this.tClass = tClass;

        cachedConstructor = null;
    }

    public List<T> cast() throws APITransformException {
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
        } catch (APITransformException e) {
            throw e;
        }




    }

}