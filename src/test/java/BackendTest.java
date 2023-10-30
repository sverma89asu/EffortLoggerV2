import org.junit.jupiter.api.*;
import tu14.api.Backend;
import tu14.api.exceptions.APITransformException;
import tu14.api.request.CreateRequest;
import tu14.api.request.DeleteRequest;
import tu14.api.request.GetRequest;
import tu14.api.request.UpdateRequest;
import tu14.api.tables.Tables;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BackendTest {

    static int id;
    static int count;

    @BeforeAll
    void build() {
        id = new Random().nextInt(0, 100_000_000);
    }

    @Test
    @Order(0)
    void TestGet() throws ExecutionException, InterruptedException, APITransformException {
        List<User> users =
                Backend.getInstance().send(new GetRequest().table(Tables.Users), User.class).get().cast();

        for (User user : users) {
            System.out.println(user.toString());
        }

        count = users.size();
    }

    @Test
    @Order(1)
    void TestCreate() throws ExecutionException, InterruptedException {
        User us = new User("test" + id, "pwd");

        Backend.getInstance()
                .send(new CreateRequest().table(Tables.Users).id(id).body(us), User.class)
                .get().castSafe();

        List<User> users =
                Backend.getInstance().send(new GetRequest().table(Tables.Users), User.class).get().castSafe();

        Assertions.assertEquals(count + 1, users.size());
    }

    @Test
    @Order(2)
    void TestUpdate() throws ExecutionException, InterruptedException, APITransformException {
        User user = Backend.getInstance()
                .send(new GetRequest().table(Tables.Users).id(id), User.class).get().castSafe().get(0);

        user.setPassword("H");

        Backend.getInstance()
                .send(new UpdateRequest().table(Tables.Users).id(id).body(user), User.class).get().cast();

        user = Backend.getInstance()
                .send(new GetRequest().table(Tables.Users).id(id), User.class).get().castSafe().get(0);

        Assertions.assertEquals("H", user.password);
    }

    @Test
    @Order(3)
    void TestDelete() throws ExecutionException, InterruptedException, APITransformException {
        Backend.getInstance().send(new DeleteRequest().table(Tables.Users).id(id), User.class).get().cast();

        List<User> users = Backend.getInstance()
                .send(new GetRequest().table(Tables.Users), User.class).get().castSafe();

        Assertions.assertEquals(count, users.size());
    }

    @AfterAll
    void tearDown() {
        Backend backend = Backend.getInstance();
        backend.send(new DeleteRequest().table(Tables.Users).id(id), User.class);
    }


}
