import { PolymerElement,html } from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin';
import { ElementMixin } from '@vaadin/vaadin-element-mixin';
import { mixinBehaviors } from '@polymer/polymer/lib/legacy/class.js';
import { IronResizableBehavior } from '@polymer/iron-resizable-behavior/iron-resizable-behavior.js';

class SynneoLayout extends mixinBehaviors([IronResizableBehavior], ThemableMixin(ElementMixin(PolymerElement))) {

    static get template() {
        return html`
        	<style>
						
				:host {
					--_synneo-layout-gutter: var(--synneo-size-size-xs);
					display: flex;
					flex-wrap: wrap;
					align-items: flex-start;
				}
				
				:host ::slotted(.slot) {
					flex-grow: 0;
					flex-shrink: 0;
					flex-basis: 50%;
					box-sizing: border-box;
					padding-right: var(--_synneo-layout-gutter);
					padding-bottom: var(--_synneo-layout-gutter);
				}
				
				/* Default layouts */
				:host([size="large"]) ::slotted(.slot) {
					max-width: calc(100% * ( 4 / 12 ));
					flex-basis: 100%;
				}
				:host([size="medium"]) ::slotted(.slot) {
					max-width: 50%;
					flex-basis: 100%;
				}
				:host([size="small"]) ::slotted(.slot) {
					max-width: 100%;
					flex-basis: 100%;
				}
				
				/* Special layouts */
				/* Layout 12 */
				:host([layout="12"][size="large"]) ::slotted(.slot),
				:host([layout="12"][size="medium"]) ::slotted(.slot) {
					max-width: 100%;
				}
				
				/* Layout 8 */
				:host([layout="8"][size="large"]) ::slotted(.slot) {
					max-width: calc(100% * ( 8 / 12 ));
				}
				:host([layout="8"][size="medium"]) ::slotted(.slot) {
					max-width: 100%;
				}
				
				/* Layout 8-4 */
				:host([layout="8-4"][size="large"]) ::slotted(.slot:nth-child(odd)) {
					max-width: calc(100% * ( 8 / 12 ));
				}
				:host([layout="8-4"][size="large"]) ::slotted(.slot:nth-child(even)) {
					max-width: calc(100% * ( 4 / 12 ));
				}
				:host([layout="8-4"][size="medium"]) ::slotted(.slot:nth-child(odd)) {
					max-width: 100%;
				}
				:host([layout="8-4"][size="medium"]) ::slotted(.slot:nth-child(even)) {
					max-width: 50%;
				}
				
				/* Layout 4-8 */
				:host([layout="4-8"][size="large"]) ::slotted(.slot:nth-child(odd)) {
					max-width: calc(100% * ( 4 / 12 ));
				}
				:host([layout="4-8"][size="large"]) ::slotted(.slot:nth-child(even)) {
					max-width: calc(100% * ( 8 / 12 ));
				}
				:host([layout="4-8"][size="medium"]) ::slotted(.slot:nth-child(odd)) {
					max-width: 50%;
				}
				:host([layout="4-8"][size="medium"]) ::slotted(.slot:nth-child(even)) {
					max-width: 100%;
				}
				
				/* Layout 6-6 */
				:host([layout="6-6"][size="large"]) ::slotted(.slot) {
					max-width: 50%;
				}
				:host([layout="6-6"][size="medium"]) ::slotted(.slot) {
					max-width: 50%;
				}
				
				/* Layout free floating */
				:host([layout="float"]) ::slotted(.slot) {
					max-width: 100%;
					flex-basis: auto;
				}
		    </style>
        <slot></slot>
		   	`;
    }

    static get is() {
          return 'synneo-layout';
    }
    
    static get properties () {
        return {
    		layout: {
    			type: String,
    			reflectToAttribute: true
    		},
    		size: {
    			type: String,
    			reflectToAttribute: true
    		}
        };
    }
    
    connectedCallback() {
        super.connectedCallback();
        this.addEventListener('iron-resize', this._onResize.bind(this));
    }
    
    ready() {
        super.ready();
        
        this._onResize();
        
        this.shadowRoot.querySelectorAll('slot').forEach(slot => {
	    	slot.addEventListener('slotchange', e => {
	    		const nodes = slot.assignedNodes();
	    		if (nodes.length && nodes[0].className !== "slot"){
	    			nodes.forEach(node => {
	    				const wrapper = document.createElement("div");
	    				wrapper.className = "slot";
	    				wrapper.appendChild(node);
	    				this.appendChild(wrapper);
	    			});
	    		}
	    	});
        });
    }
    
    _onResize() {
    	var size = "large";
    	var clientWidth = this.getBoundingClientRect().width;
    	let steps = this.readSteps();
    	for (var step in steps) {
    		var stepMaxWidth = steps[step];
    		if (clientWidth <= stepMaxWidth) {
    			var hasSmaller = false;
    			for (var otherStep in steps) {
    				var otherStepMaxWidth = steps[otherStep];
    				if (step != otherStep && clientWidth <= otherStepMaxWidth && otherStepMaxWidth < stepMaxWidth ) {
    					hasSmaller = true;
    					break;
    				}
    			}
    			if (!hasSmaller) {
    				size = step;
    				break;
    			}
    		}
    	}
    	
    	if (this.size != size) {
    		this.size = size;
    	}
    }
    
    readSteps() {
    	let style = getComputedStyle(document.documentElement);
    	return {
    		"small" : parseInt(style.getPropertyValue('--synneo-size-breakpoint-s'), 10),
    		"medium" : parseInt(style.getPropertyValue('--synneo-size-breakpoint-m'), 10)
    	};
    }

}

customElements.define(SynneoLayout.is, SynneoLayout);