<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Libro - Biblioteca SkillNest</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
		<div class="container">
			<a class="navbar-brand" href="/"><i class="bi bi-book"></i> Biblioteca SkillNest</a>
			<div class="navbar-nav ms-auto">
				<a class="nav-link active" href="/libros">Libros</a>
				<a class="nav-link" href="/autores">Autores</a>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card">
					<div class="card-header bg-warning">
						<h3 class="mb-0"><i class="bi bi-pencil-square"></i> Editar Libro</h3>
					</div>
					<div class="card-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								${error}
								<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
							</div>
						</c:if>

						<form action="/libros/actualizar" method="post">
							<input type="hidden" name="id" value="${libro.id}">

							<div class="row">
								<div class="col-md-8 mb-3">
									<label for="titulo" class="form-label">
										<i class="bi bi-bookmark"></i> Título: <span class="text-danger">*</span>
									</label>
									<input type="text" class="form-control" id="titulo" name="titulo" 
										value="${libro.titulo}" required>
								</div>

								<div class="col-md-4 mb-3">
									<label for="isbn" class="form-label">
										<i class="bi bi-upc"></i> ISBN: <span class="text-danger">*</span>
									</label>
									<input type="text" class="form-control" id="isbn" name="isbn" 
										value="${libro.isbn}" required>
								</div>
							</div>

							<div class="mb-3">
								<label for="autorId" class="form-label">
									<i class="bi bi-person-badge"></i> Autor: <span class="text-danger">*</span>
								</label>
								<select class="form-select" id="autorId" name="autorId" required>
									<option value="">Seleccione un autor...</option>
									<c:forEach var="autor" items="${autores}">
										<option value="${autor.id}" ${autor.id == libro.autor.id ? 'selected' : ''}>
											${autor.nombreCompleto}
										</option>
									</c:forEach>
								</select>
							</div>

							<div class="row">
								<div class="col-md-4 mb-3">
									<label for="anioPublicacion" class="form-label">
										<i class="bi bi-calendar"></i> Año Publicación:
									</label>
									<input type="number" class="form-control" id="anioPublicacion" 
										name="anioPublicacion" min="1000" max="2024" 
										value="${libro.anioPublicacion}">
								</div>

								<div class="col-md-4 mb-3">
									<label for="genero" class="form-label">
										<i class="bi bi-tags"></i> Género:
									</label>
									<input type="text" class="form-control" id="genero" name="genero" 
										value="${libro.genero}">
								</div>

								<div class="col-md-4 mb-3">
									<label for="numeroPaginas" class="form-label">
										<i class="bi bi-file-earmark-text"></i> Páginas:
									</label>
									<input type="number" class="form-control" id="numeroPaginas" 
										name="numeroPaginas" min="1" value="${libro.numeroPaginas}">
								</div>
							</div>

							<div class="mb-3">
								<label for="editorial" class="form-label">
									<i class="bi bi-building"></i> Editorial:
								</label>
								<input type="text" class="form-control" id="editorial" name="editorial" 
									value="${libro.editorial}">
							</div>

							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="cantidadTotal" class="form-label">
										<i class="bi bi-stack"></i> Cantidad Total:
									</label>
									<input type="number" class="form-control" id="cantidadTotal" 
										name="cantidadTotal" min="0" value="${libro.cantidadTotal}">
								</div>

								<div class="col-md-6 mb-3">
									<label for="cantidadDisponible" class="form-label">
										<i class="bi bi-check-circle"></i> Cantidad Disponible:
									</label>
									<input type="number" class="form-control" id="cantidadDisponible" 
										name="cantidadDisponible" min="0" max="${libro.cantidadTotal}" 
										value="${libro.cantidadDisponible}">
								</div>
							</div>

							<div class="alert alert-warning">
								<i class="bi bi-exclamation-triangle"></i> 
								<strong>Nota:</strong> La cantidad disponible no puede ser mayor que la cantidad total
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn btn-warning btn-lg">
									<i class="bi bi-save"></i> Actualizar Libro
								</button>
								<a href="/libros" class="btn btn-secondary">
									<i class="bi bi-x-circle"></i> Cancelar
								</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Validar que cantidad disponible no sea mayor que cantidad total
		document.querySelector('form').addEventListener('submit', function(e) {
			const cantidadTotal = parseInt(document.getElementById('cantidadTotal').value) || 0;
			const cantidadDisponible = parseInt(document.getElementById('cantidadDisponible').value) || 0;
			
			if (cantidadDisponible > cantidadTotal) {
				e.preventDefault();
				alert('La cantidad disponible no puede ser mayor que la cantidad total');
			}
		});
	</script>
</body>
</html>
