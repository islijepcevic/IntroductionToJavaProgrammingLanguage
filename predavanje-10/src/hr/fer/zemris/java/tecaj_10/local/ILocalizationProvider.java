package hr.fer.zemris.java.tecaj_10.local;

public interface ILocalizationProvider {
	
	public void addLocalizationProvider(ILocalizationListener l);
	
	public void removeLocalizationProvider(ILocalizationListener l);

	public String getText(String key);
}
