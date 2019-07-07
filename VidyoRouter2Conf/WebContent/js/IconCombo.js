Ext.ux.IconCombo = Ext.extend(Ext.form.ComboBox, {

    initComponent:function() {

        Ext.apply(this, {
            tpl:  '<tpl for=".">'
                + '<div class="x-combo-list-item ux-icon-combo-item '
                + '{' + this.iconClsField + '}">'
                + '{' + this.displayField + '}'
                + '</div></tpl>'
        });

        // call parent initComponent
        Ext.ux.IconCombo.superclass.initComponent.call(this);

    },

    onRender:function(ct, position) {
        // call parent onRender
        Ext.ux.IconCombo.superclass.onRender.call(this, ct, position);

        // adjust styles
        this.wrap.applyStyles({position:'relative'});
        this.el.addClass('ux-icon-combo-input');

        // add div for icon
        this.icon = Ext.DomHelper.append(this.el.up('div.x-form-field-wrap'), {
            tag: 'div', style:'position:absolute'
        });
    },

    setIconCls:function() {
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if(rec) {
            this.icon.className = 'ux-icon-combo-icon ' + rec.get(this.iconClsField);
        }
    },

    setValue: function(value) {
        Ext.ux.IconCombo.superclass.setValue.call(this, value);
        this.setIconCls();
    }
});

// register xtype
Ext.reg('iconcombo', Ext.ux.IconCombo);
