<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Biblioteca SkillNest - Inicio</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
	body {
		background-color: #f8f9fa;
	}
	.hero-section {
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		color: white;
		padding: 60px 0;
		margin-bottom: 40px;
		border-radius: 0 0 50px 50px;
	}
	.card {
		transition: transform 0.3s;
		border: none;
		box-shadow: 0 4px 6px rgba(0,0,0,0.1);
	}
	.card:hover {
		transform: translateY(-5px);
		box-shadow: 0 8px 12px rgba(0,0,0,0.15);
	}
	.stat-card {
		background: white;
		border-radius: 15px;
		padding: 20px;
		margin: 10px 0;
	}
</style>
</head>
<body>
	<!-- Hero Section -->
	<div class="hero-section">
		<div class="container text-center">
			<h1 class="display-3 fw-bold"><i class="bi bi-book"></i> Biblioteca SkillNest</h1>
			<p class="lead">Sistema de Gestión de Inventario de Biblioteca</p>
		</div>
	</div>

	<div class="container">
		<!-- Estadísticas -->
		<div class="row mb-4">
			<div class="col-md-6">
				<div class="stat-card">
					<h3><i class="bi bi-book-fill text-primary"></i> Total de Libros</h3>
					<h2 class="text-primary">${totalLibros}</h2>
				</div>
			</div>
			<div class="col-md-6">
				<div class="stat-card">
					<h3><i class="bi bi-person-fill text-success"></i> Total de Autores</h3>
					<h2 class="text-success">${totalAutores}</h2>
				</div>
			</div>
		</div>

		<!-- Menú Principal -->
		<div class="row g-4">
			<!-- Gestión de Libros -->
			<div class="col-md-6">
				<div class="card h-100">
					<div class="card-body text-center">
						<i class="bi bi-journal-bookmark display-1 text-primary mb-3"></i>
						<h3 class="card-title">Gestión de Libros</h3>
						<p class="card-text">Administra el catálogo de libros de la biblioteca</p>
						<div class="d-grid gap-2">
							<a class="btn btn-primary btn-lg" href="/libros" role="button">
								<i class="bi bi-list-ul"></i> Ver Libros
							</a>
							<a class="btn btn-outline-primary" href="/libros/crear" role="button">
								<i class="bi bi-plus-circle"></i> Agregar Nuevo Libro
							</a>
							<a class="btn btn-outline-primary" href="/libros/disponibles" role="button">
								<i class="bi bi-check-circle"></i> Libros Disponibles
							</a>
						</div>
					</div>
				</div>
			</div>

			<!-- Gestión de Autores -->
			<div class="col-md-6">
				<div class="card h-100">
					<div class="card-body text-center">
						<i class="bi bi-person-badge display-1 text-success mb-3"></i>
						<h3 class="card-title">Gestión de Autores</h3>
						<p class="card-text">Administra el registro de autores</p>
						<div class="d-grid gap-2">
							<a class="btn btn-success btn-lg" href="/autores" role="button">
								<i class="bi bi-list-ul"></i> Ver Autores
							</a>
							<a class="btn btn-outline-success" href="/autores/crear" role="button">
								<i class="bi bi-plus-circle"></i> Agregar Nuevo Autor
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Información adicional -->
		<div class="row mt-5 mb-4">
			<div class="col-12">
				<div class="card bg-light">
					<div class="card-body">
						<h5 class="card-title"><i class="bi bi-info-circle"></i> Funcionalidades del Sistema</h5>
						<ul class="list-unstyled">
							<li><i class="bi bi-check-circle-fill text-success"></i> Registro y actualización de libros y autores</li>
							<li><i class="bi bi-check-circle-fill text-success"></i> Control de inventario con cantidad disponible y total</li>
							<li><i class="bi bi-check-circle-fill text-success"></i> Búsqueda por título, ISBN, autor</li>
							<li><i class="bi bi-check-circle-fill text-success"></i> Gestión de préstamos y devoluciones</li>
							<li><i class="bi bi-check-circle-fill text-success"></i> Implementación con JPA y JDBC</li>
							<li><i class="bi bi-check-circle-fill text-success"></i> Transacciones con @Transactional</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<footer class="text-center py-4 mt-5 bg-dark text-white">
		<p class="mb-0">© 2024 Biblioteca SkillNest - Sistema de Gestión de Inventario</p>
	</footer>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>