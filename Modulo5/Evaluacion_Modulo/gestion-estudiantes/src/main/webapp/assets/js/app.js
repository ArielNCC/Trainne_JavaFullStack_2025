// Validación del formulario de estudiantes
function validarFormulario() {
    const nombreCompleto = document.getElementById('nombreCompleto').value.trim();
    const correoElectronico = document.getElementById('correoElectronico').value.trim();
    const carrera = document.getElementById('carrera').value.trim();
    
    if (nombreCompleto === '') {
        alert('❌ El nombre completo es obligatorio');
        return false;
    }
    
    if (nombreCompleto.length < 3) {
        alert('❌ El nombre debe tener al menos 3 caracteres');
        return false;
    }
    
    if (correoElectronico === '') {
        alert('❌ El correo electrónico es obligatorio');
        return false;
    }
    
    // Validar formato de correo electrónico
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(correoElectronico)) {
        alert('❌ El correo electrónico no tiene un formato válido');
        return false;
    }
    
    if (carrera === '') {
        alert('❌ La carrera es obligatoria');
        return false;
    }
    
    if (carrera.length < 3) {
        alert('❌ La carrera debe tener al menos 3 caracteres');
        return false;
    }
    
    return true;
}

// Confirmación antes de eliminar
function confirmarEliminacion(nombreEstudiante) {
    return confirm('¿Está seguro que desea eliminar al estudiante ' + nombreEstudiante + '?\n\nEsta acción no se puede deshacer.');
}
