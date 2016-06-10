package br.diogo_julia.financialcontrol.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import br.diogo_julia.financialcontrol.dominio.resumo.Resumo;

import java.util.List;

public class ResumoContasListAdapter extends ArrayAdapter<Resumo> {

    public ResumoContasListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ResumoContasListAdapter(Context context, int resource, List<Resumo> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_resumo, null);
        }

        Resumo p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.txtCategoria);
            TextView tt2 = (TextView) v.findViewById(R.id.txtValor);

            if (tt1 != null) {
                tt1.setText(p.getCategoria());
            }

            if (tt2 != null) {
                tt2.setText(String.valueOf(p.getSoma()));
            }

        }

        return v;
    }

}