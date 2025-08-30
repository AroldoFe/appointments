package utils.extensions

import java.util.function.Consumer
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.post(uri: String, body: Any, headers: Consumer<HttpHeaders>? = null): WebTestClient.ResponseSpec {
    var request = this.post()
        .uri(uri)
        .bodyValue(body)
    if (headers != null) {
        request = request.headers(headers)
    }
    return request.exchange()
}

inline fun <reified T> WebTestClient.postBadRequest(
    uri: String,
    body: Any,
    headers: Consumer<HttpHeaders>? = null
): T? =
    this.post(uri, body, headers)
        .expectStatus().isBadRequest
        .expectBody(T::class.java)
        .returnResult()
        .responseBody
