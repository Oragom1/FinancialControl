package br.diogo_julia.financialcontrol.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;

import java.util.List;

public class CartoesListAdapter extends ArrayAdapter<CartaoDeCredito> {

    public CartoesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CartoesListAdapter(Context context, int resource, List<CartaoDeCredito> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_cartoes, null);
        }

        CartaoDeCredito c = getItem(position);

        if (c != null) {
            TextView ttIdHidden = (TextView) v.findViewById(R.id.txtId);
            TextView tt1 = (TextView) v.findViewById(R.id.txtDescicao);
            TextView tt2 = (TextView) v.findViewById(R.id.txtTipo);
            TextView tt3 = (TextView) v.findViewById(R.id.txtLimite);
            TextView tt4 = (TextView) v.findViewById(R.id.txtBandeira);
            TextView tt5 = (TextView) v.findViewById(R.id.txtVencimento);

            if (ttIdHidden != null) {
                ttIdHidden.setText(String.valueOf(c.getId()));
            }

            if (tt1 != null) {
                tt1.setText(c.getNome());
            }

            if (tt2 != null) {
                tt2.setText(c.getTipo());
            }

            if (tt3 != null) {
                tt3.setText(String.valueOf(c.getLimite()));
            }
            if (tt4 != null) {
                tt4.setText(c.getBandeira());
            }
            if (tt5 != null) {
                tt5.setText(String.valueOf(c.getDataVencimento()));
            }
        }

        return v;
    }

}