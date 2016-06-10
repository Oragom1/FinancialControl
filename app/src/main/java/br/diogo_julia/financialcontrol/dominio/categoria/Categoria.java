package br.diogo_julia.financialcontrol.dominio.categoria;

import android.os.Parcel;
import android.os.Parcelable;

import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

public class Categoria extends EntidadeDominio implements Parcelable {

    private String nome;

    public Categoria(String nome, int id_) {
        super.setId(id_);
        this.nome = nome;
    }

    protected Categoria(Parcel in) {
        nome = in.readString();
        setId(in.readLong());
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeLong(getId());
    }



    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }


}
