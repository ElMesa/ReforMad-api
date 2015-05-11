package reformad.api.auth;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("auth")
public class Auth {
	
	OAuthService service = new ServiceBuilder()
    .provider(TwitterApi.class)
    .apiKey("LongvB0BcYsVTBZR7oYzAMg8K")
    .apiSecret("J1ONH7kj3dXMyJqOHttEKfrXAZ2fr53Z4Jcf5AAUXqbVlxfx6h")
    .callback("http://reformad.herokuapp.com/#/oauthCallback") 
    .build();
	
	private static HashMap<String,Token> requestTokens;
	private static ObjectMapper mapper;
	
	static {
		requestTokens = new HashMap<String,Token>();
		mapper = new ObjectMapper();
	}
	
	@Path("requestToken")
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response requestToken() throws JsonProcessingException {
		/*
		Token requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		
		requestTokens.put(requestToken.getToken(), requestToken);
		
		String requestTokenJSON = mapper.writeValueAsString(requestToken);
		System.out.println("\nRequestToken:\n\t" + requestTokenJSON);
		
		System.out.println("\nauthUrl:\n\t" + authUrl);
		*/
		String test = "test";
		
		return Response.ok(test).build();
		
		/*
		String test = "test";
		
		return Response.ok(new Entity(test, MediaType.TEXT_PLAIN)).build();
		*/
    }
	
	@Path("accessToken")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response accessToken(@QueryParam("oauth_token")String requestTokenString, @QueryParam("oauth_verifier")String verifierString) throws JsonProcessingException {
		Verifier v = new Verifier(verifierString);
		Token requestToken = requestTokens.get(requestTokenString);
		Token accessToken = service.getAccessToken(requestToken, v); // the requestToken you had from step 2
		
		System.out.println("\noauth_verifier:\n\t" + verifierString);
		
		String VerifierJSON = mapper.writeValueAsString(v);
		System.out.println("\nVerifier:\n\t" + VerifierJSON);
		
		String requestTokenJSON = mapper.writeValueAsString(requestToken);
		System.out.println("\nRequestToken:\n\t" + requestTokenJSON);
		
		String accessTokenJSON = mapper.writeValueAsString(accessToken);
		System.out.println("\naccessToken:\n\t" + accessTokenJSON);
				
		return Response.ok(accessTokenJSON).build();
	}
}
