package com.chukwu.MyStreamSite.data;
/*import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;


import org.springframework.messaging.handler.annotation.Payload;

import java.util.Collections;

public class GoogleIdTokenVerifier {


  GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
      // Specify the CLIENT_ID of the app that accesses the backend:
      .setAudience(Collections.singletonList(CLIENT_ID))
      // Or, if multiple clients access the backend:
      //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
      .build();

// (Receive idTokenString by HTTPS POST)

  GoogleIdToken idToken = verifier.verify(idTokenString);
if (idToken != null) {
    Payload payload = idToken.getPayload();

    // Print user identifier
    String userId = payload.getSubject();
    System.out.println("User ID: " + userId);

    // Get profile information from payload
    String email = payload.getEmail();
    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
    String name = (String) payload.get("name");
    String pictureUrl = (String) payload.get("picture");
    String locale = (String) payload.get("locale");
    String familyName = (String) payload.get("family_name");
    String givenName = (String) payload.get("given_name");

    // Use or store profile information
    // ...

  } else {
    System.out.println("Invalid ID token.");
  }
}

 */
