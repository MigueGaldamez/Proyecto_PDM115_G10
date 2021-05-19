package com.example.proyecto_pdm_g10;

public class AsistenciaEmpleado {
    private String idAsistenciaEmpleado;
    private String asistencia;
    private String empleadoId;
    private String capacitacionId;


    public AsistenciaEmpleado() {
    }

    public AsistenciaEmpleado(String idAsistenciaEmpleado, String asistencia, String empleadoId, String capacitacionId) {
        this.idAsistenciaEmpleado = idAsistenciaEmpleado;
        this.asistencia = asistencia;
        this.empleadoId = empleadoId;
        this.capacitacionId = capacitacionId;
    }

    public String getIdAsistenciaEmpleado() {
        return idAsistenciaEmpleado;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public String getEmpleadoId() {
        return empleadoId;
    }

    public String getCapacitacionId() {
        return capacitacionId;
    }

    public void setIdAsistenciaEmpleado(String idAsistenciaEmpleado) {
        this.idAsistenciaEmpleado = idAsistenciaEmpleado;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public void setCapacitacionId(String capacitacionId) {
        this.capacitacionId = capacitacionId;
    }
}


