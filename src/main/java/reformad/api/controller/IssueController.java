package reformad.api.controller;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Issue;
import persistance.IssueDAO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/issues")
public class IssueController {
	/*
	@Path("/testInsert")
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    public String testInsert() throws URISyntaxException, SQLException {

		
		
		IssueDAO.create();
        return "Hello, Heroku!";
    }
	*/
	@POST
	public Response post(String resourceJSON) throws JsonParseException, JsonMappingException, IOException {
		Response response;
		String error = "";
		long issueId = -1;

		ObjectMapper mapper = new ObjectMapper();
		Issue issue = mapper.readValue(resourceJSON, Issue.class);

		if (issue != null) {
			issueId = IssueDAO.create(issue);
			
			if(issueId >= 0) {

			// TODO Componer la uri con la location del recurso creado

			// String baseUri = uri.getBaseUri().toString();
			// String resourceUri = baseUri + "incidencia/" +
			// incidencia.getId();
			// response = Response.created(resourceUri).build();
			response = Response.status(201).entity(issueId).build();
			} else {
				error = "Server could not persist the issue";
				response = Response.status(500).entity(error).type("text/plain").build();
			}
		} else {
			error = "Server could not decode the JSON issue";
			response = Response.status(400).entity(error).type("text/plain").build();
		}

		return response;
	}
}
