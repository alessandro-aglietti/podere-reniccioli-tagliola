package i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

public class I18nPathFilter implements Filter {

	Logger log = Logger.getLogger(I18nPathFilter.class.getName());

	private static final String LANG_REGEX = "lang-regex";

	public static final String I18N_PATH = "i18nPath";
	private FilterConfig fc;

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain fc) throws IOException, ServletException {

		sreq.setAttribute(I18N_PATH, "");

		if (sreq instanceof HttpServletRequest && sresp instanceof HttpServletResponse) {
			HttpServletRequest req = (HttpServletRequest) sreq;
			String requestURI = req.getRequestURI();

			if (requestURI != null) {
				Matcher matcher = Pattern.compile(this.fc.getInitParameter(LANG_REGEX)).matcher(requestURI);

				if (matcher.find()) {
					String lang = matcher.group();

					String newLocale = lang.replaceAll("/", "");
					Config.set(sreq, Config.FMT_LOCALE, new Locale(newLocale));
					log.info("Cambiato il locale con " + newLocale);

					String forwardPath = requestURI.replace(lang, "");

					if (!forwardPath.startsWith("/"))
						forwardPath += "/";

					log.info("Forward to " + forwardPath);

					req.setAttribute(I18N_PATH, lang);

					req.getRequestDispatcher(forwardPath).forward(req, (HttpServletResponse) sresp);
					return;
				}
			}
		}

		fc.doFilter(sreq, sresp);
	}

	public void init(FilterConfig fc) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = fc;
		log.info("Le lingue gestite sono " + this.fc.getInitParameter(LANG_REGEX));
	}
}