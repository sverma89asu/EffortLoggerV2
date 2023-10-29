import tu14.api.Backend;
import tu14.api.PlaintextBearerAuthentication;
import tu14.api.exceptions.APITransformException;
import tu14.api.request.CreateRequest;
import tu14.api.request.DeleteRequest;
import tu14.api.request.GetRequest;
import tu14.api.request.UpdateRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BackendTest {

    boolean failed = true;

    static int id;

    @BeforeAll
    void build() {
        id = new Random().nextInt(0, 100_000_000);
    }

    @Test
    void TestComplete() throws ExecutionException, InterruptedException, APITransformException {

        Backend backend = Backend.getInstance();

        User us = new User("test" + id, "pwd");

        List<User> users = backend.send(new GetRequest().table("user"), User.class).get().cast();

        for (User user : users) {
            System.out.println(user.toString());
            assertNotEquals(id, user.id);
        }

        int count = users.size();
        System.out.println(count);

        backend.send(new CreateRequest().table("user").id(id).body(us), User.class).get().castSafe();

        users = backend.send(new GetRequest().table("user"), User.class).get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        assertEquals(count + 1, users.size());

        us.setPassword("H");

        backend.send(new UpdateRequest().table("user").id(id).body(us), User.class).get().castSafe();

        users = backend.send(new GetRequest().table("user"), User.class).get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        User theOne = users.stream().filter((u) -> u.id == id).findFirst().get();

        assertEquals("H", theOne.password);

        backend.send(new DeleteRequest().table("user").id(id), User.class).get().castSafe();

        users = backend.send(new GetRequest().table("user"), User.class).get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        assertEquals(count, users.size());

        failed = false;
    }

    @AfterAll
    void tearDown() {
        if (failed) {
            Backend backend = Backend.getInstance();
            backend.send(new DeleteRequest().table("user").id(id), User.class);
        }
    }


}
