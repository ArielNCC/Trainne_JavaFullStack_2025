<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle de Libro - Biblioteca SkillNest</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
	.info-badge {
		font-size: 1.1rem;
		padding: 10px 15px;
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

		<div class="card">
			<div class="card-header bg-info text-white">
				<h3 class="mb-0"><i class="bi bi-book-half"></i> Detalles del Libro</h3>
			</div>
			<div class="card-body">
				<div class="row">
					<!-- Información Principal -->
					<div class="col-md-8">
						<h2 class="text-primary mb-3">${libro.titulo}</h2>
						
						<div class="mb-3">
							<p><strong><i class="bi bi-hash"></i> ID:</strong> ${libro.id}</p>
							<p><strong><i class="bi bi-upc"></i> ISBN:</strong> <code>${libro.isbn}</code></p>
							<p><strong><i class="bi bi-person-badge"></i> Autor:</strong> 
								<a href="/autores/detalle/${libro.autor.id}" class="text-decoration-none">
									${libro.autor.nombreCompleto}
								</a>
							</p>
							<p><strong><i class="bi bi-tags"></i> Género:</strong> 
								<span class="badge bg-secondary">${libro.genero}</span>
							</p>
							<p><strong><i class="bi bi-building"></i> Editorial:</strong> 
								${not empty libro.editorial ? libro.editorial : 'No especificada'}
							</p>
							<p><strong><i class="bi bi-calendar"></i> Año de Publicación:</strong> 
								${not empty libro.anioPublicacion ? libro.anioPublicacion : 'No especificado'}
							</p>
							<p><strong><i class="bi bi-file-earmark-text"></i> Número de Páginas:</strong> 
								${not empty libro.numeroPaginas ? libro.numeroPaginas : 'No especificado'}
							</p>
						</div>
					</div>

					<!-- Panel de Disponibilidad -->
					<div class="col-md-4">
						<div class="card bg-light">
							<div class="card-body text-center">
								<h5 class="card-title">Disponibilidad</h5>
								<c:choose>
									<c:when test="${libro.cantidadDisponible > 0}">
										<div class="alert alert-success">
											<i class="bi bi-check-circle-fill fs-1"></i>
											<h3 class="mt-2">Disponible</h3>
										</div>
										<span class="badge bg-success info-badge">
											${libro.cantidadDisponible} de ${libro.cantidadTotal} disponibles
										</span>
										
										<form action="/libros/prestar/${libro.id}" method="post" class="mt-3">
											<button type="submit" class="btn btn-primary w-100">
												<i class="bi bi-box-arrow-right"></i> Prestar Libro
											</button>
										</form>
									</c:when>
									<c:otherwise>
										<div class="alert alert-danger">
											<i class="bi bi-x-circle-fill fs-1"></i>
											<h3 class="mt-2">Agotado</h3>
										</div>
										<span class="badge bg-danger info-badge">
											0 de ${libro.cantidadTotal} disponibles
										</span>
									</c:otherwise>
								</c:choose>

								<c:if test="${libro.cantidadDisponible < libro.cantidadTotal}">
									<form action="/libros/devolver/${libro.id}" method="post" class="mt-3">
										<button type="submit" class="btn btn-success w-100">
											<i class="bi bi-box-arrow-in-left"></i> Devolver Libro
										</button>
									</form>
								</c:if>
							</div>
						</div>
					</div>
				</div>

				<hr>
				
				<!-- Acciones -->
				<div class="d-flex gap-2 justify-content-center flex-wrap">
					<a href="/libros/editar/${libro.id}" class="btn btn-warning">
						<i class="bi bi-pencil"></i> Editar
					</a>
					<a href="/libros/eliminar/${libro.id}" 
						class="btn btn-danger"
						onclick="return confirm('¿Está seguro de eliminar este libro?')">
						<i class="bi bi-trash"></i> Eliminar
					</a>
					<a href="/libros" class="btn btn-secondary">
						<i class="bi bi-arrow-left"></i> Volver a la lista
					</a>
					<a href="/" class="btn btn-outline-primary">
						<i class="bi bi-house"></i> Inicio
					</a>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
