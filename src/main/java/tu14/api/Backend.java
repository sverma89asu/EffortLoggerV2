package tu14.api;

import tu14.api.request.APIRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class Backend {

    private final IAuthenticationService auth;
    private HttpRequest.Builder requestBuilder;
    private final HttpClient httpClient;

    private static final String BASE_URL = "https://cse360.flerp.dev";

    private static Backend backend = null;

    private Backend(IAuthenticationService service, HttpClient client) {
        this.auth = service;
        this.httpClient = client;
    }

    /**
     * Singleton Factory method to get a backend, providing an auth service
     */
    public static Backend getInstance(IAuthenticationService service) {
        if (backend == null) {
            backend = new Backend(service, HttpClient.newHttpClient());
        }
        return backend;
    }

    /**
     * Singleton Factory method to get a backend
     */
    public static Backend getInstance() {
        return getInstance(new PlaintextBearerAuthentication("dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5"));
    }

    /**
     * @deprecated use {@link #getInstance()}
     */
    @Deprecated
    public Backend(IAuthenticationService authenticationService) {
        this(authenticationService, HttpClient.newHttpClient());
    }

    protected <K extends IRawImplementer<K>> CompletableFuture<HttpResponse<RawData<K>>> makeRequest(Class<K> kClass) {

        auth.includeAuthenticationLayer(requestBuilder);
        HttpRequest request = requestBuilder.build();

        System.out.print(request);
        System.out.print(" ");
        System.out.println(request.headers().toString());

        return httpClient.sendAsync(request, new RawBodyHandler<>(kClass));
    }

    /**
     * Send an APIRequest to the server
     * @param request the APIRequest to send
     * @param kClass the IRawImplementer type to transform resultant data into
     */
    public <K extends IRawImplementer<K>> CompletableFuture<RawData<K>> send(APIRequest request, Class<K> kClass) {
        requestBuilder = HttpRequest.newBuilder();

        request.includeRequestDataLayer(requestBuilder);
        try {
            requestBuilder.uri(new URI(BASE_URL + request.getEndpoint()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<HttpResponse<RawData<K>>> response = makeRequest(kClass);

        //noinspection SwitchStatementWithTooFewBranches
        return response.thenApplyAsync((resp) -> switch (resp.statusCode()) {
            case 200 -> resp.body();
            default -> {
                String error = resp.headers().firstValue("x-error-message").orElse("Internal Server Error");
                yield new RawData<>(URLDecoder.decode(error, StandardCharsets.UTF_8), resp.statusCode());
            }
        });
    }
}

class RawBodyHandler<T extends IRawImplementer<T>> implements  HttpResponse.BodyHandler<RawData<T>> {

    private final Class<T> tClass;
    private static final ObjectMapper mapper = new ObjectMapper();

    RawBodyHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public HttpResponse.BodySubscriber<RawData<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(upstream, (String body) -> {
            try {
                return new RawData<>(responseInfo.statusCode(), mapper.readTree(body), tClass);
            } catch (IOException e) {
                return null;
            }
        });
    }
}
