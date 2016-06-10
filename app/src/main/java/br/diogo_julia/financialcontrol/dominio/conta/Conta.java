package br.diogo_julia.financialcontrol.dominio.conta;

import android.os.Parcel;
import android.os.Parcelable;

import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

import java.util.Date;

public class Conta extends EntidadeDominio implements Parcelable {

    private Date data;
    private Categoria categoria;
    private double valorConta;
    private String tipoConta;
    private String cartao;
    private int qtdParcelas;

    protected Conta(Parcel in) {
        valorConta = in.readDouble();
        tipoConta = in.readString();
        cartao = in.readString();
        qtdParcelas = in.readInt();
        categoria = in.readParcelable(ClassLoader.getSystemClassLoader());
    }

    public static final Creator<Conta> CREATOR = new Creator<Conta>() {
        @Override
        public Conta createFromParcel(Parcel in) {
            return new Conta(in);
        }

        @Override
        public Conta[] newArray(int size) {
            return new Conta[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(data.getTime());
        dest.writeDouble(valorConta);
        dest.writeString(tipoConta);
        dest.writeString(cartao);
        dest.writeInt(qtdParcelas);
        dest.writeParcelable(getCategoria(),flags);

    }

    public Conta(Date data, Categoria categoria, double valor, String tipo){
        this.setData(data);
        this.setCategoria(categoria);
        this.setTipoConta(tipo);
        this.setValorConta(valor);
    }

    public Conta(Date data, Categoria categoria, double valor, String tipo, String cartao, int qtdParcelas){
        this.setData(data);
        this.setCategoria(categoria);
        this.setTipoConta(tipo);
        this.setValorConta(valor);
        this.setCartao(cartao);
        this.setQtdParcelas(qtdParcelas);
    }

    public Conta(){

    }

    public Conta(Date data, Categoria categoria, double valor, String tipo, int id_, String cartao, int qtdParcelas){
        this.setData(data);
        this.setCategoria(categoria);
        this.setTipoConta(tipo);
        this.setValorConta(valor);
        this.setCartao(cartao);
        this.setQtdParcelas(qtdParcelas);
    }

    public Conta(Date data, Categoria categoria, double valor, String tipo, int id_){
        super.setId(id_);
        this.setData(data);
        this.setCategoria(categoria);
        this.setTipoConta(tipo);
        this.setValorConta(valor);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorConta() {
        return valorConta;
    }

    public void setValorConta(double valorConta) {
        this.valorConta = valorConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }


}
