package models;

public class EmpresaProveedora {
    private String nombre;
    private  int nit;
    private ContratoProveedor contrato;

    public EmpresaProveedora(String nombre, int nit, int contrato) throws EmpresaProveedoraException, ContratoProveedorException {
        if(nombre == null){
            throw new EmpresaProveedoraException("Nombre del empresa nulo");
        }if(nit == 0){
            throw new EmpresaProveedoraException("Nit del empresa no puede ser 0");
        }else{
            this.nombre = nombre;
            this.nit = nit;
            this.contrato = new ContratoProveedor(contrato);
        }

    }

    public int getNit(){
        return nit;
    }

    public void setNit(int nit){
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ContratoProveedor getContrato() {
        return contrato;
    }

    public void setContrato(ContratoProveedor contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "EmpresaProveedora{" +
                "nombre='" + nombre + '\'' +
                ", nit=" + nit +
                ", contrato=" + contrato +
                '}';
    }
}
