
/**
 * Modelo que representa una respuesta de encuesta.
 * Contiene los datos ingresados por el usuario en el formulario.
 */
package com.skillnest.model;

/**
 * Clase modelo para representar una respuesta de encuesta
 */
public class RespuestaEncuesta {
    private String nombre;
    private int edad;
    private String recomendaria;
    private int calificacion;
    private String comentario;
    
    /**
     * Constructor vacío requerido por JavaBeans.
     */
    public RespuestaEncuesta() {}

    /**
     * Constructor completo para inicializar todos los campos.
     * @param nombre Nombre del usuario
     * @param edad Edad del usuario
     * @param recomendaria Si recomendaría el sitio
     * @param calificacion Calificación otorgada
     * @param comentario Comentario adicional
     */
    public RespuestaEncuesta(String nombre, int edad, String recomendaria,
                           int calificacion, String comentario) {
        this.nombre = nombre;
        this.edad = edad;
        this.recomendaria = recomendaria;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la edad del usuario.
     * @return edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del usuario.
     * @param edad edad
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene si recomendaría el sitio.
     * @return recomendaria
     */
    public String getRecomendaria() {
        return recomendaria;
    }

    /**
     * Establece si recomendaría el sitio.
     * @param recomendaria recomendaria
     */
    public void setRecomendaria(String recomendaria) {
        this.recomendaria = recomendaria;
    }

    /**
     * Obtiene la calificación otorgada.
     * @return calificacion
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación otorgada.
     * @param calificacion calificacion
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el comentario adicional.
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario adicional.
     * @param comentario comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    /**
     * Representación en texto de la respuesta de encuesta.
     * @return string descriptivo
     */
    @Override
    public String toString() {
        return "RespuestaEncuesta{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", recomendaria='" + recomendaria + '\'' +
                ", calificacion=" + calificacion +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}