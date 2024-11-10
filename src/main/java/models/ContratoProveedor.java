package models;

import java.io.Serializable;

public class ContratoProveedor implements Serializable {
    private String contrato;

    public ContratoProveedor(String contrato) throws ContratoProveedorException{
        if(contrato.equalsIgnoreCase("")){
            throw new ContratoProveedorException("Contrato invalido");
        }else{
            this.contrato = contrato;
        }
    }

    String getContrato() {
        return contrato;
    }

    @Override
    public String toString() {
        return "ContratoProveedor{" +
                "idContrato=" + contrato +
                '}';
    }
}
