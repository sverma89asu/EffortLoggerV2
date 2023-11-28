This backend service communicates with the backend.

To use it, use `Backend.getInstance()`.

`tu14.api.Backend.Backend` requires an `tu14.api.IAuthenticationService`.
There is a default version provided; to get a `Backend` instance with custom 
Authentication, call `Backend.getInstance(IAuthenticationService)`.

At this time, `IAuthenticationService` is provided by 
the following classes:
- `tu14.api.PlaintextBearerAuthentication`
  - `PlaintextBearerAuthentication(String token)`
  - `PlaintextBearerAuthentication(String username, String password)`

To send a request, use `Backend.send(APIRequest, Class<T>)`.
The request reply will be cast to `tu14.api.RawData<T>`. 
To get data from `RawData<T>`, use the `cast()` or `castSafe()` method.

`Backend.send()` returns `CompletableFuture<<RawData<T>>`.

`APIRequest` has four implementers:
- `CreateRequest`
- `DeleteRequest`
- `GetRequest`
- `UpdateRequest`

All implementers implement the following:
- A default constructor.
- `id(long)` sets the row id. This is required for deletes and updates.
- `table(String)` sets the table. This is required for all requests. 
Set values for known tables can be found in `tu14.api.tables.Tables`
- `body(IRawImplementer<T>)` sets the request body. This is required 
for creates and updates, and forbidden for gets and deletes.

All classes that interact with this service as data must implement 
`IRawImplementer<T>`, where `T` is the class itself. An example may 
be found in `src/test/java/User.java`. All public or protected 
getters and fields will be serialized. If they cannot be serialized 
or should not be serialized, use the annotation `@JsonIgnore`. In all cases, 
the `id` field **must** be ignored.