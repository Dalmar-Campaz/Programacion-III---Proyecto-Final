
package servicios;

import excepciones.EstudianteNoEncontradoException;
import modelos.Estudiante;

class EstudianteService {

    public void registrarNota(String idEstudiante, int semestre, double nota)
            throws EstudianteNoEncontradoException {

        Estudiante e = buscarPorId(idEstudiante);

        if (nota < 0.0 || nota > 5.0)
            throw new IllegalArgumentException("Nota fuera de rango");

        e.registrarNota(semestre, nota);
    }

    public Estudiante buscarPorId(String idEstudiante){
        return null;
    }
}
