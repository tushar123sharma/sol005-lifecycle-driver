// package com.ibm.nfvodriver.config;
// import static java.util.Objects.isNull;

// import java.io.IOException;
// import java.util.Collection;
// import java.util.Collections;

// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpRequest;
// import org.springframework.http.client.ClientHttpRequestExecution;
// import org.springframework.http.client.ClientHttpRequestInterceptor;
// import org.springframework.http.client.ClientHttpResponse;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
// import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
// import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;

// /**
//  * A {@link org.springframework.web.client.RestTemplate} interceptor which embeds an OAuth Bearer token in the request headers
//  * based on the provided {@link ClientRegistration} instance
//  */
// public class OAuthClientCredentialsRestTemplateInterceptor implements ClientHttpRequestInterceptor {

//     private final OAuth2AuthorizedClientManager manager;
//     private final Authentication principal;
//     private final ClientRegistration clientRegistration;

//     public OAuthClientCredentialsRestTemplateInterceptor(OAuth2AuthorizedClientManager manager, ClientRegistration clientRegistration) {
//         this.manager = manager;
//         this.clientRegistration = clientRegistration;
//         this.principal = createPrincipal();
//     }

//     @Override
//     public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//         OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
//                 .withClientRegistrationId(clientRegistration.getRegistrationId())
//                 .principal(principal)
//                 .build();
//         OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
//         if (isNull(client)) {
//             throw new IllegalStateException("client credentials flow on " + clientRegistration.getRegistrationId() + " failed, client is null");
//         }

//         request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
//         return execution.execute(request, body);
//     }

//     private Authentication createPrincipal() {
//         return new Authentication() {
//             @Override
//             public Collection<? extends GrantedAuthority> getAuthorities() {
//                 return Collections.emptySet();
//             }

//             @Override
//             public Object getCredentials() {
//                 return null;
//             }

//             @Override
//             public Object getDetails() {
//                 return null;
//             }

//             @Override
//             public Object getPrincipal() {
//                 return this;
//             }

//             @Override
//             public boolean isAuthenticated() {
//                 return false;
//             }

//             @Override
//             public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//             }

//             @Override
//             public String getName() {
//                 return clientRegistration.getClientId();
//             }
//         };
//     }

// }