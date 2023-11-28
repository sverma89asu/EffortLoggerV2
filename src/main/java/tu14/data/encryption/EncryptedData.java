package tu14.data.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tu14.api.IRawImplementer;
import tu14.api.exceptions.APITransformException;
import tu14.api.exceptions.InvalidDatatypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//TODO: encrypted data has no identifying characteristics...

/**
 * @hidden Not complete yet
 * @param <T>
 */
public class EncryptedData<T extends IEncryptableData<T> & IRawImplementer<T>> implements IRawImplementer<EncryptedData<T>> {

    private static final ObjectMapper mapper = new ObjectMapper();

    protected String data;

    EncryptedData() {}

    public T decrypt(Class<T> tClass, IEncryptionService encryptor) throws APITransformException {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);

            String decrypted = encryptor.decrypt(data);

            JsonNode datum = mapper.readTree(decrypted);

            T obj = constructor.newInstance();
            obj.construct(datum);

            return obj;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new InvalidDatatypeException(e);
        } catch (JsonProcessingException e) {
            throw new APITransformException(e);
        }
    }

    @Override
    public EncryptedData<T> construct(JsonNode data) {
        this.data = data.get("data").asText();
        return this;
    }
}
