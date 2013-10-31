package hr.fer.zemris.java.tecaj_10.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	List<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();
	
	@Override
	public void addLocalizationProvider(ILocalizationListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeLocalizationProvider(ILocalizationListener l) {
		if (!listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	/**
	 * obavjestavanje nasih observera
	 */
	protected void fire() {
		ILocalizationListener[] array =
			listeners.toArray(
				new ILocalizationListener[listeners.size()]	
			);
		for (ILocalizationListener l : array) {
			l.localizationChanged();
		}
	}
}
