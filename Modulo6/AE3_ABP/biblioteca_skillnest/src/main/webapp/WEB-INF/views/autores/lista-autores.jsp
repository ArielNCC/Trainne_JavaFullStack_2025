<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Autores - Biblioteca SkillNest</title>
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
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2><i class="bi bi-person-badge"></i> Lista de Autores</h2>
			<a class="btn btn-success" href="/autores/crear"><i class="bi bi-plus-circle"></i> Nuevo Autor</a>
		</div>

		<c:if test="${not empty mensaje}">
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				${mensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				${error}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<!-- Buscador -->
		<div class="card mb-4">
			<div class="card-body">
				<form action="/autores/buscar" method="get" class="row g-3">
					<div class="col-md-10">
						<input type="text" class="form-control" name="apellido" 
							placeholder="Buscar por apellido..." value="${busqueda}">
					</div>
					<div class="col-md-2">
						<button type="submit" class="btn btn-primary w-100">
							<i class="bi bi-search"></i> Buscar
						</button>
					</div>
				</form>
			</div>
		</div>

		<!-- Tabla de autores -->
		<div class="card">
			<div class="card-body">
				<c:choose>
					<c:when test="${empty autores}">
						<div class="alert alert-info">
							<i class="bi bi-info-circle"></i> No hay autores registrados
						</div>
					</c:when>
					<c:otherwise>
						<div class="table-responsive">
							<table class="table table-hover">
								<thead class="table-dark">
									<tr>
										<th>#</th>
										<th>Nombre Completo</th>
										<th>Nacionalidad</th>
										<th>Fecha Nacimiento</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="autor" items="${autores}">
										<tr>
											<td>${autor.id}</td>
											<td><strong>${autor.nombreCompleto}</strong></td>
											<td>${autor.nacionalidad}</td>
											<td>${autor.fechaNacimiento != null ? autor.fechaNacimiento : 'N/A'}</td>
											<td>
												<a href="/autores/detalle/${autor.id}" class="btn btn-sm btn-info">
													<i class="bi bi-eye"></i> Ver
												</a>
												<a href="/autores/editar/${autor.id}" class="btn btn-sm btn-warning">
													<i class="bi bi-pencil"></i> Editar
												</a>
												<a href="/autores/eliminar/${autor.id}" 
													class="btn btn-sm btn-danger"
													onclick="return confirm('¿Está seguro de eliminar este autor?')">
													<i class="bi bi-trash"></i> Eliminar
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<p class="text-muted mt-3">Total de autores: ${autores.size()}</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="mt-3">
			<a href="/" class="btn btn-secondary"><i class="bi bi-house"></i> Volver al Inicio</a>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
