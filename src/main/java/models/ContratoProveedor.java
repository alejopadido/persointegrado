package models;

import java.io.Serializable;

public class ContratoProveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String contrato;

    public ContratoProveedor(String contrato) throws ContratoProveedorException{
        if(contrato.equalsIgnoreCase("")){
            throw new ContratoProveedorException("Contrato invalido");
        }else{
            this.contrato = contrato;
        }
    }

    public String getContrato() {
        return contrato;
    }

    @Override
    public String toString() {
        return "ContratoProveedor{" +
                "idContrato=" + contrato +
                '}';
    }
}
