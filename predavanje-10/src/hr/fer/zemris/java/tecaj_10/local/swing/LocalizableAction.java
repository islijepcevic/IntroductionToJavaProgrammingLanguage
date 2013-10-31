package hr.fer.zemris.java.tecaj_10.local.swing;

import hr.fer.zemris.java.tecaj_10.local.ILocalizationListener;
import hr.fer.zemris.java.tecaj_10.local.ILocalizationProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private ILocalizationProvider provider;
	private String key;
	
	private ILocalizationListener listener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			updateText();
		}
	};
	
	public LocalizableAction(ILocalizationProvider provider, String key) {
		super();
		this.provider = provider;
		this.key = key;
		this.provider.addLocalizationProvider(listener);
		updateText();
	}
	
	private void updateText() {
		putValue(Action.NAME, provider.getText(key));
	}

}
