import { html, LitElement, customElement, css, query } from 'lit-element';
import { KeyboardShortcutManager, KeyboardShortcutUtils } from '@vaadin-component-factory/keyboard-shortcut-manager';
import { shortcuts } from './keyboard-shortcuts';
import '@vaadin-component-factory/vcf-enhanced-dialog';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-text-field';
import '@vaadin/vaadin-tabs/vaadin-tab';
import '@vaadin/vaadin-tabs/vaadin-tabs';
import './demos/logical-group-demo';
import './demos/form-demo';

@customElement('keyboard-shortcuts-view')
export class KeyboardShortcutsView extends LitElement {
  @query('logical-group-demo') logicalGroupDemo?: HTMLElement;
  @query('form-demo') formDemo?: HTMLElement;
  @query('#help-dlg') helpDialog?: any;

  content?: HTMLElement;
  ksm?: KeyboardShortcutManager;

  static styles = [
    css`
      :host {
        --akdb-primary-color: #001e48;
        --akdb-primary-text-color: #003979
        display: block;
        height: 100vh;
        width: 100vw;
        position: fixed;
        overflow: hidden;
      }

      #container {
        height: 100%;
      }

      #header {
        width: 100%;
        padding: var(--lumo-space-s) var(--lumo-space-m);
        background-color: var(--akdb-primary-color);
        align-items: center;
        font-size: var(--lumo-font-size-xl);
      }

      #header-txt {
        flex-grow: 1;
        color: #fff;
      }

      #help-btn {
        padding: 0;
        margin: 0;
        color: var(--lumo-tint-60pct);
        cursor: pointer;
      }

      #content {
        padding-left: var(--lumo-space-s);
        background-color: var(--akdb-primary-color);
        width: 100%;
        overflow: hidden;
      }

      #side-menu {
        background-color: var(--akdb-primary-text-color);
        flex: 1 0 auto;
      }

      #side-menu vaadin-tab[selected] {
        color: var(--akdb-primary-color);
        background-color: #fff;
        border-top-left-radius: var(--lumo-border-radius-l);
        border-bottom-left-radius: var(--lumo-border-radius-l);
      }

      #side-menu vaadin-tab {
        color: var(--lumo-tint-60pct);
        padding: 0 0 0 var(--lumo-space-s);
      }

      #side-menu vaadin-tab::before {
        display: none;
      }
    `
  ];

  render() {
    return html`
      <vaadin-notification id="notification"></vaadin-notification>
      <vaadin-vertical-layout id="container">
        <!-- header -->
        <vaadin-horizontal-layout id="header">
          <div id="header-txt"><b>AKDB</b> | Keyboard Shortcuts</div>
          <vaadin-button id="help-btn" theme="icon small" @click="${this.onHelpBtnClick}">
            <iron-icon icon="vaadin:question-circle"></iron-icon>
          </vaadin-button>
        </vaadin-horizontal-layout>
        <vaadin-horizontal-layout id="content">
          <!-- side menu -->
          <vaadin-tabs id="side-menu" orientation="vertical" @selected-changed="${this.onSelectedChanged}">
            <vaadin-tab>
              <iron-icon icon="lumo:unordered-list"></iron-icon>
            </vaadin-tab>
            <vaadin-tab>
              <iron-icon icon="lumo:edit"></iron-icon>
            </vaadin-tab>
          </vaadin-tabs>
          <!-- content -->
          <form-demo selected class="content"></form-demo>
          <logical-group-demo class="content"></logical-group-demo>
        </vaadin-horizontal-layout>
      </vaadin-vertical-layout>
    `;
  }

  firstUpdated() {
    this.setAttribute('tabindex', '-1');
    this.initKeyboardShortcutEvents();
    this.ksm = new KeyboardShortcutManager({ shortcuts, helpDialog: true, root: this.shadowRoot as ShadowRoot });
    this.ksm.subscribe();
  }

  private onSelectedChanged = (e: CustomEvent) => {
    const index = e.detail.value;
    this.clearSelected();
    switch (index) {
      case 0:
        this.content = this.logicalGroupDemo;
        break;
      case 1:
        this.content = this.formDemo;
        break;
    }
    this.content?.setAttribute('selected', '');
  };

  private onHelpBtnClick = () => this.ksm?.toggleHelpDialog();

  private clearSelected() {
    Array.from(this.shadowRoot!.querySelectorAll('.content')).forEach((el) => el.removeAttribute('selected'));
  }

  private get currentGroup() {
    let group = null;
    const focused = KeyboardShortcutUtils.getActiveElement();
    if (focused) {
      group = (focused?.getRootNode() as ShadowRoot).host;
      while (group && group.tagName.toLowerCase() !== 'synneo-grid-toolbar') {
        group = (group.getRootNode() as ShadowRoot).host;
      }
      group = group.parentElement;
    }
    return group;
  }

  private get nextGroup() {
    let group = this.currentGroup?.nextElementSibling as HTMLElement | null | undefined;
    if (group?.tagName.toLowerCase() !== 'vcf-anchor-nav-section') {
      group = null;
    }
    return group;
  }

  private get previousGroup() {
    let group = this.currentGroup?.previousElementSibling as HTMLElement | null | undefined;
    if (group?.tagName.toLowerCase() !== 'vcf-anchor-nav-section') {
      group = null;
    }
    return group;
  }

  private focusFirstGroup(reverse = false) {
    let sections = Array.from(this.content?.shadowRoot!.querySelectorAll('vcf-anchor-nav-section') ?? []);
    const focused = KeyboardShortcutUtils.getActiveElement();
    if (reverse) {
      sections = sections.reverse();
    }
    for (const section of sections) {
      const toolbar = section.querySelector('synneo-grid-toolbar');
      if (!toolbar) continue;
      else {
        const currentTxt = (focused?.getRootNode() as any)?.host;
        const txt = toolbar.shadowRoot!.querySelector('vaadin-text-field') as HTMLElement;
        if (currentTxt !== txt) {
          txt?.focus();
          break;
        }
      }
    }
  }

  private focusLastGroup() {
    this.focusFirstGroup(true);
  }

  private focusGroup(group: HTMLElement) {
    const toolbar = group.querySelector('synneo-grid-toolbar');
    if (toolbar) {
      const txt = toolbar.shadowRoot!.querySelector('vaadin-text-field');
      txt?.focus();
    }
  }

  private initKeyboardShortcutEvents() {
    window.addEventListener('open-help-dialog', () => this.onHelpBtnClick());
    window.addEventListener('current-focused-field', () => console.log(KeyboardShortcutUtils.getActiveElement()));
    window.addEventListener('next-logical-group', () => {
      if (this.currentGroup) {
        if (this.nextGroup) this.focusGroup(this.nextGroup);
        else this.focusFirstGroup();
      } else {
        this.focusFirstGroup();
      }
    });
    window.addEventListener('previous-logical-group', () => {
      if (this.currentGroup) {
        if (this.previousGroup) this.focusGroup(this.previousGroup);
        else this.focusLastGroup();
      } else {
        this.focusFirstGroup();
      }
    });
  }
}
