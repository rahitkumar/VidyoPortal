ComboLoadValue = Ext.extend(Ext.util.Observable, {
    field : null,

    init : function(field){
        var self = this;
        this.field = field;
        this.store = field.getStore();
        this.setLoaded(false);

        this.store.on('load', function(){
            return self.onLoad();
        }, this);
    },

    onLoad : function(){
        if(this.store !== null){
            this.field.setValue(this.field.getValue());
            this.setLoaded(true);
        }
        return true;
    },

    setLoaded: function(bool){
        this.store.hasOnceLoaded = bool;
    },

    getLoaded: function(){
        return this.store.hasOnceLoaded;
    }

});