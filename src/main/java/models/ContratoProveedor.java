package models;

public class ContratoProveedor {
    int contrato;

    public ContratoProveedor(int contrato) throws ContratoProveedorException{
        if(contrato<=0){
            throw new ContratoProveedorException("Contrato invalido");
        }else{
            this.contrato = contrato;
        }
    }

    @Override
    public String toString() {
        return "ContratoProveedor{" +
                "idContrato=" + contrato +
                '}';
    }
}
