import { css, html, LitElement } from 'lit-element';
import '@vaadin/vaadin-lumo-styles/all-imports';
import '@vaadin/vaadin-text-field';

class SynneoGridToolbarElement extends LitElement {

    static get styles() {
        return css`
            :host {
                align-items: baseline;
                display: flex;
                gap: var(--lumo-space-m);
                overflow: hidden;
                white-space: nowrap;
            }
            h1, h2, h3, h4, h5, h6 {
                margin-bottom: 0;
                margin-top: 0;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            [role="status"] {
                min-width: var(--lumo-icon-size-m);
            }
            [role="status"] iron-icon + span {
                margin-inline-start: var(--lumo-space-s);
            }
            vaadin-text-field {
                margin-inline-start: auto;
            }
            vaadin-menu-bar {
                min-width: var(--lumo-size-m); 
            }
            @media (max-width: 768px) {
                #titleWrapper,
                #search {
                    flex: 1;
                    min-width: 7rem;
                }
                #status span,
                #outOfSync span {
                    border-width: 0;
                    clip: rect(0, 0, 0, 0);
                    height: 1px;
                    margin: -1px;
                    overflow: hidden;
                    padding: 0;
                    position: absolute;
                    white-space: nowrap;
                    width: 1px;
                }
            }
        `;
    }

    render() {
        return html`
            <div id="titleWrapper"></div>
            <div role="status">
                <span id="status"></span>
                <span id="outOfSync"></span>
            </div>
            <vaadin-text-field id="search"></vaadin-text-field>
            <vaadin-menu-bar id="menu"></vaadin-menu-bar>
        `;
    }
}

window.customElements.define('synneo-grid-toolbar', SynneoGridToolbarElement);
