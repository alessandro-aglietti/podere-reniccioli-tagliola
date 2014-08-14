package it.aqquadro.webapp;

import i18n.I18nPathFilter;
import i18n.I18nUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

@Path("/")
public class IndexResource {

	private final static Logger LOGGER = Logger.getLogger(IndexResource.class
			.getName());

	@NotNull
	@Context
	private UriInfo uriInfo;
	
	@Context
	HttpServletRequest req;

	@Context
	HttpServletResponse resp;

	@GET
	@Produces("text/html")
	@Template(name = "/index")
	public Map<String, Object> get() throws InterruptedException {
		LOGGER.info("IndexResource.get()");

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("title", "Podere Reniccioli | Rufina");
		model.put("path", req.getAttribute(I18nPathFilter.I18N_PATH));
		model.put("i18n", (Map<String, String>) I18nUtils.getBundle(req));

		return model;
	}
}
