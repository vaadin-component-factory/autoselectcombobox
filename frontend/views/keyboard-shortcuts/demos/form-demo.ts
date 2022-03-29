import { html, LitElement, customElement, css, query } from 'lit-element';
import { FormLayoutResponsiveStep } from '@vaadin/vaadin-form-layout';
import { KeyboardShortcutUtils } from '@vaadin-component-factory/keyboard-shortcut-manager';
import { NotificationElement } from '@vaadin/vaadin-notification';
import '@vaadin-component-factory/vcf-breadcrumb';

@customElement('form-demo')
export class FormDemo extends LitElement {
  @query('#notification') notificationElement?: NotificationElement;
  @query('#next-btn') nextBtn?: HTMLElement;
  @query('#prev-btn') prevBtn?: HTMLElement;

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

      #nav-tabs {
        background-color: #3371a7;
      }

      #nav-tabs iron-icon {
        height: calc(var(--lumo-space-s) * 2.5);
        width: calc(var(--lumo-space-s) * 2.5);
      }

      #nav-tabs vaadin-tab[selected] {
        color: #fff;
      }

      #nav-tabs vaadin-tab {
        color: var(--lumo-tint-60pct);
        font-weight: bold;
      }

      #nav-tabs vaadin-tab::before {
        display: none;
      }

      #settings vaadin-horizontal-layout > * {
        flex: 1;
        min-width: 0;
      }

      #settings vaadin-form-item {
        display: block;
        margin-bottom: var(--lumo-space-m);
      }

      #settings vaadin-form-item label {
        margin-right: var(--lumo-space-m);
      }

      #settings vaadin-form-item span {
        color: var(--akdb-primary-color);
        font-weight: bold;
      }

      #footer {
        background-color: #f4f4f4;
        display: flex;
        position: fixed;
        bottom: 0;
        width: calc(100% - 57px);
      }

      #save-exit {
        margin-left: auto;
      }

      .no-header {
        display: none;
      }

      vcf-breadcrumbs {
        padding: 0;
        background-color: #f4f4f4;
        display: flex;
      }

      vcf-breadcrumb[selected]::part(link) {
        background-color: var(--akdb-primary-color);
        color: #fff;
      }

      vcf-breadcrumb::part(link) {
        padding: var(--lumo-space-m);
      }

      vcf-breadcrumb::part(separator) {
        margin: 0;
        position: relative;
      }

      vcf-breadcrumb.before::part(separator) {
        background-color: var(--akdb-primary-color);
      }

      vcf-breadcrumb::part(separator)::after {
        content: '';
        width: 0;
        height: 0;
        border-top: 29px solid transparent;
        border-bottom: 29px solid transparent;
        border-left: 20px solid var(--akdb-primary-color);
        display: block;
      }

      vcf-breadcrumb:not([selected])::part(separator)::before {
        content: '';
        width: 0;
        height: 0;
        border-top: 29px solid transparent;
        border-bottom: 29px solid transparent;
        border-left: 20px solid #f4f4f4;
        display: block;
        position: absolute;
        left: -1px;
      }

      vaadin-form-layout.form vaadin-vertical-layout > * {
        width: 100%;
      }

      vaadin-button {
        margin-right: var(--lumo-space-s);
      }

      vaadin-button:first-child {
        margin-left: var(--lumo-space-s);
      }
    `
  ];

  private layoutSteps: FormLayoutResponsiveStep[] = [
    { minWidth: 0, columns: 1 },
    { minWidth: '40em', columns: 2 }
  ];

  render() {
    return html`
      <vcf-anchor-nav theme="expand-last" .disablePreserveOnRefresh="${true}">
        <vaadin-tabs id="nav-tabs" slot="tabs" theme="small">
          <vaadin-tab id="tab-1">Produktkonto hinzufügen</vaadin-tab>
          <vaadin-tab id="tab-2">
            <iron-icon icon="lumo:edit"></iron-icon>
          </vaadin-tab>
        </vaadin-tabs>

        <vcf-anchor-nav-section name="Personendaten" tab-id="tab-1">
          <span slot="header" class="no-header"></span>
          <vaadin-form-layout id="settings" .responsiveSteps="${this.layoutSteps}">
            <vaadin-form-item colspan="2">
              <label>Ansicht: </label>
              <span>
                Aufgabenliste
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </span>
            </vaadin-form-item>
            <vaadin-horizontal-layout theme="spacing">
              <vaadin-text-field label="Bearbeiter" required></vaadin-text-field>
              <vaadin-text-field label="Aufgabe" required>
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
              <vaadin-text-field label="FAD-Nummer" required></vaadin-text-field>
            </vaadin-horizontal-layout>
            <vaadin-horizontal-layout theme="spacing">
              <vaadin-text-field label="Rechnungssteller" required></vaadin-text-field>
              <vaadin-text-field label="Mandant" required>
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
              <vaadin-text-field label="Dienststelle" required>
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
            </vaadin-horizontal-layout>
          </vaadin-form-layout>
        </vcf-anchor-nav-section>

        <vcf-anchor-nav-section name="Personendaten" tab-id="tab-2" class="container">
          <vcf-breadcrumbs slot="header">
            <vcf-breadcrumb selected>Grunddaten</vcf-breadcrumb>
            <vcf-breadcrumb>Umsatzsteuer</vcf-breadcrumb>
            <vcf-breadcrumb>Umsatzsteuer</vcf-breadcrumb>
            <vcf-breadcrumb>Kontierungserweiterung</vcf-breadcrumb>
          </vcf-breadcrumbs>

          <vaadin-form-layout class="form" .responsiveSteps="${this.layoutSteps}">
            <vaadin-vertical-layout theme="spacing">
              <h4>Grunddaten</h4>
              <vaadin-text-field label="Haushaltsjahr">
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
              <vaadin-text-field label="Produkt">
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
              <vaadin-text-field label="Konto">
                <iron-icon icon="lumo:search" slot="suffix"></iron-icon>
              </vaadin-text-field>
              <vaadin-text-field label="Bezeichnung"></vaadin-text-field>
            </vaadin-vertical-layout>

            <vaadin-vertical-layout theme="spacing">
              <h4>Weitere Eigenschaften</h4>
              <vaadin-checkbox-group label="Eigenschaften" theme="vertical">
                <vaadin-checkbox value="ANBU-relevant">ANBU-relevant</vaadin-checkbox>
                <vaadin-checkbox value="Allgemeine ZAO zulässig">Allgemeine ZAO zulässig</vaadin-checkbox>
                <vaadin-checkbox value="Planungsebene">Planungsebene</vaadin-checkbox>
                <vaadin-checkbox value="Buchungsebene">Buchungsebene</vaadin-checkbox>
              </vaadin-checkbox-group>
              <vaadin-text-field label="Dienststelle">
                <iron-icon icon="lumo:angle-down" slot="suffix"></iron-icon>
              </vaadin-text-field>
            </vaadin-vertical-layout>
          </vaadin-form-layout>
        </vcf-anchor-nav-section>
      </vcf-anchor-nav>

      <div id="footer">
        <vaadin-button id="prev-btn" @click="${this.onPrevClick}">
          <span>Zurück</span>
          <iron-icon icon="lumo:angle-left" slot="prefix"></iron-icon>
        </vaadin-button>
        <vaadin-button id="next-btn" @click="${this.onNextClick}">
          <span>Weiter</span>
          <iron-icon icon="lumo:angle-right" slot="suffix"></iron-icon>
        </vaadin-button>

        <div id="save-exit">
          <vaadin-button>
            <span>Abbrechen</span>
            <iron-icon icon="lumo:undo" slot="suffix"></iron-icon>
          </vaadin-button>
          <vaadin-button>
            <span>Speichern</span>
            <iron-icon icon="lumo:checkmark" slot="suffix"></iron-icon>
          </vaadin-button>
        </div>
      </div>
    `;
  }

  firstUpdated() {
    this.setAttribute('tabindex', '-1');
    this.initKeyboardShortcutEvents();
  }

  private get currentForm() {
    return this.shadowRoot!.querySelector('vcf-breadcrumb[selected]') as HTMLElement | null;
  }

  private get breadcrumbs() {
    return this.shadowRoot!.querySelectorAll('vcf-breadcrumb') as NodeListOf<HTMLElement>;
  }

  private clearBreadcrumbs() {
    this.breadcrumbs.forEach((b) => {
      b.removeAttribute('selected');
      b.classList.remove('before');
    });
  }

  private onNextClick = () => {
    const current = this.currentForm;
    const next = current?.nextElementSibling;
    if (next) {
      this.clearBreadcrumbs();
      next.setAttribute('selected', '');
      if (current) {
        current.classList.add('before');
      }
    }
  };

  private onPrevClick = () => {
    const prev = this.currentForm?.previousElementSibling;
    if (prev) {
      this.clearBreadcrumbs();
      prev.setAttribute('selected', '');
      const before = prev.previousElementSibling;
      if (before) {
        before.classList.add('before');
      }
    }
  };

  private notification(msg: string) {
    const notificationElement = document.createElement('vaadin-notification');
    document.body.appendChild(notificationElement);

    notificationElement.duration = 1000;
    notificationElement.renderer = (root) => {
      if (root.firstElementChild) return;
      const container = window.document.createElement('div');
      const plainText = window.document.createTextNode(msg);
      container.appendChild(plainText);
      root.appendChild(container);
    };
    notificationElement.open();

    setTimeout(() => notificationElement.remove());
  }

  private initKeyboardShortcutEvents() {
    this.addEventListener('next-invalid-field', () => {
      KeyboardShortcutUtils.focusNextInvalidField(this.shadowRoot as ShadowRoot);
    });
    this.addEventListener('save', () => {
      this.notification('Saved.');
    });
    this.addEventListener('next-form-section', () => {
      debugger;
      this.nextBtn?.click();
    });
    this.addEventListener('previous-form-section', () => {
      this.prevBtn?.click();
    });
  }
}
