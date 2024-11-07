package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersoIntegrado implements Serializable {
    List<EmpresaProvedora> empresas;

    public PersoIntegrado() {
        empresas = new ArrayList<EmpresaProvedora>();
    }
}
