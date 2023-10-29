import api.Backend;
import api.PlaintextBearerAuthentication;
import api.exceptions.APITransformException;
import api.request.CreateRequest;
import api.request.DeleteRequest;
import api.request.GetRequest;
import api.request.UpdateRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BackendTest {

    boolean failed = true;

    static int id = 14;

    @Test
    void TestComplete() throws ExecutionException, InterruptedException, APITransformException {

        Backend backend = new Backend(new PlaintextBearerAuthentication("dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5"));

        User us = new User("test3", "pwd");

        List<User> users = backend.send(new GetRequest().table("user"), User.class).get().get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        int count = users.size();
        System.out.println(count);

        backend.send(new CreateRequest().table("user").id(id).body(us), User.class).get();

        users = backend.send(new GetRequest().table("user"), User.class).get().get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        assertEquals(count + 1, users.size());

        us.setPassword("H");

        backend.send(new UpdateRequest().table("user").id(id).body(us), User.class).get();

        users = backend.send(new GetRequest().table("user"), User.class).get().get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        assertEquals("H", users.get(count).password);

        backend.send(new DeleteRequest().table("user").id(id), User.class).get();

        users = backend.send(new GetRequest().table("user"), User.class).get().get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        assertEquals(count, users.size());

        failed = false;
    }

    @AfterAll
    void tearDown() {
        if (failed) {
            Backend backend = new Backend(new PlaintextBearerAuthentication("dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5"));
            backend.send(new DeleteRequest().table("user").id(id), User.class);
        }
    }


}
