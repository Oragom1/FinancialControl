package br.diogo_julia.financialcontrol.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.diogo_julia.financialcontrol.dominio.conta.Conta;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContasListAdapter extends ArrayAdapter<Conta> {

    public ContasListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ContasListAdapter(Context context, int resource, List<Conta> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_contas, null);
        }

        Conta p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.txtCategoria);
            TextView tt2 = (TextView) v.findViewById(R.id.txtTipo);
            TextView tt3 = (TextView) v.findViewById(R.id.txtValor);
            TextView tt4 = (TextView) v.findViewById(R.id.txtData);
            TextView tt5 = (TextView) v.findViewById(R.id.txtId);

            if (tt1 != null) {
                tt1.setText(p.getCategoria().getNome());
            }

            if (tt2 != null) {
                tt2.setText(p.getTipoConta());
            }

            if (tt3 != null) {
                if(p.getTipoConta().equals(TipoConta.SAIDA.getTipo())) {
                    tt3.setText("(".concat(String.valueOf(p.getValorConta())).concat(")"));

                }
                else
                    tt3.setText(String.valueOf(p.getValorConta()));
            }
            if (tt4 != null) {
                Date data = new Date();
                data.setTime(Long.valueOf(p.getData().getTime()));
                String dateString = new SimpleDateFormat("dd/MM/yyyy").format(data);
                tt4.setText(dateString);
            }

            if (tt5 != null) {
                tt5.setText(String.valueOf(p.getId()));
            }
        }

        return v;
    }

}