<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Libros - Biblioteca SkillNest</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
	.disponible {
		color: green;
		font-weight: bold;
	}
	.no-disponible {
		color: red;
	}
</style>
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
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2><i class="bi bi-journal-bookmark"></i> Catálogo de Libros</h2>
			<a class="btn btn-success" href="/libros/crear"><i class="bi bi-plus-circle"></i> Nuevo Libro</a>
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

		<!-- Filtros -->
		<div class="card mb-4">
			<div class="card-body">
				<div class="row g-3">
					<div class="col-md-8">
						<form action="/libros/buscar" method="get" class="d-flex gap-2">
							<input type="text" class="form-control" name="titulo" 
								placeholder="Buscar por título..." value="${busqueda}">
							<button type="submit" class="btn btn-primary">
								<i class="bi bi-search"></i> Buscar
							</button>
						</form>
					</div>
					<div class="col-md-4">
						<a href="/libros/disponibles" class="btn btn-outline-success w-100">
							<i class="bi bi-check-circle"></i> Solo Disponibles
						</a>
					</div>
				</div>
			</div>
		</div>

		<!-- Tabla de libros -->
		<div class="card">
			<div class="card-body">
				<c:choose>
					<c:when test="${empty libros}">
						<div class="alert alert-info">
							<i class="bi bi-info-circle"></i> No hay libros registrados
						</div>
					</c:when>
					<c:otherwise>
						<div class="table-responsive">
							<table class="table table-hover">
								<thead class="table-dark">
									<tr>
										<th>#</th>
										<th>Título</th>
										<th>ISBN</th>
										<th>Autor</th>
										<th>Género</th>
										<th>Disponible</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="libro" items="${libros}">
										<tr>
											<td>${libro.id}</td>
											<td><strong>${libro.titulo}</strong></td>
											<td><code>${libro.isbn}</code></td>
											<td>${libro.autor.nombreCompleto}</td>
											<td><span class="badge bg-secondary">${libro.genero}</span></td>
											<td>
												<c:choose>
													<c:when test="${libro.cantidadDisponible > 0}">
														<span class="disponible">
															<i class="bi bi-check-circle-fill"></i> 
															${libro.cantidadDisponible}/${libro.cantidadTotal}
														</span>
													</c:when>
													<c:otherwise>
														<span class="no-disponible">
															<i class="bi bi-x-circle-fill"></i> Agotado
														</span>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<a href="/libros/detalle/${libro.id}" class="btn btn-sm btn-info">
													<i class="bi bi-eye"></i>
												</a>
												<a href="/libros/editar/${libro.id}" class="btn btn-sm btn-warning">
													<i class="bi bi-pencil"></i>
												</a>
												<a href="/libros/eliminar/${libro.id}" 
													class="btn btn-sm btn-danger"
													onclick="return confirm('¿Está seguro de eliminar este libro?')">
													<i class="bi bi-trash"></i>
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<p class="text-muted mt-3">Total de libros: ${libros.size()}</p>
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
