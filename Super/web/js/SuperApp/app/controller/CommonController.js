Ext.define('SuperApp.controller.CommonController', {
    extend: 'Ext.app.Controller',

    refs: [{
    	ref: 'poolDetailsGrid',
        selector: 'pools grid[reference="poolsListGrid"]'
    }, {
    	ref: 'pools',
    	selector: 'pools'
    }],
    
    showPoolDetails: function(pool) {
    	this.getPoolDetailsGrid().fireEvent('rowclick', this.getPoolDetailsGrid(), pool.get('name'));
    },
    
    goBackToPoolsInGrid: function() {
    	this.getPools().fireEvent('goBackToPools', false);
    },
    
    savePoolCoordinates: function(newX, newY) {
    	this.getPools().fireEvent('savePoolCoordinates', newX, newY);
    }
});