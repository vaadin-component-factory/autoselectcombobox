import {PolymerElement,html} from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin';
import { ElementMixin } from '@vaadin/vaadin-element-mixin';

class FieldSet extends ThemableMixin(ElementMixin(PolymerElement)) {

    static get template() {
        return html`
        	<style>
        		:host([form-size~="s"]){
        			flex-basis: 100%;
        		}
        		:host([form-size~="m"]){
        			flex-basis: 75%;
        		}
        		:host([form-size~="l"]){
        			flex-basis: 33%;
        		}
        		#set {
        			width: 100%;
        			display: flex;
		      		flex-wrap: wrap;
        		}
        	    
        	    #title, #set {
        			width: 100%;
        		}
		    </style>
		    <div id="set" part="set" role="group" aria-labelledby="title">
		    	<div id="title" part="title" hidden$="{{!_titleVisible}}">{{title}}</div>
		    	<slot></slot>
		    </div>
		   	`;
		   	
    }

    static get is() {
          return 'synneo-field-set';
    }
        
    static get properties () {
        return {
    		title: {
    			type: String,
    			observer: '_titleChanged'
    		},
    		_titleVisible: {
    			type: Boolean,
    			value: false
    		}
        };
    }
     
    _titleChanged(newValue, oldValue) {
    	 this._titleVisible = newValue && newValue != "";
    }

}

customElements.define(FieldSet.is, FieldSet);