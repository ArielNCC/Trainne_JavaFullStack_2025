$(document).ready(function() {
  // Mostrar/ocultar ejemplos de amenazas
  $('.show-example').click(function() {
    var target = $(this).data('target');
    $(target).slideToggle();
  });

  // Validación del formulario de contacto
  $('#contactoForm').on('submit', function(e) {
    if(this.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }
    $(this).addClass('was-validated');
  });

  // Test de seguridad
  $('#testForm').on('submit', function(e) {
    e.preventDefault();
    var correcta1 = $('select[name="pregunta1"]').val() === 'a';
    var correcta2 = $('select[name="pregunta2"]').val() === 'b';
    var feedback = '';
    if(correcta1 && correcta2) {
      feedback = '<div class="alert alert-success">¡Excelente! Tus respuestas son correctas.</div>';
    } else {
      feedback = '<div class="alert alert-danger">Revisa las respuestas. Algunas son incorrectas.</div>';
    }
    $('#testFeedback').html(feedback);
  });

  // Limpiar feedback al cerrar el modal
  $('#testModal').on('hidden.bs.modal', function () {
    $('#testFeedback').html('');
    $('#testForm')[0].reset();
  });
});