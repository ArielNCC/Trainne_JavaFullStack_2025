<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle de Autor - Biblioteca SkillNest</title>
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
		<c:if test="${not empty mensaje}">
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				${mensaje}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<div class="card">
			<div class="card-header bg-info text-white">
				<h3 class="mb-0"><i class="bi bi-person-badge"></i> Detalles del Autor</h3>
			</div>
			<div class="card-body">
				<div class="row">
					<div class="col-md-6">
						<p><strong><i class="bi bi-hash"></i> ID:</strong> ${autor.id}</p>
						<p><strong><i class="bi bi-person"></i> Nombre:</strong> ${autor.nombre}</p>
						<p><strong><i class="bi bi-person"></i> Apellido:</strong> ${autor.apellido}</p>
						<p><strong><i class="bi bi-person-vcard"></i> Nombre Completo:</strong> 
							<span class="text-primary fs-5">${autor.nombreCompleto}</span>
						</p>
					</div>
					<div class="col-md-6">
						<p><strong><i class="bi bi-flag"></i> Nacionalidad:</strong> 
							${not empty autor.nacionalidad ? autor.nacionalidad : 'No especificada'}
						</p>
						<p><strong><i class="bi bi-calendar"></i> Fecha de Nacimiento:</strong> 
							${not empty autor.fechaNacimiento ? autor.fechaNacimiento : 'No especificada'}
						</p>
					</div>
				</div>

				<c:if test="${not empty autor.biografia}">
					<hr>
					<div class="mt-3">
						<h5><i class="bi bi-card-text"></i> Biografía:</h5>
						<p class="text-muted">${autor.biografia}</p>
					</div>
				</c:if>

				<hr>
				<div class="d-flex gap-2 justify-content-center">
					<a href="/autores/editar/${autor.id}" class="btn btn-warning">
						<i class="bi bi-pencil"></i> Editar
					</a>
					<a href="/autores/eliminar/${autor.id}" 
						class="btn btn-danger"
						onclick="return confirm('¿Está seguro de eliminar este autor?')">
						<i class="bi bi-trash"></i> Eliminar
					</a>
					<a href="/autores" class="btn btn-secondary">
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
