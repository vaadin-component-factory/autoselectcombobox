package org.vaadin.addons.autoselectcombobox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractDemo extends VerticalLayout {

	public AbstractDemo() {
		super();
		initView();
		setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
	}
	protected abstract void initView();

	protected void addCard(String title, Component... components) {
		Card card = new Card();
		card.setTitle(title);
		VerticalLayout verticalLayout = new VerticalLayout(components);
		verticalLayout.setPadding(false);
		card.add(verticalLayout);
		add(card);
	}
}
