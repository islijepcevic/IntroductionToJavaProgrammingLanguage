package hr.fer.zemris.java.tecaj_10.local;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private static LocalizationProvider instance = new LocalizationProvider();

	private String language;
	private ResourceBundle bundle;
	
	private LocalizationProvider() {
		setLanguage("hr");
	}
	
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(this.language);
		this.bundle = ResourceBundle.getBundle(
			"hr.fer.zemris.java.tecaj_10.local.Poruke", locale);
			// dakle treba imat fileove tipa Poruke_en.properties
		this.fire();
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getText(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException ex) {
			ex.printStackTrace();
			return "?" + key;
		}
	}
	
}
