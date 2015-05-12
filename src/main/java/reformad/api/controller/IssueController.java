package reformad.api.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import reformad.api.model.Issue;
import reformad.api.persistance.IssueDAO;
import reformad.api.util.JSON;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/issues")
public class IssueController {
	
	@POST
	public Response post(String resourceJSON) throws JsonParseException, JsonMappingException, IOException {
		Response response;
		String error = "";
		long issueId = -1;

		//ObjectMapper mapper = new ObjectMapper();
		//Issue issue = mapper.readValue(resourceJSON, Issue.class);
		Issue issue = (Issue) JSON.decode(resourceJSON, Issue.class);

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
	
	@GET
	public Response get() {

		Response response;
		String json = "";
		String error = "Failed to retrieve resource";
		
		List<Issue> issues = IssueDAO.getAll();

		if (issues != null) {
			json = JSON.encode(issues);
			response = Response.ok(json).build();
		} else {
			response = Response.status(404).entity(error).type("text/plain").build();
		}

		return response;
	}
}
