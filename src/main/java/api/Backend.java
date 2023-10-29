package api;

import api.request.APIRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Backend {

    private IAuthenticationService auth;
    private HttpRequest.Builder requestBuilder;
    private HttpClient httpClient;

    private final String BASE_URL = "https://cse360.flerp.dev";

    public Backend(IAuthenticationService authenticationService) {
        auth = authenticationService;
        httpClient = HttpClient.newHttpClient();
    }

    protected <K extends IRawImplementer<K>> CompletableFuture<HttpResponse<RawData<K>>> makeRequest(Class<K> kClass) {

        auth.includeAuthenticationLayer(requestBuilder);
        HttpRequest request = requestBuilder.build();

        System.out.print(request);
        System.out.print(" ");
        System.out.println(request.headers().toString());

        return httpClient.sendAsync(request, new RawBodyHandler<K>(kClass));
    }

    public <K extends IRawImplementer<K>> CompletableFuture<Optional<RawData<K>>> send(APIRequest request, Class<K> kClass) {
        requestBuilder = HttpRequest.newBuilder();

        request.includeRequestDataLayer(requestBuilder);
        try {
            requestBuilder.uri(new URI(BASE_URL + request.getEndpoint()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<HttpResponse<RawData<K>>> response = makeRequest(kClass);

        // TODO make send return a union type that indicates type of error on non-successful completion
        return response.thenApplyAsync((resp) -> switch (resp.statusCode()) {
            case 200 -> resp.body();
            default -> null;
        }).thenApplyAsync((data) -> Optional.ofNullable(data));
    }
}

class RawBodyHandler<T extends IRawImplementer<T>> implements  HttpResponse.BodyHandler<RawData<T>> {

    private final Class<T> tClass;
    private static ObjectMapper mapper = new ObjectMapper();

    RawBodyHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public HttpResponse.BodySubscriber<RawData<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(upstream, (String body) -> {
            try {
                return new RawData<>(mapper.readTree(body), tClass);
            } catch (IOException e) {
                return null;
            }
        });
    }
}
