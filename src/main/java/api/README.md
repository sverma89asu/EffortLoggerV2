This backend service communicates with the backend.

To use it, instantiate an instance of `api.Backend.Backend`.

`api.Backend.Backend` requires an `api.IAuthenticationService`. At this time, `IAuthenticationService` is provided by 
the following classes:
- `api.PlaintextBearerAuthentication`
  - `PlaintextBearerAuthentication(String token)`
  - `PlaintextBearerAuthentication(String username, String password)`

To send a request, use `Backend.send(APIRequest, Class<T>)`.
The request reply will be cast to `api.RawData<T>`. 
To get data from `RawData<T>`, use the `cast()` method.

`Backend.send()` returns `CompletableFuture<Optional<RawData<T>>>`.

`APIRequest` has four implementers:
- `CreateRequest`
- `DeleteRequest`
- `GetRequest`
- `UpdateRequest`

All implementers implement the following:
- `id(long)` sets the row id. This is required for deletes and updates.
- `table(String)` sets the table. This is required for all requests. Set values for known tables can be found in `api.tables.Tables`
- `body(IRawImplementer<T>)` sets the request body. This is required for creates and updates, and forbidden for gets and deletes.

All classes that interact with this service as data must implement `IRawImplementer<T>`, where `T` is the class itself.
An example may be found in `src/test/java/User.java`. All public or protected getters and fields will be serialized. 
If they cannot be serialized or should not be serialized, use the annotation @JsonIgnore.
