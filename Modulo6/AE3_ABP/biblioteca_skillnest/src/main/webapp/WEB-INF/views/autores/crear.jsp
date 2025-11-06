<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Autor - Biblioteca SkillNest</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
		<div class="container">
			<a class="navbar-brand" href="/"><i class="bi bi-book"></i> Biblioteca SkillNest</a>
			<div class="navbar-nav ms-auto">
				<a class="nav-link" href="/libros">Libros</a>
				<a class="nav-link active" href="/autores">Autores</a>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card">
					<div class="card-header bg-success text-white">
						<h3 class="mb-0"><i class="bi bi-person-plus"></i> Registrar Nuevo Autor</h3>
					</div>
					<div class="card-body">
						<%-- Mostrar errores si existen --%>
						<c:if test="${not empty error}">
							<div class="alert alert-danger alert-dismissible fade show" role="alert">
								${error}
								<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
							</div>
						</c:if>

						<form action="/autores/guardar" method="post">
							<div class="mb-3">
								<label for="nombre" class="form-label">
									<i class="bi bi-person"></i> Nombre: <span class="text-danger">*</span>
								</label>
								<input type="text" class="form-control" id="nombre" name="nombre" required>
							</div>

							<div class="mb-3">
								<label for="apellido" class="form-label">
									<i class="bi bi-person"></i> Apellido: <span class="text-danger">*</span>
								</label>
								<input type="text" class="form-control" id="apellido" name="apellido" required>
							</div>

						<div class="mb-3">
							<label for="nacionalidad" class="form-label">
								<i class="bi bi-flag"></i> Nacionalidad:
							</label>
							<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" 
								placeholder="Ej: Colombiano, Chilena, Argentino">
						</div>

						<div class="mb-3">
							<label for="fechaNacimiento" class="form-label">
								<i class="bi bi-calendar"></i> Fecha de Nacimiento:
							</label>
							<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento">
						</div>

						<div class="mb-3">
							<label for="biografia" class="form-label">
								<i class="bi bi-card-text"></i> Biografía:
							</label>
							<textarea class="form-control" id="biografia" name="biografia" 
								rows="4" placeholder="Breve biografía del autor..."></textarea>
						</div>							<div class="alert alert-info">
								<i class="bi bi-info-circle"></i> Los campos marcados con <span class="text-danger">*</span> son obligatorios
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn btn-success btn-lg">
									<i class="bi bi-save"></i> Guardar Autor
								</button>
								<a href="/autores" class="btn btn-secondary">
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
</body>
</html>
