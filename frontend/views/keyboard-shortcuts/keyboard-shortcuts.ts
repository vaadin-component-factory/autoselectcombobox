import { KeyboardShortcut } from '@vaadin-component-factory/keyboard-shortcut-manager';

export const shortcuts: KeyboardShortcut[] = [
  {
    scope: window,
    keyBinding: ['Control+Shift+?', 'Meta+/'],
    handler: 'open-help-dialog',
    description: 'Opens the help dialog.'
  },
  {
    scope: window,
    keyBinding: 'F6',
    handler: 'next-logical-group',
    description: 'Go to next logical group.'
  },
  {
    scope: window,
    keyBinding: 'Shift+F6',
    handler: 'previous-logical-group',
    description: 'Go to previous logical group.'
  },
  {
    scope: 'form-demo',
    keyBinding: 'Alt+F8',
    handler: 'next-invalid-field',
    description: 'Go to next invalid field in form.'
  },
  {
    scope: 'form-demo',
    keyBinding: 'Alt+Shift+F8',
    handler: 'previous-invalid-field',
    description: 'Go to previous invalid field in form.'
  },
  {
    scope: 'form-demo',
    keyBinding: 'Control+Shift+ArrowRight',
    handler: 'next-form-section',
    description: 'Go to next form section.'
  },
  {
    scope: 'form-demo',
    keyBinding: 'Control+Shift+ArrowLeft',
    handler: 'previous-form-section',
    description: 'Go to previous form section.'
  },
  {
    scope: 'form-demo',
    keyBinding: 'Control+Esc',
    handler: 'abort',
    description: 'Leaves the form.'
  }
];
