import { html, LitElement, customElement, css, query } from 'lit-element';
import { FormLayoutResponsiveStep } from '@vaadin/vaadin-form-layout';

@customElement('logical-group-demo')
export class LogicalGroupDemo extends LitElement {
  @query('#group-1') groupToolbar1?: HTMLElement;
  @query('#group-2') groupToolbar2?: HTMLElement;
  @query('#group-3') groupToolbar3?: HTMLElement;

  private get items() {
    return [
      {
        Adresstyp: 'Inlandsadresse',
        Aostleitzahl: '02625',
        Ort: 'Bautzen',
        Ortsteil: 'Salzenforst',
        Strasse: 'Brudermühlstraße',
        Hausnummer: 99,
        Hausnummernzusatz: 'b'
      },
      {
        Adresstyp: 'Inlandsadresse',
        Aostleitzahl: '02625',
        Ort: 'Bautzen',
        Ortsteil: 'Stiebitz',
        Strasse: 'Handrij-Zejler-Straße',
        Hausnummer: 12
      },
      {
        Adresstyp: 'Inlandsadresse',
        Aostleitzahl: '02625',
        Ort: 'Bautzen',
        Ortsteil: 'Stiebitz',
        Strasse: 'Handrij-Zejler-Straße',
        Hausnummer: 13
      }
    ];
  }

  private headerSteps: FormLayoutResponsiveStep[] = [{ minWidth: 0, columns: 1 }];
  private groupSteps: FormLayoutResponsiveStep[] = [
    { minWidth: 0, columns: 1 },
    { minWidth: '20em', columns: 2 }
  ];

  static styles = [
    css`
      :host {
        display: none;
        height: 100%;
        width: 100%;
        background-color: var(--lumo-tint);
      }

      :host([selected]) {
        display: block;
      }

      vaadin-form-layout iron-icon {
        height: var(--lumo-space-m);
        width: var(--lumo-space-m);
      }

      #details {
        margin-bottom: var(--lumo-space-l);
      }

      #details vaadin-form-item::part(label) {
        width: var(--lumo-space-l);
      }
    `
  ];

  render() {
    return html`
      <vcf-anchor-nav theme="expand-last" .disablePreserveOnRefresh="${true}">
        <vaadin-tabs id="nav-tabs" slot="tabs" theme="small">
          <vaadin-tab id="tab-1">Personendaten</vaadin-tab>
          <vaadin-tab id="tab-2">Hauptadresse</vaadin-tab>
          <vaadin-tab id="tab-3">Weitere Adressen</vaadin-tab>
        </vaadin-tabs>
        <div slot="header">
          <h2>Max Mustermann</h2>
          <vaadin-form-layout id="details" .responsiveSteps="${this.headerSteps}">
            <vaadin-form-item>
              <label slot="label">
                <iron-icon icon="vaadin:phone"></iron-icon>
              </label>
              <span>(+49) 123 456789</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">
                <iron-icon icon="vaadin:mailbox"></iron-icon>
              </label>
              <a href="mailto:max.mustermann@beispiel.de">max.mustermann@beispiel.de</a>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">
                <iron-icon icon="vaadin:building"></iron-icon>
              </label>
              <span>Heidestraße 123 a, 87653 München</span>
            </vaadin-form-item>
          </vaadin-form-layout>
        </div>

        <vcf-anchor-nav-section name="Personendaten" tab-id="tab-1">
          <synneo-grid-toolbar id="group-1" slot="header"></synneo-grid-toolbar>
          <vaadin-form-layout .responsiveSteps="${this.groupSteps}">
            <vaadin-form-item>
              <label slot="label">Personen Nr.</label>
              <span>1234567</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Personentyp</label>
              <span>Natürliche Person</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Geburtsdatum</label>
              <span>15.06.1952</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Sterbedatum</label>
              <span>19.08.2020</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Anrede</label>
              <span>Herr</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Akademischer Grad</label>
              <span>Dipl. Ozeanograph</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Vorname</label>
              <span>Max</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Nachname</label>
              <span>Mustermann</span>
            </vaadin-form-item>
          </vaadin-form-layout>
        </vcf-anchor-nav-section>

        <vcf-anchor-nav-section name="Hauptadresse" tab-id="tab-2">
          <synneo-grid-toolbar id="group-2" slot="header"></synneo-grid-toolbar>
          <vaadin-form-layout .responsiveSteps="${this.groupSteps}">
            <vaadin-form-item>
              <label slot="label">Adressenart</label>
              <span>Inlandsadresse</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Postleitzahl</label>
              <span>87653</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Ort</label>
              <span>München</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Ortsteil</label>
              <span>Sendling</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Straße</label>
              <span>Heidestraße</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Hausnummer</label>
              <span>123</span>
            </vaadin-form-item>
            <vaadin-form-item>
              <label slot="label">Hausnummerzusatz</label>
              <span>a</span>
            </vaadin-form-item>
          </vaadin-form-layout>
        </vcf-anchor-nav-section>

        <vcf-anchor-nav-section name="Weitere Adressen (3)" tab-id="tab-3">
          <synneo-grid-toolbar id="group-3" slot="header"></synneo-grid-toolbar>
          <vaadin-grid .items="${this.items}" all-rows-visible>
            <vaadin-grid-column auto-width path="Adresstyp"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Postleitzahl"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Ort"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Ortsteil"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Strasse"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Hausnummer"></vaadin-grid-column>
            <vaadin-grid-column auto-width path="Hausnummernzusatz"></vaadin-grid-column>
          </vaadin-grid>
        </vcf-anchor-nav-section>
      </vcf-anchor-nav>
    `;
  }

  firstUpdated() {
    this.setAttribute('tabindex', '-1');
    this.initToolbar(this.groupToolbar1, 'Personendaten');
    this.initToolbar(this.groupToolbar2, 'Hauptadresse');
    this.initToolbar(this.groupToolbar3, 'Weitere Adressen (3)');
  }

  initToolbar(toolbar?: HTMLElement, title = '') {
    requestAnimationFrame(() => {
      if (toolbar) {
        const titleWrapper = toolbar?.shadowRoot!.querySelector('#titleWrapper');
        if (titleWrapper && title) {
          const h2 = document.createElement('h2');
          h2.textContent = title;
          titleWrapper.appendChild(h2);
        }
      }
    });
  }
}
