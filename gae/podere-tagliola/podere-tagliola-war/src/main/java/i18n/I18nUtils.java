package i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.jstl.core.Config;

public class I18nUtils {

	public final static HashMap<String, String> getBundle(ServletRequest req) {

		Locale locale = getLocale(req);

		ResourceBundle bundle = ResourceBundle.getBundle("i18n.i18n", locale);
		HashMap<String, String> i18nMap = new HashMap<String, String>();
		Set<String> keys = bundle.keySet();
		for (String k : keys) {
			i18nMap.put(k.replace(".", "_"), bundle.getString(k));
		}

		return i18nMap;

	}

	public final static Locale getLocale(ServletRequest req) {
		Locale locale = (Locale) Config.get(req, Config.FMT_LOCALE);

		if (locale == null)
			locale = req.getLocale();

		return locale;
	}
}